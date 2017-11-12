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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/*
 * author: Joseph Lynch
 *
 * Helper class for the train controllers
 * 		keeps track of all train controller objects
 * 		timer to signal train controller objects to calculate next power command
 * 		
 */
public class TrainControllerHelper {
	private double pid_p = 0.4;
	private double pid_i = 0.25; 
	private Timer powerTimer = new Timer();
	private List<Integer> idList = new ArrayList<>();
	private List<TrainController> tcList = new ArrayList<TrainController>();
	private boolean manualMode = true;
	private int clockMultiplier = 1;
	private TimerTask powerTimerTask;
	private long simulatedClockTime = System.currentTimeMillis();
	
	public TrainControllerHelper(){
		//Initialize Timer - Timer controls updating the commanded power every second
		powerTimerTask = new TimerTask() {
			@Override
			public void run() {
				for(TrainController tc : tcList ){
					tc.calculatePowerCommand();
				}
				
				//Update simulated clock time - assume every running of timer task is 1 second in the "real world"
				simulatedClockTime += 1000;
			}
		};
		
		powerTimer.scheduleAtFixedRate(powerTimerTask, 0, 1000);
	}

	public TrainController addNewTrainController(int trainID) {
		//Check if new train ID has been used
		if(!idList.contains(trainID)) {
			TrainController tc = new TrainController(trainID, null, pid_p, pid_i);
			
			//Add tc to list of trianControllers
			tcList.add(tc);
			idList.add(trainID);
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
	
	public void TrainControlHelper_setOperationMode(boolean manMode) {
		manualMode = manMode;
		
		for(TrainController tc : tcList ){
			tc.TrainControl_setOperationMode(manMode);
		}
	}
	
	public void TrainControlHelper_setTimeMultiplier(int mult) {
		clockMultiplier = mult;
		
		rescheduleTimer();
	}
	
	private void rescheduleTimer() {
		powerTimer.scheduleAtFixedRate(powerTimerTask, 0, 1000/clockMultiplier);
	}
}
