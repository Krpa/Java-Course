package hr.fer.zemris.java.filechecking.syntax.nodes.arguments;

import hr.fer.zemris.java.filechecking.lexical.FCVariable;

/**
 * Variable argument.
 * Sadrži varijablu.
 * @see Argument
 * @see FCVariable
 * @author Ivan Krpelnik
 */
public class VariableArgument extends Argument {

	private FCVariable var;
	
	public VariableArgument(FCVariable value) {
		this.var = value;
	}
	
	public FCVariable getVar() {
		return var;
	}
	
	public String getName() {
		return var.getName();
	}
	
	@Override
	public String toString() {
		return "VarArg: " + var.toString();
	}
}
