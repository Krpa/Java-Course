package hr.fer.zemris.web.radionice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Test;

public class BazaTest {

	@Test
	public void test1() throws IOException {
		RadioniceBaza baza = RadioniceBaza.ucitaj("./baza");
		Path d = Files.createTempDirectory("temp");
		baza.snimi(d.toString());
		baza.snimi();
		System.out.println(d);
		delete(d);
	}
	
	@Test
	public void test2() throws IOException {
		RadioniceBaza baza = RadioniceBaza.ucitaj("./baza");
		Radionica radionica = baza.dohvatiRadionicu(1);
		radionica.getOprema().add(new Opcija("101", "USB stick"));
		Path d = Files.createTempDirectory("temp");
		try {
			baza.snimi();
		} catch (InconsistentDatabaseException e) {
			delete(d);
			return;
		}
		delete(d);
		Assert.fail();
	}
	
	public void delete(Path d) throws IOException {
		String[]entries = d.toFile().list();
		for(String s: entries){
		    File currentFile = new File(d.toString(),s);
		    currentFile.delete();
		}
	}
}
