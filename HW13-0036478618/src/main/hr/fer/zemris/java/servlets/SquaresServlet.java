package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
@WebServlet("/kvadrati")
public class SquaresServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Integer a = null;
		Integer b = null;
		
		try {
			a = Integer.valueOf(req.getParameter("a"));
		} catch (Exception e) { a = 0; }
		
		try {
			b = Integer.valueOf(req.getParameter("b"));
		} catch (Exception e) { b = 20; }
		
		if(a > b) {
			Integer tmp = a;
			a = b;
			b = tmp;
		}
		
		List<Par> lista = new ArrayList<>();
		for(int i = a; i < b; ++i) {
			lista.add(new Par(i, i*i));
		}
		
		req.setAttribute("parovi", lista);
		req.getRequestDispatcher("/WEB-INF/pages/squares.jsp").forward(req, resp);
	}
	
	public static class Par {
		int broj;
		int vrijednost;
		
		public Par(int broj, int vrijednost) {
			super();
			this.broj = broj;
			this.vrijednost = vrijednost;
		}

		public int getBroj() {
			return broj;
		}

		public int getVrijednost() {
			return vrijednost;
		}
		
		
	}
	
}
