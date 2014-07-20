package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Implementacija naredbe move.<br>
 * Naredba kopira sadržaj jednog registra u drugi.
 * @author Ivan Krpelnik
 *
 */
public class InstrMove implements Instruction {

	private int regIndex1;
	private int regIndex2;
	
	/**
	 * Konstruktor prima 2 registra.
	 * @param arguments - registri.
	 * @throws IllegalArgumentException - ako nisu primljena 2 registra.
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if(arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if(!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		this.regIndex1 = ((Integer)arguments.get(0).getValue()).intValue();
		this.regIndex2 = ((Integer)arguments.get(1).getValue()).intValue();
	}
	
	
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(regIndex1, computer.getRegisters().getRegisterValue(regIndex2));
		return false;
	}

}
