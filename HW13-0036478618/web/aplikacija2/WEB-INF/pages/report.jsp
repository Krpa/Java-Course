<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body style="background-color: <%= session.getAttribute("currentColor")%>">
		<a href = "index.html">-Back to index-</a>
		<p>
			Here are the results of OS usage in survey that we completed.
		</p>
		<img alt="reportImage" src="reportImage.png">
	</body>
</html>