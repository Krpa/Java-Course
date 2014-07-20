package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * tree <br>
 * Tree command lists (recursive) content of given folder.
 * @author Ivan Krpelnik
 *
 */
public class TreeCommand implements ShellCommand {

	/**
	 * 1 argument should be provided and it should be a path to folder.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		if(arguments.length != 1) {
			out.write("tree command should get 1 argument");
		} else {
			try {
				if(!Files.isDirectory(Paths.get(arguments[0]))) {
					out.write("Path should lead to directory.");
				} else {
					Files.walkFileTree(Paths.get(arguments[0]), new Ispitivac(out));
				}
			} catch(SecurityException e) {
				out.write("Access denied.");
			}
		}
		out.newLine();
		return null;
	}

	private static class Ispitivac implements FileVisitor<Path> {

		private int indent = 0;
		private BufferedWriter out;

		public Ispitivac(BufferedWriter out) {
			this.out = out;
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			if (indent == 0) {
				out.write(String.format("%s%n", dir));
			} else {
				out.write(String.format("%" + indent + "s%s%n", "", dir.getName(dir.getNameCount() - 1)));
			}
			indent += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			out.write(String.format("%" + indent + "s%s%n", "", file.getName(file.getNameCount() - 1)));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			indent -= 2;
			return FileVisitResult.CONTINUE;
		}

	}
	
}
