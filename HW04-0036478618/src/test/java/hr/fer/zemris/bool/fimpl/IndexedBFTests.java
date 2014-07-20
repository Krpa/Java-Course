package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for {@link IndexedBF}
 * @author Ivan Krpelnik
 *
 */
public class IndexedBFTests {
	
	@Test
	public void IndexedBFtest1() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		List<Integer> minterms = Arrays.asList(0, 1, 5, 7);
		List<Integer> maxterms = Arrays.asList(4, 6);
		List<Integer> dontCares = Arrays.asList(2, 3);
		BooleanFunction f1 = new IndexedBF(
		"f1", 
		Arrays.asList(varA, varB, varC), 
		true,
		minterms,
		dontCares
		);
		int j = 0;
		for(Integer i : f1.mintermIterable()) {
			assertEquals(minterms.get(j), i);
			j++;
		}
		j = 0;
		for(Integer i : f1.maxtermIterable()) {
			assertEquals(maxterms.get(j), i);
			j++;
		}
		j = 0;
		for(Integer i : f1.dontcareIterable()) {
			assertEquals(dontCares.get(j), i);
			j++;
		}
		assertEquals(true, f1.hasMinterm(0));
		assertEquals(false, f1.hasMinterm(2));
		assertEquals(true, f1.hasMaxterm(4));
		assertEquals(false, f1.hasMaxterm(3));
		assertEquals(true, f1.hasDontCare(2));
		assertEquals(false, f1.hasDontCare(5));
		assertEquals(BooleanValue.TRUE, f1.getValue());	
		varB.setValue(BooleanValue.TRUE);
		System.out.println(f1.getValue());
	}
	
	@Test
	public void IndexedBFtest2() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		List<Integer> maxterms = Arrays.asList(0, 1, 5, 7);
		List<Integer> minterms = Arrays.asList(4, 6);
		List<Integer> dontCares = Arrays.asList(2, 3);
		BooleanFunction f1 = new IndexedBF(
		"f1", 
		Arrays.asList(varA, varB, varC), 
		false,
		maxterms,
		dontCares
		);
		int j = 0;
		for(Integer i : f1.mintermIterable()) {
			assertEquals(minterms.get(j), i);
			j++;
		}
		j = 0;
		for(Integer i : f1.maxtermIterable()) {
			assertEquals(maxterms.get(j), i);
			j++;
		}
		j = 0;
		for(Integer i : f1.dontcareIterable()) {
			assertEquals(dontCares.get(j), i);
			j++;
		}
		assertEquals(true, f1.hasMaxterm(0));
		assertEquals(false, f1.hasMaxterm(2));
		assertEquals(true, f1.hasMinterm(4));
		assertEquals(false, f1.hasMinterm(3));
		assertEquals(true, f1.hasDontCare(2));
		assertEquals(false, f1.hasDontCare(5));
		assertEquals(BooleanValue.FALSE, f1.getValue());	
	}
	
	@Test
	public void IndexedBFtest3() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanFunction f2 = new IndexedBF(
				"f2", 
				Arrays.asList(varA, varB, varC), 
				true,
				new ArrayList<Integer>(),
				new ArrayList<Integer>()
				);
		for(int i = 0; i < 8; ++i)
			assertEquals(true, f2.hasMaxterm(i));
		for(int i = 0; i < 8; ++i)
			assertEquals(false, f2.hasMinterm(i));
		for(int i = 0; i < 8; ++i)
			assertEquals(false, f2.hasDontCare(i));
		assertEquals(false, f2.hasMaxterm(63));
	}
	
	@Test
	public void IndexedBFtest4() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanFunction f2 = new IndexedBF(
				"f2", 
				Arrays.asList(varA, varB, varC), 
				true,
				new ArrayList<Integer>(),
				new ArrayList<Integer>()
				);
		for(int i = 0; i < 8; ++i)
			assertEquals(true, f2.hasMaxterm(i));
		for(int i = 0; i < 8; ++i)
			assertEquals(false, f2.hasMinterm(i));
		for(int i = 0; i < 8; ++i)
			assertEquals(false, f2.hasDontCare(i));
		assertEquals(false, f2.hasMaxterm(63));
		assertEquals(BooleanValue.FALSE, f2.getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IndexedBFtest5() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		new IndexedBF(
				"f2", 
				Arrays.asList(varA, varB, varC), 
				true,
				new ArrayList<Integer>(10),
				new ArrayList<Integer>()
				);
		new IndexedBF(
				"f2", 
				Arrays.asList(varA, varB, varC), 
				true,
				new ArrayList<Integer>(),
				new ArrayList<Integer>(10)
				);
		new IndexedBF(
				"f2", 
				Arrays.asList(varA, varB, varC), 
				true,
				new ArrayList<Integer>(Arrays.asList(2, 3)),
				new ArrayList<Integer>(Arrays.asList(2, 3))
				);
	}
}
