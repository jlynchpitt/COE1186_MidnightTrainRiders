package MTR.NorthShoreExtension.UI;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MTR.NorthShoreExtension.Backend.TrainSrc.Train;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TrainModelTestUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private Train tr;
	private double p;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainModelTestUI frame = new TrainModelTestUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public TrainModelTestUI() {
		setBackground(new Color(240, 240, 240));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 200, 90, 100, 100, 100, 100, 100, 100, 100, 100, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblSelectTrain = new JLabel("Select Train:");
		lblSelectTrain.setForeground(new Color(0, 0, 51));
		lblSelectTrain.setFont(new Font("Arial", Font.BOLD, 18));
		GridBagConstraints gbc_lblSelectTrain = new GridBagConstraints();
		gbc_lblSelectTrain.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectTrain.gridx = 1;
		gbc_lblSelectTrain.gridy = 1;
		contentPane.add(lblSelectTrain, gbc_lblSelectTrain);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.fill = GridBagConstraints.VERTICAL;
		gbc_separator.ipady = 2;
		gbc_separator.ipadx = 2;
		gbc_separator.gridheight = 6;
		gbc_separator.gridx = 2;
		gbc_separator.gridy = 1;
		contentPane.add(separator, gbc_separator);
		
		JLabel lblNewLabel = new JLabel("Constants:");
		lblNewLabel.setBackground(new Color(34, 139, 34));
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 18));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblMovementInformation = new JLabel("Movement:");
		lblMovementInformation.setForeground(new Color(0, 0, 51));
		lblMovementInformation.setFont(new Font("Arial", Font.BOLD, 18));
		GridBagConstraints gbc_lblMovementInformation = new GridBagConstraints();
		gbc_lblMovementInformation.gridwidth = 2;
		gbc_lblMovementInformation.insets = new Insets(0, 0, 5, 5);
		gbc_lblMovementInformation.gridx = 5;
		gbc_lblMovementInformation.gridy = 1;
		contentPane.add(lblMovementInformation, gbc_lblMovementInformation);
		
		JLabel lblPassnegerInformation = new JLabel("Passengers:");
		lblPassnegerInformation.setFont(new Font("Arial", Font.BOLD, 18));
		GridBagConstraints gbc_lblPassnegerInformation = new GridBagConstraints();
		gbc_lblPassnegerInformation.gridwidth = 2;
		gbc_lblPassnegerInformation.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassnegerInformation.gridx = 7;
		gbc_lblPassnegerInformation.gridy = 1;
		contentPane.add(lblPassnegerInformation, gbc_lblPassnegerInformation);
		
		JLabel lblDynamic = new JLabel("Controllables:");
		lblDynamic.setFont(new Font("Arial", Font.BOLD, 18));
		GridBagConstraints gbc_lblDynamic = new GridBagConstraints();
		gbc_lblDynamic.gridwidth = 2;
		gbc_lblDynamic.insets = new Insets(0, 0, 5, 0);
		gbc_lblDynamic.gridx = 9;
		gbc_lblDynamic.gridy = 1;
		contentPane.add(lblDynamic, gbc_lblDynamic);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		contentPane.add(comboBox, gbc_comboBox);
		
		JLabel lblMass = new JLabel("Mass:");
		lblMass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblMass = new GridBagConstraints();
		gbc_lblMass.anchor = GridBagConstraints.EAST;
		gbc_lblMass.insets = new Insets(0, 0, 5, 5);
		gbc_lblMass.gridx = 3;
		gbc_lblMass.gridy = 2;
		contentPane.add(lblMass, gbc_lblMass);
		
		JLabel lblAcceleration = new JLabel("Acceleration:");
		lblAcceleration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblAcceleration = new GridBagConstraints();
		gbc_lblAcceleration.anchor = GridBagConstraints.EAST;
		gbc_lblAcceleration.insets = new Insets(0, 0, 5, 5);
		gbc_lblAcceleration.gridx = 5;
		gbc_lblAcceleration.gridy = 2;
		contentPane.add(lblAcceleration, gbc_lblAcceleration);
		
		JLabel lblPassengerCount = new JLabel("Passenger Count:");
		lblPassengerCount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPassengerCount = new GridBagConstraints();
		gbc_lblPassengerCount.anchor = GridBagConstraints.EAST;
		gbc_lblPassengerCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassengerCount.gridx = 7;
		gbc_lblPassengerCount.gridy = 2;
		contentPane.add(lblPassengerCount, gbc_lblPassengerCount);
		
		JLabel lblLights = new JLabel("Lights:");
		lblLights.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblLights = new GridBagConstraints();
		gbc_lblLights.anchor = GridBagConstraints.EAST;
		gbc_lblLights.insets = new Insets(0, 0, 5, 5);
		gbc_lblLights.gridx = 9;
		gbc_lblLights.gridy = 2;
		contentPane.add(lblLights, gbc_lblLights);
		
		JLabel lblLength = new JLabel("Length:");
		lblLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblLength = new GridBagConstraints();
		gbc_lblLength.anchor = GridBagConstraints.EAST;
		gbc_lblLength.insets = new Insets(0, 0, 5, 5);
		gbc_lblLength.gridx = 3;
		gbc_lblLength.gridy = 3;
		contentPane.add(lblLength, gbc_lblLength);
		
		JLabel lblDeceleration = new JLabel("Deceleration:");
		lblDeceleration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblDeceleration = new GridBagConstraints();
		gbc_lblDeceleration.anchor = GridBagConstraints.EAST;
		gbc_lblDeceleration.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeceleration.gridx = 5;
		gbc_lblDeceleration.gridy = 3;
		contentPane.add(lblDeceleration, gbc_lblDeceleration);
		
		JLabel lblPassengerWeight = new JLabel("Passenger Weight:");
		lblPassengerWeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPassengerWeight = new GridBagConstraints();
		gbc_lblPassengerWeight.anchor = GridBagConstraints.EAST;
		gbc_lblPassengerWeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassengerWeight.gridx = 7;
		gbc_lblPassengerWeight.gridy = 3;
		contentPane.add(lblPassengerWeight, gbc_lblPassengerWeight);
		
		JLabel lblLeftDoor = new JLabel("Left Door:");
		lblLeftDoor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblLeftDoor = new GridBagConstraints();
		gbc_lblLeftDoor.anchor = GridBagConstraints.EAST;
		gbc_lblLeftDoor.insets = new Insets(0, 0, 5, 5);
		gbc_lblLeftDoor.gridx = 9;
		gbc_lblLeftDoor.gridy = 3;
		contentPane.add(lblLeftDoor, gbc_lblLeftDoor);
		
		JLabel lblWidth = new JLabel("Width:");
		lblWidth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.anchor = GridBagConstraints.EAST;
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.gridx = 3;
		gbc_lblWidth.gridy = 4;
		contentPane.add(lblWidth, gbc_lblWidth);
		
		JLabel lblVelocity = new JLabel("Velocity:");
		lblVelocity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblVelocity = new GridBagConstraints();
		gbc_lblVelocity.anchor = GridBagConstraints.EAST;
		gbc_lblVelocity.insets = new Insets(0, 0, 5, 5);
		gbc_lblVelocity.gridx = 5;
		gbc_lblVelocity.gridy = 4;
		contentPane.add(lblVelocity, gbc_lblVelocity);
		
		JLabel lblCrewCount = new JLabel("Crew Count:");
		lblCrewCount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblCrewCount = new GridBagConstraints();
		gbc_lblCrewCount.anchor = GridBagConstraints.EAST;
		gbc_lblCrewCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblCrewCount.gridx = 7;
		gbc_lblCrewCount.gridy = 4;
		contentPane.add(lblCrewCount, gbc_lblCrewCount);
		
		JLabel lblRightDoor = new JLabel("Right Door:");
		lblRightDoor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblRightDoor = new GridBagConstraints();
		gbc_lblRightDoor.anchor = GridBagConstraints.EAST;
		gbc_lblRightDoor.insets = new Insets(0, 0, 5, 5);
		gbc_lblRightDoor.gridx = 9;
		gbc_lblRightDoor.gridy = 4;
		contentPane.add(lblRightDoor, gbc_lblRightDoor);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.EAST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 3;
		gbc_lblHeight.gridy = 5;
		contentPane.add(lblHeight, gbc_lblHeight);
		
		JLabel lblAuthority = new JLabel("Authority:");
		lblAuthority.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblAuthority = new GridBagConstraints();
		gbc_lblAuthority.anchor = GridBagConstraints.EAST;
		gbc_lblAuthority.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthority.gridx = 5;
		gbc_lblAuthority.gridy = 5;
		contentPane.add(lblAuthority, gbc_lblAuthority);
		
		JLabel lblTotalWeight = new JLabel("Total Weight:");
		lblTotalWeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTotalWeight = new GridBagConstraints();
		gbc_lblTotalWeight.anchor = GridBagConstraints.EAST;
		gbc_lblTotalWeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalWeight.gridx = 9;
		gbc_lblTotalWeight.gridy = 5;
		contentPane.add(lblTotalWeight, gbc_lblTotalWeight);
		
		JLabel lblCars = new JLabel("Cars:");
		lblCars.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblCars = new GridBagConstraints();
		gbc_lblCars.anchor = GridBagConstraints.EAST;
		gbc_lblCars.insets = new Insets(0, 0, 5, 5);
		gbc_lblCars.gridx = 3;
		gbc_lblCars.gridy = 6;
		contentPane.add(lblCars, gbc_lblCars);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 13;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				p=Double.parseDouble(textField.getText());
			}
		});
		GridBagConstraints gbc_btnEnter = new GridBagConstraints();
		gbc_btnEnter.insets = new Insets(0, 0, 0, 5);
		gbc_btnEnter.gridx = 2;
		gbc_btnEnter.gridy = 13;
		contentPane.add(btnEnter, gbc_btnEnter);
	}

}
