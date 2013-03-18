package common.tests;

import java.util.ArrayList;

import java.util.Date;
import java.util.Properties;
import java.sql.*; 

import common.models.Event;
import common.models.Room;
import common.models.User;
import common.models.Booking;

import server.db.SQLquerries;
import server.db.SqlConnector;


public class testSqlConnectior {

	private static Properties set;
	private static SQLquerries sql; 

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
			set = new Properties();
			set.setProperty("url", "129.241.127.13"); 
			set.setProperty("username", "root"); 
			set.setProperty("database", "prosjekt");  // må forandres
			set.setProperty("password", "stiligPassord"); 
			
			sql = new SQLquerries(set);
		
			
			Date dato1 = new Date(2012,07,25,11,50);
			Date dato2 = new Date(2012,07,25,11,30);
			String des = "DETTE ER ET MOTHERFUCKINGS MØTE!";
		    User haakon = new User("haakon1","hei","haakon Aasebæ");
			User hAkon = new User("håkon1","stiligPassord","håkon kwrl");
			User p = new User("admiralen","onkel",":P");
//			sql.addSomeUser(p);
//			sql.addSomeUser(haakon);
//			sql.addSomeUser(hAkon);	
			Event vent = new Event(haakon, dato1, dato2);
			vent.addParticipant(hAkon); 
			vent.addParticipant(p); 
			
			
		//	addSomeUsers(1);	
		//	addSomeEvent(2013,05,25,20,60,2013,06,25,21,20,"haakon1");
			//	addSomeEventDes("DETTE ER ET MOTHERFUCKINGS MØTE!");
	//		int event_id = sql.addSomeEvent(dato1, dato2, "haakon1", des, "her");
	//		System.out.println(event_id);
			
			
		//	String get = "SELECT username, name FROM User";
		//	ArrayList<User> k = sql.getFromUser(get);
				
		//	for(User p:k)
		//		System.out.println(p.getName() + " " + p.getUsername());
						 
			sql.addFullEvent(vent);
			Room rom = new Room("HAAKONSROM", 5); 
		//	sql.addRoom(rom);
		//	sql.addBooking(vent, rom); 
			
			//System.out.println(sql.gettAllEvent(hAkon).toString());
			System.out.println(sql.getBooking(vent).toString()); 
	}
	
	
	public static void addSomeUsers(int t) throws SQLException
	{
		String test;
		for(int i = 0; i < t; i++)
		{
//			test = "INSERT INTO User(username, passord, name) VALUES ('derpe" + i +"','DerpeMer', 'Derpus')";
			sql.addSomeUser("haakon" + i, "hei", "haakon");
		}
		
	}
	private static void addSomeEvent(int year, int month, int day, int hour, int min, int dyear,int dmonth, int dday, int dhour, int dmin, String userName)
	{
		sql.addSomeEvent(year, month, day, hour, min, dyear, dmonth, day, dhour, dmin, userName); 
	}
	private static void addSomeEventDes(Date dateStart, Date dateEnd, String userName, String description, String location)
	{
		sql.addSomeEvent(dateStart, dateEnd, userName, description, location); 
		 
	}
	
	

}
