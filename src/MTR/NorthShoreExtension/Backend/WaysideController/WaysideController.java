
package MTR.NorthShoreExtension.Backend.WaysideController;

import java.awt.List;
import java.util.Stack;
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
	public static int[][] TrackPlans = null;
	public static int[] NorthGreenSwitchArray;
	public static int[] SouthGreenSwitchArray;
	public static int[] NorthRedSwitchArray;
	public static int[] SouthRedSwitchArray;
	static Stack<Integer> NG = new Stack<>();
	static Stack<Integer> SG = new Stack<>();
	static Stack<Integer> NR = new Stack<>();
	static Stack<Integer> SR = new Stack<>();
	static DBHelper load = MainMTR.getDBHelper();
	
	public static void AuthorityArray(int TrackID, int[] IncomingAuthorityArray)
	{
		
		for (int x = 0; x < OccupiedTracks.length; x++)
		{
			for(int y = 0; y< IncomingAuthorityArray.length+1; y++)
			{
				TrackPlans[x][y] = TrackID;
				TrackPlans[x][y+1] = IncomingAuthorityArray[x];
			}
			
		}
	}
		

	//read in the switches
	//create four stacks of switches  based on location
	public static void UpdateSwitches()  
	{
		//stack.toArray(array)
		//go through all green tracks
		for (int x = 0; x < 150; x++)
		{
			//give it proper code
			int input = 2000 + x;
			
			//if the infrastructure at that track is a switch
			if (load.getInfrastructure(input).equals("swtich"))
			{
				//if the switch designation is within these parameters
				if (input >= 2000 && input <= 2068 || input >= 2110 && input <= 2150)
				{
					//push it as a north green switch
					NG.push(input);
				}
				if (input > 2068 && input < 2110)
				{
					//push it as a north green switch
					SG.push(input);
				}
			}
		}
		//go through all the red tracks
		for (int x = 0; x < 76; x++)
		{
			//give it proper coding
			int input = 1000 + x;
			
			//if the infrastructure if a switch
			if (load.getInfrastructure(input).equals("swtich"))
			{
				//if the track possesses certain designations
				if (input >= 1000 && input <= 1035 || input >= 1072 && input <= 1076)
				{
					//push it as a north red switch
					NR.push(input);
				}
				if (input > 1035 && input < 1072)
				{
					//push it as a south red switch
					SR.push(input);
				}
			}
		}
	}

	
	//takes a moment to decide actions based on occupied tracks
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
	
	/*
	public static void RailwayCrossingCheck()
	{
		if (TrackPlans != null)
		{
			for (int x = 0; x < TrackPlans.length; x++) //go through all the track plans
			{
				if (TrackPlans[x] != null)
				{
					for (int y = 0; y < TrackPlans[x].length; y++)
					{
						if (load.getInfrastructure(TrackPlans[x][y]).equals("Railway Crossing") || load.getInfrastructure(TrackPlans[x][y+1]).equals("Railway Crossing") || load.getInfrastructure(TrackPlans[x][y-1]).equals("Railway Crossing")) //if an upcoming track is a crossing
						{
							
						}
					{
						if (TrackPlans[x][1] == TrackPlans[x][0] + 1 || TrackPlans[x][1] == TrackPlans[x][0] + 1)  //if the next track is only one off of the previous, it goes in a straight line
						{
							TrackModel.TrackModel_setSwitch(TrackPlans[x][1], 0);  //so set switch to straight
						}
						else  //if next track is not one difference, then it takes a diferent switch
						{
							TrackModel.TrackModel_setSwitch(TrackPlans[x][1], 1);  //set switch angled
						}
					}
					}
					
				}
			}
		}
	}
	
	*/
	public static void NorthGreenLine()
	{
		if (TrackPlans != null)
		{
			for (int x = 0; x < TrackPlans.length; x++) //go through all the track plans
			{
				if (TrackPlans[x] != null)
				{
					if (NG.contains(TrackPlans[x][1]))  //if an upcoming track is a switch
					{
						if (TrackPlans[x][1] == TrackPlans[x][0] + 1 || TrackPlans[x][1] == TrackPlans[x][0] - 1)  //if the next track is only one off of the previous, it goes in a straight line
						{
							if (load.getSwitch(TrackPlans[x][1]) == 1) //if needs to be straight but not straight
							{
								//so set switch to straight
								WaysideFunctionsHub.WaysideController_Switch(TrackPlans[x][1]);
							}
							
						}
						else  //if next track is not one difference, then it takes a diferent switch
						{
							if (load.getSwitch(TrackPlans[x][1]) == 0)  //if needs to be angled but straight
							{
								//so set switch to angle
								WaysideFunctionsHub.WaysideController_Switch(TrackPlans[x][1]);
							}
						}
					}
				}
			}
		}
		
	}
	public static void SouthGreenLine()
	{
		if (TrackPlans != null)
		{
			for (int x = 0; x < TrackPlans.length; x++) //go through all the track plans
			{
				if (TrackPlans[x] != null)
				{
					if (SG.contains(TrackPlans[x][1]))  //if an upcoming track is a switch
					{
						if (TrackPlans[x][1] == TrackPlans[x][0] + 1 || TrackPlans[x][1] == TrackPlans[x][0] - 1)  //if the next track is only one off of the previous, it goes in a straight line
						{
							if (load.getSwitch(TrackPlans[x][1]) == 1) //if needs to be straight but not straight
							{
								//so set switch to straight
								WaysideFunctionsHub.WaysideController_Switch(TrackPlans[x][1]);
							}
							
						}
						else  //if next track is not one difference, then it takes a diferent switch
						{
							if (load.getSwitch(TrackPlans[x][1]) == 0)  //if needs to be angled but straight
							{
								//so set switch to angle
								WaysideFunctionsHub.WaysideController_Switch(TrackPlans[x][1]);
							}
						}
					}
				}
				
			}
		}
		
	}
	public static void NorthRedLine()
	{
		if (TrackPlans != null)
		{
			for (int x = 0; x < TrackPlans.length; x++) //go through all the track plans
			{
				if (TrackPlans[x] != null)
				{
					if (NR.contains(TrackPlans[x][1]))  //if an upcoming track is a switch
					{
						if (TrackPlans[x][1] == TrackPlans[x][0] + 1 || TrackPlans[x][1] == TrackPlans[x][0] - 1)  //if the next track is only one off of the previous, it goes in a straight line
						{
							if (load.getSwitch(TrackPlans[x][1]) == 1) //if needs to be straight but not straight
							{
								//so set switch to straight
								WaysideFunctionsHub.WaysideController_Switch(TrackPlans[x][1]);
							}
							
						}
						else  //if next track is not one difference, then it takes a diferent switch
						{
							if (load.getSwitch(TrackPlans[x][1]) == 0)  //if needs to be angled but straight
							{
								//so set switch to angle
								WaysideFunctionsHub.WaysideController_Switch(TrackPlans[x][1]);
							}
						}
					}
				}
				
			}
		}
		
	}
	public static void SouthRedLine()
	{
		if (TrackPlans != null)
		{
			for (int x = 0; x < TrackPlans.length; x++) //go through all the track plans
			{
				if (TrackPlans[x] != null)
				{
					if (SR.contains(TrackPlans[x][1]))  //if an upcoming track is a switch
					{
						if (TrackPlans[x][1] == TrackPlans[x][0] + 1 || TrackPlans[x][1] == TrackPlans[x][0] - 1)  //if the next track is only one off of the previous, it goes in a straight line
						{
							if (load.getSwitch(TrackPlans[x][1]) == 1) //if needs to be straight but not straight
							{
								//so set switch to straight
								WaysideFunctionsHub.WaysideController_Switch(TrackPlans[x][1]);
							}
							
						}
						else  //if next track is not one difference, then it takes a diferent switch
						{
							if (load.getSwitch(TrackPlans[x][1]) == 0)  //if needs to be angled but straight
							{
								//so set switch to angle
								WaysideFunctionsHub.WaysideController_Switch(TrackPlans[x][1]);
							}
						}
					}
				}
				
			}
		}
		
	}

}
