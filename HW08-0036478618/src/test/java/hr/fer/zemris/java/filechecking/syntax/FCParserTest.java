package hr.fer.zemris.java.filechecking.syntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.nodes.Node;

import org.junit.Test;


public class FCParserTest {

	@Test
	public void test() throws IOException {
		String program = ucitaj("provjere.txt");
		FCTokenizer tokenizer = new FCTokenizer(program);
		FCParser parser = new FCParser(tokenizer);
		System.out.println(obidjiStablo(parser.getProgramNode(), 1));
	}

	public String ucitaj(String fileName) throws IOException {
		return new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
	}
	
	public String obidjiStablo(Node curr, int indent) {
		String s = "";
		s += curr.toString() + "\n";
		int sz = curr.getChildren().size();
		for(int i = 0; i < sz; ++i) {
			s += String.format("%"+indent+"s", "") + obidjiStablo(curr.getChildren().get(i), indent+3) + "\n";
		}
		return s;
	}
}
