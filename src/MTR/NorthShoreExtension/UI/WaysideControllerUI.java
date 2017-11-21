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



public class WaysideControllerUI
{
	//imports all packages
	public static String SampleCode = "Int total = 0;\n Public static void main (String [] args)\n{\n    If (traindist <= 100)\n    {\n        Do\n        {\n            Close gate\n            Signal light\n        }\n    }\n    Public boolean distmeas(int trackcount)\n    {\n        For (int x = 0; x < trackcount; x++)\n        {\n            Total += x; \n        }\n        If (total <= standarddist)\n        {\n            Return true;\n        }\n        Else\n        {\n            Return false;\n        ]\n    }\n}";
	//basic frame and components
	public static JPanel plc = new JPanel();
	public static JPanel TI = new JPanel();
	public static JPanel SC = new JPanel();
	public static JFrame f = new JFrame("Wayside Controller");  //create frame with frame name
	public static Component text = new TextArea(SampleCode);  //create text area
	public static JButton SaveButton = new JButton("Save"); //create one of the buttons
	//public static JButton SaveButton = new JButton("Save");
	public static JButton SwtchCtrlButton = new JButton("To Switch Control Hub"); //create one of the buttons
	public static JButton PLCButton = new JButton("To PLC"); //create one of the buttons
	public static JButton TrackInfoButton = new JButton("To Track Info Hub"); //create one of the buttons
	public static int FrameTracker = 0;
	
	
	
    
	  
	  //public static int[] ProtoArray = {0,1,2,3,4,5,6};
   public static void main(String[] args) //main body
   {
	   //setup panels
	   PLCSetup();
	   TrackInfoSetup();
	   SwitchSetup();
	   //add action
	    ActionAdder();  
	//create frame 
	  int width = 750;
      int height = 500;
      f.setSize(width, height);
	  f.getContentPane().add(plc);
      f.setVisible(true);
	  
	  
	  
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
   
<<<<<<< HEAD
=======
   public static void OccupiedTrackAuthoritySpeedUpdater(int TrackID, int NextTrack, int Speed, int AuthorityDist)
   {
	   /*
	   for (int x = 0; x < dm1.getRowCount(); x++)
	   {
		   System.out.print("Current Chart: ");
		   for (int y = 0; y < dm1.getColumnCount(); y++)
		   {
			   System.out.print("||" + dm1.getValueAt(x, y));
		   }
		   System.out.println("");
	   }
	   */
	   
		   //dm1.addRow(ObjectArray[x]);
		   
		   String LineColor = null;
	   String BlockNumber =  Integer.toString(TrackID).substring(1,4);
	   int firstDigit = Character.getNumericValue(Integer.toString(TrackID).charAt(0));
	   if (firstDigit == 1)
	   {
		   LineColor = "Red";
	   }
	   if (firstDigit == 2)
	   {
		   LineColor = "Green";
	   }
	   for (int x = 0; x < dm1.getRowCount(); x++)
	   {
		   if (dm1.getValueAt(x, 0).equals(LineColor))
		   {
			   //System.out.println("PAIN TRAIN: " + dm1.getValueAt(x, 0));
			   //System.out.println(dm1.getValueAt(x, 1) + "-----" + BlockNumber + "----------" + TrackID);
			   if (dm1.getValueAt(x, 1).equals(BlockNumber))
			   {
				   dm1.setValueAt(NextTrack, x, 2);
				   dm1.setValueAt(AuthorityDist, x, 3);
			   }
			   
		   }
		   
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
>>>>>>> parent of 3fc3e00... Basic Logic unit up
   //register all panels
   public static void ComponentAdder()
   {
	   PLCSetup();
	   TrackInfoSetup();
	   SwitchSetup();
   }
   
 //set up plc panel  
   public static void PLCSetup()
   {
	   
	   
	   SwtchCtrlButton = new JButton("To Switch Control Hub"); //create one of the buttons
	   PLCButton = new JButton("To PLC"); //create one of the buttons
		TrackInfoButton = new JButton("To Track Info Hub"); //create one of the buttons
	   
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
	   plc.add(text, BorderLayout.WEST);  //add tframo e 
	   plc.add(ButtonBox, BorderLayout.WEST);
	   //f.getContentPane().add(plc);
   }
  

//set up switch panel
  public static void SwitchSetup()
   {
	   
	   SwtchCtrlButton = new JButton("To Switch Control Hub"); //create one of the buttons
	   PLCButton = new JButton("To PLC"); //create one of the buttons
		TrackInfoButton = new JButton("To Track Info Hub"); //create one of the buttons
		ActionAdder();
		
	   DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][] { { "Green", "2", "A1", "C2", "C5" },
			{ "Green", "3", "A2", "A1", "B2" },
			{ "Green", "4", "A3", "B2", "D6" }		}, new Object[] { "Line", "Block", "Track", "Dest Track", "Alt Track" });

		JTable table = new JTable(dm);
		JScrollPane scroll = new JScrollPane(table);
		SC.add(scroll, BorderLayout.WEST);
		
		
		
		
		Box ButtonBox1;
		
		Box ButtonBox4;
		ButtonBox1 = Box.createVerticalBox();
		  
		   ButtonBox1.add( Box.createVerticalStrut( 25 ) );
		   ButtonBox4 = Box.createVerticalBox();
		  
		   ButtonBox4.add( Box.createVerticalStrut( 25 ) );
		   
		   ButtonBox1.add(PLCButton, BorderLayout.EAST);
		    
		 ButtonBox1.add(TrackInfoButton, BorderLayout.EAST);	  //add to frame
		
		SC.add(ButtonBox1, BorderLayout.EAST);
		
		//f.getContentPane().add(SC);
		   
	   
   }
     
   //set up track info panel
   
   public static void TrackInfoSetup()
   {
	   SwtchCtrlButton = new JButton("To Switch Control Hub"); //create one of the buttons
	   PLCButton = new JButton("To PLC"); //create one of the buttons
		TrackInfoButton = new JButton("To Track Info Hub"); //create one of the buttons
		
		ActionAdder();
	   DefaultTableModel dm = new DefaultTableModel();
    dm.setDataVector(new Object[][] { { "Green", "C5", "Swtch", "20 mi" },
        { "Red", "D7", "T4", "12mi" } }, new Object[] { "Line", "Occupied Track", "Dest Track", "Athrty" });

    JTable table = new JTable(dm);


//---------------------------------------------------------------------		
	DefaultTableModel am = new DefaultTableModel();
    am.setDataVector(new Object[][] { { "Green", "C5" },
        { "Red", "A2" } }, new Object[] { "Xing", "Line"});

    JTable lighttable = new JTable(am);
    JScrollPane scroll = new JScrollPane(table);
	JScrollPane scroll2 = new JScrollPane(lighttable);
	scroll.setPreferredSize(new Dimension(500,75));
	scroll2.setPreferredSize(new Dimension(100,75));
	TI.add(scroll, BorderLayout.WEST);
	TI.add(scroll2, BorderLayout.EAST);
	
	
	
	
	Box ButtonBox;
	   ButtonBox = Box.createVerticalBox();
	   ButtonBox.add( Box.createVerticalStrut( 100 ) );
	    ButtonBox.add(SwtchCtrlButton, BorderLayout.EAST);
	   ButtonBox.add(PLCButton, BorderLayout.EAST);
    
	TI.add(ButtonBox, BorderLayout.EAST);
	//f.getContentPane().add(TI);
	
   }


   
   
}
