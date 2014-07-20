package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;

/**
 * Bazna implementacija sučelja {@link IFilter}.<br>
 * Razred sadrži boolean varijablu koja ima informaciju da li je taj filter invertiran. 
 * Ta varijabla je inicijalno postavljena na false. Postavlja se na true ako je filter invertiran.
 * Dodatno, svaki filter mora imati javni statični String regex koji ga predstavlja.
 * @author Ivan Krpelnik
 *
 */
public class FilterBasic implements IFilter {

	protected boolean reversed = false;
	
	public FilterBasic() {
		super();
	}
	
	/**
	 * @param reversed: True ako treba biti inverzan, false inače
	 */
	public FilterBasic(boolean reversed) {
		super();
		this.reversed = reversed;
	}
	
	@Override
	public boolean accepts(File file) {
		return true^reversed;
	}
	
	/**
	 * Vraća null.
	 * @param s
	 * @return null
	 */
	public static IFilter parse(String s) {
		return null;
	}
}
