package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.ErrorStringArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.StringArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.NodeVisitor;

import java.util.Map;

/**
 * Razred koji služi za pohranu naredbe exists.
 * @author Ivan Krpelnik
 *
 */
public class ExistsNode extends Node {

	private boolean isDir;
	private StringArgument value;
	private ErrorStringArgument err;
	
	public ExistsNode(boolean isDir, StringArgument value) {
		super();
		this.isDir = isDir;
		this.value = value;
	}

	public ExistsNode(boolean isDir, StringArgument value, ErrorStringArgument err) {
		this(isDir, value);
		this.isDir = isDir;
		this.value = value;
		this.err = err;
	}
	
	
	public boolean dir() {
		return isDir;
	}
	
	public StringArgument getValue() {
		return value;
	}
	
	public String getStrArg(Map<String, Object> variables) {
		return value.getArgument(variables);
	}
	
	public String getStrErr(Map<String, Object> variables) {
		return err != null ? err.getArgument(variables) : 
			("Naredba exists nije prosla! Naredba: " + this);
	}
	
	public ErrorStringArgument getErr() {
		return err;
	}
	
	@Override
	public String toString() {
		return "Exists: [" + (isDir ? "d, " : "f, ") + value.toString() + ", " + err + "]" + super.toString();
	}

	@Override
	public boolean accept(NodeVisitor visitor) {
		return visitor.visit(this);
	}
}
