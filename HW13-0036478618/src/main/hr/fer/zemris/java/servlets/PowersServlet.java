package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@SuppressWarnings("serial")
public class PowersServlet extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Integer a = null;
		Integer b = null;
		Integer n = null;
	
		try {
			a = Integer.parseInt(req.getParameter("a"));
		} catch(NumberFormatException ex) {}
		
		try {
			b = Integer.parseInt(req.getParameter("b"));
		} catch(NumberFormatException ex) {}
		
		try {
			n = Integer.parseInt(req.getParameter("n"));
		} catch(NumberFormatException ex) {}
		
		if(a == null || b == null || n == null || 
				(a < -100 || a > 100) || (b < -100 || b > 100) || (n < 1 || n > 5)) {
			req.getSession().setAttribute("errMessage", "Invalid parameters for PowersServlet. "
					+ "Expected 3 numbers a and b from interval [-100,100] and n from interval [1,5].");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		resp.setContentType("application/octet.stream");
		resp.setHeader("Content-Disposition", "attachment;filename=powers.xls");
		createDocument(a, b, n).write(resp.getOutputStream());
	}
	
	private HSSFWorkbook createDocument(int start, int end, int power) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		for(int i = 1; i <= power; ++i) {
			HSSFSheet sheet = hwb.createSheet("Powers" + i);
			for(int j = start; j <= end; ++j) {
				HSSFRow row = sheet.createRow(j-start);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}
		return hwb;
	}
	
}
