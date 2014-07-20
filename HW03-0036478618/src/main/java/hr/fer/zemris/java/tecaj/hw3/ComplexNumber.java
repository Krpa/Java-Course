package hr.fer.zemris.java.tecaj.hw3;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * 
 * Class for storing and operating with complex numbers.
 * Stores 2 private doubles, real and imaginary parts of a complex number.
 * @author Ivan Krpelnik <br>
 */

public class ComplexNumber {

	private double real;
	private double imaginary;
	
	
	/**
	 * Constructs a complex number from real and imaginary parts.
	 * @param real
	 * @param imaginary
	 */
	public ComplexNumber(double real, double imaginary) {
		super();
		this.real = real;
		this.imaginary = imaginary;

	}
	
	
	/**
	 * Returns new ComplexNumber with given real part.
	 * @param real
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Returns new ComplexNumber with given imaginary part.
	 * @param imaginary
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Returns new ComplexNumber constructed from given magnitude and angle.
	 * @param magnitude
	 * @param angle
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		
		if(magnitude < 0) {
			throw new ComplexNumberException("Magnitude must not be negative.");
		}
		double real = magnitude * Math.cos(angle);
		double imaginary = magnitude * Math.sin(angle);
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Invokes a method for parsing a complex number from string.
	 * @param s String to be parsed.
	 * @throws ComplexNumberException if given string cannot be parsed.
	 * @return ComplexNumber constructed from given string.
	 */
	public static ComplexNumber parse(String s) {
		
		ComplexNumber ret;
		try {
			ret = getNumber(s);
		} catch(ComplexNumberException c) {
			throw c;
		} catch(Exception e) {
			throw new ComplexNumberException("Invalid number. Cannot convert " + s + " to complex number." , e);
		}
		
		return ret;
	}
	
	/**
	 * Parser for complex numbers. <br>
	 * Erases all whitespace characters from string. <br>
	 * Parser constructs from following regular expressions: <br>
	 * "[-+]?\\d+\\.?\\d*" <br>
	 * "[-+]?\\d+\\.?\\d*i" <br>
	 * "[-+]?\\d+\\.?\\d*[-+]\\d+\\.?\\d*i" <br>
	 * "[-+]?\\d+\\.?\\d*[-+]i" <br>
	 * "[-+]?i" <br>
	 * @param givenString String to be parsed
	 * @return ComplexNumber constructed from givenString
	 */
	private static ComplexNumber getNumber(String givenString) {
		String s = givenString.replaceAll("\\s", "");
		
		String regexReal = "[-+]?\\d+\\.?\\d*";
		String regexImaginary = "[-+]?\\d+\\.?\\d*i";
		String regexComplex1 = "[-+]?\\d+\\.?\\d*[-+]\\d+\\.?\\d*i";
		String regexComplex2 = "[-+]?\\d+\\.?\\d*[-+]i";
		String regexI = "[-+]?i";
		
		Pattern pattern = Pattern.compile(regexComplex1);
		Matcher matcher = pattern.matcher(s);
		
		//a + bi
		if(matcher.find()) {
			String temp = matcher.group();
			if(!temp.equals(s)) {
				throw new ComplexNumberException("Invalid string. Cannot convert " + s + " to complex number.");
			}
			
			String tempReal = giveSubString(temp, regexReal);
			String tempImaginary = giveSubString(temp, regexImaginary);
			tempImaginary = tempImaginary.substring(0, tempImaginary.length()-1);
			double real = Double.parseDouble(tempReal);
			double imaginary = Double.parseDouble(tempImaginary);
			
			return new ComplexNumber(real, imaginary);
		}
		
		//a + i
		pattern = Pattern.compile(regexComplex2);
		matcher = pattern.matcher(s);
		if(matcher.find()) {
			String temp = matcher.group();
			if(!temp.equals(s)) {
				throw new ComplexNumberException("Invalid string. Cannot convert " + s + " to complex number.");
			}
			
			String tempReal = giveSubString(temp, regexReal);
			String tempImaginary = giveSubString(temp, regexI);
			double real = Double.parseDouble(tempReal);
			double imaginary = tempImaginary.charAt(0) == '-' ? -1 : +1;
			
			return new ComplexNumber(real, imaginary);
		}
		
		//ai
		pattern = Pattern.compile(regexImaginary);
		matcher = pattern.matcher(s);
		if(matcher.find()) {
			String temp = matcher.group();
			if(!temp.equals(s)) {
				throw new ComplexNumberException("Invalid string. Cannot convert " + s + " to complex number.");
			}
			
			temp = temp.substring(0, temp.length()-1);
			double imaginary = Double.parseDouble(temp);
			
			return new ComplexNumber(0, imaginary);
		}
		
		//a
		pattern = Pattern.compile(regexReal);
		matcher = pattern.matcher(s);
		if(matcher.find()) {
			String temp = matcher.group();
			if(!temp.equals(s)) {
				throw new ComplexNumberException("Invalid string. Cannot convert " + s + " to complex number.");
			}
			
			double real = Double.parseDouble(temp);
			
			return new ComplexNumber(real, 0);
		}
		
		//i
		pattern = Pattern.compile(regexI);
		matcher = pattern.matcher(s);
		if(matcher.find()) {
			String temp = matcher.group();
			if(!temp.equals(s)) {
				throw new ComplexNumberException("Invalid string. Cannot convert " + s + " to complex number.");
			}
			
			int i = temp.charAt(0) == '-' ? -1 : 1;
			
			return new ComplexNumber(0, i);
		}
		throw new ComplexNumberException();
	}
	
