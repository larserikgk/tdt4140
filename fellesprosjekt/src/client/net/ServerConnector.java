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
	
	/*
	Trenger følgende metoder:
	
	void deleteEvent(Event event)
		om eventet faktisk slettes
		eller bare relasjonen til deltagerne vet jeg ikke.
		viktigste at kobling til hver deltager (og admin) slettes.
		Ser ut som denne også må implementeres i SqlConnector 
	
	User login(String username, String password)
		som i SqlConnector
		
	ArrayList<User> getParticipants(Event event)
		
	void addEvent(Event event)
	
	void editEvent(Event event)
		får inn det nye eventet med likt event_id som det som skal endres
	*/
	
	
	public ServerConnector(Properties settings, String username) {
		this.url = settings.getProperty("url");
		this.username = username;
		this.handlerPort = 1600;
		this.pushPort = 1601;
		this.xmlConverter = new XMLConverter();
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

		events = xmlConverter.constructEventListFromNode(doc.getFirstChild());
		for (Event e : events) {
			System.out.println("Serverconnector\t"+e.getId());
		}
		return events;
	}

	@Override
	public ArrayList<Event> getMeetings(User user, int count) throws ConnectException{
		ArrayList<Event> events = new ArrayList<Event>();
		Request request = new Request("meetings", Request.EVENT);
		request.addProperty("username", user.getUsername());
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		events = xmlConverter.constructEventListFromNode(doc);

		return events;

	}

	@Override
	public ArrayList<Event> getAppointments(User user, int count) throws ConnectException{
		ArrayList<Event> events = new ArrayList<Event>();
		Request request = new Request("appointments", Request.EVENT);
		request.addProperty("username", user.getUsername());
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		
		events = xmlConverter.constructEventListFromNode(doc);

		return events;
	}

	@Override
	public ArrayList<Notification> getNotifications(User user,
			boolean unreadOnly, int count) throws ConnectException{
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		Request request;
		if (unreadOnly)
			request = new Request("notificationslimited", Request.NOTIFICATION);
		else
			request = new Request("notifications", Request.NOTIFICATION);
		request.addProperty("username", user.getUsername());
		request.addProperty("undreadonly", Boolean.toString(unreadOnly));
		request.addProperty("amount", String.valueOf(count));
		String result = sendRequest(request);
		
		System.out.println("result i serconn allnotif: "+result);
		Document doc = xmlConverter.StringToDOMDocument(result);
		
		notifications = xmlConverter.constructNotificationListFromNode(doc);

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
		events = xmlConverter.constructEventListFromNode(doc);

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
		events = xmlConverter.constructEventListFromNode(doc);

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
		events = xmlConverter.constructEventListFromNode(doc);

		return events;
	}

	@Override
	public ArrayList<Notification> getNotifications(User user,
			boolean unreadOnly, Date from, Date to) throws ConnectException{
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		Request request = new Request("notifications", Request.EVENT);
		request.addProperty("username", user.getUsername());
		request.addProperty("startdate", from.toString());
		request.addProperty("undreadonly", Boolean.toString(unreadOnly));
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		notifications = xmlConverter.constructNotificationListFromNode(doc);

		return notifications;
	}
	
	@Override
	public void deleteEvent(Event event) throws ConnectException{
		Request request = new Request("delete", Request.EVENT);
		request.addProperty("id", Integer.toString(event.getId()));
		sendRequest(request);
	}

	@Override
	public void addEvent(Event event) throws ConnectException{
		ArrayList<User> users = event.getParticipants();
		Document doc = xmlConverter.getNewDocument();
		xmlConverter.userListToDOMElement(users, doc, null);
		
		
		Request request = new Request("add", Request.EVENT);
		request.setUsers(xmlConverter.DOMDocumentToString(doc));
		request.addProperty("name", event.getName());
		request.addProperty("start", Long.toString(event.getStart().getTime()));	
		request.addProperty("end", Long.toString(event.getEnd().getTime()));
		request.addProperty("location", event.getLocation());
		request.addProperty("description", event.getDescription());
		request.addProperty("location", event.getLocation());
		if (event.getRoom() != null) request.addProperty("roomname", event.getRoom().getName());
		request.addProperty("admin", event.getAdmin().getUsername());
		sendRequest(request);
		
	}

	@Override
	public void editEvent(Event event) throws ConnectException{
		ArrayList<User> users = event.getParticipants();
		Document doc = xmlConverter.getNewDocument();
		xmlConverter.userListToDOMElement(users, doc, null);
		
		
		Request request = new Request("edit", Request.EVENT);
		request.setUsers(xmlConverter.DOMDocumentToString(doc));
		request.addProperty("eventid", Integer.toString(event.getId()));
		request.addProperty("name", event.getName());
		request.addProperty("start", event.getStart().getTime()+"");
		request.addProperty("end", event.getStart().getTime()+"");
		request.addProperty("location", event.getLocation());
		request.addProperty("description", event.getDescription());
		request.addProperty("location", event.getLocation());
		request.addProperty("roomname", event.getRoom().getName());
		request.addProperty("admin", event.getAdmin().getUsername());
		sendRequest(request);
		
	}

	@Override
	public ArrayList<User> getAllUsers() throws ConnectException {
		Request request = new Request("getall", Request.USER);
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		return xmlConverter.constructUserListFromNode(doc.getFirstChild());
	}
	
	@Override
	public ArrayList<Room> getAllRooms() throws ConnectException {
		ArrayList<Room> rooms = new ArrayList<Room>();
		Request request = new Request("getall", Request.ROOM);
		String result = sendRequest(request);

		Document doc = xmlConverter.StringToDOMDocument(result);
		rooms = (xmlConverter.constructRoomListFromNode(doc.getFirstChild()));

		return rooms;
	}
	
	@Override
	public ArrayList<Room> getAllAvailableRooms(Event event)
			throws ConnectException {
		ArrayList<Room> rooms = new ArrayList<Room>();
		Request request = new Request("getavailable", Request.ROOM);
		request.addProperty("starttime", String.valueOf(event.getStart().getTime()));
		request.addProperty("endtime", String.valueOf(event.getEnd().getTime()));
		String result = sendRequest(request);
		System.out.println(result);

		Document doc = xmlConverter.StringToDOMDocument(result);
		rooms = (xmlConverter.constructRoomListFromNode(doc.getFirstChild()));

		return rooms;
	}
	
	public void updateStatus(ParticipantStatus participantStatus) throws ConnectException {
		Request request = new Request(Request.STATUS, participantStatus);
		sendRequest(request);
	}


	private class NotificationListener extends Thread {
		public void run() {
			System.out.println("notif listener initialized");
			while(true) {
				try {
					String result = (String) pushInput.readObject();
					Document doc = xmlConverter.StringToDOMDocument(result);
					System.out.println("next: gui.handlenotif");
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
