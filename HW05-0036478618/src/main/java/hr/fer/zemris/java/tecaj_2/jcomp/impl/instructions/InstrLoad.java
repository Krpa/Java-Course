package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Implementacija naredbe load.<br>
 * Naredba upisuje objekt s dane memorijske lokacije u dani registar.
 * @author Ivan Krpelnik
 *
 */
public class InstrLoad implements Instruction {

	private int regIndex;
	private int location;
	
	/**
	 * Konstruktor prima registar i memorijsku lokaciju.
	 * @param arguments - registar i memorijska lokacija.
	 * @throws IllegalArgumentException - ako nisu primljeni registar i memorijska lokacija.
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if(arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if(!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		this.regIndex = ((Integer)arguments.get(0).getValue()).intValue();
		this.location = ((Integer)arguments.get(1).getValue()).intValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(regIndex, computer.getMemory().getLocation(location));
		return false;
	}
}
