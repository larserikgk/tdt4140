package server.db;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

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
	
	public void addSomeEvent(Date dateStart, Date dateEnd, String userName, String description, String location)
	{	
//		System.out.println(dateStart.getTime());
//		System.out.println(dateEnd.getTime()); 
		
		long dateStartInMiliSec = dateStart.getTime();
		long dateEndInMiliSec = dateEnd.getTime();	
		 
		set("INSERT INTO Event(startTime, endTime, owner, description, location) " +
					"VALUES(" + dateStartInMiliSec + "," + dateEndInMiliSec + ",'" +userName+"','"+description+"','"+location+"')");		
		
	}
	public void addSomeEvent(long dateStartInMiliSec, long dateEndInMiliSec, String userName, String description, String location)
	{
	
//		System.out.println(dateStart.getTime());
//		System.out.println(dateEnd.getTime()); 		 
		
		set("INSERT INTO Event(startTime, endTime, owner, description, location) " +
				"VALUES(" + dateStartInMiliSec + "," + dateEndInMiliSec + ",'" +userName+"','"+description+"','"+location+"')");	 
	}
	
	private boolean testToSeIfTextIsEmpty(String t)
	{
		if(!t.equals(""))
		{
			return true; 
		}
		return false; 
	}		
	
	
	//beste jeg får til... returnerer en liste... 
	public static ArrayList<String> getFromUser(String querry, int rows) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> mid = new ArrayList<String>();
		int teller = 0; 
		
		try {
			stmt = conn.createStatement();
			rs = (ResultSet) stmt.executeQuery(querry);
			while(rs.next())
			{				
				for(int i = 1; i < rows + 1; i++)
				{
					mid.add(rs.getString(i)); 
					System.out.println(rs.getString(i)); 
					System.out.println(i);					
				}				
				
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(mid.toString()); 
		System.out.println(mid.get(0)); 
		return mid;
		
			//note that getString anomalously starts counting at 1, not 0
	}
		
	
	
	
	
	// for å kjøte en statement; 
	private void set(String querry)
	{
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(querry);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
