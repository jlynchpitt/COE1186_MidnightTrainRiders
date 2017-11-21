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
import java.util.Random; 

public class WaysideFunctions  //this is a test class designed to function as a dummy CTC and TrackModel sending information
{
	public static Random rand = new Random();
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
	public static int[][]  ScheduleArray = {OccupiedTrack1,OccupiedTrack2, OccupiedTrack3, OccupiedTrack4, OccupiedTrack5, OccupiedTrack6}; 
	
	
	public static int[] IncomingArray;
	public static WaysideFunctionsHub obj = new WaysideFunctionsHub();
	
	public static void main(String[] args) 
	{

		WaysideFunctionsHub.WaysideController_Switch(5);
		WaysideFunctionsHub.WaysideController_TrackOccupancy(ProtoArray);
		WaysideFunctionsHub.WaysideController_BrokenTrack(ProtoArray);
	}
	static int counter = 0;
	static int counter2 = 0;
	Timer trimer = new Timer();

	public static void SwitchTester()
	{
		if (counter ==0)
		{	
			WaysideFunctionsHub.WaysideController_Switch(1027);
			counter++;
		}
		else if (counter == 1)
		{
			WaysideFunctionsHub.WaysideController_Switch(1038);
			counter++;
		}
		else if (counter == 2)
		{
			WaysideFunctionsHub.WaysideController_Switch(2062);
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
		System.out.println("");
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
	   /*
	   for (int x = 0; x < IncomingTrackOccupancyArray.length; x++)
	   {
		   System.out.println("CTC_getOccupancy: " + IncomingTrackOccupancyArray[x]);
	   }
	   */
	   for (int x = 0; x < IncomingTrackOccupancyArray.length; x++)
	   {
		   //System.out.println("CTC_getOccupancy: " + IncomingTrackOccupancyArray[x]);
		   int TrackNumber = IncomingTrackOccupancyArray[x];
		   int NextTrack = 0;
		   int LateralPosition = 0;
		   int XPosition = 0;
		   for (int y = 0; y < ScheduleArray.length; y++)
		   {
			   for (int z = 0; z < ScheduleArray[y].length; z++)
			   {
				   if (TrackNumber == ScheduleArray[y][z] && y !=ScheduleArray.length-1)
				   {
					   NextTrack = ScheduleArray[y+1][z];
					   LateralPosition = y;
					   XPosition = z;
				   }
				   else if (TrackNumber == ScheduleArray[y][z] && y == ScheduleArray.length-1)
				   {
					   NextTrack = ScheduleArray[0][z];
					   LateralPosition = y;
					   XPosition = z;
				   }
			   }
		   }
		   //WaysideFunctionsHub.OccupiedSpeedAuthority(IncomingArray[x], rand.nextInt(31) + 30, rand.nextInt(51) + 30);
		   //System.out.println("Current Track: " + IncomingArray[x] + "\tNext Track: " + NextTrack);
		   int[] AuthorityArray = new int[ScheduleArray.length - LateralPosition];
		   for (int a = LateralPosition; a < ScheduleArray.length; a++)
		   {
			   AuthorityArray[a - LateralPosition] = ScheduleArray[a][XPosition];
			   //System.out.println(AuthorityArray[a - LateralPosition]);
			   //System.out.println(a);
		   }
		   WaysideFunctionsHub.OccupiedSpeedAuthority(IncomingTrackOccupancyArray[x], rand.nextInt(31) + 30, AuthorityArray);
	   }
	   
   }
   
   public static void CTC_getBrokenTrack(int[] IncomingBrokenTrackArray) //call this from the CTC to send the CTC info 
   {
	   IncomingArray = IncomingBrokenTrackArray;
	   /*
	   for (int x = 0; x < IncomingArray.length; x++)
	   {
		   System.out.println("Broken: " + IncomingArray[x]);
	   }
	   */
	   
   }
   
   public static void TrackModel_setSpeedAuthority(int TrackID, int Speed, int Authority) //call this from the TrackModel to send the TM info 
   {
	   //System.out.println(TrackID);;
	   //System.out.println(Speed);
	   /*
	   for (int x = 0; x <  Authority.length; x++)
	   {
		   System.out.println(Authority[x]);
	   }
	   */
   }
   
	public static void TrackModel_setAuthority(int[] IncomingAuthorityArray) //call this from the TM to send the TM info 
   {
		IncomingArray = IncomingAuthorityArray;
		/*
	   for (int x = 0; x < IncomingArray.length; x++)
	   {
		   System.out.println("Authority: " + IncomingArray[x]);
	   }  
	   */ 

   }
   
}
