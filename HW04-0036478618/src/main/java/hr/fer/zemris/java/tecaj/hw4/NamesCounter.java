package hr.fer.zemris.java.tecaj.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Class reads names from standard input and counts how many times was each written. <br>
 * Input should end with "quit" (without quotes). <br>
 * Lower and upper case letters are treated as equal, meaning strings "name" and "NaMe" are considered to be equal.
 * @author Ivan Krpelnik
 *
 */

public class NamesCounter {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		Map<String, Integer> mapNames = new HashMap<String, Integer>();
		String line = reader.readLine();
		while(line != null && !line.equalsIgnoreCase("quit")) {
			line = line.toUpperCase();
			if(mapNames.containsKey(line)) {
				int br = mapNames.get(line);
				br++;
				mapNames.put(line, br);
			} else {
				mapNames.put(line, 1);
			}
			line = reader.readLine();
		}
		System.out.println(mapNames);
	}
}
