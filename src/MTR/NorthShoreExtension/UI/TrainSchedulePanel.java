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

import MTR.NorthShoreExtension.Backend.CTCSrc.*;

public class TrainSchedulePanel extends JPanel {
	//JPanel trainLine;
	//JPanel firstStop;
	JPanel overall;
	//JPanel trainID;
	JButton dispatch;
	JButton delete;
	
	TrainSchedule trainSchedule;
	
	public TrainSchedulePanel(TrainSchedule ts) {
		overall = new JPanel();
		trainSchedule = ts;
		int id = ts.getTrainID();
		String first = ts.getFirstStop();
		String line = ts.getLine();
		
		overall.setLayout(new GridLayout(5,1));
		//trainID = new JPanel();
		JLabel tID = new JLabel(Integer.toString(id));
		//trainID.add(tID);
		overall.add(tID);
		
		//trainLine = new JPanel();
		JLabel tLine = new JLabel(line);
		//trainLine.add(tLine);
		overall.add(tLine);
		
		//firstStop = new JPanel();
		JLabel tFirst = new JLabel(first);
		overall.add(tFirst);
		
		dispatch = new JButton("Dispatch");
		overall.add(dispatch);
		
		delete = new JButton("Delete");
		overall.add(delete);
		
	}
	
}