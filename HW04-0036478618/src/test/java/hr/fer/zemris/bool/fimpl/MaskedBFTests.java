package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Masks;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link MaskedBFTests}
 * @author Ivan Krpelnik
 *
 */
public class MaskedBFTests {

	@Test
	public void MaskedBFtest1() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		BooleanFunction f1 = new MaskBasedBF(
		"f1", 
		Arrays.asList(varA, varB, varC, varD), 
		true,
		Masks.fromStrings("00x0", "1xx1"),
		Masks.fromStrings("10x0")
		);
		List<Integer> minterms = Arrays.asList(0, 2, 9, 11, 13, 15);
		List<Integer> maxterms = Arrays.asList(1, 3, 4, 5, 6, 7, 12, 14);
		List<Integer> dontCares = Arrays.asList(8, 10);
		
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
		assertEquals(false, f1.hasMinterm(1));
		assertEquals(true, f1.hasMaxterm(1));
		assertEquals(false, f1.hasMaxterm(2));
		assertEquals(true, f1.hasDontCare(8));
		assertEquals(false, f1.hasDontCare(9));
	}
}
