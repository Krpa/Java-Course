package hr.fer.zemris.java.filechecking.syntax.nodes.visitors;

import hr.fer.zemris.java.filechecking.syntax.nodes.DefNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.ExistsNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FailNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FileNameNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FormatNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.TerminateNode;

/**
 * Sučelje za razred koji će obilaziti sintaksno stablo i izvršavati naredbe.
 * Svaka metoda koja posjećuje neki čvor mora vraćati true ako posjetitelj treba
 * nastaviti posjećivati ili false ako se treba zaustaviti.
 * @author Ivan Krpelnik
 *
 */
public interface NodeVisitor {


	/**
	 * Posjetitelj za {@link DefNode}
	 * @param stmt - čvor koji treba posjetiti
	 * @return - boolean
	 */
	public boolean visit(DefNode stmt);
	
	/**
	 * Posjetitelj za {@link ProgramNode}
	 * @param stmt - čvor koji treba posjetiti
	 * @return - boolean
	 */
	public boolean visit(ProgramNode node);

	/**
	 * Posjetitelj za {@link ExistsNode}
	 * @param stmt - čvor koji treba posjetiti
	 * @return - boolean
	 */
	public boolean visit(ExistsNode existsNode);

	/**
	 * Posjetitelj za {@link FileNameNode}
	 * @param stmt - čvor koji treba posjetiti
	 * @return - boolean
	 */
	public boolean visit(FileNameNode fileNameNode);

	/**
	 * Posjetitelj za {@link FormatNode}
	 * @param stmt - čvor koji treba posjetiti
	 * @return - boolean
	 */
	public boolean visit(FormatNode formatNode);

	/**
	 * Posjetitelj za {@link FailNode}
	 * @param stmt - čvor koji treba posjetiti
	 * @return - boolean
	 */
	public boolean visit(FailNode failNode);

	/**
	 * Posjetitelj za {@link TerminateNode}
	 * @param stmt - čvor koji treba posjetiti
	 * @return - boolean
	 */
	public boolean visit(TerminateNode terminateNode);
	
	
}
