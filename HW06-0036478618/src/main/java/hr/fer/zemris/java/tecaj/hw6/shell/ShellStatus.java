package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Contains values CONTINUE and TERMINATE. <br>
 * Has public static method for writing a message and returning ShellStatus.
 * @author Ivan Krpelnik
 *
 */
public enum ShellStatus {
	CONTINUE, 
	TERMINATE;
	
	/**
	 * Writes a message s to given out and returns given status.
	 * @param out - BufferedWriter
	 * @param s - String message
	 * @param status - ShellStatus.
	 * @return status
	 * @throws IOException
	 */
	public static ShellStatus writeMessage(BufferedWriter out, String s, ShellStatus status) throws IOException {
		out.write(s);
		out.newLine();
		return status;
	}
}
