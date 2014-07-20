package hr.fer.zemris.bool.opimpl;

import java.util.List;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;

/**
 * Represents boolean operator OR.
 * @author Ivan Krpelnik
 *
 */
public class BooleanOperatorOR extends BooleanOperator {

	/**
	 * Constructor
	 * @param sources - sources from which operator should be constructed.
	 */
	public BooleanOperatorOR(List<BooleanSource> sources) {
		super(sources);
		if(sources == null || sources.contains(null)) {
			throw new NullPointerException();
		}
	}
	
	/**
	 * Returns value of this operator.
	 * @return BooleanValue
	 */
	@Override
	public BooleanValue getValue() {
		return makeValue();
	}
	
	/**
	 * Calculates BooleanValue of this function.
	 * @return BooleanValue.
	 */
	private BooleanValue makeValue() {
		boolean hasDontCare = false;
		List<BooleanSource> sources = this.getSources();
		
		for(BooleanSource source : sources) {
			if(source.getValue() == BooleanValue.TRUE) {
				return BooleanValue.TRUE;
			}
			if(source.getValue() == BooleanValue.DONT_CARE) {
				hasDontCare = true;
			}
		}
		
		if(hasDontCare) {
			return BooleanValue.DONT_CARE;
		}
		
		return BooleanValue.FALSE;
	}
}
