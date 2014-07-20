package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class that has two methods capable of constructing list of masks from indexes and from strings.
 * @author Ivan Krpelnik
 */

public class Masks {

	private Masks() {
		super();
	}
	
	/**
	 * Constructs list of Masks from given indexes and size.
	 * @param size - size of one instance of Mask
	 * @param indexes - indexes from which masks should be constructed
	 * @return List<Mask>
	 * @throws NullPointerException() if some value is null <br>
	 * 		   IllegalArgumentException() if some value is not valid.
	 */
	public static List<Mask> fromIndexes(int size, int ... indexes) {
		if(indexes == null) {
			throw new NullPointerException();
		}
		List<Mask> masks = new ArrayList<Mask>();
		for(int index : indexes) {
			if(index >= 1 << size || index < 0) {
				throw new IllegalArgumentException("Index must be less than (1 << size) and index must not be negative");
			}
			MaskValue[] values = new MaskValue[size];
			for(int j = size - 1; j >= 0; --j) {
				if(index % 2 == 0) {
					values[j] = MaskValue.ZERO;
				} else {
					values[j] = MaskValue.ONE;
				}
				index >>= 1;
			}
			masks.add(new Mask(values));
		}
		return masks;
	}
	
	/**
	 * Constructs list of Masks from given strings.
	 * @param strings - strings from which masks should be constructed
	 * @return List<Mask>
	 * @throws NullPointerException() if some value is null <br>
	 * 		   IllegalArgumentException() if some value is not valid.
	 */
	public static List<Mask> fromStrings(String ... strings ) {
		if(strings == null) {
			throw new NullPointerException();
		}
		List<Mask> masks = new ArrayList<Mask>();
		for(String string : strings) {
			try {
				masks.add(Mask.parse(string));
			} catch(Exception e) {
				throw new IllegalArgumentException("Cannot convert " + string + " to mask.", e);
			}
		}
		return masks;
	}
}
