/*
 * Filename: TrainControllerHelper.java
 * Author: Joe Lynch
 * Date Created: 10/9/2017
 * File Description: This is a helper class for the individual Train Controllers and acts
 * 			as a mediator between the front end and backend. One of these must be 
 * 			instantiated within the main function. It holds a list of each Train Controller
 * 			and a timer to trigger the power calculations.
 */
package MTR.NorthShoreExtension.Backend.TrainControllerSrc;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.TrackModelSrc.TrackModel;
import MTR.NorthShoreExtension.Backend.TrainSrc.Train;
import MTR.NorthShoreExtension.UI.TrackModelUI;
import MTR.NorthShoreExtension.UI.TrackModelUI.TrackGraphic;
import MTR.NorthShoreExtension.UI.TrainControlUI;
import MTR.NorthShoreExtension.UI.TrainModelUI;
import MTR.NorthShoreExtension.UI.ctcUI;


/*
 * author: Joseph Lynch
 *
 * Helper class for the train controllers
 * 		keeps track of all train controller objects
 * 		timer to signal train controller objects to calculate next power command
 * 		
 */
public class TrainControllerHelper {
	public static final int ENGINE_FAILURE = 1;
	public static final int SIGNAL_PICKUP_FAILURE = 2;
	public static final int BRAKE_FAILURE = 3;
	
	private double pid_p = 8;
	private double pid_i = 0.25; 
	private Timer powerTimer = new Timer();
	private List<Integer> idList = new ArrayList<>();
	private List<TrainController> tcList = new ArrayList<TrainController>();
	private boolean manualMode = true;
	private int clockMultiplier = 1;
	private TimerTask powerTimerTask;
	private long simulatedClockTime = System.currentTimeMillis();
	private ctcUI ctc_ui = null;
	private final int timeBetweenRedraw = 3000;
	private long lastRedrawTime = 0;
	public static long programStartTime = System.currentTimeMillis();
	
	public TrainControllerHelper(){
		//System.out.println("TCH initialized");
		System.out.println("Start Time: " + programStartTime);
		//Initialize Timer - Timer controls updating the commanded power every second
		powerTimerTask = new PowerTimerTask();
		/*powerTimerTask = new TimerTask() {
			@Override
			public void run() {
				//TODO: Still getting concurrent modification exception
				for(TrainController tc : tcList ){
					tc.calculatePowerCommand();
				}
				
				//Update simulated clock time - assume every running of timer task is 1 second in the "real world"
				simulatedClockTime += 1000;
				
				//if(ctc_ui != null) {
				//	ctc_ui.setTime(simulatedClockTime);
				//}
				ctcUI.setTime(simulatedClockTime);
				
				//Repaint Track model UI - TODO: Limit how often this runs
				if(TrackModelUI.trackGraphic != null && (simulatedClockTime - lastRedrawTime) > timeBetweenRedraw) {
					lastRedrawTime = simulatedClockTime;
					TrackModelUI.trackGraphic.actionPerformed(new ActionEvent(this, 1, ""));
				}
				MainMTR.getTrainModelUI().actionPerformed(new ActionEvent(this, 1, ""));
			}
		};*/
		
		powerTimer.scheduleAtFixedRate(powerTimerTask, 0, 1000);
	}

	/*
	 * NOTE: For line color capitalize first letter: Green or Red
	 */
	public TrainController addNewTrainController(int trainID, String lineColor, Train train) {
		//Check if new train ID has been used
		if(!idList.contains(trainID)) {
			idList.add(trainID);
			TrainController tc = new TrainController(trainID, train, this, pid_p, pid_i, lineColor);
			
			//Add tc to list of trianControllers
			tcList.add(tc);
			
			TrainControlUI.reloadGUI();
			return tc;
		}
		else {
			return null;
		}
	}
	
	public List<TrainController> getTrainControllerList(){
		//TODO: Sort by train ID
		return new ArrayList<TrainController>(tcList);
	}
	
	public List<Integer> getTrainIDList(){
		return new ArrayList<Integer>(idList);
	}
	
	public void setPIDParameters(double kp, double ki) {
		pid_p = kp;
		pid_i = ki;
	}
	
	public double getPIDParameter_p() {
		return pid_p;
	}
	

	public double getPIDParameter_i() {
		return pid_i;
	}
	
	public void TrainControlHelper_setTimeMultiplier(int mult) {
		clockMultiplier = mult;
		
		rescheduleTimer();
	}
	
	public void TrainControlHelper_setctcUI(ctcUI ctc_ui) {
		this.ctc_ui = ctc_ui;
	}
	
	private void rescheduleTimer() {
		//powerTimer.scheduleAtFixedRate(powerTimerTask, 0, 1000/clockMultiplier);
		powerTimer.scheduleAtFixedRate(new PowerTimerTask(), 0, 1000/clockMultiplier);
	}
	
	public void removeTrain(int trainID) {
		if(getTrainIDList().contains(trainID)) {
			//Train exists - remove it from both lists
			idList.remove(idList.indexOf(trainID));
			int tcIndex = -1;
			for(TrainController tc : tcList) {
				if(trainID == tc.getTrainID()) {
					tcIndex = tcList.indexOf(tc);
					break;
				}
			}
			if(tcIndex != -1) {
				tcList.remove(tcIndex);
			}
			TrainControlUI.reloadGUI();
		}
	}

	public long getTime() {
		return simulatedClockTime;
	}
	
	class PowerTimerTask extends TimerTask{
        PowerTimerTask(){

        }
        
		@Override
		public void run() {
			//TODO: Still getting concurrent modification exception
			for(TrainController tc : tcList ){
				tc.calculatePowerCommand();
			}
			
			//Update simulated clock time - assume every running of timer task is 1 second in the "real world"
			simulatedClockTime += 1000;
			
			/*if(ctc_ui != null) {
				ctc_ui.setTime(simulatedClockTime);
			}*/
			if(MainMTR.fullUI) {
				ctcUI.setTime(simulatedClockTime);
			}
			
			//Repaint Track model UI - TODO: Limit how often this runs
			if(TrackModelUI.trackGraphic != null && (simulatedClockTime - lastRedrawTime) > timeBetweenRedraw) {
				lastRedrawTime = simulatedClockTime;
				TrackModelUI.trackGraphic.actionPerformed(new ActionEvent(this, 1, ""));
			}
			
			TrainModelUI tm = MainMTR.getTrainModelUI();
			if(tm != null) {
				tm.actionPerformed(new ActionEvent(this, 1, ""));
			}
		}
	}
	
}