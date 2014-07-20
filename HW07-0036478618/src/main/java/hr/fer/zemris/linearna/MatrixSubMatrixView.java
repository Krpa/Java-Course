package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * Implementacija razreda {@link AbstractMatrix} koja pruža pogled na podmatricu neke matrice.
 * @author Ivan Krpelnik
 *
 */
public class MatrixSubMatrixView extends AbstractMatrix implements IMatrix {

	private IMatrix matrix;
	private int[] rowIndexes;
	private int[] colIndexes;
	
	/**
	 * Konstruktor
	 * @param matrix - originalna matrica
	 * @param row - redak koji zelimo izbacit.
	 * @param col - stupac koji zelimo izbaciti.
	 */
	public MatrixSubMatrixView(IMatrix matrix, int row, int col) {
		this(matrix, makeIndexes(matrix, row, true), makeIndexes(matrix, col, false));
	}
	
	/**
	 * Privatni konstruktor
	 * @param matrix - originalna matrica
	 * @param rowIndexes - indeksi redaka koje zelimo u matrici 
	 * @param colIndexes - indeksi stupaca koje zelimo u matrici
	 */
	private MatrixSubMatrixView(IMatrix matrix, int[] rowIndexes, int[] colIndexes) {
		this.matrix = matrix;
		this.rowIndexes = rowIndexes;
		this.colIndexes = colIndexes;
	}

	/**
	 * Metoda koja vraća niz indeksa tako da iz indeksa redaka dane matrice izbaci dani indeks.
	 * @param matrix - matrica
	 * @param index - indeks koji treba izbaciti
	 * @param rows - true ako je dan indeks retka, false ako nije
	 * @return array indeksa
	 */
	static int[] makeIndexes(IMatrix matrix, int index, boolean rows) {
		if(index < 0 || rows && index >= matrix.getRowsCount() || !rows && index >= matrix.getColsCount()) {
			throw new IncompatibleOperandException("Index out of bounds.");
		}
		
		int size = rows ? matrix.getRowsCount() : matrix.getColsCount();
		int[] indexes = new int[size-1];
		for(int i = 0; i < size; ++i) {
			if(i < index) {
				indexes[i] = i;
			} else if(i > index) {
				indexes[i-1] = i; 
			}
		}
		return indexes;
	}
	
	@Override
	public int getRowsCount() {
		return rowIndexes.length;
	}

	@Override
	public int getColsCount() {
		return colIndexes.length;
	}

	@Override
	public double get(int row, int col) {
		if(row < 0 || row >= rowIndexes.length || col < 0 || col >= colIndexes.length) {
			throw new IncompatibleOperandException("This matrix does not contain element at position "
					+ row + ", " + col + ".");
		}
		return matrix.get(rowIndexes[row], colIndexes[col]);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		if(row < 0 || row >= rowIndexes.length || col < 0 || col >= colIndexes.length) {
			throw new IncompatibleOperandException("This matrix does not contain element at position "
					+ row + ", " + col + ".");
		}
		matrix.set(rowIndexes[row], colIndexes[col], value);
		return this;
	}

	@Override
	public IMatrix copy() {
		return new MatrixSubMatrixView(matrix.copy(), 
				Arrays.copyOf(rowIndexes, rowIndexes.length),
				Arrays.copyOf(colIndexes, colIndexes.length));
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new MatrixSubMatrixView(matrix.newInstance(rows, cols), new int[rows-1], new int[cols-1]);
	}

	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if(liveView) {
			return new MatrixSubMatrixView(this, row, col);
		} else {
			return new MatrixSubMatrixView(this.copy(), row, col);
		}
	}
	
}
