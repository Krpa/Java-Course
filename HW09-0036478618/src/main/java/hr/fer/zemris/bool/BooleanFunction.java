package hr.fer.zemris.bool;

/**
 * Interface BooleanFunction represents an abstract boolean function. It declares methods hasMinterm, 
 * hasMaxterm, hasDontCare which should return boolean true if BooleanFunctions contains minterm, maxterm or don't care
 * at given index. It also has 3 methods which ensure that we can use it for iterations. 
 * @author Ivan Krpelnik
 *
 */
public interface BooleanFunction extends NamedBooleanSource {

	/**
	 * Checks if this function has minterm at given index.
	 * @param minterm - given index.
	 * @return true if function contains minterm, false otherwise.
	 */
	boolean hasMinterm(int minterm);

	/**
	 * Checks if this function has maxterm at given index.
	 * @param maxterm - given index.
	 * @return true if function contains maxterm, false otherwise.
	 */
	boolean hasMaxterm(int maxterm);

	/**
	 * Checks if this function has don't care at given index.
	 * @param dontcare - given index.
	 * @return true if function contains don't care, false otherwise.
	 */
	boolean hasDontCare(int dontcare);
	
	/**
	 * Returns iterable collection of minterms of this function.
	 */
	Iterable<Integer> mintermIterable();
	/**
	 * Returns iterable collection of maxterms of this function.
	 */
	Iterable<Integer> maxtermIterable();
	/**
	 * Returns iterable collection of don't cares of this function.
	 */
	Iterable<Integer> dontcareIterable();
}
