package hr.fer.zemris.java.aplikacija5.servleti;

import hr.fer.zemris.java.aplikacija5.dao.DAOProvider;
import hr.fer.zemris.java.aplikacija5.model.Zapis;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GlasanjeGlasajServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Long id = null; 
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch(NumberFormatException ex) {
			req.getServletContext().setAttribute("pogreska", "Dogodila se pogreska, nevaljani id za zapis: " + req.getParameter("id"));
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		
		Zapis zapis = DAOProvider.getDao().dohvatiZapis(id);
		zapis.setBrojGlasova(zapis.getBrojGlasova()+1);
		DAOProvider.getDao().osvjeziBrojGlasova(zapis);
		req.getSession().setAttribute("pollID", zapis.getPollID());
		resp.sendRedirect(req.getContextPath()+"/servleti/glasanje-rezultati");
	}
}
