package MTR.NorthShoreExtension.UI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.TrackModelSrc.*;
import MTR.NorthShoreExtension.ProofOfConcept.Testing;
 
public class TrackModelUI extends JPanel {
	public static TrackGraphic trackGraphic = null;
	//Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = null;
    static TrackModelUI instance;
    TrackModel trackFunctions = new TrackModel();
    static LoadTrackModelUI loading = new LoadTrackModelUI();
    int numTrack = loading.sendRow();
    static DBHelper load;
    String[] returnString = new String[15];
    String showLine = " ";
	String showStatus = " ";
	String showAuth = " ";
	String showSpeed = " ";
	String showSect = " ";
	String showBlock = " ";
	String showLength = " ";
	String showGrade = " ";
	String showLimit = " ";
	String showInf = " ";
	String showEl = " ";
	String showCEl = " ";
	String showBroken = " ";
	String showHeater = " ";
	
	public static TrackModelUI getModel() {
		return instance;
	}
    public static void getDB(DBHelper db) {
    		load = db;
    }
    
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
    
    public class TrackGraphic extends JPanel implements MouseListener, ActionListener{
    		private static final long serialVersionUID = 1L;
    		int[] drawArray = new int[9];
    		int trackNumber = 0;
    		int occupied = 0;
    		int position = 0;
    		int trackid = 0;
    		String color;
    		String inf;
    		String stat;
    		
    		public void drawString(Graphics g, String text, int x, int y)
    		{
    			for(String line : text.split("\n")) {
    				g.drawString(line, x, y += g.getFontMetrics().getHeight());
    			}
    		}
    		
    		TrackGraphic(){
    			//setPreferredSize(new Dimension(1000,600));
    			setPreferredSize(new Dimension(1500,900));
    			addMouseListener(this);
    		}

