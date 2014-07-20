package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.servlets.SquaresServlet.Par;

import org.junit.*;
import static org.junit.Assert.*;

public class ParTest {

	@Test
	public void test1() {
		Par par = new Par(5, 25);
		assertEquals(5, par.getBroj());
		assertEquals(25, par.getVrijednost());
	}
}
