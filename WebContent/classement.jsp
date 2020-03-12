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
				<table cellspacing=10>
				<tr><td>
				<c:out value="Classement par victoire"></c:out>
				<table border="1" style="border-collapse:collapse">
					<tr>
						<td><b><p>Pseudo</p></b></td>
						<td><b><p>Nombre de victoire</p></b></td>				
					</tr>
					<c:forEach items="${ classementJournalier }" var="jourV">
					<tr>
						<td><p><c:out value="${jourV.pseudo }"></c:out></p></td>
						<td><p><c:out value="${jourV.nbVictoire }"></c:out></p></td>				
					</tr>
				</c:forEach>
				</table>
				</td>
				<td></td>
				<td>
				<c:out value="Classement par ratio"></c:out>
				<table border="1" style="border-collapse:collapse">
					<tr>
						<td><b><p>Pseudo</p></b></td>
						<td><b><p>Ratio</p></b></td>				
					</tr>
					<c:forEach items="${ classementJournalierRatio }" var="jourRatio">
					<tr>
						<td><p><c:out value="${jourRatio.pseudo }"></c:out></p></td>
						<td><p><c:out value="${jourRatio.ratio }"></c:out></p></td>				
					</tr>
				</c:forEach>
				</table>
				</td>
				</tr>
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
				<table cellspacing=10>
				<tr><td>
				<c:out value="Classement par victoire"></c:out>
				<table border="1" style="border-collapse:collapse">
					<tr>
						<td><b><p>Pseudo</p></b></td>
						<td><b><p>Nombre de victoire</p></b></td>				
					</tr>
					<c:forEach items="${ classementMensuel }" var="mensuelV">
					<tr>
						<td><p><c:out value="${mensuelV.pseudo }"></c:out></p></td>
						<td><p><c:out value="${mensuelV.nbVictoire }"></c:out></p></td>				
					</tr>
				</c:forEach>
				</table>
				</td>
				<td></td>
				<td>				
				<c:out value="Classement par ratio"></c:out>
				<table border="1" style="border-collapse:collapse">
					<tr>
						<td><b><p>Pseudo</p></b></td>
						<td><b><p>Ratio</p></b></td>				
					</tr>
					<c:forEach items="${ classementMensuelRatio }" var="mensuelRatio">
					<tr>
						<td><p><c:out value="${mensuelRatio.pseudo }"></c:out></p></td>
						<td><p><c:out value="${mensuelRatio.ratio }"></c:out></p></td>				
					</tr>
				</c:forEach>
				</table>
				</td>
				</tr>
				</table>
			</c:otherwise>
		</c:choose>
</fieldset>
</body>
</html>