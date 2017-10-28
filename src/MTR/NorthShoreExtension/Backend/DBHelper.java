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
	//Table names
	private static final String DBName = "jdbc:sqlite:MTRDatabase.db";
	
	private static final String TRACK_INFO_TABLENAME = "TrackInfo";
	private static final String TRACK_INFO_COLUMNS = "trackID INTEGER, line STRING, section STRING, number INTEGER, "
			+ "length INTEGER, grade REAL, speedLimit INTEGER, infrastructure STRING, switchPosition INTEGER, elevation REAL, "
			+ "cumElevation REAL, startX INTEGER, startY INTEGER, endX INTEGER, endY INTEGER, curveStart INTEGER, "
			+ "curveEnd INTEGER, trackStatus STRING, heater STRING, speed INTEGER, authority INTEGER, occupied INTEGER";
	
	//NOTE: All times are integers as # of seconds since 1970
	private static final String TRAIN_CONTROLS_TABLENAME = "TrainControls";
	private static final String TRAIN_CONTROLS_COLUMNS = "trainID INTEGER, time INTEGER, powerCommand INTEGER, "
			+ "speedCommand INTEGER, actualSpeed REAL";
	
	private static final String INCIDENT_TABLENAME = "Incidents";
	private static final String INCIDENT_COLUMNS = "errorType STRING, timeBroken INTEGER, timeFixed INTEGER, trackID INTEGER"
			+ "trainID INTEGER";
	
	private static final String PASSENGER_TABLENAME = "Passengers";
	private static final String PASSENGER_COLUMNS = "trainID INTEGER, station STRING, scheduleArrivalTime INTEGER, "
			+ "actualArrivalTime INTEGER, numPassengersOn INTEGER, numPassengersOff INTEGER";
		
	public DBHelper() throws ClassNotFoundException {
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		try
		{
		    // create a database connection
		    connection = connect();
	
		    Statement statement = connection.createStatement();
		    statement.setQueryTimeout(30);  // set timeout to 30 sec. Keep this???
	
		    statement.executeUpdate("DROP TABLE IF EXISTS " + TRACK_INFO_TABLENAME);
		    statement.executeUpdate("CREATE TABLE " + TRACK_INFO_TABLENAME + " (" + TRACK_INFO_COLUMNS + ")");	
		    
		    statement.executeUpdate("DROP TABLE IF EXISTS " + TRAIN_CONTROLS_TABLENAME);
		    statement.executeUpdate("CREATE TABLE " + TRAIN_CONTROLS_TABLENAME + " (" + TRAIN_CONTROLS_COLUMNS + ")");	
		    
		    statement.executeUpdate("DROP TABLE IF EXISTS " + INCIDENT_TABLENAME);
		    statement.executeUpdate("CREATE TABLE " + INCIDENT_TABLENAME + " (" + INCIDENT_COLUMNS + ")");	
		    
		    statement.executeUpdate("DROP TABLE IF EXISTS " + PASSENGER_TABLENAME);
		    statement.executeUpdate("CREATE TABLE " + PASSENGER_TABLENAME + " (" + PASSENGER_COLUMNS + ")");	
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
	
	public void addTrack(int trackID, String line, String section, int blockNum, int blockLength, double blockGrade, 
			int speedLimit, String infrastructure, double elevation, double cumulativeElevation, int startX, int startY, 
			int endX, int endY, int curveStart, int curveEnd, String heater) throws SQLException {
		Connection connection = connect();
		
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30); //TODO: Is this needed?
		
	    statement.executeUpdate("INSERT INTO " + TRACK_INFO_TABLENAME + " values(' "+trackID+"', '"+line+"', '"+section+"', '"
	    +blockNum+"', '"+blockLength+"', '"+blockGrade+"', '"+speedLimit+"', '"+infrastructure+"', '0', '"+elevation+"', '"
	    		+cumulativeElevation+"', '"+startX+"', '"+startY+"', '"+endX+"', '"+endY+"', '"+curveStart+"', '"+curveEnd+
	    		"', '', '"+heater+"', '0', '0', '0')");   
	    
	    connection.close();
	    //default trainStatus = ""
	    //default speed, authority, occupied = 0
	    //default switchPosition = 0
	}
	
	public void updateTrackStatus(String line, String section, int blockNum, String status) {
		
	}

	public void updateHeater(String line, String section, int blockNum, String heaterStatus) {
		
	}

	public void updateSpeedAuthority(String line, String section, int blockNum, int speed, int authority) {
		
	}

	public void updateTrackOccupied(String line, String section, int blockNum, boolean isOccupied) {
		
	}
	
	public void addTrainStateRecord(int trainID, int time, int powerCmd, int speedCmd, double actualSpeed) throws SQLException {
		Connection connection = connect();
		
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30); //TODO: Is this needed?
		
	    statement.executeUpdate("INSERT INTO " + TRAIN_CONTROLS_TABLENAME + " values(' "+trainID+"', '"+time+"', '"
	    		+powerCmd+"', '"+speedCmd+"', '"+actualSpeed+")");   
	    
	    connection.close();
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
