package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Implementacija naredbe decrement. <br>
 * Umanjuje vrijednost u danom registru za 1.
 * @author Ivan Krpelnik
 *
 */
public class InstrDecrement implements Instruction {

	private int registerIndex;
	
	/**
	 * Konstruktor koji prima registar koji se treba umanjiti.
	 * @param arguments - Registar
	 * @throws IllegalArgumentException - ako nije primljen 1 registar.
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument.");
		}
		
		this.registerIndex = ((Integer)arguments.get(0).getValue()).intValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue
								(registerIndex, 
								((Integer)computer.getRegisters().getRegisterValue(registerIndex)).intValue()-1);
		return false;
	}

}
