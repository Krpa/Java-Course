package hr.fer.zemris.java.hw11.jvdraw;

import hr.fer.zemris.java.hw11.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw11.jvdraw.components.JDrawRGBDisplay;
import hr.fer.zemris.java.hw11.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw11.jvdraw.drawing.DrawingModelImpl;
import hr.fer.zemris.java.hw11.jvdraw.drawing.DrawingObjectListModel;
import hr.fer.zemris.java.hw11.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.Circle;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.FilledCircle;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalType;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.Line;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.ObjectFactory;
import hr.fer.zemris.java.hw11.jvdraw.listeners.ColorType;
import hr.fer.zemris.java.hw11.jvdraw.listeners.DrawingModelListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;


/**
 * Jednostavan "paint" program.
 * Zna crtati linije, kruznice i krugove s okvirima. Također je moguće biranje boja.
 * Nudi opcije za spremanje i otvaranje datoteke (zna otvoriti datoteke s ekstenzijom .jvd). Prilikom 
 * spremanja datoteke program ne dodjeljuje sam ekstenziju.
 * Ponuđena je i opcija za exportanje slike u *.png, *.gif ili *.jpg. Ako se ne preda ekstenzija 
 * prilikom exportanja, onda se stavlja ekstenzija .png.
 * Dvoklikom na element u listi na desnoj strani programa moguće je promjeniti neki element.
 * Ako je u vrijeme dvoklika stisnuta alt tipka, moguće je izbrisati element.
 * @author Ivan Krpelnik
 *
 */
@SuppressWarnings("serial")
public class JVDraw extends JFrame {

	private static final Color FOREGROUND = Color.RED;
	private static final Color BACKGROUND = Color.BLUE;
	
	private JDrawingCanvas canvas;
	private DrawingModel model;
	private Path openedFilePath;
	private boolean documentChanged = false;
	
