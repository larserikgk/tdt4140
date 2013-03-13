package client.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;

public class ServerConnector {
	private Socket socket;
	private Object caller;
	private String url;
	private String username;
	private int port;
	private InputStream input;
	private OutputStream output;
	
	public ServerConnector(Properties settings, String username) {
		this.url = settings.getProperty(url);
		this.username = username;
		this.port = port;
	}
	
	public boolean start() {
		try {
			socket = new Socket(url, port);
		} 
		catch(Exception ec) {
			return false;
		}



		try
		{
			input  = socket.getInputStream();
			output = socket.getOutputStream();
		}
		catch (IOException eIO) {
			return false;
		}

		new ListenFromServer().start();
		try
		{
//			output.writeObject(username);
		}
		catch (IOException eIO) {
			return false;
		}
		return true;
	}


	

	
	private class ListenFromServer extends Thread {
		public void run() {
			while(true) {
				try {
					
				}
				catch(IOException e) {
					
				}
				
			}
		}
	}

}
