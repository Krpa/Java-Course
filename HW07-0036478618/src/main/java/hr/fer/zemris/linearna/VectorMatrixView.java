package hr.fer.zemris.linearna;

/**
 * Implementacija razreda {@link AbstractVector}.
 * Razred daje pogled na matricu s jednik retkom ili jednim stupcem u obliku vektora.
 * @author Ivan Krpelnik
 *
 */
public class VectorMatrixView extends AbstractVector {

	private int dimension;
	private boolean rowMatrix;
	private IMatrix matrix;
	
	/**
	 * Konstruktor 
	 * @param matrix - matrica
	 */
	public VectorMatrixView(IMatrix matrix) {
		if(matrix == null || matrix.getColsCount() > 1 && matrix.getRowsCount() > 1) {
			throw new IncompatibleOperandException();
		}
		this.dimension = matrix.getColsCount() > 1 ? matrix.getColsCount() : matrix.getRowsCount();
		this.rowMatrix = matrix.getRowsCount() > 1;
		this.matrix = matrix;
	}
	
	@Override
	public double get(int index) {
		return matrix.get(rowMatrix ? 0 : index,
							rowMatrix ? index : 0);
	}

	@Override
	public IVector set(int index, double value) {
		matrix.set(rowMatrix ? 0 : index,
					rowMatrix ? index : 0, value);
		return this;
	}

	@Override
	public int getDimension() {
		return this.dimension;
	}

	@Override
	public IVector copy() {
		return new VectorMatrixView(matrix.copy());
	}

	@Override
	public IVector newInstance(int dimension) {
		return new VectorMatrixView(matrix.newInstance(dimension, 1));
	}

}
