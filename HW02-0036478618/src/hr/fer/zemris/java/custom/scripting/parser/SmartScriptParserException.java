package hr.fer.zemris.java.custom.scripting.parser;

/**
 * @author Ivan Krpelnik
 * 
 * Exception class for SmartScriptParser
 *
 */
public class SmartScriptParserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    
    /**
     * Constructors
     */
	public SmartScriptParserException() {
		super();
	}

	public SmartScriptParserException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public SmartScriptParserException(String message) {
		super(message);
	}

	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}

    
}