package hr.fer.zemris.java.tecaj.hw6.problem1b;

/**
 * @see hr.fer.zemris.java.tecaj.hw6.problem1a.SquareValue
 * @author Ivan Krpelnik
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange storageChange) {
		System.out.println("Provided new value: " + storageChange.getNewValue() +
				", square is " + storageChange.getNewValue() * storageChange.getNewValue());
	}
}
