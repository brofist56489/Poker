package com.bh.poker.menu;

import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import com.bh.poker.Game;
import com.bh.poker.PacketHandler;

public class ChoiceGameMenu extends GameMenu {
	
	
	/**
	 * 0 - Fold
	 * 1 - Raise
	 * 2 - Check
	 * 3 - Call
	 * @param options
	 */
	public ChoiceGameMenu(String options) {
		String[] o = options.split("-");
		List<String> ops = Arrays.asList(o);
		int count = 1;
		int yo = 4;
		if(ops.contains("0")) {
			objects.add(new Button(10, Game.HEIGHT - count * 26 - yo, "Fold ") {
				public void onClick() {
					String msg = PacketHandler.FOLD(Game.PLAYER_NAME);
					Game.client.send(msg);
				}
			});
			count += 1;
		}
		if(ops.contains("1")) {
			objects.add(new Button(10, Game.HEIGHT - count * 26 - yo, "Raise") {
				public void onClick() {
					new Thread() {
						public void run() {
							int ammount = Integer.parseInt(JOptionPane.showInputDialog(null, "Raise: ", Game.NAME, JOptionPane.PLAIN_MESSAGE));
							if(ammount + Game.client.getPlayer(Game.PLAYER_NAME).getBet() > Game.client.getPlayer(Game.PLAYER_NAME).getCoin()) {
										JOptionPane.showMessageDialog(null, "You don't have that much money!", Game.NAME, JOptionPane.ERROR_MESSAGE);
								return;
							}
							String msg = PacketHandler.RAISE(Game.PLAYER_NAME, ammount);
							Game.client.send(msg);
						}
					}.start();
				}
			});
			count += 1;
		}
		if(ops.contains("2")) {
			objects.add(new Button(10, Game.HEIGHT - count * 26 - yo, "Call ") {
				public void onClick() {
					
				}
			});
			count += 1;
		}
		if(ops.contains("3")) {
			objects.add(new Button(10, Game.HEIGHT - count * 26 - yo, "Check") {
				public void onClick() {
					
				}
			});
			count += 1;
		}
	}
	
	public void render() {
		super.render();
	}
}
