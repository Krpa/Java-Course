package hr.fer.zemris.java.hw11.notepad;

import javax.swing.JToolBar;

public class LocalizableToolbar extends JToolBar {

	private static final long serialVersionUID = 1L;
	
	private String key;
	
	public LocalizableToolbar(String key, final ILocalizationProvider lp) {
		super();
		this.key = key;
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setName(lp.getString(LocalizableToolbar.this.key));
			}
		});

		setName(lp.getString(LocalizableToolbar.this.key));
		
	}
}
