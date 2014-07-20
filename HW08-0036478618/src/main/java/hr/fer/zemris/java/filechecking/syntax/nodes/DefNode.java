package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.StringArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.VariableArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.NodeVisitor;

import java.util.Map;

/**
 * Razred koji služi za pohranu naredbe def.
 * @author Ivan Krpelnik
 *
 */
public class DefNode extends Node {
	
	private VariableArgument var;
	private StringArgument value;
	
	public DefNode(VariableArgument var, StringArgument value) {
		super();
		this.var = var;
		this.value = value;
	}
	
	public VariableArgument getVar() {
		return var;
	}
	
	public String getName() {
		return var.getName();
	}
	
	public StringArgument getValue() {
		return value;
	}
	
	public String getStrArg(Map<String, Object> variables) {
		return value.getArgument(variables);
	}
	
	@Override
	public String toString() {
		return "Def: [" + var.toString() + ", " + value.toString() + "]";
	}

	@Override
	public boolean accept(NodeVisitor visitor) {
		return visitor.visit(this);
	}
}
