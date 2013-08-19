package com.bh.poker;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.bh.poker.menu.MainMenu;
import com.bh.poker.menu.Menu;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = WIDTH * 3 / 4;
	public static final int SCALE = 2;
	public static final String NAME = "Texas Hold'em";
	
	public static String PLAYER_NAME;
	
	public static Game game;
	
	public static JFrame frame;
	
	private static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public static boolean running = false;
	public static int tickCount = 0;
	
	public static Menu menu;
	public static Client client;
	public static Server server;
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		new Thread(this, "MAIN_GAME").start();
	}
	
	public void run() {
		{
			NetBase.init();
			
			game = this;
			
			KeyHandler.init(this);
			MouseHandler.init(this);
			
			menu = new MainMenu();
		}
		
		long lt = System.nanoTime();
		double nsPt = 1000000000.0/60.0;
		double delta = 0.0;
		long now = 0;
		int ticks = 0;
		int frames = 0;
		
		boolean shouldRender = false;
		long ltr = System.currentTimeMillis();
		
		new Thread() {
			public void run() {
				PLAYER_NAME = JOptionPane.showInputDialog(frame, "Enter your name: ", NAME, JOptionPane.PLAIN_MESSAGE);
			}
		}.start();
		
		while(true) {
			now = System.nanoTime();
			delta += (now - lt) / nsPt;
			lt = now;
			shouldRender = false;
			
			while(delta >= 1) {
				tick();
				ticks++;
				delta--;
				shouldRender = true;
			}
			
			if(shouldRender) {
				render();
				frames++;
			}
			
			if(System.currentTimeMillis() - ltr >= 1000) {
				ltr += 1000;
				frame.setTitle(ticks + " tps, " + frames + " fps");
				ticks = frames = 0;
			}
		}
	}

	public static void setMenu(Menu m) {
		menu = m;
	}
	
	private void tick() {
		tickCount++;
		MouseHandler.poll();
		KeyHandler.poll();
		
		if(menu != null) {
			menu.tick();
		}
		if(server != null) {
			Server.tick();
		}
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x7f7f7f;
		}
		
		if(menu != null) {
			menu.render();
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		
		Game g = new Game();
		Game.frame = new JFrame(NAME);
		Game.frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		Game.frame.setSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		Game.frame.add(g);
		Game.frame.addWindowListener(new WindowListener() {
			
			public void windowActivated(WindowEvent arg0) {
			}

			
			public void windowClosed(WindowEvent arg0) {
			}
			
			public void windowClosing(WindowEvent arg0) {
				if(client != null) {
					String m = PacketHandler.LEAVE(PLAYER_NAME);
					client.send(m);
				}
			}
			
			public void windowDeactivated(WindowEvent arg0) {
			}
			
			public void windowDeiconified(WindowEvent arg0) {
			}
			
			public void windowIconified(WindowEvent arg0) {
			}
			
			public void windowOpened(WindowEvent arg0) {
			}
			
		});
		Game.frame.setLocationRelativeTo(null);
		Game.frame.setResizable(true);
		Game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game.frame.setVisible(true);
		g.start();
	}
}
