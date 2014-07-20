package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;

public class Prog4 {

	public static void main(String[] args) {
		IMatrix m1 = Matrix.parseSimple("1 5 3 | 0 0 8 | 1 1 1");
		Vector v1 = Vector.parseSimple("3 4 1");
		IMatrix rez = m1.nInvert().nMultiply(v1.toColumnMatrix(true));
		System.out.println("Rjesenje sustava je:");
		System.out.println(rez);
	}
	
}
