package MTR.NorthShoreExtension.Backend.TrackModelSrc;

public class TrackModel {
	int trackOccupency[] = new int[300];
	int brokenTrack[] = new int[300];
	TrainsOperating trainList[] = new TrainsOperating[300];
	
	class Track{
		String line;
		boolean occupied; 
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
		int x;
		int y;
		int curveStart;
		int curveEnd;
	}
	
	class TrainsOperating{
		int traindID;
		double distanceLeft;
		int trackOccupying;
	}
	
	public static void loadCSV() {
			//read csv into database
	}
	
	public static void TrackModel_setDistance(int trainNum, double distance) {
		
	}
	
	public static void breakTrack() {
		
	}
	
	public static void addTrain() {
		
	}

}
