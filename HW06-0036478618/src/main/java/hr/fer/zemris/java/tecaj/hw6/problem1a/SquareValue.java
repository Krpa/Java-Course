package hr.fer.zemris.java.tecaj.hw6.problem1a;

import hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorageObserver;

/**
 * Implementation of {@link IntegerStorageObserver} that prints squared value that's stored by 
 * watched {@link IntegerStorage} to standard out.
 * @author Ivan Krpelnik
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints  squared value that's stored by 
	 *  watched {@link IntegerStorage} to standard out.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Provided new value: " + istorage.getValue() +
				", square is " + istorage.getValue() * istorage.getValue());
	}

}
