package hr.fer.zemris.java.filechecking;

/**
 * 
 * @author Ivan Krpelnik
 *
 */
public class FileCheckingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public FileCheckingException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public FileCheckingException(String message) {
		super(message);
	}
}
