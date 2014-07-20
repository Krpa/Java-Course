package hr.fer.zemris.java.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GlasanjeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		List<Zapis> lista = new ArrayList<>();
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] params = line.split("\\t");
			lista.add(new Zapis(params[0], params[1], params[2], 0));
		}
		scanner.close();
		Collections.sort(lista);
		req.getServletContext().setAttribute("bands", lista);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	
	public static class Zapis implements Comparable<Zapis> {
		private String id;
		private String ime;
		private String link;
		private int brojGlasova;
		public Zapis(String id, String ime, String link, int brojGlasova) {
			super();
			this.id = id;
			this.ime = ime;
			this.link = link;
			this.brojGlasova = brojGlasova;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
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
	
}
