package common.tests;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Properties;

import common.models.Event;
import common.models.User;

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
			User derp = connector.getUser("admiralen", "onkel");
			ArrayList<Event> events= connector.getAppointments(derp, 10); 
			for(Event e:events)
				System.out.println(e.getName());
		} catch (ConnectException e) {
			e.printStackTrace();
		}		
		
	}

}
