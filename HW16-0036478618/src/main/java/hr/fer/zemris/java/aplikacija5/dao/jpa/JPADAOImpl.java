package hr.fer.zemris.java.aplikacija5.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.aplikacija5.dao.DAO;
import hr.fer.zemris.java.aplikacija5.dao.DAOException;
import hr.fer.zemris.java.aplikacija5.model.BlogComment;
import hr.fer.zemris.java.aplikacija5.model.BlogEntry;
import hr.fer.zemris.java.aplikacija5.model.BlogUser;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}
	
	@Override
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = 
				(List<BlogEntry>) em.createQuery("select b from BlogEntry as b where b.creator=:creator")
					.setParameter("creator", user)
					.getResultList();
		
		return entries;
	}
	
	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		@SuppressWarnings("unchecked")
		List<BlogUser> users = 
				(List<BlogUser>) em.createQuery("select b from BlogUser as b where b.nick=:nick")
					.setParameter("nick", nick)
					.getResultList();
		
		if(users.size() == 0) return null;
		return users.get(0);
	}
	
	@Override
	public void updateBlogUser(BlogUser user) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		if(user.getId() != null) {
			em.merge(user);
		} else {
			em.persist(user);
		}
		
		em.getTransaction().commit();
	}
	
	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		@SuppressWarnings("unchecked")
		List<BlogUser> users = 
				(List<BlogUser>) em.createQuery("select b from BlogUser as b")
					.getResultList();
		return users;
	}
	
	@Override
	public void updateBlogEntry(BlogEntry entry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		if(entry.getId() != null) {
			em.merge(entry);
		} else {
			em.persist(entry);
		}
		
		em.getTransaction().commit();
	}
	
	@Override
	public void updateBlogComment(BlogComment comment) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(comment);
		em.getTransaction().commit();
	}
}