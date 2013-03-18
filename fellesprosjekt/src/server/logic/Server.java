package server.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
import server.net.ClientHandler;

public class Server {
	private ArrayList<ClientHandler> connectedClients;
	private boolean keepGoing;
	private Properties settings;

	public Server(int handlerPort, int pushPort) {
		settings.setProperty("handlerPort", Integer.toString(handlerPort));
		settings.setProperty("pushPort", Integer.toString(pushPort));
		settings.setProperty("url","");
		settings.setProperty("database", "");
		settings.setProperty("username", "");
		settings.setProperty("password", "");
		connectedClients = new ArrayList<ClientHandler>();

	}

	public void start() {
		keepGoing = true;
		try 
		{
			ServerSocket serverSocket = new ServerSocket(Integer.parseInt(settings.getProperty("handlerPort")));

			while(keepGoing) 
			{

				Socket socket = serverSocket.accept();				
				if(!keepGoing)
					break;
				ClientHandler t = new ClientHandler(socket,this,settings);
				connectedClients.add(t); 
				new Thread(t).start();
			}
			try {

			}
			catch(Exception e) {

			}
		}
		catch (IOException e) {

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
}
