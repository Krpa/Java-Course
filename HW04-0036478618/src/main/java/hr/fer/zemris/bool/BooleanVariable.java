package hr.fer.zemris.bool;

import java.util.Arrays;
import java.util.List;

/**
 *  Class BooleanVariable is an implementation of NamedBooleanSource. It has a single public constructor 
 *  which accepts name; by default, variable's value is set to FALSE. It offers getter and setter for variable's 
 *  value. Variable's domain is a collection containing the variable itself.
 * @author Ivan Krpelnik
 *
 */
public class BooleanVariable implements NamedBooleanSource {

	private BooleanValue value = BooleanValue.FALSE;
	private String name;
	
	/**
	 * Public constructor sets name this BooleanVariable.
	 * @param name - name to be set.
	 */
	public BooleanVariable(String name) {
		if(name == null) {
			throw new NullPointerException("Name should not be null.");
		}
		this.name = name;
	}
	
	/**
	 * Sets value of this BooleanVariable.
	 * @param value - value to be set.
	 */
	public void setValue(BooleanValue value) {
		if(value == null) {
			throw new NullPointerException();
		}
		this.value = value;
	}
	
	/**
	 * Returns value of this BooleanVariable.
	 * @return BooleanValue of this BooleanVariable.
	 */
	@Override
	public BooleanValue getValue() {
		return this.value;
	}

	/**
	 * Returns domain of this BooleanValue.
	 * @return List<BooleanVariable> containing this BooleanVariable. 
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		return Arrays.asList(this);
	}

	/**
	 * Returns name of this BooleanVariable.
	 * @return String name of this BooleanValue.
	 */
	@Override
	public String getName() {
		return name;
	}

}
