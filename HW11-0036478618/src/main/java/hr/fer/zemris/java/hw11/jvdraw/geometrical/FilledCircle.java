package hr.fer.zemris.java.hw11.jvdraw.geometrical;

import java.awt.Color;

public class FilledCircle extends Circle implements GeometricalObject {

	public static final GeometricalType TYPE = GeometricalType.FCIRCLE;
	
	private Color background;
	
	public FilledCircle(int centerX, int centerY, double radius, Color color, Color background) {
		super(centerX, centerY, radius, color);
		this.background = background;	
	}
	
	public Color getBackground() {
		return background;
	}
	
	public void setBackground(Color background) {
		this.background = background;
	}
	
	@Override
	public GeometricalObject copy() {
		return new FilledCircle(getCenterX(), getCenterY(), getRadius(), getColor(), getBackground());
	}
	
	@Override
	public GeometricalType getType() {
		return TYPE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((background == null) ? 0 : background.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FilledCircle other = (FilledCircle) obj;
		if (background == null) {
			if (other.background != null)
				return false;
		} else if (!background.equals(other.background))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("FCircle (%d, %d), %.1f", getCenterX(), getCenterY(), getRadius());
	}
	
	@Override
	public String toRGBString() {
		return "FCIRCLE " + getCenterX() + " " + getCenterY() + " " + getRadius() +
				" " + getColor().getRed() + " " + getColor().getGreen() + " " + getColor().getBlue() +
				" " + background.getRed() + " " + background.getGreen() + " " + background.getBlue();
	}
}
