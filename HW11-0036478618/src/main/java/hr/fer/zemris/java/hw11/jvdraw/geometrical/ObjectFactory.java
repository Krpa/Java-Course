package hr.fer.zemris.java.hw11.jvdraw.geometrical;

import java.awt.Color;

public class ObjectFactory {
	
	private ObjectFactory() {
		
	}
	
	public static GeometricalObject giveObject(GeometricalType type, int sx, int sy, int ex, int ey, 
			Color foreground, Color background) {
		if(type == GeometricalType.LINE) {
			return new Line(sx, sy, ex, ey, foreground);
		} else if(type == GeometricalType.CIRCLE) {
			return new Circle(sx, sy,
							Math.sqrt(Math.pow(sx - ex, 2) + Math.pow(sy - ey, 2)), foreground);
		} else if(type == GeometricalType.FCIRCLE) {
			return new FilledCircle(sx, sy,
					Math.sqrt(Math.pow(sx - ex, 2) + Math.pow(sy - ey, 2)), foreground, background);
		} 
		return null;
	}
	
	
	public static GeometricalObject parse(String...params) {
		try {
			
			GeometricalType type = GeometricalType.parse(params[0]);
			int sx = Integer.parseInt(params[1]);
			int sy = Integer.parseInt(params[2]);
			int ex, ey;
			Color foreground, background = null;
			if(type == GeometricalType.LINE) {
				ex = Integer.parseInt(params[3]);
				ey = Integer.parseInt(params[4]);
				foreground = new Color(
						Integer.parseInt(params[5]),
						Integer.parseInt(params[6]),
						Integer.parseInt(params[7]));
			} else if(type == GeometricalType.CIRCLE || type == GeometricalType.FCIRCLE) {
				double r = Double.parseDouble(params[3]);
				ex = (int)(sx + r);
				ey = sy;
				foreground = new Color(
						Integer.parseInt(params[4]),
						Integer.parseInt(params[5]),
						Integer.parseInt(params[6]));
			} else {
				return null;
			}
			if(type == GeometricalType.FCIRCLE) {
				background = new Color(
						Integer.parseInt(params[7]),
						Integer.parseInt(params[8]),
						Integer.parseInt(params[9]));
			}
			return giveObject(type, sx, sy, ex, ey, foreground, background);
		} catch(RuntimeException e) {
			return null;
		}
	}
}
