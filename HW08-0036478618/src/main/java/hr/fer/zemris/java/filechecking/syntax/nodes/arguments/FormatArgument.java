package hr.fer.zemris.java.filechecking.syntax.nodes.arguments;

/**
 * Format argument. Sadrži ekstenziju datoteke (format).
 * @author Ivan Krpelnik
 *
 */
public class FormatArgument extends Argument {

	private String format;
	
	public FormatArgument(String format) {
		this.format = format;
	}
	
	public String getFormat() {
		return format;
	}
	
	@Override
	public String toString() {
		return "Format: " + format;
	}
}
