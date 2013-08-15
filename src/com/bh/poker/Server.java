package com.bh.poker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server extends Thread {
	
	public static enum GameState {
		JOINING, IN_GAME, BETWEEN_TURNS;
	}
	
	private List<ServerPlayer> players;
	
	public HashMap<String, Object> config = new HashMap<String, Object>();
	
	private DatagramSocket socket;
	private final int port = 8008;
	
	public static boolean running = false;
	
	public static GameState state;
	
	public Server() {
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		running = true;
		players = new ArrayList<ServerPlayer>();
		
		config.put("max_players", 4);
		config.put("host_player", null);
	}
	
	public List<ServerPlayer> getPlayers() {
		return players;
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
			System.out.println(new String(packet.getData()).trim());
		}
	}
	
	public void sendtoall(String msg) {
		sendtoall(msg, null);
	}
	
	public void sendtoall(String msg, ServerPlayer p) {
		DatagramPacket packet;
		byte[] data = msg.getBytes();
		
		for(ServerPlayer player : players) {
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
		for(ServerPlayer p : players) {
			if(p.getId() >= id) {
				id = p.getId() + 1;
			}
			if(id == (Integer)config.get("max_players")) {
				id = -1;
				break;
			}
		}
		return id;
	}
}
