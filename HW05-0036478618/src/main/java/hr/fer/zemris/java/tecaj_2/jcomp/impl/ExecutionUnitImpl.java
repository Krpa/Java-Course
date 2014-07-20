package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;

/**
 * Implementacija sučelja ExecutionUnit.
 * @see ExecutionUnit
 * @author Ivan Krpelnik
 *
 */

public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		Instruction instruction;
		Boolean halt = false;
		do {
			try {
				instruction = (Instruction)computer.getMemory().getLocation(computer.getRegisters().getProgramCounter());
				computer.getRegisters().incrementProgramCounter();
				halt = instruction.execute(computer);
			} catch(Exception e) {
				return false;
			}
		} while(!halt);
		return true;
	}

}
