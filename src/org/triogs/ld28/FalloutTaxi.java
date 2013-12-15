package org.triogs.ld28;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class FalloutTaxi extends StateBasedGame{

	public static final int MAINMENU = 0;
	public static final int PLAY = 1;
	public FalloutTaxi() {
		super("Fallout Taxi");
		this.addState(new MainMenu());
		this.addState(new Play());
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.getState(MAINMENU).init(container, this);
		this.getState(PLAY).init(container, this);
		this.enterState(MAINMENU);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new FalloutTaxi());
			app.setDisplayMode(1920, 1080, true);
			app.setShowFPS(true);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
