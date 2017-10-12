/*
* Filename: ctcUI.java
* Author: Matt Snyder
* Last Edited: 10/12/2017
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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class trainScheduler extends JFrame {
	//create two arrays for demo purpose. actual will have the list imported from the database
	String[] grnLineStops = {"Choose a stop...", "A1", "A2", "A3", "A4", "B1", "B2", "B3", "C1", "C2"};
	String[] redLineStops = {"Choose a stop...", "A1", "A2", "A3", "B1", "B2", "B3", "B4", "B5", "C1"};
	int x = 1; //number of stops
	int auth[] = new int[99];
	//double depart[] = new double[99];
	
	private JFrame frame = new JFrame("Schedule A Train");
	private JButton addStop = new JButton("Add A Stop");
	private JButton schedTrain = new JButton("Schedule Train");
	private JComboBox<String> stops = new JComboBox<String>(grnLineStops);
	
	public trainScheduler() {
		render();
	}
	
	public void render() {
		auth[0] = 0;
	
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.setBorder(BorderFactory.createLineBorder(Color.black,1));
		//right panel
		JPanel rightPanel = new JPanel(new GridLayout(5,1));
		JPanel leftPanel = new JPanel();
		JTextArea stopRoute = new JTextArea(50, 40);
		//left panel
		
		stopRoute.setText("Add a Stop....");
		stopRoute.setEditable(false);
		leftPanel.add(stopRoute);
				
		panel.add(leftPanel);
		
		JPanel radioBtns = new JPanel();
		JRadioButton green = new JRadioButton("Green Line");
		JRadioButton red = new JRadioButton("Red Line");
		ButtonGroup line = new ButtonGroup();
		line.add(green);
		line.add(red);
		red.setBackground(Color.red);
		green.setBackground(Color.green);
		green.setSelected(true);
		radioBtns.setBackground(Color.green);
		
		green.setMnemonic(KeyEvent.VK_G);
		green.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (green.isSelected()) {
					radioBtns.setBackground(Color.green);
					//stops.setModel(grnLineStops); //find out why I can't change the data
				}
			}
		});
		
		red.setMnemonic(KeyEvent.VK_R);
		red.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (red.isSelected()) {
					radioBtns.setBackground(Color.red);
				}
			}
		});
		
		radioBtns.add(green);
		radioBtns.add(red);
		rightPanel.add(radioBtns);
		
		stops.setEditable(false);
		rightPanel.add(stops);
		
		JPanel departure = new JPanel(new GridLayout(1,5));
		JTextField hour1 = new JTextField(1);
		JTextField hour0 = new JTextField(1);
		JTextField split = new JTextField(3);
		JTextField mins1 = new JTextField(1);
		JTextField mins0 = new JTextField(1);
		hour1.setText("0");
		hour0.setText("0");
		split.setText(" : ");
		split.setEditable(false);
		mins1.setText("0");
		mins0.setText("0");
		departure.add(hour1);
		departure.add(hour0);
		departure.add(split);
		departure.add(mins1);
		departure.add(mins0);
		rightPanel.add(departure);
		
		rightPanel.add(addStop);
		
		addStop.setMnemonic(KeyEvent.VK_A);
		addStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String line = "";
				if (green.isSelected()) {
					line = "Green Line";
				} else {
					line = "Red Line";
				}
				int nextAuth = Math.abs(stops.getSelectedIndex() - auth[x-1]);
				auth[x] = stops.getSelectedIndex();
				String stop = stops.getSelectedItem().toString();
				String depart = (hour1.getText() + hour0.getText() + ":" + mins1.getText() + mins0.getText());
				String nextStop = "Stop " + x + ": \n " + line + ": " + stop + " \n Departure: " + depart + "\n Authority: " + nextAuth + " \n Suggested Speed: MAX";
				String previous =  stopRoute.getText();
				stopRoute.setText(previous + "\n\n" + nextStop);
				x++;
			}
		});
		
		rightPanel.add(schedTrain);
		schedTrain.setMnemonic(KeyEvent.VK_S);
		schedTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String writ = stopRoute.getText();
				String toWrite = writ + "\n\n Scheduling Train...!";
				stopRoute.setText(toWrite);
				stops.setEnabled(false);
				hour1.setEnabled(false);
				hour0.setEnabled(false);
				mins1.setEnabled(false);
				mins0.setEnabled(false);
				addStop.setEnabled(false);
				schedTrain.setEnabled(false);
			}
		});
		
		panel.add(rightPanel);
		
		frame.add(panel);
		frame.setSize(800, 700);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	
}