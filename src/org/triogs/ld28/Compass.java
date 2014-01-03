package org.triogs.ld28;

import java.awt.Dimension;
import java.awt.Point;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Compass {
	private static Image bg;
	private static Image rose;
	private static float angle;
	private static Point bgRenderPoint;
	private static Point roseCenter;
	private static Vector2f shPos;
	private static Vector2f dest;
	
	public static void init(Vector2f safehousePos, Dimension screenSize) {
		try {
			bg = new Image("res/img/compass.png");
			rose = new Image("res/img/rose.png");
			angle = 0;
			rose.setRotation(angle);
			shPos = safehousePos;
			dest = shPos;
			bgRenderPoint = new Point(0,screenSize.height - bg.getHeight());
			roseCenter = new Point(bgRenderPoint.x + 13, bgRenderPoint.y + 95);
		}catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void update(Vector2f worldPos) {
		if (MainMenu.sprites.taxi.hasPassenger) {
			dest = shPos;
		}
		else {
			dest = VIPDisplay.getVIP().getWorldPos();
			if (dest == null) {
				dest = shPos;
			}
		}
		Vector2f betw = dest.copy().sub(worldPos);
		angle = (float)betw.getTheta();
		rose.setRotation(angle);
	}
	
	public static void draw (Graphics g) {
		g.drawImage(bg, bgRenderPoint.x, bgRenderPoint.y);
		g.drawImage(rose, roseCenter.x, roseCenter.y);
	}
}
