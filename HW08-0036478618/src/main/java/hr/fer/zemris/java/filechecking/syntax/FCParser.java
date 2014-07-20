package hr.fer.zemris.java.filechecking.syntax;

import hr.fer.zemris.java.filechecking.lexical.FCLexicalException;
import hr.fer.zemris.java.filechecking.lexical.FCTokenType;
import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.lexical.FCVariable;
import hr.fer.zemris.java.filechecking.syntax.nodes.DefNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.ExistsNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FailNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FileNameNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FormatNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.Node;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.TerminateNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.CaseInsStringArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.ErrorStringArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.FormatArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.StringArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.VariableArgument;

import java.util.Stack;

/**
 * Razred koji služi za sintaksnu obradu tokena koje generira razred {@link FCTokenizer}.
 * @author Ivan Krpelnik
 *
 */
public class FCParser {

	/**
	 * Tokenizator izvornog koda.
	 */
	private FCTokenizer tokenizer;
	/**
	 * Stablo koje predstavlja parsirani program.
	 */
	private ProgramNode programNode;
	
	/**
	 * Konstruktor.
	 * @param tokenizer tokenizator izvornog koda
	 * @throws FCSyntaxException u slučaju pogreške pri parsiranju
	 * @throws FCLexicalException u slučaju pogreške pri tokenizaciji
	 */
	public FCParser(FCTokenizer tokenizer) {
		this.tokenizer = tokenizer;
		programNode = parse();
	}

	/**
	 * Dohvat stabla nastalog parsiranjem izvornog koda.
	 * @return stablo programa
	 */
	public ProgramNode getProgramNode() {
		return programNode;
	}
	
	/**
	 * Generira korijen sintaksnog stabla iz internog tokenizatora.
	 * @return - korijen sintaksnog stabla
	 * @throws - {@link FCSyntaxException}
	 */
	private ProgramNode parse() {
		ProgramNode root = new ProgramNode();
		Stack<Node> stack = new Stack<>();
		stack.push(root);
		while(true) {
			if(stack.empty()) {
				throw new FCSyntaxException("Previse zatvorenih zagrada.");
			}
			// Ako je kraj programa, gotovi smo:
			if(tokenizer.getCurrentToken().getTokenType() == FCTokenType.EOF) {
				if(stack.size() != 1) {
					throw new FCSyntaxException("Nisu svi blokovi zatvoreni.");
				}
				break;
			}
			
			boolean reversed = false;
			if(tokenizer.getCurrentToken().getTokenType() == FCTokenType.INVERT) {
				reversed = true;
				tokenizer.nextToken();
			}
			
			if(tokenizer.getCurrentToken().getTokenType() != FCTokenType.KEYWORD) {
				throw new FCSyntaxException("Ocekuje se kljucna rijec. Dobiveno: \"" + tokenizer.getCurrentToken() +"\"");
			}
			
			Node statement = null;
			Object keyword = tokenizer.getCurrentToken().getValue();
			
			if("def".equals(keyword)) {
				statement = parseDef();
			} else if("exists".equals(keyword)) {
				statement = parseExists();
			} else if("filename".equals(keyword)) {
				statement = parseFileName();
			} else if("format".equals(keyword)) {
				statement = parseFormat();
			} else if("fail".equals(keyword)) {
				statement = parseFail();
			} else if("terminate".equals(keyword)) {
				statement = parseTerminate();
			}
			
			if(statement != null) {
				if(!isConditional(statement) && reversed) {
					throw new FCSyntaxException("Ne moze se obrnuti znacenje naredbe koja ne radi neku provjeru.");
				}
				statement.setReverse(reversed);
				stack.peek().pushNode(statement);
				if(tokenizer.getCurrentToken().getTokenType() == FCTokenType.OPEN_PARENTHESES && isConditional(statement)) {
					stack.push(statement);
					tokenizer.nextToken();
				} else if(tokenizer.getCurrentToken().getTokenType() == FCTokenType.CLOSED_PARENTHESES) {
					stack.pop();
					tokenizer.nextToken();
				}
			} else {
				throw new FCSyntaxException("Nepoznata naredba." + tokenizer.getCurrentToken());
			}
		}
		return root;
	}
	
	/**
	 * Metoda koja provjerava da li je dani čvor instanca nekog koji je naredba koja izvršava neku provjeru. 
	 * @param statement - dani čvor
	 * @return rezultat provjere
	 */
	private boolean isConditional(Node statement) {
		return (statement instanceof ExistsNode) || (statement instanceof FailNode) ||
				(statement instanceof FileNameNode) || (statement instanceof FormatNode);
	}
	
	/**
	 * Pretvara tokene u argumente naredbe def.
	 * @return {@link DefNode}
	 * @throws FCSyntaxException - ako su krivi argumenti
	 */
	private Node parseDef() {
		if(tokenizer.nextToken().getTokenType() != FCTokenType.ARGUMENT) {
			throw new FCSyntaxException("Prvi argument naredbe def mora biti ime varijable."
					+ "Dobiveno: " + tokenizer.getCurrentToken());
		}
		String name = (String)tokenizer.getCurrentToken().getValue();
		if(tokenizer.nextToken().getTokenType() != FCTokenType.STRING) {
			throw new FCSyntaxException("Drugi argument naredbe def mora biti string."
					+ "Dobiveno: " + tokenizer.getCurrentToken());
		}
		StringArgument argument = new StringArgument(tokenizer.getCurrentToken());
		tokenizer.nextToken();
		return new DefNode(new VariableArgument(new FCVariable(name)), argument);
	}
	
