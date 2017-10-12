import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.AbstractListModel;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.Choice;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TrainmodelUI {
	private static Train[] tr;

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		tr = new Train[2];
		tr[0] = new Train();
		tr[1] =new Train();
		
		tr[0].setAcceleration(0.3);
		tr[0].setAuthority("Block 1");
		tr[0].setVelocity(20);
		tr[0].setDeceleration(0);
		tr[0].setPassengermass(2000);
		tr[0].setTemperature(77);
		tr[0].setPassengers(16);
		tr[0].setCrewcount(2);
		tr[0].setOpendoors(false);
		tr[0].setLightson(true);
		tr[0].setMaxacceleration(1);
		tr[0].setMaxdeceleration(1.2);
		tr[0].setMaxpassengers(148);
		tr[1].setAcceleration(0);
		tr[1].setAuthority("Block 5");
		tr[1].setVelocity(50);
		tr[1].setDeceleration(0);
		tr[1].setPassengermass(4000);
		tr[1].setTemperature(77);
		tr[1].setPassengers(32);
		tr[1].setCrewcount(2);
		tr[1].setOpendoors(false);
		tr[1].setLightson(false);
		tr[1].setMaxacceleration(1);
		tr[1].setMaxdeceleration(1.2);
		tr[1].setMaxpassengers(148);
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					TrainmodelUI window = new TrainmodelUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TrainmodelUI() {
		System.out.println(tr[1].getAcceleration());
		
		initialize(0);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int i) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 150, 150, 150, 150, 150, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Train 1", "Train 2"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 2;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		
		JLabel lblNewLabel = new JLabel("Train Constants:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 4;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_4 = new JLabel("Train Motion:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 3;
		gbc_lblNewLabel_4.gridy = 4;
		frame.getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JLabel lblLimits = new JLabel("Limits:");
		lblLimits.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblLimits = new GridBagConstraints();
		gbc_lblLimits.anchor = GridBagConstraints.WEST;
		gbc_lblLimits.insets = new Insets(0, 0, 5, 5);
		gbc_lblLimits.gridx = 4;
		gbc_lblLimits.gridy = 4;
		frame.getContentPane().add(lblLimits, gbc_lblLimits);
		
		JLabel lblPassengerInfo = new JLabel("Passenger Info:");
		lblPassengerInfo.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblPassengerInfo = new GridBagConstraints();
		gbc_lblPassengerInfo.anchor = GridBagConstraints.WEST;
		gbc_lblPassengerInfo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassengerInfo.gridx = 5;
		gbc_lblPassengerInfo.gridy = 4;
		frame.getContentPane().add(lblPassengerInfo, gbc_lblPassengerInfo);
		
		JLabel lblMiscellaneous = new JLabel("Miscellaneous: ");
		lblMiscellaneous.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblMiscellaneous = new GridBagConstraints();
		gbc_lblMiscellaneous.anchor = GridBagConstraints.WEST;
		gbc_lblMiscellaneous.insets = new Insets(0, 0, 5, 5);
		gbc_lblMiscellaneous.gridx = 6;
		gbc_lblMiscellaneous.gridy = 4;
		frame.getContentPane().add(lblMiscellaneous, gbc_lblMiscellaneous);
		
		JLabel lblNewLabel_1 = new JLabel("Length: 32.2m");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 5;
		frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblNewLabel_5 = new JLabel("Acceleration: "+String.format("%.3f", tr[i].getAcceleration()*3.28084)+"ft/s^2");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 3;
		gbc_lblNewLabel_5.gridy = 5;
		frame.getContentPane().add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JLabel lblNewLabel_7 = new JLabel(String.format("%.3f", tr[i].getMaxacceleration()*3.28084)+"ft/s^2");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 4;
		gbc_lblNewLabel_7.gridy = 5;
		frame.getContentPane().add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		JLabel lblPassengers = new JLabel("Passengers: "+tr[i].getPassengers());
		GridBagConstraints gbc_lblPassengers = new GridBagConstraints();
		gbc_lblPassengers.anchor = GridBagConstraints.WEST;
		gbc_lblPassengers.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassengers.gridx = 5;
		gbc_lblPassengers.gridy = 5;
		frame.getContentPane().add(lblPassengers, gbc_lblPassengers);
		
		JLabel lblAuthority = new JLabel("Authority:"+tr[i].getAuthority());
		GridBagConstraints gbc_lblAuthority = new GridBagConstraints();
		gbc_lblAuthority.anchor = GridBagConstraints.WEST;
		gbc_lblAuthority.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthority.gridx = 6;
		gbc_lblAuthority.gridy = 5;
		frame.getContentPane().add(lblAuthority, gbc_lblAuthority);
		
		JLabel lblNewLabel_2 = new JLabel("Width: 2.65m");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 6;
		frame.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JLabel lblSpeed = new JLabel("Speed: "+String.format("%.3f", tr[i].getVelocity()*3.28084)+"ft/s");
		GridBagConstraints gbc_lblSpeed = new GridBagConstraints();
		gbc_lblSpeed.anchor = GridBagConstraints.WEST;
		gbc_lblSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpeed.gridx = 3;
		gbc_lblSpeed.gridy = 6;
		frame.getContentPane().add(lblSpeed, gbc_lblSpeed);
		
		JLabel lblNewLabel_8 = new JLabel(String.format("%.3f", tr[i].getMaxvelocity()*3.28084)+"ft/s");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 4;
		gbc_lblNewLabel_8.gridy = 6;
		frame.getContentPane().add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		JLabel lblCrewCount = new JLabel("Crew Count:"+tr[i].getCrewcount());
		GridBagConstraints gbc_lblCrewCount = new GridBagConstraints();
		gbc_lblCrewCount.anchor = GridBagConstraints.WEST;
		gbc_lblCrewCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblCrewCount.gridx = 5;
		gbc_lblCrewCount.gridy = 6;
		frame.getContentPane().add(lblCrewCount, gbc_lblCrewCount);
		
		JLabel lblTotalMass = new JLabel("Total Mass: "+String.format("%.0f",(tr[i].getPassengermass()+tr[i].getTrainmass())*2.20462)+"lb");
		GridBagConstraints gbc_lblTotalMass = new GridBagConstraints();
		gbc_lblTotalMass.anchor = GridBagConstraints.WEST;
		gbc_lblTotalMass.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalMass.gridx = 6;
		gbc_lblTotalMass.gridy = 6;
		frame.getContentPane().add(lblTotalMass, gbc_lblTotalMass);
		
		JLabel lblHeight = new JLabel("Height: 3.42m");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.WEST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 2;
		gbc_lblHeight.gridy = 7;
		frame.getContentPane().add(lblHeight, gbc_lblHeight);
		
		JLabel lblDeceleration = new JLabel("Deceleration:"+String.format("%.3f", tr[i].getDeceleration()*3.28084)+"ft/s^2");
		GridBagConstraints gbc_lblDeceleration = new GridBagConstraints();
		gbc_lblDeceleration.anchor = GridBagConstraints.WEST;
		gbc_lblDeceleration.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeceleration.gridx = 3;
		gbc_lblDeceleration.gridy = 7;
		frame.getContentPane().add(lblDeceleration, gbc_lblDeceleration);
		
		JLabel lblNewLabel_9 = new JLabel(String.format("%.3f", tr[i].getMaxdeceleration()*3.28084)+"ft/s^2");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 4;
		gbc_lblNewLabel_9.gridy = 7;
		frame.getContentPane().add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		JLabel lblNewLabel_6 = new JLabel("Total Passenger Mass:"+String.format("%.0f", tr[i].getPassengermass()*2.20462)+"lb");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 5;
		gbc_lblNewLabel_6.gridy = 7;
		frame.getContentPane().add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		JLabel lblLights;
		if(tr[i].getLightson()) {
			lblLights = new JLabel("Lights: On");
		}else {
			lblLights = new JLabel("Lights: Off");
		}
		GridBagConstraints gbc_lblLights = new GridBagConstraints();
		gbc_lblLights.anchor = GridBagConstraints.WEST;
		gbc_lblLights.insets = new Insets(0, 0, 5, 5);
		gbc_lblLights.gridx = 6;
		gbc_lblLights.gridy = 7;
		frame.getContentPane().add(lblLights, gbc_lblLights);
		
		JLabel lblNewLabel_3 = new JLabel("Train Mass:"+String.format("%.0f", tr[i].getTrainmass()*2.20462)+"lb");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 8;
		frame.getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		
		JLabel lblDoors;
		if(tr[i].getOpendoors()) {
			lblDoors = new JLabel("Doors: Open");
		}else {
			lblDoors = new JLabel("Doors: Closed");
		}
		GridBagConstraints gbc_lblDoors = new GridBagConstraints();
		gbc_lblDoors.anchor = GridBagConstraints.WEST;
		gbc_lblDoors.insets = new Insets(0, 0, 5, 5);
		gbc_lblDoors.gridx = 6;
		gbc_lblDoors.gridy = 8;
		frame.getContentPane().add(lblDoors, gbc_lblDoors);
		
		JLabel lblCars = new JLabel("Cars: 5");
		GridBagConstraints gbc_lblCars = new GridBagConstraints();
		gbc_lblCars.anchor = GridBagConstraints.WEST;
		gbc_lblCars.insets = new Insets(0, 0, 5, 5);
		gbc_lblCars.gridx = 2;
		gbc_lblCars.gridy = 9;
		frame.getContentPane().add(lblCars, gbc_lblCars);
		
		JButton btnNewButton = new JButton("Enginer Failure");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 14;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Signal Pickup Failure");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 3;
		gbc_btnNewButton_1.gridy = 14;
		frame.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Brake Failure");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 4;
		gbc_btnNewButton_2.gridy = 14;
		frame.getContentPane().add(btnNewButton_2, gbc_btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Passenger E-Brake");
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_3.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_3.gridx = 5;
		gbc_btnNewButton_3.gridy = 14;
		frame.getContentPane().add(btnNewButton_3, gbc_btnNewButton_3);
		
		comboBox.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e){
		    	initialize(comboBox.getSelectedIndex());
		    	System.out.println(comboBox.getSelectedIndex());
		    }

		});
	}
	

}
