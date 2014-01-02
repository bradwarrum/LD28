package org.triogs.ld28;

import java.awt.Dimension;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Scrub extends Sprite {
	
	private static final float MAXV = (float) 70;
	private static final float AGGRO_RADIUS = 500;
	
	private Animation _death;
	private Image _deathframe;
	private boolean _dead;
	private long _erasureTime = 0;

	public Scrub() {
		super("res/img/scrubwalk.png",true,new Dimension(64,64), BoundType.CIRCULAR);
		updateAnimation(0);
		this.setBoundScale((float)0.5);
		freeze();
		try {
		Image i = new Image("res/img/scrubdie.png");
		SpriteSheet ss = new SpriteSheet(i, 128, 64);
		_death = new Animation(ss, 60);
		_death.setAutoUpdate(false);
		_death.setLooping(false);
		_death.setCurrentFrame(0);
		_deathframe = _death.getCurrentFrame();
		} catch (SlickException s) {
			s.printStackTrace();
		}
		
		//TODO: OVERRIDE ANIMATION SETTINGS AND BOUNDING CIRCLE SIZES
	}
	public void freeze() {
		this.setVelocity(new Vector2f(0,0));
		//this.setRotation(0);
	}
	
	public void update(int elapsedTime, Taxi t, Map m) {
		if (!_dead) {
		// If the taxi is within a certain distance, turn the scrub toward the car and 
		// start running at it. Otherwise stop and don't do anything
		float dist = this.getWorldPos().distance(t.getWorldPos());
		Vector2f toTaxi;
		if (dist > AGGRO_RADIUS) {
			toTaxi = this.getWorldPos().copy().sub(t.getWorldPos());
			this.setPosition(t.getPosition().copy().add(toTaxi));
			this.freeze();
			return;
		}
		// Get a vector pointing at the car
		toTaxi = t.getWorldPos().copy().sub(this.getWorldPos());
		toTaxi.normalise();
		this.setVelocity(toTaxi.scale(MAXV));
		this.addWorldPos(getVelocity().copy().scale((float)elapsedTime / (float) 1000));
		// Set the screen position based on how far the scrub is apart from the taxi
		updateAnimation(elapsedTime);
		toTaxi = this.getWorldPos().copy().sub(t.getWorldPos());
		this.setPosition(t.getPosition().copy().add(toTaxi));
		this.setRotation((float)toTaxi.copy().negate().getTheta());
		_deathframe.setRotation(this.getRotation());
		m.collidesScrub(this);
		updateBounds();
		}else {
			_death.update(elapsedTime);
			_deathframe = _death.getCurrentFrame();
			_deathframe.setRotation(this.getRotation());
			_erasureTime += elapsedTime;
			Vector2f toTaxi = this.getWorldPos().copy().sub(t.getWorldPos());
			this.setPosition(t.getPosition().copy().add(toTaxi));
		}
	}
	
	public void collidesTaxi(Taxi t) {
		if (!_dead && this.collides(t) && t.speedAsPercentage() > 0.2) {
			this._dead = true;
			t.getVelocity().scale((float).76);
		}
	}
	
	public void drawIfNecessary(Rectangle screen, Graphics g, boolean showBounds) {
		Point p = new Point(this.getWorldPos().x, this.getWorldPos().y);
		if (!screen.contains(p)) {
			return;
		}
		if (!_dead) {
			this.draw(g, showBounds);
		} else {
			g.drawImage(_deathframe, this.getPosition().x - _deathframe.getWidth() / 2, this.getPosition().y - _deathframe.getHeight() / 2);
		}
	}
}
