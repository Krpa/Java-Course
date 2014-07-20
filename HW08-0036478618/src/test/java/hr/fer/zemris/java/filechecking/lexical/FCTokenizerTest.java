package hr.fer.zemris.java.filechecking.lexical;


import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class FCTokenizerTest {

	@Test
	public void test() throws IOException{
		String program = ucitaj("provjere_za_test.txt");
		FCTokenizer FCTokenizer = new FCTokenizer(program);
		FCTokenType[] tokens = generatedByHand();
		int i = 0;
		while(FCTokenizer.getCurrentToken().getTokenType() != FCTokenType.EOF) {
			System.out.println("Tip: " + FCTokenizer.getCurrentToken().getTokenType());
			System.out.println("Token: " + FCTokenizer.getCurrentToken().getValue());
			assertEquals(tokens[i], FCTokenizer.getCurrentToken().getTokenType());
			FCTokenizer.nextToken();
			i++;
		}
	}
	
	@Test
	public void testStringTokens() {
		String string = "\"tekst${src}:hr.fer.zemris.java.tecaj.hw5.problem1a\"";
		FCTokenizer FCTokenizer = new FCTokenizer(string);
		FCTokenType[] strTokens = new FCTokenType[3];
		strTokens[0] = FCTokenType.TEXT;
		strTokens[1] = FCTokenType.REPLACE;
		strTokens[2] = FCTokenType.PACKAGE;
		FCToken token = FCTokenizer.getCurrentToken();
		if(token.getValue() instanceof List<?>) {
			List<FCToken> lista = (List<FCToken>) token.getValue();
			int i = 0;
			for(FCToken t : lista) {
				assertEquals(strTokens[i], t.getTokenType());
				i++;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	private FCTokenType[] generatedByHand() {
		FCTokenType[] tokens = new FCTokenType[50];
		tokens[0] = FCTokenType.KEYWORD;
		tokens[1] = FCTokenType.CASE_INSENSITIVE;
		tokens[2] = FCTokenType.STRING;
		tokens[3] = FCTokenType.KEYWORD;
		tokens[4] = FCTokenType.ARGUMENT;
		tokens[5] = FCTokenType.OPEN_PARENTHESES;
		tokens[6] = FCTokenType.KEYWORD;
		tokens[7] = FCTokenType.ARGUMENT;
		tokens[8] = FCTokenType.STRING;
		tokens[9] = FCTokenType.INVERT;
		tokens[10] = FCTokenType.KEYWORD;
		tokens[11] = FCTokenType.ARGUMENT;
		tokens[12] = FCTokenType.STRING;
		tokens[13] = FCTokenType.KEYWORD;
		tokens[14] = FCTokenType.ARGUMENT;
		tokens[15] = FCTokenType.STRING;
		tokens[16] = FCTokenType.KEYWORD;
		tokens[17] = FCTokenType.ARGUMENT;
		tokens[18] = FCTokenType.STRING;
		tokens[19] = FCTokenType.KEYWORD;
		tokens[20] = FCTokenType.ARGUMENT;
		tokens[21] = FCTokenType.STRING;
		tokens[22] = FCTokenType.KEYWORD;
		tokens[23] = FCTokenType.CLOSED_PARENTHESES;
		tokens[24] = FCTokenType.KEYWORD;
		tokens[25] = FCTokenType.AT;
		tokens[26] = FCTokenType.STRING;
		tokens[27] = FCTokenType.KEYWORD;
		return tokens;
	}
	
	private String ucitaj(String fileName) throws IOException {
		return new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
	}
}
