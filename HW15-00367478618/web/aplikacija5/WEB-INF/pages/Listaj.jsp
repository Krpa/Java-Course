<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
		<h1>Odaberite anketu:</h1>
		
		<ol>
			<c:forEach var="z" items="${ankete}">
			<li><a href="glasanje?pollID=${z.id}">${z.title}</a></li>
			</c:forEach>
		</ol>

</html>