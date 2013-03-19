package server.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
import server.net.ClientHandler;
import server.net.NotificationHandler;
import common.models.*;

public class Server {
	private ArrayList<NotificationHandler> connectedClients;
	private boolean keepGoing;
	private Properties settings;

	public Server(int handlerPort, int pushPort) {
		settings = new Properties();
		settings.setProperty("handlerPort", Integer.toString(handlerPort));
		settings.setProperty("pushPort", Integer.toString(pushPort));
		settings.setProperty("url","");
		settings.setProperty("database", "");
		settings.setProperty("username", "");
		settings.setProperty("password", "");
		connectedClients = new ArrayList<NotificationHandler>();

	}

	public void start() {
		keepGoing = true;
		try 
		{
			ServerSocket handlerSocket = new ServerSocket(Integer.parseInt(settings.getProperty("handlerPort")));
			ServerSocket pushSocket = new ServerSocket(Integer.parseInt(settings.getProperty("pushPort")));
			while(keepGoing) 
			{

				Socket hSocket = handlerSocket.accept();				
				if(!keepGoing)
					break;
				ClientHandler handler = new ClientHandler(hSocket,this,settings); 
				new Thread(handler).start();
				Socket pSocket = pushSocket.accept();				
				if(!keepGoing)
					break;
				NotificationHandler push = new NotificationHandler(pSocket,this,settings); 
				new Thread(push).start();
				connectedClients.add(push);
			}
		}
		catch (IOException e) {

		}
	}

	private void notifyClient(String username, Notification notification) {
		for (int i = 0; i < connectedClients.size(); i++) {
			if(connectedClients.get(i).getUsername() == username) {
				connectedClients.get(i).notifyUser(notification);
			}
		}
	}

	public void disconnect(String username) {
		for (int i = 0; i < connectedClients.size(); i++) {
			if(connectedClients.get(i).getUsername() == username) {
				connectedClients.get(i).disconnect();
			}
		}
	}

	protected void stop() {
		keepGoing = false;
		try {
			new Socket("localhost", Integer.parseInt(settings.getProperty("handlerPort")));
		}
		catch(Exception e) {
		}
	}

	public static void main(String[] args) {
		new Server(1600, 1601);
	}
}
