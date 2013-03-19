package client.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.w3c.dom.Document;

import client.gui.MainFrame;

import common.interfaces.IServerConnector;
import common.models.*;
import common.util.XMLConverter;

public class ServerConnector implements IServerConnector{
	private Socket socket;
	private Socket pushSocket;
	private MainFrame gui;
	private String url;
	private String username;
	private int handlerPort;
	private int pushPort;
	private ObjectInputStream input;
	private ObjectInputStream pushInput;
	private ObjectOutputStream output;
	private ObjectOutputStream pushOutput;
	private XMLConverter xmlConverter;

	public ServerConnector(Properties settings, String username) {
		this.url = settings.getProperty(url);
		this.username = username;
		this.handlerPort = 1600;
		this.pushPort = 1601;
	}

	public boolean start() throws ConnectException{
		try {
			socket = new Socket(url, handlerPort);
			pushSocket = new Socket(url, pushPort);
		} 
		catch(Exception ec) {
			System.err.println("Error connecting to server:" + ec);
			return false;
		}

		try
		{
			input  = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			pushInput = new ObjectInputStream(pushSocket.getInputStream());
			pushOutput = new ObjectOutputStream(pushSocket.getOutputStream());
		}
		catch (IOException eIO) {
			System.err.println("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		new NotificationListener().start();
		try
		{
			output.writeObject(username);
			pushOutput.writeObject(username);
			output.flush();
			pushOutput.flush();
		}
		catch (IOException eIO) {
			System.err.println("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		return true;
	}


	/**Transmits the request via outputstream to the server.
	 * 
	 * @param req
	 * @throws ConnectException 
	 */
	private String sendRequest(Request req) throws ConnectException {
		try {
			output.writeObject(req);
			output.flush();

			String result = (String) input.readObject();			
			return result;
		}
		catch(IOException e) {
			System.err.println("Exception writing to server: " + e);
			throw new ConnectException();
		}
		catch(ClassNotFoundException e) {

		}
		return null;

	}

	/**Closes all streams and shuts down the socket.
	 * 
	 */
	public void disconnect() throws ConnectException{
		try {
			output.writeObject(new Request(null,Request.LOGOUT));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try { 
			if(input != null) input.close();
		}
		catch(Exception e) { /** Ignore errors */}
		try {
			if(output != null) output.close();
		}
		catch(Exception e) {/** Ignore errors */} 
		try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {/** Ignore errors */} 

		if(gui != null)
			throw new ConnectException();

	}

	@Override
	public User getUser(String username, String password) throws ConnectException{
		Request request = new Request("login", Request.USER);
		request.addProperty("username", username);
		request.addProperty("password", password);
		String result = sendRequest(request);
		
		Document doc = xmlConverter.StringToDOMDocument(result);		

		return xmlConverter.constructUserFromNode(doc.getFirstChild());
	}

	@Override
	public ArrayList<Event> getEvents(User user, int count) throws ConnectException{
		ArrayList<Event> events = new ArrayList<Event>();
		Request request = new Request("events", Request.EVENT);
		request.addProperty("username", user.getUsername());
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		do{
			events.add(xmlConverter.constructEventFromNode(doc.getFirstChild()));

		} while(false);

		return events;
	}

	@Override
	public ArrayList<Event> getMeetings(User user, int count) throws ConnectException{
		ArrayList<Event> events = new ArrayList<Event>();
		Request request = new Request("meetings", Request.EVENT);
		request.addProperty("username", user.getUsername());
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		do{
			events.add(xmlConverter.constructEventFromNode(doc.getFirstChild()));

		} while(false);

		return events;
	}

	@Override
	public ArrayList<Event> getAppointments(User user, int count) throws ConnectException{
		ArrayList<Event> events = new ArrayList<Event>();
		Request request = new Request("appointments", Request.EVENT);
		request.addProperty("username", user.getUsername());
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		do{
			events.add(xmlConverter.constructEventFromNode(doc.getFirstChild()));

		} while(false);

		return events;
	}

	@Override
	public ArrayList<Notification> getNotifications(User user,
			boolean unreadOnly, int count) throws ConnectException{
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		Request request = new Request("notificationslimited", Request.EVENT);
		request.addProperty("username", user.getUsername());
		request.addProperty("undreadonly", Boolean.toString(unreadOnly));
		request.addProperty("amount", String.valueOf(count));
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		do{
			notifications.add(xmlConverter.constructNotificationFromNode(doc.getFirstChild()));

		} while(false);

		return notifications;
	}

	@Override
	public ArrayList<Event> getEvents(User user, Date from, Date to) throws ConnectException{
		ArrayList<Event> events = new ArrayList<Event>();
		Request request = new Request("eventsbydate", Request.EVENT);
		request.addProperty("username", user.getUsername());
		request.addProperty("startdate", from.toString());
		request.addProperty("enddate", to.toString());
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		do{
			events.add(xmlConverter.constructEventFromNode(doc.getFirstChild()));

		} while(false);

		return events;
	}

	@Override
	public ArrayList<Event> getMeetings(User user, Date from, Date to) throws ConnectException{
		ArrayList<Event> events = new ArrayList<Event>();
		Request request = new Request("meetingsbydate", Request.EVENT);
		request.addProperty("username", user.getUsername());
		request.addProperty("startdate", from.toString());
		request.addProperty("enddate", to.toString());
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		do{
			events.add(xmlConverter.constructEventFromNode(doc.getFirstChild()));

		} while(false);

		return events;
	}

	@Override
	public ArrayList<Event> getAppointments(User user, Date from, Date to) throws ConnectException{
		ArrayList<Event> events = new ArrayList<Event>();
		Request request = new Request("appoinmentsbydate", Request.EVENT);
		request.addProperty("username", user.getUsername());
		request.addProperty("startdate", from.toString());
		request.addProperty("enddate", to.toString());
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		do{
			events.add(xmlConverter.constructEventFromNode(doc.getFirstChild()));

		} while(false);

		return events;
	}

	@Override
	public ArrayList<Notification> getNotifications(User user,
			boolean unreadOnly, Date from, Date to) throws ConnectException{
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		Request request = new Request("notifications", Request.EVENT);
		request.addProperty("username", user.getUsername());
		request.addProperty("startdate", from.toString());
		request.addProperty("enddate", to.toString());
		request.addProperty("undreadonly", Boolean.toString(unreadOnly));
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		do{
			notifications.add(xmlConverter.constructNotificationFromNode(doc.getFirstChild()));

		} while(false);

		return notifications;
	}

	private class NotificationListener extends Thread {
		public void run() {
			while(true) {
				try {
					String result = (String) input.readObject();
					Document doc = xmlConverter.StringToDOMDocument(result);
					gui.handleNotifications(xmlConverter.constructNotificationFromNode(doc.getFirstChild()));
				}
				catch(IOException e) {
				}
				catch(ClassNotFoundException e) {
					
				}

			}
		}
	}

}
