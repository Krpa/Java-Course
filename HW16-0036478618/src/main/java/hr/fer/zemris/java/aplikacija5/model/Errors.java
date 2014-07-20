package hr.fer.zemris.java.aplikacija5.model;

import java.util.HashMap;
import java.util.Map;

public class Errors {
	
	private Map<String, String> errors = new HashMap<>();
	
	public String getErrorMessage(String type) {
		return errors.get(type);
	}
	
	public void putErrorMessage(String type, String message) {
		if(type == null) {
			throw new IllegalArgumentException("Error type cannot be null.");
		}
		if(message == null) {
			throw new IllegalArgumentException("Error message cannot be null.");
		}
		
		errors.put(type, message);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

}
