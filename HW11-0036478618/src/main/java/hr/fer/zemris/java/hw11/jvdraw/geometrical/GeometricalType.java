package hr.fer.zemris.java.hw11.jvdraw.geometrical;

public enum GeometricalType {
	
	LINE,
	CIRCLE,
	FCIRCLE;

	public static GeometricalType parse(String type) {
		if("line".equalsIgnoreCase(type)) {
			return LINE;
		}
		if("circle".equalsIgnoreCase(type)) {
			return CIRCLE;
		}
		if("fcircle".equalsIgnoreCase(type)) {
			return FCIRCLE;
		}
		return null;
	}
	
}
