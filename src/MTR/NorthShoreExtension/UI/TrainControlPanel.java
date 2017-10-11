/*
 * Filename: TrainControlPanel.java
 * Author: Joe Lynch
 * Date Created: 10/9/2017
 * File Description: This contains the main user interface controls for the Train
 * 			Controller gui. One of these panels will be created for each Train
 * 			Controller that is active at that time.
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

package MTR.NorthShoreExtension.UI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.beans.*; //property change stuff
import MTR.NorthShoreExtension.Backend.*;

public class TrainControlPanel extends JPanel
                             implements ActionListener,
                                        ChangeListener,
                                        PropertyChangeListener {
    TrainController trainController;
    NumberFormat numberFormat;
    
    /* Vital Info Components*/
    Label commandedAuthority;
    Label commandedSetSpeed;
    Label actualPower;
    Label actualSpeed;
    Label announcements;
    Label faults;
    
    /* Vital Controls Components */
    JFormattedTextField setSpeed;
    JButton brake;
    JButton eBrake;
    
    /* Track Info Components */
    Label trackID;
    Label trackLength;
    Label trackGrade;
    Label trackSpeedLimit;
    Label trackUnderground;
    Label stoppedAtStation;
    
    /* Non-Vital Controls Components */
    JFormattedTextField setTemp;
    JButton openRDoor;
    JButton openLDoor;
    JButton turnOnLights;

    TrainControlPanel(TrainController tc) {
        
    	//Save arguments in instance variables.
        trainController = tc;
        
        setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Train ID: " + new Integer(trainController.getTrainID()).toString()),
                        BorderFactory.createEmptyBorder(5,5,5,5)));        
        
        /* Top panel for displaying vital info */
        JPanel vitalInfoPanel = new JPanel();
        GridLayout panelLayout = new GridLayout(2,6);
        vitalInfoPanel.setLayout(panelLayout);
        
        //Dynamic text labels
        commandedAuthority = new Label(new Integer(trainController.authority).toString() + " blocks");
        commandedSetSpeed = new Label(new Integer(trainController.ctcCommandedSetSpeed).toString() + " MPH");
        actualPower = new Label(new Double(trainController.commandedPower).toString() + " Watts");
        actualSpeed = new Label(new Double(trainController.actualSpeed).toString() + " MPH");
        announcements = new Label(trainController.announcements);
        faults = new Label(trainController.trainFaults);
        
        //Add all labels to layout
        vitalInfoPanel.add(new JLabel("Authority: "));
        vitalInfoPanel.add(commandedAuthority);
        vitalInfoPanel.add(new JLabel("Power: "));
        vitalInfoPanel.add(actualPower);
        vitalInfoPanel.add(new JLabel("Announcements: "));
        vitalInfoPanel.add(announcements);
        vitalInfoPanel.add(new JLabel("Commanded Set Speed: "));
        vitalInfoPanel.add(commandedSetSpeed);
        vitalInfoPanel.add(new JLabel("Actual Speed: "));
        vitalInfoPanel.add(actualSpeed);
        vitalInfoPanel.add(new JLabel("Train Faults: "));
        vitalInfoPanel.add(faults);       
        
        vitalInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Vital Info"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));

        /* Bottom control panel */
        JPanel bottomControlPanel = new JPanel();
        GridLayout bottomPanelLayout = new GridLayout(1,3);
        bottomControlPanel.setLayout(bottomPanelLayout);
        
        /* Vital Speed control panel */
        JPanel speedControlPanel = new JPanel();
        speedControlPanel.setLayout(new GridBagLayout()); 
        GridBagConstraints c = new GridBagConstraints();
        
        //Add components
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,15,0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        speedControlPanel.add(new JLabel("Set Speed (MPH): "), c);
        
        //Create the set speed field format, and then the text field.
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(0);
        NumberFormatter formatter = new NumberFormatter(numberFormat);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);//seems to be a no-op --
        //aha -- it changes the value property but doesn't cause the result to
        //be parsed (that happens on focus loss/return, I think).
        //
        setSpeed = new JFormattedTextField(formatter);
        setSpeed.setColumns(1);
        setSpeed.setValue(trainController.driverCommandedSetSpeed);
        setSpeed.addPropertyChangeListener(this); //TODO: Handle listeners
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        speedControlPanel.add(setSpeed, c);
        
        brake = new JButton();
        brake.addActionListener(this);
        String buttonText = trainController.brakeApplied ? "Release Brake" : " Apply Brake";
        setControlButtonState(brake, buttonText, trainController.brakeApplied);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(10,0,5,0);
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 1;
    	speedControlPanel.add(brake, c);
    	
    	eBrake = new JButton("Apply Emergency Brake");
    	eBrake.addActionListener(this);
    	buttonText = trainController.eBrakeApplied ? "Release E-Brake" : " Apply E-Brake";
        setControlButtonState(eBrake, buttonText, trainController.eBrakeApplied);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 2;
    	speedControlPanel.add(eBrake, c);
    	
    	speedControlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Speed Controls"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
    	
    	
    	/* Track Info Panel */
    	JPanel trackInfoPanel = new JPanel();
        GridLayout trackInfoPanelLayout = new GridLayout(6,2);
        trackInfoPanel.setLayout(trackInfoPanelLayout);
        
        //Dynamic text labels
        trackID = new Label(trainController.trackID);
        trackLength = new Label(new Integer(trainController.trackLength).toString() + " feet");
        trackGrade = new Label(new Double(trainController.trackGrade).toString() + "%");
        trackSpeedLimit = new Label(new Double(trainController.trackSpeedLimit).toString() + " MPH");
        trackUnderground = new Label(trainController.trackUnderground ? "Yes" : "No");
        stoppedAtStation = new Label(trainController.stoppedAtStation ? "Yes" : "No");
        
        //Add all labels to layout
        trackInfoPanel.add(new JLabel("ID: "));
        trackInfoPanel.add(trackID);
        trackInfoPanel.add(new JLabel("Length: "));
        trackInfoPanel.add(trackLength);
        trackInfoPanel.add(new JLabel("Grade: "));
        trackInfoPanel.add(trackGrade);
        trackInfoPanel.add(new JLabel("Speed Limit: "));
        trackInfoPanel.add(trackSpeedLimit);
        trackInfoPanel.add(new JLabel("Underground: "));
        trackInfoPanel.add(trackUnderground);
        trackInfoPanel.add(new JLabel("Stopped at Station: "));
        trackInfoPanel.add(stoppedAtStation);  
        
    	trackInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Track Info"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
        
        
        /* Non-Vital Train Controls */
        JPanel nonVitalControlPanel = new JPanel();
        nonVitalControlPanel.setLayout(new GridBagLayout()); 
        c = new GridBagConstraints();
        
        //Add components
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,15,0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        nonVitalControlPanel.add(new JLabel("Set Temp (deg F): "), c);
        
        //Create the set speed field format, and then the text field.
        NumberFormat nvNumberFormat = NumberFormat.getNumberInstance();
        nvNumberFormat.setMaximumFractionDigits(0);
        NumberFormatter nvFormatter = new NumberFormatter(nvNumberFormat);
        nvFormatter.setAllowsInvalid(false);
        nvFormatter.setCommitsOnValidEdit(true);//seems to be a no-op --
        //aha -- it changes the value property but doesn't cause the result to
        //be parsed (that happens on focus loss/return, I think).
        //
        setTemp = new JFormattedTextField(nvFormatter);
        setTemp.setColumns(5);
        setTemp.setValue(trainController.setTemp);
        setTemp.addPropertyChangeListener(this); //TODO: Handle listeners
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        nonVitalControlPanel.add(setTemp, c);
        
        openRDoor = new JButton("Open Right Door"); //TODO: Change button text based on train controller
        openRDoor.addActionListener(this);
        buttonText = trainController.rightDoorOpen ? "Close R Door" : " Open R Door";
        setControlButtonState(openRDoor, buttonText, trainController.rightDoorOpen);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(0,0,5,0);
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 1;
    	nonVitalControlPanel.add(openRDoor, c);
    	
    	openLDoor = new JButton("Open Left Door");
    	openLDoor.addActionListener(this);
    	buttonText = trainController.leftDoorOpen ? "Close L Door" : " Open L Door";
        setControlButtonState(openLDoor, buttonText, trainController.leftDoorOpen);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(0,0,10,0);
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 2;
    	nonVitalControlPanel.add(openLDoor, c);
    	
    	turnOnLights = new JButton("Turn On Lights");
    	turnOnLights.addActionListener(this);
    	buttonText = trainController.lightsOn ? "Turn Off Lights" : " Turn On Lights";
        setControlButtonState(turnOnLights, buttonText, trainController.lightsOn);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 3;
    	nonVitalControlPanel.add(turnOnLights, c);
    	
    	nonVitalControlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Non-Vital Controls"),
                BorderFactory.createEmptyBorder(5,5,5,5)));    	
    	
    	//Combine all panels
    	bottomControlPanel.add(speedControlPanel);
    	bottomControlPanel.add(trackInfoPanel);
    	bottomControlPanel.add(nonVitalControlPanel);

    	//GridLayout overallLayout = new GridLayout(2, 1);
        //setLayout(overallLayout);
    	setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 0;
        add(vitalInfoPanel, c);
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
    	
        if(e.getSource() == brake) {
        	if(brake.getBackground() == Color.RED) {
        		//button originally activated
        		newButtonText = "Apply Brake";
        		setButtonActivated = false;
        	}
        	else {
        		
        		newButtonText = "Release Brake";
        		setButtonActivated = true;
        	}
        		
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
        		
        	setControlButtonState(turnOnLights, newButtonText, setButtonActivated);
        }
    }

    /**
     * Detects when the value of the text field (not necessarily the same
     * number as you'd get from getText) changes.
     */
    public void propertyChange(PropertyChangeEvent e) {
        if ("value".equals(e.getPropertyName())) {
            Number value = (Number)e.getNewValue();
            //sliderModel.setDoubleValue(value.doubleValue());
        }
    }
    
    private static void setControlButtonState(JButton button, String newText, boolean activated) {
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
}
