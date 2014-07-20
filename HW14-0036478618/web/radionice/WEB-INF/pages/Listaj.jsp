<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Radionice</title>
	</head>
	
		<c:choose>
		<c:when test='<%=session.getAttribute("current.user") == null %>'>
			Anonimni korisnik | <a href="login">login</a>
		</c:when>
		<c:otherwise>
			<%=session.getAttribute("current.user").toString() %> | <a href="logout">logout</a>
		</c:otherwise>
		</c:choose>
		
	<body>
		<h1>Lista postojećih radionica</h1>
		<c:choose>
		<c:when test="${zapisi.isEmpty()}">
			<p>Trenutno nemate evidentiranih radionica.</p>
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="zapis" items="${zapisi}">
					<li>
						<c:out value="${zapis.naziv}"></c:out>
						<c:out value="${zapis.datum}"></c:out>
						<c:if test='<%=session.getAttribute("current.user") != null %>'>
						<a href="edit?id=<c:out value="${zapis.id}"/>">Uredi</a>
						</c:if>
					</li>
				</c:forEach>
			</ol>
		</c:otherwise>
		</c:choose>
		<c:if test='<%=session.getAttribute("current.user") != null %>'>
		<p><a href="new">Nova</a></p>
		</c:if>
	</body>
	
</html>