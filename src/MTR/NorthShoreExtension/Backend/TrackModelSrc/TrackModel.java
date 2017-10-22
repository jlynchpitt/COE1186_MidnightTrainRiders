package MTR.NorthShoreExtension.Backend.TrackModelSrc;

public class TrackModel {
	class Track{
		String linestatus; 
		int speed;
		int authority;
		String section;
		int blockLength;
		double blockGrade;
		int speedLimit;
		String infrastructure;
		double elevation;
		double cumulativeElevation;
		String trackStatus;
		String heater;
	}
	
	public static Track[] loadCSV() {
		Track[] lineInfo = new Track[100];
		return lineInfo;
	}
}
