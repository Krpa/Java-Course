package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Ivan Krpelnik
 * 
 * Implementacija jednostruko vezane liste
 */

public class ProgramListe {

	/**
	 * struktura čvora liste
	 */
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}
	
	public static void main(String[] args) {
		CvorListe cvor = null;
		
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");

		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		
		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: " + vel);
		
	}

	/**
	 * Metoda koja izračunava duljinu liste
	 * @param cvor - početni čvor liste od kojeg izračunavamo duljinu
	 * @return duljina liste
	 */
	static int velicinaListe(CvorListe cvor) {
		if(cvor.sljedeci == null)
			return 1;
		return 1 + velicinaListe(cvor.sljedeci);
	}
	
	
	/**
	 * Metoda za ubacivanje novog čvora na kraj liste
	 * @param prvi - čvor koji je početak liste
	 * @param podatak - podatak koji se ubacuje u listu
	 * @return novi čvor koji je sada početak proširene liste
	 */
	static CvorListe ubaci(CvorListe prvi, String podatak) {
		CvorListe novi = new CvorListe();
		novi.podatak = podatak;
		
		if(prvi == null)
			return novi;
		
		CvorListe it = new CvorListe();
		for(it = prvi; it.sljedeci != null; it = it.sljedeci);
		it.sljedeci = novi;
		
		return prvi;
	}
	
	
	/**
	 * Metoda koja ispisuje listu na standardni izlaz
	 * @param cvor čvor koji je početak liste
	 */
	static void ispisiListu(CvorListe cvor) {
		if(cvor == null)
			return;
		System.out.println(cvor.podatak);
		ispisiListu(cvor.sljedeci);
	}
	
	
	/**
	 * Metoda koja sortira listu od zadanog čvora
	 * @param cvor pocetak liste koju treba sortirati
	 * @return cvor pocetak sortirane liste
	 */
	static CvorListe sortirajListu(CvorListe cvor) {
		
		boolean sortirano = false;
		
		while(sortirano == false) {
			
			boolean zamjenio = false;
			for(CvorListe it = cvor; it.sljedeci != null; it = it.sljedeci) {
				if(it.podatak.compareTo(it.sljedeci.podatak) > 0) {
					String temp = new String();
					temp = it.sljedeci.podatak;
					it.sljedeci.podatak = it.podatak;
					it.podatak = temp;
					zamjenio = true;
				}
			sortirano = !zamjenio;	
			}
		}
		
		return cvor;
	}
}
