package MTR.NorthShoreExtension.Backend.TrackModelSrc;

import java.util.HashMap;
import java.util.Map;

public class TrackModel {
	//private WaysideControllerHelper wayside;
	private static int trackOccupency[] = new int[300];
	private static int brokenTrack[] = new int[300];
	private static Map<Integer, TrainsOperating> trainList = new HashMap<Integer, TrainsOperating>();
	private static TrainsOperating newTrain;
	private static TrainsOperating update;
	private static double difference;
	
	public TrackModel(/*WaysideControllerHelper t*/) {
		
	}
	
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
		int switchPosition;
		int x;
		int y;
		int curveStart;
		int curveEnd;
		int trackID;
	}
	
	class TrainsOperating{
		int trainID;
		double distanceLeft;
		int trackOccupying;
	}
	
	public static void loadCSV() {
			//read csv into database
	}
	
	public static void TrackModel_setSwitch(int trackid, int tracksend) {
		//set into db the trackid of the switch where it will go
	}
	
	public static void TrackModel_setDistance(int trainNum, double distance) {
		update = trainList.get(trainNum);
		trainList.remove(trainNum);
		difference = update.distanceLeft - distance;
		if(difference > 0) {
			update.distanceLeft = difference;
			trainList.put(trainNum, update);
		}
		else {
			//pull from db switchPosition for next track
			//if no switch then just add 1 to the track
			//if switch then take that value
			trainList.put(trainNum, update);
		}
	}
	
	public static void breakTrack(int id) {
		brokenTrack[(brokenTrack.length -1)] = id;
		//have wayside type and send the updated array of broken tracks
	}
	
	public static void TrackModel_addTrain(int trackid, int trainid) {
		newTrain.trainID = trainid; 
		newTrain.trackOccupying = trackid;
		//newTrain.trackOccupying = call to database that pulls the length of this track
		trainList.put(trainid, newTrain);
		trackOccupency[(trackOccupency.length - 1)] = trackid;
		//have wayside type and send the updated array of occupied tracks
	}
	
	public static void TrackModel_sellTicket() {
		
	}
	
	public static void TrackModel_setSpeedAuthority(int speed, int authority) {
		
	}

}
