package hr.fer.zemris.java.custom.collections;
import hr.fer.zemris.java.custom.collections.EmptyStackException;;


/**
 * 
 * @author Ivan Krpelnik
 * 
 * Implementation of stack.
 * Stack can't contain null references.
 */

public class ObjectStack {
	
	private ArrayBackedIndexedCollection adaptee;
	
	
	public ObjectStack() {
		this.adaptee = new ArrayBackedIndexedCollection();
	}
	
	/**
	 * Checks if this stack is empty.
	 * @return true if stack is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return this.adaptee.isEmpty();
	}
	
	/**
	 * Returns size of this stack.
	 * @return size of this stack.
	 */
	public int size() {
		return this.adaptee.size();
	}
	
	/**
	 * Pushes new object on this stack.
	 * @param value object to be pushed on this stack.
	 */
	public void push(Object value) {
		if(value == null)
			throw new NullPointerException("Value to be pushed must not be null.");
		this.adaptee.add(value);
	}
	
	
	/**
	 * Removes the object on top of this stack and returns it.
	 * @throws EmptyStackException if stack is empty.
	 * @return object on top of this stack.
	 */
	public Object pop() {
		if(this.adaptee.size() == 0)
			throw new EmptyStackException("Stack is empty, nothing to pop.");
		Object ret = this.adaptee.get(adaptee.size() - 1);
		this.adaptee.remove(adaptee.size() - 1);
		return ret;
	}
	
	/**
	 * Returns the object on top of this stack, but doesn't remove it from the stack.
	 * @throws EmptyStackException if stack is empty.
	 * @return object on top of this stack.
	 */
	public Object peek() {
		if(this.adaptee.size() == 0)
			throw new EmptyStackException("Stack is empty, nothing to peek.");
		
		Object ret = this.adaptee.get(adaptee.size() - 1);
		return ret;
	}
	
	/**
	 * Clears this stack.
	 */
	public void clear() {
		this.adaptee.clear();
	}

}
