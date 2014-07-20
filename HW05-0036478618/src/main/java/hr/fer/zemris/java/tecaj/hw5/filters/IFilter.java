package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;

/**
 * Sučelje za implementaciju filtera.
 * @author Ivan Krpelnik
 *
 */
public interface IFilter {
	/**
	 * Metoda provjerava da li file zadovoljava filter.
	 * @param file - file koji treba provjeriti
	 * @return Vraća true ako file zadovoljava filter, inače false.
	 */
	boolean accepts(File file);
}
