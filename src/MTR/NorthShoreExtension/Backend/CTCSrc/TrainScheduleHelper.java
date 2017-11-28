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

import javax.swing.*;

import MTR.NorthShoreExtension.Backend.CTCSrc.TrainSchedule;

public class TrainScheduleHelper {
	static List<TrainSchedule> tsl;
	static int[] occupied = new int[250];
	static int[] broken = new int [250];
	
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
		occupied = occupiedTracks;
	}
	
	public static void CTC_getBrokenTracks(int[] brokenTracks) {
		broken = brokenTracks;
	}
}