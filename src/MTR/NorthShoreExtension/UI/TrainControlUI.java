/*
 * Filename: TrainControlUI.java
 * Author: Joe Lynch
 * Date Created: 10/9/2017
 * File Description: This contains the main frame for the Train Controller GUI 
 * 			as well as a main function to run the Train Controller as an individual 
 * 			sub-module. The main function sets up several sample Train Controllers 
 * 			for demonstration purposes.
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

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.StaticTrackDBHelper;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainController;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainControllerHelper;

import java.awt.*;
import java.awt.event.*;

//For testing new graph
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class TrainControlUI implements ActionListener {
	//Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = "System";
    
    private static JFrame frame = null;
    private static TrainControlUI tcUI = null;
    private JPanel mainPane;
    private JButton graphButton;
    private JComboBox trainIDComboBox;
    private StaticTrackDBHelper db = null;
    public static TrainControllerHelper tch;
    
    public TrainControlUI() {
    	createMainPanel();
    }
    
    protected void createMainPanel() {
    	db = MainMTR.getStaticTrackDBHelper();
    	
    	System.out.println("creating main panel");
    	
        mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        mainPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        mainPane.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        List<Integer> trainIDList = new ArrayList<>();
        trainIDList.add(2);
        if(db != null) {
        	trainIDList = db.getTrainIDList();
        }

        trainIDComboBox = new JComboBox(trainIDList.toArray());
        if(trainIDComboBox.getItemCount() > 0) {
        	trainIDComboBox.setSelectedIndex(0);
        }
        //trainIDComboBox.addActionListener(this);
        trainIDComboBox.setSize(300, trainIDComboBox.getPreferredSize().height);
        buttonPanel.add(new JLabel("Train ID: "));
        buttonPanel.add(trainIDComboBox);
        
        graphButton = new JButton("Open Train Graph");
        graphButton.addActionListener(this);
        buttonPanel.add(graphButton);
        mainPane.add(buttonPanel);
        
        for(TrainController tc : tch.getTrainControllerList()) {
	        mainPane.add(new TrainControlPanel(tc));
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
	
	public static void reloadGUI() {
		if(frame != null && tcUI != null) {
			tcUI.createMainPanel();
			frame.setContentPane(tcUI.mainPane);
			frame.pack();
			frame.revalidate();
			frame.repaint();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        if(e.getSource() == graphButton) {
        	SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                   GraphPanel2.createAndShowGui(MainMTR.getStaticTrackDBHelper(), 123);
                }
             });
        }	
	}

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowTrainControlGUI(TrainControllerHelper t) {
    	if(t == null) {
    		tch = MainMTR.getTrainControllerHelper();
    	}
    	else {
    		tch = t;
    	}

        //Set the look and feel.
        initLookAndFeel();

        //Create and set up the window.
        if(frame == null) {
	        frame = new JFrame("Train Controllers");
	        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	        
	        //Create and set up the content pane.
	        tcUI = new TrainControlUI();
	        tcUI.mainPane.setOpaque(true); //content panes must be opaque
	        JScrollPane jsp = new JScrollPane(tcUI.mainPane);
	        frame.setContentPane(jsp);
        }
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	//Create TrainControllerHelper - with sample test data to show different UI states
    	TrainControllerHelper tcHelper = new TrainControllerHelper();
    	TrainController tc123 = tcHelper.addNewTrainController(123, "Green", null); 
    	tc123.TrainControl_setCommandedSpeedAuthority(65, 5);
    	/*TrainController tc456 = tcHelper.addNewTrain(456);
    	tc456.brakeApplied = false;
    	tc456.eBrakeApplied = false;
    	tc456.leftDoorOpen = true;
    	tc456.rightDoorOpen = true;
    	tc456.lightsOn = true;*/
    	
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowTrainControlGUI(tcHelper);
            }
        });
    }
    
    //TODO: Remove this for testing only
    private static XYDataset createDataset() {

        DefaultXYDataset ds = new DefaultXYDataset();

        double[][] data = { {0.1, 0.2, 0.3}, {1, 2, 3} };

        ds.addSeries("series1", data);

        return ds;
    }
}