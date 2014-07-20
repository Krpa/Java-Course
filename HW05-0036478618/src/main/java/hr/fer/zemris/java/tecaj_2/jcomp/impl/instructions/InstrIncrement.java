package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Implementacija naredbe increment.<br>
 * Uvećava dani registar za 1.
 * @author Ivan Krpelnik
 *
 */
public class InstrIncrement implements Instruction {

	private int registerIndex;
	
	/**
	 * Konstruktor prima registar koji treba uvećati.
	 * @param arguments - registar
	 * @throws IllegalArgumentException - ako nije dan registar.
	 */
	public InstrIncrement(List<InstructionArgument> arguments) {
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
								((Integer)computer.getRegisters().getRegisterValue(registerIndex)).intValue()+1);
		return false;
	}

}
