/*
 * Filename: TrainController.java
 * Author: Joe Lynch
 * Date Created: 10/9/2017
 * File Description: This is the backend for the Train Controller. One of these objects
 * 			will be instantiated with each train. It's main function is calculating
 * 			a power command to be sent to the train model to drive the train at the
 * 			proper speed. This is a vital safety component and must take every 
 * 			precaution to not crash the train.
 */

package MTR.NorthShoreExtension.Backend.TrainControllerSrc;

import java.awt.event.ActionEvent;

import MTR.NorthShoreExtension.UI.TrainControlPanel;

public class TrainController {
	//TODO: Change these variables to private and add getters/setters
	
	private int trainID = 0;
	//public TrainModel = null;
	private TrainControlPanel trainControlPanel;
	private MiniPID pid;
	private boolean CONNECTEDTOTRAINMODEL = false; //This must be true when in the full system - for testing individual submodule
	
	//Vital train controls/info
	public double powerCommand = 0; //kilowatts
	public double actualSpeed = 0; //MPH
	public int ctcCommandedSetSpeed = 0; //MPH
	public int driverCommandedSetSpeed = 0; //MPH
	public int trainSetSpeed = 0; //MPH - the actual speed the train is set to
	public int authority = 0; //# of tracks/blocks
	public boolean brakeApplied = true; //default to brakes applied
	public boolean eBrakeApplied = true; //emergency brake - default to brakes applied
	public String trainFaults = "none"; //TODO: Possibly change to enumerated type
	public String announcements = "at Steel Plaza";
	
	//Current track info TODO: Change defaults - just for testing purposes
	public String trackID = "Green-A-1"; 
	public int trackLength = 200; //feet
	public double trackGrade = 0.5; //%
	public int trackSpeedLimit = 55; //mph
	public boolean trackUnderground = false;
	public boolean stoppedAtStation = false;	
	
	//non-vital train controls
	public boolean rightDoorOpen = false;
	public boolean leftDoorOpen = false;
	public boolean lightsOn = false;
	public int setTemp = 73; //default room temperature is 73 degrees
	public int actualTemp = 73; //TODO: Change actual temp to outside temp to start
	
	//TODO: Pass in train model object
	public TrainController(int id){
		trainID = id;
		//trainModel = tm;
		
		//Initialize pid controller
		pid = new MiniPID(0.4, 0.25, 0);
		pid.setOutputLimits(0, 160); //Train engine power limits: 0-120 kW (0-160 horsepower)
		pid.setSetpoint(0);
		//pid.setDirection(true);
	}
	
	public int getTrainID() {
		return trainID;
	}
	
	public void setTrainControlPanel(TrainControlPanel tcp) {
		trainControlPanel = tcp;
	}
	
	public void calculatePowerCommand() {
		String actionCommand = "powerCmd";
		
		if(!eBrakeApplied && !brakeApplied && authority > 0) {
			//Simple check against speed limit
			if(driverCommandedSetSpeed > trackSpeedLimit) {
				driverCommandedSetSpeed = trackSpeedLimit;
			}
			//Use PID loop to calculate next power command - pass to train model + UI
			//double speedError = trainSetSpeed - actualSpeed; //TODO: Change this back to trainSetSpeed
			//double speedError = driverCommandedSetSpeed - actualSpeed;
			double speedError = actualSpeed - driverCommandedSetSpeed;
			powerCommand = pid.getOutput(speedError, 0);
		}
		else {
			powerCommand = 0;
			pid.reset();
		}
		
		//System.out.println("Train id: " + trainID + " Speed error: " + speedError + " power command: " + powerCommand);
		
		if(CONNECTEDTOTRAINMODEL == false) {
			//simple calculation of speed based on powerCommand
			//Power (W) = Force (kg * m/s2) * Velocity (m/s)
			//TrainForce = mass * acceleration = 51.43 tons * 0.5 m/s2 (train 2/3 loaded) = 46656.511 kg * 0.5 m/s2
			//Velocity = Power/Train Force			
			//NOTE: 1.34102 converts horsepower back to kWatts
			double msSpeed = (powerCommand * 1000 / 1.34102)/((46656.511/9.8) * 0.5);
			actualSpeed = msSpeed * 2.23694;
			
			actionCommand = "powerCmd_actualSpeed";
		}
		
		//TODO: This may be an issue when working with full subsystem and train control UI popup is closed
		if(trainControlPanel != null) {
			//TODO: Find out what needs to be passed in event
			trainControlPanel.actionPerformed(new ActionEvent(this, 1, actionCommand));
			//trainControlPanel.firePropertyChange("actualSpeed", 0.0, actualSpeed); property change listener may not be registered
		}
	}
	
	private double calculateBasicSpeed() {
		//for testing purposes when not attached to train model
		//simple velocity calculation for demonstration of adjusting power command
		return 0.0;
	}
}
