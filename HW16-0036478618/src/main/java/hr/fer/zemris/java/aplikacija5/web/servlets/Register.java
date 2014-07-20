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


@WebServlet("/servleti/register")
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		req.setAttribute("user", new BlogUser());
		req.setAttribute("errors", new Errors());
		req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String metoda = req.getParameter("metoda");
		if ("Odustani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/");
			return;
		}
		
		req.setCharacterEncoding("UTF-8");
		
		BlogUser user = getDataFromHttpRequest(req);
		Errors errors = new Errors();
		validateHttpRequest(user, errors, (String) req.getParameter("password"));
		
		if(errors.hasErrors()) {
			req.setAttribute("user", user);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
			return;
		}
		user.setPasswordHash(new Crypto(req.getParameter("password").toString()).calculateDigest().getDigestAsHexString());
		DAOProvider.getDAO().updateBlogUser(user);
		resp.sendRedirect(req.getServletContext().getContextPath() + "/");
	}

	private void validateHttpRequest(BlogUser user, Errors errors, String password) {
		if(password.equals("")) {
			errors.putErrorMessage("lozinka", "Molimo Vas unesite Vašu novu lozinku");
		}
		if(user.getFirstName().isEmpty()) {
			errors.putErrorMessage("ime", "Molimo Vas unesite Vaše ime.");
		}
		if(user.getLastName().isEmpty()) {
			errors.putErrorMessage("prezime", "Molimo Vas unesite Vaše prezime.");
		}
		if(user.getEmail().isEmpty()) {
			errors.putErrorMessage("email", "Molimo Vas unesite Vašu e-mail adresu.");
		}
		if(user.getNick().isEmpty()) {
			errors.putErrorMessage("nadimak", "Molimo Vas unesite Vaš nadimak.");
		}
		else {
			BlogUser tempUser = DAOProvider.getDAO().getBlogUser(user.getNick());
			if(tempUser != null) {
				errors.putErrorMessage("nadimak", "Postoji korisnik s danim nadimkom.");			
			}
		}
	}

	private BlogUser getDataFromHttpRequest(HttpServletRequest req) {
		BlogUser user = new BlogUser();
		user.setFirstName((String) req.getParameter("ime"));
		user.setLastName((String) req.getParameter("prezime"));
		user.setEmail((String) req.getParameter("email"));
		user.setNick((String) req.getParameter("nadimak"));
		return user;
	}
}
