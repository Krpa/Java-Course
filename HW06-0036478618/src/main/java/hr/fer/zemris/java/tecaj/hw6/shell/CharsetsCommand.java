package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import static hr.fer.zemris.java.tecaj.hw6.shell.ShellStatus.writeMessage;

/**
 * charsets<br>
 * Writes supported {@link Charset}s to shell.
 * @author Ivan Krpelnik
 *
 */
public class CharsetsCommand implements ShellCommand {

	/**
	 * Should not get any arguments.
	 * @return {@link ShellStatus}.CONTINUE
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		
		if(arguments.length != 0) {
			return writeMessage(out, "charsets command should not get any arguments", ShellStatus.CONTINUE);
		}
		
		for(String name : Charset.availableCharsets().keySet()) {
			out.write(name);
			out.newLine();
		}
		return ShellStatus.CONTINUE;
	}

}
