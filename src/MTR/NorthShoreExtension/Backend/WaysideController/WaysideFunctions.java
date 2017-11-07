/*
 * Filename: WaysideFunctions.java
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



public class WaysideFunctions
{
	
	public static int[] ProtoArray = {0,1,2,3,4,5,6};
	
	
	
   //---------------------------------------------------------------------------------
   public static void CTC_getOccupancy(int[] IncomingTrackOccupancyArray)  //call this from the CTC to send the CTC info 
   {
	   //int[] OccupancyArray = new int[4];  
	   int length = IncomingTrackOccupancyArray.length;
	   for (int x = 0; x < length; x++)
	   {
		   System.out.println(IncomingTrackOccupancyArray[x]);
	   }
	   
   }
   
   public static void CTC_getBrokenTrack(int[] IncomingBrokenTrackArray) //call this from the CTC to send the CTC info 
   {
	   //int[] BrokenTrackArray = new int[4];	   
	   int length = IncomingBrokenTrackArray.length;
	   for (int x = 0; x < length; x++)
	   {
		   System.out.println(IncomingBrokenTrackArray[x]);
	   }
	   
   }
   
   public static void TrackModel_setSpeed() //call this from the TrackModel to send the TM info 
   {
	   
   }
   
	public static void TrackModel_setAuthority() //call this from the TM to send the TM info 
   {
	   //int[] AuthorityArray = new int[4];    

   }
   
}