package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BooleanOperator is an abstraction over boolean operators. It has private list of sources based on 
 * which final result is calculated; this list must be provided through BooleanOperator's protected constructor 
 * as its only argument. Operators determine their domain by inspecting domains of given sources and 
 * producing an union.
 * @author Ivan Krpelnik
 *
 */
public abstract class BooleanOperator implements BooleanSource {

	private List<BooleanSource> sources;
	
	/**
	 * Constructor - sets private list sources.
	 * @param sources - list to be copied to this.sources.
	 */
	protected BooleanOperator(List<BooleanSource> sources) {
		if(sources == null || sources.contains(null)) {
			throw new NullPointerException();
		}
		this.sources = new ArrayList<BooleanSource>(sources);
	}
	
	/**
	 * Getter for sources of this operator.
	 * @return List<BooleanSource> sources.
	 */
	protected List<BooleanSource> getSources() {
		return Collections.unmodifiableList(sources);
	}
	
	/**
	 * Should be implemented.
	 * @return null or value of this BooleanOperator.
	 */
	@Override
	public BooleanValue getValue() {
		return null;
	}

	/**
	 * Returns union sources's domain.
	 * @return List<BooleanVariabl> domain.
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		if(sources == null) {
			return null;
		}
		List<BooleanVariable> domain = new ArrayList<>();
		for(BooleanSource source : sources) {
			if(source.getDomain() != null) {
				domain.addAll(source.getDomain());
			}
		}
		return Collections.unmodifiableList(domain);
	}

}
