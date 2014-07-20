package hr.fer.zemris.java.tecaj.hw6.problem1b;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @see hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorage
 * @author Ivan Krpelnik
 *
 */
public class IntegerStorage {
	
	private int value;
	private List<IntegerStorageObserver> observers;
	
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	public void addObserver(IntegerStorageObserver observer) {
		if(observers == null) {
			observers = new CopyOnWriteArrayList<>();
		}
		observers.add(observer);
	}
	
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}
	
	public void clearObservers() {
		observers.clear();
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		IntegerStorageChange storageChange = new IntegerStorageChange(this, this.value, value);
		this.value = value;
		callObservers(storageChange);
	}

	private void callObservers(IntegerStorageChange storageChange) {
		if(this.observers == null || storageChange.getNewValue() == storageChange.getOldValue()) {
			return;
		}
		for(IntegerStorageObserver observer : observers) {
			observer.valueChanged(storageChange);
		}
	}
}

	