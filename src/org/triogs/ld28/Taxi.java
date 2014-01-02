package org.triogs.ld28;

import java.awt.Dimension;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Taxi extends Sprite {
	private static final float ACC = (float) 200;
	private static final float Kf = (float) .99;
	private static final float MAXV =(float) 600;
	private static final float RPS = (float) 70;

	
	public Taxi(Dimension screenSize, Vector2f startingPos) {
		super("res/img/taxi.png", false, new Dimension(0, 0), BoundType.RECTANGULAR);
		setPosition(new Vector2f((float)(screenSize.width / 2.0), (float)(screenSize.height / 2.0)));
		setWorldPos(startingPos);
	}

	
	public void update(Input i, int elapsedTime) {
		Vector2f oldVel = getVelocity();
		float dt = (float)elapsedTime / (float) 1000;
		float rot = (float)oldVel.getTheta();
		if (i.isKeyDown(Input.KEY_A)) {
			addRotation( - RPS * dt);
			rot -= RPS * dt;
		}
		if (i.isKeyDown(Input.KEY_D)) {
			addRotation( RPS * dt);
			rot += RPS * dt;

		}
		oldVel.setTheta(rot);
		float oldabs = oldVel.lengthSquared();
		Vector2f acc = new Vector2f(1,0);
		acc.setTheta(getRotation());
		acc.scale(ACC * dt);
		if (i.isKeyDown(Input.KEY_W)) {
			oldVel.add(acc);
			if (oldVel.lengthSquared() < oldabs) {
				oldVel.add(acc.copy().scale(3));
			}
		}else if (i.isKeyDown(Input.KEY_S)) {
			oldVel.sub(acc);
			if (oldVel.lengthSquared() < oldabs) {
				oldVel.sub(acc.copy().scale(3));
			}
		}else {
			oldVel.scale(Kf);
		}
		oldVel = MathT.clamp(oldVel, MAXV);
		setVelocity(oldVel);
		addWorldPos(getVelocity().copy().scale(dt));
		updateBounds();
		updateAnimation(elapsedTime);
	}
	
	public void stopTheCar() {
		setVelocity(new Vector2f(0,0));
	}
	
	public float speedAsPercentage() {
		return getVelocity().length() / MAXV;
	}
	
	public Rectangle getVisibleMap(Dimension screenSize) {
		Vector2f tl = new Vector2f(getWorldPos().x - (float)screenSize.width / (float)2,
				getWorldPos().y - (float)screenSize.height / (float)2);
		return new Rectangle(tl.x, tl.y, screenSize.width, screenSize.height);
		
	}
	

}
