<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
	<body style="background-color: <%= session.getAttribute("currentColor")%>">
		<a href="colors">Background color chooser</a><br>
		<a href="squares?a=100&b=120">Table of squares</a><br>
		<a href="stories/funny.jsp">Funny story</a><br>
		<a href="report.jsp">Report</a><br>
		<a href="powers?a=1&b=100&n=3">Get powers.xls</a><br>
		<a href="appinfo.jsp">Get appinfo</a><br>
		<a href="glasanje">Glasanje</a>
	</body>
</html>