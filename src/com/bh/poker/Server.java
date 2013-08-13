package com.bh.poker;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Server implements Runnable {
	
	private DatagramSocket socket;
	private int port;
	
	public Server() {
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		
	}
}
