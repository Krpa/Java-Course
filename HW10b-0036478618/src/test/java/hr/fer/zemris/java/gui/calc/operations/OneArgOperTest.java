package hr.fer.zemris.java.gui.calc.operations;

import org.junit.Test;
import static org.junit.Assert.*;
import static java.lang.Math.*;

public class OneArgOperTest {

	@Test
	public void sinTest() {
		assertEquals(String.valueOf(sin(PI/3)), OneArgOperations.doOperation("sin", String.valueOf(PI/3), false));
		assertEquals(String.valueOf(asin(sin(PI/3))), OneArgOperations.doOperation("sin", String.valueOf(sin(PI/3)), true));
	}
	
	@Test
	public void cosTest() {
		assertEquals(String.valueOf(cos(PI/3)), OneArgOperations.doOperation("cos", String.valueOf(PI/3), false));
		assertEquals(String.valueOf(acos(cos(PI/3))), OneArgOperations.doOperation("cos", String.valueOf(cos(PI/3)), true));
	}
	
	@Test
	public void tanTest() {
		assertEquals(String.valueOf(tan(PI/3)), OneArgOperations.doOperation("tan", String.valueOf(PI/3), false));
		assertEquals(String.valueOf(atan(tan(PI/3))), OneArgOperations.doOperation("tan", String.valueOf(tan(PI/3)), true));
	}
	
	@Test
	public void ctgTest() {
		assertEquals(String.valueOf(tan(1./(PI/3))), OneArgOperations.doOperation("ctg", String.valueOf(PI/3), false));
		assertEquals(String.valueOf(atan(1./tan(PI/3))), OneArgOperations.doOperation("ctg", String.valueOf(tan(PI/3)), true));
	}
	
	@Test
	public void lnTest() {
		assertEquals(String.valueOf(log(13)), OneArgOperations.doOperation("ln", "13", false));
		assertEquals(String.valueOf(pow(E, 13)), OneArgOperations.doOperation("ln", "13", true));
	}
	
	@Test
	public void logTest() {
		assertEquals(String.valueOf(log10(13)), OneArgOperations.doOperation("log", "13", false));
		assertEquals(String.valueOf(pow(10, 13)), OneArgOperations.doOperation("log", "13", true));
	}
	
	@Test
	public void reciprocalTest() {
		assertEquals(String.valueOf(1./12), OneArgOperations.doOperation("1/x", "12", false));
		assertEquals(String.valueOf(12.), OneArgOperations.doOperation("1/x", "12", true));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalArgTest() {
		OneArgOperations.doOperation("asdf", "1234", true);
	}
	
	@Test(expected=NumberFormatException.class)
	public void numbFormatTest() {
		OneArgOperations.doOperation("sin", "1afs234", true);
	}
}
