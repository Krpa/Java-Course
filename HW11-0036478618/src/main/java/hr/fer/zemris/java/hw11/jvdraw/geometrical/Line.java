package hr.fer.zemris.java.hw11.jvdraw.geometrical;

import java.awt.Color;

public class Line implements GeometricalObject {

	public final static GeometricalType TYPE = GeometricalType.LINE;

	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private Color color;
	
	public Line(int startX, int startY, int endX, int endY, Color color) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.color = color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setPosition(int startX, int startY, int endX, int endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		
	}
	
	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	
	@Override
	public GeometricalObject copy() {
		return new Line(startX, startY, endX, endY, color);
	}
	
	@Override
	public GeometricalType getType() {
		return TYPE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + endX;
		result = prime * result + endY;
		result = prime * result + startX;
		result = prime * result + startY;
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
		Line other = (Line) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (endX != other.endX)
			return false;
		if (endY != other.endY)
			return false;
		if (startX != other.startX)
			return false;
		if (startY != other.startY)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("Line (%d, %d), (%d, %d)", startX, startY, endX, endY);
	}

	@Override
	public String toRGBString() {
		return String.format("LINE %d %d %d %d %d %d %d",
								startX, startY, endX, endY, color.getRed(), color.getGreen(), color.getBlue());
	}
}
