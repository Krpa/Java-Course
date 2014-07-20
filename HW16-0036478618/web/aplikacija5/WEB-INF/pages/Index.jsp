<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="hr.fer.zemris.java.aplikacija5.model.BlogUser"%>
<%@page import="java.util.List" %>

<html>
<head>
<title>Main</title>
</head>
<body>
 
 
 	<c:choose>	
		<c:when test='<%=session.getAttribute("current.user.id") == null%>'>
		<h3>Anonimni korisnik</h3> 
		<h2>
		Prijava | <a href="register">Registracija</a>
		</h2>
		<h3>
			<font color="red"> <c:if test="${errors.getErrorMessage('loginError') != null}">${errors.getErrorMessage('loginError')}</c:if>
			</font>
		</h3>
		<form action="" method="post">
			<table>
				<tr>
					<td align="right">Korisničko ime</td>
					<td><input type="text" name="username" value="${username}"></td>
				</tr>
				<tr>
					<td align="right">Lozinka</td>
					<td><input type="password" name="password" value="${password}"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" name="metoda" value="Potvrdi">
						<input type="submit" name="metoda" value="Odustani"></td>
				</tr>
			</table>
		</form>
		</c:when>
		<c:otherwise><h3><%=session.getAttribute("current.user.nick")%> | <a href="logout">logout</a></h3></c:otherwise>
	</c:choose>
	
	<br>
	<h2>Popis postojećih korisnika</h2>
	
	<ul>
	<% List<BlogUser> users = (List<BlogUser>) request.getAttribute("users");
		for(BlogUser u : users) {
			%>
		<li>
			<a href= <%=request.getContextPath() + "/servleti/author/" + u.getNick() %>><%= u.getFirstName() + " " + u.getLastName()%></a>
		</li>	
		<% } %>
	</ul>
</body>
</html>