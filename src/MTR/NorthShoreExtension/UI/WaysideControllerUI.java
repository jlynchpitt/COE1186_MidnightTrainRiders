/*
 * Filename: WaysideControllerUI.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.UI;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
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

import MTR.NorthShoreExtension.Backend.WaysideController.WaysideFunctions;
import MTR.NorthShoreExtension.Backend.WaysideController.WaysideFunctionsHub;



public class WaysideControllerUI
{
	//imports all packages
	public static String SampleCode = "Int total = 0;\n Public static void main (String [] args)\n{\n    If (traindist <= 100)\n    {\n        Do\n        {\n            Close gate\n            Signal light\n        }\n    }\n    Public boolean distmeas(int trackcount)\n    {\n        For (int x = 0; x < trackcount; x++)\n        {\n            Total += x; \n        }\n        If (total <= standarddist)\n        {\n            Return true;\n        }\n        Else\n        {\n            Return false;\n        ]\n    }\n}";
	//basic frame and components
	public static JPanel plc = new JPanel();
	public static JPanel TI = new JPanel();
	public static JPanel SC = new JPanel();
	public static JPanel TestPanel = new JPanel();
	public static JFrame f = new JFrame("Wayside Controller");  //create frame with frame name
	public static Component text = new TextArea(SampleCode);  //create text area
	public static JButton SaveButton = new JButton("Save"); //create one of the buttons
	//public static JButton SaveButton = new JButton("Save");
	public static JButton SwtchCtrlButton = new JButton("To Switch Control Hub"); //create one of the buttons
	public static JButton PLCButton = new JButton("To PLC"); //create one of the buttons
	public static JButton TrackInfoButton = new JButton("To Track Info Hub"); //create one of the buttons
	public static JButton Test = new JButton ("Tester");
	public static int FrameTracker = 0;
	public static WaysideFunctions obj = new WaysideFunctions();
	public static DefaultTableModel dm = new DefaultTableModel();
	public static DefaultTableModel om = new DefaultTableModel();
	public static DefaultTableModel am = new DefaultTableModel();
	public static DefaultTableModel dm1 = new DefaultTableModel();
	public static JTable table1;
	public static JTable table;
	public static JScrollPane scroll1;
	public static JScrollPane scroll;
	public static JScrollPane scroll2;
	public static int Scroll1Height = 75;
	
    
	  
	  //public static int[] ProtoArray = {0,1,2,3,4,5,6};
   public static void main(String[] args) //main body
   {
	   //setup panels
	   PLCSetup();
	   TrackInfoSetup();
	   SwitchSetup();
	   TestSetup();
	   //add action
	    ActionAdder();  
	//create frame 
	  int width = 750;
      int height = 500;
      f.setSize(width, height);
	  f.getContentPane().add(plc);
      f.setVisible(true);
      //OccupiedTrackTableUpdater();
	  
	  
	  
   } 
   
   //set the functions of the buttons
   public static void ActionAdder()
   {
	   
	   //exit window
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
	  /*
	  PLIC -- 0
	  SC -- 1
	  TI -- 2
	  */
	   
		//button that goes to switch control
	   Test.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  f.getContentPane().removeAll();
			  FrameTracker = 1;
			  f.getContentPane().add(TestPanel);
			  f.revalidate();
			  WaysideFunctions.Timer();
			  //start test
		  }
		});
	  SwtchCtrlButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  f.getContentPane().removeAll();
			  FrameTracker = 1;
			  f.getContentPane().add(SC);
			  f.revalidate();
		  }
		});
	//button that goes to track info
	TrackInfoButton.addActionListener(new ActionListener()
	{
	  public void actionPerformed(ActionEvent e)
	  {

		  f.getContentPane().removeAll();
		  FrameTracker = 2;
		  f.getContentPane().add(TI);
		  f.revalidate();
	  }
	});
	
	//button that goes to plc
	PLCButton.addActionListener(new ActionListener()
	{
	  public void actionPerformed(ActionEvent e)
	  {

			f.getContentPane().removeAll();
		  FrameTracker = 2;
		  f.getContentPane().add(plc);
		  f.revalidate();
	  }
	});
		
		

   }
   public static void SwitchSwitcher(int x)  //switch based on location in the chart
   {
	   //System.out.println(dm.getRowCount());
	   Object placeholder = dm.getValueAt(x, 4);
	   dm.setValueAt(dm.getValueAt(x, 3), x,4);
	   dm.setValueAt(placeholder, x,3);
   }

   public static void OccupiedTrackTableUpdater(Object[][] ObjectArray)
   {
	   for (int x = 0; x < ObjectArray.length; x++)
	   {
		   //dm1.addRow(ObjectArray[x]);
		   
		   for (int y = 0; y < ObjectArray[x].length; y++)
		   {
			   if (x > dm1.getRowCount()-1)
			   {
				   dm1.addRow(ObjectArray[x]);
			   }
			   else
			   {
				   dm1.setValueAt(ObjectArray[x][y], x, y);
			   }
			   Scroll1Height = dm1.getRowCount()*(20); 
			   scroll1.setPreferredSize(new Dimension(500, Scroll1Height));
			   //System.out.println(Scroll1Height);
		   }
		   
		   
			   //System.out.println(x);
	   }
   }
   public static void SwitchChartUpdater(int ID)  //update with actual information
   {
	   String BlockNumber =  Integer.toString(ID).substring(1,4);

	   //LineColor = DB.getColor(IncomingTrackOccupancyArray[x]);
	   for (int x = 0; x < dm.getRowCount(); x++)
	   {
		   if (Integer.parseInt(BlockNumber) == Integer.parseInt((String)(dm.getValueAt(x,1))))
		   {
			   SwitchSwitcher(x);
		   }
	   }
   }
   //register all panels
   public static void ComponentAdder()
   {
	   PLCSetup();
	   TrackInfoSetup();
	   SwitchSetup();
   }
   public static void TestSetup()
   {   
	   ButtonAdder();  
	   ActionAdder(); 
	   dm.setDataVector(new Object[][] { { "Red", "09", "C3", "Yard", "D1" },
			{ "Red", "15", "A2", "A1", "B2" },
			{ "Red", "27", "E3", "F1", "A1" },
			{ "Red", "32", "H8", "T1", "H9" },
			{ "Red", "38", "H15", "Q1", "H16" },
			{ "Red", "43", "H20", "H21", "O1" },
			{ "Red", "52", "J4", "N1", "J5" },
			{ "Green", "12", "C6", "D4", "A1" },
			{ "Green", "29", "G1", "F8", "Z1" },
			{ "Green", "58", "J1", "K1", "Yard" },
			{ "Green", "62", "J5", "I22", "Yard" },
			{ "Green", "76", "M3", "R1", "N1" },
			{ "Green", "86", "O1", "N9", "Q3" }		}, new Object[] { "Line", "Block", "Track", "Dest Track", "Alt Track" });

		table = new JTable(dm);
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(500,260));
		//-----------------------------------------------------------------------------
	    dm1.setDataVector(new Object[][] { { "Green", "C5", "Swtch", "20 mi" }}, new Object[] { "Line", "Occupied Track", "Dest Track", "Athrty" });

	    table1 = new JTable(dm1);


	//---------------------------------------------------------------------		
		DefaultTableModel am = new DefaultTableModel();
	    am.setDataVector(new Object[][] { { "Green", "E3" },
	        { "Red", "I2" } }, new Object[] { "Xing", "Line"});

	    JTable lighttable = new JTable(am);
	    scroll1 = new JScrollPane(table1);
		scroll2 = new JScrollPane(lighttable);
		scroll1.setPreferredSize(new Dimension(500,Scroll1Height));
		scroll2.setPreferredSize(new Dimension(100,75));
		//------------------------------------------
		TestPanel.add(scroll, BorderLayout.WEST);
		TestPanel.add(scroll1, BorderLayout.WEST);
		TestPanel.add(scroll2, BorderLayout.EAST);
		//------------------------------------
	   //final JComboBox<String> cb = new JComboBox<String>(choices);
	   Box ButtonBox;
	   ButtonBox = Box.createVerticalBox();
	   ButtonBox.add( Box.createVerticalStrut( 25 ) );
	   //ButtonBox.add(cb, BorderLayout.NORTH);
	   ButtonBox.add(SwtchCtrlButton, BorderLayout.EAST);
	   ButtonBox.add(TrackInfoButton, BorderLayout.EAST);
	   ButtonBox.add(PLCButton, BorderLayout.EAST);//add to frame
	   //TestPanel.add(text, BorderLayout.WEST);  //add tframo e 
	   TestPanel.add(ButtonBox, BorderLayout.WEST);
	   //f.getContentPane().add(plc);
   }
 //set up plc panel  
   public static void PLCSetup()
   {
	   
	   
	   ButtonAdder();
	   
	   ActionAdder();
	   
	   String[] choices = { "Green Line","Red Line"};
	   final JComboBox<String> cb = new JComboBox<String>(choices);
	   Box ButtonBox;
	   ButtonBox = Box.createVerticalBox();
	   ButtonBox.add( Box.createVerticalStrut( 25 ) );
	   ButtonBox.add(cb, BorderLayout.NORTH);
	   ButtonBox.add(SaveButton, BorderLayout.EAST);
	   ButtonBox.add(SwtchCtrlButton, BorderLayout.EAST);
	   ButtonBox.add(TrackInfoButton, BorderLayout.EAST);	  //add to frame
	   ButtonBox.add(Test, BorderLayout.EAST);
	   plc.add(text, BorderLayout.WEST);  //add tframo e 
	   plc.add(ButtonBox, BorderLayout.WEST);
	   //f.getContentPane().add(plc);
   }
  

