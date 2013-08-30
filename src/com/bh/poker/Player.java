package com.bh.poker;

import java.net.InetAddress;

public class Player {
	private Card[] cards;
	private int id;
	private String name;
	
	private int bet;
	private int coin;

	private InetAddress ip;
	private int port;
	
	private boolean inTurn;
	private boolean hasGone;
	
	public Player(int id, String name) {
		this(id, name, null, 0);
	}
	
	public Player(int id, String name, InetAddress i, int p) {
		cards = new Card[2];
		this.id = id;
		this.name = name;
		this.coin = 1000;
		this.hasGone = false;
		ip = i;
		port = p;
		inTurn = true;
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
	
	public int getBet() {
		return bet;
	}
	
	public Integer getBeti() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}
	
	public void changeBet(int bet) {
		this.bet += bet;
	}

	public void setCard(int cn, Card c) {
		cards[cn] = c;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}
	
	public void changeCoin(int c) {
		this.coin += coin;
	}

	public boolean isInTurn() {
		return inTurn;
	}

	public void setInTurn(boolean inTurn) {
		this.inTurn = inTurn;
	}

	public boolean hasGone() {
		return hasGone;
	}

	public void setHasGone(boolean hasGone) {
		this.hasGone = hasGone;
	}
}
