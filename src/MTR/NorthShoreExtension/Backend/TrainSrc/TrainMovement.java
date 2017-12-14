package MTR.NorthShoreExtension.Backend.TrainSrc;
import java.lang.Math.*;

public class TrainMovement {
	private double mass; 			//in kg//
	private double engineForce;			//in Newtons//
	private double power;			//in Watts//
	private double acceleration;	//in m/s^2//
	private double velocity;		//in m/s//
	private double nextVelocity;
	private double distance;		//in m from the last second//
	private double normalForce; 	//in Newtons//
	private double xForce;			//in Newtons, this is the force in the x direction neglecting friction//
	private double totalForce; 		//in Newtowns//
	private double pheta;			//angle of the slope//
	private double cd;
	private boolean eBrake, brake;
	final double staticFriction = 0.001; //Static Friciton of hard Steel on Steel//
	final double kineticFriction = 0.001; //Kinetic Friction of hard Steel on steel//
	final double gravity =  9.807; //expressed in m/s^2//
	final double accLimit=0.5;
	final double velLimit=19.4444;
	final double brakeAcc = -1.2;
	final double eBrakeAcc = -2.73;
	
	
	public TrainMovement(double m){
		this.eBrake=false;
		this.brake=false;
		this.pheta=0;
		this.mass=m;
		this.velocity=0;
	}
	
	public void setMass(double m) {
		this.mass=m;
	}
	
	public void setGrade(double g) {
		this.pheta=Math.atan(g/100);
		//for calculations we're using pheta so it converts grade to pheta
		//The track model should call this function
	}
	
	public void setMovement(double p){
		if(brake==false&&eBrake==false) {
			
			this.power=p;
			
			normalForce = mass*gravity*Math.cos(pheta);
		    if(velocity==0){
		    	engineForce = power/0.1;
		    	xForce=engineForce-mass*gravity*Math.sin(pheta);
		    	
		    	if(Math.abs(xForce)>staticFriction*normalForce&&xForce>0) {
		    		totalForce=(xForce-staticFriction*normalForce); 
		    	}else if (Math.abs(xForce)<staticFriction*normalForce) {
		    		totalForce=0;
		    	}else {
		    		//totalForce=xForce+staticFriction*normalForce;
		    		totalForce=0;
		    	}
		    }else{
		    	engineForce=power/velocity;
		    	xForce=engineForce-mass*gravity*Math.sin(pheta);
		    	if(xForce>=0) {
		    		totalForce=xForce-kineticFriction*normalForce;
		    	}else {
		    		//totalForce=xForce+kineticFriction*normalForce;
		    		totalForce=0;
		    	}
		    }
		    acceleration = totalForce/mass;
			
		}else if(eBrake==true) {
			if(velocity>0) {
				acceleration = eBrakeAcc;
			}
		}else if(brake==true && eBrake==false){
			if(velocity>0) {
				acceleration = brakeAcc;
			}
		}
		
		
		if(acceleration>accLimit) {
			acceleration=accLimit;
		}
		
		nextVelocity = acceleration+velocity;
		if(nextVelocity<0) {
			nextVelocity=0;
			acceleration=0;
		}
		
		if(nextVelocity>velLimit) {
			nextVelocity=velLimit;
			acceleration=0;
		}
		distance = 0.5*acceleration+velocity;
		if(distance<0) {
			distance=0;
		}
		cd+=distance;
		//System.out.println("Velocity: "+velocity);
		//System.out.println("Next Velocity: "+nextVelocity);
		//System.out.println("Accerleration: "+acceleration);
		this.velocity = nextVelocity;
	}
	
	public double getVelocity() {
		return velocity;
	}
	
	public double getAcceleration() {
		return acceleration;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public double getCumulativeDistance() {
		return cd;
	}
	
	public void setBrake(boolean b) {
		this.brake=b;	
	}
	
	public void seteBrake(boolean b) {
		this.eBrake=b;	
	}
	
	public boolean getBrake() {
		return brake;
	}
	
	public boolean geteBrake() {
		return eBrake;
	}
}
