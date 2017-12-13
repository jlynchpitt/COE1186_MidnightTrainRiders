/*
* Filename: TrainSchedulerManualUI.java
* Author: Matt Snyder
* Last Edited: 12/12/2017
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

	String[] travelingRed;
	String[] travelingGrn;
	List<String> redInput = new ArrayList();
	List<String> grnInput = new ArrayList();
	List<String> imporRed = new ArrayList<String>();//from auto mode
	List<String> imporGrn = new ArrayList<String>();
	int xRed = 1; //number of grnStops
	int xGrn = 1;
	//int authRed[] = new int[150];
	//int authGrn[] = new int[150];
	int schedStopsRed[] = new int[150];
	int schedStopsGrn[] = new int[150];
	int trainID = 0;
	int departRed[] = new int[150];
	int departGrn[] = new int[150];
	int speedRedSched = 0; 
	int speedGrnSched = 0;
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
	//private JComboBox<String> grnStops = new JComboBox<String>(grnLineStops);
	//private JComboBox<String> redStops = new JComboBox<String>(redLineStops);
	private JComboBox<String> grnStopsImported;
	private JComboBox<String> redStopsImported;
	private JTextField travelGrn;
	private JTextField travelRed;
	
	public TrainSchedulerManualUI() {
		render();
	}
	
	public void render() {
		JPanel grnTrain = new JPanel();
		JPanel redTrain = new JPanel();
		grnTrain.setLayout(new GridLayout(1,2));
		redTrain.setLayout(new GridLayout(1,2));
		
		JPanel grnLeft = new JPanel();
		JPanel grnRght = new JPanel(new GridLayout(5,1));
		JPanel redLeft = new JPanel();
		JPanel redRght = new JPanel(new GridLayout(5,1));
		
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
		travelGrn = new JTextField();
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
		travelRed = new JTextField();
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
		
		//add it all together and make it visible
		frame.add(tabbedPane);
		frame.setSize(800, 750);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		//add action Listeners for the buttons
		addStopRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e ) {
				String redLine = "";
				redInput = Arrays.asList(travelRed.getText().split(","));
				travelingRed = new String[redInput.size()];
				for (int a = 0; a < travelingRed.length; a++) {
					travelingRed[a] = redInput.get(a);
				}

				String setSpeedRed = speedRed.getText().toString();
				String redNextStop = "Stop " + xRed + ": \n " + redLine + ": " + travelingRed[travelingRed.length-1] +  "\n Authority: " + travelingRed.length + "\n Set Speed: " + setSpeedRed;
				String redPrevious = stopRouteRed.getText();
				stopRouteRed.setText(redPrevious + "\n\n" + redNextStop);
				
				schedStopsRed = new int[travelingRed.length];
				for (int c = 0; c < travelingRed.length; c++) {
					schedStopsRed[c] = Integer.parseInt(travelingRed[c]);
				}
				speedRedSched = Integer.parseInt(setSpeedRed);
				xRed++;
			}
		});
		
		addStopGrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String grnLine = "";
				grnInput = Arrays.asList(travelGrn.getText().split(","));
				travelingGrn = new String[grnInput.size()];
				for (int b = 0; b < travelingGrn.length; b++) {
					travelingGrn[b] = grnInput.get(b);
				}
				String setSpeedGrn = speedGrn.getText().toString();
				String grnNextStop = "Stop " + xGrn + ": \n " + grnLine + ": " + travelingGrn[travelingGrn.length-1] + "\n Authority: " + travelingGrn.length + "\n Set Speed: " + setSpeedGrn;
				String grnPrevious = stopRouteGrn.getText();
				stopRouteGrn.setText(grnPrevious + "\n\n" + grnNextStop);
				
				schedStopsGrn = new int[travelingGrn.length];
				for (int d = 0; d < travelingGrn.length; d++) {
					schedStopsGrn[d] = Integer.parseInt(travelingGrn[d]);
				}
				speedGrnSched = Integer.parseInt(setSpeedGrn);
				xGrn++;
			}
		});
		
		schedTrainRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String redWrit = stopRouteRed.getText();
				String redToWrite = redWrit + "\n\n Scheduling Train...!";
				stopRouteRed.setText(redToWrite);
				
				addStopRed.setEnabled(false);
				schedTrainRed.setEnabled(false);
				nextTrainRed.setEnabled(true);
				
				ctcUI.tsh.addNewTrainSchedule("Red", trainID, schedStopsRed, departRed, speedRedSched);
				trainID++;
			}
		});
		
		schedTrainGrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String grnWrit = stopRouteGrn.getText();
				String grnToWrite = grnWrit + "\n\n Scheduling Train...!";
				stopRouteGrn.setText(grnToWrite);

 				addStopGrn.setEnabled(false);
				schedTrainGrn.setEnabled(false);
				nextTrainGrn.setEnabled(true);
				TrainScheduleHelper.trainTracker.add(trainID, 9999);
				ctcUI.tsh.addNewTrainSchedule("Green", trainID, schedStopsGrn, departGrn, speedGrnSched);
				TrainSchedulesUI.repaintGUI();
				trainID++;
			}
		});
		
		nextTrainRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				addStopRed.setEnabled(true);
				schedTrainRed.setEnabled(true);
				nextTrainRed.setEnabled(false);
				stopRouteRed.setText("Add a Stop...");
				xRed = 1;
				//authRed = new int[150];
				departRed = new int[150];
				schedStopsRed = new int[150];
			}
		});
		
		nextTrainGrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

 				addStopGrn.setEnabled(true);
				schedTrainGrn.setEnabled(true);
				nextTrainGrn.setEnabled(false);
				stopRouteGrn.setText("Add a Stop...");
				schedStopsGrn = new int[150];
				xGrn = 1;
				//authGrn = new int[150];
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