package hr.fer.zemris.java.sorters;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class QSortParallelTest {

	@Test
	public void isSortedTest1() {
		int[] a = new int[] {-2, -2, 0, 1, 2, 3, 3, 4, 5};
		assertEquals("Za sortirani niz metoda mora vratiti true", true, QSortParallel.isSorted(a));
	}
	
	@Test
	public void isSortedTest2() {
		int[] a = new int[] {-1, 0, 1, 2, -2};
		assertEquals("Za nesortirani niz metoda mora vratiti false", false, QSortParallel.isSorted(a));
	}
	
	@Test
	public void sortTest() {
		int brPonavljanja = 100;
		while(brPonavljanja >= 0) {
			final int SIZE = 150000;
			Random rand = new Random();
			int[] data = new int[SIZE];
			for(int i = 0; i < data.length; i++) {
				data[i] = rand.nextInt();
			}
			long t0 = System.currentTimeMillis();
			QSortParallel.sort(data);
			long t1 = System.currentTimeMillis();
			System.out.println("Vrijeme: " + (t1-t0)+" ms");
			assertEquals("Nije uspijelo sortiranje", true, QSortParallel.isSorted(data));
			brPonavljanja--;
		}
	}
}
