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
import java.awt.font.TextAttribute;
import java.util.*;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.beans.*; //property change stuff
import MTR.NorthShoreExtension.Backend.*;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainController;

public class TrainControlPanel extends JPanel
                             implements ActionListener,
                                        ChangeListener,
                                        PropertyChangeListener {
	//Action Performed commands from backend
	public static final String VITAL = "vitalInfo";
	public static final String TRACK_INFO = "trackInfo";
	public static final String TEMPERATURE = "temperature";
	public static final String DOORS = "doors";
	public static final String LIGHTS = "lights";
	public static final String BRAKES = "brakes";
	public static final String ANNOUNCEMENT = "announcement";
	public static final String FAULT = "trainFault";
    
	TrainController trainController;
    NumberFormat numberFormat;
    
    /* Panels */
    JPanel nonVitalInfoPanel;
    JPanel vitalInfoPanel;
    JPanel speedControlPanel;
    JPanel nonVitalControlPanel;
    
    /* Vital Info Components*/
    JLabel commandedAuthority;
    JLabel commandedSetSpeed;
    JLabel trainSetSpeed;
    JLabel actualPower;
    JLabel actualSpeed;
    
    /* Non Vital Info Components */
    JLabel announcements;
    JLabel faults;
    JLabel internalTemp;
    
    /* Vital Controls Components */
    JFormattedTextField driverSetSpeed;
    JButton brake;
    JButton eBrake;
    
    /* Non-Vital Controls Components */
    JFormattedTextField setTemp;
    JButton openRDoor;
    JButton openLDoor;
    JButton turnOnLights;

    public TrainControlPanel(TrainController tc) {
        
    	//Save arguments in instance variables.
        trainController = tc;
        trainController.setTrainControlPanel(this);
        
        setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Train ID: " + new Integer(trainController.getTrainID()).toString()),
                        BorderFactory.createEmptyBorder(5,5,5,5)));        
        
        createNonVitalInfoPanel(); //Top panel

        /* Bottom control panel */
        JPanel bottomControlPanel = new JPanel();
        GridLayout bottomPanelLayout = new GridLayout(1,3);
        bottomControlPanel.setLayout(bottomPanelLayout);
        
        createVitalControlsPanel();
    	
    	createVitalInfoPanel();
    	
    	createNonVitalControlsPanel();
    	
    	//Combine all panels
    	bottomControlPanel.add(speedControlPanel);
    	bottomControlPanel.add(vitalInfoPanel);
    	bottomControlPanel.add(nonVitalControlPanel);

    	//GridLayout overallLayout = new GridLayout(2, 1);
        //setLayout(overallLayout);
    	setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 0;
        add(nonVitalInfoPanel, c);
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
        	switch(e.getActionCommand()) {
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
        	}
        }
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
    	if(e.getSource().equals(driverSetSpeed)) {
    		//TODO: Handle speeds > 1000 - issue with , in 1,000
    		int newSetSpeed = Integer.parseInt(((JFormattedTextField)e.getSource()).getText());
    		trainController.setDriverCommandedSetSpeed(newSetSpeed);
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
    
    private JLabel newTitleLabel(String lableText) {
    	JLabel jl = new JLabel(lableText);
    	
    	//Map<TextAttribute, Object> attributes = new HashMap<>();

    	//attributes.put(TextAttribute.FAMILY, Font.DIALOG);
    	//attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD);
    	//attributes.put(TextAttribute.SIZE, 12);

    	//jl.setFont(Font.getFont(attributes));
    	//jl.setFont(new Font("Serif", Font.BOLD, 11));
    	return jl;
    }
    
    /* Functions for making JPanels */
    private void createVitalInfoPanel() {
    	/* Track Info Panel */
    	vitalInfoPanel = new JPanel();
        GridLayout vitalInfoPanelLayout = new GridLayout(6,2);
        vitalInfoPanel.setLayout(vitalInfoPanelLayout);
        
        //Dynamic text labels        
        commandedAuthority = new JLabel(new Integer(trainController.getAuthority()).toString() + " blocks");
        commandedSetSpeed = new JLabel(new Integer(trainController.getCTCCommandedSetSpeed()).toString() + " MPH");
        trainSetSpeed = new JLabel(new Integer(trainController.getTrainSetSpeed()).toString() + " MPH");
        actualPower = new JLabel(new Double(trainController.getPower()).toString() + " horsepower");
        actualSpeed = new JLabel(new Double(trainController.getActualSpeed()).toString() + " MPH");
        
        
        //Add all labels to layout
        vitalInfoPanel.add(new JLabel("Train Set Speed: "));
        vitalInfoPanel.add(trainSetSpeed);
        vitalInfoPanel.add(new JLabel("Power: "));
        vitalInfoPanel.add(actualPower);
        vitalInfoPanel.add(new JLabel("Actual Speed: "));
        vitalInfoPanel.add(actualSpeed);
        vitalInfoPanel.add(new JLabel(""));
        vitalInfoPanel.add(new JLabel(""));
        vitalInfoPanel.add(new JLabel("Commanded Authority: "));
        vitalInfoPanel.add(commandedAuthority);
        vitalInfoPanel.add(new JLabel("Commanded Set Speed: "));
        vitalInfoPanel.add(commandedSetSpeed);
        
    	vitalInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Vital Info"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
        
    }
    
    private void createNonVitalInfoPanel() {
    	/* Top panel for displaying non-vital info */
        nonVitalInfoPanel = new JPanel();
        GridLayout panelLayout = new GridLayout(0,6);
        nonVitalInfoPanel.setLayout(panelLayout);
        
        //Dynamic text labels
        announcements = new JLabel(trainController.getAnnouncements());
        faults = new JLabel(trainController.getTrainFaults());
        internalTemp = new JLabel(new Double(trainController.getInternalTemp()).toString() + " deg F");
        
        //Add all labels to layout
        nonVitalInfoPanel.add(new JLabel("Announcements: "));
        nonVitalInfoPanel.add(announcements);
        nonVitalInfoPanel.add(new JLabel("Train Faults: "));
        nonVitalInfoPanel.add(faults);       
        nonVitalInfoPanel.add(new JLabel("Inside Temp: "));
        nonVitalInfoPanel.add(internalTemp);       
        
        nonVitalInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Non-Vital Info"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
    }

    private void createVitalControlsPanel() {
    	/* Vital Speed control panel */
        speedControlPanel = new JPanel();
        speedControlPanel.setLayout(new GridBagLayout()); 
        GridBagConstraints c = new GridBagConstraints();
        
        //Add components
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,15,0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        speedControlPanel.add(new JLabel("Driver Set Speed (MPH): "), c);
        
        //Create the set speed field format, and then the text field.
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(0);
        NumberFormatter formatter = new NumberFormatter(numberFormat);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);//seems to be a no-op --
        //aha -- it changes the value property but doesn't cause the result to
        //be parsed (that happens on focus loss/return, I think).
        //
        driverSetSpeed = new JFormattedTextField(formatter);
        driverSetSpeed.setColumns(1);
        driverSetSpeed.setValue(trainController.getDriverCommandedSetSpeed());
        driverSetSpeed.addPropertyChangeListener(this); //TODO: Handle listeners
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        speedControlPanel.add(driverSetSpeed, c);
        
        brake = new JButton();
        brake.addActionListener(this);
        boolean brakeApplied = trainController.isBrakeApplied();
        String buttonText = brakeApplied ? "Release Brake" : " Apply Brake";
        setControlButtonState(brake, buttonText, brakeApplied);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(10,0,5,0);
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 1;
    	speedControlPanel.add(brake, c);
    	
    	eBrake = new JButton("Apply Emergency Brake");
    	eBrake.addActionListener(this);
    	boolean eBrakeApplied = trainController.isEBrakeApplied();
    	buttonText = eBrakeApplied ? "Release E-Brake" : " Apply E-Brake";
        setControlButtonState(eBrake, buttonText, eBrakeApplied);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 2;
    	speedControlPanel.add(eBrake, c);
    	
    	speedControlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Speed Controls"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
    }
    
    private void createNonVitalControlsPanel() {
    	/* Non-Vital Train Controls */
        nonVitalControlPanel = new JPanel();
        nonVitalControlPanel.setLayout(new GridBagLayout()); 
        GridBagConstraints c = new GridBagConstraints();
        
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
        setTemp.setValue(trainController.getInternalTemp());
        setTemp.addPropertyChangeListener(this); //TODO: Handle listeners
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        nonVitalControlPanel.add(setTemp, c);
        
        openRDoor = new JButton("Open Right Door"); //TODO: Change button text based on train controller
        openRDoor.addActionListener(this);
        boolean rightDoorOpen = trainController.isRightDoorOpen();
        String buttonText = rightDoorOpen ? "Close R Door" : " Open R Door";
        setControlButtonState(openRDoor, buttonText, rightDoorOpen);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(0,0,5,0);
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 1;
    	nonVitalControlPanel.add(openRDoor, c);
    	
    	openLDoor = new JButton("Open Left Door");
    	openLDoor.addActionListener(this);
    	boolean leftDoorOpen = trainController.isLeftDoorOpen();
    	buttonText = leftDoorOpen ? "Close L Door" : " Open L Door";
        setControlButtonState(openLDoor, buttonText, leftDoorOpen);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(0,0,10,0);
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 2;
    	nonVitalControlPanel.add(openLDoor, c);
    	
    	turnOnLights = new JButton("Turn On Lights");
    	turnOnLights.addActionListener(this);
    	boolean lightsOn = trainController.areLightsOn();
    	buttonText = lightsOn ? "Turn Off Lights" : " Turn On Lights";
        setControlButtonState(turnOnLights, buttonText, lightsOn);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 3;
    	nonVitalControlPanel.add(turnOnLights, c);
    	
    	nonVitalControlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Non-Vital Controls"),
                BorderFactory.createEmptyBorder(5,5,5,5)));    	
    }
}
