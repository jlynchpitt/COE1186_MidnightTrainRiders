/*
 * Filename: WaysideFunctionsHub.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.Backend.WaysideController;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;
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
	public static int[] AuthorArray = {2130,2131,2141,2145,2165,2146,2147};
	public static WaysideFunctions obj = new WaysideFunctions();
	public static WaysideControllerUI obj2 = new WaysideControllerUI();
	static WaysideControllerUI helper = new WaysideControllerUI();
	//static DBHelper load = helper.sendDB();
	
	public static void main(String[] args) 
	{


	}


	//CTC calls this to send me information
	//calls this multiple times
	//trackID of track currently occupied
	//the next track downt he line
	//the speed
	//authority
	//not sure if correct
	public static void OccupiedSpeedAuthority(int TrackID, int Speed, int[] Authority) // CTC calls this to send me info
	{
		WaysideFunctions.TrackModel_setSpeedAuthority(TrackID, Speed, Authority.length);
		TrackModel.TrackModel_setSpeedAuthority(TrackID, Speed, Authority);
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
	

	public static int WaysideController_Switch(int SwitchID)   //CTC calls this to send me authority info
	 {
		//System.out.println("Switch ID: " + SwitchID);
		//check to see if conditions are safe
		WaysideControllerUI.SwitchChartUpdater(SwitchID);
		return 0;
		   //update switch
	 }
	
	   //TM calls this to send me list of tracks that are occupied
		//sends the occupied tracks to the CTC for further information
		//puts the data on the UI
	   public static void WaysideController_TrackOccupancy(int[] IncomingTrackOccupancyArray)  //TM calls this to send me occupancy info
	   {
		   //WaysideController TrackUpdate = new WaysideController();
		   WaysideController.UpdateOccupiedTracks(IncomingTrackOccupancyArray);
		   //send the CTC HERE-------------------------------

		   
		   DBHelper DB = new DBHelper();
		   int ArrayLength = IncomingTrackOccupancyArray.length;
		   Object[][] multi = new Object[ArrayLength][4];
		   for (int x = 0; x < IncomingTrackOccupancyArray.length; x++)
		   {

			   String IncomingNumber = Integer.toString(IncomingTrackOccupancyArray[x]);
			   String LineColor = null;
			   
			   int firstDigit = Character.getNumericValue(IncomingNumber.charAt(0));
			   String BlockNumber =  IncomingNumber.substring(1,4);
			   WaysideFunctionsHub track = new WaysideFunctionsHub();

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
	   
	   public static void WaysideController_BrokenTrack(int[] IncomingBrokenTrackArray)  //TM calls this to send me broken track info
	   {
		   
		   BrokenTrackArray = IncomingBrokenTrackArray;
			WaysideFunctions.CTC_getBrokenTrack(BrokenTrackArray);
			//Send teh ctc HERE ----------------------------------------------

	   }
   

}