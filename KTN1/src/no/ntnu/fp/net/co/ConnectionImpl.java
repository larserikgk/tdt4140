/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebj�rn Birkeland and Stein Jakob Nordb�
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */

public class ConnectionImpl extends AbstractConnection {

	public final int ATTEMPTS = 2;
	
	/** Keeps track of the used ports for each server port. */
	private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
	/**
	 * Initialise initial sequence number and setup state machine.
	 * 
	 * @param myPort
	 *            - the local port to associate with this connection
	 */
	public ConnectionImpl(int myPort) 
	{
		super();
		usedPorts.put(myPort, true);
		this.myPort = myPort;
		this.myAddress = getIPv4Address();
	}

	private String getIPv4Address() {
		try {
			System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());
			return InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}

	/**
	 * Establish a connection to a remote location.
	 * 
	 * @param remoteAddress
	 *            - the remote IP-address to connect to
	 * @param remotePort
	 *            - the remote portnumber to connect to
	 * @throws IOException
	 *             If there's an I/O error.
	 * @throws java.net.SocketTimeoutException
	 *             If timeout expires before connection is completed.
	 * @see Connection#connect(InetAddress, int)
	 */
	public void connect(InetAddress remoteAddress, int remotePort) throws IOException,SocketTimeoutException {
				
		if(state!=State.CLOSED)
			throw new IllegalStateException("VI er av en aller annet teit grunn ikke closed");
		
		//det vi skal koble oss på. 
		
		this.remoteAddress = remoteAddress.getHostAddress();   
		this.remotePort = remotePort;
		this.myAddress = getIPv4Address(); 
		
		//opprette en syn vi skal sende. 
		KtnDatagram syn = constructInternalPacket(Flag.SYN);		
		KtnDatagram synAck = null;		
		
		
		//vi sender en syn, og mottar synAck. 
		synAck = sendingDurr(syn, State.CLOSED, State.SYN_SENT);
		System.out.println("STATE: " + this.state);
		
		//første mottat skal være SYN_ACK
		if(synAck == null || synAck.getFlag()!= Flag.SYN_ACK)
			throw new IOException("Flag er ikke SYN_ACK" + synAck.getFlag());
		
		this.lastValidPacketReceived = synAck;
		System.out.println("synAck Port" + synAck.getSrc_port());
		this.remotePort = synAck.getSrc_port();
		

		
		//sender ACK på SYN_ACK vi mottokk. 
		try {
			sendAck(synAck, false); //innebygd 3 forsøk. Tror jeg.
			state = State.ESTABLISHED; 
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
	}
	
	//første gang skal ack være SYN_ACK
	// datagram er SYN
	public KtnDatagram sendingDurr(KtnDatagram datagram, State before, State after)
	{
		//pakken vi mottar og skal sjekke. 
		KtnDatagram ack = null;
		
		int attempts = ATTEMPTS;
		
		//state på state
		while(attempts-- > 1)
		{
			try{				
				this.state = before;				
				simplySendPacket(datagram);				
				this.state = after;			
				
			}
			catch(Exception e)
			{
				e.printStackTrace(); 
			}
		}
		int x = ATTEMPTS;
		while(!isValid(ack) && x++ < 5)
		{
			try {
				ack = receiveAck();
			} catch (EOFException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
		return ack;			
	}
	
	public void sendAckDurr(KtnDatagram datagram) throws Exception 
	{
		if(datagram.getFlag()==Flag.ACK)
			throw new IllegalArgumentException("Flag:\t"+datagram.getFlag());
		
		int attempts = ATTEMPTS;
		
		while(attempts-- > 0)
		{
			try{
				System.out.println("Teit test.");
				System.out.println("flagget skal ikke være SYN: " + (datagram.getFlag()==Flag.SYN));
				sendAck(datagram, datagram.getFlag()==Flag.SYN);
				return;
			}
			catch(ConnectException c)
			{
				System.out.println("liten penis");
			}
			catch(IOException e)
			{
				continue;
			}
			
		}
		System.out.println("chill pill");
		throw new Exception();
	}

	/**
	 * Listen for, and accept, incoming connections.
	 * 
	 * @return A new ConnectionImpl-object representing the new connection.
	 * @see Connection#accept()
	 */
	public Connection accept() throws IOException, SocketTimeoutException {
		
		if(state!=State.CLOSED)
			throw new IllegalStateException("Vi står ikke i closed");
		
		//Server er nå i Listen
		state = State.LISTEN;
		
		//synen vi mottar
		KtnDatagram syn = null;
		
		
		while(!isValid(syn))
		{
			try{
				syn = receivePacket(true);
			}catch(Exception e){
				e.printStackTrace(); 
			}
		}
		
		//den som skal være i SYn_receive skal egentlig være en annen instans.
		
		
		ConnectionImpl connection = new ConnectionImpl(getNewPort());
		connection.remoteAddress = syn.getSrc_addr(); 
		connection.remotePort = syn.getSrc_port(); // Enorm sikkerthetsrisiko 
		connection.myAddress = syn.getDest_addr();
		
		connection.state = State.LISTEN; 
		connection.state = State.SYN_RCVD;

		KtnDatagram ack = null;
		try{
			int attempts = ATTEMPTS;
			
			while(!connection.isValid(ack) && attempts-- > 0)
			{
				connection.sendAck(syn, true); //sender SYN_ACK
				ack = connection.receiveAck();
			}
		}catch(Exception e)
		{
			e.printStackTrace(); 
		}
		
		state = State.CLOSED; //STENGER
		
		if(connection.isValid(ack))
		{			
			connection.state = State.ESTABLISHED;
			connection.lastValidPacketReceived = ack;
			return connection;
		}
		else 
			throw new IOException();
	}
	
	public static int getNewPort()
	{
		for(int i = 2331; i <= 9999; i++)
			if(!usedPorts.containsValue(i))
			{
				usedPorts.put(i, true); 
				return i;
			}
		return -1;
	}

	/**
	 * Send a message from the application.
	 * 
	 * @param msg
	 *            - the String to be sent.
	 * @throws Exception
	 *             If no connection exists.
	 * @throws IOException
	 *             If no ACK was received.
	 * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
	 * @see no.ntnu.fp.net.co.Connection#send(String)
	 */
	public void send(String msg) throws ConnectException, IOException {
		if(state!=State.ESTABLISHED)
			return;
		
		KtnDatagram packet 	= constructDataPacket(msg);
		KtnDatagram ack		= null;
		
		int attempts = ATTEMPTS;
		
		while(attempts-- > 1)
		{
			if(isValid(ack = sendDataPacketWithRetransmit(packet))) 
			{
				lastValidPacketReceived = ack;
				//lastDataPacketSent = packet;
				return;
			}
		}
		
		state= State.CLOSED;
		throw new IOException();
	}

	/**
	 * Wait for incoming data.
	 * 
	 * @return The received data's payload as a String.
	 * @see Connection#receive()
	 * @see AbstractConnection#receivePacket(boolean)
	 * @see AbstractConnection#sendAck(KtnDatagram, boolean)
	 */
	public String receive() throws ConnectException, IOException {
		if(state != State.ESTABLISHED)
			throw new IllegalStateException("Error i: receive");
		
		
		KtnDatagram packet = null;
		
		
		try{			
			packet = receivePacket(false);
		}
		catch(EOFException e)
		{}	

		if(isValid(packet))
		//if(isValid(packet) && packet.getSeq_nr()==lastValidPacketReceived.getPayloadAsBytes().length+lastValidPacketReceived.getSeq_nr())
		{

			if(packet.getFlag() == Flag.FIN)
			{
				
				System.out.println("jungelSwag");
				disconnectRequest = packet; 
				
				close(); 
				return "avsluttet"; 
			}
			
			lastValidPacketReceived = packet;
			try {
				sendAck(packet, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return (String)packet.getPayload();
		}
		try {
			sendAck(lastValidPacketReceived, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return receive();
	}

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	  public void close() { //mulig begge parter timer ut om begge kaller close samtidig
	   
		  if(this.state != state.ESTABLISHED)
		  {
			  System.out.println("vi er ikke establisja");
			  return;
		  }
		  
		  KtnDatagram fin = constructInternalPacket(Flag.FIN); 
		  KtnDatagram ack = null;		 
		  
		  if(disconnectRequest == null)
		  {
			  try {
				simplySendPacket(fin);
				this.state = State.FIN_WAIT_1;
				
				while(!isValid(ack))
					ack = receiveAck();				
				
			} catch (ClException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			 
		  }
		  else
		  {
			  try {
				System.out.println("jungelPenis");
				sendAck(disconnectRequest, false);
				this.state = State.CLOSE_WAIT; 
				Thread.sleep(100);
				simplySendPacket(fin);
				this.state = state.LAST_ACK; 
				while(!isValid(ack))
					ack = receiveAck(); 
				
				this.state = state.CLOSED; 
				
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			  
		  }
			
		 
	  }

	/**
	 * Test a packet for transmission errors. This function should only called
	 * with data or ACK packets in the ESTABLISHED state.
	 * 
	 * @param packet
	 *            Packet to test.
	 * @return true if packet is free of errors, false otherwise.
	 */
	protected boolean isValid(KtnDatagram packet) {
		if(packet==null)
		{
			return false;
		}
		if(packet.getChecksum() != packet.calculateChecksum())
		{
			return false;
		}
		
		boolean addr, port;
		
		
		addr = packet.getSrc_addr().equals(remoteAddress);
		port = packet.getSrc_port() == remotePort;
		
		switch(state)
		{
		case CLOSED: 		return false;  
		case TIME_WAIT: 	return false; 
		case CLOSE_WAIT: 	return false; //forandret 
		case FIN_WAIT_2: 	return addr && port && packet.getFlag()==Flag.FIN;
		case LISTEN: 		return packet.getFlag()==Flag.SYN; // return true hvis SYN.
		case ESTABLISHED: 	return 		((packet.getFlag() == Flag.NONE) && (packet.getSeq_nr()>lastValidPacketReceived.getSeq_nr()) 
				 						|| //eller
				 						//(packet.getFlag() == Flag.ACK && packet.getAck() == (lastDataPacketSent.getSeq_nr() + lastDataPacketSent.getPayloadAsBytes().length))
				 						(packet.getFlag() == Flag.ACK)
				 						|| //eller
										(packet.getFlag() == Flag.FIN)
										) //og
										&& addr && port
				 						;		 							
		case LAST_ACK:  	return addr && port && packet.getFlag()==Flag.ACK;
		case FIN_WAIT_1:    return addr && port && packet.getFlag()==Flag.ACK;
		case SYN_RCVD: 		return addr && port && packet.getFlag()==Flag.ACK;
		case SYN_SENT: 		return packet.getFlag()==Flag.SYN_ACK;
		default:			return false;
		}
	}
}
