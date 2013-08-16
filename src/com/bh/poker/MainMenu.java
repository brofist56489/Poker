package com.bh.poker;

import javax.swing.JOptionPane;

public class MainMenu extends Menu {
	
	public MainMenu() {
		objects.add(new Button(0, 0, "Host Game") {
			public void onClick() {
				Game.server = new Server();
				Game.server.start();
				
				Game.client = new Client();
				Game.client.start();
				
				String m = PacketHandler.JOIN(Game.PLAYER_NAME, -1);
				Game.client.send(m);
				
				Game.setMenu(new JoiningMenu());
			}
		});
		objects.add(new Button(0, 100, "Join Game") {
			public void onClick() {
				new Thread() {
					public void run() {
						Client.setAddress(JOptionPane.showInputDialog(Game.frame, "Enter Server IP: ", Game.NAME, JOptionPane.PLAIN_MESSAGE));
						Game.client.send(PacketHandler.JOIN(Game.PLAYER_NAME, -1));
						Game.setMenu(new JoiningMenu());
					}
				}.start();
				Game.client = new Client();
				Game.client.start();
			}
		});
	}
}
