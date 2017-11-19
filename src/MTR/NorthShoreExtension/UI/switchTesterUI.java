/*
* Filename: switchTesterUI.java
* Author: Matt Snyder
* Last Edited: 11/7/2017
* File Description: The Switch Testing UI
* 
*/

package MTR.NorthShoreExtension.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import MTR.NorthShoreExtension.Backend.CTCSrc.switchTester;

public class switchTesterUI extends JFrame {
	private JFrame frame = new JFrame("Switch Tester");
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JLabel redYardSwitch = new JLabel("Red Line Switches: ");
	private JLabel grnYardSwitch = new JLabel("Green Line Switches: ");
	
	public switchTesterUI() throws AWTException {
		render();
	}
	
	private void render() throws AWTException {
		JPanel redPanel = new JPanel();
		JPanel grnPanel = new JPanel();
		redPanel.setLayout(new GridBagLayout());
		grnPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbcRed = new GridBagConstraints();
		GridBagConstraints gbcGrn = new GridBagConstraints();
		
		gbcRed.gridx = 0;
		gbcRed.gridy = 0;
		redPanel.add(redYardSwitch, gbcRed);
		
		JLabel locationSwitch = new JLabel(" Switch Location: ");
		gbcRed.gridx = 1;
		gbcRed.gridy = 0;
		redPanel.add(locationSwitch, gbcRed);
		
		JLabel positionOptionA = new JLabel(" Postioned Track: ");
		gbcRed.gridx = 2;
		gbcRed.gridy = 0;
		redPanel.add(positionOptionA, gbcRed);
		
		JLabel positionOptionB = new JLabel(" Alternate: ");
		gbcRed.gridx = 3;
		gbcRed.gridy = 0;
		redPanel.add(positionOptionB, gbcRed);
		
		JLabel buttonSwitch = new JLabel(" Test Switch ");
		gbcRed.gridx = 4;
		gbcRed.gridy = 0;
		redPanel.add(buttonSwitch, gbcRed);
		////////////
		JLabel redSwitch1 = new JLabel(" Switch: C9");
		gbcRed.gridx = 1;
		gbcRed.gridy = 1;		
		redPanel.add(redSwitch1, gbcRed);
		
		JLabel rSwitch1A = new JLabel("Yard");
		gbcRed.gridx = 2;
		gbcRed.gridy = 1;
		redPanel.add(rSwitch1A, gbcRed);
		
		JLabel rSwitch1B = new JLabel ("D10");
		gbcRed.gridx = 3;
		gbcRed.gridy = 1;
		redPanel.add(rSwitch1B, gbcRed);
		
		JButton rSwitch1 = new JButton("Switch");
		gbcRed.gridx = 4;
		gbcRed.gridy = 1;
		redPanel.add(rSwitch1, gbcRed);
		///////////////////////
		JLabel redSwitch2 = new JLabel(" Switch: E15 ");
		gbcRed.gridx = 1;
		gbcRed.gridy = 2;		
		redPanel.add(redSwitch2, gbcRed);
		
		JLabel rSwitch2A = new JLabel("E14");
		gbcRed.gridx = 2;
		gbcRed.gridy = 2;
		redPanel.add(rSwitch2A, gbcRed);
		
		JLabel rSwitch2B = new JLabel ("A1");
		gbcRed.gridx = 3;
		gbcRed.gridy = 2;
		redPanel.add(rSwitch2B, gbcRed);
		
		JButton rSwitch2 = new JButton("Switch");
		gbcRed.gridx = 4;
		gbcRed.gridy = 2;
		redPanel.add(rSwitch2, gbcRed);
		///////////
		JLabel redSwitch3 = new JLabel(" Switch: H27 ");
		gbcRed.gridx = 1;
		gbcRed.gridy = 3;		
		redPanel.add(redSwitch3, gbcRed);
		
		JLabel rSwitch3A = new JLabel("H28");
		gbcRed.gridx = 2;
		gbcRed.gridy = 3;
		redPanel.add(rSwitch3A, gbcRed);
		
		JLabel rSwitch3B = new JLabel ("T76");
		gbcRed.gridx = 3;
		gbcRed.gridy = 3;
		redPanel.add(rSwitch3B, gbcRed);
		
		JButton rSwitch3 = new JButton("Switch");
		gbcRed.gridx = 4;
		gbcRed.gridy = 3;
		redPanel.add(rSwitch3, gbcRed);
		///////////
		JLabel redSwitch4 = new JLabel(" Switch: H32 ");
		gbcRed.gridx = 1;
		gbcRed.gridy = 4;		
		redPanel.add(redSwitch4, gbcRed);
		
		JLabel rSwitch4A = new JLabel("H31");
		gbcRed.gridx = 2;
		gbcRed.gridy = 4;
		redPanel.add(rSwitch4A, gbcRed);
		
		JLabel rSwitch4B = new JLabel ("R72");
		gbcRed.gridx = 3;
		gbcRed.gridy = 4;
		redPanel.add(rSwitch4B, gbcRed);
		
		JButton rSwitch4 = new JButton("Switch");
		gbcRed.gridx = 4;
		gbcRed.gridy = 4;
		redPanel.add(rSwitch4, gbcRed);
		////////////
		JLabel redSwitch5 = new JLabel(" Switch: H38 ");
		gbcRed.gridx = 1;
		gbcRed.gridy = 5;		
		redPanel.add(redSwitch5, gbcRed);
		
		JLabel rSwitch5A = new JLabel("H39");
		gbcRed.gridx = 2;
		gbcRed.gridy = 5;
		redPanel.add(rSwitch5A, gbcRed);
		
		JLabel rSwitch5B = new JLabel ("Q71");
		gbcRed.gridx = 3;
		gbcRed.gridy = 5;
		redPanel.add(rSwitch5B, gbcRed);
		
		JButton rSwitch5 = new JButton("Switch");
		gbcRed.gridx = 4;
		gbcRed.gridy = 5;
		redPanel.add(rSwitch5, gbcRed);
		////////////
		JLabel redSwitch6 = new JLabel(" Switch: H43 ");
		gbcRed.gridx = 1;
		gbcRed.gridy = 6;		
		redPanel.add(redSwitch6, gbcRed);
		
		JLabel rSwitch6A = new JLabel("H42");
		gbcRed.gridx = 2;
		gbcRed.gridy = 6;
		redPanel.add(rSwitch6A, gbcRed);
		
		JLabel rSwitch6B = new JLabel ("O67");
		gbcRed.gridx = 3;
		gbcRed.gridy = 6;
		redPanel.add(rSwitch6B, gbcRed);
		
		JButton rSwitch6 = new JButton("Switch");
		gbcRed.gridx = 4;
		gbcRed.gridy = 6;
		redPanel.add(rSwitch6, gbcRed);
		///////////
		JLabel redSwitch7 = new JLabel(" Switch: J52 ");
		gbcRed.gridx = 1;
		gbcRed.gridy = 7;		
		redPanel.add(redSwitch7, gbcRed);
		
		JLabel rSwitch7A = new JLabel("J53");
		gbcRed.gridx = 2;
		gbcRed.gridy = 7;
		redPanel.add(rSwitch7A, gbcRed);
		
		JLabel rSwitch7B = new JLabel ("N66");
		gbcRed.gridx = 3;
		gbcRed.gridy = 7;
		redPanel.add(rSwitch7B, gbcRed);
		
		JButton rSwitch7 = new JButton("Switch");
		gbcRed.gridx = 4;
		gbcRed.gridy = 7;
		redPanel.add(rSwitch7, gbcRed);
		
		//Green Switch Tester//
		gbcGrn.gridx = 0;
		gbcGrn.gridy = 0;
		grnPanel.add(grnYardSwitch, gbcGrn);
		
		JLabel locationSwitchGrn = new JLabel(" Switch Location: ");
		gbcGrn.gridx = 1;
		gbcGrn.gridy = 0;
		grnPanel.add(locationSwitchGrn, gbcGrn);
		
		JLabel positionOptionAGrn = new JLabel(" Postioned Track: ");
		gbcGrn.gridx = 2;
		gbcGrn.gridy = 0;
		grnPanel.add(positionOptionAGrn, gbcGrn);
		
		JLabel positionOptionBGrn = new JLabel(" Alternate: ");
		gbcGrn.gridx = 3;
		gbcGrn.gridy = 0;
		grnPanel.add(positionOptionBGrn, gbcGrn);
		
		JLabel buttonSwitchGrn = new JLabel(" Test Switch ");
		gbcGrn.gridx = 4;
		gbcGrn.gridy = 0;
		grnPanel.add(buttonSwitchGrn, gbcGrn);
		/////////
		JLabel grnSwitch1 = new JLabel(" Switch: C12 ");
		gbcGrn.gridx = 1;
		gbcGrn.gridy = 1;		
		grnPanel.add(grnSwitch1, gbcGrn);
		
		JLabel gSwitch1A = new JLabel("A1");
		gbcGrn.gridx = 2;
		gbcGrn.gridy = 1;
		grnPanel.add(gSwitch1A, gbcGrn);
		
		JLabel gSwitch1B = new JLabel ("D13");
		gbcGrn.gridx = 3;
		gbcGrn.gridy = 1;
		grnPanel.add(gSwitch1B, gbcGrn);
		
		JButton gSwitch1 = new JButton("Switch");
		gbcGrn.gridx = 4;
		gbcGrn.gridy = 1;
		grnPanel.add(gSwitch1, gbcGrn);
		/////////
		JLabel grnSwitch2 = new JLabel(" Switch: G29 ");
		gbcGrn.gridx = 1;
		gbcGrn.gridy = 2;		
		grnPanel.add(grnSwitch2, gbcGrn);
		
		JLabel gSwitch2A = new JLabel("G30");
		gbcGrn.gridx = 2;
		gbcGrn.gridy = 2;
		grnPanel.add(gSwitch2A, gbcGrn);
		
		JLabel gSwitch2B = new JLabel ("Z150");
		gbcGrn.gridx = 3;
		gbcGrn.gridy = 2;
		grnPanel.add(gSwitch2B, gbcGrn);
		
		JButton gSwitch2 = new JButton("Switch");
		gbcGrn.gridx = 4;
		gbcGrn.gridy = 2;
		grnPanel.add(gSwitch2, gbcGrn);
		/////////
		JLabel grnSwitch3 = new JLabel(" Switch: J58 ");
		gbcGrn.gridx = 1;
		gbcGrn.gridy = 3;		
		grnPanel.add(grnSwitch3, gbcGrn);
		
		JLabel gSwitch3A = new JLabel("Yard");
		gbcGrn.gridx = 2;
		gbcGrn.gridy = 3;
		grnPanel.add(gSwitch3A, gbcGrn);
		
		JLabel gSwitch3B = new JLabel ("J59");
		gbcGrn.gridx = 3;
		gbcGrn.gridy = 3;
		grnPanel.add(gSwitch3B, gbcGrn);
		
		JButton gSwitch3 = new JButton("Switch");
		gbcGrn.gridx = 4;
		gbcGrn.gridy = 3;
		grnPanel.add(gSwitch3, gbcGrn);
		/////////
		JLabel grnSwitch4 = new JLabel(" Switch: J62 ");
		gbcGrn.gridx = 1;
		gbcGrn.gridy = 4;		
		grnPanel.add(grnSwitch4, gbcGrn);
		
		JLabel gSwitch4A = new JLabel("Yard");
		gbcGrn.gridx = 2;
		gbcGrn.gridy = 4;
		grnPanel.add(gSwitch4A, gbcGrn);
		
		JLabel gSwitch4B = new JLabel ("J61");
		gbcGrn.gridx = 3;
		gbcGrn.gridy = 4;
		grnPanel.add(gSwitch4B, gbcGrn);
		
		JButton gSwitch4 = new JButton("Switch");
		gbcGrn.gridx = 4;
		gbcGrn.gridy = 4;
		grnPanel.add(gSwitch4, gbcGrn);
		/////////
		JLabel grnSwitch5 = new JLabel(" Switch: M76 ");
		gbcGrn.gridx = 1;
		gbcGrn.gridy = 5;		
		grnPanel.add(grnSwitch5, gbcGrn);
		
		JLabel gSwitch5A = new JLabel("M75");
		gbcGrn.gridx = 2;
		gbcGrn.gridy = 5;
		grnPanel.add(gSwitch5A, gbcGrn);
		
		JLabel gSwitch5B = new JLabel ("R101");
		gbcGrn.gridx = 3;
		gbcGrn.gridy = 5;
		grnPanel.add(gSwitch5B, gbcGrn);
		
		JButton gSwitch5 = new JButton("Switch");
		gbcGrn.gridx = 4;
		gbcGrn.gridy = 5;
		grnPanel.add(gSwitch5, gbcGrn);
		/////////
		JLabel grnSwitch6 = new JLabel(" Switch: O86 ");
		gbcGrn.gridx = 1;
		gbcGrn.gridy = 6;		
		grnPanel.add(grnSwitch6, gbcGrn);
		
		JLabel gSwitch6A = new JLabel("O87");
		gbcGrn.gridx = 2;
		gbcGrn.gridy = 6;
		grnPanel.add(gSwitch6A, gbcGrn);
		
		JLabel gSwitch6B = new JLabel ("Q100");
		gbcGrn.gridx = 3;
		gbcGrn.gridy = 6;
		grnPanel.add(gSwitch6B, gbcGrn);
		
		JButton gSwitch6 = new JButton("Switch");
		gbcGrn.gridx = 4;
		gbcGrn.gridy = 6;
		grnPanel.add(gSwitch6, gbcGrn);
		
		/* Add Action Listeners for the various buttons */
		rSwitch1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(1010);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		rSwitch2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(1014);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		rSwitch3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(1027);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		rSwitch4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(1032);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		rSwitch5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(1038);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		rSwitch6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(1043);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		rSwitch7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(1052);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		gSwitch1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(2012);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		gSwitch2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(2029);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		gSwitch3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(2058);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		gSwitch4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(2062);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		gSwitch5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(2076);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		gSwitch6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switchTester.testSwitch(2086);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		/////////
		tabbedPane.add("Red Line",redPanel);
		tabbedPane.add("Green Line",grnPanel);
		
		frame.add(tabbedPane);
		frame.setSize(800, 550);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}}