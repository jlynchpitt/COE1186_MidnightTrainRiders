/*
* Filename: repairScheduler.java
* Author: Matt Snyder
* Last Edited: 12/06/2017
* File Description: This contains the Repair Scheduler. 
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
import java.util.ArrayList;
import java.util.List;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;
import javax.swing.*;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.CTCSrc.repairScheduler;

public class repairSchedulerUI extends JFrame {
	
	private JFrame frame = new JFrame("Schedule A Repair");
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JButton closeBtnRed = new JButton("Close");
	private JButton closeBtnGrn = new JButton("Close");
	private JButton openBtnRed = new JButton("Open");
	private JButton openBtnGrn = new JButton("Open");
	//for demo purposes only, full version will pull from database
	private String[] greenLineBlocks = {"A1","B1","C1","D1","E1","F1","G1","H1","I1","J1","K1","L1","M1"}; //fall back data
	private String[] redLineBlocks = {"A1","B1","C1","D1","E1","F1","G1","H1","I1","J1","K1"};
	private String[] importedRed = new String[1];
	private String[] importedGrn = new String[1];
	private List<String> imporRed = new ArrayList<String>();
	private List<String> imporGrn = new ArrayList<String>();
	//private String[] greenLineSections = {"1","2","3","4","5","6","7","8","9","10","11"};
	//private String[] redLineSections = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
	private String[] repairTypes = {"Broken Rail","Broken Track Heater", "Stuck Train: Engine Failure", "Stuck Train: Break Failure"};
	private JComboBox<String> blockGrn = new JComboBox(greenLineBlocks);
	private JComboBox<String> blockRed = new JComboBox(redLineBlocks);
	private JComboBox<String> grnStopsImported;
	private JComboBox<String> redStopsImported;
	private JComboBox<String> repairChoiceRed = new JComboBox(repairTypes);
	private JComboBox<String> repairChoiceGrn = new JComboBox(repairTypes);
	static DBHelper database = MainMTR.getDBHelper();
	boolean loaded = false;
	
	public void loadComboBoxes() {
		//parameters for test.csv
		int j = 0;
		int k = 0;
		int dataLength = database.getDatabaseSize();
		System.out.println("Rows: " + dataLength);
		for (int i = 0; i < dataLength+1; i++) { //change to end of file?
			System.out.println("Loading...");
			String text = Integer.toString(database.getTrackID(i));
			String line = database.getColor(i);
			if (line.equals("green")) {
				imporGrn.add(text);
				System.out.println("ID: " + text);
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
	
	public repairSchedulerUI() {
		render();
	}
	
	public void render() {
		JPanel redPanel = new JPanel();
		JPanel grnPanel = new JPanel();
		redPanel.setLayout(new GridBagLayout());
		grnPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbcRed = new GridBagConstraints();
		GridBagConstraints gbcGrn = new GridBagConstraints();
		loadComboBoxes();
		
		JLabel redRepairs = new JLabel("Red Line Repairs: ");
		gbcRed.gridx = 0;
		gbcRed.gridy = 0;
		gbcRed.insets = new Insets (0,0,5,0);
		gbcRed.fill = GridBagConstraints.HORIZONTAL;
		gbcRed.gridwidth = 1;
		redPanel.add(redRepairs, gbcRed);
		
		gbcRed.gridx = 1;
		gbcRed.gridy = 0;
		redPanel.add(repairChoiceRed,gbcRed);
		
		JLabel grnRepairs = new JLabel("Green Line Repairs: ");
		gbcGrn.gridx = 0;
		gbcGrn.gridy = 0;
		gbcGrn.insets = new Insets (0,0,5,0);
		gbcGrn.fill = GridBagConstraints.HORIZONTAL;
		gbcGrn.gridwidth = 1;
		grnPanel.add(grnRepairs, gbcGrn);
		
		gbcGrn.gridx = 1;
		gbcGrn.gridy = 0;
		grnPanel.add(repairChoiceGrn,gbcGrn);
		
		
		JLabel blockChoiceR = new JLabel("Choose a section: ");
		gbcRed.gridx = 0;
		gbcRed.gridy = 1;
		gbcRed.gridwidth = 1;
		redPanel.add(blockChoiceR,gbcRed);
		
		gbcRed.gridx = 1;
		gbcRed.gridy = 1;
		if (loaded == false) {
			redPanel.add(blockRed,gbcRed);
		} else {
			redPanel.add(redStopsImported, gbcRed);
		}
		
		gbcRed.gridx = 0;
		gbcRed.gridy = 2;
		closeBtnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int closed = repairScheduler.openRepairJob(Integer.parseInt(redStopsImported.getSelectedItem().toString()), repairChoiceRed.getSelectedItem().toString());
				if (closed == 0) {
					System.out.println("Already closed");
				} else if (closed == 1) {
					System.out.println("Closed succesfully");
					SystemTray tray = SystemTray.getSystemTray();
					Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
					TrayIcon trayIcon = new TrayIcon(image, "Close Track Success");
					trayIcon.setImageAutoSize(true);
					trayIcon.setToolTip("Closing of Track ID: " + redStopsImported.getSelectedItem().toString());
					try {
						tray.add(trayIcon);
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					trayIcon.displayMessage("Track Closed", "Track " + redStopsImported.getSelectedItem().toString() + " is closed.", MessageType.INFO);

				}
			}			
		});
		redPanel.add(closeBtnRed,gbcRed);
		
		gbcRed.gridx = 1;
		gbcRed.gridy = 2;
		openBtnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opened = repairScheduler.closeRepairJob(Integer.parseInt(redStopsImported.getSelectedItem().toString()), repairChoiceRed.getSelectedItem().toString());
				if (opened == 0) {
					System.out.println("Already open");
				} else if (opened == 1) {
					System.out.println("Opened succesfully");
					SystemTray tray = SystemTray.getSystemTray();
					Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
					TrayIcon trayIcon = new TrayIcon(image, "Open Track Success");
					trayIcon.setImageAutoSize(true);
					trayIcon.setToolTip("Openinging of Track ID: " + redStopsImported.getSelectedItem().toString());
					try {
						tray.add(trayIcon);
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					trayIcon.displayMessage("Track Opened", "Track " + redStopsImported.getSelectedItem().toString() + " is opened.", MessageType.INFO);

				}
			}
		});
		redPanel.add(openBtnRed,gbcRed);
		//////////////////////////////////////////////
		
		JLabel blockChoiceG =  new JLabel("Choose a section: ");
		gbcGrn.gridx = 0;
		gbcGrn.gridy = 1;
		gbcGrn.gridwidth = 1;
		grnPanel.add(blockChoiceG,gbcGrn);
		
		gbcGrn.gridx = 1;
		gbcGrn.gridy = 1;
		if (loaded == false) {
			grnPanel.add(blockGrn,gbcGrn);
		} else {
			grnPanel.add(grnStopsImported, gbcGrn);
		}
		
		gbcGrn.gridx = 0;
		gbcGrn.gridy = 2;
		closeBtnGrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int closed = repairScheduler.openRepairJob(Integer.parseInt(grnStopsImported.getSelectedItem().toString()), repairChoiceGrn.getSelectedItem().toString());
				if (closed == 0) {
					System.out.println("Already closed");
				} else if (closed == 1) {
					System.out.println("Closed succesfully");
					SystemTray tray = SystemTray.getSystemTray();
					Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
					TrayIcon trayIcon = new TrayIcon(image, "Close Track Success");
					trayIcon.setImageAutoSize(true);
					trayIcon.setToolTip("Closing of Track ID: " + grnStopsImported.getSelectedItem().toString());
					try {
						tray.add(trayIcon);
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					trayIcon.displayMessage("Track Closed", "Track " + grnStopsImported.getSelectedItem().toString() + " is closed.", MessageType.INFO);

				}
			}
		});
		grnPanel.add(closeBtnGrn,gbcGrn);
		
		gbcGrn.gridx = 1;
		gbcGrn.gridy = 2;
		openBtnGrn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opened = repairScheduler.closeRepairJob(Integer.parseInt(grnStopsImported.getSelectedItem().toString()), repairChoiceGrn.getSelectedItem().toString());
				if (opened == 0) {
					System.out.println("Already open");
				} else if (opened == 1) {
					System.out.println("Opened succesfully");
					SystemTray tray = SystemTray.getSystemTray();
					Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
					TrayIcon trayIcon = new TrayIcon(image, "Open Track Success");
					trayIcon.setImageAutoSize(true);
					trayIcon.setToolTip("Openinging of Track ID: " + grnStopsImported.getSelectedItem().toString());
					try {
						tray.add(trayIcon);
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					trayIcon.displayMessage("Track Opened", "Track " + grnStopsImported.getSelectedItem().toString() + " is opened.", MessageType.INFO);

				}
			}
		});
		grnPanel.add(openBtnGrn,gbcGrn);
		
		tabbedPane.addTab("Red Line",redPanel);
		tabbedPane.addTab("Green Line", grnPanel);
		
		frame.add(tabbedPane);
		frame.setSize(400, 350);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
	}
}