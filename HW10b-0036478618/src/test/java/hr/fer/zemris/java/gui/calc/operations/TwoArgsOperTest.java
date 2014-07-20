package hr.fer.zemris.java.gui.calc.operations;

import static java.lang.Math.pow;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TwoArgsOperTest {

	@Test
	public void divTest() {
		assertEquals(String.valueOf(19./7), TwoArgsOperations.doOperation("/", "19", "7", true));
		assertEquals(String.valueOf(19./7), TwoArgsOperations.doOperation("/", "19", "7", false));
	}
	
	
	@Test
	public void mulTest() {
		assertEquals(String.valueOf(19.*7), TwoArgsOperations.doOperation("*", "19", "7", true));
		assertEquals(String.valueOf(19.*7), TwoArgsOperations.doOperation("*", "19", "7", false));
	}
	
	@Test
	public void subTest() {
		assertEquals(String.valueOf(19.123-7), TwoArgsOperations.doOperation("-", "19.123", "7", true));
		assertEquals(String.valueOf(19.123-7), TwoArgsOperations.doOperation("-", "19.123", "7", false));
	}
	
	@Test
	public void addTest() {
		assertEquals(String.valueOf(19.531+7), TwoArgsOperations.doOperation("+", "19.531", "7", true));
		assertEquals(String.valueOf(19.531+7), TwoArgsOperations.doOperation("+", "19.531", "7", false));
	}
	
	@Test
	public void powTest() {
		assertEquals(String.valueOf(pow(19, 1./7)), TwoArgsOperations.doOperation("x^n", "19", "7", true));
		assertEquals(String.valueOf(pow(19, 7)), TwoArgsOperations.doOperation("x^n", "19", "7", false));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalArgTest() {
		TwoArgsOperations.doOperation("asdf", "123", "521", true);
	}
	
	@Test(expected=NumberFormatException.class)
	public void numbFormatTest1() {
		TwoArgsOperations.doOperation("+", "1afs234", "123", true);
	}
	
	@Test(expected=NumberFormatException.class)
	public void numbFormatTest2() {
		TwoArgsOperations.doOperation("+", "123", "1afs234", true);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void divByZero() {
		TwoArgsOperations.doOperation("/", "124", "0", false);
	}
}
