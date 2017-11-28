/**
 * 
 */
package MTR.NorthShoreExtension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.StaticTrackDBHelper;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainControllerHelper;
import MTR.NorthShoreExtension.UI.LoadTrackModelUI;

/**
 * @author Joe Lynch
 * 
 *
 */
public class MainMTR3 {
	public static boolean fullUI = false;
	private static DBHelper dbHelper = null;
	private static StaticTrackDBHelper staticDBHelper = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		fullUI = true;
				
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                LoadTrackModelUI.createAndShowGUI();
            }
        });
	}
	
	public static DBHelper getDBHelper() {
		if(dbHelper != null) {
			return dbHelper;
		}
		else {
			dbHelper = new DBHelper();
			return dbHelper;
		}
	}
}
