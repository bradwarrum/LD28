package org.triogs.ld28;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class VIP {
	
	public static String[] LastNames;
	public static String[] MaleNames;
	public static String[] FemaleNames;
	
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
	
	//Object Properties
	private int firstName;
	private int lastName;
	private TraitSet traits;
	
	public VIP() {
		createPersona();
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
	
}
