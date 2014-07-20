package hr.fer.zemris.bool.qmc;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.Masks;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Razred koji generira minimalne oblike funkcije koristeći  
 * QMC metodu.
 * Minimalni oblici se vraćaju kao niz {@link MaskBasedBF}.
 * @author Ivan Krpelnik
 *
 */
public class QMCMinimizer {

	/**
	 * privatni konstruktor
	 */
	private QMCMinimizer() {
	}
	
	/**
	 * Statička metoda koja generira niz minimalnih oblika dane funkcije.
	 * @param bf - dana funkcija
	 * @param getProducts - true ako treba minimizirati po makstermima, false ako treba po mintermima
	 * @return {@link MaskBasedBF}[] - minimalni oblici
	 */
	public static MaskBasedBF[] minimize(BooleanFunction bf, boolean getProducts) {
		List<QMCMask> maske = reduce(makeImplicants(bf, getProducts), getProducts);
		List<QMCMask> primary = makePrimaryImplicants(bf, maske, getProducts);
		maske.removeAll(primary);
//		for(QMCMask mask : primary) {
//			System.out.println(mask.getMask() + " : bitni primarni");
//		}
//		for(QMCMask mask : maske) {
//			System.out.println(mask.getMask() + " : ostali primarni");
//		}
		List<Mask> miniMaske = extractMasks(primary);
		MaskBasedBF[] fije;
		if(isCovered(primary, bf, getProducts)) {
			fije = new MaskBasedBF[1];
			fije[0] = new MaskBasedBF("Mini", bf.getDomain(), true, miniMaske, new ArrayList<Mask>());
		} else {
			List<List<QMCMask>> fromPyne = pyne(maske, indexesNotCovered(primary, bf, getProducts));
			fije = new MaskBasedBF[fromPyne.size()];
			int i = 0; 
			for(List<QMCMask> lista : fromPyne) {
				List<Mask> temp = new ArrayList<>();
				temp.addAll(miniMaske);
				temp.addAll(extractMasks(lista));
				fije[i] = new MaskBasedBF("Fija"+i, bf.getDomain(), true, temp, new ArrayList<Mask>());
				i++;
			}
		}			
		return fije;
	}
	
	/**
	 * Privatna metoda koja generira maske implikanata dane funkcije
	 * @param bf - dana funkcija
	 * @param getProducts - treba li generirati implikante po makstermima ili mintermima
	 * @return - lista implikanata
	 */
	private static List<QMCMask> makeImplicants(BooleanFunction bf, boolean getProducts) {
		List<QMCMask> maske = new ArrayList<>();
		int size = bf.getDomain().size();
		for(int i = 0; i < 1 << size; ++i) {
			if(bf.hasMinterm(i) && !getProducts ||
					bf.hasMaxterm(i) && getProducts) {
				maske.add(new QMCMask(Masks.fromIndexes(size, i).get(0)));
			} else if(bf.hasDontCare(i)) {
				maske.add(new QMCMask(Masks.fromIndexes(size, i).get(0), true));
			}
		}
		return maske;
	}
	
	/**
	 * Metoda koja reducira dane maske po QMC algoritmu.
	 * @param maske - dane maske koje treba spojiti
	 * @param getProducts - treba li grupirati po nulama ili jedinicama
	 * @return - Lista novih maski koje pokrivaju dane maske
	 */
	private static List<QMCMask> reduce(List<QMCMask> maske, boolean getProducts) {
		final boolean products = getProducts;
		Comparator<QMCMask> comparator = new Comparator<QMCMask>() {

			@Override
			public int compare(QMCMask o1, QMCMask o2) {
				if(!products) {
					return o1.compareTo(o2);
				} else {
					return Integer.compare(o1.getMask().getNumberOfZeroes(),
							o2.getMask().getNumberOfZeroes());
				}
			}
		};
		while(true) {
			Collections.sort(maske, comparator);
			List<QMCMask> reducirano = new ArrayList<>();
			int size = maske.size();
			boolean[] flags = new boolean[maske.size()];
			for(int i = 0; i < size; ++i) {
				for(int j = i+1; j < size; ++j) {
					QMCMask prva = maske.get(i);
					QMCMask druga = maske.get(j);
					int size1 = getNumberOf(prva, getProducts);
					int size2 = getNumberOf(druga, getProducts);
					if(size2 - size1 >= 2) {
						break;
					}
					if(size2 - size1 == 1) {
						Mask temp = Mask.combine(prva.getMask(), druga.getMask());
						if(temp == null) {
							continue;
						}
						flags[i] = true;
						flags[j] = true;
						reducirano.add(new QMCMask(temp, prva.isDontCare() & druga.isDontCare()));
					}
				}
			}
			if(reducirano.isEmpty()) {
				break;
			}
			int i = 0;
			for(QMCMask mask : maske) {
				if(flags[i] == false) {
					reducirano.add(mask);
				}
				i++;
			}
			maske = new ArrayList<>(new HashSet<>(reducirano));
		}
		return maske;
	}
	
