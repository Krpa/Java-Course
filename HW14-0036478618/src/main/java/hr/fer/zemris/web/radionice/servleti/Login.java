package hr.fer.zemris.web.radionice.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
@SuppressWarnings("serial")
public class Login extends HttpServlet {

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
	
	
	private void obradi(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String metoda = req.getParameter("metoda_login");
		if("Odustani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath()+"/listaj");
			return;
		}
		
		String username = req.getParameter("username");
		if(username != null) {
			User korisnik = provjeri(username, req.getParameter("password"));
			if(korisnik != null) {
				req.getSession().setAttribute("current.user", korisnik);
				resp.sendRedirect(req.getServletContext().getContextPath()+"/listaj");
			} else {
				req.setAttribute("pogreska", "Username ili password je pogrešan!");
				req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req, resp);
			}
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req, resp);
		}
	}


	private User provjeri(String username, String password) {
		if("aante".equals(username) && "tajna".equals(password)) {
			return new User(username, password, "Ante", "Anić");
		}
		return null;
	}
}
