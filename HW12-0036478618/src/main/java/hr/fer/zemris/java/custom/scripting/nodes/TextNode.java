package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.visitors.INodeVisitor;


/**
 * 
 * @author Ivan Krpelnik
 * 
 * TextNode class.
 * Stores text as a String.
 */
public class TextNode extends Node {

	private String text;
	
	/**
	 * Constructor sets text.
	 * @param text
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}
	
	/**
	 * Getter for text.
	 * @return String text
	 */
	public String getText() {
		 return this.text;
	}
	
	/**
	 * Returns String representation.
	 */
	@Override
	public String toString() {
		return this.getText();
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
