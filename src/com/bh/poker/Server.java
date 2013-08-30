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
	public Player player;
	public int intvar;
	
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
		player = null;
		intvar = 0;
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
		String m = PacketHandler.SERVER_STOP();
		sendtoall(m);
		socket.close();
	}
	
	public static void tick() {
		Server self = Game.server;
		
		switch(state) {
		case PRE_GAME:
		{
			NetBase.config.put("players", self.players.size());
			String m = PacketHandler.START_GAME();
			self.sendtoall(m);
			state = GameState.INIT_DEAL;
		}
			break;
		case INIT_DEAL:
		{
			self.setPot(0);
			for(Player p : self.players) {
				Card c1 = getNewCard();
				Card c2 = getNewCard();
				
				dealtCards.put(p.getName() + "_0", c1);
				dealtCards.put(p.getName() + "_1", c2);
				for(Player p1 : self.players) {
					if(p.getName().equals(p1.getName())) {
						self.sendto(PacketHandler.CARD(p.getName() + "_0", c1), p1);
						self.sendto(PacketHandler.CARD(p.getName() + "_1", c2), p1);
					} else {
						self.sendto(PacketHandler.CARD(p.getName() + "_0", c1.invisible()), p1);
						self.sendto(PacketHandler.CARD(p.getName() + "_1", c2.invisible()), p1);
					}
				}
			}
			self.bet = 100;
			state = GameState.BET_INIT;
		}
			break;
		case BET_INIT:
		{
			if(self.player == null) {
				self.player = self.getPlayer(0);
				self.sendtoall(PacketHandler.TURN(self.player.getName(), "0-1-3"));
			}
			if(self.player.hasGone()) {
				if(self.player.getBet() == self.bet) {
					if(!self.switchPlayer())
						state = GameState.FLOP;
				} else if (!self.player.isInTurn()) {
					if(!self.switchPlayer())
						state = GameState.FLOP;
				}
			}
		}
			break;
		case FLOP:
		{
			
		}
			break;
		default:
			break;
		}
	}
	
	private boolean switchPlayer() {
		if(player.getId() + 1 < (Integer)NetBase.config.get("players")) {
			player = getPlayer(player.getId() + 1);
			sendtoall(PacketHandler.TURN(player.getName(), "0-1-3"));
			return true;
		} else {
			return false;
		}
	}
	
	private static Card getNewCard() {
		boolean works = false;
		Card card = new Card(true, -1, -1);
		Random r = new Random();
		while(!works) {
			card.setSuit(r.nextInt(4));
			card.setValue(r.nextInt(12) + 2);
			if(dealtCards.isEmpty()) {
				works = true;
			}
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
