package MTR.NorthShoreExtension.Backend;

public class UnitConverter {
	private static final double MPH_per_MPS = 2.23694;
	private static final double METERS_PER_MILE = 1609.34;
	private static final double KILOMETERS_PER_MILE = 1.60934;
	private static final double FEET_PER_METER = 3.28084;
	
	/**
	 * Convert miles per hour to meters per second
	 * 
	 * @param speed
	 * @return
	 */
	public static double MPHtoMPS(double speed) {
		return speed/MPH_per_MPS;
	}
	
	/**
	 * Convert meters per second to miles per hour
	 * 
	 * @param speed
	 * @return
	 */
	public static double MPStoMPH(double speed) {
		return speed*MPH_per_MPS;
	}
	
	/**
	 * Convert meters to miles
	 * 
	 * @param speed
	 * @return
	 */
	public static double metersToMiles(double distance) {
		return distance/METERS_PER_MILE;
	}
	
	/**
	 * Convert miles to meters
	 * 
	 * @param speed
	 * @return
	 */
	public static double milesToMeters(double distance) {
		return distance*METERS_PER_MILE;
	}
	
	/**
	 * Convert feet to meters
	 * 
	 * @param speed
	 * @return
	 */
	public static double feetToMeters(double distance) {
		return distance/FEET_PER_METER;
	}
	
	/**
	 * Convert meters to feet
	 * 
	 * @param speed
	 * @return
	 */
	public static double metersToFeet(double distance) {
		return distance*FEET_PER_METER;
	}
	
	/**
	 * Convert kilometers to miles
	 * 
	 * @param speed
	 * @return
	 */
	public static double kilometersToMiles(double distance) {
		return distance/KILOMETERS_PER_MILE;
	}
	
	/**
	 * Convert miles to kilometers
	 * 
	 * @param speed
	 * @return
	 */
	public static double milesToKilometers(double distance) {
		return distance*KILOMETERS_PER_MILE;
	}
}
