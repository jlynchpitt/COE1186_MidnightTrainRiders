/*
 * Filename: StaticDBHelper.java
 * Author: Joe Lynch
 * Date Created: 10/26/2017
 * File Description: This is the class that handles all interactions
 * 			with the static track SQLite database used by the train controller. 
 * 			This includes creating the database as well as functions to load/update new data
 * 			and query data from the database.
 * 			Since this database only uses the static track and data that is calculated
 * 			within the train controller it is the equivalent of the track being loaded
 * 			onto the train controller when it is in the yard.
 */

package MTR.NorthShoreExtension.Backend;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MTR.NorthShoreExtension.Backend.TrainControllerSrc.DriverTrackInfo;
import MTR.NorthShoreExtension.Backend.TrainControllerSrc.TrainControllerHelper;

public class StaticTrackDBHelper {
	//Table names
	private static final String DBName = "jdbc:sqlite:MTRStaticTrackDatabase.db";
	
	private static final String TRACK_INFO_TABLENAME = "TrackInfo";
	private static final String TRACK_INFO_COLUMNS = "trackID INTEGER, line STRING, section STRING, blockNum INTEGER, "
			+ "length REAL, grade REAL, speedLimit INTEGER, primaryNext INTEGER, secondaryNext INTEGER, primaryPrev INTEGER, "
			+ "secondaryPrev INTEGER, biDirectional INTEGER, station INTEGER, switch INTEGER, underground INTEGER, firstTrack INTEGER, "
			+ "lastTrack INTEGER, elevation REAL, cumulativeElevation REAL";
	
	//NOTE: All times are integers as # of seconds since 1970
	private static final String TRAIN_CONTROLS_TABLENAME = "TrainControls";
	private static final String TRAIN_CONTROLS_COLUMNS = "trainID INTEGER, time INTEGER, powerCommand REAL, "
			+ "speedCommand INTEGER, actualSpeed REAL";
	
	private static final String STATION_LIST_TABLENAME = "StationList";
	private static final String STATION_LIST_COLUMNS = "trackID INTEGER, stationID INTEGER, stationName STRING, stationSide STRING";
		
	public StaticTrackDBHelper() {
		// load the sqlite-JDBC driver using the current class loader
		Connection connection = null;
		try
		{
			Class.forName("org.sqlite.JDBC");

		    // create a database connection
		    connection = connect();
	
		    Statement statement = connection.createStatement();
		    statement.setQueryTimeout(30);  // set timeout to 30 sec. Keep this???
	
		    statement.executeUpdate("DROP TABLE IF EXISTS " + TRACK_INFO_TABLENAME);
		    statement.executeUpdate("CREATE TABLE " + TRACK_INFO_TABLENAME + " (" + TRACK_INFO_COLUMNS + ")");	
		    
		    statement.executeUpdate("DROP TABLE IF EXISTS " + STATION_LIST_TABLENAME);
		    statement.executeUpdate("CREATE TABLE " + STATION_LIST_TABLENAME + " (" + STATION_LIST_COLUMNS + ")");	
		    
		    statement.executeUpdate("DROP TABLE IF EXISTS " + TRAIN_CONTROLS_TABLENAME);
		    statement.executeUpdate("CREATE TABLE " + TRAIN_CONTROLS_TABLENAME + " (" + TRAIN_CONTROLS_COLUMNS + ")");	
		    
		    //statement.executeUpdate("DROP TABLE IF EXISTS " + INCIDENT_TABLENAME);
		    //statement.executeUpdate("CREATE TABLE " + INCIDENT_TABLENAME + " (" + INCIDENT_COLUMNS + ")");	
		    
		    //statement.executeUpdate("DROP TABLE IF EXISTS " + PASSENGER_TABLENAME);
		    //statement.executeUpdate("CREATE TABLE " + PASSENGER_TABLENAME + " (" + PASSENGER_COLUMNS + ")");	
		 }
		 catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		 catch(ClassNotFoundException e) {
			 System.err.println(e.getMessage());
		 }
		 finally {         
			 try {
	               if(connection != null)
	                  connection.close();
		     }
		     catch(SQLException e) {  // Use SQLException class instead.          
		    	 System.err.println(e); 
		     }
		 }
	}
	
