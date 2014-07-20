package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;
import hr.fer.zemris.java.custom.scripting.visitors.INodeVisitor;

/**
 * 
 * @author Ivan Krpelnik
 * 
 * Base Node class.
 * Stores children to private ArrayBackedIndexedCollection.
 */
public abstract class Node {

	private ArrayBackedIndexedCollection children = null;
	
	/**
	 * Constructs private ArrayBackedIndexedCollection if needed.
	 * Adds Node child to private ArrayBackedIndexedCollection.
	 * @param child Node to be added
	 */
	public void addChildNode(Node child) {
		if(this.children == null)
			this.children = new ArrayBackedIndexedCollection();
		this.children.add(child);
	}
	
	/**
	 * Returns number of children.
	 * @return int number of children
	 */
	public int numberOfChildren() {
		if(this.children == null)
			return 0;
		return this.children.size();
	}
	
	/**
	 * Returns child at given index.
	 * @param index
	 * @return Node child
	 */
	public Node getChild(int index) {
		if(index < 0 || index >= this.numberOfChildren())
			throw new IndexOutOfBoundsException("Invalid index. Index must be greater than or equal to zero "
					+ "and less than number of children");
		
		return (Node)this.children.get(index);
	}
	
	public abstract void accept(INodeVisitor visitor);
}
