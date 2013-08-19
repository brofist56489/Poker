package com.bh.poker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client extends NetBase implements Runnable {

	private DatagramSocket socket;
	
	private static Thread clientThread;
	
	public static boolean running = false;
	private static InetAddress targetAddress;
	
	public Client() {
		super();
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		setAddress("localhost");
		running = true;
	}
	
	public static InetAddress getAddress() {
		return targetAddress;
	}
	
	public static void setAddress(String i) {
		try {
			targetAddress = InetAddress.getByName(i);
		} catch (UnknownHostException e) {
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null, "Server not found", Game.NAME, JOptionPane.ERROR_MESSAGE);
				}
			}.start();
		}
	}
	
	public void start() {
		if(clientThread != null)
			return;
		clientThread = new Thread(this, "CLIENT");
		clientThread.start();
	}
	
	public void stop() {
		running = false;
		clientThread = null;
	}
	
	public void run() {
		byte[] data;
		DatagramPacket packet;
		while(running) {
			data = new byte[4096];
			packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			PacketParser.parseClient(this, packet);
		}
	}

	public void send(String msg) {
		byte[] data = msg.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, targetAddress, NetBase.PORT);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
