<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Registracija</title>

<style type="text/css">
		.greska {
			font-family: fantasy;
			font-weight: bold;
			font-size: 0.9em;
			color: #FF0000;
		}
		.lijevo {
			text-align: right;
			vertical-align:top;
			font-style: italic;
		}
		</style>
</head>

<body>
	
	<p><a href="<%= request.getContextPath() %>/servleti/main">Main</a></p>
	<h3><%=session.getAttribute("current.user.nick")%></h3>
	<c:choose>
	<c:when test="${eid == null}"><h4>Novi zapis</h4></c:when>
	<c:otherwise>Izmjeni zapis</c:otherwise>
	</c:choose>

	<form action="<%= request.getContextPath() %>/servleti/author/<%=session.getAttribute("current.user.nick")%>" method="post">

		<table>
			<tr><td><input type="hidden" name="id" value="${id}"/></td></tr>
			<tr><td class="lijevo">Naslov </td><td><input type="text" name ="title" value='<c:out value="${form.title}"/>' size="30"></td>
		<c:if test="${form.imaPogresku('title')}">
			<div class="greska"><c:out value="${form.dohvatiPogresku('title')}"/></div>
		</c:if>
		<tr><td class="lijevo">Tekst</td><td><textarea name="text" rows="5" cols="100">${form.text}</textarea></td></tr>
		<c:if test="${form.imaPogresku('text')}">
			<div class="greska"><c:out value="${form.dohvatiPogresku('text')}"></c:out></div>
		</c:if>
			<tr>
				<td></td>
				<td><input type="submit" name="metoda" value="Pohrani">
				</td>
			</tr>

		</table>
	</form>

</body>

</html>