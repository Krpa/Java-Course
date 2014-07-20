package hr.fer.zemris.java.aplikacija5.web.servlets;

import hr.fer.zemris.java.aplikacija5.dao.DAOProvider;
import hr.fer.zemris.java.aplikacija5.model.BlogComment;
import hr.fer.zemris.java.aplikacija5.model.BlogEntry;
import hr.fer.zemris.java.aplikacija5.model.BlogEntryForm;
import hr.fer.zemris.java.aplikacija5.model.BlogUser;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String url[] = req.getPathInfo().substring(1).split("/");
		
		if(url.length == 1) {
			BlogUser user = DAOProvider.getDAO().getBlogUser(url[0]);
			if(user == null) {
				req.setAttribute("pogreska", "Ne postoji dani korisnik: " + url[0]);
				req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
				return;
			}
			
			req.setAttribute("entries", DAOProvider.getDAO().getBlogEntries(user));
			req.setAttribute("authorNick", url[0]);
			req.setAttribute("authorFn", user.getFirstName());
			req.setAttribute("authorLn", user.getLastName());
			req.getRequestDispatcher("/WEB-INF/pages/Author.jsp").forward(req, resp);
			return;
		}
		
		if(url.length == 2) {
			BlogUser user = DAOProvider.getDAO().getBlogUser(url[0]);
			if(user == null) {
				req.setAttribute("pogreska", "Ne postoji dani korisnik: " + url[0]);
				req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
				return;
			}
			
			if("new".equals(url[1])) {
				if(!user.getId().equals(req.getSession().getAttribute("current.user.id"))) {
					req.setAttribute("pogreska", "Zabranjen pristup!");
					req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
					return;
				}
				
				BlogEntryForm form = new BlogEntryForm();
				BlogEntry entry = new BlogEntry();
				form.popuniIzBlogEntry(entry);
				req.setAttribute("form", form);
				req.getRequestDispatcher("/WEB-INF/pages/EntryFormular.jsp").forward(req, resp);
				return;
			} else if("edit".equals(url[1])) {
				if(!user.getId().equals(req.getSession().getAttribute("current.user.id"))) {
					req.setAttribute("pogreska", "Zabranjen pristup!");
					req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
					return;
				}
				
				Long id = null;
				try {
					id = Long.parseLong(req.getParameter("id"));
				} catch(NumberFormatException ex) {}
				if(id == null) {
					req.setAttribute("pogreska", "Zabranjen pristup!");
					req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
					return;
				}
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
				if(entry == null) {
					req.setAttribute("pogreska", "Zabranjen pristup!");
					req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
					return;
				}
				BlogEntryForm form = new BlogEntryForm();
				form.popuniIzBlogEntry(entry);
				req.setAttribute("id", entry.getId());
				req.setAttribute("form", form);
				req.getRequestDispatcher("/WEB-INF/pages/EntryFormular.jsp").forward(req, resp);
			} else {
				Long id = null;
				try {
					id = Long.parseLong(url[1]);
				} catch(NumberFormatException ex) {}
				if(id == null) {
					req.setAttribute("pogreska", "Zabranjen pristup!");
					req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
					return;
				}
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
				if(entry == null) {
					req.setAttribute("pogreska", "Zabranjen pristup!");
					req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
					return;
				}
				req.setAttribute("blogEntry", entry);
				req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
			}
		} else {
			req.setAttribute("pogreska", "Zabranjen pristup!");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(req.getParameter("comment") != null) {
			String[] params = req.getPathInfo().substring(1).split("/");
			
			BlogEntry blogEntry = new BlogEntry();
			blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(params[1]));
			BlogUser user = new BlogUser();
			user.setNick((String) req.getSession().getAttribute("current.user.nick"));
			user = DAOProvider.getDAO().getBlogUser(user.getNick());
			
			BlogComment comment = new BlogComment();
			comment.setMessage(req.getParameter("comment"));
			comment.setBlogEntry(blogEntry);
			comment.setUsersEMail(user.getNick());
			comment.setPostedOn(new Date());
			
			DAOProvider.getDAO().updateBlogComment(comment);
			
			req.setAttribute("pageTitle", blogEntry.getTitle());
			req.setAttribute("blogEntry", blogEntry);
			req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
			return;
		}
		
		BlogEntryForm form = new BlogEntryForm();
		form.popuniIzRequesta(req);
		form.check();
		
		if(form.imaPogreske()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/EntryFormular.jsp").forward(req, resp);
			return;
		} else {
			BlogEntry entry = new BlogEntry();
			form.popuniUBlogEntry(entry);
			BlogUser creator = DAOProvider.getDAO().getBlogUser(req.getSession().getAttribute("current.user.nick").toString());
			entry.setCreator(creator);
			entry.setCreatedAt(new Date());
			DAOProvider.getDAO().updateBlogEntry(entry);
			resp.sendRedirect(req.getContextPath() + "/servleti/author/" + creator.getNick() + "/" + entry.getId());
			return;
		}
	}
}
