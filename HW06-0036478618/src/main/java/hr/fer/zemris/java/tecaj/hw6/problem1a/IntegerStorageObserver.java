package hr.fer.zemris.java.tecaj.hw6.problem1a;

/**
 * Interface for observers of {@link IntegerStorage} <br>
 * It has one method that should be called when watched objects changes it's value.
 * @author Ivan Krpelnik
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Called method when value of {@link IntegerStorage} changes.
	 * @param istorage - observed IntegerStorage.
	 */
	public void valueChanged(IntegerStorage istorage);
}
