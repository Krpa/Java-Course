package hr.fer.zemris.java.tecaj.hw5.comparators;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Razred koji sadrži listu komparatora i može sortirati danu listu objekata (tipa File) prema tim komparatorima.
 * @author Ivan Krpelnik
 *
 */
public class ComparatorList {

	private List<Comparator<File>> listaKomparatora;
	
	/**
	 * Stvara listu komparatora iz dane liste stringova (specifikatora) pozivanje metode parse.
	 * @param strings - specifikatori
	 * @see parse
	 */
	public ComparatorList(List<String> strings) {
		listaKomparatora = new ArrayList<>(parse(strings));
	}
	
	/**
	 * Privatna metoda koja parsira danu listu stringova pomoću statičke metode parse razreda {@link ComparatorParser}.
	 * @param strings - lista specifikatora
	 * @return vraća listu komparatora.
	 */
	private List<Comparator<File>> parse(List<String> strings) {
		List<Comparator<File>> lista = new ArrayList<>();
		for(String string : strings) {
			lista.add(ComparatorParser.parse(string));
		}
		return lista;
	}
	
	/**
	 * Metoda vraća true ako lista komparatora ne sadrži nijedan null, inače vraća false. <br>
	 * Ova metoda bi trebala biti pozvana uvijek prije sortiranja.
	 * @return true ako lista komparatora ne sadrži null / false inače
	 */
	public boolean isValid() {
		return !listaKomparatora.contains(null);
	}
	
	/**
	 * Metoda radi kopiju dane liste i vraća sortiranu kopiju.
	 * @param forSorting - lista koju treba sortirati
	 * @return vraća sortiranu listu
	 */
	public List<File> sortFiles(List<File> forSorting) {
		List<File> sorted = new ArrayList<>(forSorting);
		if(listaKomparatora.isEmpty()) {
			return forSorting;
		} else {
			Collections.sort(sorted, new Composition(listaKomparatora));
			return sorted;
		}
	}
	
	/**
	 * Razred koji je kompozicija komparatora.
	 * @author Ivan Krpelnik
	 */
	private static class Composition implements Comparator<File> {
		
		private List<Comparator<File>> komparatori;
		
		public Composition(List<Comparator<File>> komparatori) {
			this.komparatori = new ArrayList<>(komparatori);
		}
		
		@Override
		public int compare(File o1, File o2) {
			for(Comparator<File> c : komparatori) {
				int ret = c.compare(o1, o2);
				if(ret != 0) {
					return ret;
				}
			}
			return 0;
		}
	}
	
	/**
	 * Postavlja listu komparatora prema danim specifikatorima.
	 * @param listaKomparatora - specifikatori
	 */
	public void setListaKomparatora(List<String> listaKomparatora) {
		this.listaKomparatora = parse(listaKomparatora);
	}
}
