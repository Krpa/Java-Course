<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Pogreška</title>
	</head>
	
	<body>
		<h1>Dogodila se pogreška</h1>
		<p><c:out value="${pogreska}"></c:out></p>
		
		<p><a href="index">Povratak na početnu stranicu.</a></p>
	</body>
</html>