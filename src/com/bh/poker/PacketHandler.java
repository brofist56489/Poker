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
		return build(PacketParser.CARD, to, c);
	}

	public static String SERVER_STOP() {
		return build(PacketParser.SERVER_STOP);
	}
	
	public static String TURN(String name, String options) {
		return build(PacketParser.TURN, name, options);
	}
	
	public static String FOLD(String name) {
		return build(PacketParser.FOLD, name);
	}

	public static String RAISE(String name, int ammount) {
		return build(PacketParser.RAISE, name, ammount);
	}
}
