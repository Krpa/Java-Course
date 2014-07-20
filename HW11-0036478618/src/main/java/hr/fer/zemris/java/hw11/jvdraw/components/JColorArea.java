package hr.fer.zemris.java.hw11.jvdraw.components;

import hr.fer.zemris.java.hw11.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw11.jvdraw.listeners.ColorType;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JColorArea extends JPanel implements IColorProvider {

	private static final int DIMX = 15;
	private static final int DIMY = 15;
	private Color selectedColor;
	private List<ColorChangeListener> listeners;
	private String name;
	private ColorType type;
	
	public JColorArea(Color initialColor, String name, ColorType type) {
		selectedColor = initialColor;
		listeners = new ArrayList<>();
		this.name = name;
		this.type = type;
		setOpaque(true);
		setBackground(selectedColor);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color temp = JColorChooser.showDialog(JColorArea.this, "Choose " + JColorArea.this.name + " color:", selectedColor);
				if(temp != null) {
					Color oldColor = selectedColor;
					selectedColor = temp;
					JColorArea.this.setBackground(selectedColor);
					JColorArea.this.fire(oldColor, selectedColor);
				}
			}
		});
	}
	
	public void addColorChangeListener(ColorChangeListener l) {
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}
	
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}
	
	private void fire(Color oldColor, Color newColor) {
		for(ColorChangeListener l : listeners) {
			l.newColorSelected(this, oldColor, newColor);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DIMX, DIMY);
	}
	
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(DIMX, DIMY);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public ColorType getType() {
		return type;
	}
}
