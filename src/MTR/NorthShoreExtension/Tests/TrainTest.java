package MTR.NorthShoreExtension.Tests;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.TrackModelSrc.TrackModel;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainController;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainControllerHelper;
import MTR.NorthShoreExtension.Backend.TrainSrc.Passengers;
import MTR.NorthShoreExtension.Backend.TrainSrc.TrainMovement;

public class TrainTest {
	final double length=32.2;
	final double height=3.42;
	final double width=2.65;
	final double trainmass=40900;
	
	
	private int cars; //static int variables
	private int temperature, crewcount; //dynamic int variables
	private boolean leftdoor, rightdoor, lightson;
	private int authority;
	private double maxacceleration, maxvelocity, nextpower;
	private int maxpassengers, trainID;
	private int commandedSpeed;
	private boolean engineFailure, signalFailure, brakeFailure, passengerEBrake;
	private String trainLine;
	private TrainMovement tm;
	private String announcement;
	Passengers p;
	
	public TrainTest(int t, int trackID){
		tm = new TrainMovement(trainmass);
		TrainControllerHelper tch = MainMTR.getTrainControllerHelper();
		
		int line=(int) Math.floor(trackID / Math.pow(10, Math.floor(Math.log10(trackID))));
		System.out.println("tid "+t);
		trainLine="Green";
	
		p = new Passengers();
		leftdoor=false;
		rightdoor=false;
		trainID=t;
	}
	
	public int getTrainID() {
		return trainID;
	}
	
	/*
	public void setNextPower(double np) {
		this.nextpower=np;
	}
	
	public double getNextPower(){
		return(nextpower);
	}
	*/
	//Functions that the Train Controller Calls//
	public void TrainModel_setPower(double p){
		tm.setMovement(p*1000); //the power is multiplied by 1000 to convert from Kilowatts to watts		
		
		
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
	}
	
	public void TrainModel_resendSpeedAuthority(int v, int a) {
		this.authority=a;
		this.commandedSpeed=v;

	}
	
	public void TrainModel_resendSpeedAuthority() {
	
	}
	public void TrainModel_sendBeacon(int beacon) {

	}
	
	public void TrainModel_sendAnnouncement(String a) {
		this.setAnnouncement(a);
	}
	
	
	public void TrainModel_setNumberOfPassengers(int tickets) {
		if (p.getTotalPassengers()==0) {
			p.passengersIn(tickets);
		}else {
			p.passengersOut();
			p.passengersIn(tickets);
		}
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
	public int getAuthority() {
		return authority;
	}


	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getPassengers() {
		return p.getTotalPassengers();
	}

	public void setPassengers(int passengers) {
		p.setPassengers(passengers);
		tm.setMass(p.getPassengerWeight()+getTotalMass());
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

	public boolean getLeftDoor() {
		return leftdoor;
	}
	
	public boolean getRightDoor() {
		return rightdoor;
	}

	
	public double getTotalMass() {
		return(p.getPassengerWeight()+trainmass);
	}
	
	public double getCumulativeDistance() {
		return(tm.getCumulativeDistance());
	}
	
	public void setEngineFailure(boolean b) {
		this.engineFailure=b;
		this.TrainModel_setBrake(b);
	}
	
	public void setSignalFailure(boolean b) {
		this.signalFailure=b;
		this.TrainModel_setBrake(b);

	}
	
	public void setBrakeFailure(boolean b) {
		this.brakeFailure=b;
		this.TrainModel_setEBrake(b);

	}
	
	
	public void setPassnegerEBrake(boolean b) {
		this.passengerEBrake=b;
		this.TrainModel_setEBrake(b);
	}
	
	public boolean getEngineFailure() {
		return engineFailure;
	}
	
	public boolean getSignalFailure() {
		return signalFailure;
	}
	
	public boolean getBrakeFailure() {
		return brakeFailure;
		
	}
	
	public boolean getPassengerEBrake() {
		return passengerEBrake;
		
	}

	public String getTrainLine() {
		return trainLine;
	}

	public void setTrainLine(String trainLine) {
		this.trainLine = trainLine;
	}
	
	public void setGrade(double g) {
		tm.setGrade(g);
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}
	
	public boolean getBrake() {
		return tm.getBrake();
	}
	
	public boolean getEBrake() {
		return tm.geteBrake();
	}
}
