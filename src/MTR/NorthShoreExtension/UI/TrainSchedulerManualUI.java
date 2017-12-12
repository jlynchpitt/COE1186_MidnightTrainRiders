/*
* Filename: trainScheduler.java
* Author: Matt Snyder
* Last Edited: 12/06/2017
* File Description: This contains the Train Scheduler user interface for use in Manual mode.  
* It allows the user to enter the authority and speed manually instead of them being calculated
* based upon the stops that are entered.. 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HODLERS AND CONTRIBUTORS "AS IS" AND ANY
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
* OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCALIMED. IN NO EVENT
* SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
* INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING BUT NOT LIMITED
* TO, PROCUREMENT OF SUBSITUTE GOODS OR SERVICES; LOSS OF USE, DATA OR PROFITS; OR
* BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
* ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF 
* SUCH DAMAGE.
*/

package MTR.NorthShoreExtension.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.*;
import MTR.NorthShoreExtension.Backend.CTCSrc.*;

public class TrainSchedulerManualUI extends JFrame {
	//create two arrays for demo purpose. actual will have the list imported from the database
	static DBHelper database = MainMTR.getDBHelper();
	
	String[] grnLineStops = {"2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009"}; //fall-back data in case file doesn't get loaded
	String[] redLineStops = {"1001", "1002", "1003", "1004", "1005", "1006", "1007", "1008", "1009"};
	String[] importedRed = new String[1];
	String[] importedGrn = new String[1];
	List<String> imporRed = new ArrayList<String>();
	List<String> imporGrn = new ArrayList<String>();
	int xRed = 1; //number of grnStops
	int xGrn = 1;
	int authRed[] = new int[150];
	int authGrn[] = new int[150];
	int schedStopsRed[] = new int[150];
	int schedStopsGrn[] = new int[150];
	int trainID = 0;
	int departRed[] = new int[150];
	int departGrn[] = new int[150];
	boolean loaded = false;
	
	private static TrainSchedulerManualUI trsUI;
	private static JFrame frame = new JFrame("Schedule A Train");
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JButton addStopRed = new JButton("Add A Stop");
	private JButton addStopGrn = new JButton("Add A Stop");
	private JButton schedTrainRed = new JButton("Schedule Train");
	private JButton schedTrainGrn = new JButton("Schedule Train");
	private JButton nextTrainRed = new JButton("Schedule Another");
	private JButton nextTrainGrn = new JButton("Schedule Another");
	private JComboBox<String> grnStops = new JComboBox<String>(grnLineStops);
	private JComboBox<String> redStops = new JComboBox<String>(redLineStops);
	private JComboBox<String> grnStopsImported;
	private JComboBox<String> redStopsImported;
	
	public TrainSchedulerManualUI() {
		render();
	}
	
	public void loadComboBoxes() {
		//parameters for test.csv
		int j = 0;
		int k = 0;
		int dataLength = database.getDatabaseSize();
		for (int i = 0; i < dataLength+1; i++) { //change to end of file instead of 150
			String text = Integer.toString(database.getTrackID(i));
			String line = database.getColor(i);
			if (line.equals("green")) {
				imporGrn.add(text);
				j++;
			} else if (line.equals("red")) {
				imporRed.add(text);
				k++;
			}
		}
		if (j > 0) {
			importedGrn = new String[imporGrn.size()];
		}
		if (k > 0) {
			importedRed = new String[imporRed.size()];
		}
		
		for (int y = 0; y < imporGrn.size(); y++) {
			importedGrn[y] = imporGrn.get(y);
		}
		for (int x = 0; x < imporRed.size(); x++) {
			importedRed[x] = imporRed.get(x);
		}
		
		grnStopsImported = new JComboBox<String>(importedGrn);
		redStopsImported = new JComboBox<String>(importedRed);
		loaded = true;
	}
	
