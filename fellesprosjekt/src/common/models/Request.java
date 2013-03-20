package common.models;

import java.io.Serializable;
import java.util.Properties;

public class Request implements Serializable{
	private String query;
	private int type;
	public static final int USER = 1, EVENT = 2, NOTIFICATION = 3, LOGOUT = 7;
	private Properties properties;
	
	public Request(String query, int type) {
		this.query = query;
		this.type =  type;
		this.properties = new Properties();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public void addProperty(String key, String value) {
		properties.put(key, value);
	}
	
	public String getPropety(String key) {
		return properties.getProperty(key);
	}
}
