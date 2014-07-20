<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body style="background-color: <%= session.getAttribute("currentColor")%>">	
		<a href = "index.html">-Back to index-</a>
		<table border="1" cellspacing="0">
		<c:forEach var="r" items="${parovi}">
		<tr><td>${r.broj}</td>, <td>${r.vrijednost}</td></tr>
		</c:forEach>
		</table>
	</body>
</html>