package MTR.NorthShoreExtension.UI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.TrackModelSrc.*;
 
public class TrackModelUI extends JPanel implements MouseListener {
	//Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = null;
    TrackModel trackFunctions = new TrackModel();
    int numTrack = 10;
    static LoadTrackModelUI loading = new LoadTrackModelUI();
    static DBHelper load;
   
    public static DBHelper sendDB() {
		return load;
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
    
    public class TrackGraphic extends JPanel{
    		private static final long serialVersionUID = 1L;
    		int[] drawArray = new int[9];
    		int trackNumber = 0;
    		int occupied = 0;
    		int position = 0;
    		int trackid = 0;
    		String color;
    		String inf;
    		TrackGraphic(){
    			setPreferredSize(new Dimension(1000,600));
    		}
    		@Override
    		public void paintComponent(Graphics g) {
    			super.paintComponent(g);
    			g.setColor(Color.white);
    			g.fillRoundRect(10,10,975,400,15,15);
    			for(int i=0;i<numTrack;i++) {
    				System.out.println("rowid:"+i);
    				color = load.getColor(i);
    				if(color.equals("green")) {
    					g.setColor(Color.green);
    				}
    				else if(color.equals("red")) {
    					g.setColor(Color.red);
    				}
    				else {
    					g.setColor(Color.black);
    				}
    				trackid = load.getTrackID(i);
    				inf = load.getInfrastructure(trackid);
    				position = load.getSwitch(trackid);
    				drawArray = load.getDrawingCoordinates(i);
    				trackNumber = drawArray[6];
    				occupied = drawArray[7];
    				if(drawArray[4]==-1) {
    					g.drawArc(drawArray[0], drawArray[2], (drawArray[2]-drawArray[0]), (drawArray[3]-drawArray[1]), 
    							drawArray[4], drawArray[5]);
    					/*if(occupied!=0) {
    						g.fillRect((drawArray[0]+10), (drawArray[2]+10), 30, 15);
    					}*/
    				}
    				else {
    					g.drawLine(drawArray[0], drawArray[1], drawArray[2], drawArray[3]);
    					//make light green
    					if(occupied!=0) {
    						g.fillRect((drawArray[2]-drawArray[0])/2, (drawArray[3]-drawArray[1])/2, 30, 15);
    						//make light red
    					}
    					if(inf.equals("STATION") || inf.equals("STATION; PIONEER") || 
    							inf.equals("STATION; EDGEBROOK") || inf.equals("STATION; WHITED") || 
    							inf.equals("STATION; SOUTH BANK") || inf.equals("STATION; CENTRAL; UNDERDROUND") ||
    							inf.equals("STATION; INGLEWOOD; UNDERGROUND") || inf.equals("STATION; OVERBROOK; UNDERGROUND") ||
    							inf.equals("STATION; GLENBURY") || inf.equals("STATION; DORMONT") ||
    							inf.equals("STATION; MT LEBANON") || inf.equals("STATION; CASTLE SHANNON") ||
    							inf.equals("STATION: SHADYSIDE") || inf.equals("STATION: HERRON AVE") ||
    							inf.equals("STATION; SWISSVILLE") || inf.equals("STATION;    PENN STATION; UNDERGROUND") ||
    							inf.equals("STATION; STEEL PLAZA; UNDERGROUND") || inf.equals("STATION; FIRST AVE; UNDERGROUND") ||
    							inf.equals("STATION; STATION SQUARE") || inf.equals("STATION; SOUTH HILLS JUNCTION")) {
    						//draw station
    					}
    					if(inf.equals("SWITCH TO/FROM YARD") || inf.equals("SWITCH") || inf.equals("SWITCH; UNDERGROUND") ||
    							inf.equals("SWITCH TO YARD") || inf.equals("SWITCH FROM YARD")) {
    						//draw switch
    					}
    					if(inf.equals("RAILWAY CROSSING")) {
    						//draw railway crossing
    					}
    				}
    			}
    		}
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
    	load = loading.sendDB();
        TrackModelUI instance = new TrackModelUI();
    		//Set the look and feel.
        initLookAndFeel();
 
        //Create and set up the window.
        JFrame frame = new JFrame("Track Model UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.add(instance.new TrackGraphic());
        frame.add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    
}
