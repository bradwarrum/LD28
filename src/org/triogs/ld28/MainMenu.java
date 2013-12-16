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

	private Taxi taxi;
	private Input inp;
	private Scrub scrub;
	private Map map;
	private Dimension screenDim;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		screenDim = new Dimension(container.getWidth(), container.getHeight());
		taxi = new Taxi(screenDim, new Vector2f(500,500));
		inp = new Input(container.getHeight());
		map = new Map("res/map/test1/test1.tmx");
		scrub = new Scrub();
		scrub.setWorldPos(new Vector2f(1000,500));
		scrub.setPosition(new Vector2f(700,500));
		BombTimer.init(screenDim);
		BombTimer.reset(300);
		BombTimer.start();
		Compass.init(new Vector2f(0,0), screenDim);
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		Rectangle visibleMap = taxi.getVisibleMap(screenDim);
		map.draw(visibleMap);
		taxi.draw(g,false);
		taxi.drawWorldPos(g, new Vector2f(100,100));
		//map.drawFrame(g, taxi);
		scrub.drawIfNecessary(visibleMap, g, false);
		BombTimer.draw(g);
		//g.drawString("MEM total(used):   " + (Runtime.getRuntime().totalMemory()/1000000) + "(" + ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1000000) + ") MB", 10, 25);
		Compass.draw(g);
		

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		taxi.update(inp, delta);
		map.collidesTaxi(taxi);
		scrub.update(delta, taxi);
		BombTimer.update(delta);
		Compass.update(taxi.getWorldPos());
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
