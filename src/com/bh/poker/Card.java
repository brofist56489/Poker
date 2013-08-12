package com.bh.poker;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;

public class Card implements MenuObject {
	private List<Integer> values;
	private int suit;
	
	/**
	 * 0 - Hearts
	 * 1 - Diamonds
	 * 2 - Spades
	 * 3 - Clubs
	 */
	public Card(int suit, Integer... vals) {
		values = Arrays.asList(vals);
		this.suit = suit;
	}
	
	public int getVal() {
		return values.get(0);
	}
	
	public List<Integer> getVals() {
		return values;
	}
	
	public int getSuit() {
		return suit;
	}
	
	public void render(int x, int y) {
		Image.render(x, y, 0, 0);
		Image.render(x, y + 16, 0, 2);
		int r = (suit < 2) ? 14 : 15;
		Image.render(x, y, (getVal() - 2) + r * 16, 0);
		Image.render(x, y + 16, suit + 13 * 16, 0);
	}

	public void onClick() {		
	}
	public void onKey(int kCode) {
	}
	public Rectangle getRect() {
		return null;
	}
	public void render() {
	}
	
	public void onHover() {	
	}
	
	public void onNotHover() {
	}
}
