package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.demo.DemoRequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestContextTest {

	@Test(expected=IllegalArgumentException.class)
	public void constructorTest() {
		new RequestContext(null, null, null, null);
	}
	
	@Test
	public void constructorTest2() {
		new RequestContext(System.out, null, null, null);
	}
	
	@Test
	public void paramTest() {
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("bla", "asdf");
		RequestContext rc = new RequestContext(System.out, mapa, null, null);
		assertEquals("asdf", rc.getParameter("bla"));
		int i = 0;
		for(String name : rc.getParameterNames()) {
			assertEquals("bla", name);
			i++;
		}
		assertEquals(1, i);
	}
	
	@Test
	public void pparamTest() {
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("bla", "asdf");
		RequestContext rc = new RequestContext(System.out, null, mapa, null);
		assertEquals("asdf", rc.getPersistentParameter("bla"));
		int i = 0;
		for(String name : rc.getPersistenParameterNames()) {
			assertEquals("bla", name);
			i++;
		}
		assertEquals(1, i);
		rc.removePersistentParameter("bla");
		assertEquals(true, rc.getPersistenParameterNames().isEmpty());
		rc.setPersistentParameter("asdf", "bla");
		assertEquals("bla", rc.getPersistentParameter("asdf"));
		i = 0;
		for(String name : rc.getPersistenParameterNames()) {
			assertEquals("asdf", name);
			i++;
		}
		assertEquals(1, i);
	}
	
	@Test
	public void tparamTest() {
		RequestContext rc = new RequestContext(System.out, null, null, null);
		rc.setTemporaryParameter("bla", "asdf");
		assertEquals("asdf", rc.getTemporaryParameter("bla"));
		int i = 0;
		for(String name : rc.getTemporaryParameterNames()) {
			assertEquals("bla", name);
			i++;
		}
		assertEquals(1, i);
		rc.removeTemporaryParameter("bla");;
		assertEquals(true, rc.getTemporaryParameterNames().isEmpty());
	}
	
	@Test
	public void demo1() throws IOException {
		String filePath = "primjer1.txt";
		String encoding = "ISO-8859-2";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), 
		new HashMap<String, String>(), 
		new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
	
	@Test
	public void demo2() throws IOException {
		String filePath = "primjer2.txt";
		String encoding = "UTF-8";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), 
		new HashMap<String, String>(), 
		new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
	
	@Test
	public void demo3() throws IOException {
		String filePath = "primjer3.txt";
		String encoding = "UTF-8";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), 
		new HashMap<String, String>(), 
		new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}
