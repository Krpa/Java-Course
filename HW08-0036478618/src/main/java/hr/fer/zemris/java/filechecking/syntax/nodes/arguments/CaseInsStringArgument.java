package hr.fer.zemris.java.filechecking.syntax.nodes.arguments;

import hr.fer.zemris.java.filechecking.lexical.FCToken;

/**
 * Case insensitive string.
 * @see StringArgument
 * @author Ivan Krpelnik
 *
 */
public class CaseInsStringArgument extends StringArgument {

	public CaseInsStringArgument(FCToken argument) {
		super(argument);
	}
	
	@Override
	public String toString() {
		return "CaseIns" + super.toString();
	}
}
