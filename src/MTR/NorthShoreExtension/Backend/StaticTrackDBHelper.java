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

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StaticTrackDBHelper {
	//Table names
	private static final String DBName = "jdbc:sqlite:MTRStaticTrackDatabase.db";
	
	private static final String TRACK_INFO_TABLENAME = "TrackInfo";
	private static final String TRACK_INFO_COLUMNS = "trackID INTEGER, line STRING, section STRING, blockNum INTEGER, "
			+ "length REAL, grade REAL, speedLimit INTEGER, primaryNext INTEGER, secondaryNext INTEGER, primaryPrev INTEGER, "
			+ "secondaryPrev INTEGER, biDirectional INTEGER, station INTEGER, underground INTEGER, firstTrack INTEGER, "
			+ "lastTrack INTEGER, elevation REAL, cumulativeElevation REAL";
	
	//NOTE: All times are integers as # of seconds since 1970
	private static final String TRAIN_CONTROLS_TABLENAME = "TrainControls";
	private static final String TRAIN_CONTROLS_COLUMNS = "trainID INTEGER, time INTEGER, powerCommand INTEGER, "
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
			int station, int underground, int firstTrack, int lastTrack, double elevation, double cumulativeElevation) {
		Connection connection = null;
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			String queryUpdateStatement = "INSERT INTO " + TRACK_INFO_TABLENAME + " values(' "+trackID+"', '"+line+"', '"+section+"', '"
		    +blockNum+"', '"+blockLength+"', '"+blockGrade+"', '"+speedLimit+"', '"+primaryNext+"', '"+secondaryNext+"', '"+primaryPrev+"', '"
		    		+secondaryPrev+"', '"+biDirectional+"', '"+station+"', '"+underground+"', '"+firstTrack+"', '"+lastTrack
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
				int underground = loadCSVInt(trackInfo[15]);
				int firstTrack = loadCSVInt(trackInfo[16]);
				int lastTrack = loadCSVInt(trackInfo[17]);
				double elevation = loadCSVDouble(trackInfo[18]);
				double cumulativeElevation = loadCSVDouble(trackInfo[19]);
				
				int station = 0;
				if(stationID > 0) {
					station = 1;
					addStation(trackID, stationID, stationName, stationSide);
				}
				
				addTrack(trackID, lineColor, section, blockNum, blockLength, blockGrade, speedLimit, primaryNext, secondaryNext, primaryPrev, secondaryPrev, biDirectional, station, underground, firstTrack, lastTrack, elevation, cumulativeElevation);
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
		try{
			connection = connect();
			
			Statement statement = connection.createStatement();
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
	
	public void addTrainStateRecord(int trainID, int time, int powerCmd, int speedCmd, double actualSpeed) throws SQLException {
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("INSERT INTO " + TRAIN_CONTROLS_TABLENAME + " values(' "+trainID+"', '"+time+"', '"
		    		+powerCmd+"', '"+speedCmd+"', '"+actualSpeed+")");   
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