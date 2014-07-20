package hr.fer.zemris.java.hw11.notepad;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private String key;
	
	public LocalizableAction(String key, final String description, final ILocalizationProvider lp) {
		super();
		this.key = key;
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, lp.getString(LocalizableAction.this.key));
				putValue(Action.SHORT_DESCRIPTION, lp.getString(description)); 
			}
		});
		
		putValue(NAME, lp.getString(key));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(description)); 
	}

}
