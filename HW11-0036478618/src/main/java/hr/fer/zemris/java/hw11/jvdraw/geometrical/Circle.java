package hr.fer.zemris.java.hw11.jvdraw.geometrical;

import java.awt.Color;

public class Circle implements GeometricalObject {

	public static final GeometricalType TYPE = GeometricalType.CIRCLE;
	
	private int centerX;
	private int centerY;
	private double radius;
	private Color color;
	
	public Circle(int centerX, int centerY, double radius, Color color) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.color = color;
	}

	


	public int getCenterX() {
		return centerX;
	}




	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}




	public int getCenterY() {
		return centerY;
	}




	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}




	public double getRadius() {
		return radius;
	}




	public void setRadius(double radius) {
		this.radius = radius;
	}




	public Color getColor() {
		return color;
	}




	public void setColor(Color color) {
		this.color = color;
	}




	@Override
	public GeometricalType getType() {
		return TYPE;
	}


	@Override
	public GeometricalObject copy() {
		return new Circle(centerX, centerY, radius, color);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + centerX;
		result = prime * result + centerY;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		long temp;
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Circle other = (Circle) obj;
		if (centerX != other.centerX)
			return false;
		if (centerY != other.centerY)
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (Double.doubleToLongBits(radius) != Double
				.doubleToLongBits(other.radius))
			return false;
		return true;
	}




	@Override
	public String toString() {
		return "Circle (" + centerX + ", " + centerY + "), " + radius;
	}
	
	@Override
	public String toRGBString() {
		return "CIRCLE " + centerX + " " + centerY + " " + radius +
				" " + color.getRed() + " " + color.getGreen() + " " + color.getBlue();
	}
}
