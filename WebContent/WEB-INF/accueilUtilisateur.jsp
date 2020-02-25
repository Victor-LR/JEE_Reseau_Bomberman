<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="inc/style.css" />
<meta charset="UTF-8">
<title>Accueil utilisateur</title>
</head>
<body>
	<h1><c:out value="Bienvenue ${sessionScope.pseudo }" /></h1>
	<fieldset>
	<legend>Historique des parties</legend>
	<p>Nombre de parties jouées : </p></br>
	<p>Victoire / Défaites : </p></br>
	</fieldset>
	<a href="<c:url value="/CreationUtilisateur" />" >Suppression du compte</a>
</body>
</html>