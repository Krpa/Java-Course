package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Implementacija argumenta koji sadrži broj registra.
 * @see InstructionArgument
 * @author Ivan Krpelnik
 *
 */

public class RegisterArgument implements InstructionArgument {

	private Integer register;
	
	public RegisterArgument(int register) {
		this.register = Integer.valueOf(register);
	}
	
	@Override
	public boolean isRegister() {
		return true;
	}

	@Override
	public boolean isString() {
		return false;
	}

	@Override
	public boolean isNumber() {
		return false;
	}

	@Override
	public Object getValue() {
		return register;
	}

}
