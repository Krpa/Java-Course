package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class that stores an Object value. <br>
 * All operations are supported only for objects of String, Integer or Double.
 * Null is treated as Integer with value 0.
 * If some operation is called on object that is not supported by that operation, 
 * {@link RuntimeException} will be thrown.
 * @author Ivan Krpelnik
 *
 */
public class ValueWrapper {

	private Object value;
	
	/**
	 * Constructor
	 * @param value - value to be stored.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}
	
	/**
	 * Adds given value to value of this wrapper.
	 * @param incValue - given value.
	 * @throws RuntimeException if given values are not supported (i.e. Integer, Double, String are supported)
	 */
	public void increment(Object incValue) {
		try {
			this.value = obradiOperante(this.value, incValue, "+");
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Subtracts given value from value of this wrapper.
	 * @param incValue - given value.
	 * @throws RuntimeException if given values are not supported (i.e. Integer, Double, String are supported)
	 */
	public void decrement(Object incValue) {
		try {
			this.value = obradiOperante(this.value, incValue, "-");
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Multiplies value of this wrapper with given value.
	 * @param incValue - given value.
	 * @throws RuntimeException if given values are not supported (i.e. Integer, Double, String are supported) 
	 */
	public void multiply(Object incValue) {
		try {
			this.value = obradiOperante(this.value, incValue, "*");
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Divides value of this wrapper with given value.
	 * @param incValue - given value
	 * @throws RuntimeException if given values are not supported (i.e. Integer, Double, String are supported)
	 * 							or if incValue is 0.
	 */
	public void divide(Object incValue) {
		try {
			this.value = obradiOperante(this.value, incValue, "/");
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Compares value of this wrapper to given value.
	 * @param withValue - given value.
	 * @return <br>
	 * 			negative number - if value of this wrapper < given value<br>
	 * 			zero - if value of this wrapper == given value<br>
	 * 			positive number - if value of this wrapper > given value
	 * @throws RuntimeException if given values are not supported (i.e. Integer, Double, String are supported)
	 */
	public int numCompare(Object withValue) {
		Number first = obradiObjekt(this.value);
		Number second = obradiObjekt(withValue);
		first = Double.valueOf(first.doubleValue());
		second = Double.valueOf(second.doubleValue());
		return ((Double)first).compareTo((Double)second);
	}
	
	/**
	 * Getter.
	 * @return returns value of this wrapper.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets value of this wrapper to given value.
	 * @param value - given value.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Private method that extracts Number values from given objects and calls method that executes operation
	 * of given operator.
	 * @param first - first object
	 * @param second - second object
	 * @param op - given operator
	 * @return - result of operation
	 */
	private Number obradiOperante(Object first, Object second, String op) {
		Number prvi = obradiObjekt(first);
		Number drugi = obradiObjekt(second);
		return odradiOperaciju(prvi, drugi, op, (prvi instanceof Double) || (drugi instanceof Double));
	}
	
	/**
	 * Executes operation of given operator on given numbers.
	 * @param first - first number
	 * @param second - second number
	 * @param op - given operator.
	 * @param isDouble - true if result should be double, false otherwise.
	 * @return result of operation
	 */
	private Number odradiOperaciju(Number first, Number second, String op, boolean isDouble) {
		Number rez;
		switch(op) {
			case "+": 	rez = Double.valueOf(first.doubleValue() + second.doubleValue());
						break;
			case "-":	rez = Double.valueOf(first.doubleValue() - second.doubleValue());
						break;
			case "*":	rez = Double.valueOf(first.doubleValue() * second.doubleValue());
						break;
			case "/":	
						if(second.doubleValue() == 0) {
							throw new RuntimeException("Cannot divide by 0.");
						}
						rez = Double.valueOf(first.doubleValue() / second.doubleValue());
						break;
			default:	rez = null;
		}
		if(!isDouble) {
			rez = Integer.valueOf(rez.intValue());
		}
		return rez;
	}
	
	/**
	 * Extracts number from given object value.
	 * @param value - given object.
	 * @return - extracted number
	 */
	private Number obradiObjekt(Object value) {
		Number ret;
		if(value == null) {
			ret = Integer.valueOf(0);
		} else if(value instanceof Integer) {
			ret = (Integer) value;
		} else if(value instanceof Double) {
			ret = (Double) value;
		} else if(value instanceof String) {
			if(((String)value).contains(".")) {
				try {
					ret = Double.valueOf((String)value);
				} catch(NumberFormatException e) {
					throw new RuntimeException(e);
				}
			}
			else {
				try {
					ret = Integer.valueOf((String)value);
				} catch(NumberFormatException e) {
					throw new RuntimeException(e);
				}
			} 
		} else {
			throw new RuntimeException("Object stored by ValueWrapper should be String, Integer or Double.");
		}
		return ret;
	}
	
}
