package hr.fer.zemris.java.aplikacija5.model;

public class Zapis implements Comparable<Zapis> {
	private Long id;
	private String ime;
	private String link;
	private int brojGlasova;
	private Long pollID;
	
	public Zapis() {
	}
	
	public Zapis(long id, String ime, String link, int brojGlasova, Long pollID) {
		super();
		this.id = id;
		this.ime = ime;
		this.link = link;
		this.brojGlasova = brojGlasova;
		this.pollID = pollID;
	}
	
	
	
	public Long getPollID() {
		return pollID;
	}

	public void setPollID(Long pollID) {
		this.pollID = pollID;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getBrojGlasova() {
		return brojGlasova;
	}
	public void setBrojGlasova(int brojGlasova) {
		this.brojGlasova = brojGlasova;
	}
	@Override
	public int compareTo(Zapis o) {
		return id.compareTo(o.id);
	}
}