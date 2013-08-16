package com.bh.poker.menu;

import java.awt.Rectangle;

public interface MenuObject {
	
	Rectangle getRect();
	
	void onClick();
	void onKey(int kCode);
	
	void render();

	void onHover();

	void onNotHover();
}
