<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="inc/style.css" />
<meta charset="UTF-8">
<title>Historique des parties</title>
</head>
<body>
	<h1>Historique des parties de <c:out value="${sessionScope.utilisateur.pseudo }"></c:out></h1><br>
	<table border="1" style="border-collapse:collapse" >
		<tr>
			<td><p><b><c:out value="Partie n°"></c:out></b></p></td>
			<td><p><b><c:out value="Date de la partie"></c:out></b></p></td>
			<td><p><b><c:out value="Score"></c:out></b></p></td>
			<td><p><b><c:out value="Resultat"></c:out></b></p></td>
		</tr>
		<c:forEach items="${listeParties }" var="parties" >
				<tr>
				<td><c:out value="${ parties.id_partie}"></c:out></td>
				<td><c:out value="${ parties.date_partie}"></c:out></td>
				<td><c:out value="${ parties.score_partie}"></c:out></td>
				<td><c:out value="${ parties.resultat_partie}"></c:out></td>
				</tr>
		</c:forEach>
	</table>
	<input type=button onclick=window.location.href='AccesAccorde'; value="Retour" />
</body>
</html>