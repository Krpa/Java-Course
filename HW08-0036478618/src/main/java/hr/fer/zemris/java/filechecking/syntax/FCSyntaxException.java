package hr.fer.zemris.java.filechecking.syntax;

import hr.fer.zemris.java.filechecking.FileCheckingException;

/**
 * 
 * @author Ivan Krpelnik
 *
 */
public class FCSyntaxException extends FileCheckingException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public FCSyntaxException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public FCSyntaxException(String message) {
		super(message);
	}
}
