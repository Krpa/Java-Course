package hr.fer.zemris.java.tecaj.hw6.problem1b;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

/**
 * @see hr.fer.zemris.java.tecaj.hw6.problem1a.LogValue
 * @author Ivan Krpelnik
 *
 */
public class LogValue implements IntegerStorageObserver {
	
	private Writer bw;
	private Path path;
	
	public LogValue(Path path) {
		this.path = path;
	}
	
	@Override
	public void valueChanged(IntegerStorageChange storageChange) {
		try {
			bw = new FileWriter(path.toFile(), true);
			bw.append(String.valueOf(storageChange.getNewValue()) + System.getProperty("line.separator"));
			bw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
