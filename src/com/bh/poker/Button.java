package com.bh.poker;

import java.awt.Rectangle;

public class Button implements MenuObject {
	
	public int x;
	public int y;
	public int w;
	public int h;
	
	public String text;
	
	public Button(int x, int y, String text) {
		this.text = text;
		this.w = text.length() + 3;
		w /= 2;
		this.h = 1;
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w * 16, 24);
	}
	
	public void onClick() {
	}
	
	public void onKey(int kCode) {
	}

	public void render() {
		Image.renderFrame(x, y, w, h);
		Image.renderText(x + 8, y + 8, text);
	}

	public void onHover() {
		
	}

	public void onNotHover() {
		
	}

}
