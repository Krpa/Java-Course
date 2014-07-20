package hr.fer.zemris.bool;

import java.util.List;

/**
 * Interface BooleanSource represents any source capable of producing legal BooleanValue.<br>
 * That source must be capable of producing this value (method getValue()) and provide an information based on 
 * which variables is this value produced (method getDomain()).
 * @author Ivan Krpelnik
 */

public interface BooleanSource {
	
	/**
	 * Returns boolean value of this BooleanSource.
	 * @return value
	 */
	BooleanValue getValue();
	
	/**
	 * Returns domain of this BooleanSource.
	 * @return list domain
	 */
	List<BooleanVariable> getDomain();
}
