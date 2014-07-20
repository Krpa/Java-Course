package hr.fer.zemris.java.filechecking.demo;
import hr.fer.zemris.java.filechecking.FCFileVerifier;
import hr.fer.zemris.java.filechecking.FCProgramChecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Demo za razrede paketa hr.fer.zemris.java.filechecking i njegovih podpaketa.
 * @author Ivan Krpelnik
 *
 */

public class FCDemo {
	
	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
            System.out.println("Program ocekuje 3 argumenta.");
            System.exit(0);
        }
		
        File file = new File(args[0]);
        String program = ucitaj(args[2]);
        String fileName = args[1];
        FCProgramChecker checker = new FCProgramChecker(program);
		if(checker.hasErrors()) {
			System.out.println("Predani program sadrži pogreške!");
			for(String error : checker.errors()) {
				System.out.println(error);
			}
			System.out.println("Odustajem od daljnjeg rada.");
			System.exit(0);
		}
		
		Map<String,Object> initialData = new HashMap<>();
		initialData.put("jmbag", "0012345678");
		initialData.put("lastName", "Perić");
		initialData.put("firstName", "Pero");
		FCFileVerifier verifier = new FCFileVerifier(
					file, fileName, program, initialData);
		if(!verifier.hasErrors()) {
			System.out.println("Yes! Uspjeh! Ovakav upload bi bio prihvaćen.");
		}else {
			System.out.println("Ups! Ovaj upload se odbija! Što nam još ne valja?");
			for(String error : verifier.errors()) {
				System.out.println(error);
			}
		}
	}
	private static String ucitaj(String fileName) throws IOException {
		return new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
	}
}