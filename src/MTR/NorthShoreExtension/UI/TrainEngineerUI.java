/*
 * Filename: TrainEngineerUI.java
 * Author: Joe Lynch
 * Date Created: 11/2/2017
 * File Description: This contains the main frame for the Train Engineer GUI 
 * 			as well as a main function to test this as an individual sub-module.
 * 			This UI provides a way for the Train Engineer to configure the 
 * 			Kp and Ki parameters for all subsequent trains leaving the yard.
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class TrainEngineerUI implements ActionListener {
	//Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = "System";
    
    private static JFrame frame = null;
    private JPanel mainPane;
    private JFormattedTextField setKp;
    private JFormattedTextField setKi;
    private JButton submitButton;
    private TrainControllerHelper tch;
        
    public TrainEngineerUI(TrainControllerHelper tch) {
        this.tch = tch;
        
        mainPane = new JPanel();
        mainPane.setLayout(new GridBagLayout()); 
        mainPane.setPreferredSize(new Dimension(500, 215));
        GridBagConstraints c = new GridBagConstraints();
        
        //Create the set speed field format, and then the text field.
        NumberFormat nvNumberFormat = NumberFormat.getNumberInstance();
        nvNumberFormat.setMaximumFractionDigits(3);
        if (nvNumberFormat instanceof DecimalFormat) {
            ((DecimalFormat) nvNumberFormat).setDecimalSeparatorAlwaysShown(true);
        }
        NumberFormatter nvFormatter = new NumberFormatter(nvNumberFormat);
        nvFormatter.setAllowsInvalid(false);
        nvFormatter.setCommitsOnValidEdit(true);//seems to be a no-op --
        //aha -- it changes the value property but doesn't cause the result to
        //be parsed (that happens on focus loss/return, I think).        
        
        //Add components
        /* Add Kp parameter info */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,75,15,0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        mainPane.add(new JLabel("Kp: "), c);
        
        setKp = new JFormattedTextField(nvFormatter);
        setKp.setColumns(5);
        setKp.setValue(tch.getPIDParameter_p());
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,-50,0,75);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        mainPane.add(setKp, c);
        
        /* Add Ki parameter info */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,75,15,0);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        mainPane.add(new JLabel("Ki: "), c);
        
        setKi = new JFormattedTextField(nvFormatter);
        setKi.setColumns(5);
        setKi.setValue(tch.getPIDParameter_i());
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,-50,0,75);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        mainPane.add(setKi, c);
 
        /* Add submit button */
    	submitButton = new JButton("Submit PI Parameters");
    	submitButton.addActionListener(this);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(25,40,0,40);
    	c.weightx = 0.5;
    	c.gridwidth = 2;
    	c.gridx = 0;
    	c.gridy = 2;
    	mainPane.add(submitButton, c);
    	
    	mainPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("PI Parameters"),
                BorderFactory.createEmptyBorder(5,5,5,5)));    	
    }    

	@Override
	public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submitButton) {
        	Double Kp = Double.parseDouble(setKp.getText());
        	Double Ki = Double.parseDouble(setKi.getText());
        	
        	tch.setPIDParameters(Kp,Ki);
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
    public static void createAndShowTrainEngineerGUI(TrainControllerHelper tch) {
        //Set the look and feel.
        initLookAndFeel();

        //Create and set up the window.
        if(frame == null) {
	        frame = new JFrame("Train Engineer UI");
	        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

	        //Create and set up the content pane.
	        TrainEngineerUI tcUI = new TrainEngineerUI(tch);
	        //tcUI.mainPane.setOpaque(true); //content panes must be opaque
	        //frame.setContentPane(tcUI.mainPane);
	        tcUI.mainPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(tcUI.mainPane);
        }
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
    	//Create TrainControllerHelper
    	TrainControllerHelper tch = new TrainControllerHelper();
    	
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowTrainEngineerGUI(tch);
            }
        });
    }
}
