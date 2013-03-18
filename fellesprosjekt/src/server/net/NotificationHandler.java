package server.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import server.db.SqlConnector;
import server.logic.Server;

import common.models.Request;


public class NotificationHandler implements Runnable{
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Server server;
	private String username;
	private boolean keepGoing = true;
	private SqlConnector database;
	private Properties settings;

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

	private String handleRequest(Request request) {
		return null;

	}

	private void notifyConnectedUsers() {

	}

	private void kill() throws IOException {
		output.close();
		input.close();
		socket.close(); 
	}

}
