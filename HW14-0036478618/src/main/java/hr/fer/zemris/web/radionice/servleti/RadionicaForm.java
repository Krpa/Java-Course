package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.Opcija;
import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RadionicaForm {

	private String id;
	private String naziv;
	private String datum;
	private String[] oprema;
	private String trajanje;
	private String[] publika;
	private String maksPolaznika;
	private String email;
	private String dopuna;
	
	
	Map<String, String> greske = new HashMap<>();
	
	public RadionicaForm() {
	}
	
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}
	
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}
	
	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}
	
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("id"));
		this.naziv = pripremi(req.getParameter("naziv"));
		this.datum = pripremi(req.getParameter("datum"));
		this.trajanje = pripremi(req.getParameter("trajanje"));
		this.maksPolaznika = pripremi(req.getParameter("maksPolaznika"));
		this.email = pripremi(req.getParameter("email"));
		this.dopuna = pripremi(req.getParameter("dopuna"));
		this.oprema = req.getParameterValues("oprema");
		this.publika = req.getParameterValues("publika");
	}
	
	public void popuniIzRadionice(Radionica r) {
		if(r.getId() == null) {
			this.id = "";
		} else {
			this.id = r.getId().toString();
		}
		
		this.naziv = r.getNaziv();
		this.datum = r.getDatum();
		if(r.getTrajanje() != null) {
			this.trajanje = r.getTrajanje().getId();
		}
		if(r.getMaksPolaznika() != null) {
			this.maksPolaznika = r.getMaksPolaznika().toString();
		}
		this.email = r.getEmail();
		this.dopuna = r.getDopuna();
		this.oprema = new String[r.getOprema().size()];
		int i = 0;
		for(Opcija o : r.getOprema()) {
			this.oprema[i++] = o.getId();
		}
		this.publika = new String[r.getPublika().size()];
		i = 0;
		for(Opcija o : r.getPublika()) {
			this.publika[i++] = o.getId();
		}
	}
	
	public void popuniURadionicu(Radionica r, String direktorijBaze) throws IOException {
		if(this.id.isEmpty()) {
			r.setId(null);
		} else {
			r.setId(Long.valueOf(this.id));
		}
		RadioniceBaza baza = RadioniceBaza.ucitaj(direktorijBaze);
		r.setNaziv(naziv);
		r.setDatum(datum);
		r.setDopuna(dopuna);
		r.setEmail(email);
		r.setMaksPolaznika(Integer.valueOf(maksPolaznika));
		r.setTrajanje(baza.dohvatiTrajanje(Long.parseLong(trajanje)));
		if(oprema != null) {
			for(String id : oprema) {
				r.getOprema().add(baza.dohvatiOpremu(Long.parseLong(id)));
			}
		}
		for(String id : publika) {
			r.getPublika().add(baza.dohvatiPubliku(Long.parseLong(id)));
		}
	}
	
	public void validiraj() {
		greske.clear();
		
		if(this.naziv.isEmpty()) {
			greske.put("naziv", "Naziv je obavezan!");
		}
		if(naziv.length() > 40) {
			greske.put("naziv", "Naziv može imati najviše 40 znakova!");
		}
		if(this.datum.isEmpty()) {
			greske.put("datum", "Datum je obavezan!");
		}
		String[] elems = datum.split("-");
		if(elems.length != 3) {
			greske.put("datum", "Datum treba biti formata yyyy-mm-dd!");
		}
		else try {
			Integer.parseInt(elems[0]);
			Integer.parseInt(elems[1]);
			Integer.parseInt(elems[2]);
		} catch(NumberFormatException e) {
			greske.put("datum", "Datum treba biti formata yyyy-mm-dd!");
		}
		if(trajanje.isEmpty()) {
			greske.put("trajanje", "Trajanje mora biti zadano!");
		}
		if(publika == null) {
			greske.put("publika", "Mora biti odabrana barem 1 opcija za publiku!");
		}
		if(maksPolaznika.isEmpty()) {
			greske.put("maksPolaznika", "Mora biti zadan maksimalan broj polaznika!");
		}
		try {
			int brojPolaznika = Integer.parseInt(maksPolaznika);
			if(brojPolaznika < 10 || brojPolaznika > 50) {
				greske.put("maksPolaznika", "Maksimalan broj polaznika mora biti broj iz intervala [10,50]");
			}
		} catch(NumberFormatException ex) {
			greske.put("maksPolaznika", "Maksimalan broj polaznika mora biti broj iz intervala [10,50]");
		}
		
		if(this.email.isEmpty()) {
			greske.put("email", "Email je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l < 3 || p == -1 || p == 0 || p ==l-1) {
				greske.put("email", "Email nije ispravnog formata.");
			}
		}
	}
	
	private String pripremi(String parameter) {
		if(parameter == null) {
			return "";
		}
		return parameter.trim();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String[] getOprema() {
		return oprema;
	}

	public void setOprema(String[] oprema) {
		this.oprema = oprema;
	}

	public String getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(String trajanje) {
		this.trajanje = trajanje;
	}

	public String[] getPublika() {
		return publika;
	}

	public void setPublika(String[] publika) {
		this.publika = publika;
	}

	public String getMaksPolaznika() {
		return maksPolaznika;
	}

	public void setMaksPolaznika(String maksPolaznika) {
		this.maksPolaznika = maksPolaznika;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDopuna() {
		return dopuna;
	}

	public void setDopuna(String dopuna) {
		this.dopuna = dopuna;
	}

	public Map<String, String> getGreske() {
		return greske;
	}

	public void setGreske(Map<String, String> greske) {
		this.greske = greske;
	}
	
	
	
}
