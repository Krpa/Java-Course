package hr.fer.zemris.java.tecaj.hw6.problem1b;

/**
 * @see hr.fer.zemris.java.tecaj.hw6.problem1a.DoubleValue
 * @author Ivan Krpelnik
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	private int counter;
	
	public DoubleValue() {
		counter = 0;
	}
	
	@Override
	public void valueChanged(IntegerStorageChange storageChange) {
		System.out.println("Double value: " + storageChange.getNewValue() * 2);
		counter++;
		if(counter >= 2) {
			storageChange.getStorage().removeObserver(this);
		}
	}
}
