package hr.fer.zemris.java.tecaj.hw3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of iterable arithmetic integer sequence. <br>
 * Class stores private array which represents this integer sequence and a private int size of this sequence.
 * @author Ivan Krpelnik
 */

public class IntegerSequence implements Iterable<Integer> {

	private int[] sequence;
	private int size;
	
	/**
	 * Constructs new integer sequence.
	 * @throw {@link IllegalArgumentException} if: <br> (first < last && step <= 0) || (first > last && step >= 0)
	 * @param first - first element of this arithmetic sequence
	 * @param last - last element of this arithmetic sequence
	 * @param step - step of this arithmetic sequence
	 */
	public IntegerSequence(int first, int last, int step) {
		if((first < last && step <= 0) || (first > last && step >= 0)) {
			throw new IllegalArgumentException("This would last forever.");
		}
		
		makeSequence(first, last, step);
	}
	
	/**
	 * Constructs array sequence and size of this IntegerSequence.
	 * @param first - first element of this arithmetic sequence
	 * @param last - last element of this arithmetic sequence
	 * @param step - step of this arithmetic sequence
	 */
	private void makeSequence(int first, int last, int step) {
		size = Math.abs(Math.abs(last - first) / step) + 1;
		sequence = new int[size+1];
		
		int index = 0;
		if(first < last) {
			for(int i = first; i <= last; i += step) {
				sequence[index] = i;
				index++;
			}
		} else {
			for(int i = first; i >= last; i += step) {
				sequence[index] = i;
				index++;
			}
		}
	}
	
	/**
	 * Returns new iterator for this IntegerSequence
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new MyIterator();
	}
	
	/**
	 * Implementation of iterator for this IntegerSequence. <br>
	 * It does not support remove() method.
	 * @author Ivan Krpelnik
	 */
	private class MyIterator implements Iterator<Integer> {

		private int index = 0;
		
		@Override
		public boolean hasNext() {
			if(index >= size) {
				return false;
			}
			return true;
		}

		@Override
		public Integer next() {
			if(hasNext() == false) {
				throw new NoSuchElementException();
			}
			return sequence[index++];
		}

		/**
		 * Cannot remove elements.
		 * @throws UnsupportedOperationException
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
}
