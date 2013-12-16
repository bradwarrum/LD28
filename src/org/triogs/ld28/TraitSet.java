package org.triogs.ld28;

import java.util.Random;

public class TraitSet {
	// Static fields
	public static final String[] occupations = new String[]{"Unemployed"};
	private int _age;
	private int _job;
	private int _fitness;
	private boolean _male;
	
	public TraitSet() {
		Random r = new Random();
		_male = r.nextBoolean();
		_job = r.nextInt(occupations.length);
		_age = r.nextInt(63) + 18;
		_fitness = r.nextInt(100);
	}
	
	public boolean isMale() {
		return _male;
	}
	
}