package org.triogs.ld28;

import java.awt.Dimension;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Scrub extends Sprite {
	
	private static final float MAXV = (float) 70;
	private static final float AGGRO_RADIUS = 500;
	
	
	private Vector2f _mapPos;
	public Scrub() {
		super("res/img/scrub.png",false,new Dimension(64,64), BoundType.CIRCULAR);
		updateAnimation(0);
		freeze();
		
		//TODO: OVERRIDE ANIMATION SETTINGS AND BOUNDING CIRCLE SIZES
	}
	public void setWorldPos(Vector2f pos) {
		_mapPos = pos;
	}
	
	public void addWorldPos(Vector2f delta) {
		_mapPos = _mapPos.add(delta);
	}
	
	public Vector2f getWorldPos() {
		return _mapPos;
	}
	public void freeze() {
		Vector2f oldVel = this.getVelocity();
		this.setVelocity(new Vector2f(0,0));
		float rot = (float)((getVelocity().getTheta() - oldVel.getTheta()));
		this.setRotation(0);
		rotateBuffer(rot);
		updateTotalRotation();
	}
	
	public void update(int elapsedTime, Taxi t) {
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
		Vector2f oldVel = this.getVelocity();
		// Get a vector pointing at the car
		toTaxi = t.getWorldPos().copy().sub(this.getWorldPos());
		toTaxi.normalise();
		this.setVelocity(toTaxi.scale(MAXV));
		this.addWorldPos(getVelocity().copy().scale((float)elapsedTime / (float) 1000));
		// Set the screen position based on how far the scrub is apart from the taxi
		toTaxi = this.getWorldPos().copy().sub(t.getWorldPos());
		this.setPosition(t.getPosition().copy().add(toTaxi));
		updateBounds();
		updateAnimation(elapsedTime);
		System.out.println((float)((getVelocity().getTheta() - oldVel.getTheta())));
		float rot = (float)((getVelocity().getTheta() - oldVel.getTheta()));
		setRotation(rot);
		updateTotalRotation();
		rotateBuffer(rot);
	}
	
	public void drawIfNecessary(Rectangle screen, Graphics g, boolean showBounds) {
		Point p = new Point(this.getWorldPos().x, this.getWorldPos().y);
		if (!screen.contains(p)) {
			return;
		}
		this.draw(g, showBounds);
	}
}
