package hr.fer.zemris.java.filechecking;

import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.FCParser;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji provjerava da li dani program sadrži leksičke ili sintaksne pogreške.
 * @author Ivan Krpelnik
 *
 */
public class FCProgramChecker {
	
	private String program;
	private List<String> errors;
	private ProgramNode programNode;
	
	/**
	 * Konstruktor.
	 * @param program - kod programa
	 */
	public FCProgramChecker(String program) {
		super();
		this.program = program;
		this.errors = new ArrayList<>();
		checkProgram();
	}

	/**
	 * Provjerava program i sprema interno prvu pogrešku koju je nasao.
	 */
	private void checkProgram() {
		try {
			FCParser parser = new FCParser(new FCTokenizer(program));
			programNode = parser.getProgramNode();
		} catch(FileCheckingException e) {
			errors.add(e.getMessage());
			return;
		} catch(RuntimeException e) {
			errors.add(e.getMessage());
			return;
		}
	}
	
	public ProgramNode getProgramNode() {
		return programNode;
	}
	
	/**
	 * @return true ako ima pogreške, false inače.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * @return listu pogrešaka.
	 */
	public List<String> errors() {
		return errors;
	}
}
