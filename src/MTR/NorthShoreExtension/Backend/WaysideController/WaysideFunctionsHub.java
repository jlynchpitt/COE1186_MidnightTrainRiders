/*
 * Filename: WaysideFunctionsHub.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.Backend.WaysideController;

<<<<<<< HEAD
public class WaysideFunctionsHub 
=======
import MTR.NorthShoreExtension.MainMTR;
import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.UI.TrackModelUI;
import MTR.NorthShoreExtension.UI.WaysideControllerUI;

public class WaysideFunctionsHub //the purpose of this class is to receive and organize the information
>>>>>>> parent of 3fc3e00... Basic Logic unit up
{
	public static int[] AuthorityArray;
	public static int[] SpeedArray;
	public static int[] TrackOccupancyArray;
	public static int[] BrokenTrackArray;
	public static int[] AuthorArray = {2130,2131,2141,2145,2165,2146,2147};
	public static WaysideFunctions obj = new WaysideFunctions();
	
	
	public static void main(String[] args) 
	{
		System.out.println("placeholder");
		// TODO Auto-generated method stub
		WaysideFunctions.CTC_getOccupancy(AuthorArray);
		WaysideFunctions.CTC_getBrokenTrack(AuthorArray);
		WaysideFunctions.TrackModel_setSpeed(AuthorArray);
		WaysideFunctions.TrackModel_setAuthority(AuthorArray);
<<<<<<< HEAD
		
		/*
		AuthorityArray = obj.WaysideController_Authority();
		  
		  SpeedArray = obj.WaysideController_Speed();
		  TrackOccupancyArray = obj.WaysideController_TrackOccupancy();
		  BrokenTrackArray = obj.WaysideController_BrokenTrack();
		  */

	}
	public static void WaysideController_Authority(int[] IncomingAuthorityArray)   //CTC calls this to send me authority info
	 {
		   
				AuthorityArray = IncomingAuthorityArray;
				WaysideFunctions.TrackModel_setAuthority(AuthorityArray);
				for (int x = 0; x < AuthorityArray.length; x++)
				{
					System.out.println("WayAuthority: " + AuthorityArray[x]);
				}
		   
	 }
=======
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
>>>>>>> parent of 3fc3e00... Basic Logic unit up
	public static int WaysideController_Switch(int SwitchID)   //CTC calls this to send me authority info
	 {
		System.out.println("Switch ID: " + SwitchID);
		return 0;
		   //update switch
	 }
<<<<<<< HEAD
	   public static void WaysideController_Speed(int[] IncomingSpeedArray) //CTC calls this to send me speed info
	   {
=======
	
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
>>>>>>> parent of 3fc3e00... Basic Logic unit up

				SpeedArray = IncomingSpeedArray;
				WaysideFunctions.TrackModel_setSpeed(SpeedArray);
				for (int x = 0; x < SpeedArray.length; x++)
				{
					System.out.println("WayAuthority: " + SpeedArray[x]);
				}
	   }
	   
	   public static void WaysideController_TrackOccupancy(int[] IncomingTrackOccupancyArray)  //TM calls this to send me occupancy info
	   {
		   TrackOccupancyArray = IncomingTrackOccupancyArray;
		   WaysideFunctions.CTC_getOccupancy(TrackOccupancyArray);
			for (int x = 0; x < TrackOccupancyArray.length; x++)
			{
				System.out.println("WayOccupied: " + TrackOccupancyArray[x]);
			}
	   }
	   
	   public static void WaysideController_BrokenTrack(int[] IncomingBrokenTrackArray)  //TM calls this to send me broken track info
	   {
		   
		   BrokenTrackArray = IncomingBrokenTrackArray;
<<<<<<< HEAD
			//WaysideFunctions.CTC_getBrokenTrack(BrokenTrackArray);
=======
			WaysideFunctions.CTC_getBrokenTrack(BrokenTrackArray);
			/*
>>>>>>> parent of 3fc3e00... Basic Logic unit up
		   for (int x = 0; x < BrokenTrackArray.length; x++)
			{
				System.out.println("WayBroken: " + BrokenTrackArray[x]);
			}
	   }
   

}