	/**
	 * Returns substring of a given string.
	 * @param string - original string.
	 * @param regex - substring to be found
	 * @return String substring
	 */
	private static String giveSubString(String string, String regex) {
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		matcher.find();
		String ret = matcher.group();
		return ret;
	}

	/**
	 * Adds ComplexNumber to this ComplexNumber.
	 * @param c - ComplexNumber to be added.
	 * @return new ComplexNumber.
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c == null) {
			throw new ComplexNumberException("Cannot add null reference.");
		}
		
		return new ComplexNumber(this.real + c.getReal(), this.imaginary + c.getImaginary());
	}
	
	/**
	 * Subtracts ComplexNumber to this ComplexNumber.
	 * @param c - ComplexNumber to be subtracted.
	 * @return new ComplexNumber.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c == null) {
			throw new ComplexNumberException("Cannot substract null reference.");
		}
		
		return new ComplexNumber(this.real - c.getReal(), this.imaginary - c.getImaginary());
	}
	
	/**
	 * Multiplies this ComplexNumber by given ComplexNumber.
	 * @param c - ComplexNumber multiplier.
	 * @return new ComplexNumber.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c == null) {
			throw new ComplexNumberException("Cannot multiply by null reference.");
		}
		
		return new ComplexNumber(this.real * c.getReal() - this.imaginary * c.getImaginary(), 
				this.real*c.getImaginary() + this.imaginary*c.getReal());
	}
	
	/**
	 * Divides this ComplexNumber by given ComplexNumber.
	 * @param c - divider.
	 * @return new ComplexNumber.
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c == null) {
			throw new ComplexNumberException("Cannot divide by null reference.");
		}
		
		double norm = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();
		if(norm == 0) {
			throw new IllegalArgumentException("Divider must not be 0.");
		}
		
		return new ComplexNumber((this.real * c.getReal() + this.imaginary * c.getImaginary()) / norm, 
								 (-this.real * c.getImaginary() + this.imaginary * c.getReal()) / norm);
	}
	
	/**
	 * Calculates this ComplexNumber to the power of n.
	 * @param n
	 * @return new ComplexNumber.
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new ComplexNumberException("Power must be greater than or equal to 0.");
		}
		
		double r = Math.pow(this.real * this.real + this.imaginary * this.imaginary, n * 1./2);
		double fi = Math.atan2(this.imaginary, this.real);
		
		double real = r * Math.cos(fi * n);
		double imaginary = r * Math.sin(fi * n);
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Calculates nth roots of this ComplexNumber.
	 * @param n
	 * @return ComplexNumber[] - roots of this ComplexNumber
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new ComplexNumberException("Root must be greater than 0.");
		}
		
		ComplexNumber[] roots = new ComplexNumber[n];
		
		double r = Math.pow(this.real * this.real + this.imaginary * this.imaginary, 1. / (2 * n));
		double fi = Math.atan2(this.imaginary, this.real);
		for(int i = 0; i < n; ++i) {
			double real = r * Math.cos((fi + 2*i*Math.PI) / n);
			double complex = r * Math.sin((fi + 2*i*Math.PI) / n);
			roots[i] = new ComplexNumber(real, complex);
		}
		
		return roots;
	}
	
	/**
	 * Returns real part of this ComplexNumber.
	 * @return double
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns imaginary part of this ComplexNumber.
	 * @return double
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Calculates and returns magnitude of this ComplexNumber.
	 * @return double
	 */
	public double getMagnitude() {
		return Math.pow(real * real + imaginary * imaginary, 1. / 2);
	}

	/**
	 * Calculates and returns angle of this ComplexNumber.
	 * @return double
	 */
	public double getAngle() {
		return Math.atan2(imaginary, real);
	}
	
	
	/**
	 * Returns string representation of this complex number. <br>
	 * @return String
	 */
	@Override
	public String toString() {
		String ret;
		ret = Double.toString(real);
		ret += Math.signum(imaginary) == 1 || imaginary == 0 ? "+" : "";
		ret += Double.toString(imaginary) + "i";
		return ret;
	}
	
}
