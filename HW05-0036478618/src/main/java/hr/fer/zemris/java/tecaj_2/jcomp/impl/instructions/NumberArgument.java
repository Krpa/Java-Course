package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Implementacija argumenta koji sadrži cijeli broj.
 * @see InstructionArgument
 * @author Ivan Krpelnik
 *
 */
public class NumberArgument implements InstructionArgument {

	Integer number;
	
	public NumberArgument(int number) {
		this.number = Integer.valueOf(number);
	}
	
	@Override
	public boolean isRegister() {
		return false;
	}

	@Override
	public boolean isString() {
		return false;
	}

	@Override
	public boolean isNumber() {
		return true;
	}

	@Override
	public Object getValue() {
		return number;
	}

}
