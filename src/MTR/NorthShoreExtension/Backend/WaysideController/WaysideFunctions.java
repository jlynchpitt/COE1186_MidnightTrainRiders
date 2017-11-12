/*
 * Filename: WaysideFunctions.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.Backend.WaysideControllerSrc;
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



public class WaysideFunctions
{
	
	public static int[] ProtoArray = {0,1,2,3,4,5,6};
	public static int[] IncomingArray;
	public static WaysideFunctionsHub obj = new WaysideFunctionsHub();
	
	public static void main(String[] args) 
	{
		WaysideFunctionsHub.WaysideController_Authority(ProtoArray);
		WaysideFunctionsHub.WaysideController_Speed(ProtoArray);
		WaysideFunctionsHub.WaysideController_Switch(5);
		WaysideFunctionsHub.WaysideController_TrackOccupancy(ProtoArray);
		WaysideFunctionsHub.WaysideController_BrokenTrack(ProtoArray);
	}
	
   //---------------------------------------------------------------------------------
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
   
   public static void TrackModel_setSpeed(int[] IncomingSpeedArray) //call this from the TrackModel to send the TM info 
   {
	   IncomingArray = IncomingSpeedArray;
	   for (int x = 0; x < IncomingArray.length; x++)
	   {
		   System.out.println("Speed: " + IncomingArray[x]);
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
