package common.tests;

import java.net.ConnectException;
import java.util.Properties;

import client.net.ServerConnector;

public class TestConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Properties settings = new Properties();
		settings.setProperty("url", "127.0.0.1");
		ServerConnector connector = new ServerConnector(settings,"derp");
		try {
			connector.start();
		} catch (ConnectException e) {
			e.printStackTrace();
		}		
	}

}
