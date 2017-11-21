/*
 * Filename: WaysideFunctionsHub.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.Backend.WaysideController;

import java.awt.List;
import java.util.Arrays;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.TrackModelSrc.TrackModel;
import MTR.NorthShoreExtension.UI.TrackModelUI;
import MTR.NorthShoreExtension.UI.WaysideControllerUI;

public class WaysideController //this class is the logic to decide what to do with information
{
	public static int[] OccupiedTracks;
	public static int[] CurrentSwitchLayout;
	public static void AuthorityArray(int[][] IncomingAuthorityArray)
	{
		
	}
	public static void SpeedArray(int[] IncomingSpeedArray)
	{
		
	}
	public static void UpdateOccupiedTracks(int[] IncomingTrackArray)
	{
		OccupiedTracks = IncomingTrackArray;
		NorthGreenLine();
		SouthGreenLine();
		NorthRedLine();
		SouthRedLine();
	}
	public static void main(String[] args) 
	{

		


	}
	public static void NorthGreenLine()
	{
		List OccupancyList = (List) Arrays.asList(OccupiedTracks);
		int[] LayerTracks = {2013, 2014, 2002, 2003, 2027, 2028, 2150, 2149, 2057, 2056, 2062, 2061}; 
		for (int x = 0; x <LayerTracks.length; x++)
		{
			if (Arrays.asList(OccupiedTracks).contains(LayerTracks[x]))  //train approaching switch
			{
				if (LayerTracks[x] == 2013 || LayerTracks[x] == 2014)
				{
					TrackModel.TrackModel_setSwitch(2012, 1);
				}
				else if (LayerTracks[x] == 2003 || LayerTracks[x] == 2002)
				{
					TrackModel.TrackModel_setSwitch(2012, 0);
				}
				else if (LayerTracks[x] == 2049 || LayerTracks[x] == 2050)
				{
					TrackModel.TrackModel_setSwitch(2029, 1);
				}
				else if (LayerTracks[x] == 2027 || LayerTracks[x] == 2028)
				{
					TrackModel.TrackModel_setSwitch(2029, 0);
				}
				else if (LayerTracks[x] == 2056 || LayerTracks[x] == 2057)
				{
					TrackModel.TrackModel_setSwitch(2058, 1);
				}
				else if (LayerTracks[x] == 2061 || LayerTracks[x] == 2062)
				{
					TrackModel.TrackModel_setSwitch(2062, 1);
				}
				
			}
		}
	}
	public static void SouthGreenLine()
	{
		List OccupancyList = (List) Arrays.asList(OccupiedTracks);
		int[] LayerTracks = {2075, 2076, 2078, 2077, 2084, 2085, 2099, 2100}; 
		for (int x = 0; x <LayerTracks.length; x++)
		{
			if (Arrays.asList(OccupiedTracks).contains(LayerTracks[x]))  //train approaching switch
			{
				if (LayerTracks[x] == 2075 || LayerTracks[x] == 2076)
				{
					TrackModel.TrackModel_setSwitch(2076, 1);
				}
				else if (LayerTracks[x] == 2077 || LayerTracks[x] == 2078)
				{
					TrackModel.TrackModel_setSwitch(2076, 0);
				}
				else if (LayerTracks[x] == 2084 || LayerTracks[x] == 2085)
				{
					TrackModel.TrackModel_setSwitch(2086, 1);
				}
				else if (LayerTracks[x] == 2086 || LayerTracks[x] == 2087)
				{
					TrackModel.TrackModel_setSwitch(2086, 0);
				}

				
			}
		}
	}
	public static void NorthRedLine()
	{
		List OccupancyList = (List) Arrays.asList(OccupiedTracks);
		int[] LayerTracks = {1033,1032, 1031, 1072, 1073, 1028, 1027, 1026, 1076, 1075, 1017, 1016, 1015, 1014, 1001, 1002, 1008, 1009, 1010, 1011}; 
		for (int x = 0; x <LayerTracks.length; x++)
		{
			if (Arrays.asList(OccupiedTracks).contains(LayerTracks[x]))  //train approaching switch
			{
				if (LayerTracks[x] == 1033 || LayerTracks[x] == 1032)
				{
					TrackModel.TrackModel_setSwitch(1032, 0);
				}
				else if (LayerTracks[x] == 1028)
				{
					TrackModel.TrackModel_setSwitch(1027, 0);
				}
				else if (LayerTracks[x] == 1017 || LayerTracks[x] == 1016)
				{
					TrackModel.TrackModel_setSwitch(1015, 0);
				}
				else if (LayerTracks[x] == 1011 || LayerTracks[x] == 1010)
				{
					TrackModel.TrackModel_setSwitch(1009, 0);
				}
				else if (LayerTracks[x] == 1002 || LayerTracks[x] == 1001)
				{
					TrackModel.TrackModel_setSwitch(1015, 1);
				}
				else if (LayerTracks[x] == 1026)
				{
					TrackModel.TrackModel_setSwitch(1027,1);
				}
				else if (LayerTracks[x] == 1073 || LayerTracks[x] == 1072)
				{
					TrackModel.TrackModel_setSwitch(1032, 1);
				}
				
				
			}
		}
	}
	public static void SouthRedLine()
	{
		List OccupancyList = (List) Arrays.asList(OccupiedTracks);
		int[] LayerTracks = {1065,1066,1053,1052,1051,1044,1043,1042,1067,1068,1070,1071,1037,1038,1039}; 
		for (int x = 0; x <LayerTracks.length; x++)
		{
			if (Arrays.asList(OccupiedTracks).contains(LayerTracks[x]))  //train approaching switch
			{
				if (LayerTracks[x] == 1053)
				{
					TrackModel.TrackModel_setSwitch(1052, 0);
				}
				else if (LayerTracks[x] == 1044)
				{
					TrackModel.TrackModel_setSwitch(1043, 0);
				}
				else if (LayerTracks[x] == 1039)
				{
					TrackModel.TrackModel_setSwitch(1038, 0);
				}
				else if (LayerTracks[x] == 1037)
				{
					TrackModel.TrackModel_setSwitch(1038, 1);
				}
				else if (LayerTracks[x] == 1068 || LayerTracks[x] == 1067)
				{
					TrackModel.TrackModel_setSwitch(1043, 1);
				}
				else if (LayerTracks[x] == 1051)
				{
					TrackModel.TrackModel_setSwitch(1052,1);
				}
				
				
			}
		}
	}

}
