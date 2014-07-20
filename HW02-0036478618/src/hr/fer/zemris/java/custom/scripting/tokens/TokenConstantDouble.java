package hr.fer.zemris.java.custom.scripting.tokens;


/**
 * 
 * @author Ivan Krpelnik
 * 
 * Token class that stores a double expression.
 */
public class TokenConstantDouble extends Token {

	private double value;
	
	/**
	 * Constructor sets value.
	 * @param value
	 */
	public TokenConstantDouble(double value) {
		super();
		this.value = value;
	}
	
	/**
	 * Returns String representation of stored double value.
	 */
	@Override
	public String asText() {
		return Double.toString(this.value);
	}
}
