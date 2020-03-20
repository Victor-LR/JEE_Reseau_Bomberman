package dao;

import java.util.ArrayList;

import beans.JoueurClassement;
import beans.Partie;
import beans.Utilisateur;

public interface HistoriqueDao {
	void creer(Utilisateur utilisateur) throws DAOException;

	ArrayList<JoueurClassement> trouverClassement(String orderby) throws Exception;

	ArrayList<JoueurClassement> trouverRatio(ArrayList<JoueurClassement> listeJoueur, String orderby) throws Exception;

	void supprimer(int id) throws DAOException;

	void modifier(Utilisateur utilisateur, String exPseudo) throws DAOException;

	ArrayList<Partie> listingParties(String pseudo) throws DAOException;

	int countResultat(String pseudo, String resultat);

	int totalPartie(String pseudo);
}
