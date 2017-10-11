package MTR.NorthShoreExtension.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;

public class TrackModel extends JPanel implements MouseListener {
	//load csv into db
	//test
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
	//read in from db here
    int x1, x2, x3, x4, y1, y2, y3, y4;
	String status = "Free";
	Boolean clicked = false;
	Boolean failure = false;
	String dataString = " ";
	Point p = new Point();
	JTable table;
	String[] colNames = {"Line/Status","Speed/Authority","Section","Block","Length (m)", 
			"Grade(%)", "Speed Limit (km/hr)","Infrastructre", "Elevation(m)",
			"Cumlative Elevation", "Track Status", "Heater"};
	//String colString = Arrays.toString(colNames).replaceAll("\\[|\\]|,|\\s", "  ");
	//will eventually be the number of lines in a read in csv, but for now just working with the example
	String colString = " ";
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
		   public void paint(Graphics g1) {
			   //add lights!!!
			   //for box and words!
			   Graphics2D g = (Graphics2D)g1.create();
			  if(clicked) {
				  g.setColor(Color.black);
				  g.drawString(colString, 85, 490);
				  g.drawString(dataString, 85, 520);
				  g.drawLine(75, 470, 1130, 470);
				  g.drawLine(75, 500, 1130, 500);
				  g.drawLine(75, 530, 1130, 530);
				  g.drawLine(75, 470, 75, 530);
				  g.drawLine(1130, 470, 1130, 530);
				  g.drawLine(1060, 470, 1060, 530);
				  g.drawLine(970, 470, 970, 530);
				  g.drawLine(840, 470, 840, 530);
				  g.drawLine(750, 470, 750, 530);
				  g.drawLine(660, 470, 660, 530);
				  g.drawLine(520, 470, 520, 530);
				  g.drawLine(460, 470, 460, 530);
				  g.drawLine(380, 470, 380, 530);
				  g.drawLine(330, 470, 330, 530);
			  	  g.drawLine(270, 470, 270, 530);
			  	  g.drawLine(170, 470, 170, 530);
			  	  g.setColor(Color.red);
			  	  g.fillRect(550, 550, 80, 30);
			  	  g.setColor(Color.white);
			  	  g.drawRect(550,550,80,30);
			  	  g.drawString("Break", 572, 570);
			  }
			  
			  g.setColor(Color.white);
		      g.fillRoundRect (10, 10, 1178, 450, 90, 90);
		      g.setColor(Color.black);
		      g.drawRoundRect(10, 10, 1178, 450, 90, 90);
		      g.setColor(Color.GREEN);
		      g.setStroke(new BasicStroke(3));
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
		      g.drawRoundRect(870, 20, 300, 150, 90, 90);
		      g.drawString("Inputs for Individual Testing:", 900, 40);
		      
		      if(failure) {
				g.setColor(Color.red);
		    	  	g.drawLine(x1, y1, x2, y2);
		    	  	g.drawLine(x1, y2, x2, y1);
		      }
		      
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
	            		x1=584;
	            		x2=594;
	            		y1=65;
	            		y2=75;
	            		clicked = true;
	            		colString = Arrays.toString(colNames).replaceAll("\\[|\\]|,|\\s", "  ");
	            		dataString = "  Green/"+status+"           55/3                A           1           100             0.5                     55                                              0.5                        0.5                                       off";
	            		repaint();
	            }
	            else if((x>=580 && x<=600) && (y>=135 && y<=218)) {
		            	x1=584;
	            		x2=594;
	            		y1=136;
	            		y2=146;
	            		clicked = true;
	            		colString = Arrays.toString(colNames).replaceAll("\\[|\\]|,|\\s", "  ");
	            		dataString = "  Green/"+status+"          55/3                A           2           100              1                      55                     PIONEER                  1                         1.5                                       off";
	
	            		repaint();
	            }
	            else if((x>=580 && x<=600) && (y>=236 && y<=318)) {
		            	x1=584;
	            		x2=594;
	            		y1=237;
	            		y2=247;
	            		clicked = true;
	            		colString = Arrays.toString(colNames).replaceAll("\\[|\\]|,|\\s", "  ");
	            		dataString = "  Green/"+status+"           55/3                A           3           100             1.5                     55                                              1.5                         3                                       off";
	
	            		repaint();
                }
	            //red lines
	            else if((y>=190 && y<=210) && (x>=398 && x<=447)) {
		            	x1=419;
	            		x2=429;
	            		y1=170;
	            		y2=180;
	            		clicked = true;
	            		colString = Arrays.toString(colNames).replaceAll("\\[|\\]|,|\\s", "  ");
	            		dataString = "  Red/"+status+"              55/3                A           1            50             0.5                     40                                              0.25                        0.25                                       off";
	  
	            		repaint();
	            }
	            else if((y>=232 && y<=242) && (x>=398 && x<=447)) {
		            	x1=419;
	            		x2=429;
	            		y1=212;
	            		y2=222;
	            		clicked = true;
	            		colString = Arrays.toString(colNames).replaceAll("\\[|\\]|,|\\s", "  ");
	            		dataString = "  Red/"+status+"              55/3                A           2            50              1                      40                                              0.50                        0.75                                       off";
	   
	            		repaint();
	            }
	            else if((y>=220 && y<=270) && (x>=460 && x<=499)) {
		            	x1=481;
	            		x2=491;
	            		y1=194;
	            		y2=204;
	            		clicked = true;
	            		colString = Arrays.toString(colNames).replaceAll("\\[|\\]|,|\\s", "  ");
	            		dataString = "  Red/"+status+"              55/3                A           3            50             1.5                     40                      SWITCH             0.75                      1.50                                       off";
	
	            		repaint();
	            }
	            else if((y>=220 && y<=270) && (x>=510 && x<=600)) {
		            	x1=531;
	            		x2=541;
	            		y1=194;
	            		y2=204;
	            		clicked = true;
	            		colString = Arrays.toString(colNames).replaceAll("\\[|\\]|,|\\s", "  ");
	            		dataString = "  Red/"+status+"              55/3                B           4            50              2                      40                                              1.00                        2.50                                       off";
	
	            		repaint();
	            }
	            else if((y>=220 && y<=270) && (x>=611 && x<=685)) {
		            	x1=632;
	            		x2=642;
	            		y1=194;
	            		y2=204;
	            		clicked = true;
	            		colString = Arrays.toString(colNames).replaceAll("\\[|\\]|,|\\s", "  ");
	            		dataString = "  Red/"+status+"              55/3                B           5            50             1.5                     40                                              0.75                        3.25                                       off";

	            		repaint();
	            }
	            else if((y>=220 && y<=270) && (x>=692 && x<=740)) {
		            	x1=713;
	            		x2=723;
	            		y1=214;
	            		y2=224;
	            		clicked = true;
	            		colString = Arrays.toString(colNames).replaceAll("\\[|\\]|,|\\s", "  ");
	            		dataString = "  Red/"+status+"              55/3                B           6            50              1                      40                                              0.50                        3.75                                       off";
	            		repaint();
	            }
	            else if((x>=550 && x<=630) && (y>=550 && y<=600)) {
        				failure = !failure;
        				repaint();
        		    }
	            
	        }
	        
}
