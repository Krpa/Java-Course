package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanConstant;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.MaskValue;
import hr.fer.zemris.bool.opimpl.BooleanOperators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class IndexedBF represents a boolean function which is defined by specifying indexes of minterms (or 
 * maxterms) and indexes of don't cares.
 * @author Ivan Krpelnik
 *
 */
public class IndexedBF implements BooleanFunction {

	private String name;
	private List<BooleanVariable> domain;
	private boolean indexesAreMinterms;
	private List<Integer> indexes;
	private List<Integer> dontCares;
	
	
	/**
	 * Constructs IndexedBF from given parameters.
	 * Lists can be empty, but must not be null or contain nulls.
	 * @param name - name of this IndexedBF.
	 * @param domain - domain of this IndexedBF.
	 * @param indexesAreMinterms - boolean, should be true if given indexes are indexes of minterms 
	 * or false if given indexes are indexes of maxterms.
	 * @param indexes - indexes of minterms or maxterms.
	 * @param dontCares - indexes of don't care values.
	 * @throws NullPointerException if some of the values are null.
	 * @throws IllegalArgumentException if indexes are out of bounds (calculated from domain's size).
	 */
	public IndexedBF(String name, List<BooleanVariable> domain,
			boolean indexesAreMinterms, List<Integer> indexes,
			List<Integer> dontCares) {
		super();
		if(name == null || domain == null || domain.contains(null) || 
				dontCares == null || dontCares.contains(null) ||
				indexes == null || indexes.contains(null)) {
			throw new NullPointerException();
		}
		this.name = name;
		this.domain = new ArrayList<BooleanVariable>(domain);
		this.indexesAreMinterms = indexesAreMinterms;
		this.indexes = new ArrayList<Integer>();
		this.dontCares = new ArrayList<Integer>();
		this.indexes.addAll(new TreeSet<Integer>(indexes));
		if(this.indexes.size() > 0 &&
				(this.indexes.get(this.indexes.size()-1) >= (1 << domain.size()) || this.indexes.get(0) < 0)) {
			throw new IllegalArgumentException("Indexes should be in domain range.");
		}
		this.dontCares.addAll(new TreeSet<Integer>(dontCares));
		if(this.dontCares.size() > 0 && 
				(this.dontCares.get(this.dontCares.size()-1) >= 1 << domain.size() || this.dontCares.get(0) < 0)) {
			throw new IllegalArgumentException("Indexes of dontCares should be in domain range.");
		}
		Set<Integer> checkSet = new HashSet<Integer>(this.indexes);
		checkSet.addAll(this.dontCares);
		if(checkSet.size() != this.indexes.size() + this.dontCares.size()) {
			throw new IllegalArgumentException("Indexes of " + (indexesAreMinterms ? "minterms" : "maxterms") +
													" and indexes of dont care values should not overlap.");
			}
	}

	/**
	 * Returns name of this function.
	 * @return String name.
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Calculates and returns value of this function.
	 * @return BooleanValue - calculated value.
	 */
	@Override
	public BooleanValue getValue() {
		BooleanVariable result = new BooleanVariable("res");
		result.setValue(BooleanValue.FALSE);

		BooleanConstant temp;
		Mask dom = domainMask();

		for (Integer i : dom.maskToValues()) {
			if (this.hasMinterm(i)) {
				temp = BooleanConstant.TRUE;
			} else if (this.hasMaxterm(i)) {
				temp = BooleanConstant.FALSE;
			} else {
				temp = BooleanConstant.DONT_CARE;
			}
			result.setValue(BooleanOperators.or(result, temp).getValue());
		}
		return result.getValue();
	}

	/**
	 * Generates mask from given domain.
	 * @return domain mask
	 */
	private Mask domainMask() {
		MaskValue[] values = new MaskValue[this.domain.size()];
		MaskValue val;
		int i = 0;
		for (BooleanVariable var : this.domain) {
			switch (var.getValue()) {
				case TRUE:
					val = MaskValue.ONE;
					break;
				case FALSE:
					val = MaskValue.ZERO;
					break;
				default:
					val = MaskValue.DONT_CARE;
					break;
			}
			values[i++] = val;
		}
		return new Mask(values);
	}

	/**
	 * Returns domain of this function.
	 * @return unmodifiableList<BooleanVariabl> domain.
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		return Collections.unmodifiableList(domain);
	}

	@Override
	public boolean hasMinterm(int minterm) {
		return hasValue(minterm, indexesAreMinterms, this.indexes, this.dontCares);
	}


	@Override
	public boolean hasMaxterm(int maxterm) {
		return hasValue(maxterm, !indexesAreMinterms, this.indexes, this.dontCares);
	}
	

	@Override
	public boolean hasDontCare(int dontcare) {
		return hasValue(dontcare, true, this.dontCares, this.dontCares);
	}

	/**
	 * Private method that checks if given index is contained in given lists.
	 * @param value
	 * @param typeOfValue
	 * @param list
	 * @param dontCares
	 * @return
	 */
	private boolean hasValue(int value, boolean typeOfValue, List<Integer> list, List<Integer> dontCares) {
		if(value >= 1 << domain.size()) {
			return false;
		}
		if(typeOfValue) {
			for(int index : list) {
				if(index == value) {
					return true;
				}
			}
			return false;
		} else {
			boolean b = true;
			for(int index : list) {
				b &= index != value;
			}
			for(int index : dontCares) {
				b &= index != value;
			}
			return b;
		}
	}

	
	@Override
	public Iterable<Integer> mintermIterable() {
		if(indexesAreMinterms) {
			return Collections.unmodifiableList(indexes);
		} else {
			return Collections.unmodifiableList(createIndexes());
		}
	}


	
	@Override
	public Iterable<Integer> maxtermIterable() {
		if(!indexesAreMinterms) {
			return Collections.unmodifiableList(indexes);
		} else {
			return Collections.unmodifiableList(createIndexes());
		}
	}


	
	@Override
	public Iterable<Integer> dontcareIterable() {
		return Collections.unmodifiableList(dontCares);
	}
	
	/**
	 * Private method that creates indexes of maxterms or minterms
	 * @return List<Integer>
	 */
	private List<Integer> createIndexes() {
		List<Integer> retList = new ArrayList<Integer>();
		int size = 1 << domain.size();
		for(int term = 0; term < size; ++term) {
			if(!indexes.contains(term) && !dontCares.contains(term)) {
				retList.add(term);
			}
		}
		return retList;
	}
}
