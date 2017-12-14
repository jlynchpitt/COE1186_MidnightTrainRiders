package MTR.NorthShoreExtension.UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;

import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class UpdateTrackUI {
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	static DBHelper load = MainMTR.getDBHelper();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateTrackUI window = new UpdateTrackUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
	public static void createAndShowGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateTrackUI window = new UpdateTrackUI();
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
	public UpdateTrackUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 730, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		textField = new JTextField();
		textField.setBounds(128, 28, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblName = new JLabel("Track:");
		lblName.setBounds(65, 31, 46, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblPhone = new JLabel("Speed:");
		lblPhone.setBounds(65, 68, 46, 14);
		frame.getContentPane().add(lblPhone);
		
		textField_1 = new JTextField();
		textField_1.setBounds(128, 65, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblEmailId = new JLabel("Auth:");
		lblEmailId.setBounds(65, 115, 46, 14);
		frame.getContentPane().add(lblEmailId);
		
		textField_2 = new JTextField();
		textField_2.setBounds(128, 112, 247, 17);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblAddress = new JLabel("Occupied: ");
		lblAddress.setBounds(65, 162, 46, 14);
		frame.getContentPane().add(lblAddress);
				
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(126, 157, 212, 40);
		frame.getContentPane().add(textArea_1);
		
		
		
		JButton btnClear = new JButton("Clear");
		
		btnClear.setBounds(312, 387, 89, 23);
		frame.getContentPane().add(btnClear);
		
		/*JLabel lblSex = new JLabel("Failure Mode:");
		lblSex.setBounds(65, 228, 46, 14);
		frame.getContentPane().add(lblSex);
		
		JLabel lblMale = new JLabel("Circuit Failure");
		lblMale.setBounds(128, 228, 46, 14);
		frame.getContentPane().add(lblMale);
		
		JLabel lblFemale = new JLabel("Broken Rail");
		lblFemale.setBounds(292, 228, 46, 14);
		frame.getContentPane().add(lblFemale);
		
		JRadioButton radioButton = new JRadioButton("");
		radioButton.setBounds(337, 224, 109, 23);
		frame.getContentPane().add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("");
		radioButton_1.setBounds(162, 224, 109, 23);
		frame.getContentPane().add(radioButton_1);*/
		
		JLabel lblOccupation = new JLabel("Failure:");
		lblOccupation.setBounds(65, 288, 67, 14);
		frame.getContentPane().add(lblOccupation);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("Select");
		comboBox.addItem("Broken Rail");
		comboBox.addItem("Circuit Failure");
		comboBox.addItem("Power Failure");
		comboBox.addItem("None");
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		comboBox.setBounds(180, 285, 91, 20);
		frame.getContentPane().add(comboBox);
		
		
		JButton btnSubmit = new JButton("Submit");
		
		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(65, 387, 89, 23);
		frame.getContentPane().add(btnSubmit);
		
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText().isEmpty()||(textField_1.getText().isEmpty())||(textField_2.getText().isEmpty())||(textArea_1.getText().isEmpty())||(comboBox.getSelectedItem().equals("Select")))
					JOptionPane.showMessageDialog(null, "Data Missing");
				else {
					int trackid = Integer.parseInt(textField.getText().replaceAll("[^0-9]", ""));
					int speed = Integer.parseInt(textField_1.getText().replaceAll("[^0-9]", ""));
					int authority = Integer.parseInt(textField_2.getText().replaceAll("[^0-9]", ""));
					String occupied = textArea_1.getText();
					if(occupied.equals("occupied")) {
						load.updateTrackOccupied(trackid, 1);
					}
					load.updateSpeedAuthority(trackid, speed, authority);
					if(comboBox.getSelectedItem().equals("Broken Rail")) {
						load.updateTrackStatus(trackid, "Broken Rail");
					}
					else if(comboBox.getSelectedItem().equals("Circuit Failure")) {
						load.updateTrackStatus(trackid, "Circuit Failure");
					}
					else if(comboBox.getSelectedItem().equals("Power Failure")) {
						load.updateTrackStatus(trackid, "Power Failure");
					}
					//JOptionPane.showMessageDialog(null, "Data Submitted");
				}
				
			}
		});
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText(null);
				textField_2.setText(null);
				textField.setText(null);
				textArea_1.setText(null);
				comboBox.setSelectedItem("Select");
				
				
			}
		});
		
	}
}
