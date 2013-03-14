package server.db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import common.models.Event;

public class SqlConnector {
	private String url;
	private String username;
	private String database;
	private String password;
	protected Connection conn;

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
	
	public ArrayList<Event> getEvents(int userId, int amount) {
		ArrayList<Event> list = new ArrayList<Event>();
		String[][] r = processQuery("SELECT * FROM access_rights WHERE user_id = " + userId + "");
		for (int i = 0; i < r.length; i++) {
			list.add(new Event());
		}
		return list;
	}
	
	/** A method for processing SELECT queries easier. Returns a String[][] instead of using result sets given 
	 * from the sql query. 
	 * 
	 * @param str
	 * @return
	 */
	private String[][] processQuery(String str) {
		try {
			Statement s = conn.createStatement();
			s.execute(str);
			ResultSet r = s.getResultSet();
			//Count number of rows
			r.last();  
			int num_rows = r.getRow(); 
			//Count number of columns
			r.first();
			ResultSetMetaData md = r.getMetaData();
			int columnNum = md.getColumnCount();
			String[][] result = new String[num_rows][columnNum];

			int i=0;
			//Loop through result and return array
			while (true && num_rows!=0) {
				for(int n=1;n<=columnNum;n++) {
					result[i][n-1] = r.getString(n);
				}
				i++;
				if(!r.next()) break;
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
