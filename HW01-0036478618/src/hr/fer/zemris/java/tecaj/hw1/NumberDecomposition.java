package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Ivan Krpelnik
 * 
 * Razred ispisuje na standardni izlaz dekompoziciju prirodnog broja većeg od 1
 * Argument prima iz komandne linije, ako je argument nevaljan
 * ispisuje se pogreška na standard error
 */

public class NumberDecomposition {

	
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.err.println("Invalid number of arguments.");
			System.exit(1);
		}
		else if(Integer.parseInt(args[0]) <= 1){
			System.err.println("Argument must be greater than 1.");
			System.exit(1);
		}
		
		decompositionOut(Integer.parseInt(args[0]));
	}

	
	/**
	 * Metoda ispisuje na standardni izlaz dekompoziciju broja n
	 * @param n broj čija se dekompozicija ispisuje
	 */
	static void decompositionOut(int n) {
		
		System.out.println("You requested decomposition of number " + 
							n + " onto prime factors. Here they are:");
		int k = 1;
		for(int i = 2; n >= i; ++i) {
			while(n % i == 0) {
				System.out.println(k + ". " + i);
				k++;
				n /= i;
			}
		}
	}
	
}
