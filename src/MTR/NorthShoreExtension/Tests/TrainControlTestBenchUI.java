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
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainController;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainControllerHelper;
import MTR.NorthShoreExtension.UI.TrainControlPanel;
import MTR.NorthShoreExtension.UI.TrainControlUI;
import MTR.NorthShoreExtension.UI.TrainEngineerUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TrainControlTestBenchUI implements ActionListener {
	//Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = "System";
    
    private static JFrame frame;
    private static TrainControlTestBenchUI tcUI;
    private JPanel mainPane;
    private JPanel buttonPane;
    private JPanel controllerPane;
    private JPanel imagePane;
    private JLabel picLabel = null;
    private JButton dispatchTrainButton;
    private JButton switchButton;
    private JButton trainEngineerUIButton;
    private JButton trainControlsUIButton;
    private JFormattedTextField trainIDTextField;
    private static TrainControllerHelper tch;
    
    private int switchPosition = 1;
    
    public TrainControlTestBenchUI() {
    	createMainPanel();
    }
    
    protected void createMainPanel() {
        buttonPane = new JPanel();
		//buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(15,10,25,5));
        buttonPane.add(Box.createRigidArea(new Dimension(0, 5)));
        
        //Create the set speed field format for the text field.
        NumberFormat nvNumberFormat = NumberFormat.getNumberInstance();
        nvNumberFormat.setMaximumFractionDigits(0);
        NumberFormatter nvFormatter = new NumberFormatter(nvNumberFormat);
        nvFormatter.setAllowsInvalid(false);
        nvFormatter.setCommitsOnValidEdit(false);
        
        dispatchTrainButton = new JButton("Dispatch a Train");
        dispatchTrainButton.addActionListener(this);
        buttonPane.add(dispatchTrainButton);
        
        buttonPane.add(Box.createRigidArea(new Dimension(25, 0)));

        buttonPane.add(new JLabel("New Train ID: "));
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        
        trainIDTextField = new JFormattedTextField(nvFormatter);
        trainIDTextField.setText("123");
        //trainIDTextField.setMaximumSize(new Dimension(400, 100));
        trainIDTextField.setColumns(8);
        buttonPane.add(trainIDTextField);
        buttonPane.add(Box.createRigidArea(new Dimension(100, 0)));
        
        switchButton = new JButton("Operate Switch");
        switchButton.addActionListener(this);
        buttonPane.add(switchButton);
        
        buttonPane.add(Box.createRigidArea(new Dimension(100, 0)));
        
        trainEngineerUIButton = new JButton("Train Engineer UI");
        trainEngineerUIButton.addActionListener(this);
        buttonPane.add(trainEngineerUIButton);
        
        trainControlsUIButton = new JButton("Train Controls UI");
        trainControlsUIButton.addActionListener(this);
        buttonPane.add(trainControlsUIButton);
        
        controllerPane = new JPanel();
        controllerPane.setLayout(new BoxLayout(controllerPane, BoxLayout.PAGE_AXIS));
        controllerPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        controllerPane.add(Box.createRigidArea(new Dimension(0, 5)));
        for(TrainController tc : tch.getTrainControllerList()) {
	        controllerPane.add(new TrainControlTestBenchPanel(tc));
	        controllerPane.add(Box.createGlue());
        }
        
        picLabel = new JLabel();
        loadTrackImage(1);
        
		imagePane = new JPanel();
		imagePane.setLayout(new BoxLayout(imagePane, BoxLayout.X_AXIS));
		imagePane.setBorder(BorderFactory.createEmptyBorder(15,10,10,10));
		imagePane.add(picLabel);
        
        //Add all panels into 1
        mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        mainPane.add(buttonPane);
        mainPane.add(new JScrollPane(controllerPane));
      	mainPane.add(imagePane);
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
			frame.revalidate();
			frame.repaint();
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
        frame = new JFrame("Train Controller Test Bench");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane.
        tcUI = new TrainControlTestBenchUI();
        tcUI.mainPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(tcUI.mainPane);

        //Display the window.
        if(tch != null && tch.getTrainIDList().size() > 1) {
            frame.setSize(1600, 1250);
        }
        else {
            frame.setSize(1600, 1000); //TODO: resize this smaller
        }
        //frame.setMinimumSize(new Dimension(1600, 1000));
        //frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	//Create TrainControllerHelper - with sample test data to show different UI states
    	tch = new TrainControllerHelper();
    	tch.addNewTrainController(123);
    	
    	//Load up database
    	MainMTR.getStaticTrackDBHelper();

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowTrainControlGUI();
            }
        });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        if(e.getSource() == dispatchTrainButton) {
        	int id = Integer.parseInt(trainIDTextField.getText());
        	if(null != tch.addNewTrainController(id)) {
        		reloadGUI();
        		TrainControlUI.reloadGUI();
        	}
        }	
        else if(e.getSource() == switchButton) {
        	int newSwitchPosition = 2;
        	
        	if(switchPosition == 2) {
        		newSwitchPosition = 1;
        	}
        	
        	switchPosition = newSwitchPosition;
        	loadTrackImage(switchPosition);    		
        }	
        else if(e.getSource() == trainEngineerUIButton) {
        	javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    TrainEngineerUI.createAndShowTrainEngineerGUI(tch);
                }
            });
        }
        else if(e.getSource() == trainControlsUIButton) {
        	javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    TrainControlUI.createAndShowTrainControlGUI(tch);
                }
            });
        }
	}
	
	private void loadTrackImage(int imNum) {
		String imageName = "TrainControlTestTrack1.png";
		if(imNum == 2) {
			imageName = "TrainControlTestTrack2.png";
		}
		
		BufferedImage image;
		try {
			image = ImageIO.read(new File(imageName));
		} catch (IOException ex) {
			image = null;
			System.out.println("image is null");
		}
		Icon icon = new ImageIcon(image.getScaledInstance(1450, 400, Image.SCALE_FAST));
		picLabel.setIcon(icon);
	}
}