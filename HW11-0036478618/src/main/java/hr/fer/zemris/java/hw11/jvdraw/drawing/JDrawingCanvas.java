package hr.fer.zemris.java.hw11.jvdraw.drawing;

import hr.fer.zemris.java.hw11.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.Circle;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.FilledCircle;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalType;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.Line;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.ObjectFactory;
import hr.fer.zemris.java.hw11.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw11.jvdraw.listeners.ColorType;
import hr.fer.zemris.java.hw11.jvdraw.listeners.DrawingModelListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class JDrawingCanvas extends JComponent implements DrawingModelListener, ColorChangeListener {
	
	private DrawingModel model;
	private boolean isDrawing;
	private int sx, sy, ex, ey;
	private GeometricalType type;
	private Color foreground;
	private Color background;
	
	public JDrawingCanvas(DrawingModel model, Color foreground, Color background) {
		this.model = model;
		this.foreground = foreground;
		this.background = background;
		model.addDrawingModelListener(this);
		MouseAdapter crtac = new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(type == null) {
					return;
				}
				if(!isDrawing) {
					sx = ex = e.getX();
					sy = ey = e.getY();
					isDrawing = true;
				}
				else {
					JDrawingCanvas.this.model.add(
							ObjectFactory.giveObject(type, sx, sy, ex, ey, 
									JDrawingCanvas.this.foreground, JDrawingCanvas.this.background));
					isDrawing = false;
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if(type == null) {
					return;
				}
				if(!isDrawing) {
					return;
				}
				ex = e.getX();
				ey = e.getY();
				repaint();
			}
		};
		addMouseListener(crtac);
		addMouseMotionListener(crtac);
	}
	
	public void setType(GeometricalType type) {
		this.type = type;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int size = model.getSize();
		for(int i = 0; i < size; ++i) {
			paintGeometricalObject(g, model.getObject(i));
		}
		
		if(isDrawing) {
			paintGeometricalObject(g, ObjectFactory.giveObject(type, sx, sy, ex, ey, foreground, background));
		}
	}
	
	private void paintGeometricalObject(Graphics g, GeometricalObject obj) {
		GeometricalType objType = obj.getType();
		if(objType == GeometricalType.LINE) {
			paintLine(g, (Line)obj);
		} else if(objType == GeometricalType.CIRCLE) {
			paintCircle(g, (Circle)obj);
		} else if(objType == GeometricalType.FCIRCLE) {
			paintFCircle(g, (FilledCircle)obj);
		} else {
			throw new IllegalArgumentException("Unknown object to be drawn.");
		}
	}
	
	public static void paintLine(Graphics g, Line line) {
		g.setColor(line.getColor());
		g.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
	}
	
	public static void paintCircle(Graphics g, Circle circle) {
		g.setColor(circle.getColor());
		g.drawOval((int)(circle.getCenterX()-circle.getRadius()), (int)(circle.getCenterY()-circle.getRadius()),
					(int)circle.getRadius()*2, (int)circle.getRadius()*2);
	}

	public static void paintFCircle(Graphics g, FilledCircle fcircle) {
		g.setColor(fcircle.getBackground());
		g.fillOval((int)(fcircle.getCenterX()-fcircle.getRadius()), (int)(fcircle.getCenterY()-fcircle.getRadius()),
				(int)fcircle.getRadius()*2, (int)fcircle.getRadius()*2);
		g.setColor(fcircle.getColor());
		g.drawOval((int)(fcircle.getCenterX()-fcircle.getRadius()), (int)(fcircle.getCenterY()-fcircle.getRadius()),
				(int)fcircle.getRadius()*2, (int)fcircle.getRadius()*2);
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
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
	}

	

	
	public int getMinX() {
		Posao posao = new Posao() {
			
			@Override
			int vratiVrijednost(int rez, FilledCircle fcircle) {
				int temp = (int)(fcircle.getCenterX() - fcircle.getRadius());
				return temp < rez ? temp : rez;
			}
			
			@Override
			int vratiVrijednost(int rez, Circle circle) {
				int temp = (int)(circle.getCenterX() - circle.getRadius());
				return temp < rez ? temp : rez;
			}
			
			@Override
			int vratiVrijednost(int rez, Line line) {
				int temp = line.getStartX() < line.getEndX() ? line.getStartX() : line.getEndX();
				return temp < rez ? temp : rez;
			}
			
			@Override
			int provjeriRet(int ret) {
				return ret < 0 ? 0 : ret;
			}
			
			@Override
			int pocetnaVrijednost() {
				return getWidth();
			}
		};
		return posao.doWork();
	}
	
	public int getMinY() {
		Posao posao = new Posao() {
			
			@Override
			int vratiVrijednost(int rez, FilledCircle fcircle) {
				int temp = (int)(fcircle.getCenterY() - fcircle.getRadius());
				return temp < rez ? temp : rez;
			}
			
			@Override
			int vratiVrijednost(int rez, Circle circle) {
				int temp = (int)(circle.getCenterY() - circle.getRadius());
				return temp < rez ? temp : rez;
			}
			
			@Override
			int vratiVrijednost(int rez, Line line) {
				int temp = line.getStartY() < line.getEndY() ? line.getStartY() : line.getEndY();
				return temp < rez ? temp : rez;
			}
			
			@Override
			int provjeriRet(int ret) {
				return ret < 0 ? 0 : ret;
			}
			
			@Override
			int pocetnaVrijednost() {
				return getHeight();
			}
		};
		return posao.doWork();
	}
	
	public int getMaxX() {
		Posao posao = new Posao() {
			
			@Override
			int vratiVrijednost(int rez, FilledCircle fcircle) {
				int temp = (int)(fcircle.getCenterX() + fcircle.getRadius());
				return temp > rez ? temp : rez;
			}
			
			@Override
			int vratiVrijednost(int rez, Circle circle) {
				int temp = (int)(circle.getCenterX() + circle.getRadius());
				return temp > rez ? temp : rez;
			}
			
			@Override
			int vratiVrijednost(int rez, Line line) {
				int temp = line.getStartX() > line.getEndX() ? line.getStartX() : line.getEndX();
				return temp > rez ? temp : rez;
			}
			
			@Override
			int provjeriRet(int ret) {
				return ret > getWidth() ? getWidth() : ret;
			}
			
			@Override
			int pocetnaVrijednost() {
				return 0;
			}
		};
		return posao.doWork();
	}
	
	
	public int getMaxY() {
		Posao posao = new Posao() {
			
			@Override
			int vratiVrijednost(int rez, FilledCircle fcircle) {
				int temp = (int)(fcircle.getCenterY() + fcircle.getRadius());
				return temp > rez ? temp : rez;
			}
			
			@Override
			int vratiVrijednost(int rez, Circle circle) {
				int temp = (int)(circle.getCenterY() + circle.getRadius());
				return temp > rez ? temp : rez;
			}
			
			@Override
			int vratiVrijednost(int rez, Line line) {
				int temp = line.getStartY() > line.getEndY() ? line.getStartY() : line.getEndY();
				return temp > rez ? temp : rez;
			}
			
			@Override
			int provjeriRet(int ret) {
				return ret > getHeight() ? getHeight() : ret;
			}
			
			@Override
			int pocetnaVrijednost() {
				return 0;
			}
		};
		return posao.doWork();
	}

	private abstract class Posao {
		
		public int doWork() {
			int ret;
			ret = pocetnaVrijednost();
			int size = model.getSize();
			
			for(int i = 0; i < size; ++i) {
				GeometricalObject obj = model.getObject(i);
				if(obj instanceof Line) {
					ret = vratiVrijednost(ret, (Line) obj);
				} else if(obj instanceof Circle) {
					ret = vratiVrijednost(ret,(Circle) obj);
				} else if(obj instanceof FilledCircle) {
					ret = vratiVrijednost(ret, (FilledCircle)obj);
				}
			}
			return provjeriRet(ret);
		}
		
		abstract int provjeriRet(int ret);
		abstract int pocetnaVrijednost();
		abstract int vratiVrijednost(int rez, Line line);
		abstract int vratiVrijednost(int rez, Circle circle);
		abstract int vratiVrijednost(int rez, FilledCircle fcircle);
	}

}
