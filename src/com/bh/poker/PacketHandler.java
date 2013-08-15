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
	
	public static String JOIN(String name) {
		return build(PacketParser.JOIN, name);
	}
}