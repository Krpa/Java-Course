package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class mask that stores boolean mask in private list.
 * Boolean mask should contain only MaskValues.
 * It provides factory methods parse and combine.
 * @author Ivan Krpelnik
 *
 */
public class Mask {
	
	private List<MaskValue> mask;

	/**
	 * Constructor makes internal list from given MaskValues.
	 * @param values - array of MaskValues.
	 */
	public Mask(MaskValue[] values) {
		super();
		if(values == null) {
			throw new NullPointerException();
		}
		for(MaskValue value : values) {
			if(value == null) {
				throw new NullPointerException();
			}
		}
		mask = new ArrayList<MaskValue>(Arrays.asList(values));
	}
	
	/**
	 * Getter for MaskValue at given position.
	 * @param index - position of MaskValue.
	 * @return MaskValue
	 */
	public MaskValue getValue(int index) {
		if(index >= mask.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		return mask.get(index);
	}
	
	/**
	 * Factory method that parses Mask from given String.
	 * String should contain only values 1, 0, X or x.
	 * @param inputString - string to be parsed.
	 * @return returns new Mask constructed from parsed string.
	 */
	public static Mask parse(String inputString) {
		if(inputString == null) {
			throw new NullPointerException();
		}
		String maskToParse = inputString.replaceAll("\\s", "");
		
		int maskLength = maskToParse.length();
		MaskValue[] values = new MaskValue[maskLength];
		
		for(int i = 0; i < maskLength; ++i) {
			if(maskToParse.charAt(i) == 'x' || maskToParse.charAt(i) == 'X') {
				values[i] = MaskValue.DONT_CARE;
			} else if(maskToParse.charAt(i) == '1') {
				values[i] = MaskValue.ONE;
			} else if(maskToParse.charAt(i) == '0') {
				values[i] = MaskValue.ZERO;
			} else {
				throw new IllegalArgumentException();
			}
		}
		return new Mask(values);
	}
	
	/**
	 * Returns new mask created from given index
	 * @param size - size of mask
	 * @param index - given index 
	 * @return - new mask
	 * @throws IllegalArgumentException
	 */
	public static Mask fromIndex(int size, int index) throws IllegalArgumentException {
		if(size < 0 || index < 0 || index > 1 << size) {
			throw new IllegalArgumentException();
		}
		MaskValue[] values = new MaskValue[size];
		for (int i = size-1; i >= 0; i--) {
			values[i] = (index % 2 == 0) ? MaskValue.ZERO : MaskValue.ONE;
			index /= 2;
		}
		return new Mask(values);
	}
	
	/**
	 * Returns boolean true if this mask is more general than given mask.
	 * @param mask - given mask.
	 * @return - true if this mask is more general, false otherwise.
	 */
	public boolean isMoreGeneralThan(Mask mask) {
		if(mask == null) {
			throw new NullPointerException();
		}
		if(this.getSize() != mask.getSize()) {
			return false;
		}
		
		int size = this.getSize();
		int counter = 0;
		for(int index = 0; index < size; ++index) {
			if(this.getValue(index) != mask.getValue(index)) {
				counter++;
				if(this.getValue(index) != MaskValue.DONT_CARE) {
					return false;
				}
			}
		}
		if(counter < 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * Factory method, combines two masks into one that covers both masks. If that mask cannot be constructed, null is returned.
	 * @param first - first mask for combining
	 * @param second - second mask for combining
	 * @return - new mask or null.	
	 */
	public static Mask combine(Mask first, Mask second) {
		if(first == null || second == null) {
			throw new NullPointerException("Masks for combining should not be null.");
		}
		if(first.getSize() != second.getSize()) {
			return null;
		}
		int maskSize = first.getSize();
		int counter = 0;
		MaskValue[] values = new MaskValue[maskSize];
		for(int index = 0; index < maskSize; ++index) {
			if(first.getValue(index) != second.getValue(index)) {
				counter++;
				values[index] = MaskValue.DONT_CARE;
			} else {
				values[index] = first.getValue(index);
			}
		}
		Mask retMask = new Mask(values);
		
		if(first.equals(retMask) || second.equals(retMask) || counter > 1) {
				return null;
		}
		return new Mask(values);
	}
	
	/**
	 * Returns size of this mask.
	 * @return integer size.
	 */
	public int getSize() {
		return mask.size();
	}
	
	/**
	 * Returns number of zeroes in this mask.
	 * @return integer - number of zeroes.
	 */
	public int getNumberOfZeroes() {
		return countMaskValues(MaskValue.ZERO);
	}
	

	/**
	 * Returns number of ones in this mask.
	 * @return integer - number of ones.
	 */
	public int getNumberOfOnes() {
		return countMaskValues(MaskValue.ONE);
	}
	
	/**
	 * Private method used for counting MaskValues in this mask.
	 * @param checkMask - value to be counted.
	 * @return frequency of checkMask in this mask.
	 */
	private int countMaskValues(MaskValue checkMask) {
		int ret = 0;
		for(MaskValue value : mask) {
			if(value == checkMask) {
				ret++;
			}
		}
		return ret;
	}
	
	/**
	 * Private method used for checking equality between two masks.
	 * @param first - mask
	 * @param second - mask
	 * @return - true if masks are equal, false otherwise
	 */
	private static boolean checkEq(Mask first, Mask second) {
		if(first == null || second == null) {
			return false;
		}
		if(first.getSize() != second.getSize()) {
			return false;
		}
		int maskSize = first.getSize();
		for(int i = 0; i < maskSize; ++i) {
			if(first.getValue(i) != second.getValue(i)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns list of integers that are covered by this mask
	 * @return list of integers
	 */
	public List<Integer> maskToValues() {

		List<Mask> maske = this.decomposition();
		List<Integer> list = new ArrayList<>();

		for (Mask m : maske) {
			list.add(m.indexFromMask());
		}

		return list;

	}
	
	/**
	 * Returns list of masks that are covered by this mask
	 * @return list of masks
	 */
	private List<Mask> decomposition() {
		List<Mask> list = new ArrayList<>();
		list.add(this);

		boolean oneMoreTime;
		do {
			oneMoreTime = false;
			List<Mask> listNew = new ArrayList<>();

			for(Mask m : list) {
				int sizeOfMask = m.getSize();

				MaskValue[] m1 = new MaskValue[sizeOfMask];
				MaskValue[] m2 = new MaskValue[sizeOfMask];

				int numberOfX = 0;
				for (int i = 0; i < sizeOfMask; i++) {
					MaskValue value = m.getValue(i);
					if (value == MaskValue.DONT_CARE) {
						if (numberOfX == 0) {
							m1[i] = MaskValue.ZERO;
							m2[i] = MaskValue.ONE;
						} else {
							m1[i] = value;
							m2[i] = value;
						}
						numberOfX++;
					} else {
						m1[i] = value;
						m2[i] = value;
					}
				}

				listNew.add(new Mask(m1));
				listNew.add(new Mask(m2));

				oneMoreTime = numberOfX > 1;
			}
			list = listNew;
		} while (oneMoreTime);

		return list;
	}
	
	/**
	 * Returns index generated from this mask.
	 * It should be called only on mask with no dont care values.
	 * @return int - index
	 */
	private int indexFromMask() {
		int result = 0;
		int size = this.getSize();
		for (int i = 0; i < size; i++) {
			if (this.getValue(i) == MaskValue.ONE) {
				result += 1;
			}
			result *= 2;
		}
		return Integer.valueOf(result / 2);
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		for(MaskValue value : mask) {
			if(value == MaskValue.DONT_CARE) {
				hash += 0;
			} else if (value == MaskValue.ONE) {
				hash += 1;
			} else {
				hash += 2;
			}
			hash *= 3;
		}
		hash /= 3;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof Mask)) {
			return false;
		}
		Mask objMask = (Mask)obj;
		return checkEq(objMask, this);
	}
	
	@Override
	public String toString() {
		String string = "";
		for(MaskValue value : mask) {
			if(value == MaskValue.ZERO) {
				string += "0";
			} else if(value == MaskValue.ONE) {
				string += "1";
			} else {
				string += "X";
			}
		}
		return string;
	}
}
