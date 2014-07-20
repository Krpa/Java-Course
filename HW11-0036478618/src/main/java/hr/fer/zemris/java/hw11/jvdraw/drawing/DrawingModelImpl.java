package hr.fer.zemris.java.hw11.jvdraw.drawing;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.listeners.DrawingModelListener;

public class DrawingModelImpl implements DrawingModel {

	private List<GeometricalObject> objects;
	private List<DrawingModelListener> listeners;
	
	public DrawingModelImpl() {
		objects = new ArrayList<>();
		listeners = new ArrayList<>();
	}
	
	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if(index >= getSize()) {
			throw new IndexOutOfBoundsException();
		}
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		fireObjectsAdded(getSize()-1);
	}
	
	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		if(index != -1) {
			objects.remove(object);
			fireObjectsRemoved(index);
		}
	}
	
	public void objectChanged(int index) {
		fireObjectsChanged(index);
	}
	
	private void fireObjectsChanged(int index) {
		for(DrawingModelListener l : listeners) {
			l.objectsChanged(this, index, index);
		}
	}
	
	private void fireObjectsAdded(int index) {
		for(DrawingModelListener l : listeners) {
			l.objectsAdded(this, index, index);
		}
	}
	
	private void fireObjectsRemoved(int index) {
		for(DrawingModelListener l : listeners) {
			l.objectsRemoved(this, index, index);
		}
	}
	
	private void fireObjectsRemoved(int start, int end) {
		for(DrawingModelListener l : listeners) {
			l.objectsRemoved(this, start, end);
		}
	}
	
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void clear() {
		int size = getSize();
		objects.clear();
		fireObjectsRemoved(0, size);
	}

	@Override
	public void objectChanged(GeometricalObject object) {
		fireObjectsChanged(objects.indexOf(object));
	}

}
