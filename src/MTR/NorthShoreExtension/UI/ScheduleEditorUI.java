/* ScheduleEditorUI.java
 * Author: Matt Snyder
 * Last Edited: 12/13/17
 * File Purpose: This is the UI for adjusting the schedule of a train after it has dispatched from the yard. 
 * It essentially just subsitutes the new authority chain for the current one. It shouldn't be available in 
 * automatic mode (?).
 * 
 */

package MTR.NorthShoreExtension.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import MTR.NorthShoreExtension.Backend.CTCSrc.*;
import MTR.NorthShoreExtension.UI.*;

public class ScheduleEditorUI {
	private static ScheduleEditorUI seui;
	private static JFrame frame = new JFrame("Schedule Editor");
	JTextField updateSchedInput;
	JTextField updateSpeed;
	JButton update;
	JPanel mainPanel;
	
	TrainSchedule ts;
	
	
	
	public ScheduleEditorUI(TrainSchedule t) {
		ts = t;
		makeMainPanel();
	}
	
	public void makeMainPanel() {
		mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5,5,5,5);
		
		JLabel schedNew = new JLabel("New Schedule: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(schedNew, gbc);
		
		updateSchedInput = new JTextField(40);
		gbc.gridx = 1;
		gbc.gridy = 0;
		mainPanel.add(updateSchedInput, gbc);
		
		JLabel speedNew = new JLabel("New Speed: ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(speedNew, gbc);
		
		updateSpeed = new JTextField(3);
		gbc.gridx = 1;
		gbc.gridy = 1;
		mainPanel.add(updateSpeed, gbc);
		
		update  = new JButton("Update Schedule");
		update.setEnabled(true);
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 2;
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] updatedStops = updateSchedInput.getText().toString().split(",");
				int[] nextStops = new int[updatedStops.length];
				for (int x = 0; x < updatedStops.length; x++) {
					nextStops[x] = Integer.parseInt(updatedStops[x]);
					update.setEnabled(false);
				}
				ts.updateAuthority(nextStops);
			}
		});
		
		mainPanel.add(update, gbc);
		
	}
	
	public static void repaintGUI() {
		if (seui != null && frame != null) {
			seui.makeMainPanel();
			frame.setContentPane(seui.mainPanel);
			frame.pack();
			frame.revalidate();
			frame.repaint();
		}
	}
	
	public static void createAndShowGUI(TrainSchedule t) {
		if (frame != null) {
			System.out.println("Editing Schedule");
			frame = new JFrame("Schedule Editor");
			
			seui = new ScheduleEditorUI(t);
			seui.mainPanel.setOpaque(true);
			frame.setContentPane(seui.mainPanel);
		} else {
			repaintGUI();
		}
		frame.pack();
		frame.setVisible(true);
	}
}