	/**
	 * Pretvara tokene u argumente naredbe exists.
	 * @return {@link ExistsNode}
	 * @throws FCSyntaxException - ako su krivi argumenti
	 */
	private Node parseExists() {
		if(tokenizer.nextToken().getTokenType() != FCTokenType.ARGUMENT) {
			throw new FCSyntaxException("Nije dobar prvi argument naredbe exists."
					+ "Dobiveno: " + tokenizer.getCurrentToken());
		}
		String fileType = (String)tokenizer.getCurrentToken().getValue();
		if(!checkFileType(fileType)) {
			throw new FCSyntaxException("Naredba exists ne poznaje argument: " + fileType);
		}
		if(tokenizer.nextToken().getTokenType() != FCTokenType.STRING) {
			throw new FCSyntaxException("Drugi argument naredbe def mora biti string."
					+ "Dobiveno: " + tokenizer.getCurrentToken());
		}
		StringArgument argument = new StringArgument(tokenizer.getCurrentToken());
		return new ExistsNode(fileType.charAt(0) == 'd', argument, parseErrorMessage());
	}
	
	/**
	 * Pretvara tokene u argumente naredbe filename.
	 * @return {@link FileNameNode}
	 * @throws FCSyntaxException - ako su krivi argumenti
	 */
	private Node parseFileName() {
		if(tokenizer.nextToken().getTokenType() != FCTokenType.CASE_INSENSITIVE &&
				tokenizer.getCurrentToken().getTokenType() != FCTokenType.STRING) {
			throw new FCSyntaxException("Krivi argument za filename naredbu."
					+ "Dobiveno: " + tokenizer.getCurrentToken());
		}
		boolean caseInsensitive = false;
		if(tokenizer.getCurrentToken().getTokenType() == FCTokenType.CASE_INSENSITIVE) {
			caseInsensitive = true;
			tokenizer.nextToken();
		}
		if(tokenizer.getCurrentToken().getTokenType() != FCTokenType.STRING) {
			throw new FCSyntaxException("Argument naredbe parsefile mora biti stć2ring."
					+ "Dobiveno: " + tokenizer.getCurrentToken());
		}
		StringArgument argument;
		if(caseInsensitive) {
			argument = new CaseInsStringArgument(tokenizer.getCurrentToken());
		} else {
			argument = new StringArgument(tokenizer.getCurrentToken());
		}
		return new FileNameNode(argument, parseErrorMessage());
	}
	
	/**
	 * Pretvara tokene u argumente naredbe format.
	 * @return {@link FormatNode}
	 * @throws FCSyntaxException - ako su krivi argumenti
	 */
	private Node parseFormat() {
		if(tokenizer.nextToken().getTokenType() != FCTokenType.ARGUMENT) {
			throw new FCSyntaxException("Kriva vrsta argumenta za format naredbu."
					+ "Dobiveno: " + tokenizer.getCurrentToken());
		}
		return new FormatNode(new FormatArgument((String)(tokenizer.getCurrentToken().getValue())), parseErrorMessage());
	}
	
	/**
	 * Pretvara tokene u argumente naredbe fail.
	 * @return {@link FailNode}
	 * @throws FCSyntaxException - ako su krivi argumenti
	 */
	private Node parseFail() {
		return new FailNode(parseErrorMessage());
	}
	
	/**
	 * Pretvara tokene u argumente naredbe terminate.
	 * @return {@link TerminateNode}
	 */
	private Node parseTerminate() {
		tokenizer.nextToken();
		return new TerminateNode();
	}
	
	/**
	 * Metoda koja sprema string pogreške ako takav postoji u tokenima.
	 * @param lista - lista u koju se sprema pogreška.
	 * @throw {@link FCSyntaxException} - ako nakon tokena @ ne slijedi token koji predstavlja string.
	 */
	private ErrorStringArgument parseErrorMessage() {
		if(tokenizer.nextToken().getTokenType() == FCTokenType.AT) {
			if(tokenizer.nextToken().getTokenType() == FCTokenType.STRING) {
				ErrorStringArgument arg = new ErrorStringArgument(tokenizer.getCurrentToken());
				tokenizer.nextToken();
				return arg;
			} else {
				throw new FCSyntaxException("Nakon znaka @ treba ići string.");
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Provjerava da li je argument naredbe format dobar.
	 * @param fileType - argument naredbe
	 * @return rezultat provjere
	 */
	private boolean checkFileType(String fileType) {
		return "dir".equals(fileType) || "di".equals(fileType) || "d".equals(fileType) ||
				"file".equals(fileType) || "fil".equals(fileType) || "fi".equals(fileType) || "f".equals(fileType);
 	}
}
