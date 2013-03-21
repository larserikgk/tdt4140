package common.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

public class Request implements Serializable{
	private String query;
	private int type;
	public static final int USER = 1, EVENT = 2, NOTIFICATION = 3, LOGOUT = 7;
	private Properties properties;
	ArrayList<User> list;
	
	public Request(String query, int type) {
		this.query = query;
		this.type =  type;
		this.properties = new Properties();
		list = new ArrayList<User>();
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

	public ArrayList<User> getList() {
		return list;
	}

	public void setList(ArrayList<User> list) {
		for (int i = 0; i < list.size(); i++) {
			this.list.add(list.get(i));
		}
	}
	
	
}
