/* Filename: repairScheduler.java 
 * Author: Matt Snyder
 * Last Edited: 11/14/2017
 * File Description: The back-end operations of the Repair Scheduler
 */
 
package MTR.NorthShoreExtension.Backend.CTCSrc;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import MTR.NorthShoreExtension.Backend.DBHelper;

public class repairScheduler {
	static DBHelper database;
	
	public int checkStatus(int trackID) {
		int state = 2;
		//fetch the status from the database
		String status = database.getTrackStatus(trackID);
		
		//if the track is in good working condition return 1, otherwise return 0
		if (status.equals("Operational")) {
			state = 1;
		} else {
			state = 0;
		}
		return state;
	}
	
	//a function for updating the track status in the database and replying with a notification of it's successfulness
	public int openRepairJob(int trackID, String repairType) {
		int actionComplete = 0;
		database.updateTrackStatus(trackID, repairType);
		int updated = checkStatus(trackID);
		if (updated == 1) {
			actionComplete = 0;
		} else if (updated == 0) {
			actionComplete = 1;
		}
		return actionComplete;
	}
}
