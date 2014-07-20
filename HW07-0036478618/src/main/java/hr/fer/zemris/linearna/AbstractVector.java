package hr.fer.zemris.linearna;

/**
 * Bazna implementacija {@link IVector}.
 * @author Ivan Krpelnik
 *
 */
public abstract class AbstractVector implements IVector {

	public AbstractVector() {
	}
	
	/**
	 * @throws IndexOutOfBoundsException - ako je neki od indexa izvan granica.
	 */
	@Override
	public abstract double get(int index) throws IndexOutOfBoundsException;

	/**
	 * @throws IndexOutOfBoundsException - ako je neki od indexa izvan granica.
	 */
	@Override
	public abstract IVector set(int index, double value) throws IndexOutOfBoundsException;

	@Override
	public abstract int getDimension();

	@Override
	public abstract IVector copy();

	@Override
	public abstract IVector newInstance(int dimension);

	
	@Override
	public IVector copyPart(int n) {
		if(n <= 0) {
			throw new IndexOutOfBoundsException("Dimension should be > 0.");
		}
		IVector ret = this.newInstance(n);
		int size = this.getDimension();
		for(int i = 0; i < n && i < size; ++i) {
			ret.set(i, this.get(i));
		}
		return ret;
	}
	
	
	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		if(this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException("ADD - Vectors must be the same size.");
		}
		for(int i = this.getDimension() - 1; i >= 0; --i) {
			this.set(i, this.get(i) + other.get(i));
		}
		return this;
	}

	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		return this.copy().add(other);
	}

	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if(this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException("SUB - Vectors must be the same size.");
		}
		for(int i = this.getDimension() - 1; i >= 0; --i) {
			this.set(i, this.get(i) - other.get(i));
		}
		return this;
	}

	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		return this.copy().sub(other);
	}

	@Override
	public IVector scalarMultiply(double byValue) {
		
		for(int i = this.getDimension() - 1; i >= 0; --i) {
			this.set(i, this.get(i) * byValue);
		}
		return this;
	}

	@Override
	public IVector nScalarMultiply(double byValue) {
		return this.copy().scalarMultiply(byValue);
	}

	@Override
	public double norm() {
		double sum = 0;
		for(int i = this.getDimension() - 1; i >= 0; --i) {
			sum += this.get(i) * this.get(i);
		}
		return Math.sqrt(sum);
	}

	/**
	 * @throws IncompatibleOperandException - ako je norma = 0.
	 */
	@Override
	public IVector normalize() {
		if(this.norm() == 0) {
			throw new IncompatibleOperandException("NORMALIZE - norm should not be 0.");
		}
		return this.scalarMultiply(1./this.norm());
	}

	/**
	 * @throws IncompatibleOperandException - ako je norma = 0.
	 */
	@Override
	public IVector nNormalize() {
		return this.copy().normalize();
	}

	/**
	 * @throws IncompatibleOperandException - ako je norma = 0.
	 */
	@Override
	public double cosine(IVector other) throws IncompatibleOperandException {
		if(this.norm() == 0 || other.norm() == 0) {
			throw new IncompatibleOperandException("COS - cannot operate with null vector.");
		}
		return this.scalarProduct(other) / (this.norm() * other.norm());
	}

	/**
	 * @throws IncompatibleOperandException - ako je norma = 0 ili vektori nisu jednakih dimenzija.
	 */
	@Override
	public double scalarProduct(IVector other)
			throws IncompatibleOperandException {
		if(this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException("ADD - Vectors must be the same size.");
		}
		double sum = 0;
		for(int i = this.getDimension() - 1; i >= 0; i--) {
			sum += this.get(i) * other.get(i);
		}
		return sum;
	}

	/**
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector nVectorProduct(IVector other)
			throws IncompatibleOperandException {
		if(this.getDimension() != other.getDimension() || this.getDimension() != 3) {
			throw new IncompatibleOperandException("X product - Dimensions must be 3.");
		}
		IVector xProduct = this.newInstance(3);
		for(int i = 0; i < 3; ++i) {
			xProduct.set(i, this.get((i+1)%3) * other.get((i+2)%3) - this.get((i+2)%3) * other.get((i+1)%3));
		}
		return xProduct;
	}

	/**
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector nFromHomogeneus() {
		if(this.get(this.getDimension() - 1) == 0) {
			throw new IncompatibleOperandException();
		}
		IVector ret = this.copyPart(this.getDimension() - 1);
		return ret;
	}

	/**
	 * @see MatrixVectorView
	 */
	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		return new MatrixVectorView(liveView ? this : this.copy(), true);
	}

	/**
	 * @see MatrixVectorView
	 */
	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		return new MatrixVectorView(liveView ? this : this.copy(), false);
	}

	@Override
	public double[] toArray() {
		double[] ret = new double[this.getDimension()];
		for(int i = this.getDimension() - 1; i >= 0; i--) {
			ret[i] = this.get(i);
		}
		return ret;
	}
	
	/**
	 * Vraća reprezentaciju ovog vektora kao string.
	 * @param precision - broj decimala u ispisu vektora.
	 * @return string reprezentaciju ovog vektora.
	 */
	public String toString(int precision) {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		int dimension = this.getDimension();
		for(int i = 0; i < dimension; i++) {
			sb.append(String.format("%."+precision+"f", this.get(i)));
			if(i != dimension - 1) {
				sb.append(", ");
			} else {
				sb.append(")");
			}
		}
		return sb.toString();
	}

	/**
	 *	Default precision = 3.
	 */
	@Override
	public String toString() {
		return this.toString(3);
	}
	
	
}
