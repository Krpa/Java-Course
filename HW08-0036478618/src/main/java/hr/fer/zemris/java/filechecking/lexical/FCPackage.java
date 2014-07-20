package hr.fer.zemris.java.filechecking.lexical;


/**
 * Pomoćni razred koji služi za spremanje paketa kod tokeniziranja stringova.
 * @author Ivan Krpelnik
 *
 */
public class FCPackage {

	private String name;
	
	public FCPackage(String name) {
		if(name == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}
	
	public String toPathString() {
		return "/" + name.replaceAll("\\.", "/");
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof FCPackage)) {
			return false;
		}
 		return name.equals(((FCPackage)obj).getName());
	}
	
	@Override
	public String toString() {
		return "package:"+name;
	}
}
