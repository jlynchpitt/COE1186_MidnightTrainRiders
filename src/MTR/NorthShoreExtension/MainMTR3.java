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
import MTR.NorthShoreExtension.Backend.TrackModelSrc.TrackModel;
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
	static int auth[]=new int[3];
	static int trackID = 0;
	
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
	
	public static void moveTrack() {
		if(auth != null && auth.length > 1) {
			int temp[]=new int[auth.length-1];
	
			for(int i = 0; i < auth.length-1; i++) {
				temp[i] = auth[i+1];
			}
			
			auth = null;
			auth = temp;
	    	TrackModel.TrackModel_setSpeedAuthority(auth[0], 30, auth);
			System.out.println("MOVING TEST: if " + auth[0]);
		}
		else if(auth != null && auth.length == 1) {
			System.out.println("MOVING TEST: else if");
			trackID = auth[0]+1;
			auth = null;
	    	TrackModel.TrackModel_setSpeedAuthority(trackID, 30, auth);
		}
		else {
			System.out.println("MOVING TEST: else");
	    	TrackModel.TrackModel_setSpeedAuthority(trackID, 30, null);
		}
	}
	
	public static void dispatchATrain() {
		TrackModel.TrackModel_addTrain(2062, 0);
    	auth[0] = 2062;
    	auth[1] = 2063;
    	auth[2] = 2064;
    	//auth[3] = 2065;
    	//auth[4] = 2066;
    	
    	TrackModel.TrackModel_setSpeedAuthority(2062, 30, auth);
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
