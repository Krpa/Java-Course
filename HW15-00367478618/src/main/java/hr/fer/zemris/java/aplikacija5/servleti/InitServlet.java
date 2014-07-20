package hr.fer.zemris.java.aplikacija5.servleti;

import hr.fer.zemris.java.aplikacija5.dao.DAO;
import hr.fer.zemris.java.aplikacija5.dao.DAOProvider;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		DAO dao = DAOProvider.getDao();
		dao.initPolls();
		resp.sendRedirect(req.getServletContext().getContextPath()+"/servleti/index");
	}
}
