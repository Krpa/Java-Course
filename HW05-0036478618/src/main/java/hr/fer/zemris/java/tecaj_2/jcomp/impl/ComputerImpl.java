package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

/**
 * Implementacija sučelja Computer.<br>
 * Računalo se sastoji od memorije i registara. Pozivom konstruktora postavlja se veličina registara i memorije.
 * @author Ivan Krpelnik
 *
 */
public class ComputerImpl implements Computer {
	
	private Memory memory;
	private Registers registers;
	
	/**
	 * Konstruktor 
	 * @param size - Veličina memorije
	 * @param regsLen - Veličina registara
	 */
	public ComputerImpl(int size, int regsLen) {
		memory = new MemoryImpl(size);
		registers = new RegistersImpl(regsLen);
	}
	
	@Override
	public Registers getRegisters() {
		return registers;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

}
