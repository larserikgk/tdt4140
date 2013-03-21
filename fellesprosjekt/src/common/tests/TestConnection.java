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
			Event fest = new Event(derp, -1, new Date(0, 0, 0), new Date(4000, 2, 13), "Fest", "My place", "derp", new ArrayList<User>(), new Room("",0));
			connector.addEvent(fest);
		} catch (ConnectException e) {
			e.printStackTrace();
		}		
		
	}

}
