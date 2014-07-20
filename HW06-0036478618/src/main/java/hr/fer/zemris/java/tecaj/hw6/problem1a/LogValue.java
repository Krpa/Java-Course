package hr.fer.zemris.java.tecaj.hw6.problem1a;

import hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorageObserver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

/**
 * Implementation of {@link IntegerStorageObserver} that writes to file values of watched {@link IntegerStorage}.
 * @author Ivan Krpelnik
 *
 */
public class LogValue implements IntegerStorageObserver {
	
	private Writer bw;
	private Path path;
	
	/**
	 * Constructor sets path to file in which log should be written.
	 * @param path - given path
	 */
	public LogValue(Path path) {
		this.path = path;
	}
	
	/**
	 * Writes to file current value of given {@link IntegerStorage} 
	 * @throws RuntimeException if writing to file failed.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		try {
			bw = new FileWriter(path.toFile(), true);
			bw.append(String.valueOf(istorage.getValue()) + System.getProperty("line.separator"));
			bw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
