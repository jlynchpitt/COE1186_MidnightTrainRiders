/*
 * Filename: DBHelper.java
 * Author: Joe Lynch
 * Date Created: 10/26/2017
 * File Description: This is the class that handles all interactions
 * 			with the SQLite database. This includes creating the 
 * 			database as well as functions to load/update new data
 * 			and query data from the database.
 */

package MTR.NorthShoreExtension.Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
	int[] returnArray = new int[8]; 
	String[] returnString = new String[15];
	//Table names
	private static final String DBName = "jdbc:sqlite:MTRDatabase.db";
	
	private static final String TRACK_INFO_TABLENAME = "TrackInfo";
	private static final String TRACK_INFO_COLUMNS = "rowID INTEGER, trackID INTEGER, line STRING, section STRING, number INTEGER,"
			+ "length INTEGER, grade REAL, speedLimit INTEGER, infrastructure STRING, elevation REAL, "
			+ "cumElevation REAL, startX INTEGER, startY INTEGER, endX INTEGER, endY INTEGER, curveStart INTEGER, "
			+ "curveEnd INTEGER, trackStatus STRING, heater STRING, speed INTEGER, authority INTEGER, occupied INTEGER,"
			+ "nextTrack INTEGER, prevTrack INTEGER, secondSwitch INTEGER, switchPosition INTEGER";
	
	private static final String INCIDENT_TABLENAME = "Incidents";
	private static final String INCIDENT_COLUMNS = "errorType STRING, timeBroken INTEGER, timeFixed INTEGER, trackID INTEGER"
			+ "trainID INTEGER";
	
	private static final String PASSENGER_TABLENAME = "Passengers";
	private static final String PASSENGER_COLUMNS = "trainID INTEGER, station STRING, scheduleArrivalTime INTEGER, "
			+ "actualArrivalTime INTEGER, numPassengersOn INTEGER, numPassengersOff INTEGER";
		
	public DBHelper() {
		// load the sqlite-JDBC driver using the current class loader
		Connection connection = null;
		Statement statement = null;
		try
		{
			Class.forName("org.sqlite.JDBC");

		    // create a database connection
		    connection = connect();
	
		    statement = connection.createStatement();
		    statement.setQueryTimeout(30);  // set timeout to 30 sec. Keep this???
	
		    statement.executeUpdate("DROP TABLE IF EXISTS " + TRACK_INFO_TABLENAME);
		    statement.executeUpdate("CREATE TABLE " + TRACK_INFO_TABLENAME + " (" + TRACK_INFO_COLUMNS + ")");	
		    
		    statement.executeUpdate("DROP TABLE IF EXISTS " + INCIDENT_TABLENAME);
		    statement.executeUpdate("CREATE TABLE " + INCIDENT_TABLENAME + " (" + INCIDENT_COLUMNS + ")");	
		    
		    statement.executeUpdate("DROP TABLE IF EXISTS " + PASSENGER_TABLENAME);
		    statement.executeUpdate("CREATE TABLE " + PASSENGER_TABLENAME + " (" + PASSENGER_COLUMNS + ")");	
		 }
		 catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		 catch(ClassNotFoundException e) {
			 System.err.println(e.getMessage());
		 }
		finally {         
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
	}
	
	public void addTrack(int rowID, int trackID, String line, String section, int blockNum, int blockLength, double blockGrade, 
			int speedLimit, String infrastructure, double elevation, double cumulativeElevation, int startX, int startY, 
			int endX, int endY, int curveStart, int curveEnd, String status, String heater, int speed, int authority,int occupied,
			int nextTrack, int prevTrack, int secondSwitch, int switchPosition) throws SQLException {
		Connection connection = connect();
		
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30); //TODO: Is this needed?
		
	    statement.executeUpdate("INSERT INTO " + TRACK_INFO_TABLENAME + " values(' "+rowID+"' , ' "+trackID+"', '"+line+"', '"+section+"', '"
	    +blockNum+"', '"+blockLength+"', '"+blockGrade+"', '"+speedLimit+"', '"+infrastructure+"', '"+elevation+"', '"
	    		+cumulativeElevation+"', '"+startX+"', '"+startY+"', '"+endX+"', '"+endY+"', '"+curveStart+"', '"+curveEnd+
	    		"', '"+status+"', '"+heater+"', '"+speed+"', '"+authority+"', '"+occupied+"', '"+nextTrack+"','"+prevTrack+
	    		"','"+secondSwitch+"','"+switchPosition+"')");   
	    
	    connection.close();
	    //default trainStatus = ""
	    //default speed, authority, occupied = 0
	    //default switchPosition = 0
	}
	public int findCoordinates(int x, int y) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		int trackid = 0;
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		int store = 0;
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			resultSet = statement.executeQuery("SELECT * from TrackInfo");  
			 while(resultSet.next())
		        {
		           startX = resultSet.getInt("startX");
		           startY = resultSet.getInt("startY");
		           endX = resultSet.getInt("endX");
		           endY = resultSet.getInt("endY");
		           if(startX > endX) {
		        	   store = startX;
		        	   startX = endX;
		        	   endX = store;
		           }
		           
		           if(startY > endY) {
		        	   store = startY;
		        	   startY = endY;
		        	   endY = store;
		           }
		           
		        		   if((x >= (startX-10) && x <= (endX+10)) && (y >= (startY-10) && y <= (endY+10))) {
			        	   		trackid = resultSet.getInt("trackID");
			        	   }

		        }
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		return trackid;
	}
	
	public void showTrackTest() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			 resultSet = statement.executeQuery("SELECT * from TrackInfo");  
			 while(resultSet.next())
		        {
		           // iterate & read the result set
		           System.out.println("rowID = " + resultSet.getString("rowID"));
		           System.out.println("trackID = " + resultSet.getInt("trackID"));
		           System.out.println("line = " + resultSet.getString("line"));
		           System.out.println("section = " + resultSet.getInt("section"));
		           System.out.println("number = " + resultSet.getInt("number"));
		           System.out.println("length = " + resultSet.getInt("length"));
		           System.out.println("grade = " + resultSet.getInt("grade"));
		           System.out.println("speedLimit = " + resultSet.getInt("speedLimit"));
		           System.out.println("infrastructure = " + resultSet.getInt("infrastructure"));
		           System.out.println("switchPosition = " + resultSet.getInt("switchPosition"));
		           System.out.println("elevation = " + resultSet.getInt("elevation"));
		           System.out.println("cumElevation = " + resultSet.getInt("cumElevation"));
		           System.out.println("startX = " + resultSet.getInt("startX"));
		           System.out.println("startY = " + resultSet.getInt("startY"));
		           System.out.println("endX = " + resultSet.getInt("endX"));
		           System.out.println("endY = " + resultSet.getInt("endY"));
		           System.out.println("curveStart = " + resultSet.getInt("curveStart"));
		           System.out.println("curveEnd = " + resultSet.getInt("curveEnd"));
		           System.out.println("trackStatus = " + resultSet.getInt("trackStatus"));
		           System.out.println("heater = " + resultSet.getInt("heater"));
		           System.out.println("speed = " + resultSet.getInt("speed"));
		           System.out.println("authority = " + resultSet.getInt("authority"));
		           System.out.println("occupied = " + resultSet.getInt("occupied"));
		           System.out.println("nextTrack = " + resultSet.getInt("nextTrack"));
		        }
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
	}
	
	public void updateTrackStatus(int trackid, String status) {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("UPDATE TrackInfo SET trackStatus = '"+status+"' WHERE trackID = '"+trackid+"'");   

		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
	}

	public void updateHeater(int trackid, String heaterStatus) {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("UPDATE TrackInfo SET heater = '"+heaterStatus+"' WHERE trackID = '"+trackid+"'");   

		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
	}

	public void updateSpeedAuthority(int trackid, int speed, int authority) {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("UPDATE TrackInfo SET speed = '"+speed+"', authority = '"+authority+"' WHERE trackID = '"+trackid+"'");   

		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
	}

	public void updateTrackOccupied(int trackid, int isOccupied) {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("UPDATE TrackInfo SET occupied = '"+isOccupied+"' WHERE trackID = '"+trackid+"'");   

		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
	}
	
	public int getTrackOccupied(int trackid) {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		int status = 0;
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			result = statement.executeQuery("SELECT * from  TrackInfo WHERE trackID = '" + trackid + "'");
			status = result.getInt("occupied");  

		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		return status;
	}
	
	//added a method for getting information on track status (for Repair Scheduling) - Matt
	public String getTrackStatus(int trackid) {
		String status = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			result = statement.executeQuery("SELECT * from  TrackInfo WHERE trackID = '" + trackid + "'");
			status = result.getString("trackStatus");
		} catch(SQLException e) {
			System.err.println(e);
		}
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		
		return status;
	}
	
	//added a method for retrieving the speed limit for each track section
	public int getSpeedLimit(int trackid) {
		int limit = 100;
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = connect();
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			result = statement.executeQuery("SELECT * from TrackInfo WHERE trackID = '" + trackid + "'");
			limit = Integer.parseInt(result.getString("speedLimit"));
		} catch(SQLException e) {
			System.err.println(e);
		}
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		return limit;
	}
	
	public String getInfrastructure(int trackid) {
		String type = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    result = statement.executeQuery("SELECT * from TrackInfo WHERE trackID = '"+trackid+"'");   
		    type = result.getString("infrastructure");
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		if(type == null) {
    		type = "none";
    	}
		return type;
	}
	//"Line/Status","Speed/Authority","Section","Block","Length (m)", 
	//"Grade(%)", "Speed Limit (km/hr)","Infrastructre", "Elevation(m)",
	//"Cumlative Elevation", "Track Status", "Heater"
	public String[] getDisplayInfo(int trackid) {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    result = statement.executeQuery("SELECT * from TrackInfo WHERE trackID = '"+trackid+"'"); 
		    if(result.isBeforeFirst()) {
		    	//System.out.println("found track, checking");
			    	returnString[0] = result.getString("line");
			    	returnString[1] = result.getString("occupied");
			    	returnString[2] = result.getString("speed");
			    	returnString[3] = result.getString("authority");
			    	returnString[4] = result.getString("section");
			    	returnString[5] = result.getString("number");
			    	returnString[6] = result.getString("length");
			    	returnString[7] = result.getString("grade");
			    	returnString[8] = result.getString("speedLimit");
			    	returnString[9] = result.getString("infrastructure");
			    	returnString[10] = result.getString("elevation");
			    	returnString[11] = result.getString("cumElevation");
			    	returnString[12] = result.getString("trackStatus");
			    	returnString[13] = result.getString("heater");
		    }
		    else if(!result.isBeforeFirst()){
			    	returnString[0] = " ";
			    	returnString[1] = " ";
			    	returnString[2] = " ";
			    	returnString[3] = " ";
			    	returnString[4] = " ";
			    	returnString[5] = " ";
			    	returnString[6] = " ";
			    	returnString[7] = " ";
			    	returnString[8] = " ";
			    	returnString[9] = " ";
			    	returnString[10] = " ";
			    	returnString[11] = " ";
			    	returnString[12] = " ";
			    	returnString[13] = " ";
		    }
		  
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		return returnString;
	}
	
	public int getTrackLength(int trackid) {
		int length = 0;
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    result = statement.executeQuery("SELECT * from TrackInfo WHERE trackID = '"+trackid+"'");   
		    	length = result.getInt("length");
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		return length;
	}
	
	public int getDatabaseSize() {
		int size = 0;
		Connection connection = null;
		Statement statment = null;
		ResultSet result = null;
		
		try {
			connection = connect();
			
			statment = connection.createStatement();
			statment.setQueryTimeout(30);
			
			result = statment.executeQuery("Select * from TrackInfo");
			
			while (result.next()) {
				size = Integer.parseInt(result.getString("rowID"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statment != null) try { statment.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		return size;
	}
	
	public int schedNextTrack(int trackid, int prevTrack) {
		int nextTrack = 0;
		Connection connection = null;
		Statement statement = null;
		ResultSet track = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?

			track = statement.executeQuery("SELECT * from TrackInfo WHERE trackID = '"+trackid+"'");
			if(prevTrack < trackid) {
				if(track.getInt("switchPosition")==0) {
					nextTrack = track.getInt("nextTrack");
					if(nextTrack == prevTrack) {
						nextTrack = track.getInt("secondSwitch");
					}
				}
				else {
					nextTrack = track.getInt("secondSwitch");
					if(nextTrack == prevTrack) {
						nextTrack = track.getInt("nextTrack");
					}
				}
			}
			else if(prevTrack > trackid) {
				if(track.getInt("switchPosition")==0) {
					nextTrack = track.getInt("prevTrack");
					if(nextTrack == prevTrack) {
						nextTrack = track.getInt("secondSwitch");
					}
				}
				else {
					nextTrack = track.getInt("secondSwitch");
					if(nextTrack == prevTrack) {
						nextTrack = track.getInt("prevTrack");
					}
				}
			}

		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (track != null) try { track.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }

		return nextTrack;
	}
	
		//prints the alternate track of switches
	public int getAltTrack(int trackid){
		int nextTrack = 0;
		Connection connection = null;
		Statement statement = null;
		ResultSet track = null;
		//make connection
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			//access data table
			track = statement.executeQuery("SELECT * from TrackInfo WHERE trackID = '"+trackid+"'");
				//if switch is straight
				if(track.getInt("switchPosition")==0) 
				{
					//return alternate track
						nextTrack = track.getInt("secondSwitch");
					
				}
				else  //otherwise switch is angled 
				{
					//return straight track
					nextTrack = track.getInt("nextTrack");
					
				}
			

		}
		//error catcher
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (track != null) try { track.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }

		return nextTrack;
	}
	
	public int getNextTrack(int trackid, int prevTrack){
		int nextTrack = 0;
		Connection connection = null;
		Statement statement = null;
		ResultSet track = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?

			track = statement.executeQuery("SELECT * from TrackInfo WHERE trackID = '"+trackid+"'");
			if(prevTrack < trackid) {
				if(track.getInt("switchPosition")==0) {
					nextTrack = track.getInt("nextTrack");
					if(nextTrack == prevTrack) {
						nextTrack = track.getInt("secondSwitch");
					}
				}
				else {
					nextTrack = track.getInt("secondSwitch");
					if(nextTrack == prevTrack) {
						nextTrack = track.getInt("nextTrack");
					}
				}
			}
			else if(prevTrack > trackid) {
				if(track.getInt("switchPosition")==0) {
					nextTrack = track.getInt("prevTrack");
					if(nextTrack == prevTrack) {
						nextTrack = track.getInt("secondSwitch");
					}
				}
				else {
					nextTrack = track.getInt("secondSwitch");
					if(nextTrack == prevTrack) {
						nextTrack = track.getInt("prevTrack");
					}
				}
			}

		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (track != null) try { track.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }

		return nextTrack;
	}
	
	public void setSwitch(int trackid, int position) {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("UPDATE TrackInfo SET switchPosition = '"+position+"' WHERE trackID = '"+trackid+"'");   

		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
	}
	
	public int getSwitch(int trackid) {
		Connection connection = null;
		Statement statement = null;
		ResultSet track = null;
		int position = 0;
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			track = statement.executeQuery("SELECT * from TrackInfo WHERE trackID = '"+trackid+"'"); 
		    position = track.getInt("switchPosition");

		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (track != null) try { track.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		return position;
	}
	
	public int getTrackID(int rowid) {
		Connection connection = null;
		Statement statement = null;
		ResultSet track = null;
		int id = 0;
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			track = statement.executeQuery("SELECT * from TrackInfo WHERE rowID = '"+rowid+"'"); 
		    id = track.getInt("trackID");

		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (track != null) try { track.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		return id;
	}
	
	public String getColor(int rowID) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String color = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    resultSet = statement.executeQuery("SELECT * from TrackInfo where rowID = '"+rowID+"'");   
		    color = resultSet.getString("line");
		    //System.out.println("color = " + color);
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		return color;
	}
	
	public int[] getDrawingCoordinates(int rowID) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			resultSet = statement.executeQuery("SELECT * from TrackInfo where rowID = '"+rowID+"'");   
			returnArray[0] = resultSet.getInt("startX");
			returnArray[1] = resultSet.getInt("startY");
		    returnArray[2] = resultSet.getInt("endX");
		    returnArray[3] = resultSet.getInt("endY");
		    returnArray[4] = resultSet.getInt("curveStart");
		    returnArray[5] = resultSet.getInt("curveEnd");
		    returnArray[6] = resultSet.getInt("trackID");
		    returnArray[7] = resultSet.getInt("occupied");
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		finally {         
			if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		return returnArray;
	}
	
	
	/**
    * Connect to the database
    * @return the Connection object
    */
   private Connection connect() {
       // SQLite connection string
       Connection conn = null;
       try {
           conn = DriverManager.getConnection(DBName);
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
       return conn;
   }

	/* Example executing query  
    ResultSet resultSet = statement.executeQuery("SELECT * from person");
    while(resultSet.next())
    {
       // iterate & read the result set
       System.out.println("name = " + resultSet.getString("name"));
       System.out.println("id = " + resultSet.getInt("id"));
    }
   }*/
}
