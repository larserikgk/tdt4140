package server.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

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

	private String handleRequest(Request request) {
		String response = null;
		switch(request.getType()){
		case Request.USER:
			if(request.getQuery().equals("login")) {

			}
			else if(request.equals("user")) {

			}

			break;
		case Request.EVENT:
			if(request.getQuery().equals("events")) {

			}
			else if(request.equals("appointments")) {

			}
			else if(request.equals("meetings")) {

			}
			else if(request.equals("eventsbydate")) {

			}
			else if(request.equals("appointmentsbydate")) {

			}
			else if(request.equals("meetingsbydate")) {

			}
			break;
		case Request.NOTIFICATION:
			if(request.getQuery().equals("notifications")) {

			}
			else if(request.equals("notificationslimited")) {

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
