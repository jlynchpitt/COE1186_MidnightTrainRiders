/*
 * Filename: TrainScheduleHelper.java
 * Author: Matt Snyder
 * Last Edited: 12/13/17
 * File Description: A helper class for managing train schedules 
 */
package MTR.NorthShoreExtension.Backend.CTCSrc;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import javax.swing.*;

import MTR.NorthShoreExtension.Backend.CTCSrc.TrainSchedule;
import MTR.NorthShoreExtension.Backend.WaysideController.*;
import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.*;
import MTR.NorthShoreExtension.UI.ctcUI;

public class TrainScheduleHelper {
	static List<TrainSchedule> tsl;
	static List<Integer> occupied = new ArrayList<Integer>();
	public static List<Integer> trainTracker = new ArrayList<Integer>();
	static DBHelper database = MainMTR.getDBHelper();
	static int[] broken = new int [database.getDatabaseSize()+1];
	static int occUpdatedCounter = 0;
	
	public TrainScheduleHelper() {
		tsl = new ArrayList<TrainSchedule>();
	}
	
	public void addNewTrainSchedule(String line, int id, int[] stops, int[] departures, int speed) {
		TrainSchedule ts = new TrainSchedule(line, id, stops, departures, speed);
		//System.out.println("\n Line: " + line + " id: " + id + "First stop: " + stops[0]);
		//System.out.println("\n Line: " + ts.getLine() + "id: " + ts.getTrainID() + " First stop: " + ts.getFirstStop());
		tsl.add(ts);
		ctcUI.setNumTrains(tsl.size());
	}
	
	public List<TrainSchedule> getTrainScheduleList() {	
		for (int i = 0; i < tsl.size(); i++) {
			System.out.println(i);
			System.out.print("\n Stop: " + tsl.get(i).getFirstStop());
		}
		return tsl;
	}
	
	public static void CTC_getOccupiedTracks(int[] occupiedTracks) {
		occupied = new ArrayList<Integer>();
		System.out.println("Occupancy Updated, Function Called: " + occUpdatedCounter);
		for (int i = 0; i < occupiedTracks.length; i++) { //convert into arrayList
			if (occupiedTracks[i] != 0) {
				occupied.add(occupiedTracks[i]);
			}
		} 
		
		//for every train in the array list
		for (int j = 0; j < tsl.size(); j++) {
			System.out.println("Checking train: " + j);
			System.out.println("Train Info: ");
			System.out.println("Current Location: " + tsl.get(j).getCurrentLocation());
			System.out.println("Next Location: " + tsl.get(j).getNextLocation());
			//check if the occupied arraylist has this train's currLocation value
			if (occupied.contains(tsl.get(j).getCurrentLocation())) {
				//Train didn't move
				System.out.println("Train " + j + " didn't move..");
			} else {
				//if not there, check to see if the list contains this train's nextLocation
				if (occupied.contains(tsl.get(j).getNextLocation())) {
					//train moved! update it's position and authority
					tsl.get(j).setCurrentLocation(tsl.get(j).getNextLocation()); 
					tsl.get(j).authority.remove(0); //this should remove the next location from the authority and then shift everything to the left
					if (tsl.get(j).authority.size() != 0) {
						//if the authority isn't empty
						tsl.get(j).setNextLocation(tsl.get(j).authority.get(0));
					}
					else {
						//if there is no authority left, send an empty array?
						tsl.get(j).setNextLocation(0000);
					}
					int[] auth = new int[tsl.get(j).authority.size()];
					for (int i = 0; i < auth.length; i++) {
						auth[i] = tsl.get(j).authority.get(i);
					}
					WaysideFunctionsHub.OccupiedSpeedAuthority(tsl.get(j).getCurrentLocation(), tsl.get(j).getSetSpeed(), auth);
					System.out.println("Train " + j + " has moved");
				} else {
					//Your train just vanished!!!!
					System.out.println("Houston we have a problem!");
				}
			}
		}
		
		occUpdatedCounter++;
	
	}
	
	public static void CTC_getBrokenTracks(int[] brokenTracks) {
		broken = brokenTracks;
	}
}