package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Prog5 {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		String prvi = ucitaj(reader, "prvi");
		String drugi = ucitaj(reader, "drugi");
		
		if(prvi.trim().replaceAll("\\s+", " ").split(" ").length != 
				drugi.trim().replaceAll("\\s+", " ").split(" ").length) {
			System.out.println("Vektori trebaju biti istih dimenzija");
		} else {
			IVector n = Vector.parseSimple(prvi);
			IVector m = Vector.parseSimple(drugi);
			System.out.println(n.scalarMultiply((n.scalarProduct(m))*2*1/(n.norm()*n.norm())).sub(m));
		}
	}

	private static String ucitaj(BufferedReader reader, String string) throws IOException {
		String prvi;
		String[] parsePrvi;
		do {
			System.out.println("Upisite " + string + " vektor (2 ili 3 dimenzije): ");
			prvi = reader.readLine();
			parsePrvi = prvi.trim().replaceAll("\\s+",	" ").split(" ");
		} while(parsePrvi.length != 2 && parsePrvi.length != 3);
		return prvi;
	}

}
