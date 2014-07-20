package hr.fer.zemris.bool.opimpl;

import java.util.Arrays;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;

/**
 * Factory class for boolean operators.
 * @author Ivan Krpelnik
 *
 */
public class BooleanOperators {

	/**
	 * Constructor
	 */
	private BooleanOperators() {
		super();
	}
	
	/**
	 * Factory method for operator AND.
	 * @param source
	 * @return new {@link BooleanOperatorAND}
	 */
	public static BooleanOperator and(BooleanSource ... source) {
		return new BooleanOperatorAND(Arrays.asList(source));
	}
	
	/**
	 * Factory method for operator OR.
	 * @param source
	 * @return new {@link BooleanOperatorOR}
	 */
	public static BooleanOperator or(BooleanSource ... source) {
		return new BooleanOperatorOR(Arrays.asList(source));
	}

	/**
	 * Factory method for operator NOT
	 * @param source
	 * @return new {@link BooleanOperatorNOT}
	 */
	public static BooleanOperator not(BooleanSource source) {
		return new BooleanOperatorNOT(source);
	}
}
