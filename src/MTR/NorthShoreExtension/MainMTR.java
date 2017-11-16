/**
 * 
 */
package MTR.NorthShoreExtension;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainControllerHelper;
import MTR.NorthShoreExtension.UI.LoadTrackModelUI;

/**
 * @author Joe Lynch
 * 
 *
 */
public class MainMTR {
	public static TrainControllerHelper tcHelper;
	public static boolean fullUI = false;
	private static DBHelper dbHelper = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		fullUI = true;
		
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

}
