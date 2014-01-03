package org.triogs.ld28;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import java.awt.Point;

import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Vector2f;

public class VIP extends Sprite {
	
	public static String[] LastNames;
	public static String[] MaleNames;
	public static String[] FemaleNames;
	
	private static final float MAXV = (float) 70;
	private static final float AGGRO_MIN = 50;
	private static final float AGGRO_MAX = 500;
	
	private static UnicodeFont nameFont;
	
	private Image textbubble;
	private static Point tbrenderoffs;
	private int msgindex = -1;
	public boolean isPassenger = false;
	
	// Static methods
	public static void loadNames() {
		LastNames = new String[100];
		MaleNames = new String[100];
		FemaleNames = new String[100];
		Path path = Paths.get("res/txt/lastnames.csv");
		try (Scanner scanner =  new Scanner(path)){
			int i = 0;
			while (scanner.hasNextLine()){
				LastNames[i] = scanner.nextLine().toUpperCase();
				i++;
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		path = Paths.get("res/txt/malefirst.csv");
		try (Scanner scanner =  new Scanner(path)){
			int i = 0;
			while (scanner.hasNextLine()){
				MaleNames[i] = scanner.nextLine().toUpperCase();
				Character.toUpperCase(MaleNames[i].charAt(0));
				i++;
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		path = Paths.get("res/txt/femalefirst.csv");
		try (Scanner scanner =  new Scanner(path)){
			int i = 0;
			while (scanner.hasNextLine()){
				FemaleNames[i] = scanner.nextLine().toUpperCase();
				i++;
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static final String[][] messages = {
		{"Help me please!", "I don't have AIDS!"},
		{"Bring me with you!", "I don't want to die!"},
		{"I can help you!", "Don't leave me here!"}
	};
	
	//Object Properties
	private int firstName;
	private int lastName;
	private TraitSet traits;
	
	public VIP() {
		super("res/img/vip" + new Random().nextInt(2) + ".png", true, new Dimension(64,64), BoundType.CIRCULAR);
		createPersona();
		tbrenderoffs = new Point(-126, -150);
		try {
			textbubble = new Image("res/img/textbubble.png");
			nameFont = new UnicodeFont("res/font/Minecraftia.ttf", 16, false, false);
			nameFont.addAsciiGlyphs();
			ColorEffect e = new ColorEffect();
			e.setColor(java.awt.Color.white);
			nameFont.getEffects().add(e);
			nameFont.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	public void freeze() {
		this.setVelocity(new Vector2f(0,0));
	}
	
	private void createPersona() {
		Random r = new Random();
		firstName = r.nextInt(100);
		lastName = r.nextInt(100);
		traits = new TraitSet();
		System.out.println(this.getFullName());
	}
	
	public String getFullName() {
		return (traits.isMale() ? MaleNames[firstName] : FemaleNames[firstName]) + " " + LastNames[lastName];
	}
	
	public TraitSet getTraits() {
		return traits;
	}
	
	public void update(int elapsedTime, Taxi t, Map m) {
		// If the taxi is within a certain distance, turn the scrub toward the car and 
		// start running at it. Otherwise stop and don't do anything
		float dist = this.getWorldPos().distance(t.getWorldPos());
		Vector2f toTaxi;
		if (dist > AGGRO_MAX || dist < AGGRO_MIN) {
			if (dist > AGGRO_MAX) {
				msgindex = -1;
			}
			toTaxi = this.getWorldPos().copy().sub(t.getWorldPos());
			this.setPosition(t.getPosition().copy().add(toTaxi));
			this.freeze();
			return;
		}
		if (msgindex == -1) {
			msgindex = new Random().nextInt(messages.length);
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
		//m.collidesScrub(this);
		updateBounds();
	}
	
	public void drawWithText(Graphics g) {
		if (!isPassenger) {
			this.draw(g);
		if (msgindex != -1) {
			g.drawImage(textbubble, this.getPosition().x + tbrenderoffs.x, this.getPosition().y + tbrenderoffs.y);
			nameFont.drawString(this.getPosition().x + tbrenderoffs.x + 15, this.getPosition().y + tbrenderoffs.y + 15,
					messages[msgindex][0]);
			nameFont.drawString(this.getPosition().x + tbrenderoffs.x + 15, this.getPosition().y + tbrenderoffs.y + 35,
					messages[msgindex][1]);
		}
		}
	}
	
	public void collidesTaxi(Taxi t) {
		if(this.collides(t)) {
			this.addWorldPos(getVelocity().copy().negate().normalise().scale(3));
		}
	}
	
	public void clearMessage() {
		msgindex = -1;
	}
	
	
	
}
