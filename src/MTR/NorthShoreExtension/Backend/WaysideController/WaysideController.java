
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
	public static int[][] TrackPlans;
	public static void AuthorityArray(int TrackID, int[] IncomingAuthorityArray)
	{
		
		for (int x = 0; x < OccupiedTracks.length; x++)
		{
			for(int y = 0; y< IncomingAuthorityArray.length; y++)
			{
				TrackPlans[x][y] = TrackID;
				TrackPlans[x][y+1] = IncomingAuthorityArray[x];
			}
			
		}
	}
		

	public static void SpeedArray(int[] IncomingSpeedArray)
	{
		
	}
	public static void UpdateOccupiedTracks(int[] IncomingTrackArray)
	{
		OccupiedTracks = IncomingTrackArray;
		TrackPlans = new int [IncomingTrackArray.length][];
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
					for (int y = 0; y < TrackPlans.length; y++)
					{
						if (TrackPlans[y][0] == 1033)
						{
							if (TrackPlans[y][1] == 1032)
							{
								TrackModel.TrackModel_setSwitch(1032, 0);
							}
							if (TrackPlans[y][1] == 1072)
							{
								TrackModel.TrackModel_setSwitch(1032, 1);
							}
						}
					}
					
				}
				else if (LayerTracks[x] == 1028)
				{
					for (int y = 0; y < TrackPlans.length; y++)
					{
						if (TrackPlans[y][0] == 1028)
						{
							if (TrackPlans[y][1] == 1027)
							{
								TrackModel.TrackModel_setSwitch(1027, 0);
							}
						}
					}
				}
				else if (LayerTracks[x] == 1017 || LayerTracks[x] == 1016)
				{
					for (int y = 0; y < TrackPlans.length; y++)
					{
						if (TrackPlans[y][0] == 1016)
						{
							if (TrackPlans[y][1] == 1015)
							{
								TrackModel.TrackModel_setSwitch(1015, 0);
							}
							if (TrackPlans[y][1] == 1001)
							{
								TrackModel.TrackModel_setSwitch(1015, 1);
							}
						}
					}
				}
				else if (LayerTracks[x] == 1011 || LayerTracks[x] == 1010)
				{
					for (int y = 0; y < TrackPlans.length; y++)
					{
						if (TrackPlans[y][0] == 1010)
						{
							if (TrackPlans[y][1] == 1009)
							{
								TrackModel.TrackModel_setSwitch(1009, 0);
							}
							else
							{
								TrackModel.TrackModel_setSwitch(1009, 1);
							}
						}
					}
				}
				else if (LayerTracks[x] == 1002 || LayerTracks[x] == 1001)
				{
					TrackModel.TrackModel_setSwitch(1015, 1);
				}
				else if (LayerTracks[x] == 1026)
				{
					for (int y = 0; y < TrackPlans.length; y++)
					{
						if (TrackPlans[y][0] == 1026)
						{
							if (TrackPlans[y][1] == 1027)
							{
								TrackModel.TrackModel_setSwitch(1032, 0);
							}
							if (TrackPlans[y][1] == 1076)
							{
								TrackModel.TrackModel_setSwitch(1032, 1);
							}
						}
					}
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
					for (int y = 0; y < TrackPlans.length; y++)
					{
						if (TrackPlans[y][0] == 1044)
						{
							if (TrackPlans[y][1] == 1043)
							{
								TrackModel.TrackModel_setSwitch(1043, 0);
							}
							if (TrackPlans[y][1] == 1067)
							{
								TrackModel.TrackModel_setSwitch(1043, 1);
							}
						}
					}
				}
				else if (LayerTracks[x] == 1039)
				{
					TrackModel.TrackModel_setSwitch(1038, 0);
				}
				else if (LayerTracks[x] == 1037)
				{
					for (int y = 0; y < TrackPlans.length; y++)
					{
						if (TrackPlans[y][0] == 1037)
						{
							if (TrackPlans[y][1] == 1038)
							{
								TrackModel.TrackModel_setSwitch(1038, 0);
							}
							if (TrackPlans[y][1] == 1071)
							{
								TrackModel.TrackModel_setSwitch(1038, 1);
							}
						}
					}
				}
				else if (LayerTracks[x] == 1068 || LayerTracks[x] == 1067)
				{
					TrackModel.TrackModel_setSwitch(1043, 1);
				}
				else if (LayerTracks[x] == 1051)
				{
					for (int y = 0; y < TrackPlans.length; y++)
					{
						if (TrackPlans[y][0] == 1051)
						{
							if (TrackPlans[y][1] == 1052)
							{
								TrackModel.TrackModel_setSwitch(1052, 0);
							}
							if (TrackPlans[y][1] == 1066)
							{
								TrackModel.TrackModel_setSwitch(1052, 1);
							}
						}
					}
					TrackModel.TrackModel_setSwitch(1052,1);
				}
				
				
			}
		}
	}

}
