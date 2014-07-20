package hr.fer.zemris.java.gui.layouts;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CalcLayoutDemo {

	public static void addComponentsToPane(Container pane) {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JButton("1,1"), "1,1");
		p.add(new JButton("2,3"), "2,3");
		p.add(new JButton("2,7"), "2,7");
		p.add(new JButton("4,2"), "4,2");
		p.add(new JButton("4,5"), "4,5");
		p.add(new JButton("4,7"), "4,7");
		pane.add(p);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Test kalkulator");
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
