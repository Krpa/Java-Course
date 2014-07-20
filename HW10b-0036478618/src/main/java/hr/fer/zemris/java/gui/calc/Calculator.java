package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

import java.awt.Container;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * GUI for calculator.
 * @author Ivan Krpelnik
 *
 */
public class Calculator {
	
	/**
	 * Adds panel to given container with all buttons and a screen for calculator.
	 * @param pane - container
	 */
	public static void addComponentsToPane(Container pane) {
		JPanel p = new JPanel(new CalcLayout(3));
		JLabel screen = new JLabel("", JLabel.RIGHT);
		screen.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		screen.setName("screen");
		screen.setFont(new Font("Serif", Font.PLAIN, 18));
		p.add(screen, "1,1");
		p.add(new JButton("="), "1,6");
		p.add(new JButton("clr"), "1,7");
		p.add(new JButton("1/x"), "2,1");
		p.add(new JButton("sin"), "2,2");
		p.add(new JButton("7"), "2,3");
		p.add(new JButton("8"), "2,4");
		p.add(new JButton("9"), "2,5");
		p.add(new JButton("/"), "2,6");
		p.add(new JButton("res"), "2,7");
		p.add(new JButton("log"), "3,1");
		p.add(new JButton("cos"), "3,2");
		p.add(new JButton("4"), "3,3");
		p.add(new JButton("5"), "3,4");
		p.add(new JButton("6"), "3,5");
		p.add(new JButton("*"), "3,6");
		p.add(new JButton("push"), "3,7");
		p.add(new JButton("ln"), "4,1");
		p.add(new JButton("tan"), "4,2");
		p.add(new JButton("1"), "4,3");
		p.add(new JButton("2"), "4,4");
		p.add(new JButton("3"), "4,5");
		p.add(new JButton("-"), "4,6");
		p.add(new JButton("pop"), "4,7");
		p.add(new JButton("x^n"), "5,1");
		p.add(new JButton("ctg"), "5,2");
		p.add(new JButton("0"), "5,3");
		p.add(new JButton("+/-"), "5,4");
		p.add(new JButton("."), "5,5");
		p.add(new JButton("+"), "5,6");
		p.add(new JCheckBox("Inv"), "5,7");
		new CalculatorListeners(p);
		pane.add(p);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Java Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
