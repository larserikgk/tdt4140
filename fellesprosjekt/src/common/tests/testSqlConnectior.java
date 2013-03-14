package common.tests;

import java.util.Date;
import java.util.Properties;
import java.sql.*; 

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
			set.setProperty("url", "127.0.0.1"); 
			set.setProperty("username", "root"); 
			set.setProperty("database", "prosjekt");  // må forandres
			set.setProperty("password", "haakon"); 
			
			sql = new SQLquerries(set);
		
			
			Date dato1 = new Date(2010,06,25,12,50);
			Date dato2 = new Date(2010,06,25,12,50);
			String des = "DETTE ER ET MOTHERFUCKINGS MØTE!";
			
			
		// 	addSomeUsers(10);	
		//	addSomeEvent(2013,05,25,20,60,2013,06,25,21,20,"haakon1");
		//	addSomeEventDes("DETTE ER ET MOTHERFUCKINGS MØTE!");
		//	addSomeEventDes(dato1, dato2, "haakon1", des, "her");
			
			String get = "SELECT * FROM User";
			sql.getFromUser(get, 3);
			 
			
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
	private static void addSomeEvent(int year, int month, int day, int hour, int min, 
			int dyear,int dmonth, int dday, int dhour, int dmin, String userName)
	{
		sql.addSomeEvent(year, month, day, hour, min, dyear, dmonth, day, dhour, dmin, userName); 
	}
	private static void addSomeEventDes(Date dateStart, Date dateEnd, 
			String userName, String description, String location)
	{
		sql.addSomeEvent(dateStart, dateEnd, userName, description, location); 
		 
	}
	
	

}
