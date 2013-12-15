package org.triogs.ld28;

import java.awt.Dimension;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {

	private Taxi taxi;
	private Sprite taxi2;
	private Input inp;
	private Map map;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		taxi = new Taxi(new Dimension(container.getWidth(), container.getHeight()), new Vector2f(500,500));
		taxi2 = new Sprite("res/img/taxi.png", false, new Dimension(0,0), Sprite.BoundType.RECTANGULAR);
		taxi2.setPosition(new Vector2f(100,200));
		inp = new Input(container.getHeight());
		map = new Map("res/map/test1/test1.tmx");
		
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		map.draw(taxi.getVisibleMap(new Dimension(container.getWidth(), container.getHeight())));
		taxi.draw(g,true);
		taxi2.draw(g,true);
		taxi.drawWorldPos(g, new Vector2f(100,100));
		map.drawFrame(g, taxi);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		taxi.update(inp, delta);
		taxi2.update(inp, delta);
		map.collides(taxi);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
