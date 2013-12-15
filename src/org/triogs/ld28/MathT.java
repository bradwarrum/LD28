package org.triogs.ld28;

import org.newdawn.slick.geom.Vector2f;

public class MathT {
	public static Vector2f clamp(Vector2f v, float maxAbs) {
		return v.scale((float)Math.min(maxAbs / v.length(), 1.0));
	}
}
