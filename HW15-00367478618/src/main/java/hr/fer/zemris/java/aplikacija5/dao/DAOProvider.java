package hr.fer.zemris.java.aplikacija5.dao;

import hr.fer.zemris.java.aplikacija5.dao.sql.SQLDAO;

public class DAOProvider {

	private static DAO dao = new SQLDAO();
	
	public static DAO getDao() {
		return dao;
	}
	
}
