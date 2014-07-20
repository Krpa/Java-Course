package hr.fer.zemris.java.aplikacija5.dao;

import hr.fer.zemris.java.aplikacija5.model.BlogComment;
import hr.fer.zemris.java.aplikacija5.model.BlogEntry;
import hr.fer.zemris.java.aplikacija5.model.BlogUser;

import java.util.List;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException;
	
	public BlogUser getBlogUser(String nick) throws DAOException;
	
	public void updateBlogUser(BlogUser user) throws DAOException;
	
	public List<BlogUser> getBlogUsers() throws DAOException;

	public void updateBlogEntry(BlogEntry entry) throws DAOException;
	
	public void updateBlogComment(BlogComment comment) throws DAOException;
}