package com.bh.poker.menu;

import com.bh.poker.Game;

public class ChoiceGameMenu extends GameMenu {
	
	public ChoiceGameMenu() {
		int yo = 4;
		objects.add(new Button(10, Game.HEIGHT - 24 - yo, "Fold ") {
			public void onClick() {
				
			}
		});
		objects.add(new Button(10, Game.HEIGHT - 50 - yo, "Raise") {
			public void onClick() {
				
			}
		});
		objects.add(new Button(10, Game.HEIGHT - 76 - yo, "Call ") {
			public void onClick() {
				
			}
		});
	}
	
	public void render() {
		super.render();
	}
}
