package MTR.NorthShoreExtension.Backend.TrackModelSrc;

import java.util.HashMap;
import java.util.Map;

import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.UI.TrackModelUI;
import java.util.Random;

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
	static TrackModelUI helper = new TrackModelUI();
	static DBHelper load = helper.sendDB();
	static Random rand = new Random();
	static int soldTicket = 0;
		
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
		String direction;
		int ticketsSold;
	}
	
	public TrackModel(/*WaysideControllerHelper t*/) {
		
	}
	
	public static void TrackModel_setSwitch(int trackid, int position) {
		load.setSwitch(trackid, position);
	}
	
	public static void TrackModel_setDistance(int trainNum, double distance) {
		update = trainList.get(trainNum);
		int trackid = update.trackOccupying;
		trainList.remove(trainNum);
		difference = update.distanceLeft - distance;
		if(difference > 0) {
			update.distanceLeft = difference;
			trainList.put(trainNum, update);
		}
		else {
			update.trackOccupying = load.getNextTrack(trackid, update.direction);
			trainList.put(trainNum, update);
		}
		sellTicket(update.trackOccupying);
	}
	
	public static void breakTrack(int id) {
		brokenTrack[(brokenTrack.length -1)] = id;
		//have wayside type and send the updated array of broken tracks
	}
	
	public static void TrackModel_addTrain(int trackid, int trainid) {
		newTrain.trainID = trainid; 
		newTrain.trackOccupying = trackid;
		newTrain.distanceLeft = load.getTrackLength(trackid);
		trainList.put(trainid, newTrain);
		trackOccupency[(trackOccupency.length - 1)] = trackid;
		//have wayside type and send the updated array of occupied tracks
	}
	
	public static void sellTicket(int trackid) {
		String type = load.getInfrastructure(trackid);
		if(type.equals("STATION") || type.equals("STATION; PIONEER") || 
				type.equals("STATION; EDGEBROOK") || type.equals("STATION; WHITED") || 
				type.equals("STATION; SOUTH BANK") || type.equals("STATION; CENTRAL; UNDERDROUND") ||
				type.equals("STATION; INGLEWOOD; UNDERGROUND") || type.equals("STATION; OVERBROOK; UNDERGROUND") ||
				type.equals("STATION; GLENBURY") || type.equals("STATION; DORMONT") ||
				type.equals("STATION; MT LEBANON") || type.equals("STATION; CASTLE SHANNON") ||
				type.equals("STATION: SHADYSIDE") || type.equals("STATION: HERRON AVE") ||
				type.equals("STATION; SWISSVILLE") || type.equals("STATION;    PENN STATION; UNDERGROUND") ||
				type.equals("STATION; STEEL PLAZA; UNDERGROUND") || type.equals("STATION; FIRST AVE; UNDERGROUND") ||
				type.equals("STATION; STATION SQUARE") || type.equals("STATION; SOUTH HILLS JUNCTION")) {
			soldTicket += rand.nextInt(50) + 1;
		}
		else {
			soldTicket = soldTicket + 0;
		}
	}
	
	public int getTicketSales() {
		return soldTicket;
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
