package org.triogs.ld28;

import java.awt.Dimension;
import java.awt.Point;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.*;

public class Map {

	private TiledMap _bgmap;
	private boolean[][] _passMatrix;
	private Dimension _mapSize;
	private Dimension _tileSize;
	private Building bldg;
	
	public Map(String filepath) {
		try {
			_bgmap = new TiledMap(filepath);
			_mapSize = new Dimension(_bgmap.getWidth(), _bgmap.getHeight());
			_tileSize = new Dimension(_bgmap.getTileWidth(), _bgmap.getTileHeight());
			_passMatrix = new boolean[_mapSize.width][_mapSize.height];
			if (_bgmap.getObjectGroupCount() > 0) {
				initializeBldgs();
			}
			int gid = -1;
			for (int i = 0; i < _mapSize.width; i++) {
				for (int k = 0; k < _mapSize.height; k++) {
					// Find GID on collision layer
					gid = _bgmap.getTileId(i, k, 0);
					String prop = _bgmap.getTileProperty(gid, "passable", "true");
					if (prop.equalsIgnoreCase("TRUE")){
						_passMatrix[i][k] = true;
					} else {
						_passMatrix[i][k] = false;
					}
				}
			}
		}catch (SlickException e) {
			e.printStackTrace();
		}
	}
	public Dimension getTileSize() {
		return _tileSize;
	}
	public Dimension getMapSize() {
		return _mapSize;
	}
	public Dimension getMapSizePixels() {
		return new Dimension(_tileSize.width * _mapSize.width, _tileSize.height * _mapSize.height);
	}
	
	public boolean[][] getPassMatrix() {
		return _passMatrix;
	}
	
	public boolean collidesTaxi(Taxi t) {
		Vector2f pos = t.getWorldPos();
		Point index = new Point((int)(pos.x / _tileSize.width), (int)(pos.y / _tileSize.height));
		for (int i = index.x -2; i <= index.x + 2; i++) {
			for (int k = index.y - 2; k <= index.y + 2; k++ ) {
				if (!_passMatrix[i][k]) {
					// Construct a rectangle and check if it collides the taxi
					Rectangle col = new Rectangle(t.getPosition().x - (t.getWorldPos().x % _tileSize.width) + (i-index.x) * _tileSize.width,
							t.getPosition().y - (t.getWorldPos().y % _tileSize.height) + (k-index.y) * _tileSize.height,
							_tileSize.width, _tileSize.height);
					if (t.collides(col)) {
						// Find the direction we should return the car
						int x = index.x - i;
						int y = index.y - k;
						t.addWorldPos(new Vector2f(x,y).normalise());
						t.stopTheCar();
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean collidesScrub(Scrub s) {
		Vector2f pos = s.getWorldPos();
		Point index = new Point((int)(pos.x / _tileSize.width), (int)(pos.y / _tileSize.height));
		for (int i = index.x -1; i <= index.x + 1; i++) {
			for (int k = index.y - 1; k <= index.y + 1; k++ ) {
				if (!_passMatrix[i][k]) {
					// Construct a rectangle and check if it collides the taxi
					Rectangle col = new Rectangle(s.getPosition().x - (s.getWorldPos().x % _tileSize.width) + (i-index.x) * _tileSize.width,
							s.getPosition().y - (s.getWorldPos().y % _tileSize.height) + (k-index.y) * _tileSize.height,
							_tileSize.width, _tileSize.height);
					if (s.collides(col)) {
						// Find the direction we should return the car
						int x = index.x - i;
						int y = index.y - k;
						s.addWorldPos(new Vector2f(x,y).normalise().scale(3));
						s.freeze();
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean drawFrame(Graphics g, Scrub s) {
		Vector2f pos = s.getWorldPos();
		Point index = new Point((int)(pos.x / _tileSize.width), (int)(pos.y / _tileSize.height));
		for (int i = index.x -1; i <= index.x + 1; i++) {
			for (int k = index.y - 1; k <= index.y + 1; k++ ) {
				if (!_passMatrix[i][k]) {
					// Construct a rectangle and check if it collides the taxi
					Rectangle col = new Rectangle(s.getPosition().x - (s.getWorldPos().x % _tileSize.width) + (i-index.x) * _tileSize.width,
							s.getPosition().y - (s.getWorldPos().y % _tileSize.height) + (k-index.y) * _tileSize.height,
							_tileSize.width, _tileSize.height);
					g.draw(col);
					if (s.collides(col)) {
						// Find the direction we should return the car
						g.fill(col);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void drawFrame(Graphics g, Taxi t) {
		Vector2f pos = t.getWorldPos();
		Point index = new Point((int)(pos.x / _tileSize.width), (int)(pos.y / _tileSize.height));
		for (int i = index.x -2; i <= index.x + 2; i++) {
			for (int k = index.y - 2; k <= index.y + 2; k++ ) {
					// Construct a rectangle and check if it collides the taxi
				g.setColor(Color.white);
				Rectangle col = new Rectangle(t.getPosition().x - (t.getWorldPos().x % _tileSize.width) + (i-index.x) * _tileSize.width,
							t.getPosition().y - (t.getWorldPos().y % _tileSize.height) + (k-index.y) * _tileSize.height,
							_tileSize.width, _tileSize.height);
				if (t.collides(col) && !_passMatrix[i][k]) {
					g.setColor(Color.black);
					g.fill(col);
				} else
				g.draw(col);

			}
		}
		g.setColor(Color.white);
	}
	
	public void draw(Rectangle screen) {
		Vector2f remainder = new Vector2f(screen.getLocation().x % _tileSize.width, 
				screen.getLocation().y % _tileSize.height);
		remainder = remainder.negate();
		int width = (int)(screen.getWidth() / _tileSize.width);
		int height = (int)(screen.getHeight() / _tileSize.height);
		int sx =  (int)(screen.getLocation().x / _tileSize.width);
		int sy =  (int)(screen.getLocation().y / _tileSize.height);
		_bgmap.render((int)remainder.x, (int)remainder.y, sx, sy, width+1, height+1, 1, false);
		//_bgmap.render((int)remainder.x, (int)remainder.y, sx, sy, width+1, height+1, 2, false);
		bldg.draw(screen);
	}
	
	private void initializeBldgs() {
		for (int i = 0; i < _bgmap.getObjectCount(0); i++) {
			bldg = new Building(new Vector3f(_bgmap.getObjectX(0, i)/ 512f - 1.25f, _bgmap.getObjectY(0,i) / -384.0f + 1.0f, 0),
					new Vector3f((float)_bgmap.getObjectWidth(0,i) / 512f, (float)_bgmap.getObjectHeight(0,i)  / -384.0f, (float) -2f));
			bldg.initgl();
		}
		/*bldg = new Building(new Vector3f(-0.25f, -0.25f, 0),
				new Vector3f(0.5f, 0.5f,.5f));
		bldg.initgl();*/
		System.out.println(bldg);
	}
}
