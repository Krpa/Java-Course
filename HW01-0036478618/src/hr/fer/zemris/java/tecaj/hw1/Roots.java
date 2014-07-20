package hr.fer.zemris.java.tecaj.hw1;

import java.text.DecimalFormat;

/**
 * 
 * @author Ivan Krpelnik
 * 
 * Razred za izračunavanje n-tog korijena iz nekog kompleksnog broja
 * Argumente prima iz komandne linije u obliku:
 * prvi_broj(realni dio kompleksnog broja) drugi_broj(imaginarni dio) treci_broj(korijen)
 * Rezultate ispisuje na standardni izlaz
 * U slučaju krivih argumenata ispisuje se pogreška na standard error
 */

public class Roots {

	public static void main(String[] args) {
		
		if(args.length != 3) {
			System.err.println("Invalid arguments.");
			System.exit(1);
		}
		
		else if(Integer.parseInt(args[2]) < 2) {
			System.err.println("Root argument must be greater than 1.");
			System.exit(1);
		}
		
		System.out.println("You request calculation of " + args[2] + ". roots. Solutions are: ");
		calculateRoots(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
	}

	/**
	 * Metoda računa kompleksne korijene nekog kompleksnog broja i
	 * ispisuje rezultate na standardni izlaz
	 * @param x realan dio kompleksnog broja čiji se korijeni računaju
	 * @param y imaginarni dio kompleksnog broja čiji se korijeni računaju
	 * @param n broj koji određuje koji korijen se računa
	 */
	static void calculateRoots(double x, double y, double n) {
		double r, fi;
		
		r = nRoot(x * x + y * y, 2*n);
		fi = Math.atan(y/x);
		
		for(int i = 0; i < n; ++i) {
			System.out.print(i+1 + ". ");
			ispis(r * Math.cos((fi + 2*Math.PI*i)/n), "0.##;-0.##");
			ispis(r * Math.sin((fi + 2*Math.PI*i)/n), " + 0.##i; - 0.##i");
			System.out.println("");
		}
	}
	
	/**
	 * Metoda vraća n-ti korijen nekog broja
	 * @param a broj čiji se korijen računa
	 * @param b koji korijen se računa
	 * @return vraća se b-ti korijen od a
	 */
	static double nRoot(double a, double b) {
		return Math.pow(a, 1./b);
	}
	
	/**
	 * Metoda za ispis realnog broja za zadani format
	 * @param d broj koji se ispisuje
	 * @param format zadani format
	 */
	static void ispis(double d, String format) {
		DecimalFormat formatter = new DecimalFormat(format);
		System.out.print(formatter.format(d));
	}
}
