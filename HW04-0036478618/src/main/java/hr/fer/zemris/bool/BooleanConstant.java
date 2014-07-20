package hr.fer.zemris.bool;

import java.util.List;

/**
 * Class for Boolean constants. <br>
 * It is unmodifiable
 * Class contains 3 public final static references to BooleanConstants: TRUE, FALSE and DONT_CARE.
 * Those constants are statically initialized to hold an instances of constants always providing value TRUE, FALSE and DONT_CARE.
 * @author Ivan Krpelnik
 */

public class BooleanConstant implements BooleanSource {

	public final static BooleanConstant TRUE = new BooleanConstant(BooleanValue.TRUE);
	public final static BooleanConstant FALSE = new BooleanConstant(BooleanValue.FALSE);
	public final static BooleanConstant DONT_CARE = new BooleanConstant(BooleanValue.DONT_CARE);
	private BooleanValue value;
	
	/**
	 * Private constructor for static references TRUE, FALSE, DONT_CARE
	 * @param value BooleanValue
	 */
	private BooleanConstant(BooleanValue value) {
		if(value == null) {
			throw new NullPointerException();
		}
		this.value = value;
	}
	
	/**
	 * Value of this constant.
	 */
	@Override
	public BooleanValue getValue() {
		return this.value;
	}
	
	@Override
	public List<BooleanVariable> getDomain() {
		return null;
	}
	
	
}
