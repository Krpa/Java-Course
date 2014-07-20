package hr.fer.zemris.java.tecaj_2.jcomp;

import hr.fer.zemris.java.tecaj_2.jcomp.impl.ComputerImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.ProgramParser;

/**
 * Razred za simuliranje računala.
 * @author Ivan Krpelnik
 * 
 */

public class Simulator {

	public static void main(String[] args) throws Exception {
		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);
		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
		 "hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
		);
		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		ProgramParser.parse(
		 "examples/prim2.txt", 
		 comp, 
		 creator
		);
		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		// Izvedi program
		boolean izvrsen = exec.go(comp);
		if(!izvrsen) {
			System.err.println("Program se nije uspio izvršiti.");
		}
	}
	
}
