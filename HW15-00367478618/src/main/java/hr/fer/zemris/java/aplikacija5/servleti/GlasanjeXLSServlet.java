package hr.fer.zemris.java.aplikacija5.servleti;

import hr.fer.zemris.java.aplikacija5.model.Zapis;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@SuppressWarnings("serial")
public class GlasanjeXLSServlet extends HttpServlet {

	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("application/octet.stream");
		resp.setHeader("Content-Disposition", "attachment;filename=rezultati.xls");
		createDocument((List<Zapis>)req.getServletContext().getAttribute("rezultati")).write(resp.getOutputStream());
	}
	
	
	private HSSFWorkbook createDocument(List<Zapis> rezultati) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Rezultati");
		int i = 0;
		for(Zapis rezultat : rezultati) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(rezultat.getIme());
			row.createCell(1).setCellValue(rezultat.getBrojGlasova());
			i++;
		}
		return hwb;
	}
}
