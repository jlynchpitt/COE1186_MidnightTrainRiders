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

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.NumberFormatter;

import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainController;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainControllerHelper;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.*;

public class TrainControlTestBenchUI {
	//Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = "System";
    
    private JPanel mainPane;
    private JButton dispatchTrainButton;
    private JFormattedTextField trainIDTextField;
    private static TrainControllerHelper tch;
    
    public TrainControlTestBenchUI() {
        mainPane = new JPanel();
        mainPane.setLayout(new GridBagLayout()); 
        mainPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        mainPane.add(Box.createRigidArea(new Dimension(0, 5)));
        
        //Create the set speed field format for the text field.
        NumberFormat nvNumberFormat = NumberFormat.getNumberInstance();
        nvNumberFormat.setMaximumFractionDigits(0);
        NumberFormatter nvFormatter = new NumberFormatter(nvNumberFormat);
        nvFormatter.setAllowsInvalid(false);
        nvFormatter.setCommitsOnValidEdit(false);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(0,0,0,50);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        
        dispatchTrainButton = new JButton("Dispatch a Train");
        mainPane.add(dispatchTrainButton, c);
        
        c.gridx = 1;
        mainPane.add(new JLabel("New Train ID: "));
        
        c.gridx = 2;
        c.insets = new Insets(0,0,0,0);
        trainIDTextField = new JFormattedTextField(nvFormatter);
        trainIDTextField.setText("123");
        mainPane.add(trainIDTextField, c);
        
        for(TrainController tc : tch.getTrainControllerList()) {
	        mainPane.add(new TrainControlTestBenchPanel(tc));
	        mainPane.add(Box.createGlue());
        }
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
    private static void createAndShowTrainControlGUI() {
        //Set the look and feel.
        initLookAndFeel();

        //Create and set up the window.
        JFrame frame = new JFrame("Train Controllers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane.
        TrainControlTestBenchUI tcUI = new TrainControlTestBenchUI();
        tcUI.mainPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(tcUI.mainPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	//Create TrainControllerHelper - with sample test data to show different UI states
    	tch = new TrainControllerHelper();
    	
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowTrainControlGUI();
            }
        });
    }
}