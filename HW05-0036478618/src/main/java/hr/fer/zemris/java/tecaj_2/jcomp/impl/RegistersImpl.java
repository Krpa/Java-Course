package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

/**
 * Implementacija sučelja Registers.<br>
 * Razred čuva opće registre koji mogu primiti bilo kakve objekte, programsko brojilo, jednu zastavicu i broj registara.
 * @see Registers
 * @author Ivan Krpelnik
 *
 */
public class RegistersImpl implements Registers {

	private int pc;
	private Object[] register;
	private boolean flag;
	private int regsLen;
	
	/**
	 * Konstruktor postavlja broj registara.
	 * @param regsLen - broj registara.
	 */
	public RegistersImpl(int regsLen) {
		this.regsLen = regsLen;
		this.register = new Object[regsLen];
	}
	
	@Override
	public Object getRegisterValue(int index) {
		if(index < 0 || index >= regsLen) {
			throw new IllegalArgumentException("Index should be greater than or equal to zero and lesser than number of registers.");
		}
		return register[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		if(index < 0 || index >= regsLen) {
			throw new IllegalArgumentException("Index should be greater than or equal to zero and lesser than number of registers.");
		}
		register[index] = value;
	}

	@Override
	public int getProgramCounter() {
		return pc;
	}

	@Override
	public void setProgramCounter(int value) {
		pc = value;
	}

	@Override
	public void incrementProgramCounter() {
		pc++;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		flag = value;
	}

}
