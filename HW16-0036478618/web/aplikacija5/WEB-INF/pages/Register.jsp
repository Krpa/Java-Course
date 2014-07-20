<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Registracija</title>

<style type="text/css">
.greska {
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;
}

.opis {
	text-align: right;
	vertical-align: top;
}
</style>
</head>

<body>

	<p><a href="<%= request.getContextPath() %>/servleti/main">Main</a></p>
	<h2>Novi korisnik</h2>

	<form action="register" method="post">

		<table>
			<tr>
				<td align="right"><i>Ime</i></td>
				<td><input type="text" name="ime"
					value="${user.getFirstName()}" size="30"></td>
				<c:if test="${errors.getErrorMessage('ime') != null}">
					<td class="greska"><c:out
							value="${errors.getErrorMessage('ime')}" /></td>
				</c:if>
			</tr>

			<tr>
				<td align="right"><i>Prezime</i></td>
				<td><input type="text" name="prezime"
					value="${user.getLastName()}" size="30"></td>
				<c:if test="${errors.getErrorMessage('prezime') != null}">
					<td class="greska"><c:out
							value="${errors.getErrorMessage('prezime')}" /></td>
				</c:if>
			</tr>

			<tr>
				<td align="right"><i>Nadimak</i></td>
				<td><input type="text" name="nadimak" value="${user.getNick()}"
					size="30"></td>
				<c:if test="${errors.getErrorMessage('nadimak') != null}">
					<td class="greska"><c:out
							value="${errors.getErrorMessage('nadimak')}" /></td>
				</c:if>
			</tr>

			<tr>
				<td align="right"><i>E-mail</i></td>
				<td><input type="text" name="email" value="${user.getEmail()}"
					size="30"></td>
				<c:if test="${errors.getErrorMessage('email') != null}">
					<td class="greska"><c:out
							value="${errors.getErrorMessage('email')}" /></td>
				</c:if>
			</tr>

			<tr>
				<td align="right"><i>Lozinka</i></td>
				<td><input type="password" name="password" value="" size="30"></td>
				<c:if test="${errors.getErrorMessage('lozinka') != null}">
					<td class="greska"><c:out
							value="${errors.getErrorMessage('lozinka')}" /></td>
				</c:if>
				<c:if test="${errors.getErrorMessage('lozinke') != null}">
					<td class="greska"><c:out
							value="${errors.getErrorMessage('lozinke')}" /></td>
				</c:if>
			</tr>

			<tr>
				<td></td>
				<td><input type="submit" name="metoda" value="Pohrani">
					<input type="submit" name="metoda" value="Odustani"></td>
			</tr>

		</table>
	</form>

</body>

</html>