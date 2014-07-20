package hr.fer.zemris.java.custom.scripting.tokens;


/**
 * 
 * @author Ivan Krpelnik
 * 
 * Token class that stores integer expression.
 */
public class TokenConstantInteger extends Token {
	
	private int value;

	/**
	 * Constructor sets value.
	 * @param value
	 */
	public TokenConstantInteger(int value) {
		super();
		this.value = value;
	}
	
	/**
	 * Returns String representation of stored integer value.
	 */
	@Override
	public String asText() {
		return Integer.toString(this.value);
	}
	
	@Override
	public String toString() {
		return Integer.toString(this.value);
	}
}
