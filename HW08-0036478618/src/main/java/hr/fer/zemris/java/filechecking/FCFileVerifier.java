package hr.fer.zemris.java.filechecking;

import hr.fer.zemris.java.filechecking.execution.ProgramExecutorVisitor;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Razred koji provjerava zadovoljava li dana datoteka provjere iz danog programa.
 * @author Ivan Krpelnik
 *
 */
public class FCFileVerifier {
	
	private File file;
	private String fileName;
	private String program;
	private Map<String, Object> initialData;
	private List<String> errors;
	private FCProgramChecker checker;
	private ProgramNode programNode;
	
	/**
	 * Konstruktor.
	 * @param file - datoteka koju treba provjeriti
	 * @param fileName - originalno ime te datoteke
	 * @param program - program koji sadrzi provjere
	 * @param initialData - pocetne varijable za program
	 */
	public FCFileVerifier(File file, String fileName, String program,
			Map<String, Object> initialData) {
		super();
		this.file = file;
		this.fileName = fileName;
		this.program = program;
		this.initialData = initialData;
		this.errors = new ArrayList<>();
		checkFile();
	}
	
	/**
	 * Provjerava datoteku i sprema interno prijavljene pogreške.
	 */
	private void checkFile() {
		checker = new FCProgramChecker(program);
		if(checker.hasErrors()) {
			errors.add("Predani prgoram sadrzi greske.");
			errors.addAll(checker.errors());
		} else {
			try {
				programNode = checker.getProgramNode();
				ProgramExecutorVisitor executor = new ProgramExecutorVisitor(file, fileName, programNode, initialData);
				executor.execute();
				errors.addAll(executor.errors());
			} catch(FileCheckingException e) {
				errors.add("Postoji greska u programu: " + e.getMessage());
			} catch(RuntimeException e) {
				errors.add(e.getMessage());
			}
		}
	}
	
	/**
	 * @return true ako je razred nasao pogreske, inače false.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * @return Lista opisa pogrešaka.
	 */
	public List<String> errors() {
		return errors;
	}
}
