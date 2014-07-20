package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

public class CalcLayout implements LayoutManager2 {

	private final static int screenWidth = 5;
	private final static int rows = 5;
	private final static int cols = 7;
    private int minWidth = -1, minHeight = -1;
    private int preferredWidth = -1, preferredHeight = -1;
    private int maxWidth = -1, maxHeight = -1;
	private int gap;
	private Map<RCPosition, Component> components;
 	
	public CalcLayout() {
		this(0);
	}	
	
	public CalcLayout(int space) {
		if(space < 0) {
			throw new IllegalArgumentException("Spacing must be non negative int.");
		}
		this.gap = space;
		this.components = new HashMap<>();
	}


	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		setSizes(parent);
		return new Dimension(preferredWidth, preferredHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		setSizes(parent);
		return new Dimension(minWidth, minHeight);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int width = parent.getWidth();
		int height = parent.getHeight();
		int xPomak = Math.max(insets.left, insets.right);
		int yPomak = Math.max(insets.top, insets.bottom);
		int compWidth = (width - (cols-1) * gap - xPomak * 2) / cols;
		int compHeight = (height - (rows-1) * gap - yPomak * 2) / rows;
		
		for(RCPosition position : components.keySet()) {
			Component comp = components.get(position);
			if(comp.isVisible()) {
				if(position.getRow() == 1 && position.getColumn() == 1) {
					comp.setBounds(xPomak, yPomak, compWidth * screenWidth + gap*(screenWidth-1), compHeight);
				} else {
					comp.setBounds(xPomak + (position.getColumn()-1) * (gap + compWidth), 
							yPomak + (position.getRow()-1) * (gap + compHeight), compWidth, compHeight);
				}
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition pos;
		int row, col;
		if(constraints instanceof String) {
			pos = RCPosition.parseString((String) constraints);
		} else if (constraints instanceof RCPosition) {
			pos = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException("Components constraints must be instance of String or RCPosition");
		}
		
		row = pos.getRow();
		col = pos.getColumn();
		if(row < 1 || row > rows || col < 1 || col > cols) {
			throw new IndexOutOfBoundsException("Position of object must be inside of CalcLayout bounds: ["
					+ 1 + ", " + rows + "], [" + 1 + ", " + cols + "]");
		}
		if(components.containsKey(pos)) {
			throw new IllegalArgumentException("There is a component already on position " + row + ", " + col);
		}
		components.put(pos, comp);
	}
	
	
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(maxHeight, maxWidth);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return (float)0.5;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return (float)0.5;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
	
	private void setSizes(Container parent) {
		Insets insets = parent.getInsets();
		int xPomak = Math.max(insets.left, insets.right)*2;
		int yPomak = Math.max(insets.top, insets.bottom)*2;
		for(Component component : components.values()) {
			Dimension min = component.getMinimumSize();
			Dimension pref = component.getPreferredSize();
			Dimension max = component.getMaximumSize();
			if(min != null) {
				if(min.getHeight() * rows + gap * (rows - 1) > minHeight) {
					minHeight = (int) min.getHeight() * rows + gap * (rows - 1);
				}
				if(min.getWidth() * cols + gap * (cols - 1) > minWidth) {
					minWidth = (int) min.getWidth() * cols + gap * (cols - 1);
				}
			}
			if(pref != null) {
				if(pref.getHeight() * rows + gap * (rows - 1) > preferredHeight) {
					preferredHeight = (int) pref.getHeight() * rows + gap * (rows - 1);
				}
				if(min.getWidth() * cols + gap * (cols - 1) > preferredWidth) {
					preferredWidth = (int) pref.getWidth() * cols + gap * (cols - 1);
				}
			}
			if(max != null) {
				if(max.getHeight() * rows + gap * (rows - 1) > maxHeight) {
					maxHeight = (int) max.getHeight() * rows + gap * (rows - 1);
				}
				if(max.getWidth() * cols + gap * (cols - 1) > maxWidth) {
					maxWidth = (int) max.getWidth() * cols + gap * (cols - 1);
				}
			}
		}
		minHeight += yPomak;
		minWidth += xPomak;
		preferredHeight += yPomak;
		preferredWidth += xPomak;
		maxHeight += yPomak;
		maxWidth += xPomak;
	}

}
