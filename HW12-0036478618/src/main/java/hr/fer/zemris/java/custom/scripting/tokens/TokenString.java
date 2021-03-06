package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * 
 * @author Ivan Krpelnik
 *
 * Token class that stores string expression.
 *
 */
public class TokenString extends Token {

	private String value;
	
	/**
	 * Constructor sets string value.
	 * @param value
	 */
	public TokenString(String value) {
		super();
		this.value = value;
	}
	
	/**
	 * Returns value.
	 */
	@Override
	public String asText() {
		return value.substring(1, value.length()-1);
	}
	
	@Override
	public String toString() {
		return value;
	}
}
