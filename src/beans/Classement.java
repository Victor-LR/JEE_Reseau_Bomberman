package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Classement {

	private ArrayList<JoueurClassement> classementJournalier;
	private ArrayList<JoueurClassement> classementMensuel;

	public Classement() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		classementMensuel = executerCountMensuelV(Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(),
				Identifiant_BDD.getMotDePasseBdd());
		classementJournalier = executerCountJournalierV(Identifiant_BDD.getUrljdbc(),
				Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd());
	}

	public ArrayList<JoueurClassement> executerCountMensuelV(String url, String utilisateur, String motDePasse) {
		/* Connexion à la base de données */

		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		ArrayList<JoueurClassement> listeUtilisateur = new ArrayList<>();
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Création de l'objet gérant les requêtes */
			statement = connexion.prepareStatement(
					"SELECT pseudo_util , count(resultat) AS totalV FROM Historique WHERE (resultat='V' AND MONTH(date_partie)=MONTH(NOW())) GROUP BY pseudo_util ORDER BY totalv DESC;");
			resultat = statement.executeQuery();
			while (resultat.next()) {
				JoueurClassement joueur = new JoueurClassement(resultat.getString(1), resultat.getInt(2));
				System.out.println(resultat.getString(1) + " " + resultat.getInt(2));
				listeUtilisateur.add(joueur);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException ignore) {
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			}
			if (connexion != null) {
				try {
					connexion.close();
				} catch (SQLException ignore) {
				}
			}
		}
		System.out.println("fin");
		return listeUtilisateur;
	}

	public ArrayList<JoueurClassement> executerCountJournalierV(String url, String utilisateur, String motDePasse) {
		/* Connexion à la base de données */

		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		ArrayList<JoueurClassement> listeUtilisateur = new ArrayList<>();
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Création de l'objet gérant les requêtes */
			statement = connexion.prepareStatement(
					"SELECT pseudo_util , count(resultat) AS totalV FROM Historique WHERE (resultat='V' AND DAY(date_partie)=DAY(NOW())) GROUP BY pseudo_util ORDER BY totalv DESC;");
			resultat = statement.executeQuery();
			while (resultat.next()) {
				JoueurClassement joueur = new JoueurClassement(resultat.getString(1), resultat.getInt(2));
				System.out.println(resultat.getString(1) + " " + resultat.getInt(2));
				listeUtilisateur.add(joueur);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException ignore) {
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			}
			if (connexion != null) {
				try {
					connexion.close();
				} catch (SQLException ignore) {
				}
			}
		}
		System.out.println("fin");
		return listeUtilisateur;
	}

	public ArrayList<JoueurClassement> getClassementJournalier() {
		return classementJournalier;
	}

	public void setClassementJournalier(ArrayList<JoueurClassement> classementJournalier) {
		this.classementJournalier = classementJournalier;
	}

	public ArrayList<JoueurClassement> getClassementMensuel() {
		return classementMensuel;
	}

	public void setClassementMensuel(ArrayList<JoueurClassement> classementMensuel) {
		this.classementMensuel = classementMensuel;
	}
}
