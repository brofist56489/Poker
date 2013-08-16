package com.bh.poker.menu;

import com.bh.poker.Game;

public class ChoiceGameMenu extends GameMenu {
	
	public ChoiceGameMenu() {
		objects.add(new Button(0, Game.HEIGHT - 24, "Fold ") {
			public void onClick() {
				
			}
		});
		objects.add(new Button(0, Game.HEIGHT - 48, "Raise") {
			public void onClick() {
				
			}
		});
		objects.add(new Button(0, Game.HEIGHT - 72, "Call ") {
			public void onClick() {
				
			}
		});
	}
	
	public void render() {
		super.render();
	}
}
