package hr.fer.zemris.java.tecaj.hw4.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents student record.
 * @author Ivan Krpelnik
 *
 */
public class StudentRecord {
	private String jmbag;
	private List<String> prezimena;
	private List<String> imena;
	private int grade;
	
	public StudentRecord(String jmbag, List<String> prezimena,
			List<String> imena, int grade) {
		super();
		this.jmbag = jmbag;
		this.prezimena = new ArrayList<>(prezimena);
		this.imena = new ArrayList<>(imena);
		this.grade = grade;
	}
	
	public int getGrade() {
		return grade;
	}
	
	public String getJmbag() {
		return jmbag;
	}
	
	public List<String> getPrezimena() {
		return Collections.unmodifiableList(prezimena);
	}
	
	public List<String> getImena() {
		return Collections.unmodifiableList(imena);
	}
	
	@Override
	public int hashCode() {
		return jmbag.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof StudentRecord)) {
			return false;
		}
		StudentRecord drugi = (StudentRecord) obj;
		return this.jmbag.equals(drugi.jmbag);
	}
}
