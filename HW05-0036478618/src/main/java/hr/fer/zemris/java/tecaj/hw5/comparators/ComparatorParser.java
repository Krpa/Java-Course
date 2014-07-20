package hr.fer.zemris.java.tecaj.hw5.comparators;

import static hr.fer.zemris.java.tecaj.hw5.comparators.Comparators.*;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;

/**
 * Razred koji služi za parsiranje komparatora. Svaki komparator mora imati svoj regex koji ga predstavlja
 * da bi ga parser znao prepoznati.
 * @author Ivan Krpelnik
 *
 */

public final class ComparatorParser {
	
	public static final String S_REGEX = "-?s";
	public static final String E_REGEX = "-?e";
	public static final String L_REGEX = "-?l";
	public static final String M_REGEX = "-?m";
	public static final String N_REGEX = "-?n";
	public static final String T_REGEX = "-?t";
	
	private ComparatorParser() {
		super();
	}
	
	/**
	 * Metoda vraća primjerak komparatora koji odgovara danom specifikatoru. Ako dani specifikator
	 * ne odgovara nijednom komparatoru koji ovaj parser prepoznaje, onda vraža null.
	 * @param s - specifikator
	 * @return - primjerak komparatora ako je parsiranje uspijelo, null ako nije.
	 */
	public static Comparator<File> parse(String s) {
		if(s.matches(S_REGEX)) {
			return s.charAt(0) == '-' ? Collections.reverseOrder(COMPARATOR_S) : COMPARATOR_S;
		}
		if(s.matches(E_REGEX)) {
			return s.charAt(0) == '-' ? Collections.reverseOrder(COMPARATOR_E) : COMPARATOR_E;
		}
		if(s.matches(L_REGEX)) {
			return s.charAt(0) == '-' ? Collections.reverseOrder(COMPARATOR_L) : COMPARATOR_L;
		}
		if(s.matches(M_REGEX)) {
			return s.charAt(0) == '-' ? Collections.reverseOrder(COMPARATOR_M) : COMPARATOR_M;
		}
		if(s.matches(N_REGEX)) {
			return s.charAt(0) == '-' ? Collections.reverseOrder(COMPARATOR_N) : COMPARATOR_N;
		}
		if(s.matches(T_REGEX)) {
			return s.charAt(0) == '-' ? Collections.reverseOrder(COMPARATOR_T) : COMPARATOR_T;
		}
		return null;
	}
	
}
