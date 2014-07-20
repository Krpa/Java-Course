package hr.fer.zemris.java.tecaj.hw4.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simulates interface for this simple database.<br>
 * Implements two queries: <br>
 * query jmbag = "0000000000" (jmbag should be written instead of zeroes) <br>
 * query lastName = "word" (last name or part of it should be written instead of word. It can contain 1 wild cart * 
 * at the beginning, end or somewhere in the middle. Wild cart represents any character sequence)<br>
 * Spaces are ignored.  
 * Write quit to terminate program.
 * @author Ivan Krpelnik
 *
 */
public class StudentDB {
	
	static final int DULJINA_JMBAGA = 12;
	static final int DULJINA_OCJENE = 3;
	
	static String quit	 = "\\s*[Qq][Uu][Ii][Tt]\\s*";
	static String query1 = "\\s*[Qq][Uu][Ee][Rr][Yy]\\s*[Jj][Mm][Bb][Aa][Gg]\\s*\\=\\s*\"\\d{10}\"\\s*";
	static String query2 = "\\s*[Qq][Uu][Ee][Rr][Yy]\\s*[Ll][Aa][Ss][Tt][Nn][Aa][Mm][Ee]\\s*\\=\\s*\".+\"\\s*";

	public static void main(String[] args) throws IOException {
		List<String> retci = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		StudentDatabase baza = new StudentDatabase(retci);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		System.out.println("Input query or write \"quit\" to terminate program.");
		String query = "";
		while(true) {
			System.out.printf(">");
			query = reader.readLine();
			if(checkQuery(quit, query, 0, baza)) {
				break;
			}
			if(checkQuery(query1, query, 1, baza)) {
				continue;
			}
			if(checkQuery(query2, query, 2, baza)) {
				continue;
			}
			System.out.println("Invalid request.");
		}
	}
	
	public static boolean checkQuery(String regex, String query, int Q, StudentDatabase baza) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(query);
		if(matcher.find() && matcher.group().length() == query.length()) {
			if(Q == 0) {
			} else if(Q == 1) {
				String jmbag = query.substring(query.indexOf('\"') + 1, query.lastIndexOf('\"'));
				ispis(Arrays.asList(baza.forJMBAG(jmbag)));
			} else if(Q == 2) {
				String mask = query.substring(query.indexOf('\"') + 1, query.lastIndexOf('\"'));
				ispis(baza.filter(new LastNameFilter(mask)));
			}
			
			return true;
		}
		return false;
	}
	
	public static void ispis(List<StudentRecord> lista) {
		if(lista.size() == 0) {
			System.out.println("No records found.");
			return;
		}
		int duljinaPrezimena = maxDuljinaPrezimena(lista);
		int duljinaImena = maxDuljinaImena(lista);
		printGranica(DULJINA_JMBAGA, duljinaPrezimena, duljinaImena, DULJINA_OCJENE);
		for(StudentRecord student : lista) {
			String punoPrezime = makePunoIme(student.getPrezimena());
			String punoIme = makePunoIme(student.getImena());
			System.out.printf("| " + student.getJmbag() + " | " + "%"+-(duljinaPrezimena-1)+"s" 
									+ "| " + "%"+-(duljinaImena-1)+"s" + "| " + student.getGrade() + " |" + "\n", 
									punoPrezime, punoIme);
		}
		printGranica(DULJINA_JMBAGA, duljinaPrezimena, duljinaImena, DULJINA_OCJENE);
	}
	
	public static String makePunoIme(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for(String string : list) {
			sb.append(string).append(" ");
		}
		return sb.toString();
	}
	
	public static void printGranica(int...duljine) {
		for(int duljina : duljine) {
			System.out.printf("+");
			while(duljina > 0) {
				System.out.printf("=");
				duljina--;
			}
		}
		System.out.println("+");
	}
	
	public static int maxDuljinaPrezimena(List<StudentRecord> lista) {
		int maxSize = 1;
		for(StudentRecord student : lista) {
			int temp = 1;
			for(String string : student.getPrezimena()) {
				temp += string.length() + 1;
			}
			if(temp > maxSize) {
				maxSize = temp;
			}
		}
		return maxSize;
	}
	
	public static int maxDuljinaImena(List<StudentRecord> lista) {
		int maxSize = 1;
		for(StudentRecord student : lista) {
			int temp = 1;
			for(String string : student.getImena()) {
				temp += string.length() + 1;
			}
			if(temp > maxSize) {
				maxSize = temp;
			}
		}
		return maxSize;
	}
}
