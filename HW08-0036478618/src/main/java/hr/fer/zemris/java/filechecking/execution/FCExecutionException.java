package hr.fer.zemris.java.filechecking.execution;

import hr.fer.zemris.java.filechecking.FileCheckingException;


/**
 * 
 * @author Ivan Krpelnik
 *
 */
public class FCExecutionException extends FileCheckingException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public FCExecutionException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public FCExecutionException(String message) {
		super(message);
	}

}
