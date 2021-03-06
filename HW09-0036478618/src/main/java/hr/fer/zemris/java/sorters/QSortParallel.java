package hr.fer.zemris.java.sorters;

public class QSortParallel {

	/**
	 * Prag koji govori koliko elemenata u podpolju minimalno
	 * mora biti da bi se sortiranje nastavilo paralelno;
	 * ako elemenata ima manje, algoritam prelazi na klasično
	 * rekurzivno (slijedno) sortiranje.
	 */
	private final static int P_THRESHOLD = 6000;
	/**
	 * Prag za prekid rekurzije. Ako elemenata ima više od
	 * ovoga, quicksort nastavlja rekurzivnu dekompoziciju.
	 * U suprotnom ostatak sortira algoritmom umetanja.
	 */
	private final static int CUT_OFF = 5;
	/**
	 * Sučelje prema klijentu: prima polje i vraća se 
	 * tek kada je polje sortirano. Primjenjujući gornje
	 * pragove najprije posao paralelizira a kada posao
	 * postane dovoljno mali, rješava ga slijedno.
	 * 
	 * @param array polje koje treba sortirati
	 */
	
	public static void sort(int[] array) {
		new QSortJob(array, 0, array.length-1).run();
	}
	
	/**
	 * Model posla sortiranja podpolja čiji su elementi na pozicijama
	 * koje su veće ili jednake <code>startIndex</code> i manje
	 * ili jednake <code>endIndex</code>.
	 */
	static class QSortJob implements Runnable {
		private int[] array;
		private int startIndex;
		private int endIndex;
		public QSortJob(int[] array, int startIndex, int endIndex) {
			super();
			this.array = array;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}
		
		@Override
		public void run() {
			if(endIndex <= startIndex) {
				return;
			}
			if(endIndex-startIndex+1 > CUT_OFF) {
				boolean doInParallel = endIndex - startIndex + 1 > P_THRESHOLD;
				int i = partitioning(array[selectPivot()]);
				Thread t1 = null;
				if(startIndex < i) {
					QSortJob job = new QSortJob(array, startIndex, i-1);
					t1 = executeJob(doInParallel, job);
				}
				Thread t2 = null;
				if(endIndex > i) {
					QSortJob job = new QSortJob(array, i+1, endIndex);
					t2 = executeJob(doInParallel, job);
				}
				joinThread(t1);
				joinThread(t2);
			} else {
				int j, temp;
                for (int i = startIndex + 1; i <= endIndex; i++) {
                        temp = array[i];
                        j = i;
                        while (j > startIndex && array[j - 1] > temp) {
                                array[j] = array[j - 1];
                                j--;
                        }
                        array[j] = temp;
                }
			}
		}
		
		private void joinThread(Thread t) {
			if(t == null) {
				return;
			}
			while(true) {
				try {
					t.join();
					return;
				} catch (InterruptedException e) {}
			}
		}
		
		/**
		 * Direktno izvodi zadani posao pozivom run() i tada vraća <code>null</code>
		 * ili pak stvara novu dretvu, njoj daje taj posao i pokreće je te vraća
		 * referencu na stvorenu dretvu (u tom slučaju ne čeka da posao završi).
		 * @param doInParallel treba li posao pokrenuti u novoj dretvi
		 * @param job posao
		 * @return <code>null</code> ili referencu na pokrenutu dretvu
		 */
		private Thread executeJob(boolean doInParallel, QSortJob job) {
			if(doInParallel) {
				Thread t = new Thread(job);
				t.start();
				return t;
			}
			job.run();
			return null;
		}
		/**
		 * Odabir pivota metodom medijan-od-tri u dijelu polja 
		 * <code>array</code> koje je ograđeno indeksima
		 * <code>startIndex</code> i <code>endIndex</code>
		 * (oba uključena).
		 * 
		 * @return vraća indeks na kojem se nalazi odabrani pivot
		 */
		public int selectPivot() {
			int mid = startIndex + (endIndex - startIndex) / 2;
			if(array[startIndex] > array[mid]) {
				swap(array, startIndex, mid);
			}
			if(array[startIndex] > array[endIndex]) {
				swap(array, startIndex, endIndex);
			}
			if(array[mid] > array[endIndex]) {
				swap(array, mid, endIndex);
			}
			swap(array, mid, endIndex-1);
			return endIndex-1;
		}
		
		private int partitioning(int pivot) {
			int i = startIndex;
			int j = endIndex-1;
			while(true) {
				while(array[++i] < pivot);
				while(array[--j] > pivot);
				
				if(i >= j) {
					break;
				}
				swap(array, i, j);
			}
			swap(array, i, endIndex-1);
			return i;
		}
		/**
		 * U predanom polju <code>array</code> zamjenjuje
		 * elemente na pozicijama <code>i</code> i <code>j</code>. 
		 * @param array polje u kojem treba zamijeniti elemente
		 * @param i prvi indeks
		 * @param j drugi indeks
		 */
		public static void swap(int[] array, int i, int j) {
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
	}
	
	/**
	 * Pomoćna metoda koja provjerava je li predano polje 
	 * doista sortirano uzlazno.
	 * 
	 * @param array polje
	 * @return <code>true</code> ako je, <code>false</code> inače
	 */
	public static boolean isSorted(int[] array) {
		for(int i = 1; i < array.length; ++i) {
			if(array[i] < array[i-1]) {
				return false;
			}
		}
		return true;
	}
}