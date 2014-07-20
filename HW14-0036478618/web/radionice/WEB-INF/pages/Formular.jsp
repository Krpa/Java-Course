<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Radionica</title>
	
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
		<h1>
		<c:choose>
		<c:when test="${zapis.id.isEmpty()}">
		Nova radionica
		</c:when>
		<c:otherwise>
		Uređivanje radionice
		</c:otherwise>
		</c:choose>
		</h1>
		
		<form action="save" method="post">
		<table>
		<tr><td><input type="hidden" name="id" value="${id}"/></td></tr>
		<tr><td class="lijevo">Naziv </td><td><input type="text" name ="naziv" value='<c:out value="${zapis.naziv}"/>' size="30"></td>
		<c:if test="${zapis.imaPogresku('naziv')}">
			<div class="greska"><c:out value="${zapis.dohvatiPogresku('naziv')}"/></div>
		</c:if>
		</tr>
		<tr><td class="lijevo">Datum </td><td><input type="text" name ="datum" value='<c:out value="${zapis.datum}"/>' size="8"></td>
		<c:if test="${zapis.imaPogresku('datum')}">
			<div class="greska"><c:out value="${zapis.dohvatiPogresku('datum')}"/></div>
		</c:if>
		</tr>
		<tr><td class="lijevo">Oprema </td>  <td><select name="oprema" multiple size="10">
		<c:forEach var="opcija" items="${oprema}">
			  <option value="${opcija.id}"
			  		<c:forEach var="o" items="${zapis.oprema}">
			  		<c:choose>
			  		<c:when test="${o == opcija.id}">
			  		<c:out value="selected"/>
			  		</c:when>
			  		<c:otherwise>
			  		</c:otherwise>
			  		</c:choose>
			  		</c:forEach>
			  >${opcija.vrijednost}</option>
		</c:forEach>
		</select></td></tr>
		<c:if test="${zapis.imaPogresku('oprema')}">
			<div class="greska"><c:out value="${zapis.dohvatiPogresku('oprema')}"/></div>
		</c:if>
		
		
		<tr><td class="lijevo">Trajanje </td> <td><select name="trajanje">
		<c:forEach var="opcija" items="${trajanje}">
			<option value="${opcija.id}"
			  		<c:choose>
			  		<c:when test="${zapis.trajanje == opcija.id}">
			  		<c:out value="selected"/>
			  		</c:when>
			  		<c:otherwise>
			  		</c:otherwise>
			  		</c:choose>
			>
			${opcija.vrijednost}</option>
		</c:forEach>
		</select></td></tr>
		<c:if test="${zapis.imaPogresku('trajanje')}">
			<div class="greska"><c:out value="${zapis.dohvatiPogresku('trajanje')}"/></div>
		</c:if>
		
		
		<tr><td class="lijevo">Publika </td> <td><c:forEach var="opcija" items="${publika}">
				<input type="checkbox" name="publika" value="${opcija.id}"
					<c:forEach var="o" items="${zapis.publika}">
			  		<c:choose>
			  		<c:when test="${o == opcija.id}">
			  		<c:out value="checked"/>
			  		</c:when>
			  		<c:otherwise>
			  		</c:otherwise>
			  		</c:choose>
			  		</c:forEach>
				> ${opcija.vrijednost}<br>
				</c:forEach></td></tr>
		<c:if test="${zapis.imaPogresku('publika')}">
			<div class="greska"><c:out value="${zapis.dohvatiPogresku('publika')}"/></div>
		</c:if>
		<tr><td class="lijevo">Maksimalno polaznika </td><td> <input type="text" name ="maksPolaznika" value='<c:out value="${zapis.maksPolaznika}"/>' size="2"></td></tr>
		<c:if test="${zapis.imaPogresku('maksPolaznika')}">
			<div class="greska"><c:out value="${zapis.dohvatiPogresku('maksPolaznika')}"/></div>
		</c:if>
		
		<tr><td class="lijevo">EMail </td><td><input type="text" name ="email" value='<c:out value="${zapis.email}"/>' size="30"></td></tr>
		<c:if test="${zapis.imaPogresku('email')}">
			<div class="greska"><c:out value="${zapis.dohvatiPogresku('email')}"/></div>
		</c:if>
		
		<tr><td class="lijevo">Dopuna </td><td><textarea name="dopuna" rows="5" cols="100">${zapis.dopuna}</textarea></td></tr>
		<tr><td></td><td><input type="submit" name="metoda" value="Pohrani">
						 <input type="submit" name="metoda" value="Odustani"></td></tr>
		</table>
		</form>
	</body>
	
</html>