	public void render() {
		JPanel grnTrain = new JPanel();
		JPanel redTrain = new JPanel();
		//JPanel schedule = new JPanel();
		grnTrain.setLayout(new GridLayout(1,2));
		redTrain.setLayout(new GridLayout(1,2));
		
		JPanel grnLeft = new JPanel();
		JPanel grnRght = new JPanel(new GridLayout(5,1));
		JPanel redLeft = new JPanel();
		JPanel redRght = new JPanel(new GridLayout(5,1));
		//TODO: create some logic for checking that a db has been loaded
		loadComboBoxes();
		
		//Set up the Routing lists
		JTextArea stopRouteRed = new JTextArea(50, 40);
		stopRouteRed.setText("Add a Stop...");
		stopRouteRed.setEditable(false);
		redLeft.add(stopRouteRed);
		
		JTextArea stopRouteGrn = new JTextArea(50, 40);
		stopRouteGrn.setText("Add a Stop...");
		stopRouteGrn.setEditable(false);
		grnLeft.add(stopRouteGrn);
		
		//Create the interactive scheduling portion
		JPanel grnStopChoice = new JPanel(new GridLayout(3,1));
		JLabel grnChoice = new JLabel("Enter the authority chain: ");
		grnStopChoice.add(grnChoice);
		JTextField travelGrn = new JTextField();
		grnStopChoice.add(travelGrn);
		JTextArea grnNote = new JTextArea(40, 30);
		grnNote.setText("Note: Green Line Track sections are in the format 2XXX where XXX is the three digit code representing the track section and the 2 representing the Green line. The first block out from the Yard is 2062. ");
		grnNote.setLineWrap(true);
		grnNote.setWrapStyleWord(true);
		grnNote.setFont(new Font("Serif", Font.PLAIN, 11));
		grnNote.setEditable(false);
		grnStopChoice.add(grnNote);
		grnRght.add(grnStopChoice);
		
		JPanel redStopChoice = new JPanel(new GridLayout(3,1));
		JLabel redChoice = new JLabel("Enter the authority chain: ");
		redStopChoice.add(redChoice);
		JTextField travelRed = new JTextField();
		redStopChoice.add(travelRed);
		JTextArea redNote = new JTextArea(40, 30);
		redNote.setText("Note: Red Line Track sections are in the format 10XX where XX is the two digit code representing the track section and the 2 representing the Red line. The first block out from the Yard is 1009. ");
		redNote.setLineWrap(true);
		redNote.setWrapStyleWord(true);
		redNote.setFont(new Font("Serif", Font.PLAIN, 11));
		redNote.setEditable(false);
		redStopChoice.add(redNote);
		redRght.add(redStopChoice);
		
		//Add departure time segment, Green Line
		JPanel grnDepart = new JPanel(new GridBagLayout());
		GridBagConstraints grnGBC = new GridBagConstraints();
		JLabel grnDepartLabel = new JLabel("Travel Speed (MPH): ");
		grnGBC.gridx = 0;
		grnGBC.gridy = 0;
		grnGBC.fill = GridBagConstraints.HORIZONTAL;
		grnGBC.gridwidth = 5;
		grnDepart.add(grnDepartLabel, grnGBC);
		
		JFormattedTextField speedGrn = new JFormattedTextField();
		speedGrn.setColumns(3);
		grnGBC.gridx = 0;
		grnGBC.gridy = 1;
		grnDepart.add(speedGrn, grnGBC);
		grnRght.add(grnDepart);
		
		grnRght.add(addStopGrn);
		grnRght.add(schedTrainGrn);
		grnRght.add(nextTrainGrn);
		
		nextTrainGrn.setEnabled(false);
		
		//Add departure time segment, Red Line
		JPanel redDepart = new JPanel(new GridBagLayout());
		GridBagConstraints redGBC = new GridBagConstraints();
		JLabel redDepartLabel = new JLabel("Travel Speed (MPH): ");
		redGBC.gridx = 0;
		redGBC.gridy = 0;
		redGBC.fill = GridBagConstraints.HORIZONTAL;
		redGBC.gridwidth = 5;
		redDepart.add(redDepartLabel, redGBC);
		
		JFormattedTextField speedRed = new JFormattedTextField();
		speedRed.setColumns(3);
		redGBC.gridx = 0;
		redGBC.gridy = 1;
		redDepart.add(speedRed, redGBC);
		redRght.add(redDepart);
		
		nextTrainRed.setEnabled(false);
		
		redRght.add(addStopRed);
		redRght.add(schedTrainRed);
		redRght.add(nextTrainRed);

		
		//-------------------------
		redTrain.add(redLeft);
		redTrain.add(redRght);
		grnTrain.add(grnLeft);
		grnTrain.add(grnRght);
		
		tabbedPane.addTab("Red Line", redTrain);
		tabbedPane.addTab("Green Line", grnTrain);
		//tabbedPane.addTab("Schedules", schedule);
		
		//add it all together and make it visible
		frame.add(tabbedPane);
		frame.setSize(800, 750);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		//add action Listeners for the buttons
		addStopRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e ) {
				String redLine = "";
				int nextAuthRed = Math.abs(redStopsImported.getSelectedIndex() - authRed[xRed-1]);
				authRed[xRed] = redStops.getSelectedIndex();
				String stop = (String) redStopsImported.getSelectedItem();
				stop = stop.substring(1);
				int stopAsInt = Integer.parseInt(stop) + 1000;
				schedStopsRed[xRed-1] = stopAsInt;
				String redStop = redStops.getSelectedItem().toString();
				//String redDepart = (redHour1.getText() + redHour0.getText() + ":" + redMins1.getText() + redMins0.getText());
				String setSpeedRed = speedRed.getText().toString();
				String redNextStop = "Stop " + xRed + ": \n " + redLine + ": " + redStop +  "\n Authority: " + nextAuthRed + "\n Set Speed: " + setSpeedRed;
				String redPrevious = stopRouteRed.getText();
				stopRouteRed.setText(redPrevious + "\n\n" + redNextStop);
				//int departureRed = Integer.parseInt(redHour1.getText())*1000 + Integer.parseInt(redHour0.getText())*100 + Integer.parseInt(redMins1.getText())*10 + Integer.parseInt(redMins0.getText());
				//departRed[xRed - 1] = departureRed;
				xRed++;
			}
		});
		
		addStopGrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String grnLine = "";
				authGrn[0] = 2062;
				int nextAuthGrn = Math.abs(grnStopsImported.getSelectedIndex() - authGrn[xGrn-1]);
				authGrn[xGrn] = grnStops.getSelectedIndex();
				String stop = (String) grnStopsImported.getSelectedItem();
				stop = stop.substring(1);
				int stopAsInt = Integer.parseInt(stop) + 2000;
				System.out.println("Stop: " + stopAsInt); //remove after finished testing
				schedStopsGrn[xGrn-1] = stopAsInt;
				String grnStop = grnStops.getSelectedItem().toString();
				//String grnDepart = (grnHour1.getText() + grnHour0.getText() + ":" + grnMins1.getText() + grnMins0.getText());
				String setSpeedGrn = speedGrn.getText().toString();
				String grnNextStop = "Stop " + xGrn + ": \n " + grnLine + ": " + stopAsInt +"\n Set Speed: " + setSpeedGrn;
				String grnPrevious = stopRouteGrn.getText();
				stopRouteGrn.setText(grnPrevious + "\n\n" + grnNextStop);
				//int departureGrn = Integer.parseInt(grnHour1.getText())*1000 + Integer.parseInt(grnHour0.getText())*100 + Integer.parseInt(grnMins1.getText())*10 + Integer.parseInt(grnMins0.getText());
				//departGrn[xGrn - 1] = departureGrn;
				xGrn++;
			}
		});
		
		schedTrainRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String redWrit = stopRouteRed.getText();
				String redToWrite = redWrit + "\n\n Scheduling Train...!";
				stopRouteRed.setText(redToWrite);
				redStops.setEnabled(false);
				addStopRed.setEnabled(false);
				schedTrainRed.setEnabled(false);
				nextTrainRed.setEnabled(true);
				
				ctcUI.tsh.addNewTrainSchedule("Red", trainID, schedStopsRed, departRed);
				trainID++;
			}
		});
		
		schedTrainGrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String grnWrit = stopRouteGrn.getText();
				String grnToWrite = grnWrit + "\n\n Scheduling Train...!";
				stopRouteGrn.setText(grnToWrite);
				grnStops.setEnabled(false);
 				addStopGrn.setEnabled(false);
				schedTrainGrn.setEnabled(false);
				nextTrainGrn.setEnabled(true);
				TrainScheduleHelper.trainTracker.add(trainID, 9999);
				ctcUI.tsh.addNewTrainSchedule("Green", trainID, schedStopsGrn, departGrn);
				TrainSchedulesUI.repaintGUI();
				trainID++;
			}
		});
		
		nextTrainRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redStops.setEnabled(true);
				addStopRed.setEnabled(true);
				schedTrainRed.setEnabled(true);
				nextTrainRed.setEnabled(false);
				stopRouteRed.setText("Add a Stop...");
				xRed = 1;
				authRed = new int[150];
				departRed = new int[150];
				schedStopsRed = new int[150];
			}
		});
		
		nextTrainGrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grnStops.setEnabled(true);
 				addStopGrn.setEnabled(true);
				schedTrainGrn.setEnabled(true);
				nextTrainGrn.setEnabled(false);
				stopRouteGrn.setText("Add a Stop...");
				schedStopsGrn = new int[150];
				xGrn = 1;
				authGrn = new int[150];
				departGrn = new int[150];
			}
		});
	}
	
	public static void createAndShowGUI() {
		if (frame == null) {
			System.out.println("Creating Scheduler UI");
			frame = new JFrame("Train Scheduler");
			frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
			
			trsUI = new TrainSchedulerManualUI();
			trsUI.tabbedPane.setOpaque(true);
			frame.setContentPane(trsUI);
		} else {
			repaintGUI();
		}
	}
	
	public static void repaintGUI() {
		
		if (trsUI != null && frame != null) {
			trsUI.render();
			frame.setContentPane(trsUI.tabbedPane);
			frame.pack();
			frame.revalidate();
			frame.repaint();
		}
	}
}