/* 
 * Filename: TrainSchedulePanel.java
 * Author: Matt Snyder
 * Last Edited: 11/20/17
 * File Description: The sub-panels for the train schedule display.
 */

package MTR.NorthShoreExtension.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.*;
import MTR.NorthShoreExtension.Backend.CTCSrc.*;
import MTR.NorthShoreExtension.Backend.WaysideController.*;
import MTR.NorthShoreExtension.Backend.TrackModelSrc.TrackModel;

public class TrainSchedulePanel extends JPanel {
	//JPanel trainLine;
	//JPanel firstStop;
	JPanel overall;
	//JPanel trainID;
	JButton dispatch;
	JButton delete;
	static DBHelper database = MainMTR.getDBHelper();
	
	TrainSchedule trainSchedule;
	
	public TrainSchedulePanel(TrainSchedule ts) {
		//System.out.println("\n started new schedule panel");
		//System.out.println("\n Line: " + ts.getLine() + " First Stop: " + ts.getFirstStop());
		//object is being created and read correctly, error must be in rendering
		trainSchedule = ts;
		int id = ts.getTrainID();
		String first = ts.getFirstStop();
		String line = ts.getLine();
		
		setBorder(BorderFactory.createTitledBorder("Train: " + id));
		//overall.setLayout(new GridLayout(5,1));
		//trainID = new JPanel();
		JLabel tID = new JLabel("TrainID: " + Integer.toString(id));
		tID.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(tID);
		//trainID.add(tID);
		//overall.add(tID);
		
		//trainLine = new JPanel();
		JLabel tLine = new JLabel("Line: " + line);
		tLine.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(tLine);
		//trainLine.add(tLine);
		//overall.add(tLine);
		
		//firstStop = new JPanel();
		JLabel tFirst = new JLabel("First Stop: " + first);
		tFirst.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(tFirst);
		//overall.add(tFirst);
		
		dispatch = new JButton("Dispatch");
		
		dispatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] authority = trainScheduler.calcAuthority(ts.getListOfStops());
				int speed = database.getSpeedLimit(Integer.parseInt(ts.getFirstStop()));
				int trackID = Integer.parseInt(ts.getFirstStop());
				System.out.println("\n TrackID: " + trackID);
				System.out.println("\n Speed: " + speed);
				for (int i = 0; i < authority.length; i++) {
					System.out.println("\n " + authority[0]);
				}
				WaysideFunctionsHub.OccupiedSpeedAuthority(trackID, speed, authority);
				TrackModel.TrackModel_addTrain(trackID, ts.getTrainID());
				dispatch.setEnabled(false);
			}
		});
		add(dispatch);
		//overall.add(dispatch);
		
		delete = new JButton("Delete");
		add(delete);
		//overall.add(delete);
		
	}
	
}