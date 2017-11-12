/*
 * Filename: WaysideFunctionsHub.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.Backend.WaysideControllerSrc;

public class WaysideFunctionsHub 
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
	public static int WaysideController_Switch(int SwitchID)   //CTC calls this to send me authority info
	 {
		System.out.println("Switch ID: " + SwitchID);
		return 1;
		   //update switch
	 }
	   public static void WaysideController_Speed(int[] IncomingSpeedArray) //CTC calls this to send me speed info
	   {

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
			//WaysideFunctions.CTC_getBrokenTrack(BrokenTrackArray);
		   for (int x = 0; x < BrokenTrackArray.length; x++)
			{
				System.out.println("WayBroken: " + BrokenTrackArray[x]);
			}
	   }
   

}
