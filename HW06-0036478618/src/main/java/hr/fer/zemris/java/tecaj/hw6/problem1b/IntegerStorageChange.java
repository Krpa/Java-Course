package hr.fer.zemris.java.tecaj.hw6.problem1b;

/**
 * 
 * Stores instance of {@link IntegerStorage} and it's old and new value. 
 * @author Ivan Krpelnik
 *
 */
public class IntegerStorageChange {

	private IntegerStorage storage;
	private int oldValue;
	private int newValue;
	
	/**
	 * Constructor
	 * @param storage - given instance of {@link IntegerStorage}
	 * @param oldValue - old value of given storage.
	 * @param newValue - new value of given storage.
	 */
	public IntegerStorageChange(IntegerStorage storage, int oldValue, int newValue) {
		this.storage = storage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Returns storage.
	 * @return storage.
	 */
	public IntegerStorage getStorage() {
		return storage;
	}

	/**
	 * Returns old value.
	 * @return old value.
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Returns new value.
	 * @return new value.
	 */
	public int getNewValue() {
		return newValue;
	}
	
	
}
