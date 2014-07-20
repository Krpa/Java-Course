package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.servlets.GlasanjeServlet.Zapis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GlasanjeGlasajServlet extends HttpServlet {

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String id = req.getParameter("id");
		File file = new File(fileName);
		
		FileOutputStream os = new FileOutputStream(file);
		StringBuilder sb = new StringBuilder();
		for(Zapis z : (List<Zapis>)req.getServletContext().getAttribute("bands")) {
			if(z.getId().equals(id)) {
				z.setBrojGlasova(z.getBrojGlasova()+1);
			}
			sb.append(z.getId()).append("\t").append(z.getBrojGlasova()).append("\n");
		}
		os.write(sb.toString().getBytes());
		os.flush();
		os.close();
		resp.sendRedirect(req.getContextPath()+"/glasanje-rezultati");
	}
}
