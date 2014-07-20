package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;

/**
 * Implementacija filtera koji prihvaća samo datoteke.
 * @see FilterBasic
 * @author Ivan Krpelnik
 *
 */
public class FilterF extends FilterBasic {
	
	public static final String REGEX = "-?f";
	
	/**
	 * Konstruktor prima boolean ako filter treba biti inverzan.
	 * @param reversed - boolean: True ako treba biti inverzan, false inače
	 */
	public FilterF(boolean reversed) {
		super(reversed);
	}
	
	@Override
	public boolean accepts(File file) {
		return file.isFile()^reversed;
	}

}