	public void addTrack(int trackID, String line, String section, int blockNum, double blockLength, double blockGrade, 
			int speedLimit, int primaryNext, int secondaryNext, int primaryPrev, int secondaryPrev, int biDirectional,
			int station, int isSwitch, int underground, int firstTrack, int lastTrack, double elevation, double cumulativeElevation) {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			String queryUpdateStatement = "INSERT INTO " + TRACK_INFO_TABLENAME + " values(' "+trackID+"', '"+line+"', '"+section+"', '"
		    +blockNum+"', '"+blockLength+"', '"+blockGrade+"', '"+speedLimit+"', '"+primaryNext+"', '"+secondaryNext+"', '"+primaryPrev+"', '"
		    		+secondaryPrev+"', '"+biDirectional+"', '"+station+"', '"+isSwitch+"', '"+underground+"', '"+firstTrack+"', '"+lastTrack
		    		+"', '"+elevation+"', '"+cumulativeElevation+"')";
		    
			statement.executeUpdate(queryUpdateStatement);   
		    
		    connection.close();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		finally {         
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
	    //default trainStatus = ""
	    //default speed, authority, occupied = 0
	    //default switchPosition = 0
	}

	public void loadFileIntoDB(String filename) {
		File file = new File(filename);
		BufferedReader br = null;
        String line;
		try {
			br = new BufferedReader(new FileReader(file));
			br.readLine(); //Read in column headers
			while((line = br.readLine())!=null) {
				String[] trackInfo = line.split(",");
				int trackID = loadCSVInt(trackInfo[0]);
				String lineColor = trackInfo[1]; 
				String section = trackInfo[2];
				int blockNum = loadCSVInt(trackInfo[3]);
				double blockLength = loadCSVDouble(trackInfo[4]);
				double blockGrade = loadCSVDouble(trackInfo[5]);
				int speedLimit = loadCSVInt(trackInfo[6]);
				int primaryNext = loadCSVInt(trackInfo[7]);
				int secondaryNext = loadCSVInt(trackInfo[8]);
				int primaryPrev = loadCSVInt(trackInfo[9]);
				int secondaryPrev = loadCSVInt(trackInfo[10]);
				int biDirectional = loadCSVInt(trackInfo[11]);
				int stationID = loadCSVInt(trackInfo[12]);
				String stationName = trackInfo[13];
				String stationSide = trackInfo[14];
				int isSwitch = loadCSVInt(trackInfo[15]);
				int underground = loadCSVInt(trackInfo[16]);
				int firstTrack = loadCSVInt(trackInfo[17]);
				int lastTrack = loadCSVInt(trackInfo[18]);
				double elevation = loadCSVDouble(trackInfo[19]);
				double cumulativeElevation = loadCSVDouble(trackInfo[20]);
				
				int station = 0;
				if(stationID > 0) {
					station = 1;
					addStation(trackID, stationID, stationName, stationSide);
				}
				
				addTrack(trackID, lineColor, section, blockNum, blockLength, blockGrade, speedLimit, primaryNext, secondaryNext, primaryPrev, secondaryPrev, biDirectional, station, isSwitch, underground, firstTrack, lastTrack, elevation, cumulativeElevation);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private double loadCSVDouble(String d) {
		if(d == null || d.equals(""))
			return 0;
		else
			return Double.parseDouble(d);
	}
	
	private int loadCSVInt(String i) {
		if(i == null || i.equals(""))
			return 0;
		else
			return Integer.parseInt(i);
	}
	
	public void addStation(int trackID, int stationID, String stationName, String stationSide) {
		Connection connection = null;
		Statement statement = null;
		
		try{
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("INSERT INTO " + STATION_LIST_TABLENAME + " values(' "+trackID+"', '"+stationID+"', '"+stationName+"', '"
		    		+stationSide+"')");   
		    
		    connection.close();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		finally {         
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
	}
	
	public int getFirstTrack(String line) {
		String query = "SELECT * FROM " + TRACK_INFO_TABLENAME + " WHERE firstTrack = '1' and line = '" + line + "'";
		int trackID = -1;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    result = statement.executeQuery(query);   
		    trackID = result.getInt("trackID");
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		}       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		
		return trackID;
	}
	
	/*
	 * private static final String TRACK_INFO_COLUMNS = "trackID INTEGER, line STRING, section STRING, blockNum INTEGER, "
			+ "length REAL, grade REAL, speedLimit INTEGER, primaryNext INTEGER, secondaryNext INTEGER, primaryPrev INTEGER, "
			+ "secondaryPrev INTEGER, biDirectional INTEGER, station INTEGER, isSwitch INTEGER, underground INTEGER, 
			firstTrack INTEGER, "
			+ "lastTrack INTEGER, elevation REAL, cumulativeElevation REAL";
	*/
	public DriverTrackInfo getTrackInfo(int trackID) {
		DriverTrackInfo trackInfo = new DriverTrackInfo();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		ResultSet nextResult = null;
		ResultSet prevResult = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			//Get info about current track
			String query = "SELECT * FROM " + TRACK_INFO_TABLENAME + " WHERE trackID = '"+trackID+"'";
		    result = statement.executeQuery(query);   
		    trackInfo.line = result.getString("line");
		    trackInfo.isStation = result.getInt("station") == 1;
		    trackInfo.isSwitch = result.getInt("switch") == 1;
		    trackInfo.isUnderground = result.getInt("underground") == 1;
		    
		    trackInfo.trackID = trackID;
		    trackInfo.speedLimit = result.getInt("speedLimit");
		    trackInfo.length = result.getInt("length");

		    //System.out.println("Got first track info");
		    
		    //Get safest secondary track (minimum speed Limit) in both directions
		    int primaryNextTrack = result.getInt("primaryNext");
		    int secondaryNextTrack = result.getInt("secondaryNext");
	    	int primaryPrevTrack = result.getInt("primaryPrev");
	    	int secondaryPrevTrack = result.getInt("secondaryPrev");		    
		    
	    	/* Get safest next track */
		    String nextQuery = "";
		    if(secondaryNextTrack > 0) {
		    	nextQuery = "SELECT *, min(speedLimit) as minSpeedLimit FROM "
		    			+ "(SELECT * FROM " + TRACK_INFO_TABLENAME + " WHERE trackID = '"+ primaryNextTrack +"' or trackID = '"+ secondaryNextTrack +"')";
		    }
		    else {
		    	nextQuery = "SELECT * FROM " + TRACK_INFO_TABLENAME + " WHERE trackID = '"+primaryNextTrack+"'";
		    }
		    		    		    
		    nextResult = statement.executeQuery(nextQuery);   

		    trackInfo.nextTrackID = nextResult.getInt("trackID");
		    trackInfo.nextSpeedLimit = nextResult.getInt("speedLimit");
		    trackInfo.nextLength = nextResult.getInt("length");
		    
		    /* Get safest previous track
		     * 		NOTE: Only execute the query if primaryPrevTrack valid
		     */
		    if(primaryPrevTrack > 0) {
			    String prevQuery = "";
			    if(secondaryPrevTrack > 0) {
			    	prevQuery = "SELECT *, min(speedLimit) as minSpeedLimit FROM "
			    			+ "(SELECT * FROM " + TRACK_INFO_TABLENAME + " WHERE trackID = '"+ primaryPrevTrack +"' or trackID = '"+ secondaryPrevTrack +"')";
			    }
			    else {
			    	prevQuery = "SELECT * FROM " + TRACK_INFO_TABLENAME + " WHERE trackID = '"+primaryPrevTrack+"'";
			    }
			    
			    prevResult = statement.executeQuery(prevQuery);   
	
			    trackInfo.prevTrackID = prevResult.getInt("trackID");
			    trackInfo.prevSpeedLimit = prevResult.getInt("speedLimit");
			    trackInfo.prevLength = prevResult.getInt("length");
		    }		    	
		}
		catch(SQLException e){  
			 System.err.println("getTrackInfo: " + e.getMessage()); 
		}       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (nextResult != null) try { nextResult.close(); } catch (SQLException ignore) {}
	        if (prevResult != null) try { prevResult.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }		
		
		return trackInfo;
	}
	
	public int getTrackLength(int trackid) {
		int length = 0;
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    ResultSet result = statement.executeQuery("SELECT * from TrackInfo WHERE trackID = '"+trackid+"'");   
		    	length = result.getInt("length");
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		 }       
		 finally {         
			 try {
	               if(connection != null)
	                  connection.close();
		     }
		     catch(SQLException e) {  // Use SQLException class instead.          
		    	 System.err.println(e); 
		     }
		 }
		return length;
	}
	
	public void addTrainStateRecord(int trainID, long time, double powerCmd, int speedCmd, double actualSpeed) {
		//Adjust time to be number of seconds since the program started
		time = (time - TrainControllerHelper.programStartTime)/1000;
		
		//Convert units of speed from km/h to MPH
		speedCmd = (int) UnitConverter.kilometersToMiles(speedCmd);
		actualSpeed = (int) UnitConverter.kilometersToMiles(actualSpeed);
		//System.out.println("Time: " + time);
		Connection connection = null;
		String query = "";
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			query = "INSERT INTO " + TRAIN_CONTROLS_TABLENAME + " values('"+trainID+"', '"+time+"', '"
		    		+powerCmd+"', '"+speedCmd+"', '"+actualSpeed+"')";
		    statement.executeUpdate(query);   
		    
		}
		catch(SQLException e){  
			System.out.println(query);
			 System.err.println(e.getMessage()); 
		 }       
		 finally {         
			 try {
	               if(connection != null)
	                  connection.close();
		     }
		     catch(SQLException e) {  // Use SQLException class instead.          
		    	 System.err.println(e); 
		     }
		 }
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
   

   public Map<Long, Double> getPowerList(int trainID){
       //List<Double> powerList = new ArrayList<>();
       Map<Long,Double> powerMap=new HashMap<Long,Double>();  

	   Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			String query = "SELECT powerCommand, time FROM " + TRAIN_CONTROLS_TABLENAME + " WHERE trainID = '" +trainID+ "'";
		    result = statement.executeQuery(query);  
		    
		    while(result.next())
		    {
		       // iterate & read the result set
		    	//powerList.add(result.getDouble("powerCommand"));
		    	powerMap.put(result.getLong("time"), result.getDouble("powerCommand"));
		    }
		    
		    return powerMap;
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		}       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		
		return null;
   }
   
   public Map<Long, Double> getSetSpeedList(int trainID){
       //List<Double> powerList = new ArrayList<>();
       Map<Long,Double> speedMap=new HashMap<Long,Double>();  

	   Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			String query = "SELECT speedCommand, time FROM " + TRAIN_CONTROLS_TABLENAME + " WHERE trainID = '" +trainID+ "'";
		    result = statement.executeQuery(query);  
		    
		    while(result.next())
		    {
		       // iterate & read the result set
		    	//powerList.add(result.getDouble("speedCommand"));
		    	speedMap.put(result.getLong("time"), result.getDouble("speedCommand"));
		    }
		    
		    return speedMap;
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		}       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		
		return null;
   }
   
   public Map<Long, Double> getActualSpeedList(int trainID){
       //List<Double> powerList = new ArrayList<>();
       Map<Long,Double> speedMap=new HashMap<Long,Double>();  

	   Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			String query = "SELECT actualSpeed, time FROM " + TRAIN_CONTROLS_TABLENAME + " WHERE trainID = '" +trainID+ "'";
		    result = statement.executeQuery(query);  
		    
		    while(result.next())
		    {
		       // iterate & read the result set
		    	//powerList.add(result.getDouble("actualSpeed"));
		    	speedMap.put(result.getLong("time"), result.getDouble("actualSpeed"));
		    }
		    
		    return speedMap;
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		}       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		
		return null;
   }
   
   public List<Integer> getTrainIDList(){
       List<Integer> trainIDList = new ArrayList<>();

       Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			String query = "SELECT DISTINCT trainID from " + TRAIN_CONTROLS_TABLENAME + " ORDER BY trainID";

		    result = statement.executeQuery(query);  
		    
		    while(result.next())
		    {
		       // iterate & read the result set
		    	trainIDList.add(result.getInt("trainID"));
		    }
		    
		    return trainIDList;
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		}       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		
		return null;
   }
   /* Sample query
   private ResultSet executeQuery(String query) {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		
		try {
			connection = connect();
			
			statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    result = statement.executeQuery(query);   
		}
		catch(SQLException e){  
			 System.err.println(e.getMessage()); 
		}       
		finally {         
			if (result != null) try { result.close(); } catch (SQLException ignore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		 }
		
		return null;
   }*/

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
