package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;

/**
 * Implementacija naredbe add. <br>
 * @author Ivan Krpelnik
 */

public class InstrAdd implements Instruction {
	
	private int indexRegistra1;
	private int indexRegistra2;
	private int indexRegistra3;
	
	/**
	 * Konstruktor treba primiti 3 registra da bi se naredba mogla izvesti.
	 * @param arguments - argumenti instrukcije.
	 * @throws IllegalArgumentException - ako nije primljeno 3 registra.
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if(arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if(!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		if(!arguments.get(2).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 2!");
		}
		
		this.indexRegistra1 = ((Integer)arguments.get(0).getValue()).intValue();
		this.indexRegistra2 = ((Integer)arguments.get(1).getValue()).intValue();
		this.indexRegistra3 = ((Integer)arguments.get(2).getValue()).intValue();
	}

	/**
	 * Metoda koja izvršava naredbu Add Rx, Ry, Rz. Zbraja sadržaje Rx i Ry i zapisuje rezultat u Rz.
	 * @see execute
	 */
	@Override
	public boolean execute(Computer computer) {
		Integer value1 = (Integer)computer.getRegisters().getRegisterValue(indexRegistra2);
		Integer value2 = (Integer)computer.getRegisters().getRegisterValue(indexRegistra3);
		computer.getRegisters().setRegisterValue(indexRegistra1, Integer.valueOf(value1.intValue()+value2.intValue()));
		return false;
	}

}
