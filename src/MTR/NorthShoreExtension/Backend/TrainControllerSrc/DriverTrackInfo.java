package MTR.NorthShoreExtension.Backend.TrainControllerSrc;

public class DriverTrackInfo {
	public String line = "Green";
	public boolean isStation = false;
	public boolean isSwitch = false;
	public boolean isUnderground = false;
	
	//Current track
	public int trackID = 0;
	public int speedLimit = 0; //km/h
	public int length = 0;
	
	//Next track - track with lowest speed limit
	public int nextTrackID = 0;
	public int nextSpeedLimit = 0;
	public int nextLength = 0;
	
	public DriverTrackInfo() {
		//Nothing needed in constructor
	}
}
