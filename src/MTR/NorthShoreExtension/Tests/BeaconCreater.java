/*
 * Filename: TrainControlTestBenchUI.java
 * Author: Joe Lynch
 * Date Created: 11/7/2017
 * File Description: This contains the main frame for the Train Controller Test
 * 			Bench GUI. This will be used to comprehensively test the Train 
 * 			Controller separate from the rest of the system
 * 
 * 			NOTE: This file was originally taken from Oracle example code and modified.
 * 				  Oracle's license agreement is included below.
 * 
 * 
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package MTR.NorthShoreExtension.Tests;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.NumberFormatter;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.UnitConverter;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainController;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainControllerHelper;
import MTR.NorthShoreExtension.UI.TrainControlPanel;
import MTR.NorthShoreExtension.UI.TrainControlUI;
import MTR.NorthShoreExtension.UI.TrainEngineerUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class BeaconCreater implements ActionListener, PropertyChangeListener {
	//Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = "System";
    
    private static JFrame frame;
    private static TrainControlTestBenchUI tcUI;
    private JPanel mainPane;
    private JRadioButton station;
    private JRadioButton switchButton;
    private JRadioButton greenLine;
    private JRadioButton redLine;
    private JFormattedTextField currentTrack;
    private JFormattedTextField pNextStationID;
    private JFormattedTextField sNextStationID;
    private JLabel beaconLabel;
        
    public BeaconCreater() {
    	createMainPanel();
    }
    
    protected void createMainPanel() {
        mainPane = new JPanel();
        GridLayout panelLayout = new GridLayout(0,2);
        mainPane.setLayout(panelLayout);
        mainPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        //Create the set speed field format for the text field.
        NumberFormat nvNumberFormat = NumberFormat.getNumberInstance();
        nvNumberFormat.setMaximumFractionDigits(0);
        NumberFormatter nvFormatter = new NumberFormatter(nvNumberFormat);
        nvFormatter.setAllowsInvalid(false);
        nvFormatter.setCommitsOnValidEdit(true);
        
        /* Add beacon/station radio buttons */
        station = new JRadioButton("Station");
        station.addActionListener(this);
        switchButton = new JRadioButton("Switch", true);
        switchButton.addActionListener(this);
        
        ButtonGroup stationSwitchGroup = new ButtonGroup();
        stationSwitchGroup.add(station);
        stationSwitchGroup.add(switchButton);
        
        mainPane.add(station);
        mainPane.add(switchButton);
        
        /* Add line color radio buttons */
        greenLine = new JRadioButton("Green");
        greenLine.addActionListener(this);
        redLine = new JRadioButton("Red", true);
        redLine.addActionListener(this);
        
        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(redLine);
        colorGroup.add(greenLine);
        
        mainPane.add(redLine);
        mainPane.add(greenLine);
        
        currentTrack = new JFormattedTextField(nvFormatter);
        currentTrack.setText("100");
        currentTrack.setColumns(4);
        currentTrack.addPropertyChangeListener(this);
        mainPane.add(new JLabel("current track block number:"));
        mainPane.add(currentTrack);
        
        pNextStationID = new JFormattedTextField(nvFormatter);
        pNextStationID.setText("1");
        pNextStationID.setColumns(4);
        pNextStationID.addPropertyChangeListener(this);
        mainPane.add(new JLabel("Primary Next Station ID:"));
        mainPane.add(pNextStationID);
        
        sNextStationID = new JFormattedTextField(nvFormatter);
        sNextStationID.setText("1");
        sNextStationID.setColumns(4);
        sNextStationID.addPropertyChangeListener(this);
        mainPane.add(new JLabel("Secondary Next Station ID:"));
        mainPane.add(sNextStationID);
        
        mainPane.add(new JLabel(""));
        mainPane.add(new JLabel(""));
        
        beaconLabel = new JLabel();
        beaconLabel.setText("evaluating");
        mainPane.add(new JLabel("Beacon:"));
        mainPane.add(beaconLabel);
        
        evaluateBeaconInfo();
    }
    
	private static void initLookAndFeel() {
        String lookAndFeel = null;

        if (LOOKANDFEEL != null) {
            if (LOOKANDFEEL.equals("Metal")) {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("System")) {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("Motif")) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            } else if (LOOKANDFEEL.equals("GTK+")) { //new in 1.4.2
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            } else {
                System.err.println("Unexpected value of LOOKANDFEEL specified: "
                                   + LOOKANDFEEL);
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }

            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"
                                   + lookAndFeel);
                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
            } catch (UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel ("
                                   + lookAndFeel
                                   + ") on this platform.");
                System.err.println("Using the default look and feel.");
            } catch (Exception e) {
                System.err.println("Couldn't get specified look and feel ("
                                   + lookAndFeel
                                   + "), for some reason.");
                System.err.println("Using the default look and feel.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Set the look and feel.
        initLookAndFeel();

        //Create and set up the window.
        frame = new JFrame("Beacon Creater");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane.
        BeaconCreater bc = new BeaconCreater();
        bc.mainPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(bc.mainPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        evaluateBeaconInfo();
	}
	
	/**
     * Detects when the value of the text field (not necessarily the same
     * number as you'd get from getText) changes.
     */
    public void propertyChange(PropertyChangeEvent e) {
        evaluateBeaconInfo();
    }
    
	private void evaluateBeaconInfo() {
		boolean valid = true;
		int beacon = 0;

		/* Station/switch */
		if(switchButton.isSelected()) {
			beacon = beacon | 0x40000000;
		}
		
		/* Line color*/
		if(greenLine.isSelected()) {
			beacon = beacon | 0x20000000;
		}
		
		/*currentTrack;
	    private JFormattedTextField pNextStationID;
	    private JFormattedTextField sNextStationID;
		/* current track # */
		int currentTrackNum = Integer.parseInt(currentTrack.getText());
		if(currentTrackNum > 0 && ((greenLine.isSelected() && currentTrackNum <= 150) || (redLine.isSelected() && currentTrackNum <= 76))) {
			currentTrackNum = (currentTrackNum & 0xFF) << 21;
			beacon = beacon | currentTrackNum;
		}
		else {
			valid = false;
		}
		
		int primaryNextStationID = Integer.parseInt(pNextStationID.getText());
		if(primaryNextStationID >= 0 && ((greenLine.isSelected() && primaryNextStationID <= 13) || (redLine.isSelected() && primaryNextStationID <= 8))) {
			primaryNextStationID = (primaryNextStationID & 0x0F) << 17;
			beacon = beacon | primaryNextStationID;
		}
		else {
			valid = false;
		}
		
		int secondaryNextStationID = Integer.parseInt(sNextStationID.getText());
		if(secondaryNextStationID >= 0 && ((greenLine.isSelected() && secondaryNextStationID <= 13) || (redLine.isSelected() && secondaryNextStationID <= 8))) {
			secondaryNextStationID = (secondaryNextStationID & 0x0F) << 13;
			beacon = beacon | secondaryNextStationID;
		}
		else {
			valid = false;
		}
		
		if(valid) {
			beaconLabel.setText(Integer.toString(beacon));
		}
		else
		{
			beaconLabel.setText("INVALID");
		}
	}
}