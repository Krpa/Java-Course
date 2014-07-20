package hr.fer.zemris.java.hw11.jvdraw.drawing;

import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.listeners.DrawingModelListener;

public interface DrawingModel {

	public int getSize();
	public GeometricalObject getObject(int index);
	public void add(GeometricalObject object);
	public void remove(GeometricalObject object);
	public void objectChanged(GeometricalObject object);
	public void addDrawingModelListener(DrawingModelListener l);
	public void removeDrawingModelListener(DrawingModelListener l);
	public void clear();
}
