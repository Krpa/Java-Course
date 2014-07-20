package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.ErrorStringArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.FormatArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.NodeVisitor;

import java.util.Map;

/**
 * Razred koji služi za pohranu naredbe format.
 * @author Ivan Krpelnik
 *
 */
public class FormatNode extends Node {

	private FormatArgument arg;
	private ErrorStringArgument err;
	
	
	public FormatNode(FormatArgument arg, ErrorStringArgument err) {
		super();
		this.arg = arg;
		this.err = err;
	}

	public FormatArgument getArg() {
		return arg;
	}

	public String getFormat() {
		return arg.getFormat();
	}
	
	public ErrorStringArgument getErr() {
		return err;
	}
	
	public String getStrErr(Map<String, Object> variables) {
		return err != null ? err.getArgument(variables) : 
			("Naredba format nije prosla! Naredba: " + this);
	}

	@Override
	public String toString() {
		return "format: [" + arg + ", " + err + "]" + super.toString();
	}

	@Override
	public boolean accept(NodeVisitor visitor) {
		return visitor.visit(this);
	}
	
}
