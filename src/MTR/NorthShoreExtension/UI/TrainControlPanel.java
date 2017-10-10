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
    JFormattedTextField setSpeed;
    TrainController trainController;
    NumberFormat numberFormat;

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
        Label commandedAuthority = new Label(new Integer(tc.authority).toString() + " blocks");
        Label commandedSetSpeed = new Label(new Integer(tc.ctcCommandedSetSpeed).toString() + " MPH");
        Label actualPower = new Label(new Double(tc.commandedPower).toString() + " Watts");
        Label actualSpeed = new Label(new Double(tc.actualSpeed).toString() + " MPH");
        Label announcements = new Label(tc.announcements);
        Label faults = new Label(tc.trainFaults);
        
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
        setSpeed.setValue(0);
        setSpeed.addPropertyChangeListener(this); //TODO: Handle listeners
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        speedControlPanel.add(setSpeed, c);
        
        JButton brake = new JButton("Apply Standard Brake"); //TODO: Change button text based on train controller
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(10,0,5,0);
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 1;
    	speedControlPanel.add(brake, c);
    	
    	JButton eBrake = new JButton("Apply Emergency Brake");
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
        Label trackID = new Label(trainController.trackID);
        Label trackLength = new Label(new Integer(trainController.trackLength).toString() + " feet");
        Label trackGrade = new Label(new Double(trainController.trackGrade).toString() + "%");
        Label trackSpeedLimit = new Label(new Double(trainController.trackSpeedLimit).toString() + " MPH");
        Label trackUnderground = new Label(trainController.trackUnderground ? "Yes" : "No");
        Label stoppedAtStation = new Label(trainController.stoppedAtStation ? "Yes" : "No");
        
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
        JFormattedTextField setTemp = new JFormattedTextField(nvFormatter);
        setTemp.setColumns(5);
        setTemp.setValue(trainController.setTemp);
        setTemp.addPropertyChangeListener(this); //TODO: Handle listeners
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        nonVitalControlPanel.add(setTemp, c);
        
        JButton openRDoor = new JButton("Open Right Door"); //TODO: Change button text based on train controller
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(0,0,5,0);
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 1;
    	nonVitalControlPanel.add(openRDoor, c);
    	
    	JButton openLDoor = new JButton("Open Left Door");
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(0,0,10,0);
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 2;
    	nonVitalControlPanel.add(openLDoor, c);
    	
    	JButton turnOnLights = new JButton("Turn On Lights");
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
        //Combo box event. Set new maximums for the sliders.
        //int i = unitChooser.getSelectedIndex();
        //sliderModel.setMultiplier(units[i].multiplier);
        //controller.resetMaxValues(false);
        System.out.print("action");
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
}
