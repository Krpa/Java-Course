package hr.fer.zemris.java.tecaj.hw6.shell;

/**
 * Enum for {@link MyShell}.
 * Contains 3 symbols: PROMPT, MORELINES, MULTILINE. Each can be set.
 * @author Ivan Krpelnik
 *
 */
public enum ShellSymbol {
	/**
	 * Prompt symbol
	 */
	PROMPT('>'),
	
	/**
	 * More lines symbol
	 */
	MORELINES('\\'),
	
	/**
	 * Multiline symbol
	 */
	MULTILINE('|');
	
	private char symbol;
	
	private ShellSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	public static ShellSymbol parse(String s) {
		
		if(s.equals("PROMPT")) {
			return PROMPT;
		} 
		if(s.equals("MORELINES")) {
			return MORELINES;
		}
		if(s.equals("MULTILINE")) {
			return MULTILINE;
		}
		
		return null;
	}
	
	public char getSymbol() {
		return symbol;
	}
}
