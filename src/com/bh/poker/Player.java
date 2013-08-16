package com.bh.poker;

import java.net.InetAddress;

public class Player {
	private Card[] cards;
	private int id;
	private String name;
	
	private InetAddress ip;
	private int port;
	
	public Player(int id, String name) {
		this(id, name, null, 0);
	}
	
	public Player(int id, String name, InetAddress i, int p) {
		cards = new Card[2];
		cards[0] = new Card(false, 0, 4);
		cards[1] = new Card(true, 3, 14, 1);
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
