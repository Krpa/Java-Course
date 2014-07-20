package hr.fer.zemris.java.tecaj.hw1;


/**
 * 
 * @author Ivan Krpelnik
 * 
 * Razred prima prirodni broj iz komandne linije. 
 * Za nevaljane argumente ispisuje na standard error pogrešku.
 * Za primljeni broj izračunava prvih toliko prostih brojeva i 
 * ispisuje ih na standardni izlaz.
 */
public class PrimeNumbers {

	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.err.println("Invalid number of arguments.");
			System.exit(1);
		}
		else if(Integer.parseInt(args[0]) < 1) {
			System.err.println("Argument must be greater than 0.");
			System.exit(1);
		}
		
		int n = Integer.parseInt(args[0]);
		System.out.println("You requested calculation of " + n + " prime numbers. "
				+ "Here they are:");
		primesOut(n);	
	}
	
	/**
	 * Metoda ispisuje na standardni izlaz prvih n prostih brojeva
	 * @param n koliko prostih brojeva računa metoda
	 */
	static void primesOut(int n) {
		
		if(n > 0)
			System.out.println("1. 2");
		
		int i = 3, k = 1;
		while(k < n) {
			boolean b = false;
			for(int j = 2; j < i; j++)
				if(i % j == 0) {
					b = true;
					break;
				}
			if(b == false) {
				k++;
				System.out.println(k + ". " + i);
			}
			i += 2;
		}
	}
}
