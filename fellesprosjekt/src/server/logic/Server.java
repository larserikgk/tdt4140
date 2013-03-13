package server.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import server.net.ClientHandler;

public class Server {
	private ArrayList<ClientHandler> connectedClients;
	private int port;
	private boolean keepGoing;
	private Properties settings;

	public Server(int port) {
		settings.setProperty("port", Integer.toString(port));
		this.port = port;
		connectedClients = new ArrayList<ClientHandler>();
		
	}
	
	public void start() {
		keepGoing = true;
		try 
		{
			ServerSocket serverSocket = new ServerSocket(port);

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
}
