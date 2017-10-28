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
	public static int[] AuthorArray = {2130,2131,2141,2145,2165,2146,2147};
	
	public static int[] WaysideController_Authority()
   {
	   
			return ProtoArray;
	   
   }
   
   public static int[] WaysideController_Speed()
   {

			return ProtoArray;
	   
   }
   
   public static int[] WaysideController_TrackOccupancy()
   {
	   return ProtoArray;
   }
   
   public static int[] WaysideController_BrokenTrack()
   {
	   return ProtoArray;
   }
   
}
