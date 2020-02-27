<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<fieldset>
	<legend>Classement journalier des joueurs</legend>

		<c:choose>
			<c:when  test="${empty classementJournalier  }">
				<c:out value="Il n'y pas eu de partie aujourd'hui"></c:out>
			</c:when>
			<c:otherwise>
				<table border="1" style="border-collapse:collapse">
					<tr>
						<td><b><p>Pseudo</p></b></td>
						<td><b><p>Nombre de victoire</p></b></td>				
					</tr>
					<c:forEach items="${ classementJournalier }" var="jour">
					<tr>
						<td><p><c:out value="${jour.pseudo }"></c:out></p></td>
						<td><p><c:out value="${jour.nbVictoire }"></c:out></p></td>				
					</tr>
				</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>

</fieldset>
<fieldset>
	<legend>Classement mensuel des joueurs</legend>
		<c:choose>
			<c:when  test="${empty classementMensuel  }">
				<c:out value="Il n'y pas eu de partie ce mois-ci"></c:out>
			</c:when>
			<c:otherwise>
				<table border="1" style="border-collapse:collapse">
					<tr>
						<td><b><p>Pseudo</p></b></td>
						<td><b><p>Nombre de victoire</p></b></td>				
					</tr>
					<c:forEach items="${ classementMensuel }" var="mensuel">
					<tr>
						<td><p><c:out value="${mensuel.pseudo }"></c:out></p></td>
						<td><p><c:out value="${mensuel.nbVictoire }"></c:out></p></td>				
					</tr>
				</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
</fieldset>
</body>
</html>