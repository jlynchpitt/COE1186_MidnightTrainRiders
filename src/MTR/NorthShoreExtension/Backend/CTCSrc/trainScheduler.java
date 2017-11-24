/*
* Filename: trainScheduler.java
* Author: Matt Snyder
* Last Edited: 11/11/2017
* File Description: The back-end operations of the Train Scheduler sub-component
*/

package MTR.NorthShoreExtension.Backend.CTCSrc;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.CTCSrc.TrainSchedule;
import MTR.NorthShoreExtension.UI.TrainSchedulePanel;
import MTR.NorthShoreExtension.UI.ctcUI;


public class trainScheduler {
	static DBHelper database;
	
	int[] redLine = new int[99];
	int[] grnLine = new int[199];
	//import database info for greenline and red line
	//static List<TrainSchedule> tsl = new ArrayList<TrainSchedule>();
	
	public static int[] calcAuthority(int[] listOfStops) {
		
		int[] authority = new int[199];
		System.out.println("trainScheduler: first stop is " + listOfStops[0]);
		if (listOfStops[0] < 1100 && listOfStops[0] >= 1000) {
			//use red line
			authority[0] = 1001; //leaves the yard and goes to the first section of track
		} else if (listOfStops[0] >= 2000 && listOfStops[0] <= 2151) {
			//use green line
			authority[0] = 2001; //first section of green line
		}
		
		return authority;
	}
	
	/*public static void addTrainSchedule(String line, int trainID, int[] stops, int[] departures) {
		//int id = Integer.parseInt(trainID);
		//return ts;
		ctcUI.tsh.addNewTrainSchedule(line, trainID, stops, departures);
		//TrainSchedulePanel tsp = new TrainSchedulePanel(ts);
	}*/
	
	/*public List<TrainSchedule> getTrainScheduleList() {
		System.out.println(tsl);
		return tsl;
	}*/
	/*Scheduling helpers CTC_getBrokenTrack(int[] brokenTracks) {	
	
		}
	*/
	
	//Functions for setting speed and authority
	//WaysideFunctionsHub.WaysideController_Authority(int[] authority);
	//WaysideFunctionsHub.WaysideController_Speed(int[] speed);
}