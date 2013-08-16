package com.bh.poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class NetBase {
	
	public static HashMap<String, Object> config = new HashMap<String, Object>();
	
	public static enum GameState {
		JOINING, PRE_GAME,
		INIT_DEAL, FLOP, TURN, RIVER,
		IN_GAME,
		WAITING_FOR_PLAYER;
	}
	
	public static void init() {
		config.put("max_players", 8);
		config.put("host_player", null);
	}

	public static final int PORT = 8008;
	
	protected List<Player> players;
	
	public NetBase() {
		players = new ArrayList<Player>();
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public void removePlayer(int id) {
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getId() == id) {
				players.remove(i);
				break;
			}
		}
	}
	
	public void removePlayer(String name) {
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getName().equals(name)) {
				players.remove(i);
				break;
			}
		}
	}
}
