package hr.fer.zemris.bool.qmc;

import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.MaskValue;

import org.junit.Test;

import static org.junit.Assert.*;

public class QMCMaskTest {

	//testira konstruktore, gettere i settere
	@Test 
	public void QMCMaskTest1() {
		Mask m = new Mask(new MaskValue[] {MaskValue.ONE, MaskValue.ZERO, MaskValue.DONT_CARE});
		QMCMask m1 = new QMCMask(m);
		QMCMask m2 = new QMCMask(m, true);
		assertEquals("predana maska mora biti jednaka vraćenoj", m, m1.getMask());
		assertEquals("predana maska mora biti jednaka vraćenoj", m, m2.getMask());
		assertEquals("Default za boolean dont care je false.", false, m1.isDontCare());
		assertEquals("Postavljeni dont care je bio true.", true, m2.isDontCare());
		m2.setDontCare(false);
		assertEquals(m1, m2);
	}
	
	@Test
	public void containsIndexTest() {
		Mask m = new Mask(new MaskValue[] {MaskValue.ONE, MaskValue.ZERO, MaskValue.DONT_CARE});
		QMCMask m1 = new QMCMask(m);
		assertEquals("maska bi trebala sadržavati indeks 4",true, m1.containsIndex(4));
		assertEquals("maska bi trebala sadržavati indeks 5",true, m1.containsIndex(5));
		assertEquals("maska ne bi trebala sadržavati negativne indekse", false, m1.containsIndex(-7));
		assertEquals("maska ne bi trebala sadržavati indeks koji izlazi iz domene", false, m1.containsIndex(8));
		for(int i = 0; i < 8; ++i) {
			if(i == 4 || i == 5) {
				continue;
			}
			assertEquals("maska ne bi trebala sadržavati indeks " + i, false, m1.containsIndex(i));
		}
	}
	
	@Test
	public void equalsTest() {
		Mask m = new Mask(new MaskValue[] {MaskValue.ONE, MaskValue.ZERO, MaskValue.DONT_CARE});
		QMCMask m1 = new QMCMask(m);
		QMCMask m2 = new QMCMask(m, true);
		assertEquals("maske ne bi trebale biti jednake ako su im booleani razliciti", false , m1.equals(m2));
		assertEquals(false, Integer.compare(m1.hashCode(), m2.hashCode()) == 0);
		assertEquals("maska treba biti jednaka samoj sebi", true, m1.equals(m1));
		assertEquals("maske ne mogu biti jednake ako je jedna null, a druga nije", false, m1.equals(null)); 
		assertEquals("maska nije jednaka necemu sto nije po tipu razred QMCMask", false, m1.equals(
							new Mask(new MaskValue[] {MaskValue.ONE, MaskValue.ZERO, MaskValue.DONT_CARE})));
		
		assertEquals(false, new QMCMask(null).equals(m1));
		m2.setDontCare(false);
		assertEquals(true, m1.equals(m2));
		assertEquals(true, Integer.compare(m1.hashCode(), m2.hashCode()) == 0);
		QMCMask m3 = new QMCMask( new Mask(new MaskValue[] {MaskValue.ZERO, MaskValue.ONE, MaskValue.DONT_CARE}));
		assertEquals(false, m1.equals(m3));
		assertEquals(false, Integer.compare(m1.hashCode(), m3.hashCode()) == 0);
	}
	
	@Test
	public void compareToTest() {
		Mask m = new Mask(new MaskValue[] {MaskValue.ONE, MaskValue.ZERO, MaskValue.DONT_CARE});
		QMCMask m1 = new QMCMask(m);
		QMCMask m2 = new QMCMask(m, true);
		assertEquals("usporedba maski po jedinicama treba dati 0 ako imaju isti broj jedinica", 0, m1.compareTo(m2));
		m2 = new QMCMask(new Mask(new MaskValue[] {MaskValue.ONE, MaskValue.ONE, MaskValue.DONT_CARE}));
		assertEquals("treba biti negativni rezultat ako ima manje jedinica", true, m1.compareTo(m2) < 0);
	}
}