<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="hr.fer.zemris.java.aplikacija5.model.BlogEntry"%>
<%@page import="java.util.List" %>

<html>

<p><a href="<%= request.getContextPath() %>/servleti/main">Main</a></p>
<head>
<title>Blog by <c:out value='${authorNick}'></c:out></title>
</head>
<body>
 	<c:choose>	
		<c:when test='<%=session.getAttribute("current.user.id") == null%>'>
		<h3>Anonimni korisnik</h3> 
		</c:when>
		<c:otherwise><h3> <%=session.getAttribute("current.user.nick")%>  | <a href="<%= request.getContextPath() %>/servleti/logout">logout</a></h3></c:otherwise>
	</c:choose>
 
 	<h2>Titles by <c:out value='${authorFn}'></c:out> <c:out value='${authorLn}'></c:out></h2>
 	<ul>
 	<%
 		List<BlogEntry> entries = (List<BlogEntry>)request.getAttribute("entries");
 		for(BlogEntry e : entries) {
 			%>
 			<li><a href="<%= request.getContextPath() + "/servleti/author/" + request.getAttribute("authorNick") + "/" + e.getId() %>"><%= e.getTitle() %></a></li>
 		<%}
 	%>
 	</ul>
 	<%
 		if(request.getAttribute("authorNick").equals(session.getAttribute("current.user.nick"))) {
 	%>	
 		<a href="<%= request.getContextPath() + "/servleti/author/" + request.getAttribute("authorNick") + "/new"%>">Dodaj novi zapis</a>
 	<% } %>
 </body>
</html>