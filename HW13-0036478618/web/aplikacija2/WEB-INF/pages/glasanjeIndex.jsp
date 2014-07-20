<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body style="background-color: <%= session.getAttribute("currentColor")%>">	
		<h1>Glasajte za omiljeni bend:</h1>
		<p>Kliknite na link kako biste glasali!</p>
		
		<ol>
			<c:forEach var="z" items="${bands}">
			<li><a href="glasanje-glasaj?id=${z.id}">${z.ime}</a></li>
			</c:forEach>
		</ol>
	</body>

</html>