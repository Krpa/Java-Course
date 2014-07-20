package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;

public class EchoWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		
		context.setMimeType("text/html");
		
		try {
			context.write("<html><body><table border = 1>");
			context.write("<tr><th>Name</th><th>Value</th></tr>");
			for(String name : context.getParameterNames()) {
				context.write("<tr><td>" + name + "</td>" + "<td>"+context.getParameter(name)+"</td></tr>");
			}
			context.write("</table></body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
