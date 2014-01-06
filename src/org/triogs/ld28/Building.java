package org.triogs.ld28;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Building {
	
	private FloatBuffer verts;
	private FloatBuffer color;
	private FloatBuffer uv;
	private int vboi = 0;
	private int vboc = 0;
	private int vbouv = 0;
	private static ByteBuffer tex1;
	private static int tex1id;
	private static int tex1w;
	private static int tex1h;
	
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
		try {
		PNGDecoder dec = new PNGDecoder(new URL("res/img/check.png").openStream());
		tex1w = dec.getWidth();
		tex1h = dec.getHeight();
		tex1 = BufferUtils.createByteBuffer(4 * tex1w * tex1h);
		dec.decode(tex1, tex1w * 4, PNGDecoder.Format.RGBA);
		tex1.flip();
		System.out.println(tex1w + "x" + tex1h);
		} catch (Exception e) {e.printStackTrace();}
	}
	public Building(Vector3f position, Vector3f dimensions) {
		uv = BufferUtils.createFloatBuffer(12 * 2 * 4);
		/*for (int i = 0; i< 3; i++) {
		uv.put(1.0f).put(0);
		uv.put(1.0f).put(1.0f);
		uv.put(0).put(0);
		}*/
		
		
		verts = BufferUtils.createFloatBuffer(12 * 3*4);
		//T1
		// Back Top Left
		verts.put(position.x).put(position.y).put(position.z);
		uv.put(0).put(1.0f);
		// Back Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z);
		uv.put(0).put(0);
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		uv.put(1.0f).put(0);
		
		//T2
		// Back Top Left
		verts.put(position.x).put(position.y).put(position.z);
		uv.put(0).put(1.0f);
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		uv.put(1.0f).put(0);
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		uv.put(1.0f).put(1.0f);
		
		//T3
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		uv.put(1.0f).put(0);
		// Front Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		uv.put(0).put(0);
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		uv.put(0).put(1.0f);
		
		//T4
		// Front Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z+dimensions.z); 
		uv.put(1.0f).put(1.0f);
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		uv.put(1.0f).put(0);
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		uv.put(0).put(1.0f);
		
		//T5
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		uv.put(0f).put(1.0f);
		// Front Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		uv.put(1.0f).put(1.0f);
		// Back Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z);
		uv.put(1.0f).put(0);
		
		//T6
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		uv.put(0).put(1.0f);
		// Back Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z);
		uv.put(1.0f).put(0);
		// Back Top Left
		verts.put(position.x).put(position.y).put(position.z);
		uv.put(0).put(0);
		
		//T7
		// Front Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z+dimensions.z);
		uv.put(1.0f).put(1.0f);
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		uv.put(1.0f).put(0);
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		uv.put(0).put(1.0f);
		
		//T8
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		uv.put(0).put(1.0f);
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		uv.put(1.0f).put(0);
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		uv.put(0).put(0);
		
		//T9
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		uv.put(1.0f).put(1.0f);
		// Back Top Left
		verts.put(position.x).put(position.y).put(position.z);
		uv.put(1.0f).put(0);
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		uv.put(0).put(0);
		
		//T10
		// Front Top Left
		verts.put(position.x).put(position.y).put(position.z + dimensions.z);
		uv.put(1.0f).put(1.0f);
		// Back Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z);
		uv.put(0).put(0);
		// Front Top Right
		verts.put(position.x + dimensions.x).put(position.y).put(position.z+dimensions.z); 
		uv.put(0).put(1.0f);
		
		//T11
		// Front Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		uv.put(0).put(1.0f);
		// Back Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z);
		uv.put(0).put(0);
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		uv.put(1.0f).put(0);
		
		//T12
		// Front Bottom Left
		verts.put(position.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		uv.put(0).put(1.0f);
		// Back Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z);
		uv.put(1.0f).put(0);
		// Front Bottom Right
		verts.put(position.x + dimensions.x).put(position.y + dimensions.y).put(position.z+dimensions.z);
		uv.put(1.0f).put(1.0f);
		/*
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
		*/
		verts.flip();
		uv.flip();
		
		try {
			PNGDecoder dec = new PNGDecoder(new FileInputStream("res/img/check.png"));
			tex1w = dec.getWidth();
			tex1h = dec.getHeight();
			tex1 = BufferUtils.createByteBuffer(4 * tex1w * tex1h);
			dec.decode(tex1, tex1w * 4, PNGDecoder.Format.RGBA);
			tex1.flip();
			System.out.println(tex1w + "x" + tex1h);
			/*for (int i = 0; i < tex1w * tex1h; i++) {
				System.out.println(tex1.get(i*4) + " ," + tex1.get(i * 4 + 1) +  " ," + tex1.get(i * 4 + 2) +" ," + tex1.get(i * 4 + 3));
			}*/
			} catch (Exception e) {e.printStackTrace();}
	}
	
	
	public void initgl() {
		SlickCallable.enterSafeBlock();
		vboi = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboi);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verts, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,  0);
		
		vbouv = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbouv);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uv, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		tex1id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex1id);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, tex1w, tex1h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, tex1);
		System.out.println(GLU.gluErrorString(GL11.glGetError()));
		
		SlickCallable.leaveSafeBlock();
		System.out.println("" + vboi);
		
	}
	
	public void draw(Rectangle screen) {
		SlickCallable.enterSafeBlock();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
       	GL11.glFrustum(-1.0f, 1.0f, -1.0f, 1.0f, 1f, 20.0f);
       	GLU.gluLookAt(0, 0, -5f, 0,0,0, 0, 1.0f, 0);

        //following statements define the location and rotation of object in the world.
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glScalef(5f, 5f, 1f);
        GL11.glTranslatef(screen.getLocation().x / (0.5f * screen.getWidth()), screen.getLocation().y / (0.5f * screen.getHeight()), 0);
        //GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboi);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbouv);
		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex1id);
		//GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 36); //36
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex2f(-.25f, -.25f);
			GL11.glTexCoord2f(1.0f, 0);
			GL11.glVertex2f(.25f, -.25f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex2f(.25f, .25f);
			GL11.glTexCoord2f(0, 1.0f);
			GL11.glVertex2f(-.25f, .25f);

		GL11.glEnd();
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
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
