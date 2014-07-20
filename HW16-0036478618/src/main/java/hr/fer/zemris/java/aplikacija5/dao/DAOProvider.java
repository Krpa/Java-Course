package hr.fer.zemris.java.aplikacija5.dao;

import hr.fer.zemris.java.aplikacija5.dao.jpa.JPADAOImpl;

public class DAOProvider {

	private static DAO dao = new JPADAOImpl();
	
	public static DAO getDAO() {
		return dao;
	}
	
}