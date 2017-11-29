package MTR.NorthShoreExtension.Backend.TrackModelSrc;

import java.util.HashMap;
import java.util.Map;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.MainMTR3;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.TrainSrc.Train;
import MTR.NorthShoreExtension.Backend.WaysideController.WaysideFunctionsHub;
import MTR.NorthShoreExtension.UI.TrackModelUI;
import java.util.Random;

public class TrackModel {
	static WaysideFunctionsHub wayside;
	static TrackModel trackmod = new TrackModel();
	static TrackModelUI loading;
	//static Map<Integer, Track> trackList = new HashMap<Integer, Track>();
	static Track updateTrack;
	static int trackOccupency[] = new int[300];
	static int brokenTrack[] = new int[300];
	static Map<Integer, TrainsOperating> trainList = new HashMap<Integer, TrainsOperating>();
	static Map<Integer, Train> officialTrains = new HashMap<Integer, Train>();
	static TrainsOperating update;
	static double difference;
	static int speed, authority;
	static TrackModelUI helper = new TrackModelUI();
	static DBHelper load = MainMTR.getDBHelper();
	static Random rand = new Random();
	static int soldTicket = 0;
	static Train train;
	static Train updateTrains;
	static int lastTrack = 0;
		
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
		int trainOccupying;
	}
	
	class TrainsOperating{
		int trainID;
		double distanceLeft;
		int trackOccupying;
		String direction;
		int ticketsSold;
		int speed;
		int authority;
	}
	
	public void TrackModel_wayside(WaysideFunctionsHub t) {
		wayside = t;
	}
	
	public static void TrackModel_setSwitch(int trackid, int position) {
		load.setSwitch(trackid, position);
	}
	
	public static void TrackModel_setDistance(int trainNum, double distance) {
		int sub = 0;
		update = trainList.get(trainNum);
		int trackid = update.trackOccupying;
		trainList.remove(trainNum);
		difference = update.distanceLeft - distance;
		if(difference > 0) {
			update.distanceLeft = difference;
			trainList.put(trainNum, update);
		}
		else {
			load.updateTrackOccupied(trackid, 0);
			update.trackOccupying = load.getNextTrack(trackid, lastTrack);
			lastTrack = trackid;
			load.updateTrackOccupied(update.trackOccupying, 1);
			sub = load.getTrackLength(update.trackOccupying);
			difference = sub + difference;
			trainList.put(trainNum, update);
			officialTrains.get(trainNum).TrainModel_moveToNextTrack();
			if(MainMTR3.fullUI) {
				MainMTR3.moveTrack();
			}
		}
		sellTicket(update.trackOccupying);
		sendBeacon(update.trackOccupying, update.trainID);
	}
	
	public static void breakTrack(int id, String status) {
		if(brokenTrack.length == 0) {
			brokenTrack[0] = id;
		}
		else {
			brokenTrack[(brokenTrack.length)] = id;
		}
		load.updateTrackStatus(id, status);
		wayside.WaysideController_BrokenTrack(brokenTrack);
	}
	
	public static Map<Integer, Train> getTrains() {
		return officialTrains;
	}
	
	public static void TrackModel_addTrain(int trackid, int trainid) {
		TrackModel.TrainsOperating newTrain = trackmod.new TrainsOperating();
		newTrain.trainID = trainid; 
		newTrain.trackOccupying = trackid;
		newTrain.distanceLeft = load.getTrackLength(trackid);
		trainList.put(trainid, newTrain);
		trackOccupency[(trackOccupency.length - 1)] = trackid;
		/*updateTrack = trackList.get(trackid);
		trackList.remove(trackid);
		updateTrack.trainOccupying = trainid;
		trackList.put(trackid, updateTrack);*/
		load.updateTrackOccupied(trackid, 1);
		train = new Train(trainid);
		officialTrains.put(trainid, train);
		if(MainMTR.fullUI) {
			wayside.WaysideController_TrackOccupancy(trackOccupency);
		}
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
				type.equals("STATION; STATION SQUARE") || type.equals("STATION; SOUTH HILLS JUNCTION") ||
				type.equals("STATION; POPLAR")) {
			soldTicket += rand.nextInt(50) + 1;
		}
		else {
			soldTicket = soldTicket + 0;
		}
	}
	
	public int getTicketSales() {
		return soldTicket;
	}
	
	public static void TrackModel_setSpeedAuthority(int trackid, int s, int[] a) {
		/*updateTrack = trackList.get(trackid);
		trackList.remove(trackid);
		updateTrack.authority = a.length;
		updateTrack.speed = s;*/
		int trainid = 0;
		int auth = 0;
		if(a != null) {
			System.out.println("Valid authority array");
			auth = a.length;
		}
		load.updateSpeedAuthority(trackid, s, auth);
		if(load.getTrackOccupied(trackid)!=0)
		{
			for(Map.Entry<Integer, TrainsOperating> entry : trainList.entrySet()) {
				trainid = entry.getKey();
				if(trainList.get(trainid).trackOccupying == trackid) {
					updateTrains = officialTrains.get(trainid);
				}
			}
			/*for(int i = 0; i < officialTrains.size(); i++) {
				if(trainList.get(i).trackOccupying == trackid) {
					updateTrains = officialTrains.get(i);
				}
			}*/
			updateTrains.TrainModel_resendSpeedAuthority(s, auth);
		}
	}
	
	public static void sendBeacon(int trackid, int trainid) {
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
				type.equals("STATION; STATION SQUARE") || type.equals("STATION; SOUTH HILLS JUNCTION") ||
				type.equals("STATION; POPLAR")) {
		
				//set beacon here
		}
		if(type.equals("SWITCH TO/FROM YARD") || type.equals("SWITCH") || type.equals("SWITCH; UNDERGROUND") ||
				type.equals("SWITCH TO YARD") || type.equals("SWITCH FROM YARD")) {
			    //set beacon here
		}
	}

}
