package hr.fer.zemris.java.hw11.jvdraw.geometrical;

public interface GeometricalObject {

	public GeometricalType getType();
	public GeometricalObject copy();
	public String toRGBString();
}
