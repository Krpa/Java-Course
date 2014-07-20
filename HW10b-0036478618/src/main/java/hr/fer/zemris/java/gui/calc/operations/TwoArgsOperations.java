package hr.fer.zemris.java.gui.calc.operations;

/**
 * Class that can execute operations with 2 arguments.
 * Operator and arguments should be given with Strings.
 * Result is generated as String.<br>
 * Supported operators are: "/", "*", "-", "+", "x^n".<br>
 * Boolean inverse works only on operators:<br>
 * "x^n"<br>
 * @author Ivan Krpelnik
 *
 */
public class TwoArgsOperations {

	private TwoArgsOperations() {	
	}
	
	/**
	 * Generates result for given operands and operator.
	 * @param operator - operator that shouold be executed
	 * @param first - first operand
	 * @param second - second operand
	 * @param inverse - true if function should be inverse.
	 * @return result - string
	 * @throws NumberFormatException - if given operands cannot be parsed to double
	 * @throws {@link IllegalArgumentException} - if unknown operation is given or if division by zero is called
	 */
	public static String doOperation(String operator, String first, String second, boolean inverse) {
		double firstOperand;
		double secondOperand;
		firstOperand = Double.valueOf(first);
		secondOperand = Double.valueOf(second);
		double rez = 0;
		switch(operator) {
			case "/": 
				if(secondOperand == 0) {
					throw new IllegalArgumentException("Cannot divide by zero.");
				}
				rez = firstOperand / secondOperand;
				break;
			case "*":
				rez = firstOperand * secondOperand;
				break;
			case "-":
				rez = firstOperand - secondOperand;
				break;
			case "+":
				rez = firstOperand + secondOperand;
				break;
			case "x^n":
				rez = Math.pow(firstOperand, inverse ? 1./secondOperand: secondOperand);
				break;
			default:
				throw new IllegalArgumentException("Unkown operation");
		}
		return String.valueOf(rez);
	}
}
