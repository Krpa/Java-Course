package hr.fer.zemris.web.radionice;


public class Opcija implements Comparable<Opcija> {

	private String id;
	private String vrijednost;
	
	public Opcija(String id, String vrijednost) {
		super();
		if(!provjeriId(id)) {
			throw new IllegalArgumentException("Nije dan valjani id: " + id);
		}
		this.id = id.trim();
		this.vrijednost = vrijednost.trim();
	}
	
	private boolean provjeriId(String id) {
		try {
			Long.parseLong(id);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

	public String getVrijednost() {
		return vrijednost;
	}

	public void setVrijednost(String vrijednost) {
		this.vrijednost = vrijednost;
	}

	public String getId() {
		return id;
	}

	@Override
	public int compareTo(Opcija o) {
		if(this.id==null) {
			if(o.id==null) return 0;
			return -1;
		} else if(o.id==null) {
			return 1;
		}
		return this.id.compareTo(o.id);
	}
	
	@Override
	public String toString() {
		return id + "\t" + vrijednost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((vrijednost == null) ? 0 : vrijednost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Opcija other = (Opcija) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (vrijednost == null) {
			if (other.vrijednost != null)
				return false;
		} else if (!vrijednost.equals(other.vrijednost))
			return false;
		return true;
	}
	
	
}
