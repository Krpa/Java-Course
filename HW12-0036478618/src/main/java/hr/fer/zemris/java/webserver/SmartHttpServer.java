package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author Ivan Krpelnik
 *
 */
public class SmartHttpServer {

	private String address;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String,String> mimeTypes = new HashMap<String, String>();
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	private Random sessionRandom = new Random();
	
	private String addressKey = "server.address";
	private String portKey = "server.port";
	private String workerThreadsKey = "server.workerThreads";
	private String documentRootKey = "server.documentRoot";
	private String mimeConfigKey = "server.mimeConfig";
	private String timeoutKey = "session.timeout";
	private String workersKey = "server.workers";
	
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Expected 1 argument: configFileName");
			return;
		}
		
		new SmartHttpServer(args[0]).start();
	}
	
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		
		try {
			properties.load(new FileReader(Paths.get(configFileName).toFile()));
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid config file: " + configFileName);
		}
		
		address = properties.getProperty(addressKey);
		port = Integer.parseInt(properties.getProperty(portKey));
		workerThreads = Integer.parseInt(properties.getProperty(workerThreadsKey));
		sessionTimeout = Integer.parseInt(properties.getProperty(timeoutKey));
		documentRoot = Paths.get(properties.getProperty(documentRootKey));
		
		Path mimeConfig = Paths.get(properties.getProperty(mimeConfigKey));
		Properties mimeProperties = new Properties();
		try {
			mimeProperties.load(new FileReader(mimeConfig.toFile()));
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid mime config file: " + mimeConfig);
		}
	
		for(Entry<Object, Object> entry : mimeProperties.entrySet()) {
			mimeTypes.put(entry.getKey().toString(), entry.getValue().toString());
		}
		
		Path workerConfig = Paths.get(properties.getProperty(workersKey));
		parseWorkersProperties(workerConfig);
	}
	
	
	private void parseWorkersProperties(Path workerConfig) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(workerConfig.toFile()));
			String redak;
			redak = reader.readLine();
			while(redak != null) {
				String[] splitted = redak.split(" = ");
				if(splitted.length != 2) {
					reader.close();
					throw new IllegalArgumentException("Illegal line in file: " + workerConfig + ", line: " + redak);
				}
				
				Class<?> referenceToClass;
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass(splitted[1]);
				} catch (ClassNotFoundException e) {
					reader.close();
					throw new IllegalArgumentException("Class " + splitted[1] + " does not exist!");
				}
				Object newObject;
				try {
					newObject = referenceToClass.newInstance();
				} catch (InstantiationException e) {
					reader.close();
					throw new IllegalArgumentException("Cannot instantiate class " + splitted[1] + "!");
				} catch (IllegalAccessException e) {
					reader.close();
					throw new IllegalArgumentException("Cannot access class " + splitted[1] + "!");
				} 
				IWebWorker iww = (IWebWorker)newObject;
				workersMap.put(splitted[0], iww);
				redak = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Error while reading " + workerConfig);
		}
	}
	
	protected synchronized void start() {
		if(serverThread == null) {
			serverThread = new ServerThread();
		}
		
		if(serverThread.isAlive()) {
			return;
		}
		
		serverThread.start();
		threadPool = Executors.newFixedThreadPool(workerThreads);
	}
	
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}
	
	
	private static class SessionMapEntry {
		String sid;
		long validUntil;
		Map<String,String> map;
		public SessionMapEntry(String sid, long validUntil) {
			super();
			this.sid = sid;
			this.validUntil = validUntil;
			map = new ConcurrentHashMap<>();
		}
		
		public String getSid() {
			return sid;
		}
		public void setSid(String sid) {
			this.sid = sid;
		}
		public long getValidUntil() {
			return validUntil;
		}
		public void setValidUntil(long validUntil) {
			this.validUntil = validUntil;
		}
		public Map<String, String> getMap() {
			return map;
		}
		public void setMap(Map<String, String> map) {
			this.map = map;
		}
	}

	protected class ServerThread extends Thread {
	
		@Override
		public void run() {
			
			try {
				ServerSocket ssocket = new ServerSocket(port);
				threadPool.submit(new CleanSessions());
				while(true) {
					Socket client = ssocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	private class CleanSessions implements Runnable {

		long lastTime;
		long waitTime = 300000;
		
		public CleanSessions() {
			lastTime = Calendar.getInstance().getTimeInMillis();
		}
		
		@Override
		public void run() {
			while(true) {
				if(Calendar.getInstance().getTimeInMillis() - lastTime < waitTime) {
					continue;
				}
				lastTime = Calendar.getInstance().getTimeInMillis();
				synchronized (address) {
					for(String name : sessions.keySet()) {
						SessionMapEntry entry = sessions.get(name);
						long currentTime = Calendar.getInstance().getTimeInMillis();
						if(entry.getValidUntil() < currentTime) {
							sessions.remove(name);
						}
					}
				}
			}
		}
		
	}
	
	private class ClientWorker implements Runnable {
		private Socket csocket;
		private PushbackInputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private Map<String,String> params = new HashMap<String, String>();
		private Map<String,String> permPrams = null;
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		private String SID;
		private String defaultMimeType = "application/octet-stream";
		private String defaultWorkerPackage = "hr.fer.zemris.java.webserver.workers";
		
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}
		
		@Override
		public void run() {
			System.out.println("run");
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
			} catch (IOException e) {
				closeClient();
				throw new RuntimeException("Error loading istream/ostream.");
			}
			
			System.out.println("loaded streams");
			List<String> request = readRequest();
			System.out.println(request);
			

			checkSession(request);
			
			RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
			rc.setStatusCode(200);
			
			if(request.isEmpty()) {
				returnError(rc, 400);
				return;
			}
			
			
			String firstLine = request.get(0);
			String[] paramStrings = firstLine.split(" ");
			if(paramStrings.length != 3) {
				returnError(rc, 400);
				return;
			}
			String method = paramStrings[0];
			String requestedPath = paramStrings[1];
			String version = paramStrings[2];
			
			if(!"GET".equalsIgnoreCase(method) || !"HTTP/1.0".equals(version) && !"HTTP/1.1".equals(version)) {
				returnError(rc, 400);
				return;
			}
			
			int indexOfParams = requestedPath.indexOf("?");
			String path = requestedPath;
			if(indexOfParams != -1) {
				path = requestedPath.substring(0, indexOfParams);
				parseParameters(requestedPath.substring(indexOfParams+1));
			}
			
			if(path.startsWith("/ext/")) {
				String className = path.substring(5);
				
				Class<?> referenceToClass;
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass(defaultWorkerPackage + "." + className);
				} catch (ClassNotFoundException e) {
					returnError(rc, 404);
					closeClient();
					return;
				}
				Object newObject;
				try {
					newObject = referenceToClass.newInstance();
				} catch (InstantiationException e) {
					returnError(rc, 400);
					closeClient();
					return;
				} catch (IllegalAccessException e) {
					returnError(rc, 400);
					closeClient();
					return;
				} 
				IWebWorker iww = (IWebWorker)newObject;
				iww.processRequest(rc);
				closeClient();
				return;
			}
			
			if(workersMap.containsKey(path)) {
				workersMap.get(path).processRequest(rc);
				closeClient();
				return;
			}
			
			Path resolvedPath;
			try {
				resolvedPath = documentRoot.resolve("." + path);
			} catch (InvalidPathException e) {
				returnError(rc, 403);
				return;
			}
			
			File requestedFile = resolvedPath.toFile();
			
			if(!requestedFile.exists() || !requestedFile.isFile() || !requestedFile.canRead()) {
				returnError(rc, 404);
				return;
			}
			
			int dotIndex = path.lastIndexOf(".");
			String extension = "";
			if(dotIndex != -1) {
				extension = path.substring(dotIndex+1);
			} else {
				returnError(rc, 404);
				return;
			}
			
			if("smscr".equals(extension)) {
				String docBody;
				try {
					docBody = new String (Files.readAllBytes(resolvedPath), "UTF-8");
				} catch (IOException e) {
					closeClient();
					throw new RuntimeException("Error while reading file: " + resolvedPath);
				}
			
				try {
					new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(), rc).execute();
				} catch (RuntimeException e) {
					closeClient();
					throw new RuntimeException("Error in file: " + resolvedPath, e);
				}
				closeClient();
				return;
			}
			
			String mimeType = defaultMimeType;
			if(mimeTypes.containsKey(extension)) {
				mimeType = mimeTypes.get(extension);
			}
			
			rc.setMimeType(mimeType);
			byte[] data;
			try {
				data = Files.readAllBytes(resolvedPath);
			} catch (IOException e) {
				closeClient();
				throw new RuntimeException("Error while reading file: " + resolvedPath);
			}
			
			try {
				rc.write(data);
			} catch (IOException e) {
				closeClient();
				throw new RuntimeException("Error while writing to ostream.");
			}
			
			closeClient();
		}

		private void checkSession(List<String> request) {
			String sidCandidate = null;
			
			for(String string : request) {
				if(!string.startsWith("Cookie:")) {
					continue;
				}
				String[] cookies = string.substring(7).split(";");
				for(int i = 0; i < cookies.length; ++i) {
					String[] cookie = cookies[i].split("=");
					String name = cookie[0].trim();
					String value = cookie[1].trim().replaceAll("\"", "");
					if("sid".equals(name)) {
						System.out.println("Found sid candidate!");
						sidCandidate = value;
					}
				}
			}
			
			synchronized(address) {
				if(sidCandidate == null) {
					createSID();
				} else {
					long currentTime = Calendar.getInstance().getTimeInMillis();
					System.out.println(sidCandidate);
					SessionMapEntry entry = sessions.get(sidCandidate);
					if(entry == null || entry.getValidUntil() < currentTime) {
						sessions.remove(entry);
						createSID();
					} else {
						entry.setValidUntil(currentTime+sessionTimeout);
						permPrams = entry.getMap();
					}
				}
			}
		}

		private void createSID() {
			String sid = "";
			
			for(int i = 0; i < 20; ++i) {
				sid += "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(sessionRandom.nextInt(26));
			}
			
			System.out.println("Generated SID: " + sid);
			
			long validUntil = Calendar.getInstance().getTimeInMillis()+sessionTimeout;
			SessionMapEntry entry = new SessionMapEntry(sid, validUntil);
			sessions.put(sid, entry);
			outputCookies.add(new RCCookie("sid", sid, sessionTimeout, address, "/"));
			permPrams = entry.getMap();
		}

		private void parseParameters(String parameters) {
			try {
				String[] strings = parameters.split("&");
				for(String string : strings) {
					String[] splitted = string.split("=");
					params.put(splitted[0], splitted[1]);
				}
			} catch(RuntimeException e) {
				throw new RuntimeException("Invalid parameters: " + params);
			}
		}

		private void returnError(RequestContext rc, int err) {
			try {
				rc.setStatusCode(err);
				rc.write(getStatusString(err));
			} catch (IOException e) {
				throw new RuntimeException("Error while writing to ostream.");
			}
			
			closeClient();
		}
		
		private String getStatusString(int err) {
			System.out.println("error " + err);
			switch(err) {
				case 400: return "HTTP/1.1 400 Bad Request\r\n";
				case 403: return "HTTP/1.1 403 Forbidden\r\n";
				case 404: return "HTTP/1.1 404 Not Found\r\n";
			}
			return "HTTP/1.1 " + err + " OK\r\n";
		}
		
		private void closeClient() {
			try {
				csocket.close();
			} catch (IOException e) {
				throw new RuntimeException("Could not close client socket.");
			}
		}

		private List<String> readRequest() {
			List<String> lista = new ArrayList<String>();

			Scanner inputScanner = new Scanner(istream);
			while(inputScanner.hasNextLine()) {
				String line = inputScanner.nextLine();
				if(line.isEmpty()) {
					break;
				}
				lista.add(line);
			}
			return lista;
		}
		
	}


}
