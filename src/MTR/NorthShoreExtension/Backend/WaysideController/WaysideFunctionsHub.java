/*
 * Filename: WaysideFunctionsHub.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.UI;

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
		obj.CTC_getOccupancy(AuthorArray);
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
		   
	 }
	   
	   public static void WaysideController_Speed(int[] IncomingSpeedArray) //CTC calls this to send me speed info
	   {

				SpeedArray = IncomingSpeedArray;
		   
	   }
	   
	   public static void WaysideController_TrackOccupancy(int[] IncomingTrackOccupancyArray)  //TM calls this to send me occupancy info
	   {
		   TrackOccupancyArray = IncomingTrackOccupancyArray;
	   }
	   
	   public static void WaysideController_BrokenTrack(int[] IncomingBrokenTrackArray)  //TM calls this to send me broken track info
	   {
		   BrokenTrackArray = IncomingBrokenTrackArray;
	   }
   

}
