package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beans.CompareRatio;
import beans.JoueurClassement;
import beans.Partie;
import beans.Utilisateur;

public class HistoriqueDaoImpl implements HistoriqueDao {

	private static final String SQL_SELECT_PARTIE = "SELECT * FROM Historique WHERE pseudo_util = ?;";
	private static final String SQL_UPDATE_HISTORIQUE = "UPDATE Historique SET pseudo_util = ? WHERE pseudo_util = ?";
	private static final String SQL_COUNT_TOTAl = "SELECT COUNT(*) FROM Historique WHERE pseudo_util=?;";
	private static final String SQL_COUNT_RESULT = "SELECT COUNT(resultat) FROM Historique WHERE (pseudo_util = ? AND resultat=?);";
	private DAOFactory daoFactory;

	public HistoriqueDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void creer(Utilisateur utilisateur) throws DAOException {
		// TODO Auto-generated method stub

	}

	// Compte le nombre de victoire de tous les utilisateurs et les stocke dans un
	// tableau trié

	@Override
	public ArrayList<JoueurClassement> trouverClassement(String orderby) throws Exception {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		ArrayList<JoueurClassement> listeUtilisateur = new ArrayList<>();
		try {
			connexion = daoFactory.getConnection();

			/* Création de l'objet gérant les requêtes */
			statement = connexion.prepareStatement(
					"SELECT pseudo_util , count(resultat) AS totalV FROM Historique WHERE (resultat='V' AND " + orderby
							+ ") GROUP BY pseudo_util ORDER BY totalv DESC;");
			resultat = statement.executeQuery();
			while (resultat.next()) {
				JoueurClassement joueur = new JoueurClassement(resultat.getString(1), resultat.getInt(2));
				listeUtilisateur.add(joueur);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			fermeturesSilencieuses(resultat, statement, connexion);
		}
		return listeUtilisateur;
	}

	@Override
	public void supprimer(int id) throws DAOException {
		// TODO Auto-generated method stub

	}

	// Modifie le pseudo des utilisateurs dans la bdd
	@Override
	public void modifier(Utilisateur utilisateur, String exPseudo) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut = 0;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_HISTORIQUE, false,
					utilisateur.getPseudo(), exPseudo);
			statut = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	// Affiche toutes les parties d'un utilisateur
	@Override
	public ArrayList<Partie> listingParties(String pseudo) throws DAOException {
		ArrayList<Partie> listeParties = new ArrayList<Partie>();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet result = null;
		int statut = 0;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PARTIE, false, pseudo);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				Partie partie = new Partie(result.getInt(1), result.getString(2), result.getDate(3), result.getInt(4),
						result.getString(5));
				listeParties.add(partie);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(result, preparedStatement, connexion);
		}
		return listeParties;
	}

	// Compte le nombre de défaite dans la bdd des joueurs et calcule ensuite le
	// ratio à partir du nombre de victoires déjà connu des joueurs pour les stocker
	// dans un tableau trié par la suite à l'aide de Comparator
	@Override
	public ArrayList<JoueurClassement> trouverRatio(ArrayList<JoueurClassement> listeJoueur, String orderby)
			throws Exception {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		List<JoueurClassement> listeClassementRatio = new ArrayList<JoueurClassement>();
		String pseudo;
		CompareRatio c = new CompareRatio();
		try {
			connexion = daoFactory.getConnection();

			/* Création de l'objet gérant les requêtes */
			for (int i = 0; i < listeJoueur.size(); i++) {
				pseudo = listeJoueur.get(i).getPseudo();
				statement = connexion.prepareStatement("SELECT count(resultat) FROM Historique WHERE (resultat='D' AND "
						+ orderby + " AND pseudo_util='" + pseudo + "'  );");
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
			fermeturesSilencieuses(resultat, statement, connexion);
		}
		Collections.sort((List<JoueurClassement>) listeClassementRatio, c);
		return (ArrayList<JoueurClassement>) listeClassementRatio;
	}

	// Compte le nombre total de parties d'un utilisateur
	@Override
	public int totalPartie(String pseudo) {
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		int count = 0;
		try {
			connexion = daoFactory.getConnection();
			statement = initialisationRequetePreparee(connexion, SQL_COUNT_TOTAl, false, pseudo);
			resultat = statement.executeQuery();
			if (resultat.next()) {
				count = resultat.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			fermeturesSilencieuses(resultat, statement, connexion);
		}
		return count;
	}

	// Compte le nombre total en fonction du resultat(victoire/défaite) d'un joueur
	@Override
	public int countResultat(String pseudo, String resultat) {
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int count = 0;
		try {
			connexion = daoFactory.getConnection();

			/* Création de l'objet gérant les requêtes */
			statement = initialisationRequetePreparee(connexion, SQL_COUNT_RESULT, false, pseudo, resultat);
			result = statement.executeQuery();
			if (result.next()) {
				count = result.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			fermeturesSilencieuses(result, statement, connexion);
		}
		return count;
	}

	public static PreparedStatement initialisationRequetePreparee(Connection connexion, String sql,
			boolean returnGeneratedKeys, Object... objets) throws SQLException {
		PreparedStatement preparedStatement = connexion.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i = 0; i < objets.length; i++) {
			preparedStatement.setObject(i + 1, objets[i]);
		}
		return preparedStatement;
	}

	/* Fermeture silencieuse du resultset */
	public static void fermetureSilencieuse(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture du ResultSet : " + e.getMessage());
			}
		}
	}

	/* Fermeture silencieuse du statement */
	public static void fermetureSilencieuse(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture du Statement : " + e.getMessage());
			}
		}
	}

	/* Fermeture silencieuse de la connexion */
	public static void fermetureSilencieuse(Connection connexion) {
		if (connexion != null) {
			try {
				connexion.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture de la connexion : " + e.getMessage());
			}
		}
	}

	/* Fermetures silencieuses du statement et de la connexion */
	public static void fermeturesSilencieuses(Statement statement, Connection connexion) {
		fermetureSilencieuse(statement);
		fermetureSilencieuse(connexion);
	}

	/* Fermetures silencieuses du resultset, du statement et de la connexion */
	public static void fermeturesSilencieuses(ResultSet resultSet, Statement statement, Connection connexion) {
		fermetureSilencieuse(resultSet);
		fermetureSilencieuse(statement);
		fermetureSilencieuse(connexion);
	}
}
