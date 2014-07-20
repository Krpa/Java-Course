package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;

/**
 * Implementacija filtera koji prihvaća samo datoteke koje imaju ekstenzije.
 * @see FilterBasic
 * @author Ivan Krpelnik
 *
 */
public class FilterE extends FilterBasic {

	public static final String REGEX = "-?e";
	
	/**
	 * Konstruktor prima boolean ako filter treba biti inverzan.
	 * @param reversed - boolean: True ako treba biti inverzan, false inače
	 */
	public FilterE(boolean reversed) {
		super(reversed);
	}
	
	@Override
	public boolean accepts(File file) {
		return file.getName().contains(".")^reversed;
	}

}
