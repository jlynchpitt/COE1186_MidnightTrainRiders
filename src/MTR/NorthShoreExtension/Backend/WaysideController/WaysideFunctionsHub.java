/*
 * Filename: WaysideFunctionsHub.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.Backend.WaysideController;

import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;
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

		// TODO Auto-generated method stub
		/*
		WaysideFunctions.CTC_getOccupancy(AuthorArray);
		WaysideFunctions.CTC_getBrokenTrack(AuthorArray);
		WaysideFunctions.TrackModel_setSpeedAuthority(2002, 65, AuthorArray);
		WaysideFunctions.TrackModel_setAuthority(AuthorArray);
		*/
		//DBHelper DB = new DBHelper();
		//System.out.println("AAAAAAAAAAAAAAAHHHHHHHHHHHHHH: " + load.getTrackLength(1026));


	}


	public static void OccupiedSpeedAuthority(int TrackID, int NextTrack, int Speed, int[] Authority) // CTC calls this to send me info
	{
		/*
		for (int x = 0; x < Authority.length; x++)
		   {
			   System.out.println(TrackID + " OccupiedSpeedAuthority: " + Authority[x]);
		   }
		   */
		WaysideFunctions.TrackModel_setSpeedAuthority(TrackID, Speed, Authority.length);
		int TotalLength = 0;
		for (int x = 0; x < Authority.length; x++)
		{
			TotalLength += 50;
			//TotalLength += load.getTrackLength(Authority[x]);
			//System.out.println(Authority[x]);
		}
		//System.out.println(TrackID + " =SPEED: " + Speed);
		WaysideControllerUI.OccupiedTrackAuthoritySpeedUpdater(TrackID, NextTrack, Speed, TotalLength);
		
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
		//input: array of ocupied tracks
	//output: 
	   public static void WaysideController_TrackOccupancy(int[] IncomingTrackOccupancyArray)  //TM calls this to send me occupancy info
	   {
		   /*
		   for (int x = 0; x < IncomingTrackOccupancyArray.length; x++)
		   {
			   System.out.println("WaysideController_TrackOccupancy: " + IncomingTrackOccupancyArray[x]);
		   }
		   */
		   
		   
		   //WaysideFunctions.CTC_getOccupancy(IncomingTrackOccupancyArray);
		   DBHelper DB = new DBHelper();
		   int ArrayLength = IncomingTrackOccupancyArray.length;
		   Object[][] multi = new Object[ArrayLength][4];
		   for (int x = 0; x < IncomingTrackOccupancyArray.length; x++)
		   {
			   //System.out.println("WaysideController_TrackOccupancy: " + IncomingTrackOccupancyArray[x]);
			   //System.out.print(IncomingTrackOccupancyArray[x] + " ");
			   String IncomingNumber = Integer.toString(IncomingTrackOccupancyArray[x]);
			   String LineColor = null;
			   
			   int firstDigit = Character.getNumericValue(IncomingNumber.charAt(0));
			   String BlockNumber =  IncomingNumber.substring(1,4);
			   WaysideFunctionsHub track = new WaysideFunctionsHub();
			   //LineColor = load.getColor(IncomingTrackOccupancyArray[x]);
			   
			   //System.out.println("AAAAAAAAAAAAAAAHHHHHHHHHHHHHH: " + load.getColor(1));
			   
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
				//"Line", "Occupied Track", "Dest Track", "Athrty"
				//System.out.println(LineColor +" =DDDDDD: " + BlockNumber);
		   }
		   //System.out.println("");

		   WaysideControllerUI.OccupiedTrackTableUpdater(multi);
		   WaysideFunctions.CTC_getOccupancy(IncomingTrackOccupancyArray);
	   }
	   
	   public static void WaysideController_BrokenTrack(int[] IncomingBrokenTrackArray)  //TM calls this to send me broken track info
	   {
		   
		   BrokenTrackArray = IncomingBrokenTrackArray;
			WaysideFunctions.CTC_getBrokenTrack(BrokenTrackArray);
			/*
		   for (int x = 0; x < BrokenTrackArray.length; x++)
			{
				System.out.println("WayBroken: " + BrokenTrackArray[x]);
			}
			*/
	   }
   

}
