package hr.fer.zemris.java.tecaj.hw4.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentDatabase {
	
	private List<StudentRecord> students;
	private Map<String, StudentRecord> mapRecords;
	
	public StudentDatabase(List<String> records) {
		students = parse(records);
		mapRecords = makeMap(students);
	}
	
	private List<StudentRecord> parse(List<String> records) {
		
		List<StudentRecord> parsedRecords = new ArrayList<>();
		for(String string : records) {
			String[] record = string.split("\t");
			StudentRecord currStudent = new StudentRecord(
											parseJMBAG(record[0]), 
											parseNames(record[1]), parseNames(record[2]),
											Integer.parseInt(record[3]));
			parsedRecords.add(currStudent);
		}
		return parsedRecords;
	}
	
	private String parseJMBAG(String jmbag) {
		String ret = jmbag.trim();
		return ret;
	}
	
	private List<String> parseNames(String string) {
		String temp = string.trim();
		String[] names = temp.split(" ");
		return Arrays.asList(names);
	}
	
	private Map<String, StudentRecord> makeMap(List<StudentRecord> students) {
		Map<String, StudentRecord> map = new HashMap<>();
		for(StudentRecord student : students) {
			map.put(student.getJmbag(), student);
		}
		return map;
	}
	
	public StudentRecord forJMBAG(String jmbag) {
		return mapRecords.get(jmbag);
	}
	
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> list = new ArrayList<>();
		for(StudentRecord student : students) {
			if(filter.accepts(student)) {
				list.add(student);
			}
		}
		return Collections.unmodifiableList(list);
	}
}
