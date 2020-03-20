package dao;

import java.util.ArrayList;

import beans.JoueurClassement;
import beans.Partie;
import beans.Utilisateur;

public interface HistoriqueDao {
	// A modifier
	void creer(Utilisateur utilisateur) throws DAOException;

	ArrayList<JoueurClassement> trouverClassement(String orderby) throws Exception;

	ArrayList<JoueurClassement> trouverRatio(ArrayList<JoueurClassement> listeJoueur, String orderby) throws Exception;

	// Suppression de l'Utilisateur dans la bdd Utilisateur
	void supprimer(int id) throws DAOException;

	// Modification des informations de l'Utilisateur dans la bdd Historique et
	// Utilisateur
	void modifier(Utilisateur utilisateur, String exPseudo) throws DAOException;

	ArrayList<Partie> listingParties(String pseudo) throws DAOException;

	int countResultat(String pseudo, String resultat);

	int totalPartie(String pseudo);
}
