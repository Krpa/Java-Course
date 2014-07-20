package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class StoryServlet extends HttpServlet {

	public static final String colors[] = {"BlueViolet", "Crimson", "DarkGoldenRod", 
											"DeepSkyBlue", "GreenYellow", "Plum",
											"SpringGreen", "Turquoise", "Violet"};
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getSession().setAttribute("storyColor", colors[new Random().nextInt(colors.length)]);
		req.getRequestDispatcher("/WEB-INF/pages/funny.jsp").forward(req, resp);
	}
	
}
