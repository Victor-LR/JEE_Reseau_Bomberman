<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="inc/style.css" />
<meta charset="UTF-8">
<title>Création d'un utilisateur</title>
</head>
<body>	
	<div>
        <fieldset>
            <legend>Informations de l'utilisateur</legend>
            
			<form method="post" action="CreationUtilisateur">
            <label for="nomUtilisateur">Nom <span class="requis">*</span></label>
            <input type="text" id="nomUtilisateur" name="nomUtilisateur" value="<c:out value="${param.nomUtilisateur }"/>" size="20" maxlength="20" />
            <span class="erreur">${erreurnom}</span>
            <br />
            
            <label for="prenomUtilisateur">Prénom </label>
            <input type="text" id="prenomUtilisateur" name="prenomUtilisateur" value="<c:out value="${param.prenomUtilisateur }"/>" size="20" maxlength="20" />
            <span class="erreur">${erreurprenom}</span>
            <br />
            
            <label for="emailUtilisateur">Adresse email</label>
            <input type="email" id="emailUtilisateur" name="emailUtilisateur" value="<c:out value="${param.emailUtilisateur }"/>" size="20" maxlength="60" />
            <span class="erreur">${erreuremail}</span>
            <br />
            
            <label for="pseudoUtilisateur">Pseudo</label>
            <input type="text" id="pseudoUtilisateur" name="pseudoUtilisateur" value="<c:out value="${param.pseudoUtilisateur }"/>" size="20" maxlength="60" />
            <span class="erreur">${erreurpseudo}</span>
            <br />
            
            <label for="mdpUtilisateur">Mot de passe</label>
            <input type="password" id="mdpUtilisateur" name="mdpUtilisateur" value="" size="20" maxlength="60" />
            <span class="erreur">${erreurmdp}</span>
            <br />
            
       		<label for="mdp2Utilisateur">Confirmation du mot de passe</label>
            <input type="password" id="mdp2Utilisateur" name="mdp2Utilisateur" value="" size="20" maxlength="60" />
            <span class="erreur">${erreurmdp2}</span>
            <br />
            
            <input type="submit" value="Valider"  />
			<input type=button onclick=window.location.href='Accueil'; value="Annuler" />
			<br />
            
            </form>
            
        </fieldset>
	</div>
</body>
</html>