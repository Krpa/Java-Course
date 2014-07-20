package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Listaj extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		RadioniceBaza baza = RadioniceBaza.ucitaj(
				req.getServletContext().getRealPath("/WEB-INF/baza"));
		
		List<Radionica> zapisi = baza.getRadionice();
		System.out.println(zapisi);
		req.setAttribute("zapisi", zapisi);
		
		req.getRequestDispatcher("/WEB-INF/pages/Listaj.jsp").forward(req, resp);
	}
}
