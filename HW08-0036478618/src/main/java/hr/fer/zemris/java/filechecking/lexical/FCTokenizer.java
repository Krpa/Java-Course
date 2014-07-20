package hr.fer.zemris.java.filechecking.lexical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Razred koji služi za generiranje tokena iz danog programa.
 * @author Ivan Krpelnik
 *
 */
public class FCTokenizer {

	/**
	 * Polje znakova koji čine izvorni kod programa koji se obrađuje.
	 */
	private char[] data;
	/**
	 * Kazaljka na prvi neobrađeni znak u polju <code>data</code>.
	 */
	private int curPos;
	/**
	 * Posljednji token koji je stvoren analizom izvornog koda programa. 
	 */
	private FCToken currentToken;

	/**
	 * Statička mapa tipova tokena koje je moguće utvrditi prema samo jednom
	 * znaku (znak == token).
	 */
	private static final Map<Character, FCTokenType> mapper;
	
	// Inicijalizacija mape mapper
	static {
		mapper = new HashMap<>();
		mapper.put(Character.valueOf('!'), FCTokenType.INVERT);
		mapper.put(Character.valueOf('{'), FCTokenType.OPEN_PARENTHESES);
		mapper.put(Character.valueOf('}'), FCTokenType.CLOSED_PARENTHESES);
		mapper.put(Character.valueOf('@'), FCTokenType.AT);
		mapper.put(Character.valueOf('i'), FCTokenType.CASE_INSENSITIVE);
	}
	

	/**
	 * Skup svih ključnih riječi programa.
	 */
	private static final Set<String> keywords;

	// Inicijalizacija skupa ključnih riječi
	static {
		keywords = new HashSet<>();
		keywords.add("exists");
		keywords.add("filename");
		keywords.add("format");
		keywords.add("fail");
		keywords.add("def");
		keywords.add("terminate");
	}
	
	/**
	 * Konstruktor. Prima izvorni kod programa kao <code>String</code>.
	 * @param program izvorni kod programa
	 * @throws FCLexicalException ako dođe do pogreške pri tokenizaciji
	 */
	public FCTokenizer(String program) {
		data = program.toCharArray();
		curPos = 0;
		extractNextToken();
	}

	/**
	 * Metoda dohvaća trenutni token. Metoda se može zvati više puta i uvijek
	 * će vratiti isti token, sve dok se ne zatraži izlučivanje sljedećeg
	 * tokena.
	 * 
	 * @return trenutni token
	 */
	public FCToken getCurrentToken() {
		return currentToken;
	}
	
	/**
	 * Metoda izlučuje sljedeći token, postavlja ga kao trenutnog i odmah ga
	 * i vraća.
	 * @throws FCLexicalException ako dođe do problema pri tokenizaciji
	 */
	public FCToken nextToken() {
		extractNextToken();
		return getCurrentToken();
	}
	
