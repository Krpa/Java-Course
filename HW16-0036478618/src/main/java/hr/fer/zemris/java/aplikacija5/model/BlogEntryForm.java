package hr.fer.zemris.java.aplikacija5.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class BlogEntryForm {

	private String id;
	private String title;
	private String text;
	private Map<String, String> pogreske;
	
	public BlogEntryForm() {
		pogreske = new HashMap<String, String>();
	}
	
	public void popuniIzRequesta(HttpServletRequest req) {
		title = pripremi(req.getParameter("title"));
		text = pripremi(req.getParameter("text"));
		id = pripremi(req.getParameter("id"));
	}
	
	public void popuniIzBlogEntry(BlogEntry entry) {
		title = pripremi(entry.getTitle());
		text = pripremi(entry.getText());
		id = pripremi(entry.getId());
	}
	
	public void popuniUBlogEntry(BlogEntry entry) {
		entry.setTitle(title);
		entry.setText(text);
		if(id != null && !id.isEmpty()) {
			entry.setId(Long.parseLong(id));
		}
	}
	
	public boolean imaPogreske() {
		return !pogreske.isEmpty();
	}
	
	public boolean imaPogresku(String s) {
		return pogreske.containsKey(s);
	}
	
	public String dohvatiPogresku(String s) {
		return pogreske.get(s);
	}
	
	public void check() {
		pogreske = new HashMap<>();
		if(title.isEmpty()) {
			pogreske.put("title", "Naslov ne smije biti prazan!");
		}
		if(text.isEmpty()) {
			pogreske.put("text", "Tekst ne smije biti prazan!");
		}
		if(id != null && !id.isEmpty()) {
			Long temp = null;
			try {
				temp = Long.parseLong(id);
			} catch(NumberFormatException ex) {}
			if(temp == null) {
				pogreske.put("id", "ID mora biti broj!");
			}
		}
	}
	
	
	private String pripremi(Object o) {
		if(o == null) {
			return "";
		} else {
			return o.toString();
		}
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
