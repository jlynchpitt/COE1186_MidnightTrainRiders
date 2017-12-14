/*
* Filename: ctcUI.java
* Author: Matt Snyder
* Last Edited: 12/12/2017
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

package MTR.NorthShoreExtension.UI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.text.*;
import static java.lang.Math.toIntExact;

import MTR.NorthShoreExtension.UI.*;
import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.*;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.CTCSrc.*;

public class ctcUI {
	
	//Specify the look and feel to use. Valid values: null (default), "Metal", "System",
	//"Motif", "GTK+"
	static DBHelper database = MainMTR.getDBHelper();
	
	final static String LOOKANDFEEL = "System";
	final static boolean shouldFill = true; //used in testing GridBagLayout
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	
	private static JPanel pane;
	private static JFrame frame;
	public static ctcUI ctc1;
	public static JTextArea time;
	public static JTextArea ambTemp;
	public static JTextArea numTrn;
	public static JPanel ambientTemp;
	public static JTextArea thrhput;
	
	int timeMultiplier;
	TrainControllerHelper tch;
	static long timeAsLong = 0;
	static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	static String simulationTime = sdf.format(timeAsLong);
	static int tempF = 0;
	static int numTrains = 0;
	static int throughput = 0;
	public static int runModeSwitch = 0; //0 for manual, 1 for automatic
	public static TrainScheduleHelper tsh;
	static int tempTracker = 0;
	static long startTime = TrainControllerHelper.programStartTime; //for calculating throughput
	
	public static int getRunMode() {
		return runModeSwitch;
	}
	
	public static TrainScheduleHelper getTrainScheduleHelper() {
		return tsh;
	}
	
	public int getTemp( ) {
		return tempF;
	}
	
	public static void setTemp(int temp) {
		tempF = temp;
		ambTemp.setText(temp + " F");
		if (tempF <= 39) {
			ambTemp.setBackground(Color.red);
			ambientTemp.setBackground(Color.red);
			//turn the heaters on for all the stations
			database.updateHeater(2002,"ON");
			database.updateHeater(2009,"ON");
			database.updateHeater(2016,"ON");
			database.updateHeater(2022,"ON");
			database.updateHeater(2031,"ON");
			database.updateHeater(2039,"ON");
			database.updateHeater(2048,"ON");
			database.updateHeater(2057,"ON");
			database.updateHeater(2065,"ON");
			database.updateHeater(2073,"ON");
			database.updateHeater(2077,"ON");
			database.updateHeater(2088,"ON");
			database.updateHeater(2096,"ON");
			database.updateHeater(2105,"ON");
			database.updateHeater(2114,"ON");
			database.updateHeater(2123,"ON");
			database.updateHeater(2132,"ON");
			database.updateHeater(2141,"ON");
			database.updateHeater(1007,"ON");
			database.updateHeater(1015,"ON");
			database.updateHeater(1021,"ON");
			database.updateHeater(1025,"ON");
			database.updateHeater(1035,"ON");
			database.updateHeater(1045,"ON");
			database.updateHeater(1048,"ON");
			database.updateHeater(1060,"ON");
		} else if (tempF >= 40) {
			ambTemp.setBackground(Color.green);
			ambientTemp.setBackground(Color.green);
			//turn the heaters off for all the stations
			database.updateHeater(2002,"Off");
			database.updateHeater(2009,"Off");
			database.updateHeater(2016,"Off");
			database.updateHeater(2022,"Off");
			database.updateHeater(2031,"Off");
			database.updateHeater(2039,"Off");
			database.updateHeater(2048,"Off");
			database.updateHeater(2057,"Off");
			database.updateHeater(2065,"Off");
			database.updateHeater(2073,"Off");
			database.updateHeater(2077,"Off");
			database.updateHeater(2088,"Off");
			database.updateHeater(2096,"Off");
			database.updateHeater(2105,"Off");
			database.updateHeater(2114,"Off");
			database.updateHeater(2123,"Off");
			database.updateHeater(2132,"Off");
			database.updateHeater(2141,"Off");
			database.updateHeater(1007,"Off");
			database.updateHeater(1015,"Off");
			database.updateHeater(1021,"Off");
			database.updateHeater(1025,"Off");
			database.updateHeater(1035,"Off");
			database.updateHeater(1045,"Off");
			database.updateHeater(1048,"Off");
			database.updateHeater(1060,"Off");		
		}
	}
	
	public int getNumTrains() {
		return numTrains;
	}
	
	public static void setNumTrains(int trains) {
		numTrains = trains;
		numTrn.setText("Trains: " + numTrains);
	}
	
	public int getThroughput() {
		return throughput;
	}
	
	public static void setThroughput(int through) {
		throughput = through;
		System.out.println("Through" + throughput);
		thrhput.setText(throughput + " passengers/hour");
		long timey = timeAsLong - startTime;
		long div = timey/3600000;
		System.out.println("Result: " + div);
		if (div >= 1 ) {
			int divisor = toIntExact(div);
			//divide by the number of hours elapsed
			throughput = throughput/divisor;
			thrhput.setText(throughput + " passengers/hour");
		} else {
			//nothing
		}
	}
	
	public static void setTime(long givenTime) {
		timeAsLong = givenTime;
		simulationTime = sdf.format(timeAsLong);
		if(time != null) {
			time.setText(simulationTime);
		}
		//have temperature change every 5 minutes
		if (tempTracker < 300 ) {
			tempTracker++;
		} else {
			Random rand = new Random();
			int randvalue = rand.nextInt(7);
			int adjuster = 3 - randvalue;
			System.out.println("Change in Temp: " + adjuster);
			tempF = tempF + adjuster;
			System.out.println("New Temp: " + tempF);
			setTemp(tempF);
			tempTracker = 0;
			ambTemp.setText(tempF + " F");
//			if (tempF <= 39) {
//				ambTemp.setBackground(Color.red);
//				ambientTemp.setBackground(Color.red);
//			} else if (tempF >= 40) {
//				ambTemp.setBackground(Color.green);
//				ambientTemp.setBackground(Color.green);
//			}
		}
	}
	
	//public static void ctcUI(Container pane) {
	  public ctcUI() {
		  pane = new JPanel();
		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}
		/*Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String currentTime = sdf.format(cal.getTime());*/
		
		JButton schedTrain, schedRepair, reporting, trnInfo, trnCtrl, trkCtrl, timeMult, switchTest, schedules, engineerCtrls;
		JPanel runningMode, thrput, trainNum, currTime, trkModel;
		tempF = 37;
		numTrains = 0;
		throughput = 0;
		timeMultiplier = 1;
		tsh = new TrainScheduleHelper();
		tch = MainMTR.getTrainControllerHelper();
		
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
				int running = ctcUI.getRunMode();
				System.out.println("Run Mode: " + running);
				if (running == 1) { //Automatic mode
					System.out.println("Automatic mode");
					JFrame schedulerT = new TrainSchedulerUI();
					//TrainSchedulerUI.createAndShowGUI();
				} else if (running == 0) { //Manual mode
					System.out.println("Manual mode");
					JFrame schedulerT = new TrainSchedulerManualUI();
					//TrainSchedulerManualUI.createAndShowGUI();
				}
			}
		});
		gbc.weightx = 0.0; //sets the width of the segment
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0; //sets the location in the GridBag with 0,0 being the upper left-hand corner
		gbc.gridy = 5;
		gbc.gridwidth = 1;  //1 column wide
		pane.add(schedTrain, gbc);
		
		schedRepair = new JButton("Schedule Repair");
		schedRepair.setActionCommand("repairTrain");
		schedRepair.setMnemonic(KeyEvent.VK_P);
		schedRepair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame schedulerR = new repairSchedulerUI();
			}
		});
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.gridx = 2;
		gbc.gridy = 5;
		pane.add(schedRepair, gbc);
		
		reporting = new JButton("Other Testing");
		reporting.setActionCommand("test");
		reporting.setMnemonic(KeyEvent.VK_R);
		reporting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OtherTestingUI.createAndShowGUI();
			}
		});
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		
		gbc.gridx = 4;
		gbc.gridy = 5;
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
		man.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				System.out.println("Das Manual Mode");
				runModeSwitch = 0;
				System.out.println(runModeSwitch);
			}
		});
		man.setSelected(true);
		
		auto.setMnemonic(KeyEvent.VK_A);
		auto.setActionCommand("automatic");
		auto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Das Auto Mode");
				runModeSwitch = 1;
				System.out.println(runModeSwitch);
			}
		});
		
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
		thrhput = new JTextArea(1, 20);
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
		numTrn = new JTextArea(1,10);
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
		time = new JTextArea(1,12);
		time.setEditable(false);
		time.setText(simulationTime);
		currTime.add(time);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		pane.add(currTime, gbc);
		//--------------
		
		//temperature
		ambientTemp = new JPanel();
		ambientTemp.setBorder(BorderFactory.createLineBorder(Color.black,1));
		ambTemp = new JTextArea(1,5);
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
		
		timeMult = new JButton("Time Mult.: 1x");
		gbc.gridx = 0;
		gbc.gridy = 1;
		pane.add(timeMult, gbc);
		timeMult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (timeMultiplier == 1) {
					timeMultiplier = 2;
					timeMult.setText("Time Mult.: 2x");
				} else if (timeMultiplier == 2) {
					timeMultiplier = 4;
					timeMult.setText("Time Mult.: 4x");
				} else if (timeMultiplier == 4) {
					timeMultiplier = 10;
					timeMult.setText("Time Mult.: 10x");
				} else if (timeMultiplier == 10) {
					timeMultiplier = 1;
					timeMult.setText("Time Mult.: 1x");
				}
				tch.TrainControlHelper_setTimeMultiplier(timeMultiplier);
			}
		});
		
		trnInfo = new JButton("Train Model");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1; 
		gbc.gridx = 1;
		gbc.gridy = 1; // 5th row
		trnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(MainMTR.getTrainModelUI()!=null) {
					TrainModelUI tmUI= MainMTR.getTrainModelUI();
			        tmUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					tmUI.setVisible(true);
				}
			}
		});
		pane.add(trnInfo, gbc);
		
		trnCtrl = new JButton("Train Controls");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 1;
		trnCtrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrainControlUI.createAndShowTrainControlGUI(null);
			}
		});
		pane.add(trnCtrl,  gbc);
		
		trkCtrl = new JButton("Wayside Controls");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 3; 
		gbc.gridwidth = 1; 
		gbc.gridy = 1; //fifth row
		trkCtrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					WaysideControllerUI.createAndShowWaysideControlGUI();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		pane.add(trkCtrl, gbc);
		
		//trackModel Panel
		trkModel = new JPanel();
		trkModel.setBorder(BorderFactory.createLineBorder(Color.black,1));
		BufferedImage image;
		try {
			image = ImageIO.read(new File("TrackLayout.png"));
		} catch (IOException ex) {
			image = null;
		}
		JLabel picLabel = new JLabel(new ImageIcon(image));
		trkModel.add(picLabel);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 20;
		gbc.ipadx = 20;
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 5;
		gbc.gridheight = 3;
		pane.add(trkModel, gbc);
		//--------------
		
		switchTest = new JButton("Test Switches");
		gbc.gridx = 3;
		gbc.gridy = 5;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		switchTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFrame tester = new switchTesterUI();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		pane.add(switchTest, gbc);
	
		schedules = new JButton("Train Schedules");
		gbc.gridx = 1;
		gbc.gridy = 5;
		schedules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrainSchedulesUI.createAndShowGUI(tsh);
			}
		});
		pane.add(schedules,gbc);
		
		engineerCtrls = new JButton("Engineer Controls");
		gbc.gridx = 4;
		gbc.gridy = 1;
		engineerCtrls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrainEngineerUI.createAndShowTrainEngineerGUI(null);
			}
		});
		pane.add(engineerCtrls,gbc);
		
		pane.setSize(850, 500);
		
		System.out.println("Results: " + getTemp() + getNumTrains() + getThroughput());
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
		
		static void formAndRenderGUI() {
			initLookAndFeel();
			
			//setup the window
			frame = new JFrame("CTC");
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//ctcUI(frame.getContentPane());
			ctc1 = new ctcUI();
			ctcUI.pane.setOpaque(true);
			frame.setContentPane(ctc1.pane);
			
			frame.pack(); //this conforms the size of the frame to the size of the contents
			//frame.setSize(850, 500); //Need to find ideal size after syncing with Track Model?
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