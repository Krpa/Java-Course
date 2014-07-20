package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Implementacija naredbe iinput.<br>
 * Naredba pokuša učitati cijeli broj sa standardnog ulaza i upisuje ga na danu memorijsku lokaciju. 
 * Ako je učitan broj postavlja zastavicu na true, inače se zastavica postavlja na false.
 * @author Ivan Krpelnik
 *
 */
public class InstrIinput implements Instruction {

	private int location;
	
	/**
	 * Konstruktor prima memorijsku lokaciju na koju se treba upisati učitani broj.
	 * @param argument - memorijska lokacija
	 * @throws IllegalArgumentException - ako nije dana memorijska lokacija.
	 */
	public InstrIinput(List<InstructionArgument> argument) {
		if(argument.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument.");
		}
		if(!argument.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument.");
		}
		location = ((Integer)argument.get(0).getValue()).intValue();
	}
	
	/**
	 * Postavlja zastavicu na true ako je naredba uspiješno izvšena (ako je učitan cijeli broj), 
	 * inače ju postavlja na false.
	 * @see execute
	 */
	@Override
	public boolean execute(Computer computer) {
		int value;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			String line = reader.readLine();
			value = Integer.parseInt(line.trim());
		} catch (Exception e) {
			computer.getRegisters().setFlag(false);
			return false;
		}
		computer.getMemory().setLocation(location, value);
		computer.getRegisters().setFlag(true);
		return false;
	}

}
