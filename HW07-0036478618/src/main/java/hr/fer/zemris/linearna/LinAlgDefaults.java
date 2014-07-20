package hr.fer.zemris.linearna;

/**
 * Razred tvornica koji može stvoriti instance razreda {@link Vector} i {@link Matrix}.
 * @author Ivan Krpelnik
 *
 */
public class LinAlgDefaults {

	public static IVector defaultVector(int dimension) {
		return new Vector(new double[dimension]);
	}
	
	public static IMatrix defaultMatrix(int rows, int cols) {
		return new Matrix(rows, cols);
	}
}