	/**
	 * Metoda koju zove nextToken() da bi se izlučio sljedeći token.
	 * @throws FCLexicalException ako dođe do problema pri tokenizaciji
	 */
	private void extractNextToken() {
		
		// Ako je već prije utvrđen kraj, ponovni poziv metode je greška:
		if(currentToken != null && currentToken.getTokenType() == FCTokenType.EOF) {
			throw new FCLexicalException("No tokens available."); 
		}
				
		// Inače preskoči praznine:
		skipBlanks();
		while(skipComments())
			skipBlanks();
		
		// Ako više nema znakova, generiraj token za kraj izvornog koda programa
		if(curPos >= data.length) {
			currentToken = new FCToken(FCTokenType.EOF, null);
			return;
		}

		// Vidi je li trenutni znak neki od onih koji direktno generiraju token:
		FCTokenType mappedType = mapper.get(Character.valueOf(data[curPos]));
		if(mappedType != null) {
			if(mappedType == FCTokenType.INVERT && !(curPos < data.length-1 && Character.isLetter(data[curPos+1]))) {
				throw new FCLexicalException("Znak za inverziju naredbe mora stajati uz naredbu.");
			} else if(mappedType == FCTokenType.CASE_INSENSITIVE && 
					!(curPos < data.length-1 && data[curPos+1] == '\"')) {}
			else {
				// Stvori takav token:
				currentToken = new FCToken(mappedType, null);
				// Postavi trenutnu poziciju na sljedeći znak:
				curPos++;
				return;
			}
		}
		
		if(data[curPos] == '\"') {
			int endPos;
			for(endPos = curPos+1; endPos < data.length && data[endPos] != '\"'; ++endPos);
			if(endPos == data.length) {
				throw new FCLexicalException("String nema zavrsetak.");
			}
			curPos++;
			List<FCToken> lista = new ArrayList<>();
			while(true) {
				if(curPos == endPos) {
					currentToken = new FCToken(FCTokenType.STRING, lista);
					curPos++;
					return;
				}
				if(data[curPos] == '$' && data[curPos+1] == '{') {
					lista.add(parseVariable());
					curPos++;
				} else if(data[curPos] == ':') {
					curPos++;
					lista.add(new FCToken(FCTokenType.PACKAGE, new FCPackage(String.valueOf(data, curPos, endPos-curPos))));
					curPos = endPos;
				} else if(checkChar()) {
					lista.add(parseTekst());
				} else {
					throw new FCLexicalException("Nevaljani znak u stringu: " + data[curPos]);
				}
			}
		}
		
		if(Character.isLetter(data[curPos])) {
			int beginPos = curPos;
			for(; curPos < data.length && !checkBlank(); ++curPos);
			String string = String.valueOf(data, beginPos, curPos - beginPos);
			if(keywords.contains(string)) {
				currentToken = new FCToken(FCTokenType.KEYWORD, string);
			} else {
				currentToken = new FCToken(FCTokenType.ARGUMENT, string);
			}
			return;
		}
		
		throw new FCLexicalException("Nepoznat znak: " + data[curPos]);
	}
	
	/**
	 * Parsira varijablu unutar stringa.
	 * @return {@link FCToken} vrste REPLACE
	 */
	private FCToken parseVariable() {
		curPos += 2;
		int beginPos = curPos;
		for(; curPos < data.length && data[curPos] != '}'; ++curPos);
		if(curPos == data.length) {
			throw new FCLexicalException("Nema znaka za kraj umetanja varijable.");
		}
		return new FCToken(FCTokenType.REPLACE, new FCVariable(String.valueOf(data, beginPos,  curPos - beginPos)));
	}
	
	/**
	 * Parsira tekst unutar stringa
	 * @return {@link FCToken} vrste TEXT
	 */
	private FCToken parseTekst() {
		int beginPos = curPos;
		for(; curPos < data.length && checkChar(); ++curPos);
		return new FCToken(FCTokenType.TEXT, String.valueOf(data, beginPos, curPos - beginPos));
	}
	
	/**
	 * Provjerava da li je trenutni znak neki koji ne pripada imenu naredbe
	 * @return rezultat provjere
	 */
	private boolean checkChar() {
		return data[curPos] != '$' && data[curPos] != '{' && data[curPos] != '}' && data[curPos] != '\\' 
				&& data[curPos] != '\"';
	}
	
	/**
	 * Provjerava da li je trenutni znak praznina
	 * @return rezultat provjere
	 */
	private boolean checkBlank() {
		return  data[curPos] == ' ' || data[curPos] == '\t' || 
				data[curPos] =='\r' || data[curPos] =='\n';
	}
	
	/**
	 * Metoda pomiče kazaljku trenutnog znaka tako da preskače sve prazne znakove
	 * (razmaci, prelasci u novi red, tabulatori).
	 */
	private void skipBlanks() {
		while(curPos<data.length) {
			if(checkBlank()) {
				curPos++;
				continue;
			}
			break;
		}
	}
	
	/**
	 * Metoda koja preskače komentare
	 * @return true ako su preskočeni komentari
	 */
	private boolean skipComments() {
		if(curPos >= data.length || data[curPos] != '#')
			return false;
		for(; curPos < data.length && data[curPos] != '\n'; ++curPos);
		return true;
	}
}
