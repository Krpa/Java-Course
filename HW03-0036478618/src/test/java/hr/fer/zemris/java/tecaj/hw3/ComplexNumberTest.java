package hr.fer.zemris.java.tecaj.hw3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for ComplexNumber class.
 * @author Ivan Krpelnik
 */
public class ComplexNumberTest {
	
	@Test
	public void testFromImaginary() {
		ComplexNumber complexNumber1 = new ComplexNumber(0, 1);
		ComplexNumber complexNumber2 = ComplexNumber.fromImaginary(1);
		assertEquals(complexNumber1.toString(), complexNumber2.toString());
	}
	
	@Test
	public void testFromReal() {
		ComplexNumber complexNumber1 = new ComplexNumber(1, 0);
		ComplexNumber complexNumber2 = ComplexNumber.fromReal(1);
		assertEquals(complexNumber1.toString(), complexNumber2.toString());
	}
	
	@Test
	public void testFromMagnitudeReal() {
		ComplexNumber complexNumber1 = new ComplexNumber(3, 0);
		ComplexNumber complexNumber2 = ComplexNumber.fromMagnitudeAndAngle(3, 0);
		assertEquals(complexNumber1.toString(), complexNumber2.toString());
	}
	
	@Test(expected=ComplexNumberException.class)
	public void testNegativeMagnitude() {
		ComplexNumber.fromMagnitudeAndAngle(-1, 3.14);
	}
	
	@Test(expected=ComplexNumberException.class)
	public void testParseException1() {
		ComplexNumber.parse("521i+531.53i");
	}
	
	@Test(expected=ComplexNumberException.class)
	public void testParseException2() {
		ComplexNumber.parse("             ");
	}
	
	@Test(expected=ComplexNumberException.class)
	public void testParseException3() {
		ComplexNumber.parse("513+53i igje315t1");
	}

	@Test
	public void testParse1() {
		assertEquals("-1.0-2.0i",ComplexNumber.parse("- 1 - 2 i").toString());
	}
	
	@Test
	public void testParse2() {
		assertEquals("1.0+2.0i", ComplexNumber.parse("1    + 2  i  ").toString());
	}
	
	@Test
	public void testParse3() {
		assertEquals("1.0-2.0i", ComplexNumber.parse("1 - 2  i  ").toString());
	}
	
	@Test
	public void testParse4() {
		assertEquals("-1.0+2.0i", ComplexNumber.parse("-1 + 2  i  ").toString());
	}
	
	@Test
	public void testParse5() {
		assertEquals("1.0-1.0i", ComplexNumber.parse("1-i").toString());
	}
	
	@Test
	public void testParse6() {
		assertEquals("1.0+1.0i", ComplexNumber.parse("1+i").toString());
	}

	@Test
	public void testParse7() {
		assertEquals("1522.512+0.0i", ComplexNumber.parse("1522.512").toString());
	}
	
	@Test
	public void testParse8() {
		assertEquals("0.0+151.52i", ComplexNumber.parse("151.52i").toString());
	}
	
	@Test
	public void testParse9() {
		assertEquals("0.0+1.0i", ComplexNumber.parse("i").toString());
	}

	@Test
	public void testParse10() {
		assertEquals("0.0-1.0i", ComplexNumber.parse("-i").toString());
	}
	

	@Test
	public void testParse11() {
		assertEquals("0.0-23.0i", ComplexNumber.parse("-23i").toString());
	}
	
	@Test
	public void testAdd() {
		ComplexNumber c1 = new ComplexNumber(1, 2);
		c1 = c1.add(new ComplexNumber(3, 4));
		assertEquals("4.0+6.0i", c1.toString());
	}
	
	@Test
	public void testSub() {
		ComplexNumber c1 = new ComplexNumber(1, 2);
		c1 = c1.sub(new ComplexNumber(4, 1));
		assertEquals("-3.0+1.0i", c1.toString());
	}
	
	@Test
	public void testMul() {
		ComplexNumber c1 = new ComplexNumber(1, 2);
		c1 = c1.mul(new ComplexNumber(3, 4));
		assertEquals("-5.0+10.0i", c1.toString());
	}
	
	@Test
	public void testDiv() {
		ComplexNumber c1 = new ComplexNumber(1, 2);
		c1 = c1.div(new ComplexNumber(0, 1));
		assertEquals("2.0-1.0i", c1.toString());
	}
	
	@Test
	public void testPower() {
		ComplexNumber c1 = new ComplexNumber(0, 1);
		c1 = c1.power(2);
		assertEquals(-1.0, c1.getReal(), 1e-6);
		assertEquals(0.0, c1.getImaginary(), 1e-6);
	}
	
	@Test
	public void testRoot() {
		ComplexNumber c1 = new ComplexNumber(-1, 0);
		ComplexNumber[] roots = c1.root(2);
		assertEquals(0.0, roots[0].getReal(), 1e-6);
		assertEquals(1.0, roots[0].getImaginary(), 1e-6);
		assertEquals(0.0, roots[1].getReal(), 1e-6);
		assertEquals(-1.0, roots[1].getImaginary(), 1e-6);
	}
	
	@Test
	public void getAngle() {
		ComplexNumber c1 = new ComplexNumber(0, 1);
		assertEquals(Math.PI/2, c1.getAngle(), 1e-6);;
	}
	
	@Test
	public void getMagnitude() {
		ComplexNumber c1 = new ComplexNumber(0, 1);
		assertEquals(1.0, c1.getMagnitude(), 1e-6);
	}
}
