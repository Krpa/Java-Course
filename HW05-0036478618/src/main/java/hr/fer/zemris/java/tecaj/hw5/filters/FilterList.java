package hr.fer.zemris.java.tecaj.hw5.filters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacija liste filtera koja može provjeriti da li neka datoteka
 * zadovoljava sve filtere koje sadrži ovaj razred i filtrirati listu danih datoteka po svojim filterima. 
 * @author Ivan Krpelnik
 *
 */
public class FilterList {
	
	private List<IFilter> listaFiltera;
	
	/**
	 * Konstruktor parsira danu listu Stringova kao filtere.
	 * @param strings - lista Stringova 
	 */
	public FilterList(List<String> strings) {
		listaFiltera = new ArrayList<IFilter>(parse(strings));
	}
	
	private List<IFilter> parse(List<String> strings) {
		List<IFilter> lista = new ArrayList<IFilter>();
		for(String string : strings) {
			lista.add(FilterParser.parse(string));
		}
		return lista;
	}
	
	public boolean isValid() {
		return !listaFiltera.contains(null);
	}
	
	public boolean accepts(File file) {
		boolean isAccepted = true;
		for(IFilter filter : listaFiltera) {
			isAccepted &= filter.accepts(file);
		}
		return isAccepted;
	}
	
	public List<File> filterFiles(List<File> forFiltering) {
		List<File> filtered = new ArrayList<>();
		for(File file : forFiltering) {
			if(accepts(file)) {
				filtered.add(file);
			}
		}
		return filtered;
	}
	
	public void setListaFiltera(List<String> listaFiltera) {
		this.listaFiltera = parse(listaFiltera);
	}
	
}
