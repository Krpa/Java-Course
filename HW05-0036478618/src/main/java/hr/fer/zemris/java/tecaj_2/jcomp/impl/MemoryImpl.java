package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Memory;

/**
 * Implementacija sučelja Memory.<br>
 * Memorija se sastoji od bilo kakvih objekata (Object). Svaka memorijska lokacija sadrži jedan objekt.
 * @see Memory
 * @author Ivan Krpelnik
 *
 */

public class MemoryImpl implements Memory {

	private Object[] memory;
	private int size;
	
	/**
	 * Konstruktor koji postavlja veličinu memorije.
	 * @param size - veličina memorije.
	 */
	public MemoryImpl(int size) {
		this.memory = new Object[size];
		this.size = size;
	}
	
	@Override
	public void setLocation(int location, Object value) {
		if(location >= size || location < 0) {
			throw new IllegalArgumentException("Location should be greater than or equal to zero and lesser than memory size.");
		}
		
		memory[location] = value;
	}

	@Override
	public Object getLocation(int location) {
		if(location >= size || location < 0) {
			throw new IllegalArgumentException("Location should be greater than or equal to zero and lesser than memory size.");
		}
		return memory[location];
	}

}
