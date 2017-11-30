/*
* Filename: trainScheduler.java
* Author: Matt Snyder
* Last Edited: 11/28/2017
* File Description: This contains the Train Scheduler. 
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

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.*;
import MTR.NorthShoreExtension.Backend.CTCSrc.*;

public class TrainSchedulerUI extends JFrame {
	//create two arrays for demo purpose. actual will have the list imported from the database
	static DBHelper database = MainMTR.getDBHelper();
	
	String[] grnLineStops = {"2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009"}; //fall-back data in case file doesn't get loaded
	String[] redLineStops = {"1001", "1002", "1003", "1004", "1005", "1006", "1007", "1008", "1009"};
	String[] importedRed = new String[75];
	String[] importedGrn = new String[150];
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
	
	private JFrame frame = new JFrame("Schedule A Train");
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
	
	public TrainSchedulerUI() {
		render();
	}
	
	public void loadComboBoxes() {
		//parameters for test.csv
		int j = 0;
		int k = 0;
		for (int i = 0; i < 150; i++) {
			String text = Integer.toString(database.getTrackID(i));
			String line = database.getColor(i);
			if (line.equals("green")) {
				importedGrn[j] = text;
				j++;
			} else if (line.equals("red")) {
				importedRed[k] = text;
				k++;
			}
		}
		grnStopsImported = new JComboBox<String>(importedGrn);
		redStopsImported = new JComboBox<String>(importedRed);
		loaded = true;
	}
	
	public void render() {
		JPanel grnTrain = new JPanel();
		JPanel redTrain = new JPanel();
		JPanel schedule = new JPanel();
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
		JPanel grnStopChoice = new JPanel(new GridLayout(2,1));
		JLabel grnChoice = new JLabel("Choose a stop: ");
		grnStopChoice.add(grnChoice);
		if (loaded == false) {
			grnStopChoice.add(grnStops);
		} else {
			grnStopChoice.add(grnStopsImported);
		}
		grnRght.add(grnStopChoice);
		
		JPanel redStopChoice = new JPanel(new GridLayout(2,1));
		JLabel redChoice = new JLabel("Choose a stop: ");
		redStopChoice.add(redChoice);
		if (loaded == false) {
			redStopChoice.add(redStops);
		} else {
			redStopChoice.add(redStopsImported);
		}
		redRght.add(redStopChoice);
		
		//Add departure time segment, Green Line
		JPanel grnDepart = new JPanel(new GridBagLayout());
		GridBagConstraints grnGBC = new GridBagConstraints();
		JLabel grnDepartLabel = new JLabel("Departure Time: ");
		grnGBC.gridx = 0;
		grnGBC.gridy = 0;
		grnGBC.fill = GridBagConstraints.HORIZONTAL;
		grnGBC.gridwidth = 5;
		grnDepart.add(grnDepartLabel, grnGBC);
		
		JTextField grnHour0 = new JTextField(1);
		JTextField grnHour1 = new JTextField(1);
		JTextField grnSpacer = new JTextField(3);
		JTextField grnMins0 = new JTextField(1);
		JTextField grnMins1 = new JTextField(1);
		
		grnHour0.setText("0");
		grnHour1.setText("0");
		grnSpacer.setText(" :");
		grnSpacer.setEditable(false);
		grnMins0.setText("0");
		grnMins1.setText("0");
				
		grnGBC.gridwidth = 1;
		grnGBC.gridx = 0;
		grnGBC.gridy = 1;
		grnDepart.add(grnHour1, grnGBC);
		grnGBC.gridx = 1;
		grnGBC.gridy = 1;
		grnDepart.add(grnHour0, grnGBC);
		grnGBC.gridx = 2;
		grnGBC.gridy = 1;
		grnDepart.add(grnSpacer, grnGBC);
		grnGBC.gridx = 3;
		grnGBC.gridy = 1;
		grnDepart.add(grnMins1, grnGBC);
		grnGBC.gridx = 4;
		grnGBC.gridy = 1;
		grnDepart.add(grnMins0, grnGBC);
		grnRght.add(grnDepart);
		
		grnRght.add(addStopGrn);
		grnRght.add(schedTrainGrn);
		grnRght.add(nextTrainGrn);
		
		nextTrainGrn.setEnabled(false);
		
		//Add departure time segment, Red Line
		JPanel redDepart = new JPanel(new GridBagLayout());
		GridBagConstraints redGBC = new GridBagConstraints();
		JLabel redDepartLabel = new JLabel("Departure Time: ");
		redGBC.gridx = 0;
		redGBC.gridy = 0;
		redGBC.fill = GridBagConstraints.HORIZONTAL;
		redGBC.gridwidth = 5;
		redDepart.add(redDepartLabel, redGBC);
		
		JTextField redHour0 = new JTextField(1);
		JTextField redHour1 = new JTextField(1);
		JTextField redSpacer = new JTextField(3);
		JTextField redMins0 = new JTextField(1);
		JTextField redMins1 = new JTextField(1);
		
		redHour0.setText("0");
		redHour1.setText("0");
		redSpacer.setText(" : ");
		redSpacer.setEditable(false);
		redMins0.setText("0");
		redMins1.setText("0");
		
		redGBC.gridwidth = 1;
		redGBC.gridx = 0;
		redGBC.gridy = 1;
		redDepart.add(redHour1, redGBC);
		redGBC.gridx = 1;
		redGBC.gridy = 1;
		redDepart.add(redHour0, redGBC);
		redGBC.gridx = 2;
		redGBC.gridy = 1;
		redDepart.add(redSpacer, redGBC);
		redGBC.gridx = 3;
		redGBC.gridy = 1;
		redDepart.add(redMins1, redGBC);
		redGBC.gridx = 4;
		redGBC.gridy = 1;
		redDepart.add(redMins0, redGBC);
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
				String redDepart = (redHour1.getText() + redHour0.getText() + ":" + redMins1.getText() + redMins0.getText());
				String redNextStop = "Stop " + xRed + ": \n " + redLine + ": " + redStop + " \n Departure: " + redDepart + "\n Authority: " + nextAuthRed + "\n Suggested Speed: MAX";
				String redPrevious = stopRouteRed.getText();
				stopRouteRed.setText(redPrevious + "\n\n" + redNextStop);
				int departureRed = Integer.parseInt(redHour1.getText())*1000 + Integer.parseInt(redHour0.getText())*100 + Integer.parseInt(redMins1.getText())*10 + Integer.parseInt(redMins0.getText());
				departRed[xRed - 1] = departureRed;
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
				String grnDepart = (grnHour1.getText() + grnHour0.getText() + ":" + grnMins1.getText() + grnMins0.getText());
				String grnNextStop = "Stop " + xGrn + ": \n " + grnLine + ": " + stopAsInt + " \n Departure: " + grnDepart + "\n Suggested Speed: " + database.getSpeedLimit(stopAsInt);
				String grnPrevious = stopRouteGrn.getText();
				stopRouteGrn.setText(grnPrevious + "\n\n" + grnNextStop);
				int departureGrn = Integer.parseInt(grnHour1.getText())*1000 + Integer.parseInt(grnHour0.getText())*100 + Integer.parseInt(grnMins1.getText())*10 + Integer.parseInt(grnMins0.getText());
				departGrn[xGrn - 1] = departureGrn;
				xGrn++;
			}
		});
		
		schedTrainRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String redWrit = stopRouteRed.getText();
				String redToWrite = redWrit + "\n\n Scheduling Train...!";
				stopRouteRed.setText(redToWrite);
				redStops.setEnabled(false);
 				redHour1.setEnabled(false);
				redHour0.setEnabled(false);
				redMins1.setEnabled(false);
				redMins0.setEnabled(false);
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
 				grnHour1.setEnabled(false);
				grnHour0.setEnabled(false);
				grnMins1.setEnabled(false);
				grnMins0.setEnabled(false);
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
 				redHour1.setEnabled(true);
				redHour0.setEnabled(true);
				redMins1.setEnabled(true);
				redMins0.setEnabled(true);
				addStopRed.setEnabled(true);
				schedTrainRed.setEnabled(true);
				nextTrainRed.setEnabled(false);
				stopRouteRed.setText("Add a Stop...");
				xRed = 1;
				authRed = new int[150];
				departRed = new int[150];
			}
		});
		
		nextTrainGrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grnStops.setEnabled(true);
 				grnHour1.setEnabled(true);
				grnHour0.setEnabled(true);
				grnMins1.setEnabled(true);
				grnMins0.setEnabled(true);
				addStopGrn.setEnabled(true);
				schedTrainGrn.setEnabled(true);
				nextTrainGrn.setEnabled(false);
				stopRouteGrn.setText("Add a Stop...");
				xGrn = 1;
				authGrn = new int[150];
				departGrn = new int[150];
			}
		});
	}
}