/* Filename: OtherTestingUI.java
* Author: Matt Snyder
*  Last Edited: 12/14/17
*  File Purpose: This file creates the interface for editing enviornment variable for testing the CTC such as ambient temperature and throughput
*/

package MTR.NorthShoreExtension.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import MTR.NorthShoreExtension.UI.*;

public class OtherTestingUI {
	
	private static JFrame frame = null;
	private static OtherTestingUI otui = null;
	private JPanel mainPanel = null;
	
	private JLabel ambientTemp = new JLabel("New Temperature (F): ");
	private JLabel throughput = new JLabel("New Throughput (per hour): ");
	private JTextField tempInput = new JTextField(3);
	private JTextField throughInput = new JTextField(5);
	private JButton changeTemp = new JButton("Update Temperature");
	private JButton changeThrough = new JButton("Update Throughput");
	
	public OtherTestingUI() {
		makeMainPanel();
	}
	
	protected void makeMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(ambientTemp, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		mainPanel.add(tempInput, gbc);
		
		changeTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Setting Temp: " + tempInput.getText());
				ctcUI.setTemp(Integer.parseInt(tempInput.getText()));
			}
		});
		gbc.gridx = 2;
		gbc.gridy = 0;
		mainPanel.add(changeTemp, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(throughput, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		mainPanel.add(throughInput, gbc);
		
		changeThrough.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Setting Throughput: " + throughInput.getText());
				ctcUI.setThroughput(Integer.parseInt(throughInput.getText()));
			}
		});
		gbc.gridx = 2;
		gbc.gridy = 1;
		mainPanel.add(changeThrough, gbc);
	}
	
	public static void createAndShowGUI() {
		if (frame == null) {
			frame = new JFrame("Other Testing");
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			
			otui = new OtherTestingUI();
			otui.mainPanel.setOpaque(true);
			
			frame.setContentPane(otui.mainPanel);
		}
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public void repaintGUI() {
		if (otui != null && frame != null) {
			otui.makeMainPanel();
			frame.setContentPane(otui.mainPanel);
			frame.pack();
			frame.revalidate();
			frame.repaint();
		}
	}
	
	
}