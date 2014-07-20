package hr.fer.zemris.linearna;

/**
 * Implementacija razreda {@link AbstractMatrix}.
 * @author Ivan Krpelnik
 *
 */
public class Matrix extends AbstractMatrix {

	private double[][] elements;
	private int rows;
	private int cols;

	public Matrix(int rows, int cols) {
		if(rows < 0 || cols < 0) {
			throw new IndexOutOfBoundsException();
		}
		this.rows = rows;
		this.cols = cols;
		this.elements = new double[rows][cols];
	}
	
	public Matrix(int rows, int cols, double[][] elements, boolean useGiven) {
		if(rows < 0 || cols < 0) {
			throw new IndexOutOfBoundsException();
		}
		this.rows = rows;
		this.cols = cols;
		if(useGiven) {
			this.elements = elements;
		} else {
			this.elements = new double[elements.length][elements[0].length];
			for(int i = 0; i < elements.length; ++i) {
				for(int j = 0; j < elements[i].length; ++j) {
					this.elements[i][j] = elements[i][j];
				}
			}
		}
	}
	
	@Override
	public int getRowsCount() {
		return rows;
	}

	@Override
	public int getColsCount() {
		return cols;
	}

	@Override
	public double get(int row, int col) throws IndexOutOfBoundsException {
		if(row < 0 || row >= rows || col < 0 || col >= cols) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		return elements[row][col];
	}

	@Override
	public IMatrix set(int row, int col, double value) throws IndexOutOfBoundsException {
		if(row < 0 || row >= rows || col < 0 || col >= cols) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		elements[row][col] = value;
		return this;
	}

	@Override
	public IMatrix copy() {
		return new Matrix(rows, cols, elements, false);
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new Matrix(rows, cols);
	}

	/**
	 * Metoda tvornica koja iz zadanog stringa konstruira matricu.
	 * Retci bi trebali biti odvojeni znakom "|".
	 * Npr. za: <br>
	 * 1 2 3 | 4 5 6 <br>
	 * Stvorit ce se matrica s 2 retka i 3 stupca i sa zadanim brojevima.
	 * @param s - string koji treba parsirati
	 * @return konstruirana matrica
	 * @throws IncompatibleOperandException ako string nije u dobrom formatu.
	 * @throws NullPointerException ako je String null
	 * @throws NumberFormatException ako u danom stringu osim znaka "|" nisu samo realni brojevi.
	 */
	public static IMatrix parseSimple(String s) {
		String[] rows = s.trim().replaceAll("\\s+\\|\\s+", "|").split("\\|");
		int rowsL = rows.length;
		int colsL = rows[0].trim().replaceAll("\\s+", " ").split(" ").length;
		double[][] mat = new double[rowsL][colsL];
		for(int i = 0; i < rowsL; ++i) {
			String[] row = rows[i].trim().replaceAll("\\s+", " ").split(" ");
			if(row.length != colsL) {
				throw new IncompatibleOperandException("Cannot parse string to matrix.");
			} else {
				for(int j = 0; j < colsL; ++j) {
					mat[i][j] = Double.parseDouble(row[j]);
				}
			}
		}
		return new Matrix(rowsL, colsL, mat, true);
	}
	
}
