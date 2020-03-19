package dao;

import beans.Utilisateur;

public interface UtilisateurDao {
	// Insertion de l'Utilisateur dans la bdd Utilisateur
	void creer(Utilisateur utilisateur) throws DAOException;

	Utilisateur trouver(String pseudo) throws Exception;

	// Suppression de l'Utilisateur dans la bdd Utilisateur
	void supprimer(int id) throws DAOException;

	// Modification des informations de l'Utilisateur dans la bdd Historique et
	// Utilisateur
	void modifier(Utilisateur utilisateur, String exPseudo) throws DAOException;

}
