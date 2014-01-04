package org.triogs.ld28;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.SlickCallable;

public class Building {
	
	private FloatBuffer verts;
	private FloatBuffer color;
	private int vboi = 0;
	private int vboc = 0;
	
	public Building() {
		Vector3f [] v = new Vector3f[8];
		for (int i = 0; i < 8; i++) {
			v[i] = new Vector3f((float)0.5, (float)-0.5, 0);
		}
		verts = BufferUtils.createFloatBuffer(36);
		for (int i = 0; i < 8; i ++) {
			verts.put( v[i].x).put( v[i].y).put( v[i].z);
		}
		System.out.println(verts);
	}
	public Building(Vector3f position, Vector3f dimensions) {
		verts = BufferUtils.createFloatBuffer(12 * 3*4);
		/*//T1
		// Back Top Left
		verts.put(position.x).put(position.y).put(position.z);
		// Back Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z);
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		
		//T2
		// Back Top Left
		verts.put(position.x).put(position.y).put(position.z);
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		
		//T3
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		// Front Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		
		//T4
		// Front Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z+dimensions.z); 
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		
		//T5
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		// Front Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		// Back Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z);
		
		//T6
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		// Back Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z);
		// Back Top Left
		verts.put(position.x).put(position.y).put(position.z);
		
		//T7
		// Front Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z+dimensions.z);
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		
		//T8
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		
		//T9
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		// Back Top Left
		verts.put(position.x).put(position.y).put(position.z);
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		
		//T10
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		// Front Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z+dimensions.z); 
		
		//T11
		// Front Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		// Back Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z);
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		
		//T12
		// Front Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);*/
		
		//L1
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		// Back Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z);
				
		// Back Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z);
		// Back Top Left
		verts.put(position.x).put(position.y).put(position.z);
		
		
		
		// Back Top Left
		verts.put(position.x).put(position.y).put(position.z);
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		// Front Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		
		// Front Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		// Front Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z+dimensions.z); 
		
		// Front Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z+dimensions.z); 
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);

		verts.flip();
		
		color = BufferUtils.createFloatBuffer(32);
		for (int i = 0; i < 32; i++) {
			color.put(1.0f);
		}
	}
	
	public void initgl() {
		SlickCallable.enterSafeBlock();
		vboi = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboi);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verts, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,  0);
		vboc = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboc);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, color, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,  0);
		

		SlickCallable.leaveSafeBlock();
		System.out.println("" + vboi);
	}
	
	public void draw(Rectangle screen) {
		SlickCallable.enterSafeBlock();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
       	GL11.glFrustum(-1.0f, 1.0f, -1.0f, 1.0f, 1f, 20.0f);
        //GLU.gluPerspective(45.0f, 1024.0f/768.0f,.25f, 20.0f);
        //GL11.glOrtho(-1.0f, 1.0f, -1.0f, 1.0f, 5.0f, 100.0f);
       	GLU.gluLookAt(0, 0, -5f, 0,0,0, 0, 1.0f, 0);

        //following statements define the location and rotation of object in the world.
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glScalef(5f, 5f, 1f);
        GL11.glTranslatef(screen.getLocation().x / (0.5f * screen.getWidth()), screen.getLocation().y / (0.5f * screen.getHeight()), 0);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboi);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0,0);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDrawArrays(GL11.GL_LINES, 0, 16);
		GL11.glFlush();
		SlickCallable.leaveSafeBlock();

	}
	@Override
	public String toString() {
		String s = "PRISMv\n";
		for (int i = 0; i<16; i++) {
			s += "{";
			for (int k = 0; k < 2; k++) {
				s += verts.get(i* 3 + k) + ",";
			}
			s += verts.get(i * 3 + 2) + "}\n";
		}
		return s;
	}
}
