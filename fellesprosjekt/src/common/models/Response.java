package common.models;

import java.io.Serializable;
import java.util.Properties;

public class Response implements Serializable{
	private String contents;
	private int type;
	public static final int PUSHNOTIFICATION = 1, LOGOUT = 2;
	private Properties properties;

	public Response(int type, String contents) {
		
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
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
