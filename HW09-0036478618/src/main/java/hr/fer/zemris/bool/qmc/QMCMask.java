package hr.fer.zemris.bool.qmc;

import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.MaskValue;

/**
 * Razred koji čuva privatni primjerak razreda {@link Mask} predanog u konstruktoru i
 * boolean dontCare koji označava da li je primjerak ovog razreda maska koja je dont care u funkciji 
 * ili nije. Razred može sadržavati maske koje su null, ali tada neke metode neće raditi.
 * @author Ivan Krpelnik
 *
 */
public class QMCMask implements Comparable<QMCMask> {

	private Mask maska;
	private boolean dontCare;
	
	/**
	 * Konstruktor.
	 * @param maska - {@link Mask}
	 */
	public QMCMask(Mask maska) {
		this.maska = maska;
	}
	
	/**
	 * Konstruktor.
	 * @param maska - {@link Mask}
	 * @param dontCare - true ako je ova maska dont care, false inače
	 */
	public QMCMask(Mask maska, boolean dontCare) {
		this(maska);
		this.dontCare = dontCare;
	}
	
	/**
	 * Getter koji vraća masku koja je sadržana
	 * @return - maska
	 */
	public Mask getMask() {
		return maska;
	}
	
	/**
	 * Getter koji vraća boolean dontCare
	 * @return - boolean
	 */
	public boolean isDontCare() {
		return dontCare;
	}
	
	/**
	 * Metoda koja provjerava da li je dani indeks sadržan u ovoj maski.
	 * @param index - dani indeks
	 * @return - true ako je, false inače
	 */
	public boolean containsIndex(int index) {
		if(index >= (1 << maska.getSize()) || index < 0) {
			return false;
		}
		int pos = maska.getSize()-1;
		int size = (1 << maska.getSize()) - 1;
		while(size > 0) {
			if(((index & 1) == 1 && maska.getValue(pos) == MaskValue.ZERO) ||
					((index & 1) == 0 && maska.getValue(pos) == MaskValue.ONE)) {
				return false;
			}
			pos--;
			size >>= 1;
			index >>= 1;
		}
		return true;
	}

	/**
	 * Postavlja boolean sadržan u ovom razredu.
	 * @param dontCare - true ako ova maska treba biti dont care, false inače.
	 */
	public void setDontCare(boolean dontCare) {
		this.dontCare = dontCare;
	}
	
	/**
	 * Dva primjerka ovog razreda uspoređuju se po broju jedinica u njihovim maskama.
	 * Onaj koji ima manje jedinica je manji.
	 * @throws NullPointerException ako je predana maska null.	
	 */
	@Override
	public int compareTo(QMCMask o) {
		return Integer.compare(this.getMask().getNumberOfOnes(),
								o.getMask().getNumberOfOnes());
	}

	/**
	 * Generirao eclipse.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (dontCare ? 1231 : 1237);
		result = prime * result + ((maska == null) ? 0 : maska.hashCode());
		return result;
	}

	/**
	 * Generirao eclipse.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QMCMask other = (QMCMask) obj;
		if (dontCare != other.dontCare)
			return false;
		if (maska == null) {
			if (other.maska != null)
				return false;
		} else if (!maska.equals(other.maska))
			return false;
		return true;
	}
}
