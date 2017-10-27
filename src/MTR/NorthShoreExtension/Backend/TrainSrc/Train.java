package MTR.NorthShoreExtension.Backend.TrainSrc;

public class Train {
	final double length=32.2;
	final double height=3.42;
	final double width=2.65;
	final double trainmass=40900;
	
	
	private int cars; //static int variables
	private double acceleration, velocity, deceleration, passengermass; //dynamic float variables
	private int temperature, passengers, crewcount; //dynamic int variables
	private boolean leftdoor,rightdoor, lightson;
	private String authority;
	private double maxacceleration, maxvelocity, maxdeceleration;
	private int maxpassengers;
	
	public Train(){
		
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getDeceleration() {
		return deceleration;
	}

	public void setDeceleration(double deceleration) {
		this.deceleration = deceleration;
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


	public double getPassengermass() {
		return passengermass;
	}

	public void setPassengermass(double passengermass) {
		this.passengermass = passengermass;
	}
	
	public double getTrainmass() {
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
	public boolean getOpendoors() {
		return opendoors;
	}
	public void turnLightsOn(boolean b) {
		// TODO Auto-generated method stub
		this.lightson=b;
	}

	public void openLeftDoor(boolean b) {
		// TODO Auto-generated method stub
		this.leftdoor=b;
	}
	
	public void openRightDoor(boolean b) {
		this.rightdoor=b;
	}


	
	
}
