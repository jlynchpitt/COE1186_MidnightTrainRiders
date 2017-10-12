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



public class UIFrame
{
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
      
   public static void main(String[] args)
   {
	   
	   PLCSetup();
	   TrackInfoSetup();
	   SwitchSetup();
	    ActionAdder();  
	
	  int width = 750;
      int height = 500;
      f.setSize(width, height);
	  f.getContentPane().add(plc);
      f.setVisible(true);
   } 
   public static void ActionAdder()
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
	  /*
	  PLIC -- 0
	  SC -- 1
	  TI -- 2
	  */

	  SwtchCtrlButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  f.getContentPane().removeAll();
			  FrameTracker = 1;
			  f.getContentPane().add(SC);
		  }
		});
	TrackInfoButton.addActionListener(new ActionListener()
	{
	  public void actionPerformed(ActionEvent e)
	  {

			f.getContentPane().removeAll();
		  FrameTracker = 2;
		  f.getContentPane().add(TI);
	  }
	});
	
	PLCButton.addActionListener(new ActionListener()
	{
	  public void actionPerformed(ActionEvent e)
	  {

			f.getContentPane().removeAll();
		  FrameTracker = 2;
		  f.getContentPane().add(plc);
	  }
	});
		
		

   }
   public static void ComponentAdder()
   {
	   PLCSetup();
	   TrackInfoSetup();
	   SwitchSetup();
   }
   
   
 
   
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
	   plc.add(text, BorderLayout.WEST);  //add to frame 
	   plc.add(ButtonBox, BorderLayout.WEST);
	   //f.getContentPane().add(plc);
   }
  


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
   
   
   public static void TrackInfoSetup()
   {
	   SwtchCtrlButton = new JButton("To Switch Control Hub"); //create one of the buttons
	   PLCButton = new JButton("To PLC"); //create one of the buttons
		TrackInfoButton = new JButton("To Track Info Hub"); //create one of the buttons
		
		ActionAdder();
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