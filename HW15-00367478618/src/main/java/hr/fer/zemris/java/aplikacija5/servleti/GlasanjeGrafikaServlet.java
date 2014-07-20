package hr.fer.zemris.java.aplikacija5.servleti;

import hr.fer.zemris.java.aplikacija5.model.Zapis;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

@SuppressWarnings("serial")
public class GlasanjeGrafikaServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		@SuppressWarnings("unchecked")
		JFreeChart chart = createChart(createDataset((List<Zapis>)req.getServletContext().getAttribute("rezultati")), 
				"Rezultati glasanja u grafičkom obliku:");
		resp.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 400, 400);
	}
	
	
	private  PieDataset createDataset(List<Zapis> rezultati) {
        DefaultPieDataset result = new DefaultPieDataset();
        for(Zapis rez : rezultati) {
        	result.setValue(rez.getIme(), rez.getBrojGlasova());
        }
        return result;
        
    }
	
	private JFreeChart createChart(PieDataset dataset, String title) {
        
        JFreeChart chart =
        		ChartFactory.createPieChart3D(
        									title,
        									dataset,
								            true,
								            true,
								            false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
	
}
