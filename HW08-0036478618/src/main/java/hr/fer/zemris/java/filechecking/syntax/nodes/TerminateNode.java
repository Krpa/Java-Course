package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.NodeVisitor;

/**
 * Razred koji služi za pohranu naredbe terminate.
 * @author Ivan Krpelnik
 *
 */
public class TerminateNode extends Node {

	public TerminateNode() {
		super();
	}


	@Override
	public String toString() {
		return "Terminate";
	}

	@Override
	public boolean accept(NodeVisitor visitor) {
		return visitor.visit(this);	
	}
}
