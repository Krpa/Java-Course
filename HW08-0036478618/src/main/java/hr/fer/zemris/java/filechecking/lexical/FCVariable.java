package hr.fer.zemris.java.filechecking.lexical;

import java.util.Map;

/**
 * Pomoćni razred koji služi za spremanje varijable.
 * @author Ivan Krpelnik
 *
 */
public class FCVariable {

	private String name;
	
	public FCVariable(String name) {
		if(name == null || name.trim().isEmpty()) {
			throw new FCLexicalException("Ime varijable ne smije biti prazno.");
		}
		if(isInvalid(name.trim())) {
			throw new FCLexicalException("Ime varijable nije legalno: " + name.trim());
		}
		this.name = name.trim();
	}
	
	public String getName() {
		return name;
	}
	
	private boolean isInvalid(String name) {
		if(Character.isDigit(name.charAt(0))) {
			return true;
		}
		for(int i = name.length()-1; i >= 0; --i) {
			if(Character.isDigit(name.charAt(i)) || Character.isLetter(name.charAt(i)) || 
					name.charAt(i) == '_' || name.charAt(i) == '.') {
				continue;
			} else {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasValue(Map<String, Object> mapa) {
		return mapa.containsKey(name);
	}
	
	public Object getValue(Map<String, Object> mapa) {
		return mapa.get(name);
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
		if(!(obj instanceof FCVariable)) {
			return false;
		}
 		return name.equals(((FCVariable)obj).getName());
	}
	
	@Override
	public String toString() {
		return "${"+name+"}";
	}
	
}
