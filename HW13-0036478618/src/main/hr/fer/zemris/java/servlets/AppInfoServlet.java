package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AppInfoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		long time = (System.currentTimeMillis() - 
						(long) req.getServletContext().getAttribute("startTime"));
		long milliseconds = time % 1000;
		time /= 1000;
		long days = time / 86400;
		time %= 86400;
		long hours = time / 3600;
		time %= 3600;
		long minutes = time / 60;
		time %= 60;
		long seconds = time;
		
		String uptime = days + " days " + hours + " hours " + minutes + " minutes " +
						seconds + " seconds and " + milliseconds + " milliseconds";
		req.setAttribute("uptime", uptime);
		req.getRequestDispatcher("/WEB-INF/pages/appinfo.jsp").forward(req, resp);
	}	
	
}
