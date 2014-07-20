package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Ivan Krpelnik
 * 
 * Implementacija Stabla
 *
 */

public class ProgramStabla {

	/**
	 * 
	 * struktura jednog čvora stabla
	 *
	 */
	static class CvorStabla {
		CvorStabla lijevi;
		CvorStabla desni;
		String podatak;
	}
	
	public static void main(String[] args) {
		CvorStabla cvor = null;
		
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		cvor = ubaci(cvor, "Anamarija");
		cvor = ubaci(cvor, "Vesna");
		cvor = ubaci(cvor, "Kristina");
		
		System.out.println("Ispisujem stablo inorder:");
		ispisiStablo(cvor);
		
		cvor = okreniPoredakStabla(cvor);
		
		System.out.println("Ispisujem okrenuto stablo inorder:");
		ispisiStablo(cvor);
		
		int vel = velicinaStabla(cvor);
		System.out.println("Stablo sadrzi elemenata: " + vel);
		
		boolean pronaden = sadrziPodatak(cvor, "Ivana");
		System.out.println("Podatak je pronadjen: " + pronaden);
	}

	/**
	 * Metoda koja prolazi kroz dano stablo i traži zadani podatak
	 * @param korijen korijen stabla u kojemu se traži podatak
	 * @param podatak string podatak koji se traži u stablu
	 * @return true ako je pronađen podatak, false ako nije
	 */
	static boolean sadrziPodatak(CvorStabla korijen, String podatak) {
		if(korijen == null)
			return false;
		
		if(korijen.podatak.equals(podatak) == true)
			return true;
		
		boolean b = false;
		b |= sadrziPodatak(korijen.lijevi, podatak);
		b |= sadrziPodatak(korijen.desni, podatak);
		return b;
	}
	
	/**
	 * Metoda koja vraća integer kao veličinu stabla po kojem prolazi
	 * @param korijen korijen stabla za koje se traži veličina
	 * @return integer - veličina stabla
	 */
	static int velicinaStabla(CvorStabla korijen) {
		if(korijen == null)
			return 0;
		
		int br = 1;
		br += velicinaStabla(korijen.lijevi);
		br += velicinaStabla(korijen.desni);
		return br;
	}
	
	/**
	 * Metoda koja ubacuje podatak u stablo
	 * @param korijen korijen stabla u koje se ubacuje podatak
	 * @param podatak podatak koji se ubacuje u stablo
	 * @return vraća korijen stabla u koje je ubačen podatak
	 */
	static CvorStabla ubaci(CvorStabla korijen, String podatak) {
		
		CvorStabla temp = new CvorStabla();
		temp.podatak = podatak;
		
		if(korijen == null)
			return temp;
		
		if(korijen.podatak.compareTo(podatak) >= 0)
			korijen.lijevi = ubaci(korijen.lijevi, podatak);
		else
			korijen.desni = ubaci(korijen.desni, podatak);
		
		return korijen;
	}
	
	
	/**
	 * Inorder ispis stabla na standardni izlaz
	 * @param cvor trenutni cvor u obilasku stabla
	 */
	static void ispisiStablo(CvorStabla cvor) {
		if(cvor == null)
			return;
		ispisiStablo(cvor.lijevi);
		System.out.println(cvor.podatak);
		ispisiStablo(cvor.desni);
	}
	
	/**
	 * Metoda koja okreće poredak stabla
	 * @param korijen stabla kojeg treba okrenuti
	 * @return vraća korijen okrenutog stabla
	 */
	static CvorStabla okreniPoredakStabla(CvorStabla korijen) {
		if(korijen == null)
			return null;
		
		CvorStabla temp = new CvorStabla();
		
		temp = korijen.lijevi;
		korijen.lijevi = okreniPoredakStabla(korijen.desni);
		korijen.desni = okreniPoredakStabla(temp);
		
		return korijen;
	}
}
