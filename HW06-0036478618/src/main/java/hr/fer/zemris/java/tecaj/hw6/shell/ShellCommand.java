package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Interface of commands for {@link MyShell}.
 * @author Ivan Krpelnik
 *
 */
public interface ShellCommand {

	/**
	 * If provided arguments are not valid, command always returns {@link ShellStatus}.CONTINUE.<br>
	 * If shell should be terminated {@link ShellStatus}.TERMINATE is returned. <br>
	 * If command is executed and shell should not be terminated {@link ShellStatus}.CONTINUE is returned.
	 * @param in - BufferedReader
	 * @param out - BufferedWriter
	 * @param arguments - arguments of a command
	 * @throws IOException
	 */
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) throws IOException;
}
