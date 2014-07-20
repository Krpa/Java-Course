package hr.fer.zemris.java.filechecking.syntax.nodes;

import java.util.Map;

import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.ErrorStringArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.NodeVisitor;

/**
 * Razred koji služi za pohranu naredbe fail.
 * @author Ivan Krpelnik
 *
 */
public class FailNode extends Node {

	private ErrorStringArgument err;
	
	public FailNode(ErrorStringArgument err) {
		super();
		this.err = err;
	}

	public ErrorStringArgument getErr() {
		return err;
	}


	public String getStrErr(Map<String, Object> variables) {
		return err != null ? err.getArgument(variables) : 
			("Naredba fail je izvrsena.");
	}
	
	@Override
	public String toString() {
		return "Fail: [" + err + "]";
	}

	@Override
	public boolean accept(NodeVisitor visitor) {
		return visitor.visit(this);
	}
	
}
