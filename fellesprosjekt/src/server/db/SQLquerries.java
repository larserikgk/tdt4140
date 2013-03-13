package server.db;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.Connection;

public class SQLquerries extends SqlConnector
{
	private Statement stmt; 

	public SQLquerries(Properties settings) {
		super(settings);
	}
	
	public void addSome(String str)
	{
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		
		
	}
	
	
}
