package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.visitors.INodeVisitor;

/**
 * 
 * @author Ivan Krpelnik
 * 
 * DocumentNode class.
 *
 */
public class DocumentNode extends Node {

	/**
	 * Returns String representation.
	 */
	@Override
	public String toString() {
		return "Document:\n";
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
