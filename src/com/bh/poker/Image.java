package com.bh.poker;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	
	private static Image tmap1 = new Image("/tileMap.png");
	private static Image font = new Image("/font.png");
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?:><";
	
	BufferedImage image;
	public int[] pixels;
	public int w;
	public int h;
	
	public Image(String path) {
		try {
			image = ImageIO.read(Image.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		w = image.getWidth();
		h = image.getHeight();
		
		pixels = image.getRGB(0, 0, w, h, null, 0, w);
		for(int i = 0; i < pixels.length; i++) {
			if(pixels[i] < 0)
				pixels[i] += 0x1000000;
		}
	}
	
	public static void render(int xp, int yp, int tId, int flip) {
		
		int[] pixels = tmap1.pixels;
		int w = tmap1.w;
		
		int tx = (tId % 16);
		int ty = (tId / 16);
		int toff = (tx * 16) + (ty * 16) * w;
		int xs, ys, col;
		
		int ww = Game.WIDTH;
		int hh = Game.HEIGHT;
		
		for(int y = 0; y < 16; y++) {
			if((y + yp) < 0 || (y + yp) >= hh) continue;
			ys = y;
			if((flip & 0x02) == 0x02) ys = 15 - y;
			for(int x = 0; x < 16; x++) {
				if((x + xp) < 0 || (x + xp) >= ww) continue;
				xs = x;
				if((flip & 0x01) == 0x01) xs = 15 - x;
				
				col = pixels[xs + ys * w + toff];
				if(col == 0x7f007f) continue;
				
				Game.pixels[(x + xp) + (y + yp) * ww] = col;
			}
		}
	}
	
	public static void renderFrame(int xp, int yp, int w, int h) {
		if(h == 1) {
			render(xp, yp + h * 16 - 8, 4 + 13 * 16, 2);
			render(xp + w * 16 - 16, yp + h * 16 - 8, 4 + 13 * 16, 3);
		} else {
			render(xp, yp + h * 16 - 16, 4 + 13 * 16, 2);
			render(xp + w * 16 - 16, yp + h * 16 - 16, 4 + 13 * 16, 3);
		}
		
		render(xp, yp, 4 + 13 * 16, 0);
		render(xp + w * 16 - 16, yp, 4 + 13 * 16, 1);
		
		for(int i = 1; i < w - 1; i++) {
			render(xp + i * 16, yp, 5 + 13 * 16, 0);
			if(h == 1) {
				render(xp + i * 16, yp + 8, 5 + 13 * 16, 2);
			} else {
				render(xp + i * 16, yp + 16, 5 + 13 * 16, 2);
			}
		}
	}
	
	public static void renderText(int xp, int yp, String text, int c, int scale) {
		text = text.toUpperCase();
		
		int index = -1;
		int toff = 0;
		for(int i = 0; i < text.length(); i++) {
			index = chars.indexOf(text.charAt(i));
			if(index >= 0 && index < chars.length()) {
				toff = ((index % 16) * 8) + ((int)(index / 16) * 8) * 128;
				
				for(int y = 0; y < 8; y++) {
					for(int x = 0; x < 8; x++) {
						int col = font.pixels[x + y * 128 + toff];
						
						for(int yo = 0; yo < scale; yo++) {
							int ya = ((y * scale) + yp + yo);
							if(ya < 0 || ya >= Game.HEIGHT) continue;
							
							for(int xo = 0; xo < scale; xo++) {
								int xa = (((x * scale) + i * 8 * scale) + xp + xo);
								if(xa < 0 || xa >= Game.WIDTH) continue;
								
								if(col == 0x7f007f) continue;
								if(col == 0xffffff) col = c;
								Game.pixels[xa + ya * Game.WIDTH] = col;
								
							}
						}
					}
				}
			}
		}
	}
	
	public static void renderRect(int xp, int yp, int w, int h, int c) {
		for(int y = 0; y < h; y++) {
			if((y + yp) < 0 || (y + yp) >= Game.HEIGHT) continue;
			for(int x = 0; x < w; x++) {
				if((x + xp) < 0 || (x + xp) >= Game.WIDTH) continue;
				Game.pixels[(x + xp) + (y + yp) * Game.WIDTH] = c;
			}
		}
	}
}
