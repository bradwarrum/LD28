package org.triogs.ld28;

import java.awt.Dimension;
import java.awt.Point;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class BombTimer {
	private static float secondsLeft;
	public static boolean timerDone;
	public static boolean paused;
	private static Image bg;
	private static Point renderPoint;
	private static Point textPoint;
	private static UnicodeFont timerFont;
	
	@SuppressWarnings("unchecked")
	public static void init (Dimension screenSize) {
		try {
			bg = new Image("res/img/timer2.png");
			renderPoint = new Point(screenSize.width / 2 - bg.getWidth()/2, 0);
			textPoint = new Point(renderPoint.x + 163, renderPoint.y + 5);
			timerFont = new UnicodeFont("res/font/Minecraftia.ttf", 48, false, false);
			timerFont.addGlyphs("0123456789:.");
			ColorEffect e = new ColorEffect();
			e.setColor(java.awt.Color.white);
			timerFont.getEffects().add(e);
			timerFont.loadGlyphs();
		}catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void reset(int seconds) {
		secondsLeft = seconds;
		timerDone = false;
		paused = true;
	}
	
	public static void start() {
		paused = false;
	}
	
	public static void pause() {
		paused = true;
	}
	
	public static void update(int milliseconds) {
		if (timerDone || paused) {
			return;
		}
		secondsLeft -= (float) milliseconds / (float) 1000;
		if (secondsLeft <= 0) {
			secondsLeft = 0;
			timerDone = true;
			paused = true;
		}	
	}
	
	public static void draw(Graphics g) {
		g.drawImage(bg, renderPoint.x, renderPoint.y);
		g.setFont(timerFont);
		g.drawString((int)secondsLeft / 60 + ":" +String.format("%02d",(int)secondsLeft % 60), textPoint.x, textPoint.y);
		System.out.println((int)secondsLeft / 60 + ":" +String.format("%02d",(int)secondsLeft % 60));
	}

}
