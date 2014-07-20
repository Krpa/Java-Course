package hr.fer.zemris.java.filechecking.demo;

import hr.fer.zemris.java.filechecking.lexical.FCTokenType;
import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Demonstracija razreda Tokenizer. Program ispisuje na ekran tokene dobivene iz koda
 * koji se nalazi u datoteci provjere.txt
 * @author Ivan Krpelnik
 *
 */

public class FCTokenizerDemo {

	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.out.println("Ocekuje se 1 argument.");
			System.exit(0);
		}
		String program = ucitaj(args[0]);
		FCTokenizer tokenizer = new FCTokenizer(program);
		while(tokenizer.getCurrentToken().getTokenType() != FCTokenType.EOF) {
			System.out.println("Tip: " + tokenizer.getCurrentToken().getTokenType());
			System.out.println("Token: " + tokenizer.getCurrentToken().getValue());
			tokenizer.nextToken();
		}
	}

	public static String ucitaj(String fileName) throws IOException {
		return new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
	}
}
