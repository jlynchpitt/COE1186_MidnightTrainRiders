package MTR.NorthShoreExtension.Backend.TrackModelSrc;

import java.util.HashMap;
import java.util.Map;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.MainMTR3;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.TrainSrc.Train;
import MTR.NorthShoreExtension.Backend.WaysideController.WaysideFunctionsHub;
import MTR.NorthShoreExtension.UI.TrackModelUI;
import MTR.NorthShoreExtension.UI.TrainModelUI;
import MTR.NorthShoreExtension.UI.ctcUI;

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
	static ctcUI ctc;
		
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
		int location = 0;
		update = trainList.get(trainNum);
		int trackid = update.trackOccupying;
		trainList.remove(trainNum);
		difference = update.distanceLeft - distance;
		if(difference > 0) {
			update.distanceLeft = difference;
			trainList.put(trainNum, update);
		}
		else {
			for(int i = 0; i < trackOccupency.length; i++)
			{
				if(trackOccupency[i]==trackid) {
					location = i;
				}
			}
			load.updateTrackOccupied(trackid, 0);
			update.trackOccupying = load.getNextTrack(trackid, lastTrack);
			lastTrack = trackid;
			load.updateTrackOccupied(update.trackOccupying, 1);
			sub = load.getTrackLength(update.trackOccupying);
			update.distanceLeft = sub + difference;
			trainList.put(trainNum, update);
			trackOccupency[location] = update.trackOccupying;
			officialTrains.get(trainNum).TrainModel_moveToNextTrack();
			if(MainMTR3.fullUI) {
				MainMTR3.moveTrack();
			}
			if(MainMTR.fullUI) {
				wayside.WaysideController_TrackOccupancy(trackOccupency);
			}
			sendBeacon(update.trackOccupying, update.trainID);
			sellTicket(update.trackOccupying, trainNum);
		}
		//System.out.println("Difference: "+difference);
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
		System.out.println("adding train: "+trainid+" at track: "+trackid);
		TrackModel.TrainsOperating newTrain = trackmod.new TrainsOperating();
		newTrain.trainID = trainid; 
		newTrain.trackOccupying = trackid;
		newTrain.distanceLeft = load.getTrackLength(trackid);
		trainList.put(trainid, newTrain);
		trackOccupency[trackOccupency.length-1] = trackid;
		/*updateTrack = trackList.get(trackid);
		trackList.remove(trackid);
		updateTrack.trainOccupying = trainid;
		trackList.put(trackid, updateTrack);*/
		load.updateTrackOccupied(trackid, 1);
		train = new Train(trainid, trackid);
		officialTrains.put(trainid, train);
		if(MainMTR.fullUI) {
			wayside.WaysideController_TrackOccupancy(trackOccupency);
		}
		if(MainMTR.getTrainModelUI()!=null) {
			MainMTR.getTrainModelUI().addTrain();
		}
	}
	
	public static void sellTicket(int trackid, int trainid) {
		String type = load.getInfrastructure(trackid);
		int peopleOn = 0;
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
			System.out.println("Tickets sold:" + soldTicket);
			updateTrains = officialTrains.get(trainid);
			updateTrains.TrainModel_setNumberOfPassengers(soldTicket);
			ctc.setThroughput(soldTicket);
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
		//String type = load.getInfrastructure(trackid);
		Train sendBeacon = officialTrains.get(trainid);
		int beacon = 0;
		if(trackid == 2001) {
			beacon = 539099136;
		}
		else if(trackid == 2011) {
			beacon = 1633820672;
		}
		else if(trackid == 2030) {
			beacon = 600440832;	
		}
		else if(trackid == 2150) {
			beacon = 1925324800;
		}
		else if(trackid == 2059) {
			beacon = 1734483968;
		}
		else if(trackid == 2075) {
			beacon = 1768038400;
		}
		else if(trackid == 2101) {
			beacon = 1822564352;
		}
		else if(trackid == 2087) {
			beacon = 720896000;
		}
		else if(trackid == 2100) {
			beacon = 1820467200;
		}
		else if(trackid == 2003) {
			beacon = 543170560;
		}
		else if(trackid == 2008) {
			beacon = 553910272;
		}
		else if(trackid == 2010) {
			beacon = 557858816;
		}
		else if(trackid == 2021) {
			beacon = 581435392;
		}
		else if(trackid == 2023) {
			beacon = 585138176;
		}
		else if(trackid == 2032) {
			beacon = 604020736;
		}
		else if(trackid == 2038) {
			beacon = 617349120;
		}
		else if(trackid == 2040) {
			beacon = 620806144;
		}
		else if(trackid == 2047) {
			beacon = 636354560;
		}
		else if(trackid == 2049) {
			beacon = 639688704;
		}
		else if(trackid == 2056) {
			beacon = 655360000;
		}
		else if(trackid == 2058) {
			beacon = 658571264;
		}
		else if(trackid == 2064) {
			beacon = 672268288;
		}
		else if(trackid == 2066) {
			beacon = 675356672;
		}
		else if(trackid == 2072) {
			beacon = 689176576;
		}
		else if(trackid == 2074) {
			beacon = 692142080;
		}
		else if(trackid == 2076) {
			beacon = 697696256;
		}
		else if(trackid == 2078) {
			beacon = 700538880;
		}
		else if(trackid == 2089) {
			beacon = 723615744;
		}
		else if(trackid == 2095) {
			beacon = 737804288;
		}
		else if(trackid == 2097) {
			beacon = 740401152;
		}
		else if(trackid == 2104) {
			beacon = 756285440;
		}
		else if(trackid == 2106) {
			beacon = 759250944;
		}
		else if(trackid == 2113) {
			beacon = 775028736;
		}
		else if(trackid == 2115) {
			beacon = 778117120;
		}
		else if(trackid == 2122) {
			beacon = 793772032;
		}
		else if(trackid == 2124) {
			beacon = 796983296;
		}
		else if(trackid == 2131) {
			beacon = 812515328;
		}
		else if(trackid == 2133) {
			beacon = 815849472;
		}
		else if(trackid == 2140) {
			beacon = 831258624;
		}
		else if(trackid == 2142) {
			beacon = 834715648;
		}
		else if(trackid == 2015) {
			beacon = 568721408;
		}
		else if(trackid == 2017) {
			beacon = 572547072;
		}
		else if(trackid == 1010) {
			beacon = 1094852608;
		}
		else if(trackid == 1001) {
			beacon = 1075978240;
		}
		else if(trackid == 1014) {
			beacon = 1103241216;
		}
		else if(trackid == 1028) {
			beacon = 1132601344;
		}
		else if(trackid == 1031) {
			beacon = 1138892800;
		}
		else if(trackid == 1039) {
			beacon = 1155670016;
		}
		else if(trackid == 1042) {
			beacon = 1161961472;
		}
		else if(trackid == 1076) {
			beacon = 1233264640;
		}
		else if(trackid == 1072) {
			beacon = 1224876032;
		}
		else if(trackid == 1071) {
			beacon = 1222778880;
		}
		else if(trackid == 1067) {
			beacon = 1214390272;
		}
		else if(trackid == 1053) {
			beacon = 1185030144;
		}
		else if(trackid == 1066) {
			beacon = 1212293120;
		}
		else if(trackid == 1006) {
			beacon = 12713984;
		}
		else if(trackid == 1008) {
			beacon = 16785408;
		}
		else if(trackid == 1015) {
			beacon = 31719424;
		}
		else if(trackid == 1017) {
			beacon = 35667968;
		}
		else if(trackid == 1020) {
			beacon = 42336256;
		}
		else if(trackid == 1022) {
			beacon = 46161920;
		}
		else if(trackid == 1024) {
			beacon = 50855936;
		}
		else if(trackid == 1026) {
			beacon = 54558720;
		}
		else if(trackid == 1034) {
			beacon = 71958528;
		}
		else if(trackid == 1036) {
			beacon = 75538432;
		}
		else if(trackid == 1044) {
			beacon = 93061120;
		}
		else if(trackid == 1046) {
			beacon = 96518144;
		}
		else if(trackid == 1047) {
			beacon = 99483648;
		}
		else if(trackid == 1049) {
			beacon = 102817792;
		}
		else if(trackid == 1059) {
			beacon = 124780544;
		}
		else if(trackid == 1061) {
			beacon = 127991808;
		}
		
		if(sendBeacon != null) {
			sendBeacon.TrainModel_sendBeacon(beacon);
		}
	}

}
