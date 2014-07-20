package hr.fer.zemris.java.tecaj.hw5;

import hr.fer.zemris.java.tecaj.hw5.comparators.ComparatorList;
import hr.fer.zemris.java.tecaj.hw5.filters.FilterList;
import hr.fer.zemris.java.tecaj.hw5.writer.Writer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Program prima argumente iz komandne linije. 
 * Prvi argument mora biti putanja do direktorija čiji se sadržaj treba ispisati.
 * Nakon toga argumenti (ako ih ima) moraju biti specifikatori sortiranja, ispisa i filtera.<br> <br>
 * Specifikatori sortiranja se označavaju sa: -s:SPECIFIKATOR <br>
 * Prepoznaju se sljedeći specifikatori: <br>
 * s - sortira se po veličini <br>
 * n - sortira se po imenu <br>
 * m - sortira se po vremenu zadnje modifikacije <br>
 * t - sortira se po vrsti datoteke. Direktoriji su "manji" od datoteka.<br>
 * l - sortira se po duljini imena. <br>
 * e - sortira se po izvršivosti. Izvršne datoteke dolaze prije.<br>
 * Ispred specifikatora može stajati - pa je poredak obrnut. Primjer specifikatora sortiranja: <br>
 * -s:-t -s:s -s:l <br>
 * Sortira obrnuto po vrsti datoteka, unutar istih tipova sortira po veličini i unutar istih veličina sortira po duljini imena.<br>
 * Ako se navede više specifikatora sortiranja, bitan je redoslijed. <br><br>
 * Specifikatori ispisa se označavaju sa : -w:SPECIFIKATOR<br>
 * Prepoznaju se sljedeći specifikatori:<br>
 * n - ime objekta, ispisuje se poravnato po lijevoj strani.<br>
 * t - tip objekta, za direktorije ispisuje d, za datoteke f.<br>
 * s - veličina objekta, ispisuje se desno poravnato.<br>
 * m - ispisuje vrijeme zadnje modifikacije.<br>
 * h - ispisuje h ako je objekt skriven, inače ništa.<br>
 * Primjer specifikatora ispisa:<br>
 * -w:n -w:t <br>
 * Ispisuje prvo ime objekta, zatim tip objekta.<br><br>
 * Specifikatori filtera označavaju se sa: -f:SPECIFIKATOR<br>
 * Prepoznaju se sljedeći specifikatori:<br>
 * sSIZE - prihvaća objekte čija je veličina manja ili jednaka SIZE. Npr. s1024 prihvaća objekte koji su veliki do 1kB.<br>
 * f - prihvaća samo datoteke<br>
 * lSIZE - prihvaća samo objekte čija je duljina imena manja ili jednaka SIZE.<br>
 * e - prihvaća samo objekte čije ime ima ekstenziju.<br>
 * Ispred specifikatora može stajati - pa se prihvaćaju objekti koje taj filter ne prihvaća
 * Primjer specifikatora filtera:<br>
 * -f:f -f:-s1024 -f:s2048 -f:l12 <br>
 * Prihvaća datoteke koje su veće od 1kB i velike su do 2kB te im je ime duljine do 12 znakova. <br><br>
 * Ako nisu primljeni nikakvi specifikatori, ispisuje se kao da je zadan samo specifikator -w:n.
 * @author Ivan Krpelnik
 *
 */
public class Dir {
	
	/**
	 * Poziva metodu koja izvršava program. Ako se program nije uspio izvršiti ispisuje pogrešku u konzolu.
	 * @param args - argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		if(args.length == 0) {
			System.err.println("Not enough arguments.");
			System.exit(-1);
		}
		String putanja = args[0];
		List<String> argumenti = new ArrayList<String>();
		for(int i = 1; i < args.length; ++i) {
			argumenti.add(args[i]);
		}
		
		try {
			doWork(putanja, argumenti);
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	/**
	 * Metoda koja izvršava program. Zove metodu za parsiranje specifikatora 
	 * i prosljeđuje specifikatore odgovarajućim razredima.
	 * @param path - putanja do direktorija čiji sadržaj treba ispisati.
	 * @param args - specifikatori
	 * @throws IllegalArgumentException
	 */
	private static void doWork(String path, List<String> args) {
		File dir = new File(path);
		if(dir.isFile()) {
			throw new IllegalArgumentException("Path should lead to folder, not file.");
		}
		List<File> files = new ArrayList<>(Arrays.asList(dir.listFiles()));
		List<String> listaFilt = parse("-f:", args);
		List<String> listaComp = parse("-s:", args);
		List<String> listaWrit = parse("-w:", args); 
		
		if(listaFilt.size() + listaComp.size() + listaWrit.size() != args.size()) {
			throw new IllegalArgumentException("Unknown arguments found.");
		}
		
		FilterList filters = new FilterList(listaFilt);
		if(filters.isValid()) {
			files = filters.filterFiles(files);
		} else {
			throw new IllegalArgumentException("Filters arguments are not valid.");
		}
		ComparatorList comparators = new ComparatorList(listaComp);
		if(comparators.isValid())  {
			files = comparators.sortFiles(files);
		} else {
			throw new IllegalArgumentException("Comparator arguments are not valid.");
		}
		Writer writer = new Writer(parse("-w:", args));
		if(writer.isValid()) {
			writer.outputFiles(files);
		} else {
			throw new IllegalArgumentException("Writer arguments are not valid.");
		}
	}
	
	/**
	 * Privatna metoda za izdvajanje određenih specifikatora iz dane liste specifikatora.
	 * @param spec - specifikatori koje treba izdvojiti
	 * @param args - lista specifikatora
	 * @return lista sa izdvojenim specifikatorima
	 */
	private static List<String> parse(String spec, List<String> args) {
		List<String> lista = new ArrayList<String>();
		for(String string : args) {
			if(string.substring(0, 3).equals(spec)) {
				lista.add(string.substring(3));
			}
		}
		return lista;
	}
	
}
