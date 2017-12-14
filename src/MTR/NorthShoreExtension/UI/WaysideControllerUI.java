/*
 * Filename: WaysideControllerUI.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.UI;

import java.awt.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.Stack;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.WaysideController.WaysideFunctions;
import MTR.NorthShoreExtension.Backend.WaysideController.WaysideFunctionsHub;
import MTR.NorthShoreExtension.UI.TrackModelUI.TrackGraphic;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.WaysideController.WaysideFunctions;
import MTR.NorthShoreExtension.UI.TrackModelUI.TrackGraphic;
 
public class WaysideControllerUI extends JFrame{
	public static String SampleCode;
	public static String FilePath = "";
	//basic frame and components
	public static JPanel plc = new JPanel();
	public static JPanel TI = new JPanel();
	public static JPanel SC = new JPanel();
	public static JPanel TestPanel = new JPanel();
	public static JFrame f = new JFrame("Wayside Controller");  //create frame with frame name
	public static JFrame frame = new JFrame("Track Model UI");
	public static TextArea text = new TextArea(SampleCode);  //create text area
	public static JButton SaveButton = new JButton("Save"); //create one of the buttons
	//public static JButton SaveButton = new JButton("Save");
	public static JButton SwtchCtrlButton = new JButton("To Switch Control Hub"); //create one of the buttons
	public static JButton PLCButton = new JButton("To PLC"); //create one of the buttons
	public static JButton TrackInfoButton = new JButton("To Track Info Hub"); //create one of the buttons
	public static JButton Test = new JButton ("Tester");
	public static JButton Browse = new JButton ("Browse");
	public static int FrameTracker = 0;
	public static WaysideFunctions obj = new WaysideFunctions();
	public static DefaultTableModel dm = new DefaultTableModel();
	public static DefaultTableModel om = new DefaultTableModel();
	public static DefaultTableModel am = new DefaultTableModel();
	public static DefaultTableModel dm1 = new DefaultTableModel();
	public static DefaultTableModel bm = new DefaultTableModel();
	public static boolean TablesCreated = false;
	public static boolean OccupiedTablesCreated = false;
	public static JTable table1;
	public static JTable table;
	public static JTable BrokenTracksTable;
	public static JTable lighttable = new JTable(am);
	public static JScrollPane scroll1;
	public static JScrollPane scroll;
	public static JScrollPane scroll2;
	public static JScrollPane scroll3;
	public static int Scroll1Height = 75;
	static DBHelper load;
	static TrackModelUI instance;
	
	
	public static TrackGraphic trackGraphic = null;
	
    final static String BUTTONPANEL = "Tab with JButtons";
    final static String TEXTPANEL = "Tab with JTextField";
    final static String PLCPANEL = "PLC";
    final static String SWITCHPANEL = "Switch Ctrl Hub";
    final static String TRACKINFO = "Track Info";
    final static String TESTPan = "Test Panel";
    final static int extraWindowWidth = 100;
	 
    public static void createAndShowWaysideControlGUI() throws IOException 
	{
    	TablesCreated = true;
    	load = MainMTR.getDBHelper();
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
         
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	  
      System.out.println("TEST");
      
	  
   }
    
    
    public static void OccupiedTrackChartManager()
    {
    	if (!OccupiedTablesCreated)
  	   {
  		  dm1.setDataVector(new Object[][] { { "Color", "Black", "Track", "length", "speed" }}, new Object[] { "Line", "Occupied Track", "Dest Track", "Athrty (ft)", "Speed (mph)" });
  		  OccupiedTablesCreated = true;
  	   }
    }
    
    public static void SwitchSwitcher(int x)  //switch based on location in the chart
    {
 	   if (TablesCreated)
 	   {
 		   //System.out.println(dm.getRowCount());
 		   Object placeholder = dm.getValueAt(x, 3);
 		   dm.setValueAt(dm.getValueAt(x, 2), x,3);
 		   dm.setValueAt(placeholder, x,2);
 	   }
 	   
    }

    //updates the table of occupied tracks
    public static void OccupiedTrackTableUpdater(Object[][] ObjectArray)
    {
    	
 	   System.out.println("DISPLAYING OCCUPIED TRACK");
 	   if (OccupiedTablesCreated)
 	   {
 		   
 		   System.out.println("TABLE HAS BEEN MADE");
 		   //go through each train on the track
 		   for (int x = 0; x < ObjectArray.length; x++)
 		   {
 		   	
 			   //dm1.addRow(ObjectArray[x]);
 			   
 			   for (int y = 0; y < ObjectArray[x].length-2; y++)
 			   {
 				   
 				   if (x > dm1.getRowCount()-1)
 				   {
 					   dm1.addRow(ObjectArray[x]);
 				   }
 				   
 				   else
 				   {
 					   dm1.setValueAt(ObjectArray[x][y], x, y);
 				   }
 				   /*
 				   Scroll1Height = dm1.getRowCount()*(20); 
 				   scroll1.setPreferredSize(new Dimension(500, Scroll1Height));
 				   //System.out.println(Scroll1Height);
 			   */
 			   }
 			   
 			   
 				   //System.out.println(x);
 				    
 		   }
 		   
 	   }
 	   
 	 
    }
    
    
    //updates occupied tracks with authority 
    public static void OccupiedTrackAuthoritySpeedUpdater(int TrackID, int NextTrack, int AuthorityDist, int Speed)
    {
    		OccupiedTrackChartManager();
 		   String LineColor = null;
 		   String BlockNumber =  Integer.toString(TrackID).substring(1);
 		   int firstDigit = Character.getNumericValue(Integer.toString(TrackID).charAt(0));
 		   
 		   if (firstDigit == 1)
 		   {
 			   LineColor = "Red";
 		   }
 		   if (firstDigit == 2)
 		   {
 			   LineColor = "Green";
 		   }
 		   //gets color
 		   System.out.println("COLOR: " + LineColor + " BLOCKS: " + BlockNumber + " NEXT TRACK: " + NextTrack);
 		   
 		   //go through all the rows
 		   for (int x = 0; x < dm1.getRowCount(); x++)
 		   {
 			   System.out.println("CHECKING TABLE FOR UPDATE");
 			   System.out.println(dm1.getValueAt(x, 0) + " to " + LineColor);
 			   if (dm1.getValueAt(x, 0).equals(LineColor) || dm1.getValueAt(x, 0).equals("Color"))
 			   {
 				   System.out.println("COLOR FOUND");
 				  System.out.println(dm1.getValueAt(x, 1) + " to " + TrackID);
 				   if (dm1.getValueAt(x, 2).equals(Integer.toString(TrackID).substring(1)) || dm1.getValueAt(x, 1).equals("Black"))
 				   {
 					   System.out.println("NUMBER FOUND");
 					   dm1.setValueAt(Integer.toString(NextTrack).substring(1), x, 2);
 					   dm1.setValueAt(Math.round((AuthorityDist * 0.3048) * 100) / 100, x, 3);
 					   dm1.setValueAt(Speed, x, 4);
 				   }
 				   
 			   }
 			   
 		   }
 		  table1.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
 		  for (int y = 0; y < am.getRowCount(); y++)
		   {

			   am.setValueAt("GREEN", y, 0);
				  	//System.out.println("CASH ME OUTSIDE!!!!");
			   
		   }
 		   for (int x = 0; x < dm1.getRowCount(); x++)
		   {
 			   for (int y = 0; y < am.getRowCount(); y++)
 			   {
 				   if (dm1.getValueAt(x, 1).equals(am.getValueAt(y, 2)) && dm1.getValueAt(x, 0).equals(am.getValueAt(y, 1)))
				   {
 					   am.setValueAt("RED", y, 0);
	 				  	//System.out.println("CASH ME OUTSIDE!!!!");
				   }
 			   }
 			  //System.out.println("I know Kung FU: " + dm1.getValueAt(x, 2) + " || Lights: " + am.getValueAt(0, 2));
 			  //System.out.println("EAT THIS!: " + dm1.getValueAt(x, 0) + " || Lights: " + am.getValueAt(0, 1));
 			 
		   }
 		  lighttable.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
 		   
 	   
 		   
 		   
 	   
 		  //table.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
 	   
    }
    
    //figures out which position in the switch chart to switch
    public static void SwitchChartUpdater(int ID)  //update with actual information
    {
 	   if (TablesCreated)
 	   {
 		   String BlockNumber =  Integer.toString(ID).substring(1,4);

 		   //LineColor = DB.getColor(IncomingTrackOccupancyArray[x]);
 		   for (int x = 0; x < dm.getRowCount(); x++)
 		   {
 			   if (Integer.parseInt(BlockNumber) == Integer.parseInt((String)(dm.getValueAt(x,1))))
 			   {
 				   System.out.println("BLOCK NUMBER: " + BlockNumber);
 				   SwitchSwitcher(x);
 			   }
 		   }
 	   }
 	   
    }
    
    
    
    public static void ActionAdder()
    {
    	frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
 	//button that browses files
 		Browse.addActionListener(new ActionListener()
 		{
 		  public void actionPerformed(ActionEvent e)
 		  {

 			  try {
 				Reader();
 			} catch (IOException e1) {
 				// TODO Auto-generated catch block
 				e1.printStackTrace();
 			}
 		  }
 		});		
 		//save button
 		SaveButton.addActionListener(new ActionListener()
 		{
 		  public void actionPerformed(ActionEvent e)
 		  {

 			  try {
 				Saver();
 			} catch (IOException e1) {
 				// TODO Auto-generated catch block
 				e1.printStackTrace();
 			}
 		  }
 		});	

    }
    
    public static void Reader() throws IOException
    {
 	   String Block = "";
 	   JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
 		//File text = new File("C:\\Users\\Owner\\Documents\\GitHub\\COE1186_MidnightTrainRiders\\src\\MTR\\NorthShoreExtension\\UI\\TrainControlUI.java");
 		int returnValue = jfc.showOpenDialog(null);
 		// int returnValue = jfc.showSaveDialog(null);

 		if (returnValue == JFileChooser.APPROVE_OPTION) {
 			File selectedFile = jfc.getSelectedFile();
 			//File text = new File("C:/temp/test.txt");
 		     
 	        //Creating Scanner instnace to read File in Java
 	        //Scanner scnr = new Scanner(Test.class.getResourceAsStream("temp.txt"));
 	        Scanner scnr = new Scanner(selectedFile);
 	     
 	        //Reading each line of file using Scanner class
 	        int lineNumber = 1;
 	        while(scnr.hasNextLine()){
 	            String line = scnr.nextLine();
 	            Block = Block + line + "\n";
 	            //System.out.println("line " + lineNumber + " :" + line);
 	            lineNumber++;
 	        } 
 			System.out.println(selectedFile.getAbsolutePath());
 			FilePath = selectedFile.getAbsolutePath();
 			//System.out.println(Block);
 			SampleCode = Block;
 			text.setText(SampleCode);
 			//System.out.println(selectedFile.getRelativePath());
 		}
    }
    
    public static void Saver() throws IOException
    {
 	   //System.out.println(text.getText());
 	   
 	   BufferedWriter bw = null;
 		FileWriter fw = null;

 		try {

 			String content = text.getText();

 			fw = new FileWriter(FilePath);
 			bw = new BufferedWriter(fw);
 			bw.write(content);

 			System.out.println("Done");

 		} catch (IOException e) {

 			e.printStackTrace();

 		} finally {

 			try {

 				if (bw != null)
 					bw.close();

 				if (fw != null)
 					fw.close();

 			} catch (IOException ex) {

 				ex.printStackTrace();

 			}

 		}
 		
    }
    
    public static void getDB(DBHelper db) {
			load = db;
	}

	public static DBHelper sendDB() {
		return load;
	}

	public static void UpdateBrokenTracks(int [] BrokenTrackArray)
	{
		bm.setDataVector(new Object[][] { }, new Object[] { "Broken Tracks"});
		for (int x = 0; x < BrokenTrackArray.length; x++)
		{
			Object[] Tracks = {BrokenTrackArray[x]};
			bm.addRow(Tracks);
		}
	}
	
    public void addComponentToPane(Container pane) {
    	ActionAdder();
    	//----------------------------------------------
    	
        JTabbedPane tabbedPane = new JTabbedPane();
        //PLC
      //---------------------------------------------------------------------------------------------------------------------------------        
        JPanel plc = new JPanel();
        String[] choices = { "South Green Line", "North Green Line", "South Red Line", "North Green Line"};
        Box ButtonBox;
 	   	
        //final JComboBox<String> cb = new JComboBox<String>(choices);
        ButtonBox = Box.createVerticalBox();
        ButtonBox.add( Box.createVerticalStrut( 25 ) );
        //ButtonBox.add(cb, BorderLayout.NORTH);
        ButtonBox.add(Browse, BorderLayout.EAST);
        ButtonBox.add(SaveButton, BorderLayout.EAST);
 	   	plc.add(text, BorderLayout.WEST);  //add tframo e 
 	   	plc.add(ButtonBox, BorderLayout.WEST);
 	 
 	   	//Switches
 	   	//---------------------------------------------------------------------------------------------------------------------------------        
 	   Stack<Integer> GreenSwitch = new Stack<>();
	   Stack<Integer> RedSwitch = new Stack<>();
	   int GreenTrack = 2001;
	   int RedTrack = 1001;
	   System.out.println("TEST PANEL SET");
	   
	   while (!load.getInfrastructure(GreenTrack).equalsIgnoreCase("none"))
	   {
		   if (load.getInfrastructure(GreenTrack).equalsIgnoreCase("SWITCH")||load.getInfrastructure(GreenTrack).equalsIgnoreCase("SWITCH; UNDERGROUND")||load.getInfrastructure(GreenTrack).equalsIgnoreCase("SWITCH TO YARD")||load.getInfrastructure(GreenTrack).equalsIgnoreCase("SWITCH FROM YARD"))
		   {
			   
			   //System.out.println(GreenTrack);
			   GreenSwitch.push(GreenTrack);
		   }
		   
		   GreenTrack++;		   
	   }
	   
	   //System.out.println(RedTrack);
	   while (!load.getInfrastructure(RedTrack).equalsIgnoreCase("none"))
	   {
		   if (load.getInfrastructure(RedTrack).equalsIgnoreCase("Switch")||load.getInfrastructure(RedTrack).equalsIgnoreCase("SWITCH; UNDERGROUND")||load.getInfrastructure(RedTrack).equalsIgnoreCase("SWITCH TO/FROM YARD"))
		   {
			   //System.out.println(RedTrack);
			   RedSwitch.push(RedTrack);
		   }
		   
		   RedTrack++;		   
	   }
	   
	   dm.setDataVector(new Object[][] { }, new Object[] { "Line", "Track", "Dest Track", "Alt Track" });
	   for(Integer obj : RedSwitch)
	   {
		   
		   String Color = Integer.toString(obj).substring(1,4);
		   Object[] Switches = {"Red", Color, Integer.toString(load.getNextTrack(obj, obj+1)).substring(1), Integer.toString(load.getAltTrack(obj)).substring(1)};
		   Object[] Switches2 = {"Red", Color, Integer.toString(load.schedNextTrack(obj, obj+1)).substring(1), Integer.toString(load.getAltTrack(obj)).substring(1)};
		   Object[] DUMDUM = Switches;
		   if(load.getNextTrack(obj, obj+1) == load.getAltTrack(obj)) 
		   {
			   DUMDUM = Switches2;
		   }
		   

		   
		   dm.addRow(DUMDUM);
	       //System.out.println(load.getInfrastructure(obj) + ": " + obj + " at " + load.getSwitch(obj) + " With the Next: " + load.getNextTrack(obj, obj+1) + " or " + load.getAltTrack(obj));
	   }
	   for(Integer obj : GreenSwitch)
	   {
		   String Color = Integer.toString(obj).substring(1,4);
		   Object[] Switches = {"Green", Color, Integer.toString(load.getNextTrack(obj, obj+1)).substring(1), Integer.toString(load.getAltTrack(obj)).substring(1)};
		   Object[] Switches2 = {"Green", Color, Integer.toString(load.schedNextTrack(obj, obj+1)).substring(1), Integer.toString(load.getAltTrack(obj)).substring(1)};
		   Object[] DUMDUM = Switches;
		   if(load.getNextTrack(obj, obj+1) == load.getAltTrack(obj)) 
		   {
			   DUMDUM = Switches2;
		   }
		   

		   
		   dm.addRow(DUMDUM);
	       //System.out.println(load.getInfrastructure(obj) + ": " + obj + " at " + load.getSwitch(obj) + " With the Next: " + load.getNextTrack(obj, obj+1) + " or " + load.getAltTrack(obj));
	   }

		
	   Color red = new Color(255, 0, 0);
	   Color ivory = new Color(255, 255, 208);
	   //dm.setBackground(red);
	   
		table = new JTable(dm);
		table.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(500,260));
		SC.add(scroll, BorderLayout.WEST);
		//SC.add(picLabel);
      	//Track Info	
      	//---------------------------------------------------------------------------------------------------------------------------------        
            JPanel TI = new JPanel();
            OccupiedTrackChartManager();
            	
            Stack<Integer> Lights = new Stack<>();
     	   int GreenLights = 2001;
     	   int RedLights = 1001;
     	   System.out.println("TEST PANEL SET");
     	   
     	   while (!load.getInfrastructure(GreenLights).equalsIgnoreCase("none"))
     	   {
     		   if (load.getInfrastructure(GreenLights).equalsIgnoreCase("RAILWAY CROSSING"))
     		   {
     			   
     			   //System.out.println(GreenTrack);
     			   Lights.push(GreenLights);
     		   }
     		   
     		   GreenLights++;		   
     	   }
     	  while (!load.getInfrastructure(RedLights).equalsIgnoreCase("none"))
    	   {
    		   if (load.getInfrastructure(RedLights).equalsIgnoreCase("RAILWAY CROSSING"))
    		   {
    			   
    			   //System.out.println(GreenTrack);
    			   Lights.push(RedLights);
    		   }
    		   
    		   RedLights++;		   
    	   }
     	  
     	 am.setDataVector(new Object[][] { }, new Object[] { "Color", "Line", "Track"});
  	   for(Integer obj : Lights)
  	   {
  		   //System.out.println("LIGHTS: " + Integer.toString(obj));
  		   String ColorLine = "Green";
  		   int Digit = Character.getNumericValue(Integer.toString(obj).charAt(0));
		   
		   if (Digit == 1)
		   {
			   ColorLine = "Red";
		   }
		   if (Digit == 2)
		   {
			   ColorLine = "Green";
		   }
  		   Object[] LightChart = {"GREEN",ColorLine,Integer.toString(obj).substring(1)};

  		   

  		   
  		  am.addRow(LightChart);
  	       //System.out.println(load.getInfrastructure(obj) + ": " + obj + " at " + load.getSwitch(obj) + " With the Next: " + load.getNextTrack(obj, obj+1) + " or " + load.getAltTrack(obj));
  	   }

  	 table1 = new JTable(dm1);
  	 	
    	    bm.setDataVector(new Object[][] { }, new Object[] { "Broken Tracks"});
    	    BrokenTracksTable = new JTable(bm);

    	    lighttable = new JTable(am);
    	    lighttable.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
    	    lighttable.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());
    	    scroll1 = new JScrollPane(table1);
    		scroll2 = new JScrollPane(lighttable);
    		scroll3 = new JScrollPane(BrokenTracksTable);
    		scroll1.setPreferredSize(new Dimension(500,250));
    		scroll2.setPreferredSize(new Dimension(250,Scroll1Height));
    		scroll3.setPreferredSize(new Dimension(150,Scroll1Height));

    	TI.add(scroll1, BorderLayout.WEST);
    	TI.add(scroll2, BorderLayout.EAST);
    	TI.add(scroll3, BorderLayout.SOUTH);
    	
    	
    	//test
        //---------------------------------------------------------------------------------------------------------------------------------        
          JPanel TestPanel = new JPanel();
          Object[] columnName = {"Yes", "No"};
          Object[][] data = {
                  {"Y", "N"},
                  {"N", "Y"},
                  {"Y", "N"}
          };

          JTable testtable2 = new JTable(data, columnName);
          testtable2.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
          //table.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());

          TestPanel.add(new JScrollPane(testtable2));
 	 //---------------------------------------------------------------------------------------------------------------------------------
        
    	tabbedPane.addTab(PLCPANEL, plc);
        tabbedPane.addTab(SWITCHPANEL, SC);
        tabbedPane.addTab(TRACKINFO, TI);
        pane.add(tabbedPane, BorderLayout.CENTER);
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
    	//System.out.println(load.getInfrastructure(2012) + ": " + load.getSwitch(2012) + " Dest: " + load.schedNextTrack(2012, 2011) + " OR: " + load.schedNextTrack(2012, 2013)+ "ALT: " + load.getAltTrack(2012));
        //Create and set up the window.
        JFrame frame = new JFrame("Wayside Controller");
    
 
        //Create and set up the content pane.
        WaysideControllerUI demo = new WaysideControllerUI();
        demo.addComponentToPane(frame.getContentPane());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) throws IOException {
    	
    	createAndShowWaysideControlGUI();
    }
}
class CustomRenderer extends DefaultTableCellRenderer 
{
	public static Color lightred = new Color(255, 200, 200);
	public static Color lightgreen = new Color(200, 255, 200);
	public static Color darkred = new Color(255, 0, 0);
	public static Color darkgreen = new Color(0, 255, 0);
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
    	
    	System.out.println("TEST TEST TEST");
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cellComponent.setBackground(Color.YELLOW);
        
        
        if(table.getValueAt(row, column).equals("Red")){
            cellComponent.setBackground(lightred);
        } else if(table.getValueAt(row, column).equals("Green")){
            cellComponent.setBackground(lightgreen);
        }
        else if(table.getValueAt(row, column).equals("RED")){
            cellComponent.setBackground(darkred);
        } else if(table.getValueAt(row, column).equals("GREEN")){
            cellComponent.setBackground(darkgreen);
        }
        
        return cellComponent;
    }

    /*
    public Component getTableCellRendererComponent2(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
    	System.out.println("TEST TEST TEST");
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cellComponent.setBackground(Color.RED);
        if(table.getValueAt(row, column).equals("Red"))
        {
            cellComponent.setBackground(Color.RED);
        } else if(table.getValueAt(row, column).equals("Green"))
        {
            cellComponent.setBackground(Color.GREEN);
        }

        return cellComponent;
    }
    */
}
