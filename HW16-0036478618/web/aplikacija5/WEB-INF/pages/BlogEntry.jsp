<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="hr.fer.zemris.java.aplikacija5.model.BlogEntry,hr.fer.zemris.java.aplikacija5.model.BlogComment"%>
<%@page import="java.util.List"%>
<%
  BlogEntry blogEntry = (BlogEntry)request.getAttribute("blogEntry");
%>
<html>

<p><a href="<%= request.getContextPath() %>/servleti/main">Main</a></p>
  <body>
	<c:choose>	
		<c:when test='<%=session.getAttribute("current.user.id") == null%>'>
		<h3>Anonimni korisnik</h3> 
		</c:when>
		<c:otherwise><h3> <%=session.getAttribute("current.user.nick")%>  | <a href="<%= request.getContextPath() %>/servleti/logout">logout</a></h3></c:otherwise>
	</c:choose>

  <% if(blogEntry==null) { %>
    Nema unosa.
  <% } else { %>
    <h1><%= blogEntry.getTitle() %></h1>
    <p><%= blogEntry.getText() %></p>
    <%
 		if(blogEntry.getCreator().getNick().equals(session.getAttribute("current.user.nick"))) {%>
 	<%= "<a href=\"" + request.getContextPath() + "/servleti/author/" + request.getSession().getAttribute("current.user.nick") + "/edit?id=" + blogEntry.getId() + "\">Izmjeni</a>" %>
 	<% } %>
    <% if(!blogEntry.getComments().isEmpty()) { %>
    <ul>
    <% for(BlogComment c : blogEntry.getComments()) { %>
    <li><div style="font-weight: bold">[Korisnik=<%= c.getUsersEMail() %>] <%= c.getPostedOn() %></div><div style="padding-left: 10px;"><%= c.getMessage() %></div></li>
    <% } %>  
    </ul>
    <% } %>
  <% } %>
  
  	
 	
 	<%
 		if(session.getAttribute("current.user.nick") != null) { 
 	%>
 	<form action="<%= request.getContextPath() %>/servleti/author/<%= blogEntry.getCreator().getNick() %>/<%= blogEntry.getId() %>" method="post">
			Komentar: <input type="text" id="comment" name="comment" /><br />
			<input type="submit" value="Komentiraj" />
		</form>
	<% } %>
  </body>
</html>