package com.bh.poker;

import java.net.DatagramPacket;

import com.bh.poker.Server.GameState;

public class PacketParser {
	
	public static final String JOIN = "0";
	public static final String LEAVE = "1";
	public static final String START_GAME = "2";
	
	
	public static void parseServer(Server server, DatagramPacket packet) {
		String msg = new String(packet.getData()).trim();
		String[] data = msg.split("~");
		
		switch(data[0]) {
		case JOIN:
		{
			if(Server.state == GameState.JOINING) {
				ServerPlayer p = new ServerPlayer(server.getNextId(), data[1], packet.getAddress(), packet.getPort());
				if(p.getId() == 0) {
					server.config.put("host_player", p);
				}
				server.getPlayers().add(p);
				String m = PacketHandler.JOIN(data[1]);
				server.sendtoall(m, p);
			}
		}
			break;
		case LEAVE:
		{
			
		}
			break;
		case START_GAME:
		{
			System.out.println("Client tried to start a game");
		}	
			break;
		}
	}
}
