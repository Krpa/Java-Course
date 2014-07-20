package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that associates Strings with stacks.
 * @author Ivan Krpelnik
 *
 */
public class ObjectMultistack {

	private Map<String, MultiStackEntry> map;
	
	/**
	 * Constructor
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}
	
	/**
	 * Pushes value into a stack that's associated with given string.
	 * @param name - given string.
	 * @param valueWrapper - given value.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if(map.get(name) == null) {
			map.put(name, new MultiStackEntry(valueWrapper));
		} else {
			map.put(name, new MultiStackEntry(valueWrapper, map.get(name)));
		}
	}
	
	/**
	 * Pops stack that's associated with given string.
	 * @param name - given string.
	 * @return popped value.
	 * @throws EmptyStackException if stack is empty.
	 */
	public ValueWrapper pop(String name) {
		MultiStackEntry entry = map.get(name);
		if(entry == null) {
			throw new EmptyStackException();
		}
		map.put(name, entry.prev);
		return entry.value;
	}
	
	/**
	 * Peeks into stack associated with given string.
	 * @param name - given string.
	 * @return top of stack.
	 * @throws EmptyStackException if stack is empty.
	 */
	public ValueWrapper peek(String name) {
		MultiStackEntry entry = map.get(name);
		if(entry == null) {
			throw new EmptyStackException();
		}
		return entry.value;
	}
	
	/**
	 * Tests whether stack associated with given string is empty.
	 * @param name - given string.
	 * @return true if stack is empty, false otherwise.
	 */
	public boolean isEmpty(String name) {
		return map.get(name) == null;
	}
	
	/**
	 * Implementation of a node in a linked list.
	 * @author Ivan Krpelnik
	 *
	 */
	private static class MultiStackEntry {
		
		ValueWrapper value;
		MultiStackEntry prev;
		
		/**
		 * Constructor
		 * @param value - {@link ValueWrapper} that should be stored.
		 */
		public MultiStackEntry(ValueWrapper value) {
			this.value = value;
		}
		
		/**
		 * Constructor
		 * @param value - {@link ValueWrapper} that should be stored.
		 * @param prev - previous node.
		 */
		public MultiStackEntry(ValueWrapper value, MultiStackEntry prev) {
			this.value = value;
			this.prev = prev;
		}
	}
	
}
