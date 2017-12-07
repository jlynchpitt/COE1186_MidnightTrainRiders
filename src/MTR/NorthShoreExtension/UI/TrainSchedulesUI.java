/* Filename: TrainScheduler.java
*  Author: Matthew Snyder
*  Last Edited: 12/06/17
*  File Description: This is the UI component of the CTC that displays the list of scheduled trains. It will show the user the train ID, which
*  line it runs on, and what its first stop will be. It will give the user the option to dispatch or delete the train. It uses TrainSchedulePanel
*  for each train that is scheduled.
*/
package MTR.NorthShoreExtension.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import MTR.NorthShoreExtension.Backend.CTCSrc.*;
import MTR.NorthShoreExtension.UI.TrainSchedulePanel;
import MTR.NorthShoreExtension.UI.*;

public class TrainSchedulesUI extends JFrame {
	
	private static JFrame frame = null;
	private static TrainSchedulesUI tsui = null;
	private JPanel mainPanel = null;
	public trainScheduler ts;
	public static TrainScheduleHelper tsh;
	JButton button;
	
	public TrainSchedulesUI() {
		makeMainPanel();
	}
	
	protected void makeMainPanel() {
		System.out.println("Entering makeMainPanel");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		for (TrainSchedule ts : ctcUI.tsh.getTrainScheduleList()) {
			System.out.println("\n creating trainSchedulePanel");
			System.out.println("\n First Stop: " + ts.getFirstStop() + "\n");
			mainPanel.add(new TrainSchedulePanel(ts));
			mainPanel.add(Box.createGlue());
		}
		System.out.println("exiting makeMainPanel");
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
	
	public static void createAndShowGUI(TrainScheduleHelper t) {
		tsh = t;
		if (frame == null) {
			System.out.println("creating schedulesUI");
			frame = new JFrame("Train Schedules");
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	
			tsui = new TrainSchedulesUI();
			//tsui.makeMainPanel();
			tsui.mainPanel.setOpaque(true);
			JScrollPane scroll = new JScrollPane(tsui.mainPanel); //scroll if gets large
			frame.setContentPane(scroll);
		} else {
			repaintGUI();
		}
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
}

