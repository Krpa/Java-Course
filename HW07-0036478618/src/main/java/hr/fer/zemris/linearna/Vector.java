package hr.fer.zemris.linearna;

/**
 * Implementacija razreda {@link AbstractVector}.
 * Razred moze biti nepromijenjiv i promijenjiv.
 * @author Ivan Krpelnik
 *
 */
public class Vector extends AbstractVector {

	private double[] elements;
	private int dimension;
	private boolean readOnly;
	
	/**
	 * Konstruktor
	 * @param elements - parametri vektora
	 */
	public Vector(double...elements) {
		this(false, false, elements);
	}
	
	/**
	 * Konstruktor
	 * @param readOnly - true ako razred treba biti nepromijenjiv.
	 * @param useElements - true ako ne treba kopirati vrijednosti iz danog niza, 
	 * nego se moze koristiti dana referenca.
	 * @param elements - parametri vektora.
	 */
	public Vector(boolean readOnly, boolean useElements, double...elements) {
		this.readOnly = readOnly;
		this.dimension = elements.length;
		if(useElements) {
			this.elements = elements;
		} else {
			this.elements = new double[elements.length];
			for(int i = 0; i < elements.length; ++i) {
				this.elements[i] = elements[i];
			}
		}
	}
	
	/**
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public double get(int index) {
		if(index < 0 || index >= dimension) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}

	/**
	 * @throws UnmodifiableObjectException - ako je vektor read only.
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public IVector set(int index, double value) {
		if(readOnly) {
			throw new UnmodifiableObjectException("Cannot modify read-only vector.");
		}
		if(index < 0 || index >= dimension) {
			throw new IndexOutOfBoundsException();
		}
		elements[index] = value;
		return this;
	}

	/**
	 * @throws UnmodifiableObjectException - ako je vektor nepromijenjiv
	 */
	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		if(readOnly) {
			throw new UnmodifiableObjectException();
		}
		return super.add(other);
	}
	
	/**
	 * @throws IncompatibleOperandException - ako je zadnji parametar = 0
	 * @throws UnmodifiableObjectException - ako je vektor nepromijenjiv
	 */
	@Override
	public IVector normalize() {
		if(readOnly) {
			throw new UnmodifiableObjectException();
		}
		return super.normalize();
	}
	
	/**
	 * @throws UnmodifiableObjectException - ako je vektor nepromijenjiv
	 */
	@Override
	public IVector scalarMultiply(double byValue) {
		if(readOnly) {
			throw new UnmodifiableObjectException();
		}
		return super.scalarMultiply(byValue);
	}
	
	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if(readOnly) {
			throw new UnmodifiableObjectException();
		}
		return super.sub(other);
	}
	
	

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public IVector copy() {
		return this.copyPart(getDimension());
	}

	@Override
	public IVector newInstance(int dimension) {
		return new Vector(new double[dimension]);
	}

	/**
	 * Stvara vektor iz danog stringa.
	 * @param s - dani string
	 * @return - vektor
	 */
	public static Vector parseSimple(String s) {
		String[] tokens = s.trim().replaceAll("\\s", " ").split(" ");
		double[] values = new double[tokens.length];
		for(int i = 0; i < tokens.length; ++i) {
			values[i] = Double.parseDouble(tokens[i]);
		}
		return new Vector(values);
	}
}
