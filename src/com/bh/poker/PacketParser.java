package com.bh.poker;

import java.net.DatagramPacket;

import javax.swing.JOptionPane;

import com.bh.poker.NetBase.GameState;
import com.bh.poker.menu.ChoiceGameMenu;
import com.bh.poker.menu.MainMenu;

public class PacketParser {
	
	public static final int JOIN = 0;
	public static final int LEAVE = 1;
	public static final int START_GAME = 2;
	public static final int CARD = 3;
	
	public static void parseServer(Server server, DatagramPacket packet) {
		String msg = new String(packet.getData()).trim();
		String[] data = msg.split("~");
		
		switch(Integer.parseInt(data[0])) {
		case JOIN:
		{
			if(Server.state == GameState.JOINING) {
				Player p = new Player(server.getNextId(), data[1], packet.getAddress(), packet.getPort());
				if(p.getId() == 0) {
					NetBase.config.put("host_player", p);
				}
				if(p.getId() == -100) {
					String m = PacketHandler.JOIN(data[1], p.getId());
					server.sendto(m, p);
					break;
				}
				server.addPlayer(p);
				String m = PacketHandler.JOIN(data[1], p.getId());
				server.sendtoall(m, p);
				
				for(Player p1 : server.getPlayers()) {
//					if(p1 != p) {
						String a = PacketHandler.JOIN(p1.getName(), p1.getId());
						server.sendto(a, p);
//					}
				}
				System.out.println(p.getName() + " has joined");
			}
		}
			break;
		case LEAVE:
		{
			if(Server.state == GameState.JOINING) {
				server.removePlayer(data[1].trim());
				server.sendtoall(msg);
				System.out.println(data[1] + " has left");
			}
		}
			break;
		case START_GAME:
		{
			System.out.println("Client tried to start a game");
		}	
			break;
		default:
			break;
		}
	}

	public static void parseClient(Client client, DatagramPacket packet) {
		String msg = new String(packet.getData()).trim();
		String[] data = msg.split("~");
		
		switch(Integer.parseInt(data[0])) {
		case JOIN:
		{
			if(data[1] == Game.PLAYER_NAME) {
				if(data[2] == "-100") {
					new Thread() {
						public void run() {
							Game.setMenu(new MainMenu());
							JOptionPane.showMessageDialog(null, "Server is full.", Game.NAME, JOptionPane.ERROR_MESSAGE);
						}
					}.start();
					break;
				}
			}
			Player p = new Player(Integer.parseInt(data[2]), data[1]);
			client.addPlayer(p);
		}
			break;
		case LEAVE:
		{
			if(data[1] == Game.PLAYER_NAME) { }
			else {
				client.removePlayer(data[1]);
			}
		}
			break;
		case START_GAME:
		{
			Game.setMenu(new ChoiceGameMenu());
		}
			break;
		case CARD:
		{
			String to = data[1];
			String[] tos = to.split("_");
			if(tos[1] == "Player") {
				
			}
		}
		default:
			break;
		}
	}
}
