package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a boolean function constructed from an operator tree.
 * @author Ivan Krpelnik
 *
 */
public class OperatorTreeBF implements BooleanFunction {

	private String name;
	private List<BooleanVariable> domain;
	private BooleanOperator operatorTree;
	
	/**
	 * Constructs this function.
	 * @param name - String
	 * @param domain - List<BooleanVariable>
	 * @param operatorTree - BooleanOperator
	 * @throws NullPointerException if some of the values are null.
	 */
	public OperatorTreeBF(String name, List<BooleanVariable> domain, BooleanOperator operatorTree) {
		super();
		if(name == null || domain == null || operatorTree == null || domain.contains(null)) {
			throw new NullPointerException();
		}
		
		this.name = name;
		this.domain = new ArrayList<BooleanVariable>(domain);
		this.operatorTree = operatorTree;
	}
	
	/**
	 * Returns name of this function.
	 * @return String
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns value of this function.
	 * @return BooleanValue
	 */
	@Override
	public BooleanValue getValue() {
		return operatorTree.getValue();
	}

	/**
	 * Returns domain of this function.
	 * @return unmodifiableList<BooleanVariable>
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		return Collections.unmodifiableList(domain);
	}

	@Override
	public boolean hasMinterm(int minterm) {
		setDomain(minterm);
		return getValue() == BooleanValue.TRUE;
	}

	@Override
	public boolean hasMaxterm(int maxterm) {
		setDomain(maxterm);
		return getValue() == BooleanValue.FALSE;
	}

	@Override
	public boolean hasDontCare(int dontcare) {
		setDomain(dontcare);
		return getValue() == BooleanValue.DONT_CARE;
	}
	
	/**
	 * Private method that sets domain of this function to some index.
	 * @param index - value on which domain should be set.
	 */
	private void setDomain(int index) {
		for(int i = domain.size() - 1; i >= 0; i--) {
			if((index & 1) == 1) {
				domain.get(i).setValue(BooleanValue.TRUE);
			} else {
				domain.get(i).setValue(BooleanValue.FALSE);
			}
			index /= 2;
		}
	}

	@Override
	public Iterable<Integer> mintermIterable() {
		return Collections.unmodifiableList(createIndexes(BooleanValue.TRUE));
	}

	@Override
	public Iterable<Integer> maxtermIterable() {
		return Collections.unmodifiableList(createIndexes(BooleanValue.FALSE));
	}

	@Override
	public Iterable<Integer> dontcareIterable() {
		return Collections.unmodifiableList(createIndexes(BooleanValue.DONT_CARE));
	}

	/**
	 * Private method that constructs indexes at which this function is given BooleanValue.
	 * @param value - BooleanValue.
	 * @return List<Integer> indexes.
	 */
	private List<Integer> createIndexes(BooleanValue value) {
		List<Integer> indexes = new ArrayList<Integer>();
		int size = 1 << domain.size();
		for(int i = 0; i < size; ++i) {
			setDomain(i);
			if(getValue() == value) {
				indexes.add(i);
			}
		}
		return indexes;
	}
}
