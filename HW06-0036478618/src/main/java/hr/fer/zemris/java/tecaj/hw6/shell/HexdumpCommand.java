package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static hr.fer.zemris.java.tecaj.hw6.shell.ShellStatus.writeMessage;

/**
 * hexdump <br>
 * Writes hexdump of a given file to shell.
 * @author Ivan Krpelnik
 *
 */
public class HexdumpCommand implements ShellCommand {

	/**
	 *	Only 1 argument should be provided. That argument should lead to readable file.
	 *	@return {@link ShellStatus}.CONTINUE
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		if(arguments.length != 1) {
			return writeMessage(out, "hexdump expects 1 argument", ShellStatus.CONTINUE);
		} else {
			Path path;
			try {
				path = Paths.get(arguments[0]);
			} catch(InvalidPathException e) {
				return writeMessage(out, "Invalid path.", ShellStatus.CONTINUE);
			}
			try {
				if(!path.toFile().isFile()) {
					return writeMessage(out, "Path should lead to file.", ShellStatus.CONTINUE);
				}
			} catch(SecurityException e) {
				return writeMessage(out, "Access denied.", ShellStatus.CONTINUE);
			}
			outputHex(path, out);
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Writes hexdump.
	 * @param path - path to file.
	 * @param out - BufferedWriter.
	 * @throws IOException
	 */
	private void outputHex(Path path, BufferedWriter out) throws IOException {
		FileInputStream reader = new FileInputStream(path.toFile());
		byte[] bytes = new byte[16];
		int len;
		int i = 0;
		while((len = reader.read(bytes)) != -1) {
			out.write(String.format("%08x:", i));
			i += 16;
			String temp = "";
			for(int j = 0; j < len && j < 8; j++) {
				temp += String.format(" %02x", bytes[j]);
			}
			out.write(String.format("%-24s", temp).toUpperCase());
			out.write("|");
			temp = "";
			for(int j = 8; j < len && j < 16; j++) {
				temp += String.format("%02x ", bytes[j]);
			}
			out.write(String.format("%-24s", temp).toUpperCase());
			out.write("| ");
			for(int j = 0; j < len; j++) {
				out.write(String.format("%c", ((bytes[j]&0xff) < 32 || (bytes[j]&0xff) > 127 ? '.' : bytes[j])));
			}
			out.newLine();
			out.flush();
		}
		reader.close();		
	}

}
