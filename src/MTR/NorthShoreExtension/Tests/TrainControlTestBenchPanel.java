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
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainControllerHelper;

public class TrainControlTestBenchPanel extends JPanel
                             implements ActionListener,
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
    JLabel powerCommandLabel;
    JLabel actualSpeedLabel;
    
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
    
    /* Internal status/state variables */
    private double powerCommand = 0;
    private double actualSpeed = 0;
    private boolean brakeApplied = false;
    private boolean eBrakeApplied = false;

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

    /** Train model commands  */
    public void TrainModel_setPower(double powerCmd) {
    	powerCommand = powerCmd;
    	
    	updateUIPower(powerCommandLabel, powerCommand);
    	
    	//Simulate train model's speed calculation
    	actualSpeed = calculateBasicSpeed();
    	updateUIDoubleSpeed(actualSpeedLabel, actualSpeed);
    	
    	trainController.TrainControl_setActualSpeed(actualSpeed);
    }
    
    public void TrainModel_setBrake(boolean applied) {
    	brakeApplied = applied;
    	
    	brakeStatus.setText(applied == true ? "Applied" : "Released");
    }
    
    public void TrainModel_setEBrake(boolean applied) {
    	eBrakeApplied = applied;
    	
    	eBrakeStatus.setText(applied == true ? "Applied" : "Released");
    }
    
    public void TrainModel_openRightDoor(boolean open) {
    	rightDoor.setText(open == true ? "Open" : "Closed");
    }
    
    public void TrainModel_openLeftDoor(boolean open) {
    	leftDoor.setText(open == true ? "Open" : "Closed");
    }
    
    public void TrainModel_turnLightsOn(boolean lightsOn) {
    	if(lightsOn) {
    		lights.setText("On");
    	}
    	else {
    		lights.setText("Off");
    	}
    }

    /**
     * Responds to the user choosing a new unit from the combo box.
     */
    public void actionPerformed(ActionEvent e) {
    	String newButtonText = "";
    	boolean setButtonActivated = false;
    	
        if(e.getSource() == engineFailureButton) {
        	if(engineFailureButton.getBackground() == Color.RED) {
        		//button originally activated
        		newButtonText = "Fail Engine";
        		setButtonActivated = false;
        	}
        	else {
        		
        		newButtonText = "Repair Engine";
        		setButtonActivated = true;
        	}
        	
        	trainController.TrainControl_setFaultStatus(TrainControllerHelper.ENGINE_FAILURE, setButtonActivated);
        		
        	setControlButtonState(engineFailureButton, newButtonText, setButtonActivated);
        }
        else if(e.getSource() == signalPickupFailureButton) {
        	if(signalPickupFailureButton.getBackground() == Color.RED) {
        		//button originally activated
        		newButtonText = "Fail Signal Pickup";
        		setButtonActivated = false;
        	}
        	else {
        		
        		newButtonText = "Repair Signal Pickup";
        		setButtonActivated = true;
        	}
        	
        	trainController.TrainControl_setFaultStatus(TrainControllerHelper.SIGNAL_PICKUP_FAILURE, setButtonActivated);
        		
        	setControlButtonState(signalPickupFailureButton, newButtonText, setButtonActivated);
        }
        else if(e.getSource() == brakeFailureButton) {
        	if(brakeFailureButton.getBackground() == Color.RED) {
        		//button originally activated
        		newButtonText = "Fail Brakes";
        		setButtonActivated = false;
        	}
        	else {
        		
        		newButtonText = "Repair Brakes";
        		setButtonActivated = true;
        	}
        	
        	trainController.TrainControl_setFaultStatus(TrainControllerHelper.BRAKE_FAILURE, setButtonActivated);
        		
        	setControlButtonState(brakeFailureButton, newButtonText, setButtonActivated);
        }
        else if(e.getSource() == moveToNextTrack) {
        	trainController.TrainControl_moveToNextTrack();
        }
        else if(e.getSource() == passEBrake) {        		
        	trainController.TrainControl_setPassengerEBrake();

        	setControlButtonState(passEBrake, "Pull Passenger Emergency Brake", true);
        }
    }

    /**
     * Detects when the value of the text field (not necessarily the same
     * number as you'd get from getText) changes.
     */
    public void propertyChange(PropertyChangeEvent e) {
    	if(e.getSource().equals(commandedSpeed) || e.getSource().equals(commandedAuthority)) {
    		//TODO: Handle speeds > 1000 - issue with , in 1,000
    		int newSetSpeed = Integer.parseInt(commandedSpeed.getText());
    		int newAuthority = Integer.parseInt(commandedAuthority.getText());
    		trainController.TrainControl_setCommandedSpeedAuthority(newSetSpeed, newAuthority);
    	}
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
    
    private double calculateBasicSpeed() {
    	double speed = 0;
    	double powerSpeed = 0;
    	boolean brake = true;
    	
		//for testing purposes when not attached to train model
		//simple velocity calculation for demonstration of adjusting power command
		
		//Power (W) = Force (kg * m/s2) * Velocity (m/s)
		//TrainForce = mass * acceleration = 51.43 tons * 0.5 m/s2 (train 2/3 loaded) = 46656.511 kg * 0.5 m/s2
		//Velocity = Power/Train Force			
		//NOTE: 1.34102 converts horsepower back to kWatts
    	if(powerCommand > 0) {
			double msSpeed = (powerCommand * 1000 / 1.34102)/((46656.511/9.8) * 0.5);
			powerSpeed = msSpeed * 2.23694;
			
			if(powerSpeed > speed)
			{
				speed = powerSpeed;
				brake = false;
			}
    	}
    	
    	if(brake) {
    		//not accelerating - decrease speed to simulate breaking
    		double frictionBrakeRate = 0.5;
    		int standardBrakeRate = 1;
    		int emergencyBrakeRate = 2;
    		
    		if(eBrakeApplied) {
    			speed = actualSpeed - emergencyBrakeRate;
    		}
    		else if(brakeApplied) {
    			speed = actualSpeed - standardBrakeRate;
    		}
    		else {
    			//brake due to friction
    			speed = actualSpeed - frictionBrakeRate;
    		}
    	}
		
		if (speed < 0) {
			speed = 0;
		}

		return speed;
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
        commandedSpeed.addPropertyChangeListener(this); //TODO: Handle listeners

        commandedAuthority = new JFormattedTextField(formatter);
        commandedAuthority.setText("0");
        commandedAuthority.addPropertyChangeListener(this); //TODO: Handle listeners
        
        powerCommandLabel = new JLabel();
        updateUIPower(powerCommandLabel, 0); //TODO: Should this get the current power command from the Train Controller?
        actualSpeedLabel = new JLabel();
        updateUIDoubleSpeed(actualSpeedLabel, trainController.getActualSpeed());
        
        //Add all labels to layout
        speedAuthorityPanel.add(new JLabel("Speed (MPH): "));
        speedAuthorityPanel.add(commandedSpeed);
        speedAuthorityPanel.add(new JLabel("Authority (blocks): "));
        speedAuthorityPanel.add(commandedAuthority);
        speedAuthorityPanel.add(new JLabel(""));
        speedAuthorityPanel.add(new JLabel(""));
        speedAuthorityPanel.add(new JLabel("Power Command: "));
        speedAuthorityPanel.add(powerCommandLabel);
        speedAuthorityPanel.add(new JLabel("Actual Speed: "));
        speedAuthorityPanel.add(actualSpeedLabel);
       
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
        setControlButtonState(brakeFailureButton, "Fail Brakes", false);
        
        //Add all buttons to layout
        failureButtonPanel.add(engineFailureButton);
        failureButtonPanel.add(brakeFailureButton);       
        failureButtonPanel.add(signalPickupFailureButton);       
        
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
        trainStatusPanel.add(new JLabel("Announcements: "));
        trainStatusPanel.add(announcements);
        
    	trainStatusPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Vital Info"),
                BorderFactory.createEmptyBorder(5,5,5,5)));    
    }
}
