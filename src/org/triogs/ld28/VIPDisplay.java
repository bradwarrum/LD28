package org.triogs.ld28;

import java.awt.Dimension;
import java.awt.Point;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class VIPDisplay {
	private static Image bg;
	private static Point renderPoint;
	private static Point textPoint;
	private static boolean shown;
	private static UnicodeFont nameFont;
	private static VIP displayedVIP;
	
	@SuppressWarnings("unchecked")
	public static void init(Dimension screenSize) {
		try {
			bg = new Image("res/img/VIPpanel.png");
			renderPoint = new Point(screenSize.width - bg.getWidth(), screenSize.height - bg.getHeight());
			textPoint = new Point(renderPoint.x + 55, renderPoint.y + 184);
			shown = true;
			nameFont = new UnicodeFont("res/font/Minecraftia.ttf", 18, false, false);
			nameFont.addGlyphs(" ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			ColorEffect e = new ColorEffect();
			e.setColor(java.awt.Color.white);
			nameFont.getEffects().add(e);
			nameFont.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void show() {
		shown = true;
	}
	public static void hide() {
		shown = false;
	}
	
	public static void setVIP(VIP newVIP) {
		displayedVIP = newVIP;
	}
	
	public static void draw(Graphics g) {
		if (shown) {
			g.drawImage(bg, renderPoint.x, renderPoint.y);
			if (displayedVIP != null) {
				nameFont.drawString(textPoint.x, textPoint.y, displayedVIP.getFullName());
			}
		}
	}

}
