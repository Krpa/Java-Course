package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static hr.fer.zemris.java.tecaj.hw6.shell.ShellStatus.writeMessage;

/**
 * ls <br>
 * Lists content of given directory to shell (not recursive). <br>
 * Output is formated as following example: <br>
 * -rw-      53412 2009-03-15 12:59:31 azuriraj.ZIP <br>
 * drwx       4096 2011-06-08 12:59:31 b <br>
 * drwx       4096 2011-09-19 12:59:31 backup <br>
 * The output consists of 4 columns. First column indicates if current object is directory (d), readable (r), 
 * writable (w) and executable (x). Second column contains object size in bytes that is right aligned and 
 * occupies 10 characters. Follows file creation date/time and finally file name.
 * 
 * @author Ivan Krpelnik
 *
 */
public class LsCommand implements ShellCommand {

	/**
	 * 1 argument should be provided and it should be a path to directory.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		Path path;
		if(arguments.length != 1) {
			return writeMessage(out, "ls command should get 1 argument", ShellStatus.CONTINUE);
		} 
		try {
			path = Paths.get(arguments[0]);
		} catch (InvalidPathException e) {
			return writeMessage(out, "Argument should be valid path.", ShellStatus.CONTINUE);
		}
		boolean isDirectory = false;
		try {
			isDirectory = path.toFile().isDirectory();
		} catch (SecurityException e) {
			return writeMessage(out, "Acces denied.", ShellStatus.CONTINUE);
		}
		if(isDirectory == false) {
			return writeMessage(out, "Path should lead to directory.", ShellStatus.CONTINUE);
		}
		lsDirectory(out, arguments[0]);
		return ShellStatus.CONTINUE;
	}

	private void lsDirectory(BufferedWriter out, String dirPath) throws IOException {
		List<String> dirContent = fileList(dirPath);
		for(String string : dirContent) {
			out.write(string);
			out.newLine();
		}
	}
	
	private List<String> fileList(String directory) {
	       List<String> files = new ArrayList<>();
	       try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
	           for (Path path : directoryStream) {
	        	   files.add(getAttributes(path));
	           }
	       } catch (IOException ex) {}
	       return files;
	}
	
	private String getAttributes(Path path) throws IOException {
    	String attributes =
    			(path.toFile().isDirectory() ? "d" : "-") +
    			(path.toFile().canRead() ? "r" : "-") +
	        	(path.toFile().canWrite() ? "w" : "-") +
    			(path.toFile().canExecute() ? "x" : "-") + " ";
    	attributes += String.format("%10d", path.toFile().length()) + " ";
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	BasicFileAttributeView faView = Files.getFileAttributeView(
    	path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
    	);
    	BasicFileAttributes fileAttributes = faView.readAttributes();
    	FileTime fileTime = fileAttributes.creationTime();
    	attributes += sdf.format(new Date(fileTime.toMillis())) + " ";
    	attributes += path.toFile().getName();
    	return attributes;
	}
}
