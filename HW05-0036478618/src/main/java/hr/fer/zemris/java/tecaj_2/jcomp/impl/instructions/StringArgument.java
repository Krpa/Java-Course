package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj.hw5.filters.FilterBasic;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Implementacija argumenta koji sadrži String.
 * @see FilterBasic
 * @author Ivan Krpelnik
 *
 */
public class StringArgument implements InstructionArgument {

	private String string;
	
	public StringArgument(String string) {
		this.string = string;
	}
	
	@Override
	public boolean isRegister() {
		return false;
	}

	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public boolean isNumber() {
		return false;
	}

	@Override
	public Object getValue() {
		return string;
	}

}
