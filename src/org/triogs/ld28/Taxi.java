package org.triogs.ld28;

import java.awt.Dimension;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Taxi extends Sprite {
	private static final float ACC = (float) .2;
	private static final float Kf = (float) .999;
	private static final float MAXV =(float) 1;
	private static final float RPS = (float) 70;
	
	private Vector2f _mapPos;
	public Taxi(Dimension screenSize, Vector2f startingPos) {
		super("res/img/taxi.png", false, new Dimension(0, 0), BoundType.RECTANGULAR);
		setPosition(new Vector2f((float)(screenSize.width / 2.0), (float)(screenSize.height / 2.0)));
		_mapPos = startingPos;
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
	
	public void drawWorldPos(Graphics g, Vector2f pos) {
		g.drawString("POS: (" + getWorldPos().x + ", " + getWorldPos().y + ")", (int)pos.x, (int)pos.y);
	}
	public void update(Input i, int elapsedTime) {
		Vector2f oldVel = getVelocity();
		float rot = 0;
		if (i.isKeyDown(Input.KEY_A)) {
			rot -=RPS * (float)elapsedTime / (float)1000;
		}
		if (i.isKeyDown(Input.KEY_D)) {
			rot += RPS * (float)elapsedTime / (float)1000;

		}
		updateTotalRotation();
		oldVel = oldVel.add(rot);
		float scale = ACC * (float) elapsedTime / (float)1000;
		Vector2f velup = new Vector2f(scale,0);
		velup.setTheta(getTotalRotation());
		if (i.isKeyDown(Input.KEY_W)){ 
			oldVel = oldVel.add(velup);
		}
		else if (i.isKeyDown(Input.KEY_S)) {
			velup = velup.negate();
			oldVel = oldVel.add(velup);
		} else {
			oldVel = oldVel.scale(Kf);
		}
		System.out.println(oldVel.length());
		oldVel = oldVel.scale(Math.min(MAXV/ oldVel.length(), 1));
		setVelocity(oldVel);
		this.addWorldPos(getVelocity());
		this.setRotation(rot);
		updateBounds();
		updateAnimation(elapsedTime);
		rotateBuffer(rot);
	}
	
	public void stopTheCar() {
		setVelocity(new Vector2f(0,0));
	}
	
	public Rectangle getVisibleMap(Dimension screenSize) {
		Vector2f tl = new Vector2f(getWorldPos().x - (float)screenSize.width / (float)2,
				getWorldPos().y - (float)screenSize.height / (float)2);
		return new Rectangle(tl.x, tl.y, tl.x + screenSize.width, tl.y + screenSize.height);
		
	}
	

}
