package hr.fer.zemris.web.radionice;


public class InconsistentDatabaseException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InconsistentDatabaseException() {
		super();
	}

	public InconsistentDatabaseException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InconsistentDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public InconsistentDatabaseException(String message) {
		super(message);
	}

	public InconsistentDatabaseException(Throwable cause) {
		super(cause);
	}


	
}
