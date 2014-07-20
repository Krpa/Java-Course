package hr.fer.zemris.java.filechecking.syntax.nodes.arguments;

import hr.fer.zemris.java.filechecking.lexical.FCPackage;
import hr.fer.zemris.java.filechecking.lexical.FCToken;
import hr.fer.zemris.java.filechecking.lexical.FCTokenType;
import hr.fer.zemris.java.filechecking.lexical.FCVariable;
import hr.fer.zemris.java.filechecking.syntax.FCSyntaxException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Razred koji sluzi za pohranu stringova kao argumenata.
 * Sadrži tokene stringa i zna ih pretvoriti u konkretan string. 
 * @see Argument
 * @author Ivan Krpelnik
 *
 */
public class StringArgument extends Argument {

	private FCToken argument;
	
	public StringArgument(FCToken argument) {
		if(argument == null || argument.getTokenType() != FCTokenType.STRING) {
			throw new IllegalArgumentException();
		}
		this.argument = argument;
	}

	@SuppressWarnings("unchecked")
	public String getArgument(Map<String, Object> var) {
		StringBuilder sb = new StringBuilder();
		List<FCToken> lista = (List<FCToken>)argument.getValue();
		for(FCToken token : lista) {
			if(token.getTokenType() == FCTokenType.REPLACE) {
				if(var.containsKey(((FCVariable)token.getValue()).getName())) {
					sb.append(var.get(((FCVariable)token.getValue()).getName()));
				} else {
					throw new FCSyntaxException("Ne postoji varijabla imena: " + ((FCVariable)token.getValue()).getName());
				}
			} else if(token.getTokenType() == FCTokenType.PACKAGE) {
				sb.append(((FCPackage)token.getValue()).toPathString());
			} else if(token.getTokenType() == FCTokenType.TEXT){
				sb.append(token.getValue());
			} else {
				throw new FCSyntaxException("Nepoznat token.");
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public boolean checkArgument(Set<String> vars) {
		List<FCToken> lista = (List<FCToken>)argument.getValue();
		for(FCToken token : lista) {
			if(token.getTokenType() == FCTokenType.REPLACE && !vars.contains(((FCVariable)token.getValue()).getName())) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "StrArg: " + argument.toString();
	}
	
}
