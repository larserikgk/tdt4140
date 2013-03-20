package common.tests;

import java.net.ConnectException;
import java.util.Properties;

import common.models.User;

import client.net.ServerConnector;

public class TestConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Properties settings = new Properties();
		settings.setProperty("url", "129.241.127.13");
		ServerConnector connector = new ServerConnector(settings,"derp");
		try {
			connector.start();
			User derp = connector.getUser("admiralen", "onkel");
			System.out.println(derp.getName());
		} catch (ConnectException e) {
			e.printStackTrace();
		}		
		
	}

}
