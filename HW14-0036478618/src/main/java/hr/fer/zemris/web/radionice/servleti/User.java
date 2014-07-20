package hr.fer.zemris.web.radionice.servleti;

public class User {
	
	private String login;
	private String lozinka;
	private String ime;
	private String prezime;
	
	public User(String login, String lozinka, String ime, String prezime) {
		super();
		this.login = login;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
	}

	public String getLogin() {
		return login;
	}

	public String getLozinka() {
		return lozinka;
	}

	public String getIme() {
		return ime;
	}

	public String getPrezime() {
		return prezime;
	}

	@Override
	public String toString() {
		return ime + " " + prezime;
	}
}
