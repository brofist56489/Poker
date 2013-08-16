package com.bh.poker;

import com.bh.poker.NetBase.GameState;

public class JoiningMenu extends Menu {
	
	public JoiningMenu() {
		if(Game.server != null) {
			objects.add(new Button(0, Game.HEIGHT - 50, "Start Game") {
				public void onClick() {
					Server.state = GameState.PRE_GAME;
				}
			});
			objects.add(new Button(0, Game.HEIGHT - 24, "Stop Game ") {
				public void onClick() {
					String msg = PacketHandler.LEAVE(Game.PLAYER_NAME);
					Game.client.send(msg);
					Game.setMenu(new MainMenu());
					
					Game.server.stop();
					Game.server = null;

					Game.client.stop();
					Game.client = null;
				}
			});
		} else {
			objects.add(new Button(0, Game.HEIGHT - 24, "Disconnect") {
				public void onClick() {
					String msg = PacketHandler.LEAVE(Game.PLAYER_NAME);
					Game.client.send(msg);
					Game.setMenu(new MainMenu());

					Game.client.stop();
					Game.client = null;
				}
			});
		}
	}
	
	public void render() {
		super.render();
		
		int xo = 250;
		int yo = 40;
		int maxPlayers = (int) NetBase.config.get("max_players");
		for(int i = 0; i < maxPlayers; i++) {
			Image.renderRect(xo, yo + i * 32, 150, 24, 0xff0000);
		}
		for(int i = 0; i < Game.client.getPlayers().size(); i++) {
			Image.renderText(xo + 2, 2 + yo + i * 32, Game.client.getPlayers().get(i).getName(), 0xffffff, 2);
		}
	}
}
