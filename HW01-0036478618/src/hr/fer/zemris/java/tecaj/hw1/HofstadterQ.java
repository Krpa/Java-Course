package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Ivan Krpelnik
 * 
 * Razred ispisuje na standardni izlaz i-ti broj Hofstadterova Q-niza
 * Argument prima iz komandne linije, ukoliko je argument nevaljan,
 * na standard error se ispisuje pogreška
 */

public class HofstadterQ {

	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.err.println("Invalid number of arguments.");
			System.exit(1);
		}
		else if(Long.parseLong(args[0]) < 1) {
			System.err.println("Argument must be greater than 0.");
			System.exit(1);
		}
		
		long arg = Long.parseLong(args[0]);
		long sol = calculate(arg);
		System.out.println("You requested calculation of " + arg + 
						". number of Hofstadter's Q-sequence." +
						" The requested number is " + sol + ".");

	}

	/**
	 * Metoda za izracunavanje x-tog broja Hofstadterova Q-niza
	 * @param x indeks broja koji se računa
	 * @return vraća se x-ti broj Hofstadterova Q-niza
	 */
	static long calculate(long x) {
		
		if(x == 1 || x == 2)
			return 1;
		
		return calculate(x - calculate(x - 1)) + calculate(x - calculate(x - 2));
	}
	
}
