<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body style="background-color: <%= session.getAttribute("currentColor")%>">
		<a href = "/aplikacija2/index.html">-Back to index-</a>
		<p style="color: <%= session.getAttribute("storyColor") %>">
				A curious child asked his mother: “Mommy, why are some of your hairs turning grey?”<br>
				The mother tried to use this occasion to teach her child: <br>
				“It is because of you, dear. <br>
				Every bad action of yours will turn one of my hairs grey!”<br>
				The child replied innocently: “Now I know why grandmother has only grey hairs on her head.”<br>
			</font>
		</p>
	</body>


</html>