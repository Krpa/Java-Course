package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Ivan Krpelnik
 *
 */
public class RequestContext {

	
	private OutputStream outputStream;
	private Charset charset;
	
	private String encoding;
	private int statusCode;
	private String statusText;
	private String mimeType;
	private Map<String,String> parameters;
	private Map<String,String> temporaryParameters;
	private Map<String, String> persistentParameters;
	private List<RCCookie> outputCookies;
	
	private boolean headerGenerated;
	
	public RequestContext(OutputStream outputStream, Map<String, String> parameters, 
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		if(outputStream == null) {
			throw new IllegalArgumentException("Outputstream must not be null!");
		}
		
		this.outputStream = outputStream;
		this.parameters = (parameters == null) ? (new HashMap<String, String>()) : parameters;
		this.persistentParameters = (persistentParameters == null) ? (new HashMap<String, String>()) : persistentParameters;
		this.outputCookies = (outputCookies == null) ? (new ArrayList<RCCookie>()) : outputCookies;
		this.temporaryParameters = new HashMap<>();
		
		this.encoding = "UTF-8";
		this.statusCode = 200;
		this.statusText = "OK";
		this.mimeType = "text/html";
		this.headerGenerated = false;
	}
	
	
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	public Set<String> getPersistenParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			createHeader();
		}
		outputStream.write(data);
		outputStream.flush();
		return this;
	}
	
	public RequestContext write(String data) throws IOException {
		if(!headerGenerated) {
			createHeader();
		}
		outputStream.write(data.getBytes(charset));
		outputStream.flush();
		return this;
	}
	
	private void createHeader() throws IOException {
		charset = StandardCharsets.ISO_8859_1;
		outputStream.write("HTTP/1.1 ".getBytes(charset));
		outputStream.write(String.valueOf(statusCode).getBytes(charset));
		outputStream.write(" ".getBytes(charset));
		outputStream.write(statusText.getBytes(charset));
		outputStream.write("\r\n".getBytes(charset));
		
		outputStream.write("Content-Type: ".getBytes(charset));
		outputStream.write(mimeType.getBytes(charset));
		if(mimeType.startsWith("text/")) {
			outputStream.write(("; charset=" + Charset.forName(encoding)).getBytes(charset));
		}
		outputStream.write("\r\n".getBytes(charset));
		
		for(RCCookie cookie : outputCookies) {
			outputStream.write("Set-Cookie: ".getBytes(charset));
			outputStream.write((cookie.getName() + "=\"" + cookie.getValue() + "\"").getBytes(charset));
			if(cookie.getDomain() != null) {
				outputStream.write(("; Domain=" + cookie.getDomain()).getBytes(charset));
			}
			if(cookie.getPath() != null) {
				outputStream.write(("; Path=" + cookie.getPath()).getBytes(charset));
			}
			if(cookie.getMaxAge() != null) {
				outputStream.write(("; Max-Age=" + cookie.getMaxAge()).getBytes(charset));
			}
			outputStream.write("\r\n".getBytes(charset));
		}
		outputStream.write("\r\n".getBytes(charset));
		outputStream.flush();
		charset = Charset.forName(encoding);
		headerGenerated = true;
	}
	
	
	public void setStatusCode(int statusCode) {
		if(headerGenerated) {
			throw new RuntimeException("Header was already generated!");
		}
		this.statusCode = statusCode;
	}


	public void setStatusText(String statusText) {
		if(headerGenerated) {
			throw new RuntimeException("Header was already generated!");
		}
		this.statusText = statusText;
	}


	public void setEncoding(String encoding) {
		if(headerGenerated) {
			throw new RuntimeException("Header was already generated!");
		}
		this.encoding = encoding;
	}


	public void setMimeType(String mimeType) {
		if(headerGenerated) {
			throw new RuntimeException("Header was already generated!");
		}
		this.mimeType = mimeType;
	}


	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}


	public static class RCCookie {
		private String name;
		private String value;
		private String domain;
		private String path;
		private Integer maxAge;
		
		
		
		public RCCookie(String name, String value, Integer maxAge, String domain, 
				String path) {
			super();
			if(name == null || value == null) {
				throw new IllegalArgumentException();
			}
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		public String getDomain() {
			return domain;
		}
		
		public Integer getMaxAge() {
			return maxAge;
		}
		
		public String getName() {
			return name;
		}
		
		public String getPath() {
			return path;
		}
		
		public String getValue() {
			return value;
		}
	}
}
