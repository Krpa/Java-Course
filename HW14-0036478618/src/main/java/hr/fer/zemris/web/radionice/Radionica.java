package hr.fer.zemris.web.radionice;

import java.util.Set;
import java.util.TreeSet;

public class Radionica {

	private Long id;
	private String naziv;
	private String datum;
	private Set<Opcija> oprema;
	private Opcija trajanje;
	private Set<Opcija> publika;
	private Integer maksPolaznika;
	private String email;
	private String dopuna;

	
	
	public Radionica() {
		this.oprema = new TreeSet<>();
		this.publika = new TreeSet<>();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Set<Opcija> getOprema() {
		return oprema;
	}
	
	public Opcija getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(Opcija trajanje) {
		this.trajanje = trajanje;
	}
	public Set<Opcija> getPublika() {
		return publika;
	}
	
	public Integer getMaksPolaznika() {
		return maksPolaznika;
	}
	public void setMaksPolaznika(Integer maksPolaznika) {
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
	
	
	public boolean isValid() {
		if(id == null) {
			return false;
		}
		if(naziv == null || naziv.isEmpty() || naziv.length() > 40) {
			return false;
		}
		if(trajanje == null) {
			return false;
		}
		if(publika.isEmpty()) {
			return false;
		}
		if(maksPolaznika == null || maksPolaznika < 10 || maksPolaznika > 50) {
			return false;
		}
		if(email == null || email.isEmpty()) {
			return false;
		}
		if(dopuna == null) {
			return false;
		}
		return true;
	}
}
