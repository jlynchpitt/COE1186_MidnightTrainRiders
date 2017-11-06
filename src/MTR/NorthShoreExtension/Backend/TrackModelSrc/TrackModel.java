package MTR.NorthShoreExtension.Backend.TrackModelSrc;

import java.util.HashMap;
import java.util.Map;

public class TrackModel {
	//private WaysideControllerHelper wayside;
	static Map<Integer, Track> trackList = new HashMap<Integer, Track>();
	static Track updateTrack;
	static int trackOccupency[] = new int[300];
	static int brokenTrack[] = new int[300];
	static Map<Integer, TrainsOperating> trainList = new HashMap<Integer, TrainsOperating>();
	static TrainsOperating newTrain;
	static TrainsOperating update;
	static double difference;
	static int speed, authority;
	
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
		int trackNext;
		int beacon;
	}
	
	class TrainsOperating{
		int trainID;
		double distanceLeft;
		int trackOccupying;
	}
	
	public TrackModel(/*WaysideControllerHelper t*/) {
		
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
		//random number generator at each station
	}
	
	public static void TrackModel_setSpeedAuthority(int trackid, int s, int a) {
		updateTrack = trackList.get(trackid);
		trackList.remove(trackid);
		updateTrack.authority = a;
		updateTrack.speed = s;
		trackList.put(trackid, updateTrack);
		//train update speed and authority 
		//convert authority to number of blocks
	}
	
	public static void beacon() {
		//if infrastructure = station
		//if infrastructure = switch
		//call Joji's beacon function
	}

}
