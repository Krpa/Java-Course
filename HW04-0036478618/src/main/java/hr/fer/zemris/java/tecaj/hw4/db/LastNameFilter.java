package hr.fer.zemris.java.tecaj.hw4.db;


public class LastNameFilter implements IFilter {

	String mask;
	String prefix;
	String suffix;
	
	public LastNameFilter(String mask) {
		this.mask = mask;
		prefix = getPrefix();
		suffix = getSuffix();
	}
	
	private String getPrefix() {
		StringBuilder prefix = new StringBuilder();
		int length = mask.length();
		for(int i = 0; i < length && mask.charAt(i) != '*'; ++i) {
			prefix.append(mask.charAt(i));
		}
		return prefix.toString();
	}
	
	private String getSuffix() {
		StringBuilder suffix = new StringBuilder();
		int length = mask.length();
		for(int i = length - 1; i >= 0 && mask.charAt(i) != '*'; --i) {
			suffix.append(mask.charAt(i)); 
		}
		return suffix.reverse().toString();
	}

	@Override
	public boolean accepts(StudentRecord record) {
		StringBuilder lastName = new StringBuilder();
		for(String s : record.getPrezimena()) {
			lastName.append(s);
		}
		String lastNameStr = lastName.toString();
		if(lastNameStr.startsWith(prefix) && lastNameStr.endsWith(suffix)) {
			return true;
		}
		return false;
	}

}
