package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SetColorServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String color = req.getParameter("color");
		req.getSession().setAttribute("currentColor", color);
		req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);;
	}
	
}
