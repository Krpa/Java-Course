package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * mkdir <br>
 * Makes a directory with given path.
 * @author Ivan Krpelnik
 *
 */
public class MkdirCommand implements ShellCommand {

	/**
	 * 1 argument should be provided. It should be a path with a name of a directory that should be created.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		if(arguments.length != 1) {
			out.write("mkdir expects 1 argument");
		} else if(arguments[0] == null){
			out.write("Path should not be null.");
		} else {
			File dir = new File(arguments[0]);
			boolean created = false; 
			try {
				created = dir.mkdirs();
			} catch(SecurityException e) {
				out.write("Access denied.");
			}
			if(created) {
				out.write("Operation successful.");
			} else {
				out.write("Operation failed.");
			}
		}
		out.newLine();
		return ShellStatus.CONTINUE;
	}

}
