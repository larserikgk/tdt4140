package server.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.w3c.dom.Document;

import common.models.*;
import common.models.Notification.NotificationType;
import common.util.XMLConverter;

import server.db.SqlConnector;
import server.logic.Server;


public class ClientHandler implements Runnable{
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Server server;
	private String username;
	private boolean keepGoing = true;
	private SqlConnector database;
	private Properties settings;
	XMLConverter xmlConverter;

	public ClientHandler(Socket socket, Server server, Properties settings) {
		this.socket = socket;
		this.server = server;
		this.settings = settings;
		database = new SqlConnector(this.settings);
		this.xmlConverter = new XMLConverter();
	}

	public void run() {
		try{
			output = new ObjectOutputStream(socket.getOutputStream());
			input  = new ObjectInputStream(socket.getInputStream());

			Request request;
			//Request request = (Request) input.readObject();
			username = (String) input.readObject();
			server.display("User" + username +" connected to Notifier");
			server.display("Client connected to Handler");


			while(keepGoing){
				request = (Request) input.readObject();

				switch(request.getType()) {
				case Request.LOGOUT:
					keepGoing = false;
					server.disconnect(username);
					kill();
					break;

				default :
					String result = handleRequest(request);
					output.writeObject(result);
					output.flush();
					break;
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	private String handleRequest(Request request) {
		String response = null;
		switch(request.getType()){
		case Request.USER:
			if(request.getQuery().equals("login")) {
				User user = database.login(request.getPropety("username"), request.getPropety("password"));
				Document doc = xmlConverter.getNewDocument();
				xmlConverter.userToDOMElement(user, doc, null);
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.getQuery().equals(("getall"))) {
				ArrayList<User> users = database.getAllUsers();

				Document doc = xmlConverter.getNewDocument();
				xmlConverter.userListToDOMElement(users, doc, null);
				response = xmlConverter.DOMDocumentToString(doc);
			}

			break;
		case Request.EVENT:
			if(request.getQuery().equals("events")) {
				ArrayList<Event> events = database.getAllEvent(new User(request.getPropety("username"),"",""));

				Document doc = xmlConverter.getNewDocument();
				xmlConverter.eventListToDOMElement(events, doc, null);
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.getQuery().equals(("appointments"))) {
				ArrayList<Event> events = database.getAllEvent(new User(request.getPropety("username"),"",""));
				ArrayList<Event> appointments = new ArrayList<Event>();
				
				Document doc = xmlConverter.getNewDocument();
				
				for (int k = 0; k < events.size(); k++) {
					if(!events.get(k).isMeeting())
						appointments.add(events.get(k));
				}
				xmlConverter.eventListToDOMElement(appointments, doc, null);
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.getQuery().equals(("meetings"))) {
				ArrayList<Event> events = database.getAllEvent(new User(request.getPropety("username"),"",""));
				ArrayList<Event> meetings = new ArrayList<Event>();
				
				Document doc = xmlConverter.getNewDocument();
				
				for (int k = 0; k < events.size(); k++) {
					if(!events.get(k).isMeeting())
						meetings.add(events.get(k));
				}
				xmlConverter.eventListToDOMElement(meetings, doc, null);
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.getQuery().equals(("eventsbydate"))) {
				ArrayList<Event> events = database.getAllEvent(new User(request.getPropety("username"),"",""));
				ArrayList<Event> correct = new ArrayList<Event>();
				
				Document doc = xmlConverter.getNewDocument();
				for (int m = 0; m < events.size(); m++) {
					if(events.get(m).getStart().compareTo(new Date(Long.parseLong(request.getPropety("startdate")))) >= 0 && 
							events.get(m).getEnd().compareTo(new Date(Long.parseLong(request.getPropety("enddate")))) <= 0)
						correct.add(events.get(m));
				}
				xmlConverter.eventListToDOMElement(correct, doc, null);
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.getQuery().equals(("appointmentsbydate"))) {
				ArrayList<Event> events = database.getAllEvent(new User(request.getPropety("username"),"",""));
				ArrayList<Event> appointments = new ArrayList<Event>();
				Document doc = xmlConverter.getNewDocument();
				for (int i = 0; i < events.size(); i++) {
					if(events.get(i).getStart().compareTo(new Date(Long.parseLong(request.getPropety("startdate")))) >= 0 && 
							events.get(i).getEnd().compareTo(new Date(Long.parseLong(request.getPropety("enddate")))) <= 0 &&
							!events.get(i).isMeeting())
						appointments.add(events.get(i));
				}
				xmlConverter.eventListToDOMElement(appointments, doc, null);
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.getQuery().equals(("meetingsbydate"))) {
				ArrayList<Event> events = database.getAllEvent(new User(request.getPropety("username"),null,null));
				ArrayList<Event> meetings = new ArrayList<Event>();

					
				Document doc = xmlConverter.getNewDocument();
				for (int i = 0; i < events.size(); i++) {
					if(events.get(i).getStart().compareTo(new Date(Long.parseLong(request.getPropety("startdate")))) >= 0 && 
							events.get(i).getEnd().compareTo(new Date(Long.parseLong(request.getPropety("enddate")))) <= 0 &&
									events.get(i).isMeeting())
						meetings.add(events.get(i));
				}
				xmlConverter.eventListToDOMElement(meetings, doc, null);
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.getQuery().equals(("delete"))) {
				database.removeEvent(Integer.parseInt(request.getPropety("id")));
			}
			else if(request.getQuery().equals(("add"))) {
				Document doc = xmlConverter.StringToDOMDocument(request.getUsers());
				ArrayList<User> users = xmlConverter.constructUserListFromNode(doc.getFirstChild());
				Event event = new Event(new User(request.getPropety("admin"), null), 0, new Date(Long.parseLong(request.getPropety("start"))), new Date(Long.parseLong(request.getPropety("end"))),
						request.getPropety("name"), request.getPropety("description"), request.getPropety("location"), 
						users, new Room(request.getPropety("roomname"),0));
				try {
					database.addFullEvent(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				for (int i = 0; i < request.getList().size(); i++) {
					User user = request.getList().get(i);
					server.notifyClient(user.getName(), new Notification(0, NotificationType.INVITATION, event.getDescription(), event));
					database.addNotification(new Notification(0, NotificationType.INVITATION, event.getDescription(), event));
				}
			}
			else if(request.getQuery().equals(("edit"))) {
				Document doc = xmlConverter.StringToDOMDocument(request.getUsers());
				ArrayList<User> users = xmlConverter.constructUserListFromNode(doc.getFirstChild());
				Event event = new Event(new User(request.getPropety("admin"), null), 0, new Date(Long.parseLong(request.getPropety("start"))), new Date(Long.parseLong(request.getPropety("end"))),
						request.getPropety("name"), request.getPropety("description"), request.getPropety("location"), 
						users, new Room(request.getPropety("roomname"),0));
				database.updateEvent(event);
				
				for (int i = 0; i < request.getList().size(); i++) {
					User user = request.getList().get(i);
					server.notifyClient(user.getName(), new Notification(0, NotificationType.EVENT_UPDATE, event.getDescription(), event));
					database.addNotification(new Notification(0, NotificationType.EVENT_UPDATE, event.getDescription(), event));
				}
			}
			break;
		case Request.NOTIFICATION:
			if(request.getQuery().equals("notifications")) {
				ArrayList<Notification> notifications = database.getAllNotifications(new User(request.getPropety("username"),"",""));
						
				Document doc = xmlConverter.getNewDocument();
				xmlConverter.notificationListToDOMElement(notifications, doc, null);
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.getQuery().equals(("notificationslimited"))) {
//				Document doc = xmlConverter.getNewDocument();
//				xmlConverter.
//				response = xmlConverter.DOMDocumentToString(doc);
			}
			break;
		case Request.ROOM:
			if(request.getQuery().equals("getall")) {
				ArrayList<Room> rooms = database.getAllRooms();
				Document doc = xmlConverter.getNewDocument();
				xmlConverter.roomListToDOMElement(rooms, doc, null);
				xmlConverter.constructRoomListFromNode(doc);
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.getQuery().equals("getavailable")) {
				ArrayList<Room> rooms = database.getAvailableRooms(new Event(new Date(Long.parseLong(request.getPropety("starttime"))),
						new Date(Long.parseLong(request.getPropety("endtime")))), "");
				Document doc = xmlConverter.getNewDocument();
				xmlConverter.roomListToDOMElement(rooms, doc, null);
				xmlConverter.constructRoomListFromNode(doc);
				response = xmlConverter.DOMDocumentToString(doc);
			}
			break;
		}
		return response;
	}



	private void kill() throws IOException {
		output.close();
		input.close();
		socket.close(); 
	}
}
