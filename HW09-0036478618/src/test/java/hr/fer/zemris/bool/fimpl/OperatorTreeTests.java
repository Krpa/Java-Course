package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanConstant;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.opimpl.BooleanOperators;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link OperatorTreeBF}
 * @author Ivan Krpelnik
 *
 */
public class OperatorTreeTests {

	@Test
	public void OperatorTreeBFtest1() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperator izraz1 = BooleanOperators.or(
		BooleanConstant.FALSE, 
		varC,
		BooleanOperators.and(varA, BooleanOperators.not(varB))
		);
		BooleanFunction f1 = new OperatorTreeBF(
		"f1", 
		Arrays.asList(varA, varB, varC), 
		izraz1);
		
		List<Integer> minterms = Arrays.asList(1, 3, 4, 5, 7);
		List<Integer> maxterms = Arrays.asList(0, 2, 6);
		
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
			System.out.println(i);
			j++;
		}
		assertEquals(0, j);
		

		assertEquals(true, f1.hasMinterm(1));
		assertEquals(false, f1.hasMinterm(2));
		assertEquals(true, f1.hasMaxterm(0));
		assertEquals(false, f1.hasMaxterm(3));
		assertEquals("f1", f1.getName());
		
		BooleanOperator izraz2 = BooleanOperators.or(
				BooleanConstant.DONT_CARE, 
				varC,
				BooleanOperators.and(varA, BooleanOperators.not(varB))
				);
		BooleanFunction f2 = new OperatorTreeBF(
				"f2", 
				Arrays.asList(varA, varB, varC), 
				izraz2);
		assertEquals(true, f2.hasDontCare(0));
		assertEquals(false, f2.hasDontCare(1));
	}
	
}
