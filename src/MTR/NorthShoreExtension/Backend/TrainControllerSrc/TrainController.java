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

import MTR.NorthShoreExtension.Backend.TrainSrc.Train;
import MTR.NorthShoreExtension.UI.TrainControlPanel;

public class TrainController {
	//TODO: Change these variables to private and add getters/setters
	
	private int trainID = 0;
	public Train trainModel = null;
	private TrainControlPanel trainControlPanel;
	private MiniPID pid;
	private boolean CONNECTEDTOTRAINMODEL = false; //This must be true when in the full system - for testing individual submodule
	
	//Vital train controls/info
	private double powerCommand = 0; //kilowatts
	private double actualSpeed = 0; //MPH
	private int ctcCommandedSetSpeed = 0; //MPH
	private int driverCommandedSetSpeed = 0; //MPH
	private int trainSetSpeed = 0; //MPH - the actual speed the train is set to
	private int authority = 0; //# of tracks/blocks
	private boolean brakeApplied = true; //default to brakes applied
	private boolean eBrakeApplied = true; //emergency brake - default to brakes applied
	
	/* Non-Vital Train Info */
	private String trainFaults = "none"; //TODO: Possibly change to enumerated type
	private String announcements = "at Steel Plaza";
	
	//non-vital train controls
	private boolean rightDoorOpen = false;
	private boolean leftDoorOpen = false;
	private boolean lightsOn = false;
	private int setTemp = 73; //default room temperature is 73 degrees
	private double internalTemp = 73; //TODO: Change actual temp to outside temp to start
	
	//TODO: Pass in train model object
	public TrainController(int id, Train t, double pid_p, double pid_i){
		trainID = id;
		trainModel = t;
		
		//Initialize pid controller
		pid = new MiniPID(pid_p, pid_i, 0);
		pid.setOutputLimits(0, 160); //Train engine power limits: 0-120 kW (0-160 horsepower)
		pid.setSetpoint(0);
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
			//Simple check against speed limit TODO: Move these checks into setters
			if(driverCommandedSetSpeed > ctcCommandedSetSpeed) {
				trainSetSpeed = ctcCommandedSetSpeed;
			}
			else {
				trainSetSpeed = driverCommandedSetSpeed;
			}
				
			//Use PID loop to calculate next power command - pass to train model + UI
			//double speedError = trainSetSpeed - actualSpeed; //TODO: Change this back to trainSetSpeed
			//double speedError = driverCommandedSetSpeed - actualSpeed;
			double speedError = actualSpeed - trainSetSpeed;
			powerCommand = pid.getOutput(speedError, 0);
		}
		else {
			powerCommand = 0;
			pid.reset();
		}
				
		if(CONNECTEDTOTRAINMODEL == false) {
			actualSpeed = calculateBasicSpeed();
			
			actionCommand = "powerCmd_actualSpeed";
		}
		
		//TODO: This may be an issue when working with full subsystem and train control UI popup is closed
		if(trainControlPanel != null) {
			//TODO: Find out what needs to be passed in event
			trainControlPanel.actionPerformed(new ActionEvent(this, 1, actionCommand));
			//trainControlPanel.firePropertyChange("actualSpeed", 0.0, actualSpeed); property change listener may not be registered
		}
	}
	
	/* Functions for vital user inputs */
	public void setDriverCommandedSetSpeed(int speed) {
		driverCommandedSetSpeed = speed;
	}
	
	public void operateBrake(boolean applied) {
		brakeApplied = applied;
	}
	
	public void operateEmergencyBrake(boolean applied) {
		eBrakeApplied = applied;
	}
	
	/* Functions for non-vital user inputs */
	public void operateRightDoor(boolean open) {
		rightDoorOpen = open;
	}
	
	public void operateLeftDoor(boolean open) {
		leftDoorOpen = open;
	}
	
	public void operateLights(boolean on) {
		lightsOn = on;
	}
	
	public void setInsideTemperature(int temp) {
		setTemp = temp;
	}
	
	/* Functions for receiving inputs from the train model */
	public void TrainControl_setActualSpeed(double speed) {
		actualSpeed = speed;
	}
	
	public void TrainControl_setAuthority(int auth) {
		authority = auth;
	}
	
	public void TrainControl_setCommandedSpeed(int speed) {
		ctcCommandedSetSpeed = speed;
	}
	
	public void TrainControl_sendBeaconInfo(int beacon) {
		//translate beacon
	}
	
	public void TrainControl_setFaultStatus(int status) {
		//TODO: Define status as enumerated type
	}
	
	public void TrainControl_setActualTemp(double temp) {
		
	}
	
	/* Functions called by UI to get Train Control info */
	public int getAuthority() {
		return authority;
	}
	
	public int getCTCCommandedSetSpeed() {
		return ctcCommandedSetSpeed;
	}
	
	public int getDriverCommandedSetSpeed() {
		return driverCommandedSetSpeed;
	}
	
	public int getTrainSetSpeed() {
		return trainSetSpeed;
	}
	
	public double getPower() {
		return powerCommand;
	}
	
	public double getActualSpeed() {
		return actualSpeed;
	}
	
	public String getAnnouncements() {
		return announcements;
	}
	
	public String getTrainFaults() {
		return trainFaults;
	}
	
	public double getInternalTemp() {
		return internalTemp;
	}
	
	public boolean isBrakeApplied() {
		return brakeApplied;
	}
	
	public boolean isEBrakeApplied() {
		return eBrakeApplied;
	}
	
	public boolean isRightDoorOpen() {
		return rightDoorOpen;
	}
	
	public boolean isLeftDoorOpen() {
		return leftDoorOpen;
	}
	
	public boolean areLightsOn() {
		return lightsOn;
	}
	
	private double calculateBasicSpeed() {
		//for testing purposes when not attached to train model
		//simple velocity calculation for demonstration of adjusting power command
		
		//Power (W) = Force (kg * m/s2) * Velocity (m/s)
		//TrainForce = mass * acceleration = 51.43 tons * 0.5 m/s2 (train 2/3 loaded) = 46656.511 kg * 0.5 m/s2
		//Velocity = Power/Train Force			
		//NOTE: 1.34102 converts horsepower back to kWatts
		double msSpeed = (powerCommand * 1000 / 1.34102)/((46656.511/9.8) * 0.5);
		double speed = msSpeed * 2.23694;
		
		return speed;
	}
}
