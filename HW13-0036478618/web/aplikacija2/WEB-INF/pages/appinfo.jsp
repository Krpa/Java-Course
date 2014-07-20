<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	
	<body style="background-color: <%= session.getAttribute("currentColor") %>">
		<h1>
			<%= request.getAttribute("uptime") %>
		</h1>
	</body>

</html>