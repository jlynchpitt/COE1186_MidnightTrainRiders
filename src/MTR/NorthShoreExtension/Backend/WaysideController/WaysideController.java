
package MTR.NorthShoreExtension.Backend.WaysideController;

import java.awt.List;
import java.util.Stack;
import java.util.ArrayList;
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
	static Stack<Integer> StackOfOccupiedTracks = new Stack<>();
	public static int[][] TrackPlans = null;
	
	static ArrayList<ArrayList<Integer>> ListOfTrackPlans = new ArrayList<ArrayList<Integer>>();
	
	public static int[] NorthGreenSwitchArray;
	public static int[] SouthGreenSwitchArray;
	public static int[] NorthRedSwitchArray;
	public static int[] SouthRedSwitchArray;
	static Stack<Integer> NG = new Stack<>();
	static Stack<Integer> SG = new Stack<>();
	static Stack<Integer> NR = new Stack<>();
	static Stack<Integer> SR = new Stack<>();
	static DBHelper load = MainMTR.getDBHelper();
	
	
	
	public static void main(String[] args) 
	{
		int[] alpha = {0,1,2,3,4,5,6};
		int[] beta = {9,8,7,6,5,4,3};
		int[] delta = {11,22,33,44,55,66,77};
		int[] Occupy = {100, 200, 300};
		int[] FloorStreet = {11, 200, 300};
		UpdateOccupiedTracks(Occupy);
		AuthorityArray(100, alpha);
		System.out.println("-------------------------");
		AuthorityArray(200, beta);
		System.out.println("-------------------------");
		AuthorityArray(300, delta);
		System.out.println("-------------------------");
		AuthorityArray(100, delta);
		System.out.println("-------------------------");
		UpdateOccupiedTracks(FloorStreet);
		System.out.println("COMPLETE");
	}
	
	public static void AuthorityArray(int TrackID, int[] IncomingAuthorityArray)
	{
		ArrayList<Integer> StorageList = new ArrayList<Integer>();
		StorageList.add(TrackID);
		boolean inside = false;
		for (int x = 0; x < IncomingAuthorityArray.length; x++)
		{
			StorageList.add(IncomingAuthorityArray[x]);
		}
		//dummy list how holds the data
		
		//go through list of track plans
		for (int x = 0; x < ListOfTrackPlans.size(); x++)
		{
			//if the list as the location has Track ID
			if (ListOfTrackPlans.get(x).get(0) == TrackID)
			{
				ListOfTrackPlans.set(x, StorageList);
				inside = true;
				break;
			}
			
		}
		if (!inside)
		{
			ListOfTrackPlans.add(StorageList);
			
		}
		
		for (int x = 0; x < ListOfTrackPlans.size(); x++)
		{
			
			for(int y = 0; y < ListOfTrackPlans.get(x).size(); y++)
			{
				System.out.print("||" + ListOfTrackPlans.get(x).get(y));
			}
			System.out.println("");
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

	
	public static void TrackPlansUpdate(int TrackID)
	{
		for (int x = 0; x < ListOfTrackPlans.size(); x++)
		{
			
			if (TrackID == ListOfTrackPlans.get(x).get(1))
			{
				ArrayList<Integer> StorageList = new ArrayList<Integer>();
				for (int y = 1; y < ListOfTrackPlans.get(x).size(); y++)
				{
					StorageList.add(ListOfTrackPlans.get(x).get(y));
				}
				
				ListOfTrackPlans.set(x,StorageList);
			}
		}
		for (int x = 0; x < ListOfTrackPlans.size(); x++)
		{
			
			for(int y = 0; y < ListOfTrackPlans.get(x).size(); y++)
			{
				System.out.print("||" + ListOfTrackPlans.get(x).get(y));
			}
			System.out.println("");
		}
	}
	
	//takes a moment to decide actions based on occupied tracks
	public static void UpdateOccupiedTracks(int[] IncomingTrackArray)
	{
		
		OccupiedTracks = IncomingTrackArray;
		TrackPlans = new int[OccupiedTracks.length][];
		for (int x = 0; x < IncomingTrackArray.length; x++)
		{
			StackOfOccupiedTracks.push(IncomingTrackArray[x]);
			TrackPlansUpdate(IncomingTrackArray[x]);
		}
		TrackPlans = new int [IncomingTrackArray.length][];
		
		NorthGreenLine();
		SouthGreenLine();
		NorthRedLine();
		SouthRedLine();
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

			for (int x = 0; x < ListOfTrackPlans.size(); x++) //go through all the track plans
			{

					if (NG.contains(ListOfTrackPlans.get(x).get(1)))  //if an upcoming track is a switch
					{
						if (ListOfTrackPlans.get(x).get(1) == ListOfTrackPlans.get(x).get(0) + 1 || ListOfTrackPlans.get(x).get(1) == ListOfTrackPlans.get(x).get(0) + 1)  //if the next track is only one off of the previous, it goes in a straight line
						{
							if (ListOfTrackPlans.get(x).get(1) == 1) //if needs to be straight but not straight
							{
								//so set switch to straight
								WaysideFunctionsHub.WaysideController_Switch(ListOfTrackPlans.get(x).get(1));
							}
							
						}
						else  //if next track is not one difference, then it takes a diferent switch
						{
							if (load.getSwitch(ListOfTrackPlans.get(x).get(1)) == 0)  //if needs to be angled but straight
							{
								//so set switch to angle
								WaysideFunctionsHub.WaysideController_Switch(ListOfTrackPlans.get(x).get(1));
							}
						}
					}
				
			}
		
		
	}
	public static void SouthGreenLine()
	{
		for (int x = 0; x < ListOfTrackPlans.size(); x++) //go through all the track plans
		{

				if (SG.contains(ListOfTrackPlans.get(x).get(1)))  //if an upcoming track is a switch
				{
					if (ListOfTrackPlans.get(x).get(1) == ListOfTrackPlans.get(x).get(0) + 1 || ListOfTrackPlans.get(x).get(1) == ListOfTrackPlans.get(x).get(0) + 1)  //if the next track is only one off of the previous, it goes in a straight line
					{
						if (ListOfTrackPlans.get(x).get(1) == 1) //if needs to be straight but not straight
						{
							//so set switch to straight
							WaysideFunctionsHub.WaysideController_Switch(ListOfTrackPlans.get(x).get(1));
						}
						
					}
					else  //if next track is not one difference, then it takes a diferent switch
					{
						if (load.getSwitch(ListOfTrackPlans.get(x).get(1)) == 0)  //if needs to be angled but straight
						{
							//so set switch to angle
							WaysideFunctionsHub.WaysideController_Switch(ListOfTrackPlans.get(x).get(1));
						}
					}
				}
			
		}
		
	}
	public static void NorthRedLine()
	{
		for (int x = 0; x < ListOfTrackPlans.size(); x++) //go through all the track plans
		{

				if (NR.contains(ListOfTrackPlans.get(x).get(1)))  //if an upcoming track is a switch
				{
					if (ListOfTrackPlans.get(x).get(1) == ListOfTrackPlans.get(x).get(0) + 1 || ListOfTrackPlans.get(x).get(1) == ListOfTrackPlans.get(x).get(0) + 1)  //if the next track is only one off of the previous, it goes in a straight line
					{
						if (ListOfTrackPlans.get(x).get(1) == 1) //if needs to be straight but not straight
						{
							//so set switch to straight
							WaysideFunctionsHub.WaysideController_Switch(ListOfTrackPlans.get(x).get(1));
						}
						
					}
					else  //if next track is not one difference, then it takes a diferent switch
					{
						if (load.getSwitch(ListOfTrackPlans.get(x).get(1)) == 0)  //if needs to be angled but straight
						{
							//so set switch to angle
							WaysideFunctionsHub.WaysideController_Switch(ListOfTrackPlans.get(x).get(1));
						}
					}
				}
			
		}
		
	}
	public static void SouthRedLine()
	{
		for (int x = 0; x < ListOfTrackPlans.size(); x++) //go through all the track plans
		{

				if (SR.contains(ListOfTrackPlans.get(x).get(1)))  //if an upcoming track is a switch
				{
					if (ListOfTrackPlans.get(x).get(1) == ListOfTrackPlans.get(x).get(0) + 1 || ListOfTrackPlans.get(x).get(1) == ListOfTrackPlans.get(x).get(0) + 1)  //if the next track is only one off of the previous, it goes in a straight line
					{
						if (ListOfTrackPlans.get(x).get(1) == 1) //if needs to be straight but not straight
						{
							//so set switch to straight
							WaysideFunctionsHub.WaysideController_Switch(ListOfTrackPlans.get(x).get(1));
						}
						
					}
					else  //if next track is not one difference, then it takes a diferent switch
					{
						if (load.getSwitch(ListOfTrackPlans.get(x).get(1)) == 0)  //if needs to be angled but straight
						{
							//so set switch to angle
							WaysideFunctionsHub.WaysideController_Switch(ListOfTrackPlans.get(x).get(1));
						}
					}
				}
			
		}
		
	}

}
