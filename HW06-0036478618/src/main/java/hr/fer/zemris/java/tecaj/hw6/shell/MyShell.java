package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple implementation of a shell. <br>
 * Commands can be written in more than one line if line ends with symbol that
 * represents MORELINES in {@link ShellSymbol.MORELINES}.
 * @see CatCommand
 * @see CharSequence
 * @see CopyCommand
 * @see ExitShellCommand
 * @see HexdumpCommand
 * @see LsCommand
 * @see MkdirCommand
 * @see ShellCommand
 * @see SymbolShellCommand
 * @see TreeCommand
 * @author Ivan Krpelnik
 *
 */
public class MyShell {

	private static Map<String, ShellCommand> commands;
	
	public static void main(String[] args) {
	
		initializeCommands();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
		
		try {
			out.write("Welcome to MyShell v 1.0");
			out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ShellStatus status = ShellStatus.CONTINUE;
		do {
			String[] arguments;
			try {
				arguments = readLines(in, out);
			} catch (IOException e) {
				throw new RuntimeException("Reading from standard input failed.");
			}
			ShellCommand command = commands.get(arguments[0]);
			if(command == null) {
				try {
					out.write("Invalid command.");
					out.newLine();
					out.flush();
					continue;
				} catch (IOException e) {
					throw new RuntimeException("Writing on standard output failed.");
				}
			}
			
			try {
				status = command.executeCommand(in, out, Arrays.copyOfRange(arguments, 1, arguments.length));
				out.flush();
			} catch (IOException e) {
				throw new RuntimeException("Failed stream.", e);
			}
		} while(status != ShellStatus.TERMINATE);
	}
	
	/**
	 * Initialize map of commands.
	 */
	private static void initializeCommands() {
		commands = new HashMap<>();

		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("cat", new CatCommand());
		commands.put("ls", new LsCommand());
		commands.put("tree", new TreeCommand());
		commands.put("copy", new CopyCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("hexdump", new HexdumpCommand());
	}

	/**
	 * Reads lines of a command with given BufferedReader.
	 * @param in
	 * @param out
	 * @return
	 * @throws IOException
	 */
	private static String[] readLines(BufferedReader in, BufferedWriter out) throws IOException {
		out.write(ShellSymbol.PROMPT.getSymbol() + " ");	
		out.flush();
		StringBuilder sb = new StringBuilder();
		String input;
		input = extInput(in);
		sb.append(input);
		while(input.endsWith(String.valueOf(ShellSymbol.MORELINES.getSymbol()))) {
			out.write(ShellSymbol.MULTILINE.getSymbol() + " ");
			out.flush();
			input = extInput(in);
			sb.append(" " + input);
		}
		
		return sb.toString().replaceAll("\\\\", "").replaceAll("\\s+", " ").split(" ");
	}

	private static String extInput(BufferedReader in) throws IOException {
		String input;
		input = in.readLine();
		input = input.trim();
		input = input.replaceAll("\\s+", " ");
		return input;
	}
	
}
