package hr.fer.zemris.bool.fimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.MaskValue;

/**
 * 
 * Represents BooleanFunction which is defined from given masks of minterms (or maxterms) and don't cares.<br>
 * It holds private instance of IndexedBF which does all the work.
 * @author Ivan Krpelnik
 *
 */
public class MaskBasedBF implements BooleanFunction {

	private String name;
	private List<BooleanVariable> domain;
	private boolean masksAreMinterms;
	private List<Mask> masks;
	private List<Mask> dontCareMasks;
	private IndexedBF indexedBF;
	
	/**
	 * Constructs MaskedBasedBF from given parameters.
	 * Lists can be empty, but must not be null or contain nulls.
	 * @param name - name of this MaskedBasedBF.
	 * @param domain - domain of this MaskedBasedBF.
	 * @param masksAreMinterms - boolean, should be true if given masks are masks of minterms 
	 * or false if given masks are masks of maxterms.
	 * @param masks - masks of minterms or maxterms.
	 * @param dontCaresMasks - masks of don't care values.
	 * @throws NullPointerException if some of the values are null.
	 * @throws IllegalArgumentException if indexes (calculated form masks) are out of bounds (calculated from domain's size).
	 */
	public MaskBasedBF(String name, List<BooleanVariable> domain, boolean masksAreMinterms, 
			List<Mask> masks, List<Mask> dontCareMasks) {
		super();
		if(name == null || domain == null || domain.contains(null) ||
				masks == null || masks.contains(null) ||
				dontCareMasks == null || dontCareMasks.contains(null)) {
			throw new NullPointerException();
		}
		this.name = name;
		this.masksAreMinterms = masksAreMinterms;
		this.domain = new ArrayList<>(domain);
		List<Integer> indexes = new ArrayList<>();
		List<Integer> dontCares = new ArrayList<>();
		this.masks = new ArrayList<Mask>(new LinkedHashSet<Mask>(masks));
		indexes.addAll(indexesFromMasks(this.masks));
		this.dontCareMasks = new ArrayList<Mask>(new LinkedHashSet<Mask>(dontCareMasks));
		dontCares.addAll(indexesFromMasks(this.dontCareMasks));
		try {
			this.indexedBF = new IndexedBF(this.name, this.domain, this.masksAreMinterms, indexes, dontCares);
		} catch(Exception e) {
			throw new IllegalArgumentException("Invalid arguments for MaskedBF.", e);
		}
	}
	
	/**
	 * Returns name of this function.
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns value of this function.
	 */
	@Override
	public BooleanValue getValue() {
		return indexedBF.getValue();
	}

	/**
	 * Returns masks of this function.
	 */
	public List<Mask> getMasks() {
		return masks;
	}
	
	/**
	 * Returns dont care masks.
	 */
	public List<Mask> getDontCareMasks() {
		return dontCareMasks;
	}
	
	/**
	 * @return True if masks are products, false otherwise.
	 */
	public boolean areMasksProducts() {
		return !masksAreMinterms;
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
		return indexedBF.hasMinterm(minterm);
	}

	@Override
	public boolean hasMaxterm(int maxterm) {
		return indexedBF.hasMaxterm(maxterm);
	}

	@Override
	public boolean hasDontCare(int dontcare) {
		return indexedBF.hasDontCare(dontcare);
	}

	@Override
	public Iterable<Integer> mintermIterable() {
		return indexedBF.mintermIterable();
	}

	@Override
	public Iterable<Integer> maxtermIterable() {
		return indexedBF.maxtermIterable();
	}
	
	@Override
	public Iterable<Integer> dontcareIterable() {
		return indexedBF.dontcareIterable();
	}
	
	/**
	 * Private methods that constructs indexes from given masks.
	 * @param masks 
	 * @return List<Integer> list of indexes.
	 */
	private List<Integer> indexesFromMasks(List<Mask> masks) {
		if(masks == null) {
			return null;
		}
		List<Integer> list = new ArrayList<Integer>();
		for(Mask mask : masks) {
			Stack<Mask> stack = new Stack<Mask>();
			stack.push(mask);
			while(!stack.empty()) {
				Mask tempMask = stack.pop();
				int size = tempMask.getSize();
				MaskValue[] values1 = new MaskValue[size];
				MaskValue[] values2 = new MaskValue[size];
				int i;
				for(i = 0; i < size; ++i) {
					if(tempMask.getValue(i) == MaskValue.DONT_CARE) {
						values1[i] = MaskValue.ONE;
						values2[i] = MaskValue.ZERO;
						for(int j = i + 1; j < size; ++j) {
							values1[j] = tempMask.getValue(j);
							values2[j] = tempMask.getValue(j);
						}
						stack.push(new Mask(values1));
						stack.push(new Mask(values2));
						break;
					}
					values1[i] = tempMask.getValue(i);
					values2[i] = tempMask.getValue(i);
				}
				if(i == size) {
					int numberValue = 0;
					for(int j = 0; j < size; ++j) {
						if(values1[j] == MaskValue.ONE) {
							numberValue = numberValue * 2 + 1;
						} else {
							numberValue = numberValue * 2;
						}
					}
					list.add(numberValue);
				}
			}
		}
		return list;
	}

}
