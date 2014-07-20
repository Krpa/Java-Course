package hr.fer.zemris.java.tecaj.hw6.problem1a;

import hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorageObserver;

/**
 * Implementation of {@link IntegerStorageObserver} that counts how many times 
 * value has been changed.
 * @author Ivan Krpelnik
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	private int old;
	private int counter;
	
	/**
	 * Constructor
	 */
	public ChangeCounter() {
		counter = 0;
		old = 0;
	}
	
	/**
	 * Increments counter of this observer if new value of given {@link IntegerStorage} is not equal to old value.
	 * Prints counter to standard output.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(counter == 0 || old != istorage.getValue()) {
			counter++;
		}
		old = istorage.getValue();
		System.out.println("Number of value changes since tracking: " + counter);
	}
}
