package hr.fer.zemris.java.custom.scripting.tokens;


/**
 * 
 * @author Ivan Krpelnik
 *
 * Token class that stores variable expression.
 */
public class TokenVariable extends Token {

	private String name;

	/**
	 * Constructor set name.
	 * @param name
	 */
	public TokenVariable(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * Returns name.
	 */
	@Override
	public String asText() {
		return this.name;
	}
	
}
