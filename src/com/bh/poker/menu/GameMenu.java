package com.bh.poker.menu;

import com.bh.poker.Card;
import com.bh.poker.Client;
import com.bh.poker.Game;
import com.bh.poker.Image;
import com.bh.poker.Player;

public class GameMenu extends Menu {
	
	public GameMenu() {
		
	}
	
	public void tick() {
		super.tick();
	}
	
	public void render() {
		super.render();
		Client c = Game.client;
		for(int i = 0; i < c.getPlayers().size(); i++) {
			Player p = c.getPlayer(i);
			Image.renderText(Game.WIDTH - p.getName().length() * 8, 40 + i * 56, p.getName(), 0xffffff, 1);
			Image.renderText(Game.WIDTH - (p.getBeti().toString().length() + 2) * 17, 56 + i  * 56, p.getBeti().toString(), 0xffffff, 2);
			if(!p.isInTurn()) continue;
			for(int j = 0; j < p.getCards().length; j++) {
				Card card = p.getCard(j); 
				if(card != null)
					card.render(Game.WIDTH - (2 - j) * 17, 50 + i * 56);
			}
		}
	}
}
