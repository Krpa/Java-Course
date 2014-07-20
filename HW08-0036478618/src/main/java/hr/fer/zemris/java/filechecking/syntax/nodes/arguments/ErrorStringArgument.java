package hr.fer.zemris.java.filechecking.syntax.nodes.arguments;

import hr.fer.zemris.java.filechecking.lexical.FCToken;

/**
 * Error string.
 * @see StringArgument
 * @author Ivan Krpelnik
 *
 */
public class ErrorStringArgument extends StringArgument {

	public ErrorStringArgument(FCToken argument) {
		super(argument);
	}

	@Override
	public String toString() {
		return "Err" + super.toString();
	}
}
