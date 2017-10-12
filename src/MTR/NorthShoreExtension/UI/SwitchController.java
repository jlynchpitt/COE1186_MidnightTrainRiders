package MTR.NorthShoreExtension.UI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SwitchController extends JFrame 
{
	public static JPanel SC = new JPanel();
	public static Component SaveButton = new Button("Save"); //create one of the buttons
	public static Component PLCButton = new Button("To PLC Hub"); //create one of the buttons
	public static Component TrackInfoButton = new Button("To Track Info Hub"); //create one of the buttons
      
  public SwitchController() 
  {
    super("JButtonTable Example");

    DefaultTableModel dm = new DefaultTableModel();
    dm.setDataVector(new Object[][] { { "Green", "2", "A1", "C2", "C5" },
        { "Green", "3", "A2", "A1", "B2" },
        { "Green", "4", "A3", "B2", "D6" }		}, new Object[] { "Line", "Block", "Track", "Dest Track", "Alt Track" });

    JTable table = new JTable(dm);
    JScrollPane scroll = new JScrollPane(table);
	SC.add(scroll, BorderLayout.WEST);
	
	
	
	
	Box ButtonBox;
	   ButtonBox = Box.createVerticalBox();
	   ButtonBox.add( Box.createVerticalStrut( 25 ) );
	   ButtonBox.add(PLCButton, BorderLayout.EAST);
	   ButtonBox.add(TrackInfoButton, BorderLayout.EAST);	  //add to frame
    
	SC.add(ButtonBox, BorderLayout.EAST);
	
	getContentPane().add(SC);
    setSize(600, 400);
    setVisible(true);
  }

  public static void main(String[] args) 
  {
  
   
    SwitchController frame = new SwitchController();
    frame.addWindowListener(new WindowAdapter() 
	{
      public void windowClosing(WindowEvent e) 
	  {
        System.exit(0);
      }
    });
  }
}





