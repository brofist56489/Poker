package com.bh.poker;

public class PacketHandler {
	
	private static String build(Object... stuff) {
		String r = "";
		for(int i = 0; i < stuff.length; i++) {
			r += stuff[i];
			if(i != stuff.length - 1) {
				r += "~";
			}
		}
		return r;
	}
	
	public static String JOIN(String name, int id) {
		return build(PacketParser.JOIN, name, id);
	}
	
	public static String LEAVE(String name) {
		return build(PacketParser.LEAVE, name);
	}
	
	public static String START_GAME() {
		return build(PacketParser.START_GAME);
	}
	
	public static String CARD(String to, Card c) {
		return build(PacketParser.CARD, to, c.getSuit(), c.getVal());
	}
}
