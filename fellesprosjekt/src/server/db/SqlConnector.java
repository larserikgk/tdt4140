package server.db;


import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import common.models.Event;
import common.models.Notification;
import common.models.Room;
import common.models.User;


public class SqlConnector {
	private String url;
	private String username;
	private String database;
	private String password;
	private Connection conn;
	private static Statement stmt; 
	private String addUser; 
	private String addEvent; 
	private static ResultSet rs;

	public SqlConnector(Properties settings) {
		this.url = settings.getProperty("url");
		this.username = settings.getProperty("username");
		this.database = settings.getProperty("database");
		this.password = settings.getProperty("password");
		
		try
		{
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			String sumUrl = "jdbc:mysql://" + url + "/" + database;
			conn = DriverManager.getConnection (sumUrl, username, password);
			System.out.println ("Database connection established");
		}
		catch (Exception e)
		{
			System.err.println ("Cannot connect to database server"+e.toString());
		}
	}
	
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
	
	public void addUserNotificationRelationDerp(Notification not)
	{
		String q = "INSERT INTO UserNotificationRelation(lest,username,notification_id) " +
				"VALUES(false, ? ," + not.getId() + ")";
		try {
			PreparedStatement p = (PreparedStatement) conn.prepareStatement(q);
			
			for(User user: not.getEvent().getParticipants() )
			{
				p.setString(1, user.getUsername());
				System.out.println(q); 
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void addUserNotificationRelation(Notification not)
	{
		String q = "INSERT INTO UserNotificationRelation(lest,username,notification_id) " +
				"VALUES(false, ? ," + not.getId() + ")";
		try {
			PreparedStatement p = (PreparedStatement) conn.prepareStatement(q);
			
			for(User user: not.getEvent().getParticipants() )
			{
				p.setString(1, user.getUsername());
				p.executeUpdate(); 
				System.out.println(q); 
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public int addNotification(Notification not)
	{
		set("INSERT INTO Notification(type, event_id, description) " +
				 " VALUES('" +not.getType()+"'," + not.getEvent().getId() + ",'"+not.getDescription() +"')");
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
		not.setId(autoIncValue);
		addUserNotificationRelation(not); 
		return autoIncValue; 
		
	}
	
	//brukes!
	public void addFullEvent(Event event) throws Exception 
	{
		
		if(event.getStart().getTime() > event.getEnd().getTime())
		{
			throw new Exception("Event tid stemmer ikke overens"); 
		}
			
		int event_id = addSomeEvent(event); 
		if(event_id > 0)
		{
			addParticipantsToEvent(event);
		}	
	}
	
	public void addParticipantsToEvent(Event event)
	{		
	//	String IKKESVAR = "IKKESVAR"; 
		String q; 
		try {
			
			for(User k: event.getParticipants())
			{
				
				q = "INSERT INTO Participant(status,username,event_id)" + 
						"VALUES("+(k.getUsername()==event.getAdmin().getUsername() ? "'SKAL'" : "'IKKESVART'")+" , ? ," 
								+ event.getId() + ")";
				System.out.println(q); 
				PreparedStatement p = conn.prepareStatement(q);
				//p.setString(1, "'IKKESVART'"); 
				p.setString(1, k.getUsername()); 
				p.executeUpdate();  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}	

	public void addAlert(Event event, User user, Date alertTime)
	{
		long time = alertTime.getTime(); 
		set("INSERT INTO HasAlert(event_id,username,alertTime)" 
				+ "Values("+event.getId() +",'"+user.getUsername() +"',"+ time +")"); 
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
		event.setId(autoIncValue); 
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
		set("INSERT INTO Booking(room_name, event_id) " +
				"VALUES('" + room.getName() + "'," + event.getId() + ")"); 
	}
		
	public void addRoom(Room room)
	{
		set("INSERT INTO Room(room_name, capacity) VALUES('" +room.getName() +"'," + room.getCapacity() + ")"); 
	}
	
	public ArrayList<Event> getBooking(Room room)
	{
		String querry = "select * from Event inner join(select * from Booking where room_name= ?) as Derp ON Event.event_id = Derp.event_id;"; 
		ArrayList<Event> result = new ArrayList<Event>(); 
		try {
			PreparedStatement p = conn.prepareStatement(querry); 
			p.setString(1, room.getName()); 
			rs = (ResultSet) p.executeQuery(); 
			while(rs.next())
			{
				result.add(new Event());
				//result.add(new Booking(rs.getInt(1), rs.getString(2), rs.getInt(3)));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null; 
	}
		
	public Room getBooking(Event event)
	{
		String querry = "select * from Room inner join(select * from Booking where event_id= ?) AS derp ON Room.room_name=derp.room_name;";
		Room result = null;
		try {
			PreparedStatement p = conn.prepareStatement(querry); 
			p.setInt(1, event.getId()); 
			rs = (ResultSet) p.executeQuery();
			while(rs.next())
			{
				p.setString(1, rs.getString(1));			 
				result = new Room(rs.getString(1), rs.getInt(2)); 
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
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
		
	public ArrayList<Room> availableRooms(Event event)
	{
		ArrayList<Room> result =  new ArrayList<Room>();
		long startTimeInMili = event.getStart().getTime(); 
		long endTimeInMili = event.getEnd().getTime(); 
		String q = "Select * from Room join (SELECT room_name FROM Booking join (SELECT event_id FROM Event WHERE( (startTime <=" + startTimeInMili + ")" +
				" AND( endTime >= " + startTimeInMili + 
				"))OR(( startTime >" + startTimeInMili + ") AND( startTime <" + endTimeInMili + " ))) AS overLapp ON Booking.event_id = overLapp.event_id) AS OverlappRoom ON Room.room_name = OverlappRoom.room_name;";
		try {
			rs = (ResultSet) stmt.executeQuery(q);
			while(rs.next())
			{
				result.add(new Room(rs.getString(1), rs.getInt(2))); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		return result; 
	}
	
	public User login(String username, String password)
	{
		ArrayList<String> send = new ArrayList<String>();
		ArrayList<User> users; 
		send.add(username); 
		users = getUsers(send, true); 
		
		if(users.get(0).getPassword().equals(password))
		 	return(users.get(0)); 
		return null; 
	}	
	
	public ArrayList<User> getUsers(ArrayList <String> ids, boolean hasPW)
	{
		ArrayList<User> result = new ArrayList<User>();
		
		String q; 
		if(hasPW)
			q = "SELECT * FROM User Where username = '"+ ids.get(0) + "'" ;
		else
			q = "SELECT username, name FROM User Where username = '"+ids.get(0)+ "'" ;
		
		ids.remove(0);
 
		try {
			
			for(String s: ids)
			{
				q += "OR '" + s +"'"; 
			}
		q += ";"; 
			stmt = conn.createStatement(); 
			rs = (ResultSet) stmt.executeQuery(q);
			if(hasPW)
				while(rs.next())
					result.add(new User(rs.getString(1), rs.getString(2), rs.getString(3)));
			else
				while(rs.next())		
					result.add(new User(rs.getString(1), rs.getString(2))); 
			
			System.out.println(q); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
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
