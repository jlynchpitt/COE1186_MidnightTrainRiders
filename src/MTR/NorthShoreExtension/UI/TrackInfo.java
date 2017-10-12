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

public class TrackInfo  
{
	public static JPanel SC = new JPanel();
	public static JFrame f = new JFrame("Wayside Controller");  //create frame with frame name
	public static Component SaveButton = new Button("Save"); //create one of the buttons
	public static Component PLCButton = new Button("To PLC"); //create one of the buttons
	public static Component TrackInfoButton = new Button("To Track Info Hub"); //create one of the buttons
      
  public TrackInfo() 
  {

    DefaultTableModel dm = new DefaultTableModel();
    dm.setDataVector(new Object[][] { { "button 1", "foo", "foo", "foo" },
        { "button 2", "bar", "bar", "bar" } }, new Object[] { "Line", "Occupied Track", "Dest Track", "Athrty" });

    JTable table = new JTable(dm);

//---------------------------------------------------------------------	
	
	DefaultTableModel am = new DefaultTableModel();
    am.setDataVector(new Object[][] { { "button 1", "foo" },
        { "button 2", "bar" } }, new Object[] { "Xing", "Line"});

    JTable lighttable = new JTable(am);
    JScrollPane scroll = new JScrollPane(table);
	JScrollPane scroll2 = new JScrollPane(lighttable);
	SC.setPreferredSize(new Dimension(50,50));
	SC.add(scroll, BorderLayout.WEST);
	SC.add(scroll2, BorderLayout.EAST);
	
	
	
	
	Box ButtonBox;
	   ButtonBox = Box.createVerticalBox();
	   ButtonBox.add( Box.createVerticalStrut( 25 ) );
	   ButtonBox.add(PLCButton, BorderLayout.EAST);
	   ButtonBox.add(TrackInfoButton, BorderLayout.EAST);	  //add to frame
    
	SC.add(ButtonBox, BorderLayout.EAST);
	
	//getContentPane().add(SC);
    f.setSize(600, 400);
    f.setVisible(true);
	f.getContentPane().add(SC);
  }

  public static void main(String[] args) 
  {
  
   
    f.addWindowListener
	   (
			new WindowAdapter() 
			{
				 public void windowClosing(WindowEvent windowEvent)
				 {
					System.exit(0);
				 }        
			}
	  );  
  }
}


