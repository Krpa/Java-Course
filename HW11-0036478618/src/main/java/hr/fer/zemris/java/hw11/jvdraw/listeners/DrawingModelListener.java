package hr.fer.zemris.java.hw11.jvdraw.listeners;

import hr.fer.zemris.java.hw11.jvdraw.drawing.DrawingModel;

public interface DrawingModelListener {

	 public void objectsAdded(DrawingModel source, int index0, int index1);
	 public void objectsRemoved(DrawingModel source, int index0, int index1);
	 public void objectsChanged(DrawingModel source, int index0, int index1);
	 
}
