package hr.fer.zemris.linearna;

/**
 * Implementacija razreda {@link AbstractMatrix}.
 * Razred daje zivi pogled na vektor kao matricu.
 * @author Ivan Krpelnik
 *
 */
public class MatrixVectorView extends AbstractMatrix {

	private boolean asRowMatrix;
	private IVector vector;
	
	/**
	 * Konstruktor
	 * @param vector - vektor
	 * @param asRowMatrix - true ako matrica treba imati jedan redak, false ako treba imati jedan stupac.
	 */
	public MatrixVectorView(IVector vector, boolean asRowMatrix) {
		if(vector == null) {
			throw new IncompatibleOperandException("Cannot construct matrix from null.");
		}
		this.vector = vector;
		this.asRowMatrix = asRowMatrix;
	}
	
	@Override
	public int getRowsCount() {
		return asRowMatrix ? 1 : vector.getDimension();
	}

	@Override
	public int getColsCount() {
		return asRowMatrix ? vector.getDimension() : 1;
	}

	@Override
	public double get(int row, int col) throws IndexOutOfBoundsException {
		if(asRowMatrix && row > 0 || !asRowMatrix && col > 0 || row < 0 || col < 0) {
			throw new IndexOutOfBoundsException();
		}
		return vector.get(asRowMatrix ? col : row);
	}
	
	@Override
	public IMatrix set(int row, int col, double value) throws IndexOutOfBoundsException {
		if(asRowMatrix && row > 0 || !asRowMatrix && col > 0 || row < 0 || col < 0) {
			throw new IndexOutOfBoundsException();
		}
		vector.set(asRowMatrix ? col : row, value);
		return this;
	}

	@Override
	public IMatrix copy() {
		return new MatrixVectorView(vector.copy(), asRowMatrix);
	}

	/**
	 * @throws IncompatibleOperandException - ako barem jedan od parametara nije jednak 1 
	 * 											ili je neki parametar manji od 1.
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		if(rows > 1 && cols > 1 || rows < 0 || cols < 0) {
			throw new IncompatibleOperandException("This matrix should have only 1 row or 1 column.");
		}
		return new MatrixVectorView(vector.newInstance(rows > 1 ? rows : cols), cols > 1);
	}

}
