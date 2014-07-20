package hr.fer.zemris.java.gui.layouts;

public class RCPosition {

	private int row;
	private int column;
	
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public static RCPosition parseString(String string) {
		if(string == null) {
			throw new IllegalArgumentException("String for parsing should not be null.");
		}
		String toParse = string.replaceAll("\\s+", "");
		String[] components = toParse.split(",");
		if(components.length != 2) {
			throw new IllegalArgumentException("String must contain two numbers seperated by \',\'.");
		}
		return new RCPosition(parseNumber(components[0]), parseNumber(components[1]));
	}
	
	private static int parseNumber(String string) {
		try {
			return Integer.parseInt(string);
		} catch(NumberFormatException e) {
			throw new NumberFormatException("RCPosition can contain only integers.");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	
	
	
}
