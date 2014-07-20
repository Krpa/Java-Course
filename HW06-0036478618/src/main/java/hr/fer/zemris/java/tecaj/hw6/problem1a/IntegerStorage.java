package hr.fer.zemris.java.tecaj.hw6.problem1a;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class that stores int value and can be watched by {@link IntegerStorageObserver}.
 * @author Ivan Krpelnik
 *
 */

public class IntegerStorage {
	
	private int value;
	private List<IntegerStorageObserver> observers;
	
	/**
	 * Constructor
	 * @param initialValue - initial value to be stored.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Adds observer to this storage.
	 * @param observer - observer to be added.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(observers == null) {
			observers = new CopyOnWriteArrayList<>();
		}
		observers.add(observer);
	}
	
	/**
	 * Removes given observer from this storage.
	 * @param observer - given observer.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * Removes all observers from this storage.
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Gets value stored by this storage.
	 * @return stored value.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets new value to this storage and calls observers.
	 * @param value - new value.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value!=value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if(observers != null) {
				for(IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}

	