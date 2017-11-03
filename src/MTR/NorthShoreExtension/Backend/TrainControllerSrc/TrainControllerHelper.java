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
	List<TrainController> tcList = new ArrayList<TrainController>();
	
	public TrainControllerHelper(){
		//Initialize Timer - Timer controls updating the commanded power every second
		powerTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				for(TrainController tc : tcList ){
					tc.calculatePowerCommand();
				}
			}
		}, 0, 500); //TODO: Update period based on time clock multiplier
	}

	public TrainController addNewTrainController(int trainID) {
		TrainController tc = new TrainController(trainID, null, pid_p, pid_i);
		
		//Add tc to list of trianControllers
		tcList.add(tc);
		
		return tc;
	}
	
	public List<TrainController> getTrainControllerList(){
		//TODO: Sort by train ID
		return tcList;
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
}
