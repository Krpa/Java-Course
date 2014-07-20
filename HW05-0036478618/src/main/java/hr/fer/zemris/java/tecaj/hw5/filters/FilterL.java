package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;

/**
 * Implementacija filtera koji prihvaća samo datoteke čija su imena 
 * kraća ili jednake po duljini zadanoj duljini.
 * @see FilterBasic
 * @author Ivan Krpelnik
 *
 */
public class FilterL extends FilterBasic {

	public static final String REGEX = "-?l\\d+";
	private int length;
	
	/**
	 * Konstruktor prima boolean ako filter treba biti inverzan i duljinu 
	 * po kojoj filter provjerava imena datoteka.
	 * @param length - duljina
	 * @param reversed - boolean
	 */
	public FilterL(int length, boolean reversed) {
		super(reversed);
		this.length = length;
	}
	
	@Override
	public boolean accepts(File file) {
		return (file.getName().length() <= length)^reversed;
	}

}
