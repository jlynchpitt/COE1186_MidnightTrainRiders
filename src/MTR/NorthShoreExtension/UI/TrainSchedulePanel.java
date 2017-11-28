/* 
 * Filename: TrainSchedulePanel.java
 * Author: Matt Snyder
 * Last Edited: 11/24/17
 * File Description: The sub-panels for the train schedule display.
 */

package MTR.NorthShoreExtension.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.UI.ctcUI; //just for testing time changing function of ctc
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
		int id = ts.getTrainID();
		String first = ts.getFirstStop();
		String line = ts.getLine();
		
		setBorder(BorderFactory.createTitledBorder("Train: " + id));

		JLabel tID = new JLabel("TrainID: " + Integer.toString(id));
		tID.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(tID);

		JLabel tLine = new JLabel("Line: " + line);
		tLine.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(tLine);

		JLabel tFirst = new JLabel("First Stop: " + first);
		tFirst.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(tFirst);
		
		dispatch = new JButton("Dispatch");
		
		dispatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] authority = trainScheduler.calcAuthority(ts.getListOfStops());
				int speed = database.getSpeedLimit(Integer.parseInt(ts.getFirstStop()));
				int trackID = Integer.parseInt(ts.getFirstStop());
				System.out.println("\n TrackID: " + trackID);
				System.out.println("\n Speed: " + speed);
				//System.out.println("\n Authority: ");
				/*for (int i = 0; i<4; i++) {
					System.out.println("\n " + authority[i]);
				}*/
				TrackModel.TrackModel_addTrain(trackID, ts.getTrainID());
				System.out.println("Adding Train");
				WaysideFunctionsHub.OccupiedSpeedAuthority(trackID, speed, authority);
				System.out.println("Sending speed and authority");
				dispatch.setEnabled(false);
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