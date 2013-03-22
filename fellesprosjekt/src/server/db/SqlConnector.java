package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import common.models.Event;
import common.models.Notification;
import common.models.Notification.NotificationType;
import common.models.Room;
import common.models.User;


public class SqlConnector {
	private String url;
	private String username;
	private String database;
	private String password;
	private Connection conn;
	private static Statement stmt; 
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

	public void updateEvent(Event event)
	{
		String q = "UPDATE Event SET owner='"+event.getAdmin().getUsername()
				+"', location='"+event.getLocation()
				+"', description='"+event.getDescription()
				+"', startTime="+event.getStart().getTime()
				+", endTime="+event.getEnd().getTime();
		System.out.println(event.getId());
		removeAllParticipants(event);
		removeBooking(event);

		addBooking(event, event.getRoom());
		addParticipantsToEvent(event);

		set(q);
	}

	public void removeEvent(int event_id)
	{
		String q = "DELETE FROM Event WHERE event_id="+event_id;
		removeAllParticipants(event_id);
		removeAllNotifications(event_id);
		removeBooking(event_id);
		set(q);
	}

	public void removeEvent(Event event)
	{
		removeEvent(event.getId());
	}

	public void removeAllParticipants(Event event)
	{
		removeAllParticipants(event.getId());
	}

	public void removeAllParticipants(int event_id)
	{
		String q = "DELETE FROM Participant WHERE event_id="+event_id;
		set(q);
	}

	public void removeAllNotifications(Event event)
	{
		removeAllNotifications(event.getId());
	}

	public void removeAllNotifications(int event_id)
	{
		String q = "DELETE FROM Notification WHERE event_id="+event_id;
		set(q);
	}

	public void removeBooking(Event event)
	{
		String q = "DELETE FROM Booking WHERE event_id="+event.getId();
		set(q);
	}

	public void removeBooking(int event_id)
	{
		String q = "DELETE FROM Booking WHERE event_id="+event_id;
		set(q);
	}

	public void addUser(User user)
	{
		addUser(user.getUsername(), user.getPassword(), user.getName());
	}

	public void addUser(String userName, String pw, String name)
	{
		String addUser = "INSERT INTO User(username, passord, name) VALUES ('"+ userName +"','" + pw +"','" + name + "')";

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(addUser);
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
	} 

