/*
 * Filename: TrainEngineerControlsUI.java
 * Author: Joe Lynch
 * Date Created: 10/16/2017
 * File Description: This contains the main frame for the Train Engineer Controls 
 * 			The Engineer Controls allow the train engineer to set the kp and ki 
 * 			parameters for trains that have not yet left the yard.
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

package MTR.NorthShoreExtension.UI;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.NumberFormatter;

import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainController;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainControllerHelper;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.*;

public class TrainEngineerControlsUI extends JPanel implements PropertyChangeListener {
	//Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = "System";
    
    private JPanel mainPane;
    private JFormattedTextField kp, ki;
    public static TrainControllerHelper trainControllerHelper;
    
    public TrainEngineerControlsUI(TrainControllerHelper tch) {
        trainControllerHelper = tch;
        
        //Create the set speed field format, and then the text field.
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        NumberFormatter formatter = new NumberFormatter(numberFormat);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);//seems to be a no-op --
        //aha -- it changes the value property but doesn't cause the result to
        //be parsed (that happens on focus loss/return, I think).
    
        mainPane = new JPanel();
        mainPane.setLayout(new GridBagLayout());
        mainPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        mainPane.add(Box.createRigidArea(new Dimension(0, 5)));
        
        GridBagConstraints c = new GridBagConstraints();
        
        //Add components
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,15,0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        mainPane.add(new JLabel("PID kp: "), c);
        
        c.gridx = 1;
        kp = new JFormattedTextField(formatter);
        kp.setColumns(1);
        kp.setValue(trainControllerHelper.getPIDParameter_p());
        kp.addPropertyChangeListener(this);
        mainPane.add(kp, c);
        
        c.gridx = 0;
        c.gridy = 1;
        mainPane.add(new JLabel("PID ki: "), c);
        
        c.gridx = 1;
        ki = new JFormattedTextField(formatter);
        ki.setColumns(1);
        ki.setValue(trainControllerHelper.getPIDParameter_i());
        ki.addPropertyChangeListener(this);
        mainPane.add(ki, c);
        
        c.gridx = 0;
        c.gridy = 2;
    	c.gridwidth = 2;
    	mainPane.add(new JLabel("Note these parameters will take effect on trains that have not left the yard"), c);
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
    private static void createAndShowGUI(TrainControllerHelper tch) {
        //Set the look and feel.
        initLookAndFeel();

        //Create and set up the window.
        JFrame frame = new JFrame("Train Engineer Controls");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane.
        TrainEngineerControlsUI tecUI = new TrainEngineerControlsUI(tch);
        tecUI.mainPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(tecUI.mainPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Detects when the value of the text field (not necessarily the same
     * number as you'd get from getText) changes.
     */
    public void propertyChange(PropertyChangeEvent e) {
    	if(e.getSource().equals(kp) || e.getSource().equals(ki)) {
    		//TODO: Handle speeds > 1000 - issue with , in 1,000
    		double newKp = Double.parseDouble((kp).getText());
    		double newKi = Double.parseDouble((ki).getText());
    		trainControllerHelper.setPIDParameters(newKp, newKi);
    	}
    }

    public static void main(String[] args) {
    	//Create TrainControllerHelper - with sample test data to show different UI states
    	TrainControllerHelper tch = new TrainControllerHelper();
    	
    	
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(tch);
            }
        });
    }


}
