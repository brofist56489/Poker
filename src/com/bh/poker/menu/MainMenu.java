package com.bh.poker.menu;

import javax.swing.JOptionPane;

import com.bh.poker.Client;
import com.bh.poker.Game;
import com.bh.poker.PacketHandler;
import com.bh.poker.Server;

public class MainMenu extends Menu {
	
	public MainMenu() {
		objects.add(new Button(Game.WIDTH / 2 - 56, 150, " Host Game ") {
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
		objects.add(new Button(Game.WIDTH / 2 - 56, 180, " Join Game ") {
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
		objects.add(new Button(Game.WIDTH / 2 - 56, 210, "    Quit    ") {
			public void onClick() {
				System.exit(0);
			}
		});
	}
}
