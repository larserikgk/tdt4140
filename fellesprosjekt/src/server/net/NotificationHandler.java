package server.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import org.w3c.dom.Document;

import server.db.SqlConnector;
import server.logic.Server;

import common.models.Notification;
import common.models.Request;
import common.util.XMLConverter;


public class NotificationHandler implements Runnable{
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Server server;
	private String username;
	private boolean keepGoing = true;
	private SqlConnector database;
	private Properties settings;
	XMLConverter xmlConverter;

	public NotificationHandler(Socket socket, Server server, Properties settings) {
		this.socket = socket;
		this.settings = settings;
		this.server = server;
		database = new SqlConnector(this.settings);
		this.xmlConverter = new XMLConverter();
	}

	public void run() {
		try{
			output = new ObjectOutputStream(socket.getOutputStream());
			input  = new ObjectInputStream(socket.getInputStream());
			//Request request = (Request) input.readObject();
			username = (String) input.readObject();
			server.display("Client connected to Notifier");
		}
		catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}


	public void notifyUser(Notification notification) {
		Document doc = xmlConverter.getNewDocument();
		xmlConverter.notificationToDOMElement(notification, doc, null, false);
		String msg = xmlConverter.DOMDocumentToString(doc);
		try {
			output.writeObject(msg);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public void disconnect() {
		keepGoing = false;
		try {
			kill();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void kill() throws IOException {
		output.close();
		input.close();
		socket.close(); 
	}

}
