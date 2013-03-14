package client.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;

import common.models.*;

public class ServerConnector {
	private Socket socket;
	private Object caller;
	private String url;
	private String username;
	private int port;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
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
			System.err.println("Error connectiong to server:" + ec);
			return false;
		}

		try
		{
			input  = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			System.err.println("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		new ListenFromServer().start();
		try
		{
			output.writeObject(username);
		}
		catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		return true;
	}


	/** Writes parameter to command line or GUI, depending on mode. 
	 * This primarily send error messages to the server.
	 * 
	 * @param req
	 */
	private void display(String req) {
		caller.handleMessage(req + "\n");
	}

	/**Transmits the request via outputstream to the server.
	 * 
	 * @param req
	 */
	void sendRequest(Request req) {
		try {
			sOutput.writeObject(req);
			sOutput.flush();
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	/**Closes all streams and shuts down the socket.
	 * 
	 */
	public void disconnect() {
		try {
			output.writeObject(new Request(Request.LOGOUT));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try { 
			if(input != null) input.close();
		}
		catch(Exception e) { /** Ignore errors */}
		try {
			if(output != null) output.close();
		}
		catch(Exception e) {/** Ignore errors */} 
		try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {/** Ignore errors */} 

		if(caller != null)
			caller.connectionFailed();

	}


	

	
	private class ListenFromServer extends Thread {
		public void run() {
			while(true) {
				try {
					String result = (String) input.readObject();			
					caller.handleResponse(result);
				}
				catch(IOException e) {
					if(caller != null) 
						caller.connectionFailed();
					break;
				}
				
			}
		}
	}

}
