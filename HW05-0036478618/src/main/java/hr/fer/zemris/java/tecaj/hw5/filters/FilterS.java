package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;

/**
 * Implementacija filtera koji prihvaća datoteke koje su veličinom u oktetima manje ili jednake zadanoj.
 * @see FilterBasic
 * @author Krpa
 *
 */
public class FilterS extends FilterBasic {

	public static final String REGEX = "-?s\\d+";
	
	private long size;
	
	/**
	 * Konstruktor prima boolean ako filter treba biti inverzan i veličinu
	 * po kojoj filter provjerava datoteke.
	 * @param size - veličina datoteka
	 * @param reversed - boolean: True ako treba biti inverzan, false inače
	 */
	public FilterS(long size, boolean reversed) {
		this.size = size;
		this.reversed = reversed;
	}
	
	@Override
	public boolean accepts(File file) {
		return (file.length() <= size)^reversed;
	}

}
