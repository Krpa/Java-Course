package hr.fer.zemris.bool;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tests for package hr.fer.zemris.bool
 * @author Ivan Krpelnik
 *
 */
public class BoolTester {
	
	
	@Test(expected=NullPointerException.class)
	public void BooleanVariableTest() {
		String name = null;
		new BooleanVariable(name);
	}

	@Test(expected=NullPointerException.class) 
	public void BooleanVariableSetTest() {
		BooleanVariable bv = new BooleanVariable("asd");
		bv.setValue(null);
		System.out.println();
	}
	
	@Test
	public void BooleanVariableTest1() {
		String name = "ASDF";
		BooleanValue bv = BooleanValue.TRUE;
		BooleanVariable varijabla1 = new BooleanVariable(name);
		assertEquals(varijabla1.getName(), name);
		assertEquals(varijabla1.getValue(), BooleanValue.FALSE);
		varijabla1.setValue(bv);
		assertEquals(varijabla1.getValue(), bv);
		assertEquals(varijabla1.getDomain().get(0).getValue(), varijabla1.getValue());
		assertEquals(varijabla1.getDomain().get(0).getName(), varijabla1.getName());
	}
	
	@Test(expected=NullPointerException.class)
	public void MaskTest1() {
		new Mask(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void MaskTest2() {
		MaskValue[] values = {MaskValue.ONE, null};
		new Mask(values);
	}
	
	@Test
	public void MaskTest3() {
		MaskValue[] values = {MaskValue.ONE, MaskValue.ZERO, MaskValue.DONT_CARE, MaskValue.DONT_CARE};
		Mask m1 = new Mask(values);
		Mask m2 = Mask.parse("10Xx");
		assertEquals(m1, m2);
		assertEquals(false, m1.isMoreGeneralThan(m2));
		assertEquals(false, m2.isMoreGeneralThan(m1));
		assertEquals(m1.getNumberOfOnes(), m2.getNumberOfOnes());
		assertEquals(m1.getNumberOfZeroes(), m2.getNumberOfZeroes());
		assertEquals(m1.hashCode(), m2.hashCode());
		assertEquals(true, m1.equals(m2));
		assertEquals(null, Mask.combine(m1, m2));
	}
	
	@Test
	public void MaskTest4() {
		MaskValue[] values = {MaskValue.ONE, MaskValue.ZERO, MaskValue.DONT_CARE, MaskValue.DONT_CARE, MaskValue.ONE};
		Mask m1 = new Mask(values);
		Mask m2 = Mask.parse("10Xx0");
		Mask m3 = Mask.parse("10XXX");
		assertEquals(false, m1.equals(m2));
		assertEquals(m3, Mask.combine(m1, m2));
		assertEquals(null, Mask.combine(m2, m3));
		assertEquals(null, Mask.combine(m1, m3));
		assertEquals(null, Mask.combine(m1, Mask.parse("10")));
		assertEquals(null, Mask.combine(m3, Mask.parse("")));
		assertEquals(true, m3.isMoreGeneralThan(m2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void MasksTest1() {
		Masks.fromStrings("100", "1Xx", "100XxA");
		Masks.fromIndexes(1, 0, 1, 2);
		Masks.fromIndexes(1, 0, 1, -1);
	}
	
	@Test
	public void MasksTest2() {
		List<Mask> m1 = new ArrayList<>(Masks.fromIndexes(2, 3));
		List<Mask> m2 = new ArrayList<>(Masks.fromStrings("11"));
		assertEquals(true, m1.get(0).equals(m2.get(0)));
	}
}
