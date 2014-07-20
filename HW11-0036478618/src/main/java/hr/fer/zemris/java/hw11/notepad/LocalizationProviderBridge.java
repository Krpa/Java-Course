package hr.fer.zemris.java.hw11.notepad;

public class LocalizationProviderBridge extends AbstractLocalizationProvider
		implements ILocalizationProvider {

	private ILocalizationProvider parent;
	private ILocalizationListener listener;
	private boolean connected;
	
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		super();
		this.parent = parent;
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	public void connect() {
		parent.addLocalizationListener(listener);
		connected = true;
	}

	public void disconnect() {
		parent.removeLocalizationListener(listener);
		connected = false;
	}
	
	public boolean isConnected() {
		return connected;
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
