<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body style="background-color: <%= session.getAttribute("currentColor")%>">	
		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezultati glasanja.</p>
		
		<table border="1" cellspacing="0" class="rez">
			 <thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
			 <tbody>
			 	<c:forEach var="r" items="${rezultati}">
			 		<tr><td>${r.name}</td><td>${r.brojGlasova}</td></tr>
			 	</c:forEach>
			 </tbody>
		</table>
		
		<br><br>
		<h2>Grafički prikaz rezultata</h2>
			 <img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
			 
			 <h2>Rezultati u XLS formatu</h2>
			 <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>	
			 
		<br><br>
		<h2>Razno</h2>
		<p>Primjeri pjesama pobjedničkih bendova:</p>	
		<ul>
			<c:forEach var="p" items="${pjesme}">
				<li><a href="${p.link}">${p.imeBenda}</a></li>
			</c:forEach>
		</ul>
	</body>


</html>