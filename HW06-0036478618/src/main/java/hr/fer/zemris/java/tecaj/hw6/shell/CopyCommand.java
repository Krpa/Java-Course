package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * copy <br>
 * Copies given file to given destination.
 * Destination can be a folder or a file. 
 * If destination is a file and it already exists, user is asked if it should be overwritten.
 * @author Ivan Krpelnik
 *
 */
public class CopyCommand implements ShellCommand {

	/**
	 * 2 arguments should be provided.
	 * First should be a path that leads to a file to be copied. 
	 * Second should be a path that leads to a folder or a file destination.
	 * @return {@link ShellStatus}.CONTINUE
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		
		if(arguments.length != 2) {
			out.write("copy command should get 2 arguments");
		} else {
			Path source = null, destination = null; 
			try {
				source = Paths.get(arguments[0]);
			} catch (InvalidPathException e) {
				return writeMessage(out, "Invalid path for first argument.");
			}
			try {
				destination = Paths.get(arguments[1]);
			} catch(InvalidPathException e) {
				return writeMessage(out, "Invalid path for second argument.");
			}
			try {
				if(!source.toFile().isFile()) {
					return writeMessage(out, "First argument shuold lead to file.");
				}
			} catch(SecurityException e) {
				return writeMessage(out, "First Argument: Access denied.");
			}
			copyFile(out, source, destination, overwrite(in, out, destination));
		}
		out.newLine();
		return ShellStatus.CONTINUE;
	}
	
	private void copyFile(BufferedWriter out, Path source, Path destination, boolean overwrite) throws IOException {
		if(!overwrite) {
			out.write("Operation failed.");
			return;
		}
		Path realDestination;
		if(destination.toFile().isDirectory()) {
			realDestination = Paths.get(destination.toFile().getPath() + "\\" + source.toFile().getName());
		} else {
			realDestination = Paths.get(destination.toFile().getPath());
		}
		FileOutputStream writer;
		try {
			writer = new FileOutputStream(realDestination.toFile());
		} catch (IOException e) {
			out.write("Cannot create destination file.");
			return;
		}
		if(Files.isSameFile(source, realDestination)) {
			writer.close();
			return;
		}
		FileInputStream reader = new FileInputStream(source.toFile());
		byte[] output = new byte[1024];
		int len;
		while((len = reader.read(output)) != -1) {
			writer.write(output, 0, len);
		}
		reader.close();
		writer.close();
		out.write("Operation successful.");
	}
	
	private boolean overwrite(BufferedReader in, BufferedWriter out, Path path) throws IOException {
		if(path.toFile().isFile()) {
			String input = "";
			do {
				out.write("Overwrite file? [y/n] ");
				out.flush();
				input = in.readLine();
			} while(!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N"));
			return input.equalsIgnoreCase("Y");
		}
		return true;
	}


	private ShellStatus writeMessage(BufferedWriter out, String s) throws IOException {
		out.write(s);
		out.newLine();
		return ShellStatus.CONTINUE;
	}
}
