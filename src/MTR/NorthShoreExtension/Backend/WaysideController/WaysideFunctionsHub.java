/*
 * Filename: WaysideFunctionsHub.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Receives and bounces data to appropriate parties.  These include the CTC and TrackModel and pass on 
 * information to the WaysideController, who will take the appropriate action
 * 
 */
package MTR.NorthShoreExtension.Backend.WaysideController;

import java.awt.List;
import java.util.Arrays;
import java.util.Stack;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.Backend.CTCSrc.TrainScheduleHelper;
import MTR.NorthShoreExtension.Backend.TrackModelSrc.TrackModel;
import MTR.NorthShoreExtension.UI.TrackModelUI;
import MTR.NorthShoreExtension.UI.WaysideControllerUI;


public class WaysideFunctionsHub //the purpose of this class is to receive and organize the information
{
	
	/*
	 *Information received: All occupied tracks, authority of trains on those tracks, the speed, broken tracks
	 */
	public static int[] AuthorityArray;
	public static int[] SpeedArray;
	public static int[] TrackOccupancyArray;
	public static int[] BrokenTrackArray;
	public static int[] OccupiedTrackArray;
	
	static Stack<Integer> OccupiedTracks = new Stack<>();
	public static int[] AuthorArray = {2130,2131,2141,2145,2165,2146,2147};
	public static WaysideFunctions obj = new WaysideFunctions();
	public static WaysideControllerUI obj2 = new WaysideControllerUI();
	static WaysideControllerUI helper = new WaysideControllerUI();
	static DBHelper load = MainMTR.getDBHelper();
	//static DBHelper load = helper.sendDB();
	
	public static void main(String[] args) 
	{


	}


	//CTC calls this to send me information on the speed of the occupied tracks and the authority
	//calls this multiple times
	//trackID of track currently occupied
	//the speed
	//authority
	//not sure if correct
	public static void OccupiedSpeedAuthority(int TrackID, int Speed, int[] Authority) // CTC calls this to send me info
	{
		WaysideFunctions.TrackModel_setSpeedAuthority(TrackID, Speed, Authority.length);
		TrackModel.TrackModel_setSpeedAuthority(TrackID, Speed, Authority); //send the data to track model
		//if the track id is something occupied, pass it to logic controller
		if (Arrays.asList(TrackOccupancyArray).contains(TrackID)) 
		{
			WaysideController.AuthorityArray(TrackID, Authority);
		}
		
		//UI stuff------------------------------------------------------------------
		int TotalLength = 0;
		for (int x = 0; x < Authority.length; x++)
		{
			TotalLength += 50;
		}

		if (Authority.length > 1)
		{
			WaysideControllerUI.OccupiedTrackAuthoritySpeedUpdater(TrackID, Authority[1], TotalLength);
		}
		else
		{
			WaysideControllerUI.OccupiedTrackAuthoritySpeedUpdater(TrackID, 0, TotalLength);
		}
		
		
	}
	
	//CTC calls this to test the switch
	//will alternate the switch positions
	public static int WaysideController_Switch(int SwitchID)   
	 {
		//System.out.println("Switch ID: " + SwitchID);
		//check to see if conditions are safe
		if (load.getSwitch(SwitchID) == 0)	
		{
			TrackModel.TrackModel_setSwitch(SwitchID, 1);
		}
		else
		{
			TrackModel.TrackModel_setSwitch(SwitchID, 0);
		}
		
		WaysideControllerUI.SwitchChartUpdater(SwitchID);
		return 0;
		   //update switch
	 }
	
	   //TM calls this to send me array of tracks that are occupied
		//sends the occupied tracks to the CTC for further information
	   public static void WaysideController_TrackOccupancy(int[] IncomingTrackOccupancyArray)
	   {
		   
		   OccupiedTrackArray = IncomingTrackOccupancyArray;
		   for (int x = 0; x < IncomingTrackOccupancyArray.length; x++)
		   {
			   OccupiedTracks.push(IncomingTrackOccupancyArray[x]);
		   }
		   WaysideController.UpdateOccupiedTracks(IncomingTrackOccupancyArray);
		   //TrainScheduleHelper.CTC_getOccupiedTracks(IncomingTrackOccupancyArray);  

		   // Just stuff for the UI----------------------------------------------------
		   int ArrayLength = IncomingTrackOccupancyArray.length;
		   Object[][] multi = new Object[ArrayLength][4];
		   for (int x = 0; x < IncomingTrackOccupancyArray.length; x++)
		   {

			   String IncomingNumber = Integer.toString(IncomingTrackOccupancyArray[x]);
			   String LineColor = null;
			   
			   int firstDigit = Character.getNumericValue(IncomingNumber.charAt(0));
			   String BlockNumber =  IncomingNumber.substring(1);
			   //System.out.println("BLOCK: " + BlockNumber);

			   if (firstDigit == 1)
			   {
				   LineColor = "Red";
			   }
			   if (firstDigit == 2)
			   {
				   LineColor = "Green";
			   }
			
				multi[x][0] = LineColor;
				multi[x][1] = BlockNumber;
				multi[x][2] = "Placeholder";
				multi[x][3] = "Placeholder";
		   }


		   WaysideControllerUI.OccupiedTrackTableUpdater(multi);
		   WaysideFunctions.CTC_getOccupancy(IncomingTrackOccupancyArray);
	   }
	   
	   
	   //TM calls this to send array of broken tracks
	   //sends the broken tracks to CTC for further information
	   public static void WaysideController_BrokenTrack(int[] IncomingBrokenTrackArray)  //TM calls this to send me broken track info
	   {
		   
		   BrokenTrackArray = IncomingBrokenTrackArray;
			WaysideFunctions.CTC_getBrokenTrack(BrokenTrackArray);
			//TrainScheduleHelper.CTC_getBrokenTrack(BrokenTrackArray);

	   }
   

}