	public void addUserNotificationRelation(Notification not)
	{
		String q = "INSERT INTO UserNotificationRelation(lest,username,notification_id) " +
				"VALUES(false, ? ," + not.getId() + ")";
		try 
		{
			PreparedStatement p = conn.prepareStatement(q);

			for(User user: not.getEvent().getParticipants() )
			{
				p.setString(1, user.getUsername());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	public int addNotification(Notification not)
	{
		set("INSERT INTO Notification(type, event_id, description) " +
				" VALUES('" +not.getType()+"'," + not.getEvent().getId() + ",'"+not.getDescription() +"')");
		int autoIncValue = -1; 
		try 
		{
			rs = (ResultSet) stmt.getGeneratedKeys();
			if(rs.next()) 
				autoIncValue = rs.getInt(1);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}

		not.setId(autoIncValue);
		addUserNotificationRelation(not); 
		return autoIncValue; 

	}

	public void addFullEvent(Event event) throws Exception 
	{
		System.out.println(event.toString());
		if(event.getStart().getTime() > event.getEnd().getTime())	
			throw new Exception("Event tid stemmer ikke overens"); 

		if(addEvent(event) > 0)
			addParticipantsToEvent(event);

		if(event.getRoom() != null || event.getRoom().getName().equals(""))
			addBooking(event, event.getRoom());
	}

	public int addEvent(Event event) 
	{		
		set("INSERT INTO Event(owner,startTime, endTime, name, description, location) " +
				"VALUES('" + event.getAdmin().getUsername() + "'," + event.getStart().getTime()+ "," + event.getEnd().getTime() + ",'" +event.getName()+"','"+event.getDescription()+"','"+event.getLocation()+"')");

		int autoIncValue = -1; 
		try 
		{
			rs = stmt.getGeneratedKeys();
			if(rs.next()) 
				autoIncValue = rs.getInt(1);

		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		event.setId(autoIncValue); 
		return event.getId(); 
	}

	public void addParticipantsToEvent(Event event)
	{		
		String q; 
		try {			
			for(User participant: event.getParticipants())
			{

				q = "INSERT INTO Participant(status,username,event_id)" + 
						"VALUES("+(participant.getUsername()==event.getAdmin().getUsername() ? "'SKAL'" : "'IKKESVART'")+" , ? ," 
						+ event.getId() + ")";
				PreparedStatement p = conn.prepareStatement(q);
				p.setString(1, participant.getUsername()); 
				p.executeUpdate();  
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} 
	}	

	public void addAlert(Event event, User user, Date alertTime)
	{
		set("INSERT INTO HasAlert(event_id,username,alertTime)" 
				+ "Values("+event.getId() +",'"+user.getUsername() +"',"+ alertTime.getTime() +")"); 
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

	public ArrayList<Notification> getUnreadNotifications (User user)
	{
		ArrayList<Notification> result = new ArrayList<Notification>();
		NotificationType type; 
		String q = "SELECT Notification.notification_id,type,description,event_id FROM Notification JOIN(SELECT notification_id FROM UserNotificationRelation " +
				"WHERE username = '" +user.getUsername()+ "' AND lest=0 ) AS derp ON derp.notification_id = Notification.notification_id";
		try {
			ResultSet rs = stmt.executeQuery(q); 
			while(rs.next())
			{
				if(rs.getString(2).equals("INVITATION"))
					type = NotificationType.INVITATION; 
				else if(rs.getString(2).equals("INV_RESPONSE"))
					type = NotificationType.INV_RESPONSE; 
				else 
					type = NotificationType.EVENT_UPDATE; 


				result.add(new Notification(rs.getInt(1), type, rs.getString(3), 
						getEvent(rs.getInt(4), true)));
			}
		} catch (Exception e) {

		}
		return result; 

	}
	public ArrayList<Notification> getAllNotifications (User user)
	{
		ArrayList<Notification> result = new ArrayList<Notification>();
		NotificationType type; 
		String q = "SELECT Notification.notification_id,type,description,event_id FROM Notification JOIN(SELECT notification_id FROM UserNotificationRelation " +
				"WHERE username = '" +user.getUsername()+ "') AS derp ON derp.notification_id = Notification.notification_id";
		System.out.println(q); 
		try {
			ResultSet rs = stmt.executeQuery(q); 
			while(rs.next())
			{
				if(rs.getString(2).equals("INVITATION"))
					type = NotificationType.INVITATION; 
				else if(rs.getString(2).equals("INV_RESPONSE"))
					type = NotificationType.INV_RESPONSE; 
				else 
					type = NotificationType.EVENT_UPDATE; 


				result.add(new Notification(rs.getInt(1), type, rs.getString(3), 
						getEvent(rs.getInt(4), false)));
			}
		} catch (Exception e) {

		}
		System.out.println("result fra database spørring  allnots: "+result.get(0).getEvent().getAdmin().getUsername());
		return result; 
	}
	public ArrayList<Notification> getReadNotifications (User user)
	{
		ArrayList<Notification> result = new ArrayList<Notification>();
		NotificationType type; 
		String q = "SELECT Notification.notification_id,type,description,event_id FROM Notification JOIN(SELECT notification_id FROM UserNotificationRelation " +
				"WHERE username = '" +user.getUsername()+ "' AND lest=1 ) AS derp ON derp.notification_id = Notification.notification_id";
		System.out.println(q); 
		try {
			ResultSet rs = stmt.executeQuery(q); 
			while(rs.next())
			{
				if(rs.getString(2).equals("INVITATION"))
					type = NotificationType.INVITATION; 
				else if(rs.getString(2).equals("INV_RESPONSE"))
					type = NotificationType.INV_RESPONSE; 
				else 
					type = NotificationType.EVENT_UPDATE; 


				result.add(new Notification(rs.getInt(1), type, rs.getString(3), 
						getEvent(rs.getInt(4), true)));
			}
		} catch (Exception e) {

		}
		return result; 
	}

	public Event getEvent(int event_id, boolean getParticipants)
	{
		Event result = null; 
		ArrayList<User> participants = new ArrayList<User>();
		ArrayList<String> ids = new ArrayList<String>(); 
		String q = "SELECT * FROM Event WHERE event_id = ?";
		System.out.println(q); 
		User owner = null; 

		try {
			PreparedStatement p = conn.prepareStatement(q); 
			p.setInt(1, event_id);
			ResultSet rs = p.executeQuery();
			while(rs.next())
			{
				if(getParticipants)
				{
					participants = getParticipants(event_id, "");
					for(User user:participants)
					{
						if(user.getUsername().equals(rs.getString(7)))
						{
							owner = user; 
						}
					}
					result = new Event(owner, event_id, new Date(rs.getLong(3)), new Date(rs.getLong(4)), rs.getString(2), rs.getString(5),rs.getString(6), participants, getBooking(event_id)); 
				}
				else
				{
					ids.add(rs.getString(7)); 
					result = new Event(new User(rs.getString(7), "", ""), event_id, new Date(rs.getLong(3)), new Date(rs.getLong(4)), rs.getString(2), rs.getString(5),rs.getString(6), new ArrayList<User>(), getBooking(event_id));
				}

			}

		} catch (Exception e) {
			e.printStackTrace(); 
		}	

		return result; 
	}

	public Room getBooking(Event event)
	{
		return getBooking(event.getId());
	}

	public Room getBooking(int eventID)

	{
		String querry = "select * from Room inner join(select * from Booking where event_id= ?) AS derp ON Room.room_name=derp.room_name;";
		Room result = new Room("",0);
		try {
			PreparedStatement p = conn.prepareStatement(querry); 
			p.setInt(1, eventID); 
			rs = p.executeQuery();
			while(rs.next())
			{
				p.setString(1, rs.getString(1));			 
				result = new Room(rs.getString(1), rs.getInt(2)); 
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return result; 
	}

	public ArrayList<Event> getAllEvent(User user)
	{
		String querry = "SELECT * FROM Event WHERE event_id = ?"; 
		ArrayList<Integer> eventID = getAllEventID(user);
		ArrayList<Event> result = new ArrayList<Event>(); 


		try {
			PreparedStatement p = conn.prepareStatement(querry);			
			for(int i : eventID)
			{
				p.setInt(1, i);
				ResultSet rs = p.executeQuery(); 				
				while(rs.next())				
					result.add(new Event(	
							new User(rs.getString(7),""), 
							rs.getInt(1), 	
							new Date(rs.getLong(3)), 
							new Date(rs.getLong(4)), 
							(rs.getString(2)!=null ? rs.getString(2) : ""), 
							(rs.getString(5)!=null ? rs.getString(5) : ""), 
							(rs.getString(6)!=null ? rs.getString(6) : ""), 
							getParticipants(i, ""), 
							getBooking(rs.getInt(1))
							));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println(result.get(0).getId());
		return result;

	}

	public ArrayList<Integer> getAllEventID(User user)
	{
		ArrayList<Integer> result = new ArrayList<Integer>(); 
		String querry = "SELECT event_id FROM Participant WHERE username ='" + user.getUsername() + "';"; 		
		try 
		{
			stmt = conn.createStatement();
			rs =  stmt.executeQuery(querry);
			while(rs.next())		
				result.add(rs.getInt(1));  

		} catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		return result;
	}

	public ArrayList<Room> getAvailableRooms(Event event, String status)
	{
		ArrayList<Room> result =  new ArrayList<Room>();
		long startTimeInMili = event.getStart().getTime(); 
		long endTimeInMili = event.getEnd().getTime(); 
		String q = "Select * from Room join (SELECT room_name FROM Booking join (SELECT event_id FROM Event WHERE( (startTime <=" + startTimeInMili + ")" +
				" AND( endTime >= " + startTimeInMili + 
				"))OR(( startTime >" + startTimeInMili + ") AND( startTime <" + endTimeInMili + " ))) AS overLapp ON Booking.event_id = overLapp.event_id) AS OverlappRoom ON Room.room_name = OverlappRoom.room_name;";
		try 
		{
			rs = (ResultSet) stmt.executeQuery(q);
			while(rs.next())
				result.add(new Room(rs.getString(1), rs.getInt(2))); 
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		

		return result; 
	}

	public ArrayList<Room> getAllRooms() {
		ArrayList<Room> result =  new ArrayList<Room>();
		String q = "Select * from Room";
		try 
		{
			rs = (ResultSet) stmt.executeQuery(q);
			while(rs.next())
				result.add(new Room(rs.getString(1), rs.getInt(2))); 
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		

		return result; 
	}

	public ArrayList<User> getParticipants(Event event, String status) 
	{
		return(getParticipants(event.getId(), status)); 
	}
	public ArrayList<User> getParticipants(int event_id, String status)
	{
		ArrayList<String> ids = new ArrayList<String>(); 
		String q = "SELECT username FROM Participant WHERE event_id =" + event_id + " AND status like ?";

		try {
			PreparedStatement p = conn.prepareStatement(q);
			if(!status.equals(""))
				p.setString(1, status);
			else
				p.setString(1, "%"); 

			rs = p.executeQuery();
			while(rs.next())
			{
				ids.add(rs.getString(1)); 
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return getUsers(ids, false); 
	}

	public ArrayList<User> getAllUsers()
	{
		ArrayList<User> users = new ArrayList<User>();
		String q = "SELECT username, name FROM User";

		try {
			stmt = conn.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(q);

			while(rs.next())
				users.add(new User(rs.getString(1), rs.getString(2)));

		} catch (SQLException e) 
		{
			e.printStackTrace();
		} 

		return users;
	}

	public ArrayList<User> getUsers(ArrayList <String> ids, boolean hasPW)

	{
		ArrayList<User> result = new ArrayList<User>();
		String q; 

		if(hasPW)
			q = "SELECT * FROM User WHERE username = '"+ ids.get(0) + "'" ;
		else
			q = "SELECT username, name FROM User WHERE username = '"+ids.get(0)+ "'" ;

		ids.remove(0);

		try {

			for(String s: ids)
				q += "OR '" + s +"'"; 

			q += ";"; 
			stmt = conn.createStatement(); 
			rs = (ResultSet) stmt.executeQuery(q);
			if(hasPW)
				while(rs.next())
					result.add(new User(rs.getString(1), rs.getString(2), rs.getString(3)));
			else
				while(rs.next())		
					result.add(new User(rs.getString(1), rs.getString(2))); 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public User login(User user)
	{
		return login(user.getUsername(),user.getPassword());
	}


	public User login(String username, String password)
	{
		ArrayList<String> send = new ArrayList<String>();
		ArrayList<User> users; 
		send.add(username); 
		users = getUsers(send, true); 
		if (users.size() < 1) return new User("", "");
		if(users.get(0).getPassword().equals(password))
			return(users.get(0)); 
		return new User("", "");
	}	

	// for å kjøre en statement; 
	private ResultSet set(String querry)
	{
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(querry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}


}
