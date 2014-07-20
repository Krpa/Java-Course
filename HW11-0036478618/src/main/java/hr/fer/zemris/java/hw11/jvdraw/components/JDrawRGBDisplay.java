package hr.fer.zemris.java.hw11.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw11.jvdraw.listeners.ColorType;

@SuppressWarnings("serial")
public class JDrawRGBDisplay extends JLabel implements ColorChangeListener {

	private Color foreground;
	private Color background;
	
	public JDrawRGBDisplay(Color foreground, Color background	) {
		super(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).", 
				foreground.getRed(),
				foreground.getGreen(), 
				foreground.getBlue(),
				background.getRed(),
				background.getGreen(), 
				background.getBlue()));
		this.foreground = foreground;
		this.background = background;
	}
	
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		if(source.getType() == ColorType.BACKGROUND) {
			background = newColor;
		} else if(source.getType() == ColorType.FOREGROUND) {
			foreground = newColor;
		} else {
			throw new IllegalArgumentException();
		}
		refreshText();
	}

	private void refreshText() {
		setText(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).", 
								foreground.getRed(),
								foreground.getGreen(), 
								foreground.getBlue(),
								background.getRed(),
								background.getGreen(), 
								background.getBlue()
								));
	}

	
}
