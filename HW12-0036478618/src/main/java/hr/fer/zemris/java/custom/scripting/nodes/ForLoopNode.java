package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.*;
import hr.fer.zemris.java.custom.scripting.visitors.INodeVisitor;

/**
 * @author Ivan Krpelnik
 *
 * ForLoopNode class.
 * Stores tokens: variable, startExpression, endExpression, stepExpression.
 */
public class ForLoopNode extends Node {

	private TokenVariable variable;
	private Token startExpression;
	private Token endExpression;
	private Token stepExpression;
	
	/**
	 * Constructor sets variable, startExpression, endExpression, stepExpression
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression, Token stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * variable getter
	 * @return TokenVariable variable
	 */
	public TokenVariable getVariable() {
		return this.variable;
	}

	/**
	 * startExpression getter
	 * @return Token startExpression
	 */
	public Token getStartExpression() {
		return startExpression;
	}

	/**
	 * endExpression getter
	 * @return Token endExpression
	 */
	public Token getEndExpression() {
		return endExpression;
	}

	/**
	 * stepExpression getter
	 * @return Token stepExpression
	 */
	public Token getStepExpression() {
		return stepExpression;
	}

	/**
	 * Returns String representation.
	 */
	@Override
	public String toString() {
		String s = "{$FOR";
		s += " " + this.variable.asText();
		s += " " + this.startExpression.asText();
		s += " " + this.endExpression.asText();
		if(this.stepExpression != null)
			s += " " + this.stepExpression.asText();
		s += "$}";
		return s;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
