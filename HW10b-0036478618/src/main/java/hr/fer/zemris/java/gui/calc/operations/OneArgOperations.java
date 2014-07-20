package hr.fer.zemris.java.gui.calc.operations;

import static java.lang.Math.*;

/**
 * Class that can execute an operation on given argument.
 * Operation and argument should both be given as Strings.
 * Result is returned as String.<br>
 * Known functions are: "sin", "cos", "log", "ln", "1/x", "tan", "ctg"<br>
 * All functions can be inverse.
 * @author Ivan Krpelnik
 *
 */
public class OneArgOperations {
	
	private OneArgOperations() {
	}
	
	/**
	 * Generates result for given operation and argument.
	 * Can return Infinity(NaN).
	 * @param operation - operation that should be executed on given argument
	 * @param argument - number argument of operation
	 * @param inverse - true if operation should be inverse
	 * @return - String result of operation on given argument
	 * @throws NumberFormatException - if given argument cannot be parsed to double
	 * @throws IllegalArgumentException - if given operation is unknown
	 */
	public static String doOperation(String operation, String argument, boolean inverse) {
		double operand = Double.parseDouble(argument);
		double rez;
		switch(operation) {
			case "sin":
				rez = sinx(operand, inverse);
				break;
			case "cos":
				rez = cosx(operand, inverse);
				break;
			case "log":
				rez = logx(operand, inverse);
				break;
			case "ln":
				rez = lnx(operand, inverse);
				break;
			case "1/x":
				rez = reciprocal(operand, inverse);
				break;
			case "tan":
				rez = tanx(operand, inverse);
				break;
			case "ctg":
				rez = ctgx(operand, inverse);
				break;
			default:
				throw new IllegalArgumentException("Unknown operation " + operation);
		}
		return String.valueOf(rez);
	}

	/**
	 * Reciprocal function (1 / x)
	 * @param operand - x
	 * @param inverse
	 * @return (1/x) if inverse is false, x if inverse is true
	 */
	private static double reciprocal(double operand, boolean inverse) {
		if(inverse) {
			return operand;
		} else {
			return 1./operand;
		}
	}	
	
	/**
	 * Sine function (sin x)
	 * @param operand - x
	 * @param inverse
	 * @return sin(x) if inverse is false, arcsin(x) if inverse is true
	 */
	private static double sinx(double operand, boolean inverse) {
		if(inverse) {
			return asin(operand);
		} else {
			return sin(operand);
		}
	}
	
	/**
	 * Log function with base 10.
	 * @param operand - x
	 * @param inverse
	 * @return log(x) if inverse is false, 10^x if inverse is true 
	 */
	private static double logx(double operand, boolean inverse) {
		if(inverse) {
			return pow(10, operand);
		} else {
			return log10(operand);
		}
	}
	
	/**
	 * Cosine function (cos x)
	 * @param operand - x
	 * @param inverse
	 * @return cos(x) if inverse is false, arccos(x) if inverse is true
	 */
	private static double cosx(double operand, boolean inverse) {
		if(inverse) {
			return acos(operand);
		} else {
			return cos(operand);
		}
	}
	
	/**
	 * Tangent function (tan x)
	 * @param operand - x
	 * @param inverse
	 * @return tan(x) if inverse is false, arctan(x) if inverse is true
	 */
	private static double tanx(double operand, boolean inverse) {
		if(inverse) {
			return atan(operand);
		} else {
			return tan(operand);
		}
	}
	
	/**
	 * Log function with base e (base of natural logarithm).
	 * @param operand - x
	 * @param inverse
	 * @return ln(x) if inverse is false, e^x if inverse is true 
	 */
	private static double lnx(double operand, boolean inverse) {
		if(inverse) {
			return pow(E, operand);
		} else {
			return log(operand);
		}
	}
	
	/**
	 * Cotangent function (sin x)
	 * @param operand - x
	 * @param inverse
	 * @return cot(x) if inverse is false, arccot(x) if inverse is true
	 */
	private static double ctgx(double operand, boolean inverse) {
		if(inverse) {
			return atan(1./operand);
		} else {
			return tan(1./operand);
		}
	}
}
