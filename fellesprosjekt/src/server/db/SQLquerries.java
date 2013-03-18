package server.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

import common.models.Booking;
import common.models.Event;
import common.models.Room;
import common.models.User;
import common.models.Status;


public class SQLquerries extends SqlConnector
{
	private static Statement stmt; 
	private String addUser; 
	private String addEvent; 
	private static ResultSet rs;
	
//	private final String addUser; 

	public SQLquerries(Properties settings) {
		super(settings);		
	}

//ikke testet, burde fungere
	public void addSomeUser(User user)
	{
		addUser = "INSERT INTO User(username, passord, name) VALUES ('"+ user.getUsername() +"','" + user.getPassword() +"','" + user.getName() + "')";
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(addUser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addSomeUser(String userName, String pw, String name)
	{
		//username, passord, name
		addUser = "INSERT INTO User(username, passord, name) VALUES ('"+ userName +"','" + pw +"','" + name + "')";
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(addUser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		
		
	}
	
	//Denne vil jeg helst at vi ungår å bruke.. 
	//description feltene, osv er ikke tatt med 
	public void addSomeEvent(int year, int month, int day, int hour, int min, int dyear,int dmonth, int dday, int dhour, int dmin, String userName)
	{
								//year, month, date, hrs, min
		Date dateStart = new Date(year, month, day, hour, min); 
		Date dateEnd = new Date(dyear, dmonth, dday, dhour, dmin); 
	
		System.out.println(dateStart.getTime());
		System.out.println(dateEnd.getTime()); 
		
		long dateStartInMiliSec = dateStart.getTime();
		long dateEndInMiliSec = dateEnd.getTime();	
		 
		
		set("INSERT INTO Event(startTime, endTime, owner) " +
				"VALUES(" + dateStartInMiliSec + "," + dateEndInMiliSec + ",'" +userName+"')");
		  
	}
	
	public int addSomeEvent(Date dateStart, Date dateEnd, String userName, String description, String location)
	{	
//		System.out.println(dateStart.getTime());
//		System.out.println(dateEnd.getTime()); 
		
		long dateStartInMiliSec = dateStart.getTime();
		long dateEndInMiliSec = dateEnd.getTime();
		java.sql.ResultSet r = null; 
		 
		set("INSERT INTO Event(startTime, endTime, owner, description, location) " +
					"VALUES(" + dateStartInMiliSec + "," + dateEndInMiliSec + ",'" +userName+"','"+description+"','"+location+"')");
	
		int autoIncValue = 0; 
		try {
			rs = (ResultSet) stmt.getGeneratedKeys();
			autoIncValue = -1;
			if(rs.next()) 
			{
			       autoIncValue = rs.getInt(1);
			       /*You can get more generated keys if they are generated in your code*/
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return autoIncValue;  
		
		
	}
	public void addSomeEvent(long dateStartInMiliSec, long dateEndInMiliSec, String userName, String description, String location)
	{		 
		set("INSERT INTO Event(startTime, endTime, owner, description, location) " +
				"VALUES(" + dateStartInMiliSec + "," + dateEndInMiliSec + ",'" +userName+"','"+description+"','"+location+"')");	
	}
	
	//brukes!
	public void addFullEvent(Event event)
	{
		int event_id = addSomeEvent(event); 
		if(event_id > 0)
		{
			addParticipantsToEvent(event, event_id);
		}	
	}
	
	public void addParticipantsToEvent(Event event, int event_ID)
	{
		
	//	String IKKESVAR = "IKKESVAR"; 
		String q = "INSERT INTO Participant(username,event_id)" + 
				"VALUES(? ," + event_ID + ")"; 
		try {
			PreparedStatement p = conn.prepareStatement(q);
			for(User k: event.getParticipants())
			{
				//p.setString(1, "'IKKESVAR'"); 
				p.setString(1, k.getUsername()); 
				p.executeUpdate();  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	//brukes 
	public int addSomeEvent(Event event) 
	{
		
		long dateStartInMiliSec = event.getStart().getTime();
		long dateEndInMiliSec = event.getEnd().getTime();
		String userName = event.getAdmin().getUsername(); 
		String description = event.getDescription(); 
		String location = event.getLocation(); 
		 
		set("INSERT INTO Event(startTime, endTime, owner, description, location) " +
				"VALUES(" + dateStartInMiliSec + "," + dateEndInMiliSec + ",'" +userName+"','"+description+"','"+location+"')");
		
		int autoIncValue = 0; 
		try {
			rs = (ResultSet) stmt.getGeneratedKeys();
			autoIncValue = -1;
			if(rs.next()) 
			{
			       autoIncValue = rs.getInt(1);
			       /*You can get more generated keys if they are generated in your code*/
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return autoIncValue; 
	}
	
	private boolean testToSeIfTextIsEmpty(String t)
	{
		if(!t.equals(""))
		{
			return true; 
		}
		return false; 
	}
	public void addBooking(Event event, Room room)
	{
		set("INSERT INTO Booking(room_name, event_id) VALUES('" + room.getName() + "','" + event.getId() + "')"); 
	}
	public void addBooking(Booking booking)
	{
		set("INSERT INTO Booking(room_name, event_id) VALUES('" + booking.getRoom_name() + "','" + booking.getEvent_id()+ "')");
	}
	
	public void addRoom(Room room)
	{
		set("INSERT INTO Room(room_name, capacity) VALUES('" +room.getName() +"'," + room.getCapacity() + ")"); 
	}
	
	public ArrayList<Booking> getBooking(Room room)
	{
		String querry = "SELECT * FROM Booking WHERE room_name = ?"; 
		ArrayList<Booking> result = new ArrayList<Booking>(); 
		try {
			PreparedStatement p = conn.prepareStatement(querry); 
			p.setString(1, room.getName()); 
			rs = (ResultSet) p.executeQuery(); 
			while(rs.next())
			{
				result.add(new Booking(rs.getInt(1), rs.getString(2), rs.getInt(3)));
				System.out.println("!"); 
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result; 
	}
	
	//ikke testet
	public ArrayList<Booking> getBooking(Booking b)
	{
		String querry = "SELECT * FROM Booking WHERE booking_id = ?"; 
		ArrayList<Booking> result = new ArrayList<Booking>(); 
		try {
			PreparedStatement p = conn.prepareStatement(querry); 
			p.setInt(1, b.getBooking_id()); 
			rs = (ResultSet) p.executeQuery(); 
			while(rs.next())
			{
				result.add(new Booking(rs.getInt(1), rs.getString(2), rs.getInt(3)));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result; 
	}
	
	public ArrayList<Booking> getBooking(Event event)
	{
		String querry = "SELECT * FROM Booking WHERE event_id = ?"; 
		ArrayList<Booking> result = new ArrayList<Booking>(); 
		try {
			PreparedStatement p = conn.prepareStatement(querry); 
			p.setInt(1, event.getId()); 
			rs = (ResultSet) p.executeQuery(); 
			while(rs.next())
			{
				result.add(new Booking(rs.getInt(1), rs.getString(2), rs.getInt(3)));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result; 
	}
	
	public ArrayList<Event> gettAllEvent(User user)
	{
		String querry = "SELECT * FROM Event WHERE event_id = ?"; 
		ArrayList<Integer> eventID = new ArrayList<Integer>();
		ArrayList<Event> result = new ArrayList<Event>(); 
		eventID = getAllEventID(user);
		
		try {
			//stmt = conn.createStatement()
			PreparedStatement p = conn.prepareStatement(querry);			
			for(int i : eventID)
			{
				p.setInt(1, i);
				rs = (ResultSet) p.executeQuery(); 				
				while(rs.next())
				{				
					//(User admin, int id, Date start, Date end, String description, String location, String name 
					result.add(new Event(user, i, new Date(rs.getLong(3)), new Date(rs.getLong(4)), rs.getString(5), rs.getString(6), rs.getString(2)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	//event, admin, start, end date
	
	public ArrayList<Integer> getAllEventID(User user)
	{
		ArrayList<Integer> result = new ArrayList<Integer>(); 
		String querry = "SELECT * FROM Participant WHERE username ='" + user.getUsername() + "';"; 		
		try {
			stmt = conn.createStatement();
			rs = (ResultSet) stmt.executeQuery(querry);
			while(rs.next())
			{				
				result.add(rs.getInt(3));  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return result;
	}
	
	
	
	//kan brukes til å hente ut brukernavn også!
	public ArrayList<User> getFromUser(String querry) {
		
		//"SELECT username, name FROM User";
		ArrayList<User> result = new ArrayList<User>();
		try {
			stmt = conn.createStatement();
			rs = (ResultSet) stmt.executeQuery(querry);
			while(rs.next())
			{				
				result.add(new User(rs.getString(1),rs.getString(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public boolean isUser(String username, String passord)
	{
		String q = "SELECT passord FROM User WHERE username = '" + username +"';"; 
		try {
			stmt = conn.createStatement(); 
			ResultSet p = (ResultSet) stmt.executeQuery(q);
			if(p.getString(1).equals(passord));
			 	return true; 
		} catch (Exception e) {
			return false; 
		}		
	}
	
	
	// for å kjøte en statement; 
	private ResultSet set(String querry)
	{
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(querry);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}	
}
