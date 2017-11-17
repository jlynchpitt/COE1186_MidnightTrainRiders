/*
 * Filename: WaysideFunctionsHub.java
 * Author: Eric Cheung
 * Date Created:10/27
 * File Description: Creates basic wayside UI and basic functionalities
 */
package MTR.NorthShoreExtension.Backend.WaysideController;

import MTR.NorthShoreExtension.Backend.DBHelper;
import MTR.NorthShoreExtension.UI.WaysideControllerUI;

public class WaysideFunctionsHub 
{
	public static int[] AuthorityArray;
	public static int[] SpeedArray;
	public static int[] TrackOccupancyArray;
	public static int[] BrokenTrackArray;
	public static int[] AuthorArray = {2130,2131,2141,2145,2165,2146,2147};
	public static WaysideFunctions obj = new WaysideFunctions();
	public static WaysideControllerUI obj2 = new WaysideControllerUI();
	
	
	public static void main(String[] args) 
	{

		// TODO Auto-generated method stub
		WaysideFunctions.CTC_getOccupancy(AuthorArray);
		WaysideFunctions.CTC_getBrokenTrack(AuthorArray);
		WaysideFunctions.TrackModel_setSpeedAuthority(2002, 65, AuthorArray);
		WaysideFunctions.TrackModel_setAuthority(AuthorArray);
		


	}
	public static void TestFunction()
	{
		
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
	public static void OccupiedSpeedAuthority(int TrackID, int Speed, int[] Authority) // CTC calls this to send me info
	{
		
	}
	public static int WaysideController_Switch(int SwitchID)   //CTC calls this to send me authority info
	 {
		//System.out.println("Switch ID: " + SwitchID);
		//check to see if conditions are safe
		WaysideControllerUI.SwitchChartUpdater(SwitchID);
		return 0;
		   //update switch
	 }
	
	   
	   public static void WaysideController_TrackOccupancy(int[] IncomingTrackOccupancyArray)  //TM calls this to send me occupancy info
	   {
		   WaysideFunctions.CTC_getOccupancy(IncomingTrackOccupancyArray);
		   //WaysideFunctions.CTC_getOccupancy(IncomingTrackOccupancyArray);
		   DBHelper DB = new DBHelper();
		   int ArrayLength = IncomingTrackOccupancyArray.length;
		   Object[][] multi = new Object[ArrayLength][4];
		   for (int x = 0; x < IncomingTrackOccupancyArray.length; x++)
		   {
			   //System.out.print(IncomingTrackOccupancyArray[x] + " ");
			   String IncomingNumber = Integer.toString(IncomingTrackOccupancyArray[x]);
			   String LineColor = null;
			   
			   int firstDigit = Character.getNumericValue(IncomingNumber.charAt(0));
			   String BlockNumber =  IncomingNumber.substring(1,4);
			   WaysideFunctionsHub track = new WaysideFunctionsHub();
			   //LineColor = DB.getColor(IncomingTrackOccupancyArray[x]);
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
		   
		   }
		   //System.out.println("");

		   WaysideControllerUI.OccupiedTrackTableUpdater(multi);
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
