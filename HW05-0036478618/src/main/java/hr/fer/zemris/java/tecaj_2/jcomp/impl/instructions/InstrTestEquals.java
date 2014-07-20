package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Implementacija naredba testEquals.<br>
 * Provjerava da li su sadržaji danih registara jednaki i postavlja zastavicu s obzirom na to.
 * @author Krpa
 *
 */
public class InstrTestEquals implements Instruction {

	private int regIndex1;
	private int regIndex2;
	
	/**
	 * Konstruktor prima 2 registra koje treba usporediti.
	 * @param arguments - registri
	 * @throws IllegalArgumentException - ako nisu primljena 2 registra.
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
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
	
	/**
	 * Postavlja zastavicu na true, ako su sadržaji danih registara jednaki, inače zastavicu postavlja na false.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object firstRegister = computer.getRegisters().getRegisterValue(regIndex1);
		Object secondRegister = computer.getRegisters().getRegisterValue(regIndex2);
		if(firstRegister == null && secondRegister == null) {
			computer.getRegisters().setFlag(true);
		} else if(firstRegister instanceof String && secondRegister instanceof String) {
			computer.getRegisters().setFlag(((String)firstRegister).equals((String)secondRegister));
		} else if(firstRegister instanceof Integer && secondRegister instanceof Integer) {
			computer.getRegisters().setFlag(((Integer)firstRegister).equals((Integer)secondRegister));
		} else {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}

}
