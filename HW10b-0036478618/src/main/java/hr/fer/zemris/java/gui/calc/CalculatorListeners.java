package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.operations.OneArgOperations;
import hr.fer.zemris.java.gui.calc.operations.TwoArgsOperations;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Creates listeners for {@link Calculator} and keeps memory of a calculator.
 * @author Ivan Krpelnik
 *
 */
public class CalculatorListeners {
	

	private static Stack<String> stack = new Stack<>();
	private static Stack<String> workingStack = new Stack<>();
	private static String operator = "";
	private static String buffer = "";
	private boolean bufferIsResult = false;
	private JPanel panel;
	private JLabel screen;
	private boolean inverse;
	
	public CalculatorListeners(JPanel panel) {
		this.panel = panel;
		setListeners();
	}
	
	private void setListeners() {
		for(Component comp : panel.getComponents()) {
			if(comp.getName() != null && comp.getName().equalsIgnoreCase("screen") && comp instanceof JLabel) {
				screen = (JLabel)comp;
			} else if(comp instanceof JCheckBox) {
				final JCheckBox box = (JCheckBox) comp;
				setBoxListener(box);
			} else if(comp instanceof JButton) {
				final JButton button = (JButton) comp;
				setButtonListener(button);
			}
		}
	}

	private void setButtonListener(final JButton button) {
		if(isDigit(button.getText())) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(bufferIsResult || "0".equals(buffer)) {
						buffer = "";
						bufferIsResult = false;
					}
					buffer = buffer + button.getText();
					screen.setText(buffer);
				}
			});
		} else if("clr".equals(button.getText())) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					buffer = "";
					bufferIsResult = false;
					screen.setText(buffer);
				}
			});
		} else if("res".equals(button.getText())) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					stack = new Stack<>();
					workingStack = new Stack<>();
					operator = "";
					buffer = "";
					screen.setText(buffer);
				}
			});
		} else if("push".equals(button.getText())) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!isNumber(screen.getText())) {
						screen.setText("Push Error - enter a number");
					} else {
						workingStack.push(screen.getText());
					}
				}
			});
		} else if("pop".equals(button.getText())) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(workingStack.isEmpty()) {
						screen.setText("Pop Error - empty stack");
						buffer = "";
						bufferIsResult = false;
					} else {
						buffer = workingStack.pop();
						bufferIsResult = false;
						screen.setText(buffer);
					}
				}
			});
		} else if("=".equals(button.getText())) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!buffer.isEmpty() && !stack.isEmpty() && !operator.isEmpty()) {
						try {
							screen.setText(TwoArgsOperations.doOperation(operator, stack.pop(), buffer, inverse));
							buffer = screen.getText();
							bufferIsResult = true;
						} catch (IllegalArgumentException exception) {
							catchedException(exception.getMessage());
						}
					}
				}
			});
		} else if("+/-".equals(button.getText())) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(buffer.startsWith("-")) {
						buffer = buffer.substring(1);
					} else {
						buffer = "-" + buffer;
					}
					screen.setText(buffer);
				}
			});
		} else if(".".equals(button.getText())) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(buffer.contains(".") && !bufferIsResult) {
						//ignore
					} else {
						if(bufferIsResult || buffer.isEmpty()) {
							buffer = "0";
							bufferIsResult = false;
						}
						buffer += ".";
					}
					screen.setText(buffer);
				} 
			});
		} else if(twoOperandsOperation(button.getText())) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(buffer.isEmpty()) {
						if(stack.isEmpty()) {
							screen.setText("Arithmetic error - enter first operand");
						} else {
							operator = button.getText();
						}
					} else {
						if(stack.isEmpty()) {
							stack.push(buffer);
							buffer = "";
							bufferIsResult = false;
							operator = button.getText();
						} else {
							try {
								screen.setText(TwoArgsOperations.doOperation(operator, stack.pop(), buffer, inverse));
								buffer = "";
								bufferIsResult = false;
								stack.push(screen.getText());
								operator = button.getText();
							} catch (IllegalArgumentException exception) {
								catchedException(exception.getMessage());
							}
						}
					}
				}
			});
		} else if(oneOperandOperation(button.getText())) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(buffer.isEmpty()) {
						screen.setText("Function error - no argument.");
					} else {
						try {
							buffer = OneArgOperations.doOperation(button.getText(), buffer, inverse);
							screen.setText(buffer);
							bufferIsResult = true;
						} catch (IllegalArgumentException exception) {
							catchedException(exception.getMessage());
						}
					}
				}
			});
		}
	}

	private void setBoxListener(final JCheckBox box) {
		if("Inv".equals(box.getText())) {
			box.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					inverse = !inverse;
				}
			});
		}
	}

	private void catchedException(String message) {
		screen.setText(message);
		buffer = "";
		bufferIsResult = false;
	}

	private boolean isNumber(String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private boolean isDigit(String text) {
		for(int i = 0; i < 10; ++i) {
			if(String.valueOf(i).equals(text)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean twoOperandsOperation(String text) {
		return "+".equals(text) || "-".equals(text) || "*".equals(text) || "/".equals(text) ||"x^n".equals(text);
	}
	
	private boolean oneOperandOperation(String text) {
		return "1/x".equals(text) || "sin".equals(text) || "cos".equals(text) || "log".equals(text) ||
				"ln".equals(text) || "tan".equals(text) || "ctg".equals(text);
	} 
}
