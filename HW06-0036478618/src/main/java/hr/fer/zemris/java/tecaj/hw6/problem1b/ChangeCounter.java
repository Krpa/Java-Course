package hr.fer.zemris.java.tecaj.hw6.problem1b;

/**
 * @see hr.fer.zemris.java.tecaj.hw6.problem1a.ChangeCounter
 * @author Ivan Krpelnik
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	private int counter;
	
	public ChangeCounter() {
		counter = 0;
	}
	
	@Override
	public void valueChanged(IntegerStorageChange storageChange) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}
}
