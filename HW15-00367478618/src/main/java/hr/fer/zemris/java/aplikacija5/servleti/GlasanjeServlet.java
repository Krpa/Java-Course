package hr.fer.zemris.java.aplikacija5.servleti;

import hr.fer.zemris.java.aplikacija5.dao.DAO;
import hr.fer.zemris.java.aplikacija5.dao.DAOProvider;
import hr.fer.zemris.java.aplikacija5.model.PollZapis;
import hr.fer.zemris.java.aplikacija5.model.Zapis;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GlasanjeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		DAO dao = DAOProvider.getDao();
		Long id = null;
		try {
			id = Long.parseLong(req.getParameter("pollID"));
		} catch(NumberFormatException ex) {
			req.getServletContext().setAttribute("pogreska", "Dogodila se pogreska, nevaljani id za anketu: " + req.getParameter("pollID"));
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		PollZapis zapis = dao.dohvatiPoll(id);
		req.getServletContext().setAttribute("title", zapis.getTitle());
		req.getServletContext().setAttribute("message", zapis.getMessage());
		List<Zapis> lista = dao.dohvatiZapise(id);
		Collections.sort(lista);
		req.getServletContext().setAttribute("zapisi", lista);
		req.getRequestDispatcher("/WEB-INF/pages/GlasanjeIndex.jsp").forward(req, resp);
	}
}
