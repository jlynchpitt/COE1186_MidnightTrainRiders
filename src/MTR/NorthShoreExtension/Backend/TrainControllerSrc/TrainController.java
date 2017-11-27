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

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.StaticTrackDBHelper;
import MTR.NorthShoreExtension.Backend.TrainSrc.Train;
import MTR.NorthShoreExtension.Tests.TrainControlTestBenchPanel;
import MTR.NorthShoreExtension.UI.TrainControlPanel;
import MTR.NorthShoreExtension.UI.TrainControlUI;

public class TrainController {
	//TODO: Change these variables to private and add getters/setters
	
	private int trainID = 0;
	private Train trainModel = null;
	private TrainControllerHelper tcHelper = null;
	private TrainControlPanel trainControlPanel = null;
	private TrainControlTestBenchPanel testBench = null;
	private boolean manualMode = true;
	
	//PID controllers
	private MiniPID spdPID1;
	private MiniPID spdPID2;
	private MiniPID spdPID3;
	
	//Vital train controls/info
	private double powerCommand = 0; //kilowatts
	private double actualSpeed = 0; //km/h
	private int ctcCommandedSetSpeed = 0; //km/h
	private int driverCommandedSetSpeed = 0; //km/h
	private int trainSetSpeed = 0; //km/h - the actual speed the train is set to
	private int authority = 0; //# of tracks/blocks
	private boolean brakeApplied = true; //default to brakes applied
	private boolean eBrakeApplied = true; //emergency brake - default to brakes applied
	private boolean signalPickupFailed = false;
	private boolean engineFailed = false;
	private boolean brakesFailed = false;
	private double Kp = 0;
	private double Ki = 0;
	
	/* Non-Vital Train Info */
	private String trainFaults = "none"; //TODO: Possibly change to enumerated type
	private String announcements = "at Steel Plaza";
	
	//non-vital train controls
	private boolean rightDoorOpen = false;
	private boolean leftDoorOpen = false;
	private boolean lightsOn = false;
	private int setTemp = 73; //default room temperature is 73 degrees
	private double internalTemp = 73; //TODO: Change actual temp to outside temp to start
	
	//Track Info
	private DriverTrackInfo currentTrackInfo = null;
	private DriverTrackInfo previousTrackInfo = null;
	private StaticTrackDBHelper db = null;
	
	//TODO: Pass in train model object
	public TrainController(int id, Train t, TrainControllerHelper tch, double pid_p, double pid_i, String line){
		trainID = id;
		trainModel = t;
		tcHelper = tch;
		
		db = MainMTR.getStaticTrackDBHelper();
		
		Kp = pid_p;
		Ki = pid_i;
		
		//Initialize pid controllers
		spdPID1 = new MiniPID(pid_p, pid_i, 0);
		spdPID2 = new MiniPID(pid_p, pid_i, 0);
		spdPID3 = new MiniPID(pid_p, pid_i, 0);

		configSpeedPID(spdPID1);
		configSpeedPID(spdPID2);
		configSpeedPID(spdPID3);
		
		//Put train on first track
		int firstTrackID = db.getFirstTrack(line);
		currentTrackInfo = db.getTrackInfo(firstTrackID, true);
	}
	
	public int getTrainID() {
		return trainID;
	}
	
	public void setTrainControlPanel(TrainControlPanel tcp) {
		trainControlPanel = tcp;
	}
	
	public void setTrainControlTestBenchPanel(TrainControlTestBenchPanel tctbp) {
		testBench = tctbp;
	}
	
	public void refreshUI() {
		updateUI(TrainControlPanel.ANNOUNCEMENT);
		updateUI(TrainControlPanel.BRAKES);
		updateUI(TrainControlPanel.DOORS);
		updateUI(TrainControlPanel.FAULT);
		updateUI(TrainControlPanel.LIGHTS);
		updateUI(TrainControlPanel.TEMPERATURE);
		updateUI(TrainControlPanel.TRACK_INFO);
		updateUI(TrainControlPanel.VITAL);
	}
	
	public void calculatePowerCommand() {
		//Log previous power and speed parameters to the database
		db.addTrainStateRecord(trainID, tcHelper.getTime(), powerCommand, trainSetSpeed, actualSpeed);
		
		if(!eBrakeApplied && !brakeApplied && authority > 0 && !engineFailed) {
			//Simple check against speed limit TODO: Move these checks into setters
			if(driverCommandedSetSpeed > ctcCommandedSetSpeed) {
				trainSetSpeed = ctcCommandedSetSpeed;
			}
			else {
				trainSetSpeed = driverCommandedSetSpeed;
			}
				
			//Use PID loop to calculate next power command - pass to train model + UI
			double speedError = actualSpeed - trainSetSpeed;
			double tempPC1 = spdPID1.getOutput(speedError, 0);
			double tempPC2 = spdPID2.getOutput(speedError, 0);
			double tempPC3 = spdPID3.getOutput(speedError, 0);
			
			Double tempPC = powerOutputSelector(tempPC1, tempPC2, tempPC3);
			if(tempPC != null /* && validatePowerCommand(tempPC)*/) {
				//Limit amount power command can increase in 1 second
				int safePowerCommandJump = 10;
				
				if(tempPC - powerCommand > safePowerCommandJump) {
					powerCommand = powerCommand + safePowerCommandJump;
				}
				else {
					powerCommand = tempPC;					
				}
			}
			else {
				//power command validation failed - set power to 0
				powerCommand = 0;
			}
		}
		else {
			powerCommand = 0;
			spdPID1.reset();
			spdPID2.reset();
			spdPID3.reset();
		}
		
		updateUI(TrainControlPanel.VITAL);
		
		//Send to train model and test bench
		if(trainModel != null) {
			trainModel.TrainModel_setPower(powerCommand);
		}
		if(testBench != null) {
			testBench.TrainModel_setPower(powerCommand);
		}
	}

