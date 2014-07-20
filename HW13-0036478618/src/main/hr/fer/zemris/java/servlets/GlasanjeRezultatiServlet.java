package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.servlets.GlasanjeServlet.Zapis;

import java.io.File;
import java.io.FileOutputStream;
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
public class GlasanjeRezultatiServlet extends HttpServlet {

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		File file = new File(fileName);
		List<Rezultat> lista = new ArrayList<>();
		List<Zapis> zapisi = (List<Zapis>)req.getServletContext().getAttribute("bands");
		if(!file.exists()) {
			FileOutputStream os = new FileOutputStream(file);
			StringBuilder sb = new StringBuilder();
			for(Zapis z : zapisi) {
				sb.append(z.getId()).append("\t").append(z.getBrojGlasova()).append("\n");
				lista.add(new Rezultat(z.getBrojGlasova(), z.getIme(), z.getLink()));
			}
			os.write(sb.toString().getBytes());
			os.close();
		} else {
			Scanner scanner = new Scanner(file);
			String line;
			while(scanner.hasNext()) {
				line = scanner.nextLine();
				String[] params = line.split("\t");
				String ime = "";
				String link = "";
				for(Zapis z : zapisi) {
					if(z.getId().equals(params[0])) {
						ime = z.getIme();
						link = z.getLink();
						break;
					}
				}
				lista.add(new Rezultat(Integer.parseInt(params[1]), ime, link));
			}
			scanner.close();
		}
		Collections.sort(lista);
		
		List<Pjesma> pjesme = new ArrayList<>();
		int maxBrojGlasova = -1;
		for(Rezultat rezultat : lista) {
			if(maxBrojGlasova == -1) {
				maxBrojGlasova = rezultat.getBrojGlasova();
			}
			if(rezultat.getBrojGlasova() < maxBrojGlasova) {
				break;
			}
			pjesme.add(new Pjesma(rezultat.getName(), rezultat.getLink()));
		}
		
		req.getServletContext().setAttribute("rezultati", lista);
		req.getServletContext().setAttribute("pjesme", pjesme);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	
	
	public static class Pjesma {
		private String imeBenda;
		private String link;
		public Pjesma(String imeBenda, String link) {
			super();
			this.imeBenda = imeBenda;
			this.link = link;
		}
		public String getImeBenda() {
			return imeBenda;
		}
		public void setImeBenda(String imeBenda) {
			this.imeBenda = imeBenda;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		
	}
	
	public static class Rezultat implements Comparable<Rezultat> {
		private int brojGlasova;
		private String name;
		private String link;
		
		public Rezultat(int brojGlasova, String name, String link) {
			super();
			this.brojGlasova = brojGlasova;
			this.name = name;
			this.link = link;
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
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public int compareTo(Rezultat o) {
			return -Integer.compare(brojGlasova, o.brojGlasova);
		}
		
	};
}
