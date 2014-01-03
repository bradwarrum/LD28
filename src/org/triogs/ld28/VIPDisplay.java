package org.triogs.ld28;

import java.awt.Dimension;
import java.awt.Point;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class VIPDisplay {
	private static Image bg;
	private static Point renderPoint;
	private static Point nameTextPoint;
	private static Point statTextPoint;
	private static boolean shown;
	private static UnicodeFont nameFont;
	private static VIP[] displayedVIPs;
	private static int dispindex;
	private static boolean hidden;
	private static boolean hidebuttondown;
	private static boolean switchbtndown;
	
	@SuppressWarnings("unchecked")
	public static void init(Dimension screenSize) {
		try {
			bg = new Image("res/img/VIPpanel.png");
			renderPoint = new Point(screenSize.width - bg.getWidth(), screenSize.height - bg.getHeight());
			nameTextPoint = new Point(renderPoint.x + 55, renderPoint.y + 184);
			statTextPoint = new Point(renderPoint.x + 25, renderPoint.y + 15);
			shown = true;
			nameFont = new UnicodeFont("res/font/Minecraftia.ttf", 14, false, false);
			nameFont.addAsciiGlyphs();
			ColorEffect e = new ColorEffect();
			e.setColor(java.awt.Color.white);
			nameFont.getEffects().add(e);
			nameFont.loadGlyphs();
			hidden = false;
			hidebuttondown = false;
			switchbtndown = false;
			dispindex = 0;
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
	
	public static void setVIPs(VIP [] newVIPs) {
		displayedVIPs = newVIPs;
		dispindex =0;
	}
	
	public static void update (Input inp) {
		if (inp.isKeyDown(Input.KEY_H)){
			if (!hidebuttondown) {
				hidden ^= true;
			}
			hidebuttondown = true;
		} else {
			hidebuttondown = false;
		}
		
		if (inp.isKeyDown(Input.KEY_LEFT) && dispindex >0 && !switchbtndown) {
			dispindex --;
			switchbtndown = true;
		}
		if (inp.isKeyDown(Input.KEY_RIGHT) && dispindex < displayedVIPs.length - 1 && !switchbtndown) {
			dispindex ++;
			switchbtndown = true;
		}
		if (!inp.isKeyDown(Input.KEY_LEFT) && !inp.isKeyDown(Input.KEY_RIGHT)) {
			switchbtndown = false;
		}
	}
	
	public static VIP getVIP() {
		return displayedVIPs[dispindex];
	}
	
	public static void draw(Graphics g) {
		if (shown && !hidden) {
			g.drawImage(bg, renderPoint.x, renderPoint.y);
			if (displayedVIPs != null) {
				nameFont.drawString(nameTextPoint.x, nameTextPoint.y, displayedVIPs[dispindex].getFullName() + "  (" + (dispindex + 1) + "/" + displayedVIPs.length + ")");
				int i;
				for ( i = 0; i < TraitSet.NUMLINES; i++) {
					nameFont.drawString(statTextPoint.x, statTextPoint.y + i * 22, displayedVIPs[dispindex].getTraits().cString()[i]);
				}
				if (displayedVIPs[dispindex].isPassenger){
					nameFont.drawString(statTextPoint.x, statTextPoint.y + i * 22, "CURRENT PASSENGER");
				}
			}
		}
	}

}
