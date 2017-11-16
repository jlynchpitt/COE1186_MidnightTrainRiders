/*
 * Filename: WaysideFunctions.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.Backend.WaysideController;
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
import java.util.Timer;
import java.util.TimerTask;
import MTR.NorthShoreExtension.Backend.DBHelper;

public class WaysideFunctions
{
	//train scheduler
	public static int[] ProtoArray = {0,1,2,3,4,5,6};
	public static int[] ProtoArray2 = {00,11,22,33,44,55,66};
	public static int[] ProtoArray3 = {000,111,222,333,444,555,666};
	public static int[] OccupiedTrack1 = {2062, 2012, 2142, 1054, 1023, 1002};
	public static int[] OccupiedTrack2 = {2063, 2013, 2143, 1055, 1024, 1003};
	public static int[] OccupiedTrack3 = {2064, 2014, 2144, 1056, 1025, 1004};
	public static int[] OccupiedTrack4 = {2065, 2015, 2145, 1057, 1026, 1005};
	public static int[] OccupiedTrack5 = {2066, 2016, 2146, 1058, 1027, 1006};
	public static int[] OccupiedTrack6 = {2067, 2017, 2147, 1059, 1028, 1007};
	public static int[] IncomingArray;
	public static WaysideFunctionsHub obj = new WaysideFunctionsHub();
	
	public static void main(String[] args) 
	{
		WaysideFunctionsHub.WaysideController_Authority(ProtoArray);
		WaysideFunctionsHub.WaysideController_Switch(5);
		WaysideFunctionsHub.WaysideController_TrackOccupancy(ProtoArray);
		WaysideFunctionsHub.WaysideController_BrokenTrack(ProtoArray);
	}
	static int counter = 0;
	static int counter2 = 0;
	Timer trimer = new Timer();
	public static void AuthorityTester()
	{
		if (counter ==0)
		{
			WaysideFunctionsHub.WaysideController_Authority(ProtoArray);
			counter++;
		}
		else if (counter == 1)
		{
			WaysideFunctionsHub.WaysideController_Authority(ProtoArray2);
			counter++;
		}
		else if (counter == 2)
		{
			WaysideFunctionsHub.WaysideController_Authority(ProtoArray3);
			counter = 0;
		}
	}
	public static void SwitchTester()
	{
		if (counter ==0)
		{	
			WaysideFunctionsHub.WaysideController_Switch(2002);
			counter++;
		}
		else if (counter == 1)
		{
			WaysideFunctionsHub.WaysideController_Switch(2003);
			counter++;
		}
		else if (counter == 2)
		{
			WaysideFunctionsHub.WaysideController_Switch(2004);
			counter = 0;
		}
		
	}
	public static void OccupiedTrackTester()
	{
		
		if (counter2 ==0)
		{	
			WaysideFunctionsHub.WaysideController_TrackOccupancy(OccupiedTrack1);
			counter2++;
		}
		else if (counter2 == 1)
		{
			WaysideFunctionsHub.WaysideController_TrackOccupancy(OccupiedTrack2);
			counter2++;
		}
		else if (counter2 == 2)
		{
			WaysideFunctionsHub.WaysideController_TrackOccupancy(OccupiedTrack3);
			counter2++;
		}
		if (counter2 ==3)
		{	
			WaysideFunctionsHub.WaysideController_TrackOccupancy(OccupiedTrack4);
			counter2++;
		}
		else if (counter2 == 4)
		{
			WaysideFunctionsHub.WaysideController_TrackOccupancy(OccupiedTrack5);
			counter2++;
		}
		else if (counter2 == 5)
		{
			WaysideFunctionsHub.WaysideController_TrackOccupancy(OccupiedTrack6);
			counter2 = 0;
		}
		
	}
	
	
	TimerTask task = new TimerTask()
	{
		public void run()
		{
				//System.out.println("Second passed");
				//System.out.println("Second passed" + counter);
				//AuthorityTester();
				SwitchTester();
				OccupiedTrackTester();
				
		}
		
	};
	public void start()
	{
		trimer.scheduleAtFixedRate(task, 1000, 1000);
	}
	public static void Timer()
	{
		//System.out.println("PLACEHOLDER");
		WaysideFunctions CountUp = new WaysideFunctions();
		CountUp.start();
	}
	
   //---------------------------------------------------------------------------------
   //not necessary below
	public static void CTC_getOccupancy(int[] IncomingTrackOccupancyArray)  //call this from the CTC to send the CTC info 
   {
	   //int[] OccupancyArray = new int[4];  
	   IncomingArray = IncomingTrackOccupancyArray;
	   for (int x = 0; x < IncomingArray.length; x++)
	   {
		   System.out.println("Occupancy: " + IncomingArray[x]);
	   }
	   
   }
   
   public static void CTC_getBrokenTrack(int[] IncomingBrokenTrackArray) //call this from the CTC to send the CTC info 
   {
	   IncomingArray = IncomingBrokenTrackArray;
	   for (int x = 0; x < IncomingArray.length; x++)
	   {
		   System.out.println("Broken: " + IncomingArray[x]);
	   }
	   
   }
   
   public static void TrackModel_setSpeedAuthority(int TrackID, int Speed, int[] Authority) //call this from the TrackModel to send the TM info 
   {
	   System.out.println(TrackID);;
	   System.out.println(Speed);
	   for (int x = 0; x <  Authority.length; x++)
	   {
		   System.out.println(Authority[x]);
	   }
   }
   
	public static void TrackModel_setAuthority(int[] IncomingAuthorityArray) //call this from the TM to send the TM info 
   {
		IncomingArray = IncomingAuthorityArray;
	   for (int x = 0; x < IncomingArray.length; x++)
	   {
		   System.out.println("Authority: " + IncomingArray[x]);
	   }   

   }
   
}
