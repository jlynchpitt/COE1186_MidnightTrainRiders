/* Filename: repairScheduler.java 
 * Author: Matt Snyder
 * Last Edited: 12/06/2017
 * File Description: The back-end operations of the Repair Scheduler
 */
 
package MTR.NorthShoreExtension.Backend.CTCSrc;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;

public class repairScheduler {
	static DBHelper database = MainMTR.getDBHelper();
	
	//a function for updating the track status in the database. returns 1 on successful operation, 0 on failure
	public static int openRepairJob(int trackID, String repairType) {
		int actionComplete = 0;
		String resulted = database.getTrackStatus(trackID);
		System.out.println(resulted);
		database.updateTrackStatus(trackID, repairType);
		String nexResulted = database.getTrackStatus(trackID);
		System.out.println(nexResulted);
		if (nexResulted.equals(repairType)) {
			actionComplete = 1;
		}
		return actionComplete;
	}
	
	public static int closeRepairJob(int trackID, String repairType) {
		int actionComplete = 0;
		String resulted = database.getTrackStatus(trackID);
		System.out.println(resulted);
		database.updateTrackStatus(trackID, "No Issues");
		String nexResulted = database.getTrackStatus(trackID);
		System.out.println(nexResulted);
		if (nexResulted.equals("No Issues")) {
			actionComplete = 1;
		}
		return actionComplete;
	}
}
