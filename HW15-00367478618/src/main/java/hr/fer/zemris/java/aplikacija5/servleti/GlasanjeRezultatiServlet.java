package hr.fer.zemris.java.aplikacija5.servleti;

import hr.fer.zemris.java.aplikacija5.dao.DAOProvider;
import hr.fer.zemris.java.aplikacija5.model.Zapis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GlasanjeRezultatiServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Long id = null;
		try {
			id = Long.parseLong(req.getSession().getAttribute("pollID").toString());
		} catch(NumberFormatException ex) {
			req.getServletContext().setAttribute("pogreska", "Dogodila se pogreska, nevaljani id za anketu: " + req.getParameter("pollID"));
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		
		List<Zapis> lista = DAOProvider.getDao().dohvatiPoBrojuGlasova(id);
		List<Zapis> primjerci = new ArrayList<>();
		long maxVotes = lista.get(0).getBrojGlasova();
		for(Zapis z : lista) {
			if(maxVotes == z.getBrojGlasova()) {
				primjerci.add(z);
			} else {
				break;
			}
		}
		
		req.getServletContext().setAttribute("rezultati", lista);
		req.getServletContext().setAttribute("primjerci", primjerci);
		req.getRequestDispatcher("/WEB-INF/pages/GlasanjeRez.jsp").forward(req, resp);
	}
	
}
