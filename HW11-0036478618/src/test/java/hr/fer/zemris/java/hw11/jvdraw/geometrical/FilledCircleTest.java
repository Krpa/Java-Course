package hr.fer.zemris.java.hw11.jvdraw.geometrical;

import java.awt.Color;

import org.junit.Test;
import static org.junit.Assert.*;

public class FilledCircleTest {
	
	@Test
	public void testFilledCircle() {
		GeometricalObject fc = new FilledCircle(5, 5, 13.52, Color.BLACK, Color.WHITE);
		assertEquals(fc.getType(), GeometricalType.FCIRCLE);
	}
	
}
