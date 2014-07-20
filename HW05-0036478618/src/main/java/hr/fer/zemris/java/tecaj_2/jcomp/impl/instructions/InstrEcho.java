package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Implementacija naredbe ECHO.<br>
 * Naredba ispisuje sadržaj danog registra.
 * @author Ivan Krpelnik
 *
 */
public class InstrEcho implements Instruction {

	private int registerIndex;
	
	/**
	 * Konstruktor prima 1 registar.
	 * @param arguments - registar
	 * @throws IllegalArgumentException - ako nije primljen jedan registar.
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
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
		System.out.print(computer.getRegisters().getRegisterValue(registerIndex));
		return false;
	}

}
