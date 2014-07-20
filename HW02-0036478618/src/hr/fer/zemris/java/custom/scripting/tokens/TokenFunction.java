package hr.fer.zemris.java.custom.scripting.tokens;


/**
 * 
 * @author Ivan Krpelnik
 *
 * Token class that stores function expression.
 */
public class TokenFunction extends Token {

	private String name;
	
	/**
	 * Constructor sets name.
	 * @param name
	 */
	public TokenFunction(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * Returns functions name.
	 */
	@Override
	public String asText() {
		return this.name;
	}
}
