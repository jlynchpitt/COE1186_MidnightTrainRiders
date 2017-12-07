/* 
 * Filename: TrainSchedulePanel.java
 * Author: Matt Snyder
 * Last Edited: 12/06/17
 * File Description: The sub-panels for the train schedule display.
 */

package MTR.NorthShoreExtension.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.UI.ctcUI; 
import MTR.NorthShoreExtension.Backend.*;
import MTR.NorthShoreExtension.Backend.CTCSrc.*;
import MTR.NorthShoreExtension.Backend.WaysideController.*;
import MTR.NorthShoreExtension.Backend.TrackModelSrc.TrackModel;

public class TrainSchedulePanel extends JPanel {
	JPanel overall;
	JButton dispatch;
	JButton delete;
	static DBHelper database = MainMTR.getDBHelper();
	
	TrainSchedule trainSchedule;
	
	public TrainSchedulePanel(TrainSchedule ts) {

		trainSchedule = ts;
		int id = trainSchedule.getTrainID();
		String first = trainSchedule.getFirstStop();
		String line = trainSchedule.getLine();
		int spot = trainSchedule.getCurrentLocation();
		
		setBorder(BorderFactory.createTitledBorder("Train: " + id));

		JLabel tID = new JLabel("TrainID: " + Integer.toString(id));
		tID.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(tID);

		JLabel tLine = new JLabel("Line: " + line);
		tLine.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(tLine);
		
		JLabel tSpot = new JLabel("Current Location: " + spot);
		tSpot.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(tSpot);

		JLabel tFirst = new JLabel("First Stop: " + first);
		tFirst.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(tFirst);
		
		dispatch = new JButton("Dispatch");
		
		dispatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Integer> authority = trainScheduler.calcAuthority(ts.getListOfStops());
				int[] auth = new int[authority.size()];
				for (int i = 0; i < auth.length; i++) {
					auth[i] = authority.get(i);
				}
				int speed = database.getSpeedLimit(Integer.parseInt(ts.getFirstStop()));
				//int trackID = Integer.parseInt(ts.getFirstStop());
				int trackID = authority.get(0);
				System.out.println("\n TrackID: " + trackID);
				System.out.println("\n Speed: " + speed);
				//System.out.println("\n Authority: ");
				/*for (int i = 0; i<4; i++) {
					System.out.println("\n " + authority[i]);
				}*/
				dispatch.setEnabled(false);
				System.out.println("Adding Train");
				TrackModel.TrackModel_addTrain(trackID, ts.getTrainID());
				System.out.println("Train Added");
				//System.out.println("Sending speed and authority");
				//WaysideFunctionsHub.OccupiedSpeedAuthority(trackID, speed, auth);
				//System.out.println("Speed and authority sent!");
				
			}
		});
		add(dispatch);
		
		delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dispatch.isEnabled()) {
					dispatch.setEnabled(false);
				} else {
					dispatch.setEnabled(true);
				}
			}
		});
		add(delete);		
	}
	
}