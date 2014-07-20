package hr.fer.zemris.java.hw11.notepad;

import javax.swing.JMenu;

public class LocalizableMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	
	private String key;
	
	public LocalizableMenu(String key, final ILocalizationProvider lp) {
		super();
		this.key = key;
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(lp.getString(LocalizableMenu.this.key));
			}
		});

		setText(lp.getString(LocalizableMenu.this.key));
		
	}
}
