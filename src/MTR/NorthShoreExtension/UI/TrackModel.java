package MTR.NorthShoreExtension.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;

public class TrackModel extends JPanel implements MouseListener {
	class Data{
		String linestatus;
		int speed;
		int authority;
		String section;
		int blockLength;
		double blockGrade;
		int speedLimit;
		String infrastructure;
		double elevation;
		double cumulativeElevation;
		String trackStatus;
		String heater;
	}
	String dataString = "Choose a Track for information!";
	Point p = new Point();
	JTable table;
	String[] colNames = {"Line/Status","Speed/Authority","Section","Block","Length (m)", 
			"Grade(%)", "Speed Limit (km/hr)","Infrastructre", "Elevation(m)",
			"Cumlative Elevation", "Track Status", "Heater"};
	String colString = Arrays.toString(colNames).replaceAll("\\[|\\]|,|\\s", "  ");
	//will eventaully be the number of lines in a read in csv, but for now just working with the example
	Data[] data = new Data[1];
	
	   public static void main(String[] a) {
		      TrackModel mouse = new TrackModel();
		      JFrame f = new JFrame("Track Model UI");
		      f.setSize(1200, 700);
		      f.addMouseListener(mouse);
		      f.add(mouse);
		      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		      f.setVisible(true);
		   }
		   public void paint(Graphics g) {
			   //add lights!!!
			  g.setColor(Color.black);
			  g.drawString(colString, 20, 490);
			  g.drawString(dataString, 20, 520);
			  g.setColor(Color.white);
		      g.fillRect (10, 10, 1178, 450);
		      g.setColor(Color.green);
		      g.drawLine(589, 40, 589, 100);
		      g.drawLine(589, 110, 589, 200);
		      g.drawLine(589, 210, 589, 300);
		      g.setColor(Color.red);
		      g.drawLine(460, 200, 500, 200);
		      g.drawLine(450, 190, 400, 170);
		      g.drawLine(450, 210, 400, 220);
		      g.drawLine(510, 200, 600, 200);
		      g.drawLine(610, 200, 700, 200);
		      g.drawArc(671, 200, 75, 95, 0, 90);
		      g.setColor(Color.green);
		      g.fillOval(460, 185, 10, 10);
		      g.setColor(Color.gray);
		      g.fillOval(590, 150, 25, 15);
		      g.setColor(Color.black);
		      g.drawRect(880, 20, 300, 150);
		      g.drawString("Inputs for Individual Testing:", 890, 40);
		   }
		   
		    public void mousePressed(MouseEvent e) {}
	        public void mouseReleased(MouseEvent e) {}
	        public void mouseEntered(MouseEvent e) {}
	        public void mouseExited(MouseEvent e) {}
	        public void mouseClicked(MouseEvent e) {
	        		int x=e.getX();
	            int y=e.getY();
	            //note in final version will just add the row because will be preloaded
	            //green lines
	            if((x>=580 && x<=600) && (y>=64 && y<=120)) {
	            		dataString = "  Green/Free           55/3                A           1           100             0.5                     55                                              0.5                        0.5                                       off";
	            		repaint();
	            }
	            else if((x>=580 && x<=600) && (y>=135 && y<=218)) {
	            		dataString = "  Green/Free           55/3                A           2           100              1                      55                     PIONEER                  1                         1.5                                       off";
	            		repaint();
	            }
	            else if((x>=580 && x<=600) && (y>=236 && y<=318)) {
	            		dataString = "  Green/Free           55/3                A           3           100             1.5                     55                                              1.5                         3                                       off";
	            		repaint();
                }
	            //red lines
	            else if((y>=190 && y<=210) && (x>=398 && x<=447)) {
	            		dataString = "  Red/Free              55/3                A           1            50             0.5                     40                                              0.25                        0.25                                       off";
	            		repaint();
	            }
	            else if((y>=232 && y<=242) && (x>=398 && x<=447)) {
	            		dataString = "  Red/Free              55/3                A           2            50              1                      40                                              0.50                        0.75                                       off";
	            		repaint();
	            }
	            else if((y>=220 && y<=270) && (x>=460 && x<=499)) {
	            		dataString = "  Red/Free              55/3                A           3            50             1.5                     40                      SWITCH             0.75                      1.50                                       off";
	            		repaint();
	            }
	            else if((y>=220 && y<=270) && (x>=510 && x<=600)) {
	            		dataString = "  Red/Free              55/3                B           4            50              2                      40                                              1.00                        2.50                                       off";
	            		repaint();
	            }
	            else if((y>=220 && y<=270) && (x>=611 && x<=685)) {
	            		dataString = "  Red/Free              55/3                B           5            50             1.5                     40                                              0.75                        3.25                                       off";
	            		repaint();
	            }
	            else if((y>=220 && y<=270) && (x>=692 && x<=736)) {
	            		dataString = "  Red/Free              55/3                B           6            50              1                      40                                              0.50                        3.75                                       off";
	            		repaint();
	            }
	            
	        }
	        
}