	/**
	 * Metoda koja iz danih maska implikanata izdvaja bitne primarne.
	 * @param bf - funkcija kojoj pripadaju implikanti
	 * @param maske - dani implikanti 
	 * @param getProducts - true ako se radi po makstermima, false ako po mintermima
	 * @return - lista bitnih primarnih implikanata
	 */
	private static List<QMCMask> makePrimaryImplicants(BooleanFunction bf, List<QMCMask> maske, boolean getProducts) {
		List<QMCMask> primary = new ArrayList<>();
		int[] tab = new int[1 << bf.getDomain().size()];
		Set<Integer> minterms = new HashSet<>();
		Iterator<Integer> it = getProducts ? bf.maxtermIterable().iterator() : bf.mintermIterable().iterator();
		while(it.hasNext()) {
			minterms.add(it.next());
		}
		for(QMCMask mask : maske) {
			if(mask.isDontCare()) {
				continue;
			}
			List<Mask> maska = new ArrayList<>();
			maska.add(mask.getMask());
			BooleanFunction f = new MaskBasedBF("temp", bf.getDomain(), true, 
					maska, new ArrayList<Mask>());
			for(int i : f.mintermIterable()) {
				tab[i]++;
			}
		}
		for(QMCMask mask : maske) {
			if(mask.isDontCare()) {
				continue;
			}
			List<Mask> maska = new ArrayList<>();
			maska.add(mask.getMask());
			BooleanFunction f = new MaskBasedBF("temp", bf.getDomain(), true, 
					maska, new ArrayList<Mask>());

			for(int i : f.mintermIterable()) {
				if(tab[i] == 1 && minterms.contains(i)) {
					primary.add(mask);
					break;
				}
			}
		}
		return primary;
	}
	
	/**
	 * Metoda koja za dane maske radi Pyne-McCluskey metodu.
	 * @param maske - dane maske
	 * @param notCovered - indeksi koje treba pokriti
	 * @return lista koja zadržava liste maski generiranih Pyne-McCluskey metodom.
	 */
	private static List<List<QMCMask>> pyne(List<QMCMask> maske, List<Integer> notCovered) {
		List<List<QMCMask>> rez = new ArrayList<>();
		boolean prviProlaz = true;
		for(int i : notCovered) {
			List<QMCMask> curr = new ArrayList<>();
			for(QMCMask maska : maske) {
				if(maska.containsIndex(i)) {
					curr.add(maska);
				}
			}
			if(prviProlaz) {
				for(QMCMask maska : curr) {
					rez.add(new ArrayList<>(Arrays.asList(maska)));
				}
				prviProlaz = false;
			} else {
				List<List<QMCMask>> nova = new ArrayList<>();
				for(List<QMCMask> temp : rez) {
					for(QMCMask maska : curr) {
						temp.add(maska);
						nova.add(new ArrayList<>(temp));
						temp.remove(maska);
					}
				}
				rez = nova;
			}
		}
		List<List<QMCMask>> bezDuplikata = new ArrayList<>();
		int size = rez.size();
		for(int i = 0; i < size; ++i) {
			boolean dodaj = true;
			for(int j = i + 1; j < size; ++j) {
				if(rez.get(j).containsAll(rez.get(i))) {
					dodaj = false;
					break;
				}
			}
			if(dodaj) {
				bezDuplikata.add(rez.get(i));
			}
		}
		return bezDuplikata;
	}
	
	/**
	 * Metoda koja provjerava da li je funkcija u potpunosti pokrivena sa danim implikantima
	 * @param primary - dani implikanti
	 * @param bf - funkcija
	 * @param getProducts - gledaju li se makstermi ili mintermi
	 * @return true ako je pokrivena, false inače
	 */
	private static boolean isCovered(List<QMCMask> primary, BooleanFunction bf, boolean getProducts) {
		Iterator<Integer> it = getProducts ? bf.maxtermIterable().iterator() : bf.mintermIterable().iterator();
		while(it.hasNext()) {
			if(!isContained(primary, it.next())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Provjerava da li je dani indeks sadržan u danim maskama.
	 * @param primary - dane maske
	 * @param index - indeks
	 * @return true ako je, false inače
	 */
	private static boolean isContained(List<QMCMask> primary, int index) {
		for(QMCMask maska : primary) {
			if(maska.containsIndex(index)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Vraća listu indeksa koji nisu pokriveni danim implikantima u danoj funkciji.
	 * @param primary - lista danih implikanata
	 * @param bf - dana funkcija
	 * @param getProducts - true ako je po makstermima, false ako je po mintermima
	 * @return lista indeksa koji nisu pokriveni
	 */
	private static List<Integer> indexesNotCovered(List<QMCMask> primary, BooleanFunction bf, boolean getProducts) {
		Iterator<Integer> it = getProducts ? bf.maxtermIterable().iterator() : bf.mintermIterable().iterator();
		List<Integer> indeksi = new ArrayList<>();
		while(it.hasNext()) {
			int index = it.next();
			if(!isContained(primary, index)) {
				indeksi.add(index);
			}
		}
		return indeksi;
	}
	
	/**
	 * Vraća broj nula ili jedinica u danoj maski.
	 * @param mask - dana maska
	 * @param getProducts - true ako treba brojati nule, false ako treba brojati jedinice
	 * @return broj nula ili jedinica.
	 */
	private static int getNumberOf(QMCMask mask, boolean getProducts) {
		if(getProducts) {
			return mask.getMask().getNumberOfZeroes();
		} else {
			return mask.getMask().getNumberOfOnes();
		}
	}
	
	/**
	 * Iz dane liste {@link QMCMask} generira listu pripadajućih {@link Mask}.
	 * @param original - dana lista koja sadrži {@link QMCMask}
	 * @return - lista koja sadržava {@link Mask}
	 */
	private static List<Mask> extractMasks(List<QMCMask> original) {
		List<Mask> maske = new ArrayList<>();
		for(QMCMask maska : original) {
			maske.add(maska.getMask());
		}
		return maske;
	}
}
