<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="inc/style.css" />
<meta charset="UTF-8">

<title>Modification utilisateur</title>
</head>
<body>
	<fieldset>
            <legend>Informations à modifier</legend>
            
			<form method="post" action="ModificationUtilisateur">
            <label for="nomUtilisateur">Nom <span class="requis">*</span></label>
            <input type="text" id="nomUtilisateur" name="nomUtilisateur" value="<c:out value="${sessionScope.utilisateur.nom }"/>" size="20" maxlength="20" />
            <span class="erreur">${erreurnom}</span>
            <br />
            
            <label for="prenomUtilisateur">Prénom </label>
            <input type="text" id="prenomUtilisateur" name="prenomUtilisateur" value="<c:out value="${sessionScope.utilisateur.prenom }"/>" size="20" maxlength="20" />
            <span class="erreur">${erreurprenom}</span>
            <br />
            
            <label for="emailUtilisateur">Adresse email</label>
            <input type="email" id="emailUtilisateur" name="emailUtilisateur" value="<c:out value="${sessionScope.utilisateur.email }"/>" size="20" maxlength="60" />
            <span class="erreur">${erreuremail}</span>
            <br />
            
            <label for="pseudoUtilisateur">Pseudo</label>
            <input type="text" id="pseudoUtilisateur" name="pseudoUtilisateur" value="<c:out value="${sessionScope.utilisateur.pseudo }"/>" size="20" maxlength="60" />
            <span class="erreur">${erreurpseudo}</span>
            <br />
            
            <label for="mdpUtilisateur">Nouveau mot de passe</label>
            <input type="password" id="mdpUtilisateur" name="mdpUtilisateur" value="${sessionScope.utilisateur.mdp}" size="20" maxlength="60" />
            <span class="erreur">${erreurmdp}</span>
            <br />
            
       		<label for="mdp2Utilisateur">Confirmation du nouveau mot de passe</label>
            <input type="password" id="mdp2Utilisateur" name="mdp2Utilisateur" value="${sessionScope.utilisateur.mdp }" size="20" maxlength="60" />
            <span class="erreur">${erreurmdp2}</span>
            <br />
            
            <input type="submit" value="Valider"  />
            </form>
			<input type=button onclick=window.location.href='AccesAccorde'; value="Annuler" />
            
	</fieldset>
</body>
</html>