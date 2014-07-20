package hr.fer.zemris.bool.opimpl;

import java.util.List;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;

/**
 * Represents boolean operator AND.
 * @author Ivan Krpelnik
 *
 */
public class BooleanOperatorAND extends BooleanOperator {
	
	/**
	 * Constructs operator.
	 * @param sources - sources from which operator should be constructed.
	 */
	public BooleanOperatorAND(List<BooleanSource> sources) {
		super(sources);
		if(sources == null || sources.contains(null)) {
			throw new NullPointerException();
		}
	}

	/**
	 * Returns value of this operator.
	 */
	@Override
	public BooleanValue getValue() {
		return makeValue();
	}
	
	/**
	 * Private methods that calculates value of this function.
	 * @return BooleanValue - value of this function
	 */
	private BooleanValue makeValue() {
		boolean hasDontCare = false;
		List<BooleanSource> sources = this.getSources();
		for(BooleanSource source : sources) {
			if(source.getValue() == BooleanValue.FALSE) {
				return BooleanValue.FALSE;
			}
			if(source.getValue() == BooleanValue.DONT_CARE) {
				hasDontCare = true;
			}
		}
		
		if(hasDontCare) {
			return BooleanValue.DONT_CARE;
		}
		
		return BooleanValue.TRUE;
	}
}
