package common.tests;

import java.net.ConnectException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Properties;

import common.models.Event;
import common.models.Room;
import common.models.User;

import client.net.ServerConnector;

public class TestConnection {
	static ServerConnector connector;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Properties settings = new Properties();
		settings.setProperty("url", "127.0.0.1");
		connector = new ServerConnector(settings,"derp");
		getEvents();
		
	}
	
	public static void addEvent() {
		try {
			connector.start();
			User derp = connector.getUser("admiralen", "onkel");
			Event fest = new Event(derp, -1, new Date(0, 0, 0), new Date(4000, 2, 13), "Fest", "My place", "derp", new ArrayList<User>(), new Room("",0));
			connector.addEvent(fest);
		} catch (ConnectException e) {
			e.printStackTrace();
		}	
	}
	
	public static void getEvents() {
		try {
			connector.start();
			User derp = connector.getUser("admiralen", "onkel");
			ArrayList<Event> list = connector.getEvents(derp, 0);
			for (int i = 0; i < list.size(); i++) {
				System.out.println("Test says: " + list.get(i).getName());
			}
		} catch (ConnectException e) {
			e.printStackTrace();
		}	
	}

}
