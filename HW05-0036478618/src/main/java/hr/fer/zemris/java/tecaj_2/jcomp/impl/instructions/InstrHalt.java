package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Implementacija instrukcije halt.<br>
 * Instrukcija zaustavlja rad procesora.
 * @author Krpa
 *
 */
public class InstrHalt implements Instruction {
	
	/**
	 * Konstruktor.
	 * @param arguments - prazna lista.
	 * @throws IllegalArgumentException - ako su primljeni argumenti.
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if(arguments.size() != 0) {
			throw new IllegalArgumentException("Expected 0 arguments!");
		}
	}
	
	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
