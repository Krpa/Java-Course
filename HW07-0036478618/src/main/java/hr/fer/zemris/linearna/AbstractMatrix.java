package hr.fer.zemris.linearna;


/**
 * Bazna implementacija {@link IMatrix}. 
 * @author Ivan Krpelnik
 *
 */
public abstract class AbstractMatrix implements IMatrix {

	public AbstractMatrix() {
	}
	
	@Override
	public abstract int getRowsCount();

	@Override
	public abstract int getColsCount();

	/**
	 * @throws IndexOutOfBoundsException - ako je neki od indexa izvan granica.
	 */
	@Override
	public abstract double get(int row, int col) throws IndexOutOfBoundsException;

	/**
	 * @throws IndexOutOfBoundsException - ako je neki od indexa izvan granica.
	 */
	@Override
	public abstract IMatrix set(int row, int col, double value) throws IndexOutOfBoundsException;
	
	@Override
	public abstract IMatrix copy();

	@Override
	public abstract IMatrix newInstance(int rows, int cols);

	/**
	 * @see MatrixTrasposeView
	 */
	@Override
	public IMatrix nTranspose(boolean liveView) {
		if(liveView) {
			return new MatrixTrasposeView(this);
		} else {
			return new MatrixTrasposeView(this.copy());
		}
	}

	/**
	 * @throws IncompatibleOperandException - ako matrice nisu jednakih dimenzija.
	 */
	@Override
	public IMatrix add(IMatrix other) {
		if(this.getRowsCount() != other.getRowsCount() || this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException("ADD - matrices should be the same size.");
		}
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < cols; ++j) {
				this.set(i, j, this.get(i, j) + other.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix nAdd(IMatrix other) {
		return this.copy().add(other);
	}

	/**
	 * @throws IncompatibleOperandException - ako matrice nisu jednakih dimenzija.
	 */
	@Override
	public IMatrix sub(IMatrix other) {
		if(this.getRowsCount() != other.getRowsCount() || this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException("SUB - matrices should be the same size.");
		}
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < cols; ++j) {
				this.set(i, j, this.get(i, j) - other.get(i, j));
			}
		}
		return this;
	}

	/**
	 * @throws IncompatibleOperandException - ako matrice nisu jednakih dimenzija.
	 */
	@Override
	public IMatrix nSub(IMatrix other) {
		return this.copy().sub(other);
	}

	/**
	 * @throws IncompatibleOperandException - ako matrice nisu jednakih dimenzija.
	 */
	@Override
	public IMatrix nMultiply(IMatrix other) {
		if(this.getColsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException("MUL - matrices are not compatibile.");
		}
		
		IMatrix matrix = this.newInstance(this.getRowsCount(), other.getColsCount());
		int rows = matrix.getRowsCount();
		int cols = matrix.getColsCount();
		int kSize = this.getColsCount();
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < cols; ++j) {
				double sum = 0;
				for(int k = 0; k < kSize; ++k) {
					sum += this.get(i, k) * other.get(k, j);
				}
				matrix.set(i, j, sum);
			}
		}
		return matrix;
	}

	/**
	 * @throws IncompatibleOperandException - ako matrica nije kvadratna.
	 */
	@Override
	public double determinant() throws IncompatibleOperandException {
		if(this.getRowsCount() != this.getColsCount()) {
			throw new IncompatibleOperandException("DET - matrix should be square.");
		}
		if(this.getColsCount() == 1) {
			return this.get(0, 0);
		}
		
		if (this.getRowsCount() == 2) {
	        return this.get(0, 0) * this.get(1, 1) - this.get(0, 1) * this.get(1, 0);
	    }
		
		double sum = 0;
		int sig;
		int size = this.getColsCount();
		
		for(int i = 0; i < size; ++i) {
			sig = i % 2 == 0 ? 1 : -1;
			sum += sig * this.get(0, i) * this.subMatrix(0, i, true).determinant();
		}
		return sum;
	}

	/**
	 * @see MatrixSubMatrixView
	 */
	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if(liveView) {
			return new MatrixSubMatrixView(this, row, col);
		} else {
			return new MatrixSubMatrixView(this.copy(), row, col);
		}
	}

	/**
	 * @throws IncompatibleOperandException - ako matrica nije kvadratna ili je singularna.
	 */
	@Override
	public IMatrix nInvert() {
		if(this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException("Matrix should be square.");
		}
		if(this.determinant() == 0) {
			throw new IncompatibleOperandException("Matrix should not be singular.");
		}
//		System.out.println(cofactor());
		return cofactor().nTranspose(true).nScalarMultiply(1.0/this.determinant());
	}
	
	private IMatrix cofactor() {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
	    IMatrix mat = new Matrix(rows, cols);
	    for(int i = 0; i < rows; i++) {
	        for(int j = 0; j < cols; j++) {
	        	IMatrix subMatrix = this.subMatrix(i, j, true);
	            mat.set(i, j, 
	            		(i % 2 == 0 ? 1 : -1) * 
	            		(j % 2 == 0 ? 1 : -1) * 
	            		subMatrix.determinant());
	        }
	    }
	    
	    return mat;
	}

	@Override
	public double[][] toArray() {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		double[][] ret = new double[rows][cols];
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < cols; ++j) {
				ret[i][j] = this.get(i, j);
			}
		}
		return ret;
	}

	/**
	 * @see VectorMatrixView
	 */
	@Override
	public IVector toVector(boolean liveView) {
		return new VectorMatrixView(liveView ? this : this.copy());
	}

	@Override
	public IMatrix nScalarMultiply(double value) {
		return this.copy().scalarMultiply(value);
	}

	@Override
	public IMatrix scalarMultiply(double value) {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < cols; ++j) {
				this.set(i, j, this.get(i, j)*value);
			}
		}
		return this;
	}

	/**
	 * @throws IncompatibleOperandException - ako matrica nije kvadratna.
	 */
	@Override
	public IMatrix makeIdentity() {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		if(rows != cols) {
			throw new IncompatibleOperandException("Identity matrix must be square.");
		}
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < cols; ++j) {
				this.set(i, j, i == j ? 1 : 0);
			}
		}
		return this;
	}
	
	/**
	 * @param precision - broj decimala
	 * @return string reprezentacija matrice
	 */
	public String toString(int precision) {
		StringBuilder sb = new StringBuilder();
		int rows = this.getRowsCount();
		for(int r = 0; r < rows; ++r) {
			sb.append("[");
			int cols = this.getColsCount();
			for(int i = 0; i < cols; i++) {
				sb.append(String.format("%."+precision+"f", this.get(r, i)));
				if(i != cols - 1) {
					sb.append(", ");
				} else {
					sb.append("]");
				}
			}
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return this.toString(3);
	}

}
