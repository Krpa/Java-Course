package hr.fer.zemris.linearna;

import static org.junit.Assert.*;

import org.junit.Test;

public class VectorTest {

	@Test
	public void getTest() {
		IVector vektor = new Vector(1, 2, 3);
		assertEquals(1, vektor.get(0), 1e-6);
		assertEquals(2, vektor.get(1), 1e-6);
		assertEquals(3, vektor.get(2), 1e-6);
		
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getTestException() {
		IVector vektor = new Vector(1, 2, 3);
		vektor.get(3);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getTestException2() {
		IVector vektor = new Vector(1);
		vektor.get(-1);
	}
	
	@Test
	public void setTest() {
		IVector vektor = new Vector(1, 2, 3);
		vektor.set(0, 3);
		vektor.set(1, 1);
		vektor.set(2, 2);
		assertEquals(3, vektor.get(0), 1e6);
		assertEquals(1, vektor.get(1), 1e6);
		assertEquals(2, vektor.get(2), 1e6);
	}
	
	@Test(expected=UnmodifiableObjectException.class)
	public void setTestException() {
		IVector vektor = new Vector(true, true, 1, 2, 3);
		vektor.set(1, 1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void setTestException2() {
		IVector vektor = new Vector(false, false, 1, 2, 3);
		vektor.set(-1, 1);
	}
	
	@Test
	public void baricentricneKoordinate()  {
		IVector a = Vector.parseSimple("1 0 0");
		IVector b = Vector.parseSimple("5 0 0");
		IVector c = Vector.parseSimple("3 8 0");
		
		IVector t = Vector.parseSimple("3 4 0");
		
		
		double pov = b.nSub(a).nVectorProduct(c.nSub(a)).norm() / 2.0;
		double povA = b.nSub(t).nVectorProduct(c.nSub(t)).norm() / 2.0;
		double povB = a.nSub(t).nVectorProduct(c.nSub(t)).norm() / 2.0;
		double povC = a.nSub(t).nVectorProduct(b.nSub(t)).norm() / 2.0;
		
		double t1 = povA / pov;
		double t2 = povB / pov;
		double t3 = povC / pov;
		
		assertEquals(0.25, t1, 1e-6);
		assertEquals(0.25, t2, 1e-6);
		assertEquals(0.5, t3, 1e-6);
	}
	
	@Test(expected=UnmodifiableObjectException.class)
	public void testSubException() {
		IVector a = new Vector(true, true, 1, 2, 3);
		a.sub(a);
	}
	
	@Test(expected=UnmodifiableObjectException.class)
	public void testAddException() {
		IVector a = new Vector(true, true, 1, 2, 3);
		a.add(a);
	}
	
	@Test(expected=UnmodifiableObjectException.class)
	public void testNormalizeException() {
		IVector a = new Vector(true, true, 1, 2, 3);
		a.normalize();
	}
	
	@Test(expected=UnmodifiableObjectException.class)
	public void testScalarMulException() {
		IVector a = new Vector(true, true, 1, 2, 3);
		a.scalarMultiply(2);
	}
	
	@Test
	public void addTest() {
		IVector a = Vector.parseSimple("1 2 3");
		a.add(a);
		assertEquals(2, a.get(0), 1e-6);
		assertEquals(4, a.get(1), 1e-6);
		assertEquals(6, a.get(2), 1e-6);
	}
	
	@Test
	public void subTest() {
		IVector a = Vector.parseSimple("1 2 3");
		a.sub(a);
		assertEquals(0, a.get(0), 1e-6);
		assertEquals(0, a.get(1), 1e-6);
		assertEquals(0, a.get(2), 1e-6);
	}
	
	@Test
	public void scalarMulTest() {
		IVector a = Vector.parseSimple("1 2 3");
		a.scalarMultiply(2);
		assertEquals(2, a.get(0), 1e-6);
		assertEquals(4, a.get(1), 1e-6);
		assertEquals(6, a.get(2), 1e-6);
	}
	
	@Test
	public void normalizeTest() {
		IVector a = Vector.parseSimple("1 2 2");
		a.normalize();
		assertEquals(1./3, a.get(0), 1e-6);
		assertEquals(2./3, a.get(1), 1e-6);
		assertEquals(2./3, a.get(2), 1e-6);
	}
}
