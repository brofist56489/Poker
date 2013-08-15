package com.bh.poker;

import java.net.InetAddress;

public class ServerPlayer {
	private Card[] cards;
	private int id;
	private String name;
	
	private InetAddress ip;
	private int port;
	
	public ServerPlayer(int id, String name, InetAddress i, int p) {
		cards = new Card[2];
		this.id = id;
		this.name = name;
		ip = i;
		port = p;
	}
	
	public Card getCard(int i) {
		return cards[i];
	}
	
	public Card[] getCards() {
		return cards;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	public InetAddress getIp() {
		return ip;
	}
}
