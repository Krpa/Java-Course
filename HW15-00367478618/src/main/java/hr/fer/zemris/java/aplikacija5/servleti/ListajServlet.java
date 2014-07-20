package hr.fer.zemris.java.aplikacija5.servleti;

import hr.fer.zemris.java.aplikacija5.dao.DAO;
import hr.fer.zemris.java.aplikacija5.dao.DAOProvider;
import hr.fer.zemris.java.aplikacija5.model.PollZapis;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ListajServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DAO dao = DAOProvider.getDao();
		List<PollZapis> zapisi = dao.dohvatiAnkete();
		req.getServletContext().setAttribute("ankete", zapisi);
		req.getRequestDispatcher("/WEB-INF/pages/Listaj.jsp").forward(req, resp);
	}
}
