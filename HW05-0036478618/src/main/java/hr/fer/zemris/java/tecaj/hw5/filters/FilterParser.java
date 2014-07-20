package hr.fer.zemris.java.tecaj.hw5.filters;

public final class FilterParser {

	private FilterParser() {
		super();
	}
	
	public static FilterBasic parse(String s) {
		if(s.matches(FilterE.REGEX)) {
			return new FilterE(s.charAt(0) == '-');
		}
		if(s.matches(FilterF.REGEX)) {
			return new FilterF(s.charAt(0) == '-');
		}
		if(s.matches(FilterL.REGEX)) {
			return new FilterL(Integer.parseInt(s.substring(s.indexOf('l')+1)), s.charAt(0) == '-');
		}
		if(s.matches(FilterS.REGEX)) {
			return new FilterS(Integer.parseInt(s.substring(s.indexOf('s')+1)), s.charAt(0) == '-');
		}
		return null;
	}
}
