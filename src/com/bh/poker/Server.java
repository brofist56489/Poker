package com.bh.poker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server extends NetBase implements Runnable {
	
	private DatagramSocket socket;
	
	public static boolean running = false;
	private static Thread serverThread;
	
	public static GameState state;
	
	public Server() {
		super();
		try {
			socket = new DatagramSocket(NetBase.PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		running = true;
		state = GameState.JOINING;
	}
	
	public void start() {
		if(serverThread != null)
			return;
		serverThread = new Thread(this, "SERVER");
		serverThread.start();
	}
	
	public void stop() {
		running = false;
		serverThread = null;
	}
	
	public void run() {
		byte[] data;
		DatagramPacket packet = null;
		
		while(running) {
			data = new byte[4096];
			packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			PacketParser.parseServer(this, packet);
		}
	}
	
	public void sendto(String msg, Player p) {
		byte[] data = msg.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, p.getIp(), p.getPort());
		
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendtoall(String msg) {
		sendtoall(msg, null);
	}
	
	public void sendtoall(String msg, Player p) {
		DatagramPacket packet;
		byte[] data = msg.getBytes();
		
		for(Player player : players) {
			if(p != player) {
				packet = new DatagramPacket(data, data.length, player.getIp(), player.getPort());
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getNextId() {
		int id = 0;
		for(Player p : players) {
			if(p.getId() >= id) {
				id = p.getId() + 1;
			}
			if(id == (Integer)config.get("max_players")) {
				id = -100;
				break;
			}
		}
		return id;
	}
}
