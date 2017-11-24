/* Filename: TrainScheduler.java
*  Author: Matthew Snyder
*  Last Edited: 11/22/17
*  File Description: This is the UI component of the CTC that displays the list of scheduled trains. It will show the user the train ID, which
*  line it runs on, and what its first stop will be. It will give the user the option to dispatch or delete the train. It uses TrainSchedulePanel
*  for each train that is scheduled.
*/
package MTR.NorthShoreExtension.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import MTR.NorthShoreExtension.Backend.CTCSrc.*;
import MTR.NorthShoreExtension.UI.*;

public class TrainSchedulesUI extends JFrame {
	
	private static JFrame frame = null;
	private static TrainSchedulesUI tsui = null;
	private JPanel mainPanel = null;
	
	public TrainSchedulesUI() {
		render();
	}
	
	protected void makeMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		for (TrainSchedule ts : ctcUI.tsh.getTrainScheduleList()) {
			mainPanel.add(new TrainSchedulePanel(ts));
			mainPanel.add(Box.createGlue());
		}
	}
	
	public static void repaintGUI( ) {
		if (tsui != null && frame != null) {
			tsui.makeMainPanel();
			frame.setContentPane(tsui.mainPanel);
			frame.pack();
			frame.revalidate();
			frame.repaint();
		}
	}
	
	private static void render() {
		if (frame == null) {
			frame = new JFrame("Train Schedules");
			
			tsui = new TrainSchedulesUI();
			tsui.makeMainPanel();
			tsui.mainPanel.setOpaque(true);
			JScrollPane scroll = new JScrollPane(tsui.mainPanel);
			frame.setContentPane(scroll);
		}
		
		frame.setSize(800,400);
		//frame.pack();
		frame.setVisible(true);
	}
	
	
}

