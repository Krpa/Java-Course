package hr.fer.zemris.java.hw11.notepad;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

	private final static LocalizationProvider PROVIDER = new LocalizationProvider();
	
	private String language;
	private ResourceBundle bundle;
	
	
	private LocalizationProvider() {
		language = "hr";
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.notepad.prijevodi", Locale.forLanguageTag(language));
	}
	
	public static LocalizationProvider getInstance() {
		return PROVIDER;
	}
	
	
	
	public void setLanguage(String language) {
		this.language = language;
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.notepad.prijevodi", Locale.forLanguageTag(language));
		fire();
	}

	@Override
	public String getString(String key) {
		try {
			return new String(bundle.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

}
