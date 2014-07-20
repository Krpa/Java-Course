package hr.fer.zemris.java.aplikacija5.web.servlets;

import hr.fer.zemris.java.aplikacija5.dao.DAOProvider;
import hr.fer.zemris.java.aplikacija5.hashing.Crypto;
import hr.fer.zemris.java.aplikacija5.model.BlogUser;
import hr.fer.zemris.java.aplikacija5.model.Errors;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("username", "");
		req.setAttribute("password", "");
		req.setAttribute("errors", new Errors());
		req.setAttribute("users", DAOProvider.getDAO().getBlogUsers());
		req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String metoda = req.getParameter("metoda");
		req.setAttribute("users", DAOProvider.getDAO().getBlogUsers());
		if ("Odustani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/");
			return;
		}
		BlogUser reqUser = new BlogUser();
		reqUser.setNick(req.getParameter("username"));
		reqUser.setPasswordHash(new Crypto(req.getParameter("password").toString()).calculateDigest().getDigestAsHexString());
		BlogUser user = DAOProvider.getDAO().getBlogUser(reqUser.getNick());
		if(user == null) {
			Errors errors = new Errors();
			errors.putErrorMessage("loginError", "Ne postoji dani username!");
			req.setAttribute("errors", errors);
			req.setAttribute("username", reqUser.getNick());
			req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);
			return;
		}
		System.out.println(user.getPasswordHash());
		System.out.println(reqUser.getPasswordHash());
		if(!user.getPasswordHash().equals(reqUser.getPasswordHash())) {
			Errors errors = new Errors();
			errors.putErrorMessage("loginError", "Username i password se ne poklapaju!");
			req.setAttribute("errors", errors);
			req.setAttribute("username", reqUser.getNick());
			req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);
			return;
		}
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);
	}
}
