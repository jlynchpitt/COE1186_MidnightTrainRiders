/*
 * Filename: WaysideFunctionsHub.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.Backend.WaysideController;

public class WaysideFunctionsHub 
{
	public static int[] AuthorityArray;
	public static int[] SpeedArray;
	public static int[] TrackOccupancyArray;
	public static int[] BrokenTrackArray;
	public static WaysideFunctions obj = new WaysideFunctions();
	
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		AuthorityArray = obj.WaysideController_Authority();
		  
		  SpeedArray = obj.WaysideController_Speed();
		  TrackOccupancyArray = obj.WaysideController_TrackOccupancy();
		  BrokenTrackArray = obj.WaysideController_BrokenTrack();

	}
   public static int[] CTC_getOccupancy()
   {
	   //int[] OccupancyArray = new int[4];  
	   return TrackOccupancyArray;
   }
   
   public static int[] CTC_getBrokenTrack()
   {
	   //int[] BrokenTrackArray = new int[4];	   
	   return BrokenTrackArray;
   }
   
   public static int[] TrackModel_setSpeed()
   {
	   //int[] SpeedArray = new int[4];
	   return SpeedArray;
   }
   
	public static int[] TrackModel_setAuthority()
   {
	   //int[] AuthorityArray = new int[4];    
	   return AuthorityArray;
   }
   

}
