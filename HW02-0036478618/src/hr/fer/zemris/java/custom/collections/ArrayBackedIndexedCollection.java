package hr.fer.zemris.java.custom.collections;

/**
 * 
 * @author Ivan Krpelnik
 * 
 * Implementation of resizable array-backed collection of objects.
 * Array can't contain null references.
 *
 */

public class ArrayBackedIndexedCollection {

	private int size;
	private int capacity;
	private Object[] elements;
	
	
	/**
	 * Constructor sets initial capacity to 16.
	 */
	public ArrayBackedIndexedCollection() {
		this.capacity = 16;
		this.elements = new Object[16];
		this.size = 0;
	}
	
	/**
	 * Constructor sets initial capacity to given argument.
	 * Given initialCapacity should be greater than or equal to 1.
	 * @throws IllegalArgumentException if initialCapacity is less than 1.
	 * @param initialCapacity integer on which capacity should be set.
	 */
	public ArrayBackedIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1)
			throw new IllegalArgumentException("Initial capacity must be greater or equal to 1.");
		
		this.capacity = initialCapacity;
		this.elements = new Object[capacity];
		this.size = 0;
	}
	
	/**
	 * Checks whether this array is empty or not.
	 * @return true if array is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Returns size of this array.
	 * @return size of this array.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Adds given value to the end of this array.
	 * Value should not be null. 
	 * If current capacity of this array is not enough, 
	 * array is resized with new capacity that's double the old.
	 * @throws IllegalArgumentException if value is null. 
	 * @param value object to be added.
	 */
	public void add(Object value) {
		if(value == null)
			throw new IllegalArgumentException("Value to be added must not be null.");
		
		this.resizeCapacity();
		this.elements[this.size] = value;
		this.size++;
	}

	/**
	 * Returns object of this array at given index.
	 * @throws IndexOutOfBoundsException if index is greater than or equal to array size, or if it's less than 0.
	 * @param index Integer index of an object in this array that should be returned.
	 * @return object of this array at given index.
	 */
	public Object get(int index) {
		if(index >= size || index < 0)
			throw new IndexOutOfBoundsException("Invalid index.");
		return this.elements[index];
	}
	
	/**
	 * Removes object at given index.
	 * @throws IndexOutOfBoundsException if index is greater than or equal to array size, or it's less than 0.
	 * @param index Integer index of an object that should be removed.
	 */
	public void remove(int index) {
		if(index >= this.size || index < 0)
			throw new IndexOutOfBoundsException("Invalid index.");
		
		this.size--;
		System.arraycopy(this.elements, index+1, this.elements, index, this.size - index);
		this.elements[this.size] = null;
	}

	/**
	 * Inserts given value at given position.
	 * If current capacity of this array is not enough, 
	 * array is resized with new capacity that's double the old. 
	 * @throws IndexOutOfBoundsException if index is greater than array size or less than 0.
	 * @param value Object which should be inserted.
	 * @param position Integer position at which value should be inserted.
	 */
	public void insert(Object value, int position) {
		if(position > this.size || position < 0)
			throw new IndexOutOfBoundsException("Invalid index.");
		this.resizeCapacity();
		
		System.arraycopy(this.elements, position, this.elements, position+1, this.size - position);
		this.elements[position] = value;
		this.size++;
	}
	
	/**
	 * Returns the index of first occurrence of a given value.
	 * If given value can't be found, -1 is returned.
	 * @throws NullPointerException if given value is null. 
	 * @param value Object to be found.
	 * @return Integer index of the first occurrence of value or -1 if value is not found.
	 */
	public int indexOf(Object value) {
		if(value == null)
			throw new NullPointerException("Value should not be null.");
		
		for(int i = 0; i < this.size; ++i)
			if(this.elements[i].equals(value))
				return i;
		
		return -1;
	}
	
	/**
	 * Checks if given value is contained in this array.
	 * @throws NullPointerException if value is null.
	 * @param value Object to check.
	 * @return true if value is contained, false otherwise.
	 */
	public boolean contains(Object value) {
		if(value == null)
			throw new NullPointerException("Value should not be null.");
		
		return indexOf(value) >= 0;
	}
	
	/**
	 * Clears this array.
	 */
	public void clear() {
		for(int i = 0; i < this.size; ++i)
			this.elements[i] = null;
		this.size = 0;
	}
	
	/**
	 * Resizes capacity of this array.
	 * New capacity becomes 2 times old capacity.
	 */
	private void resizeCapacity() {
		if(this.size < this.capacity)
			return;
		Object[] temp = new Object[2 * this.capacity];
		System.arraycopy(this.elements, 0, temp, 0, this.size);
		this.capacity = 2 * this.capacity;
		this.elements = temp;
	}
}
