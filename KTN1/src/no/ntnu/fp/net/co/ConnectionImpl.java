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

	public final int ATTEMPTS = 10;
	
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
	public void connect(InetAddress remoteAddress, int remotePort) throws IOException,
	SocketTimeoutException {
		if(state!=State.CLOSED)
			throw new IllegalStateException();
		
		this.remoteAddress = remoteAddress.getHostAddress();
		this.remotePort = remotePort;
		
		KtnDatagram syn = constructInternalPacket(Flag.SYN);
		KtnDatagram synAck = null;		
		
		synAck = sendingDurr(syn, State.CLOSED, State.SYN_SENT);
		
		if(synAck==null)
			throw new IOException();
		
		this.lastValidPacketReceived = synAck;
		this.remotePort = synAck.getSrc_port();
		
		try {
			sendingAckDurr(synAck);
		} catch (Exception e1) {
			return;
		}
		
				
		int attempts = ATTEMPTS;
		
		while(attempts-- > 0)
		{
			try{
				sendAck(lastValidPacketReceived, false);
				state = State.ESTABLISHED;
			}
			catch(SocketException e)
			{
				continue;
			}
		}
		
	}
	
	public KtnDatagram sendingDurr(KtnDatagram datagram, State before, State after)
	{
		KtnDatagram ack = null;
		
		int attempts = ATTEMPTS;
		
		while(!isValid(ack) && attempts-- > 0)
		{
			try{
				this.state = before;
				
				simplySendPacket(datagram);
				
				this.state = after;
				
				ack = receiveAck();
			}
			catch(Exception e)
			{}
		}
		
		return ack;
			
	}
	
	public void sendingAckDurr(KtnDatagram datagram) throws Exception 
	{
		if(datagram.getFlag()==Flag.ACK)
			throw new IllegalArgumentException("Flag:\t"+datagram.getFlag());
		
		int attempts = ATTEMPTS;
		
		while(attempts-- > 0)
		{
			try{
				sendAck(datagram, false);
				return;
			}
			catch(IOException e)
			{
				continue;
			}
		}
		
		throw new Exception();
	}

	/**
	 * Listen for, and accept, incoming connections.
	 * 
	 * @return A new ConnectionImpl-object representing the new connection.
	 * @see Connection#accept()
	 */
	public Connection accept() throws IOException, SocketTimeoutException {
		while(true) {
			KtnDatagram syn = receivePacket(true);
			if(syn != null) {
				if(syn.getFlag() == Flag.SYN) {
					this.remoteAddress = syn.getSrc_addr();
					this.remotePort = syn.getSrc_port();
					
					sendAck(syn, true);
					while(true) {
						KtnDatagram ack = receivePacket(true);
						if(ack != null) {
							if(ack.getFlag() == Flag.ACK && ack.getSrc_addr().equals(remoteAddress) && ack.getSrc_port() == remotePort) {
								return this;
							}
						}

					}
				}
			}
		}
	}

	/**
	 * Send a message from the application.
	 * 
	 * @param msg
	 *            - the String to be sent.
	 * @throws ConnectException
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
		
		while(attempts-- > 0)
			if(isValid(ack = sendDataPacketWithRetransmit(packet)))
			{
				lastValidPacketReceived = ack;
				//lastDataPacketSent = packet;
				return;
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
			throw new IllegalStateException();
		
		KtnDatagram packet = null;
		
		try{
			packet = receivePacket(false);
		}
		catch(EOFException e)
		{}
		
		if(isValid(packet) && packet.getSeq_nr()==lastValidPacketReceived.getPayloadAsBytes().length+lastValidPacketReceived.getSeq_nr())
		{
			lastValidPacketReceived = packet;
			try {
				sendingAckDurr(packet);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return (String)packet.getPayload();
		}
		try {
			sendingAckDurr(lastValidPacketReceived);
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
	public void close() throws IOException {
		KtnDatagram fin = new KtnDatagram();
		KtnDatagram ack = new KtnDatagram();
		fin.setFlag(Flag.FIN);
		fin.setDest_addr(remoteAddress);
		fin.setDest_port(remotePort);
		try {
			simplySendPacket(fin);
		} catch (ClException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
			ack = receiveAck();
			fin = receivePacket(true);
			if(fin.getSrc_addr().equals(remoteAddress) && fin.getSrc_port() == remotePort && fin.getFlag() == Flag.ACK) {
				sendAck(fin, false);
				return;
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
			return false;
		if(packet.getChecksum() != packet.calculateChecksum())
			return false;
		
		boolean addr, port;
		
		addr = packet.getSrc_addr().equals(remoteAddress);
		port = packet.getSrc_port() == remotePort;
		
		switch(state)
		{
		case CLOSED: 		return false;
		case TIME_WAIT:
		case CLOSE_WAIT:
		case FIN_WAIT_2: 	return addr && port && packet.getFlag()==Flag.FIN;
		case LISTEN: 		return packet.getFlag()==Flag.SYN;
		case ESTABLISHED: 	return 	 ((packet.getFlag() == Flag.NONE || packet.getFlag() == Flag.ACK || packet.getFlag() == Flag.FIN)
										&& (packet.getFlag() == Flag.NONE || packet.getAck() == lastDataPacketSent.getSeq_nr())
										&& (packet.getFlag() == Flag.ACK || packet.getSeq_nr() > lastValidPacketReceived.getSeq_nr())
										&& addr && port);
		case LAST_ACK:
		case FIN_WAIT_1:
		case SYN_RCVD: 		return addr && port && packet.getFlag()==Flag.ACK;
		case SYN_SENT: 		return packet.getFlag()==Flag.SYN_ACK;
		default:			return false;
		}
	}
}
