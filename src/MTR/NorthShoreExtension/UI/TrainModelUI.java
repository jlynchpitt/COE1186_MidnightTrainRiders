import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MTR.NorthShoreExtension.Backend.TrackModelSrc.TrackModel;
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
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class TrainModelUI extends JFrame {

	private JPanel contentPane;
	private Map tarray;
	private Train tr;
	private TrackModel tm;
	private double p;
	private JTextField txtAcc;
	private JLabel lblV, lblAcc;
	private boolean b=false;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public TrainModelUI() {
		tarray=tm.getTrains();
		
		
		setBackground(new Color(240, 240, 240));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 150, 20, 100, 100, 100, 100, 100, 100, 100, 100, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_separator.gridheight = 7;
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
		String[] tnames = new String [tarray.length];
		for(int i=0;i<tarray.length;i++) {
			tnames[i]="Train " + tarray[i].getTrainID();
		}
		comboBox.setModel(new DefaultComboBoxModel(tarray));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tr=tarray[comboBox.getSelectedIndex()];
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		contentPane.add(comboBox, gbc_comboBox);
		
		JLabel lblMass = new JLabel("Train Mass:");
		lblMass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblMass = new GridBagConstraints();
		gbc_lblMass.anchor = GridBagConstraints.WEST;
		gbc_lblMass.insets = new Insets(0, 0, 5, 5);
		gbc_lblMass.gridx = 3;
		gbc_lblMass.gridy = 2;
		contentPane.add(lblMass, gbc_lblMass);
		
		JLabel lblM = new JLabel(String.format("%.2f",tr.getTrainMass()));
		GridBagConstraints gbc_lblM = new GridBagConstraints();
		gbc_lblM.insets = new Insets(0, 0, 5, 5);
		gbc_lblM.gridx = 4;
		gbc_lblM.gridy = 2;
		contentPane.add(lblM, gbc_lblM);
		
		JLabel lblAcceleration = new JLabel("Acceleration:");
		lblAcceleration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblAcceleration = new GridBagConstraints();
		gbc_lblAcceleration.anchor = GridBagConstraints.WEST;
		gbc_lblAcceleration.insets = new Insets(0, 0, 5, 5);
		gbc_lblAcceleration.gridx = 5;
		gbc_lblAcceleration.gridy = 2;
		contentPane.add(lblAcceleration, gbc_lblAcceleration);
		
		lblAcc = new JLabel(String.format("%.2f", tr.getAcceleration()));
		GridBagConstraints gbc_lblAcc = new GridBagConstraints();
		gbc_lblAcc.insets = new Insets(0, 0, 5, 5);
		gbc_lblAcc.gridx = 6;
		gbc_lblAcc.gridy = 2;
		contentPane.add(lblAcc, gbc_lblAcc);
		
		
		
		JLabel lblPassengerCount = new JLabel("Passenger Count:");
		lblPassengerCount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPassengerCount = new GridBagConstraints();
		gbc_lblPassengerCount.anchor = GridBagConstraints.WEST;
		gbc_lblPassengerCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassengerCount.gridx = 7;
		gbc_lblPassengerCount.gridy = 2;
		contentPane.add(lblPassengerCount, gbc_lblPassengerCount);
		
		JLabel lblPc = new JLabel("pc");
		GridBagConstraints gbc_lblPc = new GridBagConstraints();
		gbc_lblPc.insets = new Insets(0, 0, 5, 5);
		gbc_lblPc.gridx = 8;
		gbc_lblPc.gridy = 2;
		contentPane.add(lblPc, gbc_lblPc);
		
		JLabel lblLights = new JLabel("Lights:");
		lblLights.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblLights = new GridBagConstraints();
		gbc_lblLights.anchor = GridBagConstraints.WEST;
		gbc_lblLights.insets = new Insets(0, 0, 5, 5);
		gbc_lblLights.gridx = 9;
		gbc_lblLights.gridy = 2;
		contentPane.add(lblLights, gbc_lblLights);
		
		JLabel lblL_1 = new JLabel("l");
		GridBagConstraints gbc_lblL_1 = new GridBagConstraints();
		gbc_lblL_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblL_1.gridx = 10;
		gbc_lblL_1.gridy = 2;
		contentPane.add(lblL_1, gbc_lblL_1);
		
		JLabel lblLength = new JLabel("Length:");
		lblLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblLength = new GridBagConstraints();
		gbc_lblLength.anchor = GridBagConstraints.WEST;
		gbc_lblLength.insets = new Insets(0, 0, 5, 5);
		gbc_lblLength.gridx = 3;
		gbc_lblLength.gridy = 3;
		contentPane.add(lblLength, gbc_lblLength);
		
		JLabel lblL = new JLabel("l");
		GridBagConstraints gbc_lblL = new GridBagConstraints();
		gbc_lblL.insets = new Insets(0, 0, 5, 5);
		gbc_lblL.gridx = 4;
		gbc_lblL.gridy = 3;
		contentPane.add(lblL, gbc_lblL);
		
		JLabel lblDeceleration = new JLabel("Deceleration:");
		lblDeceleration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblDeceleration = new GridBagConstraints();
		gbc_lblDeceleration.anchor = GridBagConstraints.WEST;
		gbc_lblDeceleration.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeceleration.gridx = 5;
		gbc_lblDeceleration.gridy = 3;
		contentPane.add(lblDeceleration, gbc_lblDeceleration);
		
		JLabel lblDcc = new JLabel("Dcc");
		GridBagConstraints gbc_lblDcc = new GridBagConstraints();
		gbc_lblDcc.insets = new Insets(0, 0, 5, 5);
		gbc_lblDcc.gridx = 6;
		gbc_lblDcc.gridy = 3;
		contentPane.add(lblDcc, gbc_lblDcc);
		
		JLabel lblPassengerWeight = new JLabel("Passenger Weight:");
		lblPassengerWeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPassengerWeight = new GridBagConstraints();
		gbc_lblPassengerWeight.anchor = GridBagConstraints.WEST;
		gbc_lblPassengerWeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassengerWeight.gridx = 7;
		gbc_lblPassengerWeight.gridy = 3;
		contentPane.add(lblPassengerWeight, gbc_lblPassengerWeight);
		
		JLabel lblPw = new JLabel("pw");
		GridBagConstraints gbc_lblPw = new GridBagConstraints();
		gbc_lblPw.insets = new Insets(0, 0, 5, 5);
		gbc_lblPw.gridx = 8;
		gbc_lblPw.gridy = 3;
		contentPane.add(lblPw, gbc_lblPw);
		
		JLabel lblLeftDoor = new JLabel("Left Door:");
		lblLeftDoor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblLeftDoor = new GridBagConstraints();
		gbc_lblLeftDoor.anchor = GridBagConstraints.WEST;
		gbc_lblLeftDoor.insets = new Insets(0, 0, 5, 5);
		gbc_lblLeftDoor.gridx = 9;
		gbc_lblLeftDoor.gridy = 3;
		contentPane.add(lblLeftDoor, gbc_lblLeftDoor);
		
		JLabel lblLd = new JLabel("ld");
		GridBagConstraints gbc_lblLd = new GridBagConstraints();
		gbc_lblLd.insets = new Insets(0, 0, 5, 0);
		gbc_lblLd.gridx = 10;
		gbc_lblLd.gridy = 3;
		contentPane.add(lblLd, gbc_lblLd);
		
		JLabel lblWidth = new JLabel("Width:");
		lblWidth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.anchor = GridBagConstraints.WEST;
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.gridx = 3;
		gbc_lblWidth.gridy = 4;
		contentPane.add(lblWidth, gbc_lblWidth);
		
		JLabel lblW = new JLabel("w");
		GridBagConstraints gbc_lblW = new GridBagConstraints();
		gbc_lblW.insets = new Insets(0, 0, 5, 5);
		gbc_lblW.gridx = 4;
		gbc_lblW.gridy = 4;
		contentPane.add(lblW, gbc_lblW);
		
		JLabel lblVelocity = new JLabel("Velocity:");
		lblVelocity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblVelocity = new GridBagConstraints();
		gbc_lblVelocity.anchor = GridBagConstraints.WEST;
		gbc_lblVelocity.insets = new Insets(0, 0, 5, 5);
		gbc_lblVelocity.gridx = 5;
		gbc_lblVelocity.gridy = 4;
		contentPane.add(lblVelocity, gbc_lblVelocity);
		
		lblV = new JLabel(String.format("%.2f", tr.getVelocity()));
		GridBagConstraints gbc_lblV = new GridBagConstraints();
		gbc_lblV.insets = new Insets(0, 0, 5, 5);
		gbc_lblV.gridx = 6;
		gbc_lblV.gridy = 4;
		contentPane.add(lblV, gbc_lblV);
		
		JLabel lblCrewCount = new JLabel("Crew Count:");
		lblCrewCount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblCrewCount = new GridBagConstraints();
		gbc_lblCrewCount.anchor = GridBagConstraints.WEST;
		gbc_lblCrewCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblCrewCount.gridx = 7;
		gbc_lblCrewCount.gridy = 4;
		contentPane.add(lblCrewCount, gbc_lblCrewCount);
		
		JLabel lblCc = new JLabel("cc");
		GridBagConstraints gbc_lblCc = new GridBagConstraints();
		gbc_lblCc.insets = new Insets(0, 0, 5, 5);
		gbc_lblCc.gridx = 8;
		gbc_lblCc.gridy = 4;
		contentPane.add(lblCc, gbc_lblCc);
		
		JLabel lblRightDoor = new JLabel("Right Door:");
		lblRightDoor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblRightDoor = new GridBagConstraints();
		gbc_lblRightDoor.anchor = GridBagConstraints.WEST;
		gbc_lblRightDoor.insets = new Insets(0, 0, 5, 5);
		gbc_lblRightDoor.gridx = 9;
		gbc_lblRightDoor.gridy = 4;
		contentPane.add(lblRightDoor, gbc_lblRightDoor);
		
		JLabel lblRd = new JLabel("rd");
		GridBagConstraints gbc_lblRd = new GridBagConstraints();
		gbc_lblRd.insets = new Insets(0, 0, 5, 0);
		gbc_lblRd.gridx = 10;
		gbc_lblRd.gridy = 4;
		contentPane.add(lblRd, gbc_lblRd);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.WEST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 3;
		gbc_lblHeight.gridy = 5;
		contentPane.add(lblHeight, gbc_lblHeight);
		
		JLabel lblH = new JLabel("h");
		GridBagConstraints gbc_lblH = new GridBagConstraints();
		gbc_lblH.insets = new Insets(0, 0, 5, 5);
		gbc_lblH.gridx = 4;
		gbc_lblH.gridy = 5;
		contentPane.add(lblH, gbc_lblH);
		
		JLabel lblAuthority = new JLabel("Authority:");
		lblAuthority.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblAuthority = new GridBagConstraints();
		gbc_lblAuthority.anchor = GridBagConstraints.WEST;
		gbc_lblAuthority.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthority.gridx = 5;
		gbc_lblAuthority.gridy = 5;
		contentPane.add(lblAuthority, gbc_lblAuthority);
		
		JLabel lblA = new JLabel("a");
		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 6;
		gbc_lblA.gridy = 5;
		contentPane.add(lblA, gbc_lblA);
		
		JLabel lblTotalWeight = new JLabel("Total Weight:");
		lblTotalWeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTotalWeight = new GridBagConstraints();
		gbc_lblTotalWeight.anchor = GridBagConstraints.WEST;
		gbc_lblTotalWeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalWeight.gridx = 9;
		gbc_lblTotalWeight.gridy = 5;
		contentPane.add(lblTotalWeight, gbc_lblTotalWeight);
		
		JLabel lblTw = new JLabel("tw");
		GridBagConstraints gbc_lblTw = new GridBagConstraints();
		gbc_lblTw.insets = new Insets(0, 0, 5, 0);
		gbc_lblTw.gridx = 10;
		gbc_lblTw.gridy = 5;
		contentPane.add(lblTw, gbc_lblTw);
		
		JLabel lblCars = new JLabel("Cars:");
		lblCars.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblCars = new GridBagConstraints();
		gbc_lblCars.anchor = GridBagConstraints.WEST;
		gbc_lblCars.insets = new Insets(0, 0, 5, 5);
		gbc_lblCars.gridx = 3;
		gbc_lblCars.gridy = 6;
		contentPane.add(lblCars, gbc_lblCars);
		
		JLabel lblC = new JLabel("c");
		GridBagConstraints gbc_lblC = new GridBagConstraints();
		gbc_lblC.insets = new Insets(0, 0, 5, 5);
		gbc_lblC.gridx = 4;
		gbc_lblC.gridy = 6;
		contentPane.add(lblC, gbc_lblC);
		
		JLabel lblDistance = new JLabel("Distance:");
		lblDistance.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblDistance = new GridBagConstraints();
		gbc_lblDistance.anchor = GridBagConstraints.WEST;
		gbc_lblDistance.insets = new Insets(0, 0, 5, 5);
		gbc_lblDistance.gridx = 5;
		gbc_lblDistance.gridy = 6;
		contentPane.add(lblDistance, gbc_lblDistance);
		
		JLabel lblD = new JLabel("d");
		GridBagConstraints gbc_lblD = new GridBagConstraints();
		gbc_lblD.insets = new Insets(0, 0, 5, 5);
		gbc_lblD.gridx = 6;
		gbc_lblD.gridy = 6;
		contentPane.add(lblD, gbc_lblD);
		
		JLabel lblCumulativeDistance = new JLabel("Cumulative Distance");
		GridBagConstraints gbc_lblCumulativeDistance = new GridBagConstraints();
		gbc_lblCumulativeDistance.anchor = GridBagConstraints.WEST;
		gbc_lblCumulativeDistance.insets = new Insets(0, 0, 5, 5);
		gbc_lblCumulativeDistance.gridx = 5;
		gbc_lblCumulativeDistance.gridy = 7;
		contentPane.add(lblCumulativeDistance, gbc_lblCumulativeDistance);
		
		JLabel lblCd = new JLabel("cd");
		GridBagConstraints gbc_lblCd = new GridBagConstraints();
		gbc_lblCd.insets = new Insets(0, 0, 5, 5);
		gbc_lblCd.gridx = 6;
		gbc_lblCd.gridy = 7;
		contentPane.add(lblCd, gbc_lblCd);
	}
	
	public void updateGUI() {
		lblV.setText(String.format("%.2f", tr.getVelocity()));
		lblAcc.setText(String.format("%.2f", tr.getAcceleration()));
	}
	
}
