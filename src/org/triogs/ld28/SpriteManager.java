package org.triogs.ld28;

import java.awt.Dimension;
import java.awt.Point;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class SpriteManager {
	private ArrayList<Scrub> scrubs;
	private ArrayList<VIP> vips;
	protected Taxi taxi;
	private ArrayList<Pair<Sprite,String>> additions;
	private ArrayList<Pair<Sprite,String>> deletions;
	private boolean[][] passable;
	private Dimension tilesize;
	
	
	public SpriteManager(Dimension screenSize, boolean[][] passableMap, Dimension tileSize) {
		scrubs = new ArrayList<Scrub>();
		vips = new ArrayList<VIP>();
		taxi = new Taxi(screenSize, new Vector2f(500,500));
		this.passable = passableMap;
		tilesize = tileSize;
		additions = new ArrayList<Pair<Sprite,String>>();
		deletions = new ArrayList<Pair<Sprite,String>>();
	}
	
	public void addVIP() {
		Point spawnPoint = findSpawnPoint();
		VIP v = new VIP();
		v.setWorldPos(new Vector2f(spawnPoint.x * tilesize.width + tilesize.width / 2, 
				spawnPoint.y * tilesize.height + tilesize.height / 2));
		additions.add(new Pair<Sprite, String>(v, "vip"));
	}
	
	public void addScrub() {
		Point spawnPoint = findSpawnPoint();
		System.out.println(spawnPoint);
		Scrub s = new Scrub();
		s.setWorldPos(new Vector2f(spawnPoint.x * tilesize.width + tilesize.width / 2, 
				spawnPoint.y * tilesize.height + tilesize.height / 2));
		additions.add(new Pair<Sprite, String>(s, "scrub"));
	}
	
	public void update(int elapsedMS, Input i, Map m) {
		taxi.update(i, elapsedMS);
		for (VIP v : vips) {
			v.update(i, elapsedMS);
		}
		for (Scrub s: scrubs) {
			s.update(elapsedMS, taxi, m);
			s.collidesTaxi(taxi);
		}
		confirmAdditions();
		confirmDeletions();
	}
	
	public void draw(Graphics g, Rectangle screen, Map m) {
		for (Scrub s: scrubs) {
			s.drawIfNecessary(screen, g, false);
			m.drawFrame(g, s);
			
		}
		for (VIP v: vips) {
			v.draw(g);
		}
		taxi.draw(g);
	}
	
	private void confirmAdditions() {
		for(Pair<Sprite, String> p : additions) {
			if (p.getSecond().equals("vip")) {
				vips.add((VIP)p.getFirst());
			}
			else if (p.getSecond().equals("scrub")) {
				scrubs.add((Scrub)p.getFirst());
			}
		}
		additions.clear();
	}
	
	private void confirmDeletions() {
		for (Pair<Sprite,String> p :deletions) {
			if (p.getSecond().equals("vip")) {
				vips.remove((VIP)p.getFirst());
			}
			else if (p.getSecond().equals("scrub")) {
				scrubs.remove((Scrub)p.getFirst());
			}
		}
		deletions.clear();
	}
	
	
	private Point findSpawnPoint() {
		Rectangle r = new Rectangle(0,0,passable.length, passable[0].length);
		return findSpawnPoint(r);
	}
	
	private Point findSpawnPoint(Rectangle spawnRestriction) {
		Random r = new Random();
		int x, y;
		do {
			x = r.nextInt((int)spawnRestriction.getWidth()) + (int)spawnRestriction.getMinX();
			y = r.nextInt((int)spawnRestriction.getHeight()) + (int)spawnRestriction.getMinY();
		} while (!passable[x][y]);
		return new Point(x,y);
	}

}
