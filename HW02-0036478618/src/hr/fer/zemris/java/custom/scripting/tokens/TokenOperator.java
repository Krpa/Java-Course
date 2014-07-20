package hr.fer.zemris.java.custom.scripting.tokens;


/**
 * 
 * @author Ivan Krpelnik
 * 
 * Token class that stores operator expression.
 */
public class TokenOperator extends Token {

	private String symbol;
	
	/**
	 * Constructor sets symbol.
	 * @param symbol
	 */
	public TokenOperator(String symbol) {
		super();
		this.symbol = symbol;
	}
	
	/**
	 * Returns symbol.
	 */
	@Override
	public String asText() {
		return this.symbol;
	}
}
