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

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.CTCSrc.TrainSchedule;
import MTR.NorthShoreExtension.UI.TrainSchedulePanel;


public class trainScheduler {
	static DBHelper database = MainMTR.getDBHelper();
	
	int[] redLine = new int[75];
	int[] grnLine = new int[150];
	//import database info for greenline and red line
	static String[] importedGrn;
	static String[] importedRed;
	
	//stop[i]
	// if stop [0] == first section of track
	//	 authority[0] = 1001;
	//	 i++;
	// else
	//   authority[0] = 1001;
	//   while authority[j] != stop[0]
	//		authority[j] = authority[j-1].nextTrack()
	//   
	
	public static int[] calcAuthority(int[] listOfStops) {
		int[] authority = new int[199];
		int l = 0;
		int m = 1;
		if (listOfStops[0] >= 1000 && listOfStops[0] < 2000) {
			authority[0] = 1001;
			//for first stop
			if (listOfStops[0] == 1001) {
				l++;
			} else {	
				while (authority[m-1] != listOfStops[l]) {
					authority[m] = database.getNextTrack(authority[m-1], "forward");
					m++;
				}
				l++;
			}
			//for the next stops
			for (int k = 0; k < listOfStops.length-1; k++) {
				while(authority[m-1] != listOfStops[l]) {
					authority[m] = database.getNextTrack(authority[m-1], "forward");
					m++;
				}
				l++;
			}

		} else if (listOfStops[0] >= 2000 && listOfStops[0] <= 2151) {
			authority[0] = 2001;
			//for first stop
			if (listOfStops[0] == 2001) {
				l++;
			} else {	
				while (authority[m-1] != listOfStops[l]) {
					authority[m] = database.getNextTrack(authority[m-1], "forward");
					m++;
				}
				l++;
			}		
			//for the next stops
			for (int k = 0; k < listOfStops.length-1; k++) {
				while(authority[m-1] != listOfStops[l]) {
					authority[m] = database.getNextTrack(authority[m-1], "forward");
					m++;
				}
				l++;
			}
			
		}
		return authority;
	}
	
	/*Scheduling helpers CTC_getBrokenTrack(int[] brokenTracks) {	
	
		}
	*/
	
	//Functions for setting speed and authority
	//WaysideFunctionsHub.WaysideController_Authority(int[] authority);
	//WaysideFunctionsHub.WaysideController_Speed(int[] speed);
}