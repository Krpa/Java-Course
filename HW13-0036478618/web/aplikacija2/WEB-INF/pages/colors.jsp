<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

	<body style="background-color: <%= session.getAttribute("currentColor")%>">
	<a href = "index.html">-Back to index-</a>
	
		<ol>
			<li><a href="setColor?color=white">White</a> </li>
			<li><a href="setColor?color=red">Red</a> </li>
			<li><a href="setColor?color=green">Green</a> </li>
			<li><a href="setColor?color=cyan">Cyan</a> </li>
			
		</ol>
	</body>
</html>
