package hr.fer.zemris.java.tecaj.hw5.comparators;

import java.io.File;
import java.util.Comparator;

/**
 * Razred koji sadrži komparatore za uspoređivanje objekata tipa File.
 * @author Ivan Krpelnik
 *
 */
public final class Comparators {
	
	private Comparators() {
		super();
	}
	
	/**
	 * Komparator za sortiranje po veličini.
	 */
	public static final Comparator<File> COMPARATOR_S = new Comparator<File>() {
		
		@Override
		public int compare(File o1, File o2) {
			return Long.compare(o1.length(), o2.length());
		}
	};
	
	/**
	 * Komparator za sortiranje po izvršivosti.
	 */
	public static final Comparator<File> COMPARATOR_E = new Comparator<File>() {

		@Override
		public int compare(File o1, File o2) {
			if(o1.canExecute() && !o2.canExecute()) {
				return -1;
			}
			if(!o1.canExecute() && o2.canExecute()) {
				return 1;
			}
			return 0;
		}
	};
	
	/**
	 * Komparator za sortiranje po duljini imena.
	 */
	public static final Comparator<File> COMPARATOR_L = new Comparator<File>() {
		
		@Override
		public int compare(File o1, File o2) {
			return Integer.compare(o1.getName().length(), o2.getName().length());
		}
	};
	
	/**
	 * Komparator za sortiranje po datumu zadnje modifikacije.
	 */
	public static final Comparator<File> COMPARATOR_M = new Comparator<File>() {
		
		@Override
		public int compare(File o1, File o2) {
			return Long.compare(o1.lastModified(), o2.lastModified());
		}
	};
	
	/**
	 * Komparator za sortiranje po imenu.
	 */
	public static final Comparator<File> COMPARATOR_N = new Comparator<File>() {
		
		@Override
		public int compare(File o1, File o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	
	/**
	 * Komparator za sortiranje po tipu objekta.
	 */
	public static final Comparator<File> COMPARATOR_T = new Comparator<File>() {
		
		@Override
		public int compare(File o1, File o2) {
			if(o1.isDirectory() && o2.isFile()) {
				return -1;
			}
			if(o1.isFile() && o2.isDirectory()) {
				return 1;
			}
			return 0;
		}
	};

	
}
