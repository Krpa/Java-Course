package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Implementacija naredbe jumpIfTrue.<br>
 * Naredba postavlja programsko brojilo na danu memorijsku lokaciju ako je zastavica postavljena na True.
 * @author Ivan Krpelnik
 *
 */
public class InstrJumpIfTrue implements Instruction {

	private int memoryLocation;
	
	/**
	 * Konstruktor prima memorijsku lokaciju na koju se treba postaviti programsko brojilo.
	 * @param arguments - memorijska lokacija
	 * @throws IllegalArgumentException - ako nije primljena memorijska lokacija.
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if(!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument.");
		}
		
		this.memoryLocation = ((Integer)arguments.get(0).getValue()).intValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		if(computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(memoryLocation);
		}
		return false;
	}

}
