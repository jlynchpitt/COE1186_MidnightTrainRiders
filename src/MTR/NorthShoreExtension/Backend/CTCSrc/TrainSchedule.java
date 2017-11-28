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

import MTR.NorthShoreExtension.Backend.CTCSrc.trainScheduler;

public class TrainSchedule {
	
	String trackLine = "";
	static int[] stops = new int[99];
	int[] authority;
	int[] departures = new int [99];
	static int trainID = 0;
	
	
	public TrainSchedule(String line, int id, int[] listOfStops, int[] departureTimes) {
		trainID = id;
		stops = listOfStops;
		authority = trainScheduler.calcAuthority(listOfStops);
		/*for (int i = 0; i < authority.length; i++) {
			System.out.println(":-: " + authority[i]);
		}*/
		trackLine = line;
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
};