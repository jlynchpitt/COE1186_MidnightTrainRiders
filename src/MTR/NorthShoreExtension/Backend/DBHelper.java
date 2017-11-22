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
	int[] returnArray = new int[6]; 
	//Table names
	private static final String DBName = "jdbc:sqlite:MTRDatabase.db";
	
	private static final String TRACK_INFO_TABLENAME = "TrackInfo";
	private static final String TRACK_INFO_COLUMNS = "rowID INTEGER, trackID INTEGER, line STRING, section STRING, number INTEGER, "
			+ "length INTEGER, grade REAL, speedLimit INTEGER, infrastructure STRING, switchPosition INTEGER, elevation REAL, "
			+ "cumElevation REAL, startX INTEGER, startY INTEGER, endX INTEGER, endY INTEGER, curveStart INTEGER, "
			+ "curveEnd INTEGER, trackStatus STRING, heater STRING, speed INTEGER, authority INTEGER, occupied INTEGER,"
			+ "nextTrack INTEGER" + "prevTrack INTEGER" + "secondSwitch INTEGER" + "switchPosition INTEGER";
	
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
		
	public DBHelper() {
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
	
	public void addTrack(int rowID, int trackID, String line, String section, int blockNum, int blockLength, double blockGrade, 
			int speedLimit, String infrastructure, double elevation, double cumulativeElevation, int startX, int startY, 
			int endX, int endY, int curveStart, int curveEnd, String heater, int nextTrack, int prevTrack, int secondSwitch, 
			int switchPosition) throws SQLException {
		Connection connection = connect();
		
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30); //TODO: Is this needed?
		
	    statement.executeUpdate("INSERT INTO " + TRACK_INFO_TABLENAME + " values(' "+rowID+"' , ' "+trackID+"', '"+line+"', '"+section+"', '"
	    +blockNum+"', '"+blockLength+"', '"+blockGrade+"', '"+speedLimit+"', '"+infrastructure+"', '0', '"+elevation+"', '"
	    		+cumulativeElevation+"', '"+startX+"', '"+startY+"', '"+endX+"', '"+endY+"', '"+curveStart+"', '"+curveEnd+
	    		"', '', '"+heater+"', '0', '0', '0', '"+nextTrack+"','"+prevTrack+"','"+secondSwitch+"','"+switchPosition+"')");   
	    
	    connection.close();
	    //default trainStatus = ""
	    //default speed, authority, occupied = 0
	    //default switchPosition = 0
	}
	public void showTrackTest() {
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			ResultSet resultSet = statement.executeQuery("SELECT * from TrackInfo");  
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
			 try {
	               if(connection != null)
	                  connection.close();
		     }
		     catch(SQLException e) {  // Use SQLException class instead.          
		    	 System.err.println(e); 
		     }
		 }
	}
	
	public void updateTrackStatus(int trackid, String status) {
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("UPDATE TrackInfo SET trackStatus = '"+status+"' WHERE trackID = '"+trackid+"'");   

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

	public void updateHeater(int trackid, String heaterStatus) {
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("UPDATE TrackInfo SET heater = '"+heaterStatus+"' WHERE trackID = '"+trackid+"'");   

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

	public void updateSpeedAuthority(int trackid, int speed, int authority) {
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("UPDATE TrackInfo SET speed = '"+speed+"', authority = '"+authority+"' WHERE trackID = '"+trackid+"'");   

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

	public void updateTrackOccupied(int trackid, int isOccupied) {
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("UPDATE TrackInfo SET occupied = '"+isOccupied+"' WHERE trackID = '"+trackid+"'");   

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
	
	//added a method for getting information on track status (for Repair Scheduling) - Matt
	public String getTrackStatus(int trackid) {
		String status = null;
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet result = statement.executeQuery("SELECT * from  TrackInfo WHERE trackID = '" + trackid + "'");
			status = result.getString("trackStatus");
		} catch(SQLException e) {
			System.err.println(e);
		}
		
		return status;
	}
	
	public String getInfrastructure(int trackid) {
		String type = null;
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    ResultSet result = statement.executeQuery("SELECT * from TrackInfo WHERE trackID = '"+trackid+"'");   
		    	type = result.getString("infrastructure");
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
		return type;
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
	
	public int getNextTrack(int trackid, String direction){
		int nextTrack = 0;
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			ResultSet track = statement.executeQuery("SELECT * from TrackInfo WHERE trackID = '"+trackid+"'");
			if(direction.equals("forward")) {
				if(track.getInt("switchPosition")==0) {
					nextTrack = track.getInt("nextTrack");
				}
				else {
					nextTrack = track.getInt("secondSwitch");
				}
			}
			else if(direction.equals("backward")) {
				if(track.getInt("switchPosition")==0) {
					nextTrack = track.getInt("prevTrack");
				}
				else {
					nextTrack = track.getInt("secondSwitch");
				}
			}

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

		return nextTrack;
	}
	
	public void setSwitch(int trackid, int position) {
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    statement.executeUpdate("UPDATE TrackInfo SET switchPosition = '"+position+"' WHERE trackID = '"+trackid+"'");   

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
	
	public String getColor(int rowID) {
		Connection connection = null;
		String color = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
		    ResultSet resultSet = statement.executeQuery("SELECT * from TrackInfo where rowID = '"+rowID+"'");   
		    color = resultSet.getString("line");
		    System.out.println("color = " + color);
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
		return color;
	}
	
	public int[] getDrawingCoordinates(int rowID) {
		Connection connection = null;
		
		try {
			connection = connect();
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); //TODO: Is this needed?
			
			ResultSet resultSet = statement.executeQuery("SELECT * from TrackInfo where rowID = '"+rowID+"'");   
			returnArray[0] = resultSet.getInt("startX");
			returnArray[1] = resultSet.getInt("startY");
		    returnArray[2] = resultSet.getInt("endX");
		    returnArray[3] = resultSet.getInt("endY");
		    returnArray[4] = resultSet.getInt("curveStart");
		    returnArray[5] = resultSet.getInt("curveEnd");
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
		return returnArray;
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
