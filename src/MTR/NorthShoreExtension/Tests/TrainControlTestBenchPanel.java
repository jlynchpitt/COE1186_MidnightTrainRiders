/*
 * Filename: TrainControlTestBenchPanel.java
 * Author: Joe Lynch
 * Date Created: 11/11/2017
 * File Description: This contains the main user interface controls for the Train
 * 			Controller Test Bench gui. One of these panels will be created for each 
 * 			Train Controller that is active at that time.
 * 
 * 			NOTE: This file was originally taken from Oracle example code and modified.
 * 				  Oracle's license agreement is included below.Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.*;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.beans.*; //property change stuff
import MTR.NorthShoreExtension.Backend.*;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainController;

public class TrainControlTestBenchPanel extends JPanel
                             implements ActionListener,
                                        ChangeListener,
                                        PropertyChangeListener {    
	TrainController trainController;
    NumberFormat numberFormat;
    
    /* Panels */
    JPanel failureButtonPanel;
    JPanel speedAuthorityPanel;
    JPanel brakePanel;
    JPanel trainStatusPanel;
    
    /* Speed Authority Components*/
    JFormattedTextField commandedSpeed;
    JFormattedTextField commandedAuthority;
    JLabel powerCommand;
    JLabel actualSpeed;
    
    /* Failure Button Components */
    JButton engineFailureButton;
    JButton signalPickupFailureButton;
    JButton brakeFailureButton;
    
    /* Brake Panel Components */
    JLabel brakeStatus;
    JLabel eBrakeStatus;
    JButton moveToNextTrack;
    JButton passEBrake;
    
    /* Train Status Components */
    JLabel rightDoor;
    JLabel leftDoor;
    JLabel lights;
    JLabel announcements;

    public TrainControlTestBenchPanel(TrainController tc) {
        
    	//Save arguments in instance variables.
        trainController = tc;
        trainController.setTrainControlTestBenchPanel(this);
        
        setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Train ID: " + new Integer(trainController.getTrainID()).toString()),
                        BorderFactory.createEmptyBorder(5,5,5,5)));        
        
        createFailureButtonPanel(); //Top panel

        /* Bottom control panel */
        JPanel bottomControlPanel = new JPanel();
        GridLayout bottomPanelLayout = new GridLayout(1,3);
        bottomControlPanel.setLayout(bottomPanelLayout);
        
        createBrakePanel();
    	
    	createSpeedAuthorityPanel();
    	
    	createTrainStatusPanel();
    	
    	//Combine all panels
    	bottomControlPanel.add(brakePanel);
    	bottomControlPanel.add(speedAuthorityPanel);
    	bottomControlPanel.add(trainStatusPanel);

    	//GridLayout overallLayout = new GridLayout(2, 1);
        //setLayout(overallLayout);
    	setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 0;
        add(failureButtonPanel, c);
        c.gridy = 1;
    	add(bottomControlPanel, c);
    }

    //Don't allow this panel to get taller than its preferred size.
    //BoxLayout pays attention to maximum size, though most layout
    //managers don't.
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE,
                             getPreferredSize().height);
    }

    /**
     * Returns the multiplier (units/meter) for the currently
     * selected unit of measurement.
     */
    public double getMultiplier() {
        return 0.25; //sliderModel.getMultiplier();
    }

    public double getValue() {
        return 0.35; //sliderModel.getDoubleValue();
    }

    /** Updates the text field when the main data model is updated. */
    public void stateChanged(ChangeEvent e) {
        int min = 0; //sliderModel.getMinimum();
        int max = 250; //sliderModel.getMaximum();
        double value = 7.25; //sliderModel.getDoubleValue();
        //NumberFormatter formatter = (NumberFormatter)textField.getFormatter();

        //formatter.setMinimum(new Double(min));
        //formatter.setMaximum(new Double(max));
        //textField.setValue(new Double(value));
    }

    /**
     * Responds to the user choosing a new unit from the combo box.
     */
    public void actionPerformed(ActionEvent e) {
    	String newButtonText = "";
    	boolean setButtonActivated = false;
    	
        /*if(e.getSource() == brake) {
        	if(brake.getBackground() == Color.RED) {
        		//button originally activated
        		newButtonText = "Apply Brake";
        		setButtonActivated = false;
        	}
        	else {
        		
        		newButtonText = "Release Brake";
        		setButtonActivated = true;
        	}
        	
        	trainController.operateBrake(setButtonActivated);
        		
        	setControlButtonState(brake, newButtonText, setButtonActivated);
        }
        else if(e.getSource() == eBrake) {
        	if(eBrake.getBackground() == Color.RED) {
        		//button originally activated
        		newButtonText = "Apply E-Brake";
        		setButtonActivated = false;
        	}
        	else {
        		
        		newButtonText = "Release E-Brake";
        		setButtonActivated = true;
        	}
        	
        	trainController.operateEmergencyBrake(setButtonActivated);
        		
        	setControlButtonState(eBrake, newButtonText, setButtonActivated);
        }
        else if(e.getSource() == openRDoor) {
        	if(openRDoor.getBackground() == Color.RED) {
        		//button originally activated
        		newButtonText = "Open R Door";
        		setButtonActivated = false;
        	}
        	else {
        		
        		newButtonText = "Close R Door";
        		setButtonActivated = true;
        	}
        		
        	trainController.operateRightDoor(setButtonActivated);

        	setControlButtonState(openRDoor, newButtonText, setButtonActivated);
        }
        else if(e.getSource() == openLDoor) {
        	if(openLDoor.getBackground() == Color.RED) {
        		//button originally activated
        		newButtonText = "Open L Door";
        		setButtonActivated = false;
        	}
        	else {
        		
        		newButtonText = "Close L Door";
        		setButtonActivated = true;
        	}
        		
        	trainController.operateLeftDoor(setButtonActivated);

        	setControlButtonState(openLDoor, newButtonText, setButtonActivated);
        }
        else if(e.getSource() == turnOnLights) {
        	if(turnOnLights.getBackground() == Color.RED) {
        		//button originally activated
        		newButtonText = "Turn On Lights";
        		setButtonActivated = false;
        	}
        	else {
        		
        		newButtonText = "Turn Off Lights";
        		setButtonActivated = true;
        	}
        		
        	trainController.operateLights(setButtonActivated);

        	setControlButtonState(turnOnLights, newButtonText, setButtonActivated);
        }
        else if(e.getSource() == trainController) {
        	//backend info updated - update it in the UI
        	/*switch(e.getActionCommand()) {
        	case VITAL:
        		updateUIIntSpeed(trainSetSpeed, trainController.getTrainSetSpeed());
        		updateUIPower(actualPower, trainController.getPower());
        		updateUIDoubleSpeed(actualSpeed, trainController.getActualSpeed());

        		//commanded authority
        		updateUIAuthority(commandedAuthority, trainController.getAuthority());
        		updateUIIntSpeed(commandedSetSpeed, trainController.getCTCCommandedSetSpeed());
        		break;
        	case TRACK_INFO:
        		break;
        	case TEMPERATURE:
        		internalTemp.setText(Double.toString(trainController.getInternalTemp()));
        		break;
        	case DOORS:
        		boolean rOpen = trainController.isRightDoorOpen();
        		String rButtonText = rOpen == true ? "Close R Door" : "Open R Door";
        		setControlButtonState(openRDoor, rButtonText, rOpen);

        		boolean lOpen = trainController.isLeftDoorOpen();
        		String lButtonText = lOpen == true ? "Close L Door" : "Open L Door";
        		setControlButtonState(openLDoor, lButtonText, lOpen);
        		break;
        	case LIGHTS:
        		boolean lightsOn = trainController.areLightsOn();
        		String lightText = lightsOn == true ? "Turn Off Lights" : "Turn On Lights";
        		setControlButtonState(openLDoor, lightText, lightsOn);
        		break;
        	case BRAKES:
        		boolean brakeApplied = trainController.isBrakeApplied();
        		String brakeText = brakeApplied == true ? "Release Brake" : "Apply Brake";
        		setControlButtonState(brake, brakeText, brakeApplied);

        		boolean eBrakeApplied = trainController.isEBrakeApplied();
        		String eBrakeText = eBrakeApplied == true ? "Release E-Brake" : "Apply E-Brake";
        		setControlButtonState(eBrake, eBrakeText, eBrakeApplied);
        		break;
        	case ANNOUNCEMENT:
        		announcements.setText(trainController.getAnnouncements());
        		break;
        	case FAULT:
        		faults.setText(trainController.getTrainFaults());
        		break;
        	}*/
        //}
    }

    /**
     * Detects when the value of the text field (not necessarily the same
     * number as you'd get from getText) changes.
     */
    public void propertyChange(PropertyChangeEvent e) {
        /*if ("value".equals(e.getPropertyName())) {
            Number value = (Number)e.getNewValue();
            //sliderModel.setDoubleValue(value.doubleValue());
        }*/
/*    	if(e.getSource().equals(driverSetSpeed)) {
    		//TODO: Handle speeds > 1000 - issue with , in 1,000
    		int newSetSpeed = Integer.parseInt(((JFormattedTextField)e.getSource()).getText());
    		trainController.setDriverCommandedSetSpeed(newSetSpeed);
    	}*/
    }
    
    private void setControlButtonState(JButton button, String newText, boolean activated) {
    	button.setText(newText);
    	
    	//If button activated -> Color = red
    	if(activated) {
    		button.setContentAreaFilled(false);
    		button.setBackground(Color.RED);
    		button.setOpaque(true);
    	}
    	else {
    		button.setContentAreaFilled(true);
    		button.setBackground(null);
    		button.setOpaque(true);
    	}
    }
    
    /*
     * Adds units onto a value before being displayed in the UI
     * Used for any label that displays a double based speed
     */
    private void updateUIDoubleSpeed(JLabel label, double speed) {
    	if(label != null) {
    		label.setText(String.format("%.2f MPH", speed));
    	}
    }
    
    /*
     * Adds units onto a value before being displayed in the UI
     * Used for any label that displays an integer based speed
     */
    private void updateUIIntSpeed(JLabel label, double speed) {
    	if(label != null) {
    		label.setText(String.format("%.0f MPH", speed));
    	}
    }
    
    /*
     * Adds units onto a value before being displayed in the UI
     * Used for any label that displays authority
     */
    private void updateUIAuthority(JLabel label, double auth) {
    	if(label != null) {
    		label.setText(String.format("%.0f blocks", auth));
    	}
    }
    
    /*
     * Adds units onto a value before being displayed in the UI
     * Used for any label that displays power
     */
    private void updateUIPower(JLabel label, double power) {
    	if(label != null) {
    		label.setText(String.format("%.2f horsepower", power));
    	}
    }
    
    /* Functions for making JPanels */
    private void createSpeedAuthorityPanel() {
    	/* Track Info Panel */
    	speedAuthorityPanel = new JPanel();
        GridLayout speedAuthorityPanelLayout = new GridLayout(5,2);
        speedAuthorityPanel.setLayout(speedAuthorityPanelLayout);
        
        //Create number formatter
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(0);
        NumberFormatter formatter = new NumberFormatter(numberFormat);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);//seems to be a no-op --
        //aha -- it changes the value property but doesn't cause the result to
        //be parsed (that happens on focus loss/return, I think).
        //
        
        //Dynamic text labels + text fields
        commandedSpeed = new JFormattedTextField(formatter);
        commandedSpeed.setText("0");
        commandedAuthority = new JFormattedTextField(formatter);
        commandedAuthority.setText("0");
        
        powerCommand = new JLabel();
        updateUIPower(powerCommand, 0); //TODO: Should this get the current power command from the Train Controller?
        actualSpeed = new JLabel();
        updateUIDoubleSpeed(actualSpeed, trainController.getActualSpeed());
        
        //Add all labels to layout
        speedAuthorityPanel.add(new JLabel("Speed (MPH): "));
        speedAuthorityPanel.add(commandedSpeed);
        speedAuthorityPanel.add(new JLabel("Authority (blocks): "));
        speedAuthorityPanel.add(commandedAuthority);
        speedAuthorityPanel.add(new JLabel(""));
        speedAuthorityPanel.add(new JLabel(""));
        speedAuthorityPanel.add(new JLabel("Power Command: "));
        speedAuthorityPanel.add(powerCommand);
        speedAuthorityPanel.add(new JLabel("Actual Speed: "));
        speedAuthorityPanel.add(actualSpeed);
       
    	speedAuthorityPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Vital Info"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
        
    }
    
    private void createFailureButtonPanel() {
    	/* Top panel for displaying non-vital info */
        failureButtonPanel = new JPanel();
        GridLayout panelLayout = new GridLayout(0,3);
        failureButtonPanel.setLayout(panelLayout);
        
        //Create buttons
        engineFailureButton = new JButton();
        engineFailureButton.addActionListener(this);
        setControlButtonState(engineFailureButton, "Fail Engine", false);
        
        signalPickupFailureButton = new JButton();
        signalPickupFailureButton.addActionListener(this);
        setControlButtonState(signalPickupFailureButton, "Fail Signal Pickup", false);

        brakeFailureButton = new JButton();
        brakeFailureButton.addActionListener(this);
        setControlButtonState(brakeFailureButton, "Fail brakes", false);
        
        //Add all buttons to layout
        failureButtonPanel.add(engineFailureButton);
        failureButtonPanel.add(signalPickupFailureButton);       
        failureButtonPanel.add(brakeFailureButton);       
        
        failureButtonPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Failure Controls"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
    }

    private void createBrakePanel() {
    	/* Vital Speed control panel */
        brakePanel = new JPanel();
        brakePanel.setLayout(new GridBagLayout()); 
        GridBagConstraints c = new GridBagConstraints();
        
        //Add components
        moveToNextTrack = new JButton();
        moveToNextTrack.addActionListener(this);
        setControlButtonState(moveToNextTrack, "Move To Next Track", false);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(10,0,5,0);
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 0;
    	brakePanel.add(moveToNextTrack, c);
    	
    	passEBrake = new JButton("Pull Passenger Emergency Brake");
    	passEBrake.addActionListener(this);
        setControlButtonState(passEBrake, "Pull Passenger Emergency Brake", false);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 1;
    	brakePanel.add(passEBrake, c);
        
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 2;
    	brakePanel.add(new JLabel("Standard Brake Status: "), c);
    	
    	brakeStatus = new JLabel();
    	boolean brakeApplied = trainController.isBrakeApplied();
    	String brakeStatusText = brakeApplied == true ? "Applied" : "Released";
    	brakeStatus.setText(brakeStatusText);
    	c.gridx = 1;
    	c.gridy = 2;
    	brakePanel.add(brakeStatus, c);
    	
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 3;
    	brakePanel.add(new JLabel("Emergency Brake Status: "), c);
    	
    	eBrakeStatus = new JLabel();
    	boolean eBrakeApplied = trainController.isEBrakeApplied();
    	String eBrakeStatusText = eBrakeApplied == true ? "Applied" : "Released";
    	eBrakeStatus.setText(eBrakeStatusText);
    	c.gridx = 1;
    	c.gridy = 3;
    	brakePanel.add(eBrakeStatus, c);
    	
    	brakePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Brake Info"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
    }
    
    private void createTrainStatusPanel() {
        /* Track Status Panel */
    	trainStatusPanel = new JPanel();
        GridLayout trainStatusPanelLayout = new GridLayout(4,2);
        trainStatusPanel.setLayout(trainStatusPanelLayout);
        
        //Dynamic text labels        
        rightDoor = new JLabel(trainController.isRightDoorOpen() ? "Open" : "Closed");
        leftDoor = new JLabel(trainController.isLeftDoorOpen() ? "Open" : "Closed");
        lights = new JLabel(trainController.areLightsOn() ? "On" : "Off");
        announcements = new JLabel(trainController.getAnnouncements());
        
        //Add all labels to layout
        trainStatusPanel.add(new JLabel("Right Door: "));
        trainStatusPanel.add(rightDoor);
        trainStatusPanel.add(new JLabel("Left Door: "));
        trainStatusPanel.add(leftDoor);
        trainStatusPanel.add(new JLabel("Lights: "));
        trainStatusPanel.add(lights);
        trainStatusPanel.add(new JLabel("Announcments: "));
        trainStatusPanel.add(announcements);
        
    	trainStatusPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Vital Info"),
                BorderFactory.createEmptyBorder(5,5,5,5)));    
    }
}
