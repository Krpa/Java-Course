package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * 
 * @author Ivan Krpelnik
 * 
 * Razred koja za dva realna broja računa površinu i opseg
 * odgovarajućeg pravokutnika.
 * 
 * Može primiti argumente iz komandne linije ili sa standardnog ulaza.
 * Ako su primljeni argumenti preko komandne linije nevaljani,
 * ispisuje se greška na standard error.
 * Ako nisu primljeni nikakvi argumenti, primaju se preko standardnog ulaza.
 */

public class Rectangle {
	

	public static void main(String[] args) throws IOException {

		double edge[] = new double[2]; 
		double area, perimeter;
		String[] podatak = new String[] {"width", "height"};
		
		/*
		 * Slucaj da su primljeni argumenti iz komandne linije
		 */
		
		if(args.length != 0) {
			if(args.length == 2) {
				System.out.println(args[0] + " " + args[1]);
				edge[0] = Double.parseDouble(args[0]);
				edge[1] = Double.parseDouble(args[1]);
				area = edge[0] * edge[1];
				perimeter = edge[0] * 2 + edge[1] * 2;
				ispis(edge, area, perimeter);
			}
			
			else {
				System.err.println("Invalid number of arguments was provided.");
				System.exit(1);
			}
		}
		
		
		/*
		 * Ucitavanje podataka sa standardnog ulaza
		 */
		
		else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
			String redak = null; 
			for(int i = 0; i < 2; ++i) {
				boolean b = false;
				while(b == false) {
					System.out.println("Please provide " + podatak[i] + ":");
					redak = reader.readLine();
					redak.trim();
					if(redak == null || redak.isEmpty()) {
						System.out.println("Nothing was given.");
						continue;
					}
					else {
						edge[i] = Double.parseDouble(redak);
						if(Math.signum(edge[i]) < 0)
							System.out.println(podatak[i] + " is negative.");
						else
							b = true;
					}
				}
			}
			
			perimeter = edge[0] * 2 + edge[1] * 2;
			area = edge[0] * edge[1];
			ispis(edge, area, perimeter);
		}
	}
	
	
	/**
	 * Metoda na standardni izlaz ispisuje stranice, povrsinu i opseg pravokutnika
	 * @param edge polje realnih brojeva za ispis stranica pravokutnika
	 * @param area realni broj za ispis površine
	 * @param perimeter realni broj za ispis opsega
	 */
	private static void ispis(double[] edge, double area, double perimeter) {
		System.out.printf("You have specifed a rectangle with width %.1f and height "
				+ "%.1f. Its area is %.1f and its perimeter is %.1f\n", 
				edge[0], edge[1], area, perimeter);
	}
	
	
	
}
