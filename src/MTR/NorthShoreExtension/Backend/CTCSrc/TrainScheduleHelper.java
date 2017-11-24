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
	
	public TrainScheduleHelper() {
		tsl = new ArrayList<TrainSchedule>();
	}
	
	public void addNewTrainSchedule(String line, int id, int[] stops, int[] departures) {
		TrainSchedule ts = new TrainSchedule(line, id, stops, departures);
		tsl.add(ts);
	}
	
	public List<TrainSchedule> getTrainScheduleList() {	
		for (int i = 0; i < tsl.size(); i++) {
			System.out.print(tsl.get(i).getTrainID());
		}
		return tsl;
	}
}