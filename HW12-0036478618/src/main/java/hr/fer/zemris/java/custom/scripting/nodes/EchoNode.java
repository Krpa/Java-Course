package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.*;
import hr.fer.zemris.java.custom.scripting.visitors.INodeVisitor;

/**
 * 
 * @author Ivan Krpelnik
 * 
 * Echo node class that stores tokens.
 *
 */
public class EchoNode extends Node {
	
	private Token[] tokens;

	/**
	 * Sets tokens.
	 * @param tokens
	 */
	public EchoNode(Token[] tokens) {
		super();
		this.tokens = tokens;
	}
	
	/**
	 * Getter for tokens.
	 * @return tokens
	 */
	public Token[] getTokens() {
		return tokens;
	}
	
	/**
	 * Returns String representation.
	 */
	@Override
	public String toString() {
		String s = "{$=";
		for(int i = 0; i < this.tokens.length; ++i)
			s += " " + this.tokens[i].asText();
		s += "$}";
		return s;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
