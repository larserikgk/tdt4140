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
			else if(request.equals("user")) {

			}

			break;
		case Request.EVENT:
			if(request.getQuery().equals("events")) {
				ArrayList<Event> events = database.getAllEvent(new User(request.getPropety("username"),null,null));

				Document doc = xmlConverter.getNewDocument();
				for (int j = 0; j < events.size(); j++) {
					xmlConverter.eventToDOMElement(events.get(j), doc, null, false);
				}
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.equals("appointments")) {
				ArrayList<Event> appointments = database.getAllEvent(new User(request.getPropety("username"),null,null));

				Document doc = xmlConverter.getNewDocument();
				for (int k = 0; k < appointments.size(); k++) {
					if(!appointments.get(k).isMeeting())
						xmlConverter.eventToDOMElement(appointments.get(k), doc, null, false);
				}
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.equals("meetings")) {
				ArrayList<Event> meetings = database.getAllEvent(new User(request.getPropety("username"),null,null));

				Document doc = xmlConverter.getNewDocument();
				for (int l = 0; l < meetings.size(); l++) {
					if(meetings.get(l).isMeeting())
						xmlConverter.eventToDOMElement(meetings.get(l), doc, null, false);
				}
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.equals("eventsbydate")) {
				ArrayList<Event> events = database.getAllEvent(new User(request.getPropety("username"),null,null));

				Document doc = xmlConverter.getNewDocument();
				for (int m = 0; m < events.size(); m++) {
					if(events.get(m).getStart().compareTo(new Date(request.getPropety("startdate"))) >= 0 && 
							events.get(m).getEnd().compareTo(new Date(request.getPropety("enddate"))) <= 0)
						xmlConverter.eventToDOMElement(events.get(m), doc, null, false);
				}
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.equals("appointmentsbydate")) {
				ArrayList<Event> appointments = database.getAllEvent(new User(request.getPropety("username"),null,null));

				Document doc = xmlConverter.getNewDocument();
				for (int i = 0; i < appointments.size(); i++) {
					if(appointments.get(i).getStart().compareTo(new Date(request.getPropety("startdate"))) >= 0 && 
							appointments.get(i).getEnd().compareTo(new Date(request.getPropety("enddate"))) <= 0 &&
							!appointments.get(i).isMeeting())
						xmlConverter.eventToDOMElement(appointments.get(i), doc, null, false);
				}
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.equals("meetingsbydate")) {
				ArrayList<Event> appointments = database.getAllEvent(new User(request.getPropety("username"),null,null));

				Document doc = xmlConverter.getNewDocument();
				for (int i = 0; i < appointments.size(); i++) {
					if(appointments.get(i).getStart().compareTo(new Date(request.getPropety("startdate"))) >= 0 && 
							appointments.get(i).getEnd().compareTo(new Date(request.getPropety("enddate"))) <= 0 &&
							appointments.get(i).isMeeting())
						xmlConverter.eventToDOMElement(appointments.get(i), doc, null, false);
				}
				response = xmlConverter.DOMDocumentToString(doc);
			}
			break;
		case Request.NOTIFICATION:
			if(request.getQuery().equals("notifications")) {
				ArrayList<Notification> notifications = database.getAllNotifications(new User(request.getPropety("username"),null,null));
						
				Document doc = xmlConverter.getNewDocument();
				for (int i = 0; i < notifications.size(); i++) {
					xmlConverter.notificationToDOMElement(notifications.get(i), doc, null, false);
				}
				
				response = xmlConverter.DOMDocumentToString(doc);
			}
			else if(request.equals("notificationslimited")) {
//				Document doc = xmlConverter.getNewDocument();
//				xmlConverter.
//				response = xmlConverter.DOMDocumentToString(doc);
			}
			break;
		case 4:

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
