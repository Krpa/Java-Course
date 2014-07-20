package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.ErrorStringArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.StringArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.NodeVisitor;

import java.util.Map;


/**
 * Razred koji služi za pohranu naredbe filename.
 * @author Ivan Krpelnik
 *
 */
public class FileNameNode extends Node {

	private StringArgument arg;
	private ErrorStringArgument err;
	
	public FileNameNode(StringArgument arg, ErrorStringArgument err) {
		super();
		this.arg = arg;
		this.err = err;
	}

	public StringArgument getArg() {
		return arg;
	}

	public String getStrArg(Map<String, Object> variables) {
		return arg.getArgument(variables);
	}
	
	public ErrorStringArgument getErr() {
		return err;
	}
	
	public String getStrErr(Map<String, Object> variables) {
		return err != null ? err.getArgument(variables) : 
							("Naredba filename nije prosla! Naredba: " + this);
	}

	@Override
	public String toString() {
		return "filename: [" + arg + ", " + err + "]" + super.toString();
	}

	@Override
	public boolean accept(NodeVisitor visitor) {
		return visitor.visit(this);
	}
}
