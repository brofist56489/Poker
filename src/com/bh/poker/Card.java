package com.bh.poker;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bh.poker.menu.MenuObject;

public class Card implements MenuObject {
	private List<Integer> values;
	private int suit;
	public boolean visible;
	
	/**
	 * 0 - Hearts
	 * 1 - Diamonds
	 * 2 - Spades
	 * 3 - Clubs
	 */
	public Card(boolean vis, int suit, Integer... vals) {
		values = Arrays.asList(vals);
		this.suit = suit;
		this.visible = vis;
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
	
	public void setValues(List<Integer> values) {
		this.values = values;
	}
	
	public void setValue(int i) {
		this.values = new ArrayList<Integer>();
		this.values.add(i);
	}

	public void setSuit(int suit) {
		this.suit = suit;
	}

	public void render(int x, int y) {
		Image.render(x, y, (visible) ? 0 : 2, 0);
		Image.render(x, y + 16, (visible) ? 0 : 2, 3);
		if(visible) {
			int r = (suit < 2) ? 14 : 15;
			Image.render(x, y, (getVal() - 2) + r * 16, 0);
			Image.render(x, y + 16, suit + 13 * 16, 0);
		}
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
	
	public Card invisible() {
		return new Card(false, suit, getVal());
	}
	
	public String toString() {
		return ((visible) ? "1" : "0") + "-" + suit + "-" + getVal();
	}

	public static Card parse(String c) {
		String[] cs = c.split("-");
		return new Card(cs[0].equals("1"), Integer.parseInt(cs[1]), Integer.parseInt(cs[2]));
	}
}
