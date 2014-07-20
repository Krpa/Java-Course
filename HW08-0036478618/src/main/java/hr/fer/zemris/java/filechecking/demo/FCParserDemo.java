package hr.fer.zemris.java.filechecking.demo;

import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.FCParser;
import hr.fer.zemris.java.filechecking.syntax.nodes.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Demonstracija razreda Parser. Program ispisuje na ekran
 * parsirani sadržaj datoteke provjere.txt
 * @author Ivan Krpelnik
 *
 */
public class FCParserDemo {

	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.out.println("Ocekuje se 1 argument.");
			System.exit(0);
		}
		String program = ucitaj(args[0]);
		FCTokenizer tokenizer = new FCTokenizer(program);
		FCParser parser = new FCParser(tokenizer);
		System.out.println(obidjiStablo(parser.getProgramNode(), 1));
	}
	

	public static String ucitaj(String fileName) throws IOException {
		return new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
	}
	
	public static String obidjiStablo(Node curr, int indent) {
		
		String s = "";
		
		s += curr.toString() + "\n";
		int sz = curr.getChildren().size();
		for(int i = 0; i < sz; ++i) {
			s += String.format("%"+indent+"s", "") + obidjiStablo(curr.getChildren().get(i), indent+3) + "\n";
		}
		return s;
	}
}
