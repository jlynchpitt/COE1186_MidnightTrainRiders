/*
 * Filename: TrainScheduleHelper.java
 * Author: Matt Snyder
 * Last Edited: 11/22/17
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

public class TrainScheduleHelper {
	static List<TrainSchedule> tsl;
	static List<Integer> occupied = new ArrayList<Integer>();
	public static List<Integer> trainTracker = new ArrayList<Integer>();
	static int[] broken = new int [250];
	static DBHelper database = MainMTR.getDBHelper();
	
	public TrainScheduleHelper() {
		tsl = new ArrayList<TrainSchedule>();
	}
	
	public void addNewTrainSchedule(String line, int id, int[] stops, int[] departures) {
		TrainSchedule ts = new TrainSchedule(line, id, stops, departures);
		//System.out.println("\n Line: " + line + " id: " + id + "First stop: " + stops[0]);
		//System.out.println("\n Line: " + ts.getLine() + "id: " + ts.getTrainID() + " First stop: " + ts.getFirstStop());
		tsl.add(ts);
	}
	
	public List<TrainSchedule> getTrainScheduleList() {	
		for (int i = 0; i < tsl.size(); i++) {
			System.out.println(i);
			System.out.print("\n Stop: " + tsl.get(i).getFirstStop());
		}
		return tsl;
	}
	
	public static void CTC_getOccupiedTracks(int[] occupiedTracks) {
		for (int i = 0; i < occupiedTracks.length; i++) { //convert into arrayList
			occupied.add(occupiedTracks[i]);
		} 
		
		for (int j = 0; j < tsl.size(); j++) { //check each train schedule to see if 
			System.out.println("Checking train: " + j);
			if (occupied.contains(trainTracker.get(j))) {
				//Train at J didn't move, do nothing
			} else {
				//train at J did move
				if (trainTracker.get(j) == 9999) {
					//train left the yard, set location to first section of track
					trainTracker.set(j, tsl.get(j).authority.get(0));
					System.out.println("Left Yard for: " + trainTracker.get(j));
					int currTrack = tsl.get(j).authority.get(0);
					tsl.get(j).authority.remove(0);
					int[] auth = new int[tsl.get(j).authority.size()];
					for (int i = 0; i < auth.length; i++) {
						auth[i] = tsl.get(j).authority.get(i);
					}
					WaysideFunctionsHub.OccupiedSpeedAuthority(currTrack, database.getSpeedLimit(tsl.get(j).authority.get(j)), auth);
					System.out.println("New Speed and Authority sent: " + database.getSpeedLimit(tsl.get(j).authority.get(j)) + " : " + tsl.get(j).authority.get(0));
				} else {
					//train moved forward, set location to next section of track
					trainTracker.set(j, tsl.get(j).authority.get(1));
					System.out.println("Moved to: " + trainTracker.get(j));
					int currTrack = tsl.get(j).authority.get(0);
					tsl.get(j).authority.remove(0);
					int[] auth = new int[tsl.get(j).authority.size()];
					for (int i = 0; i < auth.length; i++) {
						auth[i] = tsl.get(j).authority.get(i);
					}
					WaysideFunctionsHub.OccupiedSpeedAuthority(currTrack, database.getSpeedLimit(tsl.get(j).authority.get(j)), auth);
					System.out.println("Next Speed and Authority sent: " + database.getSpeedLimit(tsl.get(j).authority.get(j)) + " : " + tsl.get(j).authority.get(0));
				}
				
			}
		}
		//from start of occupied array until end of it
			//loop: compare with xth train's currLocation
			//determine which train(s) moved, remove appropriate section from the authority list
			//resend the authority and speed
	}
	
	public static void CTC_getBrokenTracks(int[] brokenTracks) {
		broken = brokenTracks;
	}
}