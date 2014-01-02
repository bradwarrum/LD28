package org.triogs.ld28;

import java.awt.Dimension;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class Sprite {
	private Animation _frames;
	private boolean _animated;
	private Image _bufImage;
	private float _rotation;
	private Vector2f _position;
	private Vector2f _velocity;
	private Shape _bounds;
	private BoundType _btype;
	private Rectangle _initBound;
	private Vector2f _mapPos;
	
	public static enum BoundType {
		NONE,
		CIRCULAR,
		RECTANGULAR
	}
	/**
	 * Creates a new sprite from the designated file.
	 * @param fileref
	 * Accesses the sprite sheet through the file reference provided.
	 * @param animated
	 * Set this to true if the sprite should be animated.
	 * @param tilesize
	 * Tile size of individual tiles inside the sprite sheet. Only used for animated sprites.
	 * @param bounding
	 * Sets the bounding type to NONE, CIRCULAR, or RECTANGULAR.
	 */
	public Sprite(String fileref, boolean animated, Dimension tilesize, BoundType bounding) {
		try {
			_position = new Vector2f(0,0);
			_rotation = 0;
			_velocity = new Vector2f(0,0);
			_animated = animated;
			Image img = new Image(fileref);
			_bufImage = new Image(0,0);
			SpriteSheet ss;
			if (!animated) {
				tilesize = new Dimension(img.getWidth(), img.getHeight());
				ss = new SpriteSheet(img, img.getWidth(), img.getHeight());
				_frames = new Animation(ss, 1);
				_frames.setAutoUpdate(false);
			} else {
				ss = new SpriteSheet(img, tilesize.width, tilesize.height);
				_frames = new Animation(ss, 60);
				_frames.setAutoUpdate(false);
				_frames.setLooping(true);
			}
			_btype = bounding;
			if (bounding == BoundType.CIRCULAR) {
				double distsq = Math.pow((double)tilesize.width / 2.0, 2.0) + Math.pow((double)tilesize.height / 2.0, 2.0);
				float dist = (float)Math.sqrt(distsq);
				_bounds = new Circle(0,0,dist);
			}else if (bounding == BoundType.RECTANGULAR) {
				_bounds = new Rectangle(0,0, tilesize.width, tilesize.height);
				_initBound = (Rectangle)_bounds;
			}else {
				_bounds = null;
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void setBoundScale(float scale) {
		if (_btype == BoundType.CIRCULAR) {
			_bounds = _bounds.transform(Transform.createScaleTransform(scale, scale));
		}
	}
	
	public void setPosition(Vector2f pos) {
		_position = pos;
		if (_btype != BoundType.NONE) {
			_bounds.setCenterX(_position.x);
			_bounds.setCenterY(_position.y);
			if (_btype == BoundType.RECTANGULAR) {
				_initBound.setCenterX(_bounds.getCenterX());
				_initBound.setCenterY(_bounds.getCenterY());
			}
		}
	}
	public void addPosition(Vector2f delta) {
		_position = _position.add(delta);
		if (_btype != BoundType.NONE) {
			_bounds.setCenterX(_position.x);
			_bounds.setCenterY(_position.y);
			if (_btype == BoundType.RECTANGULAR) {
				_initBound.setCenterX(_bounds.getCenterX());
				_initBound.setCenterY(_bounds.getCenterY());
			}
		}
	}
	
	public Vector2f getPosition() {
		return _position;
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
	
	public void setVelocity(Vector2f vel) {
		_velocity = vel;
	}
	
	public Vector2f getVelocity() {
		return _velocity;
	}
	
	public void setRotation(float theta) {
		_rotation = theta;
		rotateBuffer(_rotation);
	}
	
	public void addRotation(float delta) {
		_rotation += delta;
		while (_rotation >=360) {
			_rotation -= 360;
		}
		while (_rotation < 0) {
			_rotation += 360;
		}
		rotateBuffer(_rotation);
	}
	
	public float getRotation() {
		return _rotation;
	}
	public void update(Input i, int elapsedTime) {
		updateBounds();
		updateAnimation(elapsedTime);
	}
	
	protected void updateAnimation(int elapsedTime) {
		if (_animated) {
			_frames.update(elapsedTime);
		}
		_bufImage = _frames.getCurrentFrame();
	}
	
	protected void updateBounds() {
		if(_btype == BoundType.CIRCULAR){
			_bounds.setCenterX(_position.x);
			_bounds.setCenterY(_position.y);
		}else if (_btype == BoundType.RECTANGULAR) {
			_bounds = _initBound;
			_bounds.setCenterX(_position.x);
			_bounds.setCenterY(_position.y);
			_bounds = _bounds.transform(Transform.createRotateTransform((float)Math.toRadians(_rotation), _position.x, _position.y));
		}
	}
	protected void rotateBuffer(float theta) {
		_bufImage.setRotation(theta);
	}
	
	public boolean collides (Sprite s) {
		if (s._btype == BoundType.NONE || this._btype == BoundType.NONE){
			return false;
		}
		return this._bounds.intersects(s._bounds);
	}
	
	public boolean collides (Shape s) {
		if (this._btype == BoundType.NONE) {
			return false;
		}
		return this._bounds.intersects(s);
	}
	
	public void draw(Graphics g) {
		draw(g, false);
	}
	
	public void draw(Graphics g, boolean showBounds) {
		g.drawImage(_bufImage, (float)(_position.x- .5 * _bufImage.getWidth()), 
				(float)(_position.y-.5*_bufImage.getHeight()));
		if (showBounds) {
			if(_btype != BoundType.NONE){
				g.draw(_bounds);
			}
		}
	}
	

}