	/* Functions for UI to call to set vital user inputs */
	public void setDriverCommandedSetSpeed(int speed) {
		driverCommandedSetSpeed = speed;

		updateTrainSetSpeed();
	}
	
	/* Return true if brakes successfully set 
	 * 		- false if brakes failed and cannot be applied */
	public boolean operateBrake(boolean applied) {
		boolean brakesSuccess = false;
		if(brakesFailed) {
			brakeApplied = false;
			brakesSuccess = false;
		}
		else {
			brakeApplied = applied;			
			brakesSuccess = true;
		}

		if(trainModel != null) {
			trainModel.TrainModel_setBrake(brakeApplied);
		}
		if(testBench != null) {
			testBench.TrainModel_setBrake(brakeApplied);
		}
		
		//NOTE: Redundant if UI calling this but more convenient when backend operates brakes
		updateUI(TrainControlPanel.BRAKES);
		
		return brakesSuccess;
	}
	
	public void operateEmergencyBrake(boolean applied) {
		eBrakeApplied = applied;
		
		if(trainModel != null) {
			trainModel.TrainModel_setEBrake(applied);
		}
		if(testBench != null) {
			testBench.TrainModel_setEBrake(applied);
		}
		
		//NOTE: Redundant if UI calling this but more convenient when backend operates brakes
		updateUI(TrainControlPanel.BRAKES);
	}
	
	/* Functions for non-vital user inputs */
	public void operateRightDoor(boolean open) {
		rightDoorOpen = open;
		
		if(trainModel != null) {
			trainModel.TrainModel_openRightDoor(open);
		}
		if(testBench != null) {
			testBench.TrainModel_openRightDoor(open);
		}
	}
	
	public void operateLeftDoor(boolean open) {
		leftDoorOpen = open;
		
		if(trainModel != null) {
			trainModel.TrainModel_openLeftDoor(open);
		}
		if(testBench != null) {
			testBench.TrainModel_openLeftDoor(open);
		}
	}
	
	public void operateLights(boolean on) {
		lightsOn = on;
		
		if(trainModel != null) {
			trainModel.TrainModel_turnLightsOn(on);
		}
		if(testBench != null) {
			testBench.TrainModel_turnLightsOn(on);
		}
	}
	
	public void setInsideTemperature(int temp) {
		setTemp = temp;

		//TODO: perform calculations of actual temperature
	}
	
	public void setOperationMode(boolean manual) {
		manualMode = manual;
	}
	
	/* Functions for receiving inputs from the train model */
	public void TrainControl_setActualSpeed(double speed) {
		actualSpeed = speed;
		
		//If speed = 0 + auto mode + on a station track - open doors
		
		updateUI(TrainControlPanel.VITAL);
	}
	
	public void TrainControl_setCommandedSpeedAuthority(int speed, int auth) {
		if(signalPickupFailed) {
			ctcCommandedSetSpeed = 0;
			authority = 0;
		}
		else {
			ctcCommandedSetSpeed = speed;
			authority = auth;
		}
		
		updateTrainSetSpeed();
		
		updateUI(TrainControlPanel.VITAL);
	}
	
	public void TrainControl_sendBeaconInfo(int beacon) {
		//translate beacon - TODO: 
		if(!signalPickupFailed) {
			int trackID = (beacon & 0x1FE00000) >> 21;
			trackID += 2000; //green line
			
			if(currentTrackInfo != null && trackID != currentTrackInfo.trackID) {
				boolean travelDirection = calculateTravelDirection();
				if(currentTrackInfo != null) {
					previousTrackInfo = currentTrackInfo.copy();
					//previousTrackInfo = new DriverTrackInfo();
					//previousTrackInfo.trackID = currentTrackInfo.trackID;
				}
				currentTrackInfo = db.getTrackInfo(trackID, travelDirection);
				
				updateUI(TrainControlPanel.TRACK_INFO);
			}
		}
	}
	
