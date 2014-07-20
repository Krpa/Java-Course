package hr.fer.zemris.linearna;

/**
 * Razred daje zivi transponirani pogled na danu matricu.
 * @author Ivan Krpelnik
 *
 */
public class MatrixTrasposeView extends AbstractMatrix implements IMatrix {

	private IMatrix matrix;
	
	/**
	 * Konstruktor
	 * @param matrix - matrica
	 */
	public MatrixTrasposeView(IMatrix matrix) {
		this.matrix = matrix;
	}
	
	@Override
	public int getRowsCount() {
		return matrix.getColsCount();
	}

	@Override
	public int getColsCount() {
		return matrix.getRowsCount();
	}

	@Override
	public double get(int row, int col) {
		return matrix.get(col, row);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		matrix.set(col, row, value);
		return this;
	}

	@Override
	public IMatrix copy() {
		return new MatrixTrasposeView(matrix.copy());
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new MatrixTrasposeView(matrix.newInstance(cols, rows));
	}

}
