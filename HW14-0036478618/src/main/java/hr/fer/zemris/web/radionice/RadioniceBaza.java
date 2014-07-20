package hr.fer.zemris.web.radionice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadioniceBaza {

	private String direktorij;
	private Map<Long, Radionica> radionice;
	private Map<Long, Opcija> oprema;
	private Map<Long, Opcija> publika;
	private Map<Long, Opcija> trajanja;
	private Long maxID;
	
	private static String radioniceTxt = "radionice.txt";
	private static String opremaTxt = "oprema.txt";
	private static String publikaTxt = "publika.txt";
	private static String trajanjaTxt = "trajanje.txt";
	private static String radioniceOpremaTxt = "radionice_oprema.txt";
	private static String radionicePublikaTxt = "radionice_publika.txt";
	
	private RadioniceBaza(String direktorij) {
		this.direktorij = direktorij;
		this.radionice = new HashMap<>();
		this.oprema = new HashMap<>();
		this.publika = new HashMap<>();
		this.trajanja = new HashMap<>();
	}
	
	public static RadioniceBaza ucitaj(String direktorij) throws IOException {
		RadioniceBaza baza = new RadioniceBaza(direktorij);
		Path path = Paths.get(direktorij);
		
		Path radionicePath = Paths.get(path.toString() + "/" + radioniceTxt);
		Path trajanjaPath = Paths.get(path.toString() + "/" + trajanjaTxt);
		Path publikaPath = Paths.get(path.toString() + "/" + publikaTxt);
		Path radionicePublikaPath = Paths.get(path.toString() + "/" + radionicePublikaTxt);
		Path opremaPath = Paths.get(path.toString() + "/" + opremaTxt);
		Path radioniceOpremaPath = Paths.get(path.toString() + "/" + radioniceOpremaTxt);

		List<String> radionicePublika = Files.readAllLines(radionicePublikaPath, StandardCharsets.UTF_8);
		popuniMapu(publikaPath, baza.publika);
		List<String> radioniceOprema = Files.readAllLines(radioniceOpremaPath, StandardCharsets.UTF_8);
		popuniMapu(opremaPath, baza.oprema);
		popuniMapu(trajanjaPath, baza.trajanja);
		List<String> lines = Files.readAllLines(radionicePath, StandardCharsets.UTF_8);
		
		for(String line : lines) {
			line = line.trim();
			if(line.isEmpty()) {
				continue;
			}
			String[] elementi = line.split("\t");
			Radionica r = new Radionica();
			r.setId(Long.valueOf(elementi[0]));
			r.setNaziv(elementi[1]);
			r.setDatum(elementi[2]);
			r.setMaksPolaznika(Integer.parseInt(elementi[3]));
			r.setTrajanje(baza.trajanja.get(Long.valueOf(elementi[4])));
			r.setEmail(elementi[5]);
			String dopuna;
			if(elementi.length == 7) {
				dopuna = elementi[6];
			} else {
				dopuna = "";
			}
			dopuna = dopuna.replace("\\r", "\r").replace("\\n", "\n").replace("\\t", "\t").replace("\\\\", "\\");
			r.setDopuna(dopuna);
			
			for(String string : radionicePublika) {
				string = string.trim();
				String s[] = string.split("\t");
				if(s[0].equals(elementi[0])) {
					if(baza.publika.containsKey(Long.parseLong(s[1]))) {
						r.getPublika().add(baza.publika.get(Long.parseLong(s[1])));
					}
				}
			}
			
			for(String string : radioniceOprema) {
				string = string.trim();
				String s[] = string.split("\t");
				if(s[0].equals(elementi[0])) {
					if(baza.oprema.containsKey(Long.parseLong(s[1]))) {
						r.getOprema().add(baza.oprema.get(Long.parseLong(s[1])));
					}
				}
			}
			baza.zapamti(r);
		}
		
		return baza;
	}
	
	private static void popuniMapu(Path path, Map<Long, Opcija> ret) throws IOException {
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		for(String line : lines) {
			line = line.trim();
			String elems[] = line.split("\t");
			ret.put(Long.parseLong(elems[0]), new Opcija(elems[0], elems[1]));
		}
	}
	
	public void zapamti(Radionica r) {
		if(r.getId()==null) {
			Long noviId = maxID==null ? 1 : maxID+1;
			r.setId(noviId);
		}
		
		radionice.put(r.getId(), r);
		if(maxID == null || r.getId().compareTo(maxID)>0) {
			maxID = r.getId();
		}
	}
	
	public void snimi() throws InconsistentDatabaseException, IOException {
		provjeriIspravnostOpcija();
		snimiUDirektorij(direktorij);
	}
	
	private void snimiUDirektorij(String direktorij) throws IOException {
		Path path = Paths.get(direktorij + "/" + radioniceTxt);
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(path.toFile()), StandardCharsets.UTF_8);
		for(Long id : radionice.keySet()) {
			Radionica r = radionice.get(id);
			String dopuna = r.getDopuna();
			dopuna = dopuna.replace("\\", "\\\\").replace("\n", "\\n").replace("\t", "\\t").replace("\r", "\\r");
			String buff = r.getId() + "\t" + r.getNaziv() + "\t" + r.getDatum() + "\t" + r.getMaksPolaznika() 
					+ "\t" + r.getTrajanje().getId() + "\t" + r.getEmail() + "\t" + dopuna;
			fw.append(buff.subSequence(0, buff.length()));
			fw.append(System.lineSeparator());
		}
		fw.flush();
		fw.close();
		fw = new OutputStreamWriter(new FileOutputStream(Paths.get(direktorij + "/" + radioniceOpremaTxt).toFile()), StandardCharsets.UTF_8);
		for(Long id : radionice.keySet()) {
			Radionica r = radionice.get(id);
			for(Opcija o : r.getOprema()) {
				String buff = id + "\t" + o.getId();
				fw.append(buff.subSequence(0, buff.length()));
				fw.append(System.lineSeparator());
			}
		}
		fw.flush();
		fw.close();
		fw = new OutputStreamWriter(new FileOutputStream(Paths.get(direktorij + "/" + radionicePublikaTxt).toFile()), StandardCharsets.UTF_8);
		for(Long id : radionice.keySet()) {
			Radionica r = radionice.get(id);
			for(Opcija o : r.getPublika()) {
				String buff = id + "\t" + o.getId();
				fw.append(buff.subSequence(0, buff.length()));
				fw.append(System.lineSeparator());
			}
		}
		fw.flush();
		fw.close();
	}
	
	public void snimi(String direktorij) throws InconsistentDatabaseException, IOException {
		provjeriIspravnostOpcija();
		snimiUDirektorij(direktorij);
		Files.copy(Paths.get(this.direktorij + "/" + opremaTxt), Paths.get(direktorij + "/" + opremaTxt));
		Files.copy(Paths.get(this.direktorij + "/" + publikaTxt), Paths.get(direktorij + "/" + publikaTxt));
		Files.copy(Paths.get(this.direktorij + "/" + trajanjaTxt), Paths.get(direktorij + "/" + trajanjaTxt));
	}
	
	public void provjeriIspravnostOpcija() {
		for(Radionica r : radionice.values()) {
			for(Opcija o : r.getOprema()) {
				if(!oprema.containsValue(o)) {
					throw new InconsistentDatabaseException("Ne postoji oprema: " + o);
				}
			}
			for(Opcija o : r.getPublika()) {
				if(!publika.containsValue(o)) {
					throw new InconsistentDatabaseException("Ne postoji publika: " + o + "\n Ovo je publika: " + publika);
				}
			}
			if(!trajanja.containsValue(r.getTrajanje())) {
				throw new InconsistentDatabaseException("Ne postoji trajanje: " + r.getTrajanje());
			}
		}
		
	}
	
	public Radionica dohvatiRadionicu(long id) {
		return radionice.get(id);
	}
	
	public Opcija dohvatiOpremu(long id) {
		return oprema.get(id);
	}
	
	public Opcija dohvatiPubliku(long id) {
		return publika.get(id);
	}
	
	public Opcija dohvatiTrajanje(long id) {
		return trajanja.get(id);
	}
	
	public List<Radionica> getRadionice() {
		return new ArrayList<Radionica>(radionice.values());
	}
	
	public List<Opcija> getOprema() {
		return new ArrayList<>(oprema.values());
	}
	
	public List<Opcija> getPublika() {
		return new ArrayList<>(publika.values());
	}
	
	public List<Opcija> getTrajanja() {
		return new ArrayList<>(trajanja.values());
	}
}