	public JVDraw() {
		setSize(600, 600);
		setTitle("JVDraw");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		initGUI();
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
			}
		});
	}
	
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		model = new DrawingModelImpl();
		canvas = new JDrawingCanvas(model, FOREGROUND, BACKGROUND);
		model.addDrawingModelListener(new DrawingModelListener() {
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				documentChanged = true;
			}
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				documentChanged = true;
			}
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				documentChanged = true;
			}
		});
		JList<GeometricalObject> listaObjekata = new JList<>(new DrawingObjectListModel(model));
		JScrollPane p1 = new JScrollPane(listaObjekata);
		listaObjekata.addMouseListener(listListener);
		p1.setPreferredSize(new Dimension(150, 100));
		getContentPane().add(p1, BorderLayout.EAST);
		getContentPane().add(canvas, BorderLayout.CENTER);
		createActions();
		createToolbars();
		createMenus();
	}

	private void createActions() {
		newAction.putValue(Action.NAME, "New");
		newAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newAction.putValue(Action.SHORT_DESCRIPTION, "Otvaranje nove datoteke."); 
		
		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Otvaranje datoteke s diska."); 
		
		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S")); 
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S); 
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Spremanje datoteke na disk."); 
		
		saveAsAction.putValue(Action.NAME, "Save As"); 
		saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Spremanje datoteke na disk."); 
		
		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exportAction.putValue(Action.SHORT_DESCRIPTION, "Exportanje datoteke na disk."); 
		
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Izađi iz aplikacije."); 
		
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem(newAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.add(new JMenuItem(exportAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}
	
	private void createToolbars() {
		JToolBar toolbar = new JToolBar("Alati");
		toolbar.setFloatable(true);
		JColorArea foreground = new JColorArea(FOREGROUND, "Foreground", ColorType.FOREGROUND);
		toolbar.add(foreground);
		toolbar.addSeparator();
		JColorArea background = new JColorArea(BACKGROUND, "Background", ColorType.BACKGROUND);
		toolbar.add(background);
		getContentPane().add(toolbar, BorderLayout.PAGE_START);
		JDrawRGBDisplay display = new JDrawRGBDisplay(foreground.getCurrentColor(), background.getCurrentColor());
		foreground.addColorChangeListener(display);
		background.addColorChangeListener(display);
		foreground.addColorChangeListener(canvas);
		background.addColorChangeListener(canvas);
		getContentPane().add(display, BorderLayout.PAGE_END);
		
		toolbar.addSeparator();
		final JToggleButton line = new JToggleButton("Line");
		final JToggleButton circle = new JToggleButton("Circle");
		final JToggleButton fcircle = new JToggleButton("Filled circle");
		ButtonGroup selectType = new ButtonGroup();
		selectType.add(line);
		selectType.add(circle);
		selectType.add(fcircle);
		line.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ButtonModel buttonModel = ((AbstractButton)e.getSource()).getModel();
				if(buttonModel.isPressed()) {
					canvas.setType(GeometricalType.LINE);
				}
			}
		});
		circle.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ButtonModel buttonModel = ((AbstractButton)e.getSource()).getModel();
				if(buttonModel.isPressed()) {
					canvas.setType(GeometricalType.CIRCLE);
				}
			}
		});
		fcircle.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ButtonModel buttonModel = ((AbstractButton)e.getSource()).getModel();
				if(buttonModel.isPressed()) {
					canvas.setType(GeometricalType.FCIRCLE);
				}
			}
		});
		toolbar.add(line);
		toolbar.add(circle);
		toolbar.add(fcircle);
	}

	private Action newAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(documentChanged) {
				int rez = JOptionPane.showConfirmDialog(
							JVDraw.this,
							"Želite li spremiti trenutni dokument?",
							"Exit",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
				
				if(rez == JOptionPane.CANCEL_OPTION) {
					return;
				}
				
				if(rez == JOptionPane.YES_OPTION) {
					saveAsAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
				}
			}

			model.clear();
			documentChanged = false;
			openedFilePath = null;
		}
	};
	
	private Action openDocumentAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File filename = fc.getSelectedFile();
			Path filepath = filename.toPath();
			
			if(!Files.isReadable(filepath)) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"Datoteka " + filename.getAbsolutePath()+" ne postoji!",
						"Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!filename.getName().endsWith(".jvd")) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"Datoteka " + filename.getAbsolutePath()+" nema ekstenziju .jvd!",
						"Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			

			String redak;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(filename));
				redak = reader.readLine();
				JVDraw.this.model.clear();
				while(redak != null) {
					String[] params = redak.trim().split(" ");
					GeometricalObject obj = ObjectFactory.parse(params);
					
					if(obj == null) {
						JOptionPane.showMessageDialog(
								JVDraw.this,
								"Datoteka " + filename.getAbsolutePath()+" sadrži pogreške!",
								"Pogreška",
								JOptionPane.ERROR_MESSAGE);
						JVDraw.this.model.clear();
						reader.close();
						return;
					}
					model.add(obj);
					redak = reader.readLine();
				}
				openedFilePath = filepath;
				documentChanged = false;
				reader.close();
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"Nije uspijelo prevađanje datoteke " + filename.getAbsolutePath(),
						"Pogreška",
						JOptionPane.ERROR_MESSAGE);
				JVDraw.this.model.clear();
				return;
			}
			
		}
	};
	
	private Action saveDocumentAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(openedFilePath == null) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Save");
				if(fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(
							JVDraw.this,
							"Ništa nije snimljeno.",
							"Upozorenje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				openedFilePath = fc.getSelectedFile().toPath();
			}
			
			try {
				int size = model.getSize();
				BufferedWriter writer = new BufferedWriter(new FileWriter(openedFilePath.toFile()));
				for(int i = 0; i < size; ++i) {
					writer.append(model.getObject(i).toRGBString());
					writer.newLine();
					writer.flush();
				}
				documentChanged = false;
				writer.close();
			} catch(IOException ex) {
				JOptionPane.showMessageDialog(
							JVDraw.this,
							"Pogreška prilikom zapisivanja datoteke " + openedFilePath.toFile().getAbsoluteFile()+"!",
							"Pogreška",
							JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(
					JVDraw.this, 
					"Datoteka je snimljena.", 
					"Informacija", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	private Action saveAsAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			openedFilePath = null;
			saveDocumentAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
		}
	};
	
	private Action exitAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(documentChanged) {
				int rez = JOptionPane.showConfirmDialog(
							JVDraw.this,
							"Želite li spremiti trenutni dokument?",
							"Exit",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
				
				if(rez == JOptionPane.CANCEL_OPTION) {
					return;
				}
				
				if(rez == JOptionPane.YES_OPTION) {
					saveAsAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
				}
				
				JVDraw.this.dispose();
				return;
			}
			if(!documentChanged) {
				int rez = JOptionPane.showConfirmDialog(
						JVDraw.this,
						"Zatvori program?",
						"Exit",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(rez == JOptionPane.YES_OPTION) {
					JVDraw.this.dispose();
				}
			}
		}
	};
	
	private Action exportAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Export");
			fc.setFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "*.png, *.gif, *.jpg";
				}
				@Override
				public boolean accept(File f) {
					String name = f.getName();
					return name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".jpg");
				}
			});
			
			if(fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"Ništa nije snimljeno.",
						"Upozorenje",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			String filename = fc.getSelectedFile().getAbsolutePath();
			if(!filename.endsWith(".png") && !filename.endsWith(".gif") && !filename.endsWith(".jpg")) {
				filename += ".png";
			}
			
			File file = new File(filename);
			
			int minX = canvas.getMinX(); 
			int minY = canvas.getMinY();
			int maxX = canvas.getMaxX();
			int maxY = canvas.getMaxY();
			
			BufferedImage image = new BufferedImage(Math.abs(maxX - minX),Math.abs(maxY - minY),
					BufferedImage.TYPE_3BYTE_BGR);
			
			Graphics2D g = image.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, Math.abs(maxX - minX), Math.abs(maxY - minY));
			int size = model.getSize();
			for(int i = 0; i < size; ++i) {
				GeometricalObject obj = model.getObject(i);
				
				if(obj instanceof Line) {
					Line line = (Line) obj.copy();
					line.setStartX(line.getStartX()-minX);
					line.setStartY(line.getStartY()-minY);
					line.setEndX(line.getEndX()-minX);
					line.setEndY(line.getEndY()-minY);
					JDrawingCanvas.paintLine(g, line);
				}
				if(obj instanceof Circle) {
					Circle circle = (Circle) obj.copy();
					circle.setCenterX(circle.getCenterX() - minX);
					circle.setCenterY(circle.getCenterY() - minY);
					JDrawingCanvas.paintCircle(g, circle);
				}
				
				if(obj instanceof FilledCircle) {
					FilledCircle circle = (FilledCircle) obj.copy();
					circle.setCenterX(circle.getCenterX() - minX);
					circle.setCenterY(circle.getCenterY() - minY);
					JDrawingCanvas.paintFCircle(g, circle);
				}
			}
			
			g.dispose();
			try {
				ImageIO.write(image, filename.substring(filename.lastIndexOf('.')+1), file);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"Nije uspijelo exportanje.",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(
					JVDraw.this,
					"Slika je exportana.",
					"Informacija",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	};
	
	private MouseListener listListener = new MouseAdapter() {
		
		public void mouseClicked(java.awt.event.MouseEvent e) {
			if(e.getClickCount() != 2) {
				return;
			}
			

			@SuppressWarnings("unchecked")
			JList<GeometricalObject> lista = (JList<GeometricalObject>) e.getSource();
			int index = lista.getSelectedIndex();
			
			if(index == -1) {
				return;
			}
			
			GeometricalObject obj = model.getObject(index);
			
			if(e.isAltDown()) {
				int rez = JOptionPane.showConfirmDialog(
						JVDraw.this,
						"Izbriši " + obj.toString() + " ?",
						"Izbriši",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(rez == JOptionPane.YES_OPTION) {
					model.remove(obj);
				}
			} else {
				if(obj.getType() == GeometricalType.LINE) {
					showLineDialog((Line)obj);
				}
				if(obj.getType() == GeometricalType.CIRCLE) {
					showCircleDialog((Circle)obj);
				}
				if(obj.getType() == GeometricalType.FCIRCLE) {
					showFCircleDialog((FilledCircle)obj);
				}
			}
		};
	};
	
	
	private void showCircleDialog(Circle circle) {
		JPanel vanjski = new JPanel(new BorderLayout());
		JPanel lijevi = new JPanel(new GridLayout(4, 1));
		JPanel desni = new JPanel(new GridLayout(4, 1));
		
		vanjski.add(lijevi, BorderLayout.LINE_START);
		vanjski.add(desni, BorderLayout.CENTER);
		
		lijevi.add(new JLabel("centerX: "));
		lijevi.add(new JLabel("centerY: "));
		lijevi.add(new JLabel("Radius: "));
		lijevi.add(new JLabel("Color: "));
		
		JTextField centerX = new JTextField();
		centerX.setText(String.valueOf(circle.getCenterX()));
		JTextField centerY = new JTextField();
		centerY.setText(String.valueOf(circle.getCenterY()));
		JTextField radius = new JTextField();
		radius.setText(String.valueOf(circle.getRadius()));
		JColorArea color = new JColorArea(circle.getColor(), "color", ColorType.FOREGROUND);
		desni.add(centerX);
		desni.add(centerY);
		desni.add(radius);
		desni.add(color);
		
		while(true) {
			int rez = JOptionPane.showConfirmDialog(
							JVDraw.this, vanjski, "Unesite podatke: ",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(rez == JOptionPane.CANCEL_OPTION) {
				break;
			}
			
			if(rez == JOptionPane.OK_OPTION) {
				int cx, cy;
				double r;
				Color newColor;
				try {
					cx = Integer.parseInt(centerX.getText());
					cy = Integer.parseInt(centerY.getText());
					r = Double.parseDouble(radius.getText());
					newColor = color.getCurrentColor();
					circle.setCenterX(cx);
					circle.setCenterY(cy);
					circle.setRadius(r);
					circle.setColor(newColor);
					model.objectChanged(circle);
					break;
				} catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(
							JVDraw.this,
							"Unešeni su krivi podaci.",
							"Pogreška",
							JOptionPane.ERROR_MESSAGE);
					continue;
				}
			}
		}
	}
	
	private void showLineDialog(Line line) {
		JPanel vanjski = new JPanel(new BorderLayout());
		JPanel lijevi = new JPanel(new GridLayout(5, 1));
		JPanel desni = new JPanel(new GridLayout(5, 1));
		
		vanjski.add(lijevi, BorderLayout.LINE_START);
		vanjski.add(desni, BorderLayout.CENTER);
		
		lijevi.add(new JLabel("startX: "));
		lijevi.add(new JLabel("startY: "));
		lijevi.add(new JLabel("endX: "));
		lijevi.add(new JLabel("endY: "));
		lijevi.add(new JLabel("Color: "));
		
		JTextField startX = new JTextField();
		startX.setText(String.valueOf(line.getStartX()));
		JTextField startY = new JTextField();
		startY.setText(String.valueOf(line.getStartY()));
		JTextField endX = new JTextField();
		endX.setText(String.valueOf(line.getEndX()));
		JTextField endY = new JTextField();	
		endY.setText(String.valueOf(line.getEndY()));
		JColorArea color = new JColorArea(line.getColor(), "color", ColorType.FOREGROUND);
		desni.add(startX);
		desni.add(startY);
		desni.add(endX);
		desni.add(endY);
		desni.add(color);
		
		
		while(true) {
			int rez = JOptionPane.showConfirmDialog(
							JVDraw.this, vanjski, "Unesite podatke: ",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(rez == JOptionPane.CANCEL_OPTION) {
				break;
			}
			
			if(rez == JOptionPane.OK_OPTION) {
				int sx, sy, ex, ey;
				Color newColor;
				try {
					sx = Integer.parseInt(startX.getText());
					sy = Integer.parseInt(startY.getText());
					ex = Integer.parseInt(endX.getText());
					ey = Integer.parseInt(endY.getText());
					newColor = color.getCurrentColor();
					line.setPosition(sx, sy, ex, ey);
					line.setColor(newColor);
					model.objectChanged(line);
					break;
				} catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(
							JVDraw.this,
							"Unešeni su krivi podaci.",
							"Pogreška",
							JOptionPane.ERROR_MESSAGE);
					continue;
				}
			}
		}
	}
	
	
	private void showFCircleDialog(FilledCircle circle) {
		JPanel vanjski = new JPanel(new BorderLayout());
		JPanel lijevi = new JPanel(new GridLayout(5, 1));
		JPanel desni = new JPanel(new GridLayout(5, 1));
		
		vanjski.add(lijevi, BorderLayout.LINE_START);
		vanjski.add(desni, BorderLayout.CENTER);
		
		lijevi.add(new JLabel("centerX: "));
		lijevi.add(new JLabel("centerY: "));
		lijevi.add(new JLabel("Radius: "));
		lijevi.add(new JLabel("Circle: "));
		lijevi.add(new JLabel("Area: "));
		
		JTextField centerX = new JTextField();
		centerX.setText(String.valueOf(circle.getCenterX()));
		JTextField centerY = new JTextField();
		centerY.setText(String.valueOf(circle.getCenterY()));
		JTextField radius = new JTextField();
		radius.setText(String.valueOf(circle.getRadius()));
		JColorArea color1 = new JColorArea(circle.getColor(), "color1", ColorType.FOREGROUND);
		JColorArea color2 = new JColorArea(circle.getBackground(), "color2", ColorType.FOREGROUND);
		desni.add(centerX);
		desni.add(centerY);
		desni.add(radius);
		desni.add(color1);
		desni.add(color2);
		
		while(true) {
			int rez = JOptionPane.showConfirmDialog(
							JVDraw.this, vanjski, "Unesite podatke: ",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(rez == JOptionPane.CANCEL_OPTION) {
				break;
			}
			
			if(rez == JOptionPane.OK_OPTION) {
				int cx, cy;
				double r;
				Color newColor1, newColor2;
				try {
					cx = Integer.parseInt(centerX.getText());
					cy = Integer.parseInt(centerY.getText());
					r = Double.parseDouble(radius.getText());
					newColor1 = color1.getCurrentColor();
					newColor2 = color2.getCurrentColor();
					circle.setCenterX(cx);
					circle.setCenterY(cy);
					circle.setRadius(r);
					circle.setColor(newColor1);
					circle.setBackground(newColor2);
					model.objectChanged(circle);
					break;
				} catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(
							JVDraw.this,
							"Unešeni su krivi podaci.",
							"Pogreška",
							JOptionPane.ERROR_MESSAGE);
					continue;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JVDraw().setVisible(true);
			}
		});
	}

}
