package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static hr.fer.zemris.java.tecaj.hw6.shell.ShellStatus.writeMessage;

/**
 * cat <br>
 * Writes content of a file to shell.
 * @author Ivan Krpelnik
 *
 */
public class CatCommand implements ShellCommand {

	/**
	 * Checks if given arguments are valid and calls method for writing content of a file to shell. <br>
	 * First argument should be path leading to a file. <br>
	 * Second argument is optional, if it exists, it should be string representation of {@link Charset}.
	 * @return {@link ShellStatus}.CONTINUE
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		if(arguments.length < 1 || arguments.length > 2 || 
				arguments[0] == null || (arguments.length == 2 && arguments[1] == null)) {
			return writeMessage(out, "Invalid arguments for cat command.", ShellStatus.CONTINUE);
		}
		Path path;
		try {
			path = Paths.get(arguments[0]);
		} catch (InvalidPathException e) {
			return writeMessage(out, "First argument is invalid, it cannot be converted to path.", ShellStatus.CONTINUE);
		}
		boolean isFile = false;
		try {
			isFile = path.toFile().isFile();
		} catch (SecurityException e) {
			return writeMessage(out, "Cannot read file, denied access.", ShellStatus.CONTINUE);
		}
		if(!isFile) {
			return writeMessage(out, "Path is not leading to file.", ShellStatus.CONTINUE);
		}
		if(arguments.length == 2 && Charset.availableCharsets().get(arguments[1]) == null) {
			return writeMessage(out, "Second argument should be valid charset.", ShellStatus.CONTINUE);
		}
		catFile(arguments);
		out.newLine();
		return ShellStatus.CONTINUE;
	}

	/**
	 * Private method for writing a file.
	 * @param arguments
	 * @throws IOException
	 */
	private void catFile(String[] arguments) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(arguments[0]),
				arguments.length == 2 ? Charset.availableCharsets().get(arguments[1]) : Charset.defaultCharset()));
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out,
				arguments.length == 2 ? Charset.availableCharsets().get(arguments[1]) : Charset.defaultCharset()));
		char[] cbuf = new char[1024];
		int len;
		while((len = input.read(cbuf)) != -1) {
			output.write(cbuf, 0, len);
			output.flush();
		}
		input.close();
		return;
	}
	
}
