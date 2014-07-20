package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * symbol <br>
 * Symbol command should get 1 or 2 arguments.
 * First argument should be "PROMPT", "MORELINES" or "MULTILINE". 
 * If second argument is not provided, symbol for given first argument is written to shell.
 * If second argument is provided, it should be a new symbol on which first argument should be set.
 * @author Ivan Krpelnik
 *
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * @see SymbolShellCommand
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		
		if(arguments.length == 1) {
			ShellSymbol symbol = ShellSymbol.parse(arguments[0]);
			if(symbol == null) {
				out.write("Unknown argument for symbol command.");
			} else {
				out.write("Symbol for " + arguments[0] + " is \'" + symbol.getSymbol() +"\'");
			}
		} else if(arguments.length == 2) {
			ShellSymbol symbol = ShellSymbol.parse(arguments[0]);
			if(symbol == null || arguments[1] == null || arguments[1].length() != 1) {
				out.write("Unkown arguments for symbol command.");
			} else {
				out.write("Symbol for " + arguments[0] + " changed from \'" 
							+ symbol.getSymbol() + "\' to \'" + arguments[1] + "\'");
				symbol.setSymbol(arguments[1].charAt(0));
			}
		} else {
			out.write("Symbol command must have 1 or 2 arguments.");
		}
		out.newLine();
		return ShellStatus.CONTINUE;
	}

}
