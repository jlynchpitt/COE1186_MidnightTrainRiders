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
		JLabel tID = new JLabel(Integer.toString(id));
		add(tID);
		//trainID.add(tID);
		//overall.add(tID);
		
		//trainLine = new JPanel();
		JLabel tLine = new JLabel(line);
		add(tLine);
		//trainLine.add(tLine);
		//overall.add(tLine);
		
		//firstStop = new JPanel();
		JLabel tFirst = new JLabel(first);
		add(tFirst);
		//overall.add(tFirst);
		
		dispatch = new JButton("Dispatch");
		add(dispatch);
		//overall.add(dispatch);
		
		delete = new JButton("Delete");
		add(delete);
		//overall.add(delete);
		
	}
	
}