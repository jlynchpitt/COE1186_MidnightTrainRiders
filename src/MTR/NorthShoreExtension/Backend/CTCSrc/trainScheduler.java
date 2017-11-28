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
		int firstSection = 0;
		int prevSection = 1;
		int lastSection = 0;
		if (listOfStops[0] >= 1000 && listOfStops[0] < 2000) {
			/*firstStop = 1001; //will need to hard-code for red and green lines i guess...
			System.out.println("M: " + m);
			System.out.println("L: " + l);
			System.out.println("Authority[0]: " + authority[0]);
			System.out.println("ListofStops[0]: " + listOfStops[0]);
			System.out.println("ListOfStops[1]: " + listOfStops[1]);
			System.out.println("ListOfStops[2]: " + listOfStops[2]);
			System.out.println("ListOfStops[3]: " + listOfStops[3]);
			//for first stop
			if (listOfStops[0] == firstStop) {
				System.out.println("\n First stop is the first section out of the yard");
				authority[0] = firstStop;
				l++;
			} else if (listOfStops[0] == (firstStop + 1)) {
				System.out.println("\n First stop is the second section out of the yard");
				authority[0] = firstStop;
				authority[1] = firstStop + 1;
				m = 2;
				l++;
			} else {
				System.out.println("\n first stop is not the first or second section out of the yard");
				m = 2;
				authority[0] = firstStop;
				authority[1] = firstStop + 1;
				System.out.println("M: " + m);
				do {
					System.out.println("prior: " + authority[m-1] + " " + authority[m-2]);
					authority[m] = database.getNextTrack(authority[m-1], authority[m-2]);
					m++;
				} while (listOfStops[0] != authority[m-1]);
				l++;
			}
			System.out.println("M: " + m);
			System.out.println("Authority[m-1] : " + authority[m-1]);
			//for next stops
			while (listOfStops[l] > 1000) {
				System.out.println("Next Stop: " + listOfStops[l]);
				System.out.println("M: " + m);
				System.out.println("L: " + l);
				do {
					 
					
					//System.out.println("\n NextTrack: " + authority[m]
					//next track section to stop
					m++;
					System.out.println("ListOfStops[l]: " + listOfStops[l] + " authority[m-1] = " + authority[m]);
				} while (listOfStops[l] != authority[m-1]);//listOfStops[l] != authority[m-1]);
				//Next Stop
				l++;
			}*/
		} else if (listOfStops[0] >= 2000 && listOfStops[0] < 2151) {
			firstSection = 2062;
			prevSection = 2061;
			lastSection = 2058;
			authority[0] = firstSection; //replace with first section from yard
			authority[1] = database.schedNextTrack(firstSection, prevSection);
			//database.showTrackTest();
			System.out.println("Auth: " + authority[0] + " " + authority[1]);
			System.out.println("L: " + l);
			System.out.println("M: " + m);
			if (listOfStops[0] == firstSection) {
				l++;
			} else if (listOfStops[0] == authority[1]) {
				System.out.println(authority[m]);
				m++;
				l++;
			} else {
				while (listOfStops[l] != authority[m]) {
					m++;
					if (m == 1) {
						authority[m] = database.schedNextTrack(firstSection, (firstSection-1));
						System.out.println("" + m + ": " + authority[m]);
					} else {
						authority[m] = database.schedNextTrack(authority[m-1], authority[m-2]);
						System.out.println("" + m + ": " + authority[m]);
					}
				}
				l++;
			}
			System.out.println("L: " + l);
			System.out.println("M: " + m);
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