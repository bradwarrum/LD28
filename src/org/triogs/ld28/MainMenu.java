package org.triogs.ld28;

import java.awt.Dimension;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {

	private Input inp;
	private Map map;
	private Dimension screenDim;
	public static SpriteManager sprites;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		screenDim = new Dimension(container.getWidth(), container.getHeight());
		inp = new Input(container.getHeight());
		map = new Map("res/map/test1/test1.tmx");
		sprites = new SpriteManager(screenDim, map.getPassMatrix(), map.getTileSize());
		for (int i = 0; i<500; i++) {
			sprites.addScrub();
		}
		BombTimer.init(screenDim);
		BombTimer.reset(300);
		BombTimer.start();
		Compass.init(new Vector2f(0,0), screenDim);
		VIP.loadNames();
		VIPDisplay.init(screenDim);
		VIP[] vips = new VIP[3];
		for (int i = 0; i<3; i++) {
			vips[i] = new VIP();
			sprites.addVIP(vips[i]);
		}
		VIPDisplay.setVIPs(vips);
		
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		Rectangle visibleMap = sprites.taxi.getVisibleMap(screenDim);
		map.draw(visibleMap);
		sprites.draw(g, visibleMap, map);
		//map.drawFrame(g, taxi);
		BombTimer.draw(g);
		//g.drawString("MEM total(used):   " + (Runtime.getRuntime().totalMemory()/1000000) + "(" + ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1000000) + ") MB", 10, 25);
		Compass.draw(g);
		VIPDisplay.draw(g);


	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		sprites.update(delta, inp, map);
		map.collidesTaxi(sprites.taxi);
		BombTimer.update(delta);
		Compass.update(sprites.taxi.getWorldPos());
		VIPDisplay.update(inp);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
