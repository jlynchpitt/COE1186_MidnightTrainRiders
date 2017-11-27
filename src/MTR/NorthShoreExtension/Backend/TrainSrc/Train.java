package MTR.NorthShoreExtension.Backend.TrainSrc;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.TrackModelSrc.TrackModel;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainController;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainControllerHelper;

public class Train {
	final double length=32.2;
	final double height=3.42;
	final double width=2.65;
	final double trainmass=40900;
	
	
	private int cars; //static int variables
	private int temperature, passengers, crewcount; //dynamic int variables
	private boolean leftdoor, rightdoor, lightson;
	private String authority;
	private double maxacceleration, maxvelocity, maxdeceleration, nextpower;
	private int maxpassengers, trainID;
	private boolean engineFailure, signalFailure, brakeFailure, passengerEBrake;
	
	private TrackModel tkm;
	private TrainController tc;
	private TrainMovement tm;
	Passengers p;
	
	public Train(int t){
		tm = new TrainMovement(trainmass);
		TrainControllerHelper tch = MainMTR.getTrainControllerHelper();
		tch.addNewTrainController(t);
		p = new Passengers();
		trainID=t;
	}
	
	public int getTrainID() {
		return trainID;
	}
	
	public void setNextPower(double np) {
		this.nextpower=np;
	}
	
	public double getNextPower(){
		return(nextpower);
	}
	//Functions that the Train Controller Calls//
	public void TrainModel_setPower(double p){
		tm.setMovement(p);

		tc.TrainControl_setActualSpeed(3.6*tm.getVelocity());	// This line receives the velocity from the train movement class and sends it to the train controller
		tkm.TrackModel_setDistance(trainID, tm.getDistance());
	}
	
	public void TrainModel_turnLightsOn(boolean l) {
		this.lightson=l;
	}
	
	public void TrainModel_openRightDoor(boolean o) {
		this.rightdoor=o;
	}
	
	public void TrainModel_openLeftDoor(boolean o) {
		this.leftdoor=o;
	}
	
	public void TrainModel_setTemperatureSetting(int t) {
		this.temperature = t;
	}
	
	public void TrainModel_setBrake(boolean b) {
		tm.setBrake(b);
	}
	
	public void TrainModel_setEBrake(boolean b) {
		tm.seteBrake(b);
	}
	
	public void TrainModel_moveToNextTrack() {
		tc.TrainControl_moveToNextTrack();
	}
	
	public void TrainModel_resendSpeedAuthority(int v, int a) {
		tc.TrainControl_setCommandedSpeedAuthority(v,a);
	}

	
	public double getAcceleration() {
		if(tm.getAcceleration()>0) {
			return tm.getAcceleration();
		}else {
			return 0;
		}
	}
	
	public double getDeceleration() {
		if(tm.getAcceleration()<0) {
			return tm.getAcceleration()*-1;
		}else {
			return 0;
		}
	}

	public double getVelocity() {
		return tm.getVelocity();
	}
	
	public double getDistance() {
		return tm.getDistance();
	}
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public int getCrewcount() {
		return crewcount;
	}

	public void setCrewcount(int crewcount) {
		this.crewcount = crewcount;
	}

	public double getWidth() {
		return width;
	}


	public double getPassengerMass() {
		return p.getPassengerWeight();
	}

	
	public double getTrainMass() {
		return trainmass;
	}
	
	public double getLength() {
		return length;
	}


	public double getHeight() {
		return height;
	}


	public int getCars() {
		return cars;
	}

	public void setCars(int cars) {
		this.cars = cars;
	}

	public double getMaxacceleration() {
		return maxacceleration;
	}

	public void setMaxacceleration(double maxacceleration) {
		this.maxacceleration = maxacceleration;
	}

	public double getMaxvelocity() {
		return maxvelocity;
	}

	public void setMaxvelocity(double maxvelocity) {
		this.maxvelocity = maxvelocity;
	}

	public double getMaxdeceleration() {
		return maxdeceleration;
	}

	public void setMaxdeceleration(double maxdeceleration) {
		this.maxdeceleration = maxdeceleration;
	}

	public int getMaxpassengers() {
		return maxpassengers;
	}

	public void setMaxpassengers(int maxpassengers) {
		this.maxpassengers = maxpassengers;
	}

	public boolean getLightson() {
		return lightson;
	}

	public void turnLightsOn(boolean b) {
		this.lightson=b;
	}

	public void openLeftDoor(boolean b) {
		this.leftdoor=b;
	}
	
	public void openRightDoor(boolean b) {
		this.rightdoor=b;
	}

	
	public double getTotalMass() {
		return(p.getPassengerWeight()+trainmass);
	}
	
	public void setEngineFailure(boolean b) {
		this.engineFailure=b;
		tm.setBrake(true);
	}
	
	public void setSignalFailure(boolean b) {
		this.signalFailure=b;
		tm.setBrake(true);
	}
	
	public void setBrakeFailure(boolean b) {
		this.brakeFailure=b;
		tm.seteBrake(true);
	}
	
	public void setPassnegerEBraker(boolean b) {
		this.passengerEBrake=b;
		tm.seteBrake(true);
	}
}
