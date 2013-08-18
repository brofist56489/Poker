package com.bh.poker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Random;

public class Server extends NetBase implements Runnable {
	
	private DatagramSocket socket;
	
	public static boolean running = false;
	private static Thread serverThread;
	
	public static GameState state;
	private static Object var;
	
	private static HashMap<String, Card> dealtCards = new HashMap<String, Card>();
	
	public Server() {
		super();
		try {
			socket = new DatagramSocket(NetBase.PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		running = true;
		state = GameState.JOINING;
		var = null;
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
	
	public static void tick() {
		Server self = Game.server;
		
		switch(state) {
		case PRE_GAME:
		{
			String m = PacketHandler.START_GAME();
			self.sendtoall(m);
			state = GameState.IN_GAME;
		}
			break;
		case INIT_DEAL:
		{
			for(Player p : self.players) {
				Card c1 = getNewCard();
				Card c2 = getNewCard();
				
				dealtCards.put(p.getName() + "_1", c1);
				dealtCards.put(p.getName() + "_2", c2);
				
				
			}
		}
			break;
		default:
			break;
		}
	}
	
	private static Card getNewCard() {
		boolean works = false;
		Card card = new Card(true, -1, -1);
		Random r = new Random();
		while(!works) {
			card.setSuit(r.nextInt(4));
			card.setValue(r.nextInt(12) + 2);
			for(Card c : dealtCards.values()) {
				if(c.getVal() != card.getVal()) {
					if(c.getSuit() != card.getSuit()) {
						works = true;
					}
				}
			}
		}
		
		return card;		
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
