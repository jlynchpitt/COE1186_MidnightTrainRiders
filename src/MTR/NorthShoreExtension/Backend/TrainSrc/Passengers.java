package MTR.NorthShoreExtension.Backend.TrainSrc;
import java.util.Random;

public class Passengers {
	final double averageWeight = 80.7; //average weight of a north american in kg https://en.wikipedia.org/wiki/Human_body_weight
	final int crewCount=1;
	final int maxPassengers=148;
	
	private int totalPassengers;
	private double passengerWeight;
	
	public Passengers() {
		passengerWeight=0;
	}
	
	public void passengersIn() {
		Random rand = new Random();
		totalPassengers+=rand.nextInt(20);
		passengerWeight=totalPassengers*averageWeight;
	}
	
	public void passengersOut() {
		Random rand = new Random();
		int x;
		do {
			x=rand.nextInt(20);
		}while(totalPassengers-x<0);
		totalPassengers-=x;
		passengerWeight=totalPassengers*averageWeight;
	}
	
	public double getPassengerWeight() {
		return(passengerWeight);
	}
	
	public int getTotalPassengers() {
		return totalPassengers;
	}
}
