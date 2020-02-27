<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

<link type="text/css" rel="stylesheet" href="inc/style.css" />
<meta charset="UTF-8">
<title>Accueil</title>

</head>

	<header>
	<h1>Bienvenue sur le site Bomberman</h1>
	</header>
	
	
<body>
<fieldset>
	<legend>Connexion</legend>
	<form action="Connexion" method="post">
		<label for="pseudoUtilisateur">Pseudo </label>
		<input type="text" id="pseudoUtilisateur" name="pseudoUtilisateur" value='<c:out value="${param.pseudoUtilisateur }"></c:out>' size="20" maxlength="20" />
		<span class="erreur">${erreurpseudo}</span>
		
		</br>
		
		<label for="mdpUtilisateur">Mot de passe </label>
		<input type="password" id="mdpUtilisateur" name="mdpUtilisateur" value="" size="20" maxlength="20" />
		<span class="erreur">${erreurmdp}</span>

		</br>
		
		<input type="submit" value="Connexion"  />
		<input type="reset" value="Reset" /> </br>
		
	</form>
	<a href="<c:url value="/CreationUtilisateur" />" >Créer un utilisateur</a></br>
</fieldset>
<c:import url="/ClassementServl"></c:import>
</body>
</html>