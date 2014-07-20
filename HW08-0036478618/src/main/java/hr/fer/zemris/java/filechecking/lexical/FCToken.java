package hr.fer.zemris.java.filechecking.lexical;

/**
 * 
 * @author Ivan Krpelnik
 *
 */
public class FCToken {
	
	/**
	 * Vrsta tokena.
	 */
	private FCTokenType tokenType;
	/**
	 * Vrijednost tokena.
	 */
	private Object value;
	
	/**
	 * Konstruktor.
	 * @param tokenType vrsta tokena
	 * @param value vrijednost tokena
	 */
	public FCToken(FCTokenType tokenType, Object value) {
		super();
		if(tokenType==null) throw new IllegalArgumentException("Token type can not be null.");
		this.tokenType = tokenType;
		this.value = value;
	}
	
	/**
	 * Dohvat vrste tokena.
	 * @return vrsta tokena
	 */
	public FCTokenType getTokenType() {
		return tokenType;
	}
	
	/**
	 * Dohvat vrijednosti tokena.
	 * @return vrijednost tokena ili <code>null</code> ako token ove vrste nema pridruženu vrijednost
	 */
	public Object getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return tokenType + ": " + value;
	}
}
