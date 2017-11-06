package MTR.NorthShoreExtension.UI;
 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.*;

import MTR.NorthShoreExtension.Backend.DBHelper;

import javax.swing.SwingUtilities;
import javax.jnlp.*;
 
import java.io.*;
import java.sql.SQLException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
 
/*
 * FileChooserDemo.java uses these files:
 *   images/Open16.gif
 *   images/Save16.gif
 */
public class LoadTrackModelUI extends JPanel
                             implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    JFileChooser fc;
    static DBHelper load;
 
    public LoadTrackModelUI() {
        super(new BorderLayout());
 
        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
 
        //Create a file chooser
        fc = new JFileChooser();
 
        //Uncomment one of the following lines to try a different
        //file selection mode.  The first allows just directories
        //to be selected (and, at least in the Java look and feel,
        //shown).  The second allows both files and directories
        //to be selected.  If you leave these lines commented out,
        //then the default mode (FILES_ONLY) will be used.
        //
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
 
        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        openButton = new JButton("Open a File...",
                                 createImageIcon("images/Open16.gif"));
        openButton.addActionListener(this);
 
        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        saveButton = new JButton("Save a File...",
                                 createImageIcon("images/Save16.gif"));
        saveButton.addActionListener(this);
 
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
 
        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }
 
    public void actionPerformed(ActionEvent e) {
 
        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(LoadTrackModelUI.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                BufferedReader br = null;
                String line;
				try {
					br = new BufferedReader(new FileReader(file));
					while((line = br.readLine())!=null) {
						String[] trackInfo = line.split(",");
						String curveStart = trackInfo[14];
						int curveStartFinal;
						String curveEnd = trackInfo[15];
						int curveEndFinal;
						if(curveStart!=null) {
							curveStartFinal = Integer.parseInt(trackInfo[14]);
						}else {
							curveStartFinal = -1;
						}
						if(curveEnd!=null) {
							curveEndFinal = Integer.parseInt(trackInfo[15]);
						}else {
							curveEndFinal = -1;
						}
						load.addTrack(Integer.parseInt(trackInfo[0].replaceAll("[^0-9]", "")), trackInfo[1], trackInfo[2], Integer.parseInt(trackInfo[3].replaceAll("[^0-9]", "")), Integer.parseInt(trackInfo[4].replaceAll("[^0-9]", "")), Double.parseDouble(trackInfo[5]), Integer.parseInt(trackInfo[6].replaceAll("[^0-9]", "")), trackInfo[7], Double.parseDouble(trackInfo[8]), Double.parseDouble(trackInfo[9]), Integer.parseInt(trackInfo[10].replaceAll("[^0-9]", "")), Integer.parseInt(trackInfo[11].replaceAll("[^0-9]", "")), Integer.parseInt(trackInfo[12].replaceAll("[^0-9]", "")), Integer.parseInt(trackInfo[13].replaceAll("[^0-9]", "")), curveStartFinal, curveEndFinal, trackInfo[16], Integer.parseInt(trackInfo[17].replaceAll("[^0-9]", "")));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                try {
					br.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                log.append("Opening: " + file.getName() + "." + newline);
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
            String[] args = null;
			TrackModelUI.main(args);
        //Handle save button action.
        } else if (e.getSource() == saveButton) {
            int returnVal = fc.showSaveDialog(LoadTrackModelUI.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would save the file.
                log.append("Saving: " + file.getName() + "." + newline);
            } else {
                log.append("Save command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }
 
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = LoadTrackModelUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Load Track");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new LoadTrackModelUI());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) throws ClassNotFoundException {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
    		load = new DBHelper();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
}