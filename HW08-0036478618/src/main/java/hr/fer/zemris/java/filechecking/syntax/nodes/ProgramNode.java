package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.NodeVisitor;

import java.util.List;

/**
 * Razred koji služi za pohranu korijena sintaksnog stabla.
 * @author Ivan Krpelnik
 *
 */
public class ProgramNode extends Node {

	public ProgramNode() {
		super();
	}
	
	public ProgramNode(List<Node> children) {
		super(children);
	}

	@Override
	public String toString() {
		return "program:";
	}

	@Override
	public boolean accept(NodeVisitor visitor) {
		return visitor.visit(this);
	}
}
