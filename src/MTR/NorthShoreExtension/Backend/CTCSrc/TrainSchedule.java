/* Filename: TrainSchedule.java
 * Author: Matt Snyder
 * Last Edited: 11/24/17
 * File Description: The back-end object for containing the train schedules. One will be created for each train that is scheduled.
 * Its main function is to hold the information for each train's list of stops, the planned departures time for each stop, and the
 * line that it is on.  
 */

package MTR.NorthShoreExtension.Backend.CTCSrc;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

import MTR.NorthShoreExtension.Backend.CTCSrc.trainScheduler;

public class TrainSchedule {
	
	String trackLine = "";
	int[] stops = new int[150];
	//int[] authority;
	List<Integer> authority = new ArrayList<Integer>();
	int[] departures = new int [150];
	int trainID = 0;
	int currLocation = 999; //999 represents the Yard
	int nextLocation = 888;
	
	
	public TrainSchedule(String line, int id, int[] listOfStops, int[] departureTimes) {
		trainID = id;
		System.out.println("ID: " + trainID);
		stops = listOfStops;
		authority = trainScheduler.calcAuthority(listOfStops);
		for (int i = 0; i < authority.size(); i++) {
			System.out.println(":-: " + authority.get(i));
		}
		trackLine = line;
		if (line.equals("Green")) {
			nextLocation = 2062;
		} else {
			nextLocation = 1001; //replace with appropriate redline start point
		}
	}
	
	public int getTrainID() {
		return trainID;
	}
	
	public String getFirstStop() {
		String first = Integer.toString(stops[0]);
		return first;
	}
	
	public String getLine() {
		return trackLine;
	}
	
	public int[] getListOfStops() {
		return stops;
	}
	
	public int getCurrentLocation() {
		return currLocation;
	}
	
	public int getNextLocation() {
		return nextLocation;
	}
	
	public void setCurrentLocation(int trackID) {
		currLocation = trackID;
		//TrainScheduleHelper.trainTracker.set(trainID, trackID);
	}
	
	public void setNextLocation(int trackID) {
		nextLocation = trackID;
	}
};