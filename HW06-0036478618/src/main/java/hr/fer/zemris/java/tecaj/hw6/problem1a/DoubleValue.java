package hr.fer.zemris.java.tecaj.hw6.problem1a;

import hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorageObserver;

/**
 * Implementation of {@link IntegerStorageObserver}. 
 * When the counter reaches 2, it's erased from observed IntegerStorage.
 * @author Ivan Krpelnik
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	private int counter;
	
	public DoubleValue() {
		counter = 0;
	}
	
	/**
	 * Prints double the value of current value stored by given {@link IntegerStorage}.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Double value: " + istorage.getValue() * 2);
		counter++;
		if(counter >= 2) {
			istorage.removeObserver(this);
		}
	}

}
