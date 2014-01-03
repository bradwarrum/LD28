package org.triogs.ld28;

import java.util.Random;

public class TraitSet {
	// Static fields
	public static final int NUMLINES = 6;
	private int _age;
	private byte _mechanical;
	private byte _culinary;
	private byte _survival;
	private byte _morale;
	private byte _fitness;
	private boolean _male;
	
	private String[] s = new String[NUMLINES];
	
	public TraitSet() {
		Random r = new Random(System.nanoTime());
		_male = r.nextBoolean();
		_age = r.nextInt(63);
		_age += 16;
		_fitness = (byte) r.nextInt(10);
		_mechanical = (byte) r.nextInt(10);
		_culinary = (byte) r.nextInt(10);
		_survival = (byte) r.nextInt(10);
		_morale = (byte) r.nextInt(10);
		int i = 0;
		s[0] = _age + "yr old " +  (_male ? "male" : "female");
		s[1] = "MECHANICAL: ";
		for ( i = 0; i < (_mechanical + 1); i++) { s[1] += "O";}
		for ( i = _mechanical + 1; i < 10; i++) { s[1] += "_";}

		s[2] = "CULINARY:   ";
		for ( i = 0; i < (_culinary + 1); i++) { s[2] += "O";}
		for ( i = _culinary + 1; i < 10; i++) { s[2] += "_";}
		s[3] = "SURVIVAL:   ";
		for ( i = 0; i < (_survival + 1); i++) { s[3] += "O";}
		for ( i = _survival + 1; i < 10; i++) { s[3] += "_";}
		s[4] = "FITNESS:    ";
		for ( i = 0; i < (_fitness + 1); i++) { s[4] += "O";}
		for ( i = _fitness + 1; i < 10; i++) { s[4] += "_";}
		s[5] = "MORALE:     ";
		for ( i = 0; i < (_morale + 1); i++) { s[5] += "O";}
		for ( i = _morale + 1; i < 10; i++) { s[5] += "_";}
	
	}
	public boolean isMale() {
		return _male;
	}
	
	public String[] cString() {
		return s;
	}
	
}