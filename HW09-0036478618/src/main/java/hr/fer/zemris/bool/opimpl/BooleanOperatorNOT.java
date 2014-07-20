package hr.fer.zemris.bool.opimpl;


import java.util.Arrays;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

/**
 * Represents boolean operator NOT.
 * @author Ivan Krpelnik
 *
 */
public class BooleanOperatorNOT extends BooleanOperator {
	
	/**
	 * Constructor
	 * @param source
	 */
	public BooleanOperatorNOT(BooleanSource source) {
		super(Arrays.asList(source));
		if(source == null) {
			throw new NullPointerException();
		}
	}
	
	/**
	 * Returns value of this operator.
	 * @return BooleanValue.
	 */
	@Override
	public BooleanValue getValue() {
		return makeValue();
	}
	
	/**
	 * Calculates value of this operator.
	 * @return BooleanValue.
	 */
	public BooleanValue makeValue() {
		BooleanVariable value = this.getDomain().get(0);
		
		if(value.getValue() == BooleanValue.FALSE)
			return BooleanValue.TRUE;
		
		else if(value.getValue() == BooleanValue.TRUE)
			return BooleanValue.FALSE;
		else
			return BooleanValue.DONT_CARE;
	}
}
