package com.bh.poker;

public class TestMenu extends Menu {
	
	public TestMenu() {
		objects.add(new Button(0, 0, "Test Button") {
			public void onClick() {
				System.out.println("worked");
			}
		});
	}
}
