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
		


}
