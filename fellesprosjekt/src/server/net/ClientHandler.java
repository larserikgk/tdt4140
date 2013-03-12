package server.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import server.db.SqlConnector;
import server.logic.Server;


public class ClientHandler implements Runnable{
	private Socket socket;
	private OutputStream output;
	private InputStream input;
	private Server server;
	private String username;
	private boolean keepGoing = true;
	private SqlConnector database;
	
	
	public ClientHandler(Socket socket, Server server) {
		// TODO Auto-generated constructor stub
	}

	public void run() {
		try{
			output = socket.getOutputStream();
			input  = socket.getInputStream();
//			username = (String) input.readObject();

			while(keepGoing){

				
			}
		}
		catch(IOException e) {
			//TODO exceptions
		} 

	}
}
