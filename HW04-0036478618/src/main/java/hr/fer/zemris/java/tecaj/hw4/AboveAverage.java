package hr.fer.zemris.java.tecaj.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class reads doubles from standard input (one number per line). Input should end with "quit" (without quotes). <br>
 * Calculates average and outputs all given numbers that are at least 20% larger than the average in ascending order.
 * @author Ivan Krpelnik
 *
 */

public class AboveAverage {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		List<Double> list = new ArrayList<Double>();
		double sum = 0;
		String line = reader.readLine();
		while(line != null && !line.equalsIgnoreCase("quit")) {
			double curr = Double.parseDouble(line);
			sum += curr;
			list.add(curr);
			line = reader.readLine();
		}
		Collections.sort(list);
		double average = sum / list.size() * 1.2;
		System.out.println(average);
		for(Double number : list) {
			if(number - average > 1e-6) {
				System.out.println(number);
			}
		}
	}
}
