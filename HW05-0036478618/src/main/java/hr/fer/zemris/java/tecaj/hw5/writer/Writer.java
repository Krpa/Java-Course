package hr.fer.zemris.java.tecaj.hw5.writer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Razred koji prima listu specifikatora za ispis i prema njima radi ispis.
 * @author Ivan Krpelnik
 *
 */
public class Writer {
	
	private List<String> specifikatori;
	private int[] indents;
	private List<List<String>> outStrings;
	
	/**
	 * Konstruktor koji prima listu specifikatora.
	 * @param specifikatori
	 */
	public Writer(List<String> specifikatori) {
		this.specifikatori = new ArrayList<>(check(specifikatori));
	}
	
	/**
	 * Metoda koja se treba pozvati prije metode outputFiles da se provjeri da li su svi specifikatori dobri.
	 * @return True ako su svi specifikatori valjani, false inače.
	 */
	public boolean isValid() {
		return !specifikatori.contains(null);
	}
	
	/**
	 * Metoda ispisuje listu fajlova prema specifikatorima. Ako se ništa ne može ispisati, ispisuje odgovarajuću poruku.
	 * @param files - lista koju treba ispisati.
	 */
	public void outputFiles(List<File> files) {
		setIndentsAndStrings(files);
		if(outStrings.isEmpty()) {
			System.out.println("Nothing to output.");
			return;
		}
		outputOkvir();
		for(List<String> lista : outStrings) {
			int i = 0;
			for(String string : lista) {
				System.out.printf("| %"+indents[i]+"s ", string);
				i++;
			}
			System.out.println("|");
		}
		outputOkvir();
	}
	
	/**
	 * Ispisuje okvir tablice.
	 */
	private void outputOkvir() {
		for(int indent : indents) {
			System.out.print("+");
			indent = Math.abs(indent);
			for(int i = 0; i < indent+2; ++i) {
				System.out.print("-");
			}
		}
		System.out.println("+");
	}
	
	/**
	 * Parsira specifikatore iz dane liste specifikatora. Ako neki specifikator nije valjan umjesto njega se stavlja null.
	 * @param specifikatori - lista specifikatora koju treba parsirat
	 * @return lista specifikatora
	 */
	private List<String> check(List<String> specifikatori) {
		List<String> lista = new ArrayList<>();
		for(String string : specifikatori) {
			if(string.equals("n") || string.equals("t") || string.equals("s") || string.equals("m") || string.equals("h")) {
				lista.add(string);
			} else {
				lista.add(null);
			}
		}
		if(lista.isEmpty()) {
			lista.add("n");
		}
		return lista;
	}
	
	/**
	 * Postavlja listu specifikatora.
	 * @param specifikatori - lista specifikatora.
	 */
	public void setSpecifikatori(List<String> specifikatori) {
		this.specifikatori = new ArrayList<>(check(specifikatori));
	}
	
	/**
	 * Postavlja indentaciju i izlazne stringove za danu listu fajlova i za specifikatore koje sadrži ovaj razred.
	 * @param files - lista fajlova.
	 */
	private void setIndentsAndStrings(List<File> files) {
		outStrings = new ArrayList<>();
		indents = new int[specifikatori.size()];
		for(File file : files) {
			List<String> lista = new ArrayList<>();
			int i = 0;
			for(String specifikator : specifikatori) {
				if(specifikator.equals("s")) {
					indents[i] = Math.max(indents[i], (String.valueOf(file.length()).length()));
					lista.add(String.valueOf(file.length()));
				}
				if(specifikator.equals("n")) {
					indents[i] = Math.min(indents[i], -file.getName().length());
					lista.add(file.getName());
				}
				if(specifikator.equals("t")) {
					indents[i] = 1;
					if(file.isFile()) {
						lista.add("f");
					} else {
						lista.add("d");
					}
				}
				if(specifikator.equals("m")) {
					Date date = new Date(file.lastModified());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String formatiraniDatum = sdf.format(date);
					indents[i] = Math.max(indents[i], formatiraniDatum.length());
					lista.add(formatiraniDatum);
				}
				if(specifikator.equals("h")) {
					indents[i] = 1;
					if(file.isHidden()) {
						lista.add("h");
					} else {
						lista.add(" ");
					}
				}
				i++;
			}
			outStrings.add(lista);
		}
	}
	
}
