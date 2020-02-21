<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="inc/style.css" />
<meta charset="UTF-8">
<title>Création réussie</title>
</head>
<body>
	<h1><c:out value="Votre compte a été bien crée ${sessionScope.utilisateur.pseudo }" /></h1>
	<form action="AccesAccorde" method="get">
		<input type="submit" value="Continuer"/>
	</form>
</body>
</html>