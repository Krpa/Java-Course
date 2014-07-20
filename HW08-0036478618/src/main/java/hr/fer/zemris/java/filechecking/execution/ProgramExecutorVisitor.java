package hr.fer.zemris.java.filechecking.execution;

import hr.fer.zemris.java.filechecking.syntax.nodes.DefNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.ExistsNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FailNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FileNameNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FormatNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.Node;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.TerminateNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.arguments.CaseInsStringArgument;
import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.NodeVisitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Razred koji služi za izvršavanje danog sintaksnog stabla.
 * @see NodeVisitor
 * @author Ivan Krpelnik
 *
 */

public class ProgramExecutorVisitor implements NodeVisitor {

	/**
	 * Datoteka
	 */
	private File file;
	
	/**
	 * Originalno ime datoteke
	 */
	private String fileName;
	/**
	 * Vršni čvor programa.
	 */
	private ProgramNode programNode;
	/**
	 * Mapa definiranih varijabli.
	 */
	private Map<String, Object> variables;
	
	/**
	 * Generirane pogreške
	 */
	private List<String> pogreske;
	
	/**
	 * Konstruktor
	 * @param file - datoteka koju treba provjeriti
	 * @param fileName - originalno ime datoteke
	 * @param programNode - korijen sintaksnog stabla
	 * @param initialData - mapa definiranih varijabli
	 */
	public ProgramExecutorVisitor(File file, String fileName,
			ProgramNode programNode, Map<String, Object> initialData) {
		super();
		this.file = file;
		this.fileName = fileName;
		this.programNode = programNode;
		this.variables = initialData;
		this.pogreske = new ArrayList<>();
	}
	
	/**
	 * Pocetak izvrsavanja programa.
	 */
	public void execute() {
		visit(programNode);
	}
	
	/**
	 * Vraća listu pogrešaka
	 * @return lista pogrešaka
	 */
	public List<String> errors() {
		return pogreske;
	}
	
	/**
	 * Provjerava da li ima pogrešaka
	 * @return true ako nema pogrešaka, false inače
	 */
	public boolean hasErrors() {
		return !pogreske.isEmpty();
	}
	
	@Override
	public boolean visit(ProgramNode node) {
		for(Node child : node.getChildren()) {
			if(child.accept(this) == false) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean visit(DefNode node) {
		String name = node.getName();
		String value = node.getStrArg(variables);
		variables.put(name, value);
		return true;
	}
	
	@Override
	public boolean visit(ExistsNode node) {
		ZipFile zipfile;
		try {
			zipfile = new ZipFile(file);
			ZipEntry entry = zipfile.getEntry(
					node.getStrArg(variables)
					+ (node.dir() ? "/" : "")
					);
			zipfile.close(); 
			boolean rez = entry != null && 
					(node.dir() && entry.isDirectory() || !node.dir() && !entry.isDirectory());
			rez ^= node.isReversed();
			if(rez) {
					ProgramNode rootChildren = new ProgramNode(node.getChildren());
					return rootChildren.accept(this);
			} else {
				pogreske.add(node.getStrErr(variables));
			}
		} catch (IOException e) {
			pogreske.add("Nije uspijelo pristupanje zip datoteci u naredbi: " + node);
		}
		return true;
	}

	@Override
	public boolean visit(FileNameNode node) {
		boolean rez;
		if(node.getArg() instanceof CaseInsStringArgument) {
			rez = fileName.equalsIgnoreCase(node.getStrArg(variables));
		} else {
			rez = fileName.equals(node.getStrArg(variables));
		}
		rez ^= node.isReversed();
		if(rez) {
			ProgramNode rootChildren = new ProgramNode(node.getChildren());
			return rootChildren.accept(this);
		} else {
			pogreske.add(node.getStrErr(variables));
		}
		return true;
	}

	@Override
	public boolean visit(FormatNode node) {
		boolean rez = fileName.endsWith(node.getFormat());
		rez ^= node.isReversed();
		if(rez) {
			ProgramNode rootChildren = new ProgramNode(node.getChildren());
			return rootChildren.accept(this);
		} else {
			pogreske.add(node.getStrErr(variables));
		}
		return true;
	}

	@Override
	public boolean visit(FailNode node) {
		boolean rez = node.isReversed()^true;
		if(rez) {
			pogreske.add(node.getStrErr(variables));
			ProgramNode rootChildren = new ProgramNode(node.getChildren());
			return rootChildren.accept(this);
		}
		return true;
	}

	@Override
	public boolean visit(TerminateNode node) {
		return false;
	}

}
