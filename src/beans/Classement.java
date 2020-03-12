package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Classement {

	private ArrayList<JoueurClassement> classementJournalier;
	private ArrayList<JoueurClassement> classementMensuel;
	private ArrayList<JoueurClassement> classementJournalierRatio;
	private ArrayList<JoueurClassement> classementMensuelRatio;

	public Classement() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		classementMensuel = executerCountV(Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(),
				Identifiant_BDD.getMotDePasseBdd(), "MONTH(date_partie)=MONTH(NOW())");
		classementJournalier = executerCountV(Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(),
				Identifiant_BDD.getMotDePasseBdd(), "DAY(date_partie)=DAY(NOW())");
		classementJournalierRatio = executerSetRatio(Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(),
				Identifiant_BDD.getMotDePasseBdd(), "DAY(date_partie)=DAY(NOW())", classementJournalier);
		classementMensuelRatio = executerSetRatio(Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(),
				Identifiant_BDD.getMotDePasseBdd(), "MONTH(date_partie)=MONTH(NOW())", classementMensuel);
	}

	public ArrayList<JoueurClassement> executerCountV(String url, String utilisateur, String motDePasse, String tri) {
		/* Connexion à la base de données */

		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		ArrayList<JoueurClassement> listeUtilisateur = new ArrayList<>();
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Création de l'objet gérant les requêtes */
			statement = connexion.prepareStatement(
					"SELECT pseudo_util , count(resultat) AS totalV FROM Historique WHERE (resultat='V' AND " + tri
							+ ") GROUP BY pseudo_util ORDER BY totalv DESC;");
			resultat = statement.executeQuery();
			while (resultat.next()) {
				JoueurClassement joueur = new JoueurClassement(resultat.getString(1), resultat.getInt(2));
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
		return listeUtilisateur;
	}

	public ArrayList<JoueurClassement> executerSetRatio(String url, String utilisateur, String motDePasse, String tri,
			ArrayList<JoueurClassement> listeJoueur) {
		/* Connexion à la base de données */

		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		List<JoueurClassement> listeClassementRatio = new ArrayList<JoueurClassement>();
		String pseudo;
		CompareRatio c = new CompareRatio();
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Création de l'objet gérant les requêtes */
			for (int i = 0; i < listeJoueur.size(); i++) {
				pseudo = listeJoueur.get(i).getPseudo();
				statement = connexion.prepareStatement("SELECT count(resultat) FROM Historique WHERE (resultat='D' AND "
						+ tri + " AND pseudo_util='" + pseudo + "' );");
				resultat = statement.executeQuery();
				while (resultat.next()) {
					listeJoueur.get(i).setNbDefaite(resultat.getInt(1));
					listeJoueur.get(i).setRatio();
					listeClassementRatio.add(listeJoueur.get(i));
				}
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
		Collections.sort((List<JoueurClassement>) listeClassementRatio, c);
		return (ArrayList<JoueurClassement>) listeClassementRatio;
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

	public ArrayList<JoueurClassement> getClassementJournalierRatio() {
		return classementJournalierRatio;
	}

	public void setClassementJournalierRatio(ArrayList<JoueurClassement> classementRatio) {
		this.classementJournalierRatio = classementRatio;
	}

	public ArrayList<JoueurClassement> getClassementMensuelRatio() {
		return classementMensuelRatio;
	}

	public void setClassementMensuelRatio(ArrayList<JoueurClassement> classementMensuelRatio) {
		this.classementMensuelRatio = classementMensuelRatio;
	}
}
