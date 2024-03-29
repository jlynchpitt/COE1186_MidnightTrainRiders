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
import MTR.NorthShoreExtension.UI.TrainModelUI;

/**
 * @author Joe Lynch
 * 
 *
 */
public class MainMTR {
	public static TrainControllerHelper tcHelper = null;
	public static boolean fullUI = false;
	private static DBHelper dbHelper = null;
	private static StaticTrackDBHelper staticDBHelper = null;
	private static TrainModelUI trainModelUI; // = new TrainModelUI();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		fullUI = true;
		trainModelUI = new TrainModelUI();
		
		tcHelper = new TrainControllerHelper();
		
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
	
	public static TrainControllerHelper getTrainControllerHelper() {
		if(tcHelper != null) {
			return tcHelper;
		}
		else {
			tcHelper = new TrainControllerHelper();
			return tcHelper;
		}
	}
	
	public static StaticTrackDBHelper getStaticTrackDBHelper() {
		if(staticDBHelper != null) {
			return staticDBHelper;
		}
		else {
			staticDBHelper = new StaticTrackDBHelper();
			if(fullUI) {
				staticDBHelper.loadFileIntoDB("green_staticTrackInfo.csv");
				staticDBHelper.loadFileIntoDB("red_staticTrackInfo.csv"); //Causes read in error - array out of bounds
			}
			else{
				staticDBHelper.loadFileIntoDB("test_staticTrackInfo.csv");
			}
			return staticDBHelper;
		}
	}	
	
	public static TrainModelUI getTrainModelUI() {
		if(trainModelUI != null) {
			return trainModelUI;
		}
		else {
			return null;
		}
		/*else {
			System.out.println("CREATING NEW TRAIN MODEL");
			trainModelUI = new TrainModelUI();
			return trainModelUI;
		}*/
	}
}
