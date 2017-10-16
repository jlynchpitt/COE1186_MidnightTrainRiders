/*
* Filename: ctcUI.java
* Author: Matt Snyder
* Last Edited: 10/12/2017
* File Description: This contains the main frame for the CTC GUI. 
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

//package MTR.NorthShoreExtension.UI;

package MTR.NorthShoreExtension.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.time.*;
import java.text.*;

public class ctcUI {
	
	//Specify the look and feel to use. Valid values: null (default), "Metal", "System",
	//"Motif", "GTK+"
	
	final static String LOOKANDFEEL = "System";
	final static boolean shouldFill = true; //used in testing GridBagLayout
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	
	private JPanel mainPane;
	
	public static void ctcUI(Container pane) {
		
		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String currentTime = sdf.format(cal.getTime());
		
		JButton schedTrain, schedRepair, reporting, trnInfo, trnCtrl, trkCtrl, timeMult;
		JPanel runningMode, thrput, trainNum, ambientTemp, currTime, trkModel;
		int tempF = 56;
		int numTrains = 0;
		int throughput = 0;
		
		pane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		if (shouldFill) {
			//natural height, max width
			gbc.fill = GridBagConstraints.HORIZONTAL;
		}
		gbc.insets = new Insets(0,0,5,0);
		//set up the first button
		schedTrain = new JButton("Schedule Train");
		schedTrain.setMnemonic(KeyEvent.VK_T);
		schedTrain.setActionCommand("schedTrain");
		schedTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame schedulerT = new TrainSchedulerUI();
			}
		});
		gbc.weightx = 0.0; //sets the width of the segment
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 4; //sets the location in the GridBag with 0,0 being the upper left-hand corner
		gbc.gridy = 1;
		gbc.gridwidth = 1;  //1 column wide
		pane.add(schedTrain, gbc);
		
		schedRepair = new JButton("Schedule Repair");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.gridx = 4;
		gbc.gridy = 2;
		pane.add(schedRepair, gbc);
		
		reporting = new JButton("Reporting");
		reporting.setActionCommand("report");
		reporting.setMnemonic(KeyEvent.VK_R);
		reporting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame reportingWindow = new ReportingMenuUI();
			}
		});
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		
		gbc.gridx = 4;
		gbc.gridy = 3;
		pane.add(reporting, gbc);		
		
		//create the section for the Run Mode selection
		runningMode = new JPanel();
		runningMode.setBorder(BorderFactory.createLineBorder(Color.black,1));
		JRadioButton auto = new JRadioButton("Auto");
		JRadioButton man = new JRadioButton("Manual");
		ButtonGroup runMode = new ButtonGroup();
		runMode.add(auto);
		runMode.add(man);

		man.setMnemonic(KeyEvent.VK_M);
		man.setActionCommand("manual");
		man.setSelected(true);
		
		auto.setMnemonic(KeyEvent.VK_A);
		auto.setActionCommand("automatic");
		
		runningMode.add(auto);
		runningMode.add(man);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		pane.add(runningMode, gbc);
		//----------
		

		//throughput section
		thrput = new JPanel();
		thrput.setBorder(BorderFactory.createLineBorder(Color.black,1));
		JTextArea thrhput = new JTextArea(1, 20);
		thrhput.setEditable(false);
		thrhput.setText(throughput + " passengers/hour");
		thrput.add(thrhput);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		pane.add(thrput, gbc);
		//-----------------
		
		//number of trains
		trainNum = new JPanel();
		trainNum.setBorder(BorderFactory.createLineBorder(Color.black,1));
		JTextArea numTrn = new JTextArea(1,10);
		numTrn.setEditable(false);
		numTrn.setText("Trains: " + numTrains);
		trainNum.add(numTrn);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 0;
		pane.add(trainNum, gbc);
		//---------------
		
		//time
		currTime = new JPanel();
		currTime.setBorder(BorderFactory.createLineBorder(Color.black));
		JTextArea time = new JTextArea(1,12);
		time.setEditable(false);
		time.setText(currentTime);
		currTime.add(time);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		pane.add(currTime, gbc);
		//--------------
		
		//temperature
		ambientTemp = new JPanel();
		ambientTemp.setBorder(BorderFactory.createLineBorder(Color.black,1));
		JTextArea ambTemp = new JTextArea(1,5);
		ambTemp.setEditable(false);
		ambTemp.setText(tempF + " F");
		if (tempF <= 39) {
			ambTemp.setBackground(Color.red);
			ambientTemp.setBackground(Color.red);
		} else if (tempF >= 40) {
			ambTemp.setBackground(Color.green);
			ambientTemp.setBackground(Color.green);
		}
		ambientTemp.add(ambTemp);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 4;
		gbc.gridy = 0;
		pane.add(ambientTemp, gbc);
		//-----------------
		
		timeMult = new JButton("Time Mult.: 10x");
		gbc.gridx = 0;
		gbc.gridy = 4;
		pane.add(timeMult, gbc);
		
		trnInfo = new JButton("Train Info");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1; 
		gbc.gridx = 1;
		gbc.gridy = 4; // 5th row
		pane.add(trnInfo, gbc);
		
		trnCtrl = new JButton("Train Controls");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy = 4;
		pane.add(trnCtrl,  gbc);
		
		trkCtrl = new JButton("Track Controls");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 2; 
		gbc.gridwidth = 1; //2 columns wide
		gbc.gridy = 4; //fifth row
		
		pane.add(trkCtrl, gbc);
		
		//trackModel Panel
		trkModel = new JPanel();
		trkModel.setBorder(BorderFactory.createLineBorder(Color.black,1));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.ipady = 200;
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 4;
		gbc.gridheight = 3;
		pane.add(trkModel, gbc);
		//--------------
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
				} else if (LOOKANDFEEL.equals("GTK+")) {
					lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
				} else {
					System.err.println("Unexpected value of LOOKANDFEEL specified: " + LOOKANDFEEL);
				}
				
				try {
					UIManager.setLookAndFeel(lookAndFeel);
				} catch (ClassNotFoundException e) {
					System.err.println("Couldn't find class for specified look and feel: " + lookAndFeel);
					System.err.println("Did you include the L&F library in the class path?");
					System.err.println("Using the default look and feel.");		
				} catch (UnsupportedLookAndFeelException e) {
					System.err.println("Can't use the specified look and feel " + lookAndFeel + " on this platform.");
					System.err.println("Using the default look and feel.");
				} catch (Exception e) {
					System.err.println("Couldn't get specified look and feel " + lookAndFeel + " for some reason.");
					System.err.println("Using the default look and feel.");
					e.printStackTrace();
				}
			}
		}
		
		private static void formAndRenderGUI() {
			initLookAndFeel();
			
			//setup the window
			JFrame frame = new JFrame("CTC");
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			ctcUI(frame.getContentPane());
			
			frame.pack();
			//frame.setSize(850, 500); Need to find ideal size after syncing with Track Model
			frame.setVisible(true);
		}
		
		public static void main(String[] args) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					formAndRenderGUI();
				}
			});
		}
	
}