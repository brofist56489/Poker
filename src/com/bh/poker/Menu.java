package com.bh.poker;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class Menu {
	
	public List<MenuObject> objects = new ArrayList<MenuObject>();
	public MenuObject hoverObject;
	
	public void tick() {
		Point p = new Point();
		p.x = MouseHandler.x;
		p.y = MouseHandler.y;
		hoverObject = null;
		for(MenuObject o : objects) {
			if(o.getRect().contains(p)) {
				o.onHover();
				hoverObject = o;
			} else {
				o.onNotHover();
			}
		}
		if(MouseHandler.buttonDownOnce(1) && hoverObject != null)
			hoverObject.onClick();
	}
	
	public void render() {
		for(MenuObject o : objects) {
			o.render();
		}
	}
}
