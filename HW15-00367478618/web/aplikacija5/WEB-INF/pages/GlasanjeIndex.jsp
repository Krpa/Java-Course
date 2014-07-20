<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
		<h1>${title}</h1>
		<p>${message}</p>
		
		<ol>
			<c:forEach var="z" items="${zapisi}">
			<li><a href="glasanje-glasaj?id=${z.id}">${z.ime}</a></li>
			</c:forEach>
		</ol>

</html>