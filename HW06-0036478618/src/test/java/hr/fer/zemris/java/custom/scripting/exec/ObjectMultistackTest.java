package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

public class ObjectMultistackTest {

	@Test
	public void constructorTest() {
		new ObjectMultistack();
	}
	
	@Test
	public void pushPopPeekTest() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.push("Test", new ValueWrapper(3));
		stack.push("Test", new ValueWrapper(5));
		
		assertEquals(5, stack.peek("Test").getValue());
		assertEquals(5, stack.pop("Test").getValue());
		assertEquals(3, stack.pop("Test").getValue());
	}
	
	@Test(expected=EmptyStackException.class)
	public void popException() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.pop("Test");
	}
	
	@Test(expected=EmptyStackException.class)
	public void peekException() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.peek("Test");
	}
	
	@Test
	public void isEmptyTest() {
		ObjectMultistack stack = new ObjectMultistack();
		assertEquals(true, stack.isEmpty("Test"));
		stack.push("Test", new ValueWrapper(42));
		assertEquals(false, stack.isEmpty("Test"));
	}
}
