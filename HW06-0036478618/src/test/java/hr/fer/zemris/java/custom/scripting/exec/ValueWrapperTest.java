package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;
import org.junit.Test;

public class ValueWrapperTest {

	@Test
	public void constructorTest() {
		String s = "Test";
		ValueWrapper value = new ValueWrapper(s);
		assertEquals(s, value.getValue());
		s = "TestSet";
		value.setValue(s);
		assertEquals(s, value.getValue());
	}
	
	@Test
	public void incrementTest1() {
		String prvi = "123";
		String drugi = "321";
		ValueWrapper first = new ValueWrapper(prvi);
		ValueWrapper second = new ValueWrapper(drugi);
		first.increment(second.getValue());
		assertEquals(444, first.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void incrementTestException() {
		String prvi = "132";
		String drugi = "asg.asg";
		ValueWrapper first = new ValueWrapper(prvi);
		ValueWrapper second = new ValueWrapper(drugi);
		first.increment(second.getValue());
	}
	
	@Test
	public void decrementTest() {
		Double prvi = 3.1415;
		Integer drugi = 3;
		ValueWrapper first = new ValueWrapper(prvi);
		ValueWrapper second = new ValueWrapper(drugi);
		second.decrement(first.getValue());
		assertEquals(true, (Double)second.getValue() - 0.1415 < 1e6);
	}
	
	@Test(expected=RuntimeException.class)
	public void decrementTestException() {
		String prvi = "132";
		String drugi = "asgasg";
		ValueWrapper first = new ValueWrapper(prvi);
		ValueWrapper second = new ValueWrapper(drugi);
		first.decrement(second.getValue());
	}
	
	@Test
	public void multiplyTest() {
		Integer prvi = 3;
		String drugi = null;
		ValueWrapper first = new ValueWrapper(prvi);
		first.multiply(drugi);
		assertEquals(0, first.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void multiplyException() {
		Integer prvi = 3;
		Object drugi = new Object();
		ValueWrapper first = new ValueWrapper(prvi);
		first.multiply(drugi);
	}
	
	@Test
	public void divideTest() {
		String prvi = "12.0";
		Integer drugi = 4;
		ValueWrapper first = new ValueWrapper(prvi);
		first.divide(drugi);
		assertEquals(3.0, first.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void divideException() {
		Integer prvi = 3;
		ValueWrapper first = new ValueWrapper(prvi);
		first.divide(0);
		System.out.println(first.getValue());
	}
	
	@Test
	public void numCompareTest() {
		Integer prvi = 3;
		String drugi = "3";
		ValueWrapper first = new ValueWrapper(prvi);
		assertEquals(0, first.numCompare(drugi));
	}
	
	@Test(expected=RuntimeException.class)
	public void numCompareExceptionTest() {
		ValueWrapper first = new ValueWrapper(3);
		first.numCompare(new Object());
	}
}
