package hr.fer.zemris.java.filechecking.lexical;

import hr.fer.zemris.java.filechecking.FileCheckingException;

public class FCLexicalException extends FileCheckingException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public FCLexicalException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public FCLexicalException(String message) {
		super(message);
	}

	
}
