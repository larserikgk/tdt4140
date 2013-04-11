/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
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
 * @author Sebjørn Birkeland and Stein Jakob Nordbø
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {

	/** Keeps track of the used ports for each server port. */
	private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
	/**
	 * Initialise initial sequence number and setup state machine.
	 * 
	 * @param myPort
	 *            - the local port to associate with this connection
	 */
	public ConnectionImpl(int myPort) {
		this.myPort = myPort;
		try {
			this.myAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		KtnDatagram syn = new KtnDatagram();
		KtnDatagram synAck = new KtnDatagram();
		this.remoteAddress = remoteAddress.getHostAddress();
		this.remotePort = remotePort;
		syn.setDest_addr(this.remoteAddress);
		syn.setDest_port(remotePort);
		syn.setFlag(Flag.SYN);
		syn.setSrc_addr(myAddress);
		syn.setSrc_port(myPort);
		try {
			simplySendPacket(syn);
		} catch (ClException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(true) {
			synAck = receivePacket(true);
			if(synAck != null) {
				if(synAck.getFlag()==Flag.SYN_ACK && synAck.getSrc_addr().equals(remoteAddress.toString()) && synAck.getSrc_port() == remotePort){
					sendAck(synAck, false);
					return;
				}
			}

		}
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
		KtnDatagram packet = new KtnDatagram();
		packet.setPayload(msg);
		packet.setDest_addr(remoteAddress);
		packet.setDest_port(remotePort);
		packet.setSrc_addr(myAddress);
		packet.setSrc_port(myPort);
		packet.setFlag(Flag.NONE);
		System.out.println((int)msg.getBytes()[0]); 
		packet.setSeq_nr((int)msg.getBytes()[0]); 
		try {
			simplySendPacket(packet);
		} catch (ClException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		KtnDatagram packet = new KtnDatagram();
		while(true) {
			packet =  receivePacket(false);
			if(packet.getSrc_addr().equals(remoteAddress) && packet.getSrc_port() == remotePort && packet.getFlag() == Flag.NONE) {
				sendAck(packet, false);
				return (String) packet.getPayload();
			}
		}
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
		return true;
	}
}
