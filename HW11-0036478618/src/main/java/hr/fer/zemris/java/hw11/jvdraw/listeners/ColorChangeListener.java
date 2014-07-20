package hr.fer.zemris.java.hw11.jvdraw.listeners;

import hr.fer.zemris.java.hw11.jvdraw.components.IColorProvider;

import java.awt.Color;

public interface ColorChangeListener {
	
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