//set up switch panel
  public static void SwitchSetup()
   {
	   
	  ButtonAdder();
		ActionAdder();
		
	   //DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][] { { "Red", "09", "C3", "Yard", "D1" },
			{ "Red", "15", "A2", "A1", "B2" },
			{ "Red", "27", "E3", "F1", "A1" },
			{ "Red", "32", "H8", "T1", "H9" },
			{ "Red", "38", "H15", "Q1", "H16" },
			{ "Red", "43", "H20", "H21", "O1" },
			{ "Red", "52", "J4", "N1", "J5" },
			{ "Green", "12", "C6", "D4", "A1" },
			{ "Green", "29", "G1", "F8", "Z1" },
			{ "Green", "58", "J1", "K1", "Yard" },
			{ "Green", "62", "J5", "I22", "Yard" },
			{ "Green", "76", "M3", "R1", "N1" },
			{ "Green", "86", "O1", "N9", "Q3" }		}, new Object[] { "Line", "Block", "Track", "Dest Track", "Alt Track" });

		table = new JTable(dm);
		
		scroll = new JScrollPane(table);
		
		SC.add(scroll, BorderLayout.WEST);
		
		
		
		
		Box ButtonBox1;
		
		ButtonBox1 = Box.createVerticalBox();
		  
		   ButtonBox1.add( Box.createVerticalStrut( 25 ) );
		   
		   ButtonBox1.add(PLCButton, BorderLayout.EAST);
		    
		 ButtonBox1.add(TrackInfoButton, BorderLayout.EAST);	  //add to frame
		
		SC.add(ButtonBox1, BorderLayout.EAST);
		
		//f.getContentPane().add(SC);
		   
	   
   }
     
   //set up track info panel
   
   public static void TrackInfoSetup()
   {
	   ButtonAdder();  
	   ActionAdder(); 
		//-----------------------------------------------------------------------------
	    dm1.setDataVector(new Object[][] { { "Green", "C5", "Swtch", "20 mi" }}, new Object[] { "Line", "Occupied Track", "Dest Track", "Athrty" });

	    table1 = new JTable(dm1);


	//---------------------------------------------------------------------		
		DefaultTableModel am = new DefaultTableModel();
	    am.setDataVector(new Object[][] { { "Green", "E3" },
	        { "Red", "I2" } }, new Object[] { "Xing", "Line"});

	    JTable lighttable = new JTable(am);
	    scroll1 = new JScrollPane(table1);
		scroll2 = new JScrollPane(lighttable);
		//scroll1.setPreferredSize(new Dimension(500,Scroll1Height));
		scroll2.setPreferredSize(new Dimension(100,75));


	TI.add(scroll1, BorderLayout.WEST);
	TI.add(scroll2, BorderLayout.EAST);
	
	
	
	
	Box ButtonBox;
	   ButtonBox = Box.createVerticalBox();
	   ButtonBox.add( Box.createVerticalStrut( 100 ) );
	    ButtonBox.add(SwtchCtrlButton, BorderLayout.EAST);
	   ButtonBox.add(PLCButton, BorderLayout.EAST);
    
	TI.add(ButtonBox, BorderLayout.EAST);
	//f.getContentPane().add(TI);
	
   }
   public static void ButtonAdder()
   {
	   SwtchCtrlButton = new JButton("To Switch Control Hub"); //create one of the buttons
	   PLCButton = new JButton("To PLC"); //create one of the buttons
		TrackInfoButton = new JButton("To Track Info Hub"); //create one of the buttons
		Test = new JButton("Test");
   }



   
   
}
