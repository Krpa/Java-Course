<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Pogreška</title>
	</head>
	
	<body>
		<h1>Dogodila se pogreška</h1>
		<p><c:out value="${poruka}"></c:out></p>
		
		
		<p><a href="<%= request.getContextPath() %>/servleti/main">Povratak na početnu stranicu</a></p>
	</body>
</html>