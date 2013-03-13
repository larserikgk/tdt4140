package common.tests;

import java.util.Properties;
import java.sql.*; 

import server.db.SQLquerries;
import server.db.SqlConnector;

public class testSqlConnectior {

	private static Properties set;
	private static SQLquerries sql; 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			set = new Properties();
			set.setProperty("url", "127.0.0.1"); 
			set.setProperty("username", "root"); 
			set.setProperty("database", "prosjekt");  // må forandres
			set.setProperty("password", "haakon"); 
			
			sql = new SQLquerries(set);
			addUsers(10);
			 
			
	}
	public static void addUsers(int t)
	{
		
		String test;
		for(int i = 0; i < t; i++)
		{
			test = "INSERT INTO User(username, passord, name) VALUES ('derpe" + i +"','DerpeMer', 'Derpus')";
			sql.addSome(test);
		}
		
	}
	

}
