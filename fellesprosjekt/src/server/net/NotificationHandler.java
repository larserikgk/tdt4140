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
		this.settings = settings;
		database = new SqlConnector(this.settings);
	}

	public void run() {
		try{
			output = new ObjectOutputStream(socket.getOutputStream());
			input  = new ObjectInputStream(socket.getInputStream());
			Request request = (Request) input.readObject();
			username = (String) input.readObject();


			while(keepGoing){
				request = (Request) input.readObject();

				switch(request.getType()) {
				case Request.LOGOUT:
					keepGoing = false;
					kill();
					break;

				default :
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


	public void notifyUser(Notification notification) {
		Document doc = xmlConverter.getNewDocument();
		xmlConverter.notificationToDOMElement(notification, doc, doc.getDocumentElement(), false);
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

	private void kill() throws IOException {
		output.close();
		input.close();
		socket.close(); 
	}

}
