package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/save")
public class Save extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);
	}
	
	private void obradi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getSession().getAttribute("current.user") == null) {
			req.setAttribute("poruka", "Nije dozvoljen pristup anonimnim korisnicima.");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		
		req.setCharacterEncoding("UTF-8");
		
		String metoda = req.getParameter("metoda");
		if(!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath()+"/listaj");
			return;
		}

		String filename = req.getServletContext().getRealPath("/WEB-INF/baza");
		RadioniceBaza baza = RadioniceBaza.ucitaj(filename);
		RadionicaForm f = new RadionicaForm();
		f.popuniIzHttpRequesta(req);
		if(req.getParameter("id") != null) {
			f.setId(req.getParameter("id").toString());
		}
		f.validiraj();
		
		if(f.imaPogresaka()) {
			req.setAttribute("zapis", f);
			req.setAttribute("oprema", baza.getOprema());
			req.setAttribute("trajanje", baza.getTrajanja());
			req.setAttribute("publika", baza.getPublika());
			req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
			return;
		}
		
		Radionica r = new Radionica();
		f.popuniURadionicu(r, filename);
		
		
		baza.zapamti(r);
		baza.snimi(filename);
		
		resp.sendRedirect(req.getServletContext().getContextPath()+"/listaj");
	}
	
}
