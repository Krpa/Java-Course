package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/edit")
public class Edit extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(req.getSession().getAttribute("current.user") == null) {
			req.setAttribute("poruka", "Nije dozvoljen pristup anonimnim korisnicima.");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		
		Long id = null;
		try {
			id = Long.valueOf(req.getParameter("id"));
		} catch(Exception ex) {
			req.setAttribute("poruka", "Primljeni su neispravni parametri.");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		
		RadioniceBaza baza = RadioniceBaza.ucitaj(
			req.getServletContext().getRealPath("/WEB-INF/baza")
		);
		
		Radionica r = baza.dohvatiRadionicu(id);
		
		if(r == null) {
			req.setAttribute("poruka", "Traženi zapis ne postoji.");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		
		RadionicaForm f = new RadionicaForm();
		f.popuniIzRadionice(r);
		
		req.setAttribute("id", req.getParameter("id"));
		req.setAttribute("zapis", f);
		req.setAttribute("oprema", baza.getOprema());
		req.setAttribute("trajanje", baza.getTrajanja());
		req.setAttribute("publika", baza.getPublika());
		
		req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
	}

}