	public void TrainControl_moveToNextTrack() {
		if(!signalPickupFailed) {
			boolean travelDirection = calculateTravelDirection();
			previousTrackInfo = currentTrackInfo.copy();
			//previousTrackInfo = new DriverTrackInfo();
			//previousTrackInfo.trackID = currentTrackInfo.trackID;
			currentTrackInfo = db.getTrackInfo(currentTrackInfo.nextTrackID, travelDirection); //TODO: Determine direction
			
			updateUI(TrainControlPanel.TRACK_INFO);
		}
	}
	
	public void TrainControl_setFaultStatus(int status, boolean faultActive) {
		switch(status) {
		case TrainControllerHelper.BRAKE_FAILURE:
			brakesFailed = faultActive;
			
			//Release all brakes if failing them
			if(brakesFailed) {
				operateBrake(false);
				//TODO: Should e-brake also fail?
				//operateEmergencyBrake(false);
			}
			break;
		case TrainControllerHelper.ENGINE_FAILURE:
			engineFailed = faultActive;
			
			if(engineFailed) {
				operateEmergencyBrake(true);
			}
			break;
		case TrainControllerHelper.SIGNAL_PICKUP_FAILURE:
			signalPickupFailed = faultActive;
			
			if(signalPickupFailed) {
				//Can't get speed or authority commands from the track so set them to 0
				TrainControl_setCommandedSpeedAuthority(0, 0);
				//TODO: Clear track status to defaults

				operateEmergencyBrake(true);
			}
			else {
				if(trainModel != null) {
					//trainModel.TrainModel_resendSpeedAuthority();
				}
				if(testBench != null) {
					testBench.TrainModel_resendSpeedAuthority();
				}
			}
			break;				
		}
		
		updateUI(TrainControlPanel.FAULT);
	}
	
	public void TrainControl_setActualTemp(double temp) {
		internalTemp = temp;
		
		updateUI(TrainControlPanel.TEMPERATURE);
	}
	
	public void TrainControl_setPassengerEBrake() {
		//NOTE: Emergency brake can only be set from train model - it cannot be released
		operateEmergencyBrake(true);
	}
	
	/* Functions called by UI to get Train Control info */
	public double getKp() {
		return Kp;
	}
	
	public double getKi() {
		return Ki;
	}
	
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
	
	public DriverTrackInfo getCurrentTrackInfo() {
		return currentTrackInfo;
	}
	
	public String getEngineStatus(){
		if(engineFailed) {
			return "FAILED";
		}
		else {
			return "Operational";
		}
	}
	
	public String getBrakeStatus(){
		if(brakesFailed) {
			return "FAILED";
		}
		else {
			return "Operational";
		}
	}
	
	public String getSignalPickupStatus(){
		if(signalPickupFailed) {
			return "FAILED";
		}
		else {
			return "Operational";
		}
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
	
	/* Private helper functions*/
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
	
	private void configSpeedPID(MiniPID pid) {
		pid.setOutputLimits(0, 160); //Train engine power limits: 0-120 kW (0-160 horsepower)
		pid.setSetpoint(0);
	}
	
	/*
	 * Compare 3 power commands
	 * Allow for 1 dissenting power command + still produce valid power command
	 * 
	 * if all 3 different - return null
	 */
	private Double powerOutputSelector(double pc1, double pc2, double pc3) {
		if(pc1 == pc2 && pc2 == pc3) {
			return pc1;
		}
		else if(pc1 == pc2) {
			return pc1;
		}

		else if(pc1 == pc3) {
			return pc1;
		}

		else if(pc2 == pc3) {
			return pc2;
		}
		return null;
	}
	
	private void updateTrainSetSpeed() {
		if(manualMode && driverCommandedSetSpeed <= ctcCommandedSetSpeed && driverCommandedSetSpeed < currentTrackInfo.speedLimit) {
			trainSetSpeed = driverCommandedSetSpeed;
		}
		else if(ctcCommandedSetSpeed <= currentTrackInfo.speedLimit) {
			trainSetSpeed = ctcCommandedSetSpeed;
		}
		else {
			trainSetSpeed = currentTrackInfo.speedLimit;
		}
	}
	
	private void updateUI(String command) {
		//TODO: This may be an issue when working with full subsystem and train control UI popup is closed
		if(trainControlPanel != null) {
			trainControlPanel.actionPerformed(new ActionEvent(this, 1, command));
		}
	}

	/*
	 * Check speed + speed limit + set speeds to determine safe operations
	 * Apply standard brake or emergency brake if speed deemed unsafe
	 * 
	 * Behavior is different in manual and automatic modes
	 */
	private void ensureSafeOperations() {
		
	}
	
	private boolean calculateTravelDirection() {
		if(previousTrackInfo == null || currentTrackInfo == null || previousTrackInfo.trackID < currentTrackInfo.trackID) {
			System.out.println("Primary direction");
			return true;
		}
		else {
			if(previousTrackInfo != null) {
				System.out.println("prev track: " + previousTrackInfo.trackID);
			}
			if(currentTrackInfo != null) {
				System.out.println("current track: " + currentTrackInfo.trackID);
			}
			System.out.println("Reverse direction");
			return false;
		}
	}
}