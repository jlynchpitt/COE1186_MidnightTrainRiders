/*
* Filename: switchTester.java
* Author: Matt Snyder
* Last Edited: 11/11/2017
* File Description: The back-end operations of the Switch Tester sub-component
*/

package MTR.NorthShoreExtension.Backend.CTCSrc;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;
import javax.swing.*;

import MTR.NorthShoreExtension.Backend.WaysideController.WaysideFunctionsHub;

public class switchTester {
	//Test the switch that was selected from the UI
		public static void testSwitch(int switchID) throws AWTException {
			//call the function from the WaysideController. 1=Successfully switched, 0=busy
			int testResults = WaysideFunctionsHub.WaysideController_Switch(switchID);
			//int testResults = 0; //For testing, remove once integrated.
			if (testResults == 1) {
				//Switch successful, 
			}
			else if (testResults == 0) {
				//Switch failed, notify that that the switch is busy
				SystemTray tray = SystemTray.getSystemTray();
				Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
				TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
				trayIcon.setImageAutoSize(true);
				trayIcon.setToolTip("System tray icon demo");
				tray.add(trayIcon);
				trayIcon.displayMessage("Switch Busy", "Switch " + switchID + " is busy. \nPlease try again later.", MessageType.INFO);
			}
		}
}