    		@Override
    		public void paintComponent(Graphics g1) {
    			Graphics2D g = (Graphics2D)g1.create();
    			super.paintComponent(g);
    			//System.out.println("row: "+numTrack);
    			//draw yard
    			g.setColor(Color.white);
    			//g.fillRoundRect(250,10,735,575,15,15);
    			g.fillRoundRect(250,10,1235,885,15,15);
    			//"Line/Status","Speed/Authority","Section","Block","Length (m)", 
    			//"Grade(%)", "Speed Limit (km/hr)","Infrastructre", "Elevation(m)",
    			//"Cumlative Elevation", "Track Status", "Heater"
    			g.setColor(Color.black);
    			g.drawString("Click on a track to view information:", 10, 20);
    			g.drawString("Line:", 10, 50);
    			g.drawString("Occupied:", 10, 80);
    			g.drawString("Speed:", 10, 110);
    			g.drawString("Authority:", 10, 140);
    			g.drawString("Section:", 10, 170);
    			g.drawString("Block:", 10, 200);
    			g.drawString("Length(m):", 10, 230);
    			g.drawString("Grade(%):", 10, 260);
    			g.drawString("Speed Lm.(km/hr):", 10, 290);
    			g.drawString("Elevation(m):", 10, 320);
    			g.drawString("Tot. El.:", 10, 350);
    			g.drawString("Status:", 10, 380);
    			g.drawString("Heater:", 10, 410);
    			g.drawString("Infrastructure:", 10, 440);
    			g.drawString(showLine, 140, 50);
    			g.drawString(showStatus, 140, 80);
    			g.drawString(showSpeed, 140, 110);
    			g.drawString(showAuth, 140, 140);
    			g.drawString(showSect, 140, 170);
    			g.drawString(showBlock, 140, 200);
    			g.drawString(showLength, 140, 230);
    			g.drawString(showGrade, 140, 260);
    			g.drawString(showLimit, 140, 290);
    			g.drawString(showEl, 140, 320);
    			g.drawString(showCEl, 140, 350);
    			g.drawString(showBroken, 140, 380);
    			g.drawString(showHeater, 140, 410);
    			showInf=showInf.replaceAll(";", "\n");
    			drawString(g, showInf, 140, 445);
    			//g.drawString(showInf, 150, 410);
    			g.drawLine(10, 730, 180, 730);
    			g.drawLine(180, 510, 180, 730);
    			g.drawLine(10, 510, 10, 730);
    			g.drawLine(10, 510, 180, 510);
    			g.drawString("Key", 85, 530);
    			g.drawString("Light: ", 20,560);
    			g.drawString("Switch: ", 20,590);
    			g.drawString("Train: ", 20,620);
    			g.drawString("Station: ", 20,650);
    			g.drawString("Broken Track:", 20, 680);
    			g.drawString("Crossing:", 20, 710);
    			g.setColor(Color.green);
				g.fillRect(140, 580, 10, 10);
    			g.setColor(Color.gray);
			      g.fillOval(140, 640, 15, 10);
			    g.setColor(Color.blue);
	    	      g.fillRect(140, 610, 20, 10);
	    	    g.setColor(Color.green);
				  g.fillOval(140, 550, 8, 8);
				g.setColor(Color.red);
				g.drawLine(140, 670, 150, 680);
	    	      g.drawLine(150, 670, 140, 680);
	    	    g.setColor(Color.gray);
	    	    g.fillRect(1200, 250, 200, 100);
	    	    g.drawLine(140, 700, 150, 710);
    	  		g.drawLine(150, 700, 140, 710);
	    	    if(!MainMTR.fullUI) {
	    	    	    g.setColor(Color.black);
	    	        	g.fillRect(20, 800, 150, 20);
	  		      g.setColor(Color.white);
	  		      g.drawString("Update This Track", 40, 815);
	    	    }
	    	    g.setStroke(new BasicStroke(3));
	    	    g.setColor(Color.white);
	    	    g.drawString("YARD", 1200, 250);
	    	    g.setColor(Color.red);
	    	    g.drawLine(1350, 250, 1280, 65);
	    	    g.setColor(Color.green);
	    	    g.drawLine(1250, 350, 1120, 495);
	    	    g.drawLine(1350, 350, 1295, 550);
    			for(int i=0;i<numTrack;i++) {
    				g.setStroke(new BasicStroke(3));
    				//System.out.println("rowid:"+i);
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
    				stat = load.getTrackStatus(trackid);
    				trackNumber = drawArray[6];
    				occupied = drawArray[7];
    				if(drawArray[4]!=0) {
    					g.drawArc(drawArray[0], drawArray[2], (drawArray[2]-drawArray[0]), (drawArray[3]-drawArray[1]), 
    							drawArray[4], drawArray[5]);
    					if(occupied!=0) {
    						g.fillRect((drawArray[0]+10), (drawArray[2]+10), 30, 15);
    					}
    				}
    				else {
    					//((Graphics2D) g).setStroke(new BasicStroke(2));
    					g.drawLine(drawArray[0], drawArray[1], drawArray[2], drawArray[3]);
    					g.setColor(Color.green);
    					g.fillOval((drawArray[0]-5), (drawArray[1]-10), 8, 8);
    			
    					if(occupied!=0) {
    						g.fillRect((drawArray[2]-drawArray[0])/2, (drawArray[3]-drawArray[1])/2, 30, 15);
    						g.setColor(Color.red);
    						g.fillOval((drawArray[0]-5), (drawArray[1]-10), 8, 8);
    						//draw train
    						g.setColor(Color.blue);
    			    	     	g.fillRect((drawArray[0]+10), (drawArray[1]-10), 20, 10);
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
    							inf.equals("STATION; STATION SQUARE") || inf.equals("STATION; SOUTH HILLS JUNCTION") ||
    							inf.equals("STATION; POPLAR")) {
    						  g.setColor(Color.gray);
    					      g.fillOval((drawArray[0]), (drawArray[1]), 15, 10);
    					}
    					if(inf.equals("SWITCH TO/FROM YARD") || inf.equals("SWITCH") || inf.equals("SWITCH; UNDERGROUND") ||
    							inf.equals("SWITCH TO YARD") || inf.equals("SWITCH FROM YARD")) {
    						g.setColor(Color.green);
    						if(position == 1) {
    							g.setColor(Color.red);
    						}
        					g.fillRect((drawArray[2]-5), (drawArray[3]+5), 10, 10);
    					}
    					if(inf.equals("RAILWAY CROSSING")) {
    						//draw railway crossing
    						g.setColor(Color.gray);
    						if(occupied != 0) {
    							g.setColor(Color.yellow);
    						}
    						g.drawLine(drawArray[0]-20, drawArray[1]-25, drawArray[0]-15, drawArray[1]-14);
			    	  		g.drawLine(drawArray[0]-20, drawArray[1]-14, drawArray[0]-15, drawArray[1]-25);
    						
    					}
    					if(stat.equals("Broken - Power Failure") || stat.equals("Broken - Broken Rail") || stat.equals("Broken - Track Circuit Failure")) {
    						//draw x
    						g.setColor(Color.red);
    						g.drawLine(drawArray[0]-5, drawArray[1]-5, drawArray[0]+5, drawArray[1]+1);
    			    	  		g.drawLine(drawArray[0]-5, drawArray[1]+1, drawArray[0]+5, drawArray[1]-5);
    					}
    				}
    				}
    			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int x=e.getX();
		        int y=e.getY();
		        System.out.println("recognized! x: " +x+"y: "+y);
		        int displayTrack = 0;
		        displayTrack = load.findCoordinates(x, y);
		        returnString = load.getDisplayInfo(displayTrack);
		        showLine = returnString[0];
				showStatus = returnString[1];
				if(showStatus.equals("0")) {
					showStatus = "Free";
				}
				else if(showStatus.equals("1")) {
					showStatus = "Occupied";
				}
				else if(showStatus.equals("-1")){
					showStatus = " ";
				}
				showAuth = returnString[2];
				showSpeed = returnString[3];
				showSect = returnString[4];
				showBlock = returnString[5];
				showLength = returnString[6];
				showGrade = returnString[7];
				showLimit = returnString[8];
				showInf = returnString[9];
				showEl = returnString[10];
				showCEl = returnString[11];
				showBroken = returnString[12];
				showHeater = returnString[13];
				if(x < 150 && (y > 800 && y < 900 )) {
					UpdateTrackUI.createAndShowGUI();
				}
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: If adding more events to this contact Joe
				repaint();
			}
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
     	load = MainMTR.getDBHelper();
        instance = new TrackModelUI();
    		//Set the look and feel.
        initLookAndFeel();
 
        //Create and set up the window.
        JFrame frame = new JFrame("Track Model UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        trackGraphic = instance.new TrackGraphic();
        panel.add(trackGraphic);
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
    
}
