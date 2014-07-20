package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import static hr.fer.zemris.java.tecaj.hw6.shell.ShellStatus.writeMessage;

/**
 * exit<br>
 * Terminates shell.
 * @author Ivan Krpelnik
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * No arguments should be provided.
	 * @return {@link ShellStatus}.TERMINATE
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		
		if(arguments.length != 0) {
			return writeMessage(out, "exit command should not have arguments", ShellStatus.CONTINUE);
		}
		out.write("Shell terminated.");
		return ShellStatus.TERMINATE;
	}

}
