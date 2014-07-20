package hr.fer.zemris.java.aplikacija5.model;

public class PollZapis {

	private long id;
	private String title;
	private String message;
	
	public PollZapis() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return id + " " + title + "\n";
	}
}
