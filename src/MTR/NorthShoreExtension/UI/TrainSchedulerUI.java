<<<<<<< HEAD
/*
* Filename: trainScheduler.java
* Author: Matt Snyder
* Last Edited: 11/7/2017
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

public class TrainSchedulerUI extends JFrame {
	//create two arrays for demo purpose. actual will have the list imported from the database
	String[] grnLineStops = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "C1", "C2"};
	String[] redLineStops = {"A1", "A2", "A3", "B1", "B2", "B3", "B4", "B5", "C1"};
	int xRed = 1; //number of grnStops
	int xGrn = 1;
	int authRed[] = new int[150];
	int authGrn[] = new int[150];
	//double depart[] = new double[150];
	
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
	
	public TrainSchedulerUI() {
		render();
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
		grnStopChoice.add(grnStops);
		grnRght.add(grnStopChoice);
		
		JPanel redStopChoice = new JPanel(new GridLayout(2,1));
		JLabel redChoice = new JLabel("Choose a stop: ");
		redStopChoice.add(redChoice);
		redStopChoice.add(redStops);
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
		tabbedPane.addTab("Schedules", schedule);
		
		//add it all together and make it visible
		frame.add(tabbedPane);
		frame.setSize(800, 750);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		//add action Listeners for the buttons
		addStopRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e ) {
				String redLine = "";
				int nextAuthRed = Math.abs(redStops.getSelectedIndex() - authRed[xRed-1]);
				authRed[xRed] = redStops.getSelectedIndex();
				String redStop = redStops.getSelectedItem().toString();
				String redDepart = (redHour1.getText() + redHour0.getText() + ":" + redMins1.getText() + redMins0.getText());
				String redNextStop = "Stop " + xRed + ": \n " + redLine + ": " + redStop + " \n Departure: " + redDepart + "\n Authority: " + nextAuthRed + "\n Suggested Speed: MAX";
				String redPrevious = stopRouteRed.getText();
				stopRouteRed.setText(redPrevious + "\n\n" + redNextStop);
				xRed++;
			}
		});
		
		addStopGrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String grnLine = "";
				int nextAuthGrn = Math.abs(grnStops.getSelectedIndex() - authGrn[xGrn-1]);
				authGrn[xGrn] = grnStops.getSelectedIndex();
				String grnStop = grnStops.getSelectedItem().toString();
				String grnDepart = (grnHour1.getText() + grnHour0.getText() + ":" + grnMins1.getText() + grnMins0.getText());
				String grnNextStop = "Stop " + xGrn + ": \n " + grnLine + ": " + grnStop + " \n Departure: " + grnDepart + "\n Authority: " + nextAuthGrn + "\n Suggested Speed: MAX";
				String grnPrevious = stopRouteGrn.getText();
				stopRouteGrn.setText(grnPrevious + "\n\n" + grnNextStop);
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
			}
		});
	}
=======
/*
* Filename: trainScheduler.java
* Author: Matt Snyder
* Last Edited: 11/7/2017
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

public class TrainSchedulerUI extends JFrame {
	//create two arrays for demo purpose. actual will have the list imported from the database
	String[] grnLineStops = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "C1", "C2"};
	String[] redLineStops = {"A1", "A2", "A3", "B1", "B2", "B3", "B4", "B5", "C1"};
	int xRed = 1; //number of grnStops
	int xGrn = 1;
	int authRed[] = new int[150];
	int authGrn[] = new int[150];
	//double depart[] = new double[150];
	
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
	
	public TrainSchedulerUI() {
		render();
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
		grnStopChoice.add(grnStops);
		grnRght.add(grnStopChoice);
		
		JPanel redStopChoice = new JPanel(new GridLayout(2,1));
		JLabel redChoice = new JLabel("Choose a stop: ");
		redStopChoice.add(redChoice);
		redStopChoice.add(redStops);
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
		tabbedPane.addTab("Schedules", schedule);
		
		//add it all together and make it visible
		frame.add(tabbedPane);
		frame.setSize(800, 750);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		//add action Listeners for the buttons
		addStopRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e ) {
				String redLine = "";
				int nextAuthRed = Math.abs(redStops.getSelectedIndex() - authRed[xRed-1]);
				authRed[xRed] = redStops.getSelectedIndex();
				String redStop = redStops.getSelectedItem().toString();
				String redDepart = (redHour1.getText() + redHour0.getText() + ":" + redMins1.getText() + redMins0.getText());
				String redNextStop = "Stop " + xRed + ": \n " + redLine + ": " + redStop + " \n Departure: " + redDepart + "\n Authority: " + nextAuthRed + "\n Suggested Speed: MAX";
				String redPrevious = stopRouteRed.getText();
				stopRouteRed.setText(redPrevious + "\n\n" + redNextStop);
				xRed++;
			}
		});
		
		addStopGrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String grnLine = "";
				int nextAuthGrn = Math.abs(grnStops.getSelectedIndex() - authGrn[xGrn-1]);
				authGrn[xGrn] = grnStops.getSelectedIndex();
				String grnStop = grnStops.getSelectedItem().toString();
				String grnDepart = (grnHour1.getText() + grnHour0.getText() + ":" + grnMins1.getText() + grnMins0.getText());
				String grnNextStop = "Stop " + xGrn + ": \n " + grnLine + ": " + grnStop + " \n Departure: " + grnDepart + "\n Authority: " + nextAuthGrn + "\n Suggested Speed: MAX";
				String grnPrevious = stopRouteGrn.getText();
				stopRouteGrn.setText(grnPrevious + "\n\n" + grnNextStop);
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
			}
		});
	}
>>>>>>> 90efc8770b634015b8fd4a24498eb21419da4956
}