package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.Utilisateur;

public class UtilisateurDaoImpl implements UtilisateurDao {

	private DAOFactory daoFactory;
	private static final String SQL_INSERT = "INSERT INTO Utilisateur (email, mot_de_passe, nom, prenom, pseudo, date_inscription) VALUES(?, ?, ?, ?, ?, NOW());";
	private static final String SQL_SELECT_PSEUDO = "SELECT * FROM Utilisateur WHERE pseudo = ?;";
	private static final String SQL_DELETE = "DELETE FROM Utilisateur WHERE id = ?;";
	private static final String SQL_UPDATE_UTILISATEUR = "UPDATE Utilisateur SET email = ?, mot_de_passe = ?, nom = ?, prenom = ?, pseudo = ? WHERE id = ?";

	public UtilisateurDaoImpl(DAOFactory dao) {
		daoFactory = dao;
	}

	@Override
	public void creer(Utilisateur utilisateur) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, utilisateur.getEmail(),
					utilisateur.getMdp(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getPseudo());
			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Echec de la création de l'utilisateur, aucun  ajout effectué !");
			}
			result = preparedStatement.getGeneratedKeys();
			if (result.next()) {
				utilisateur.setIdentifiant(result.getInt(1));
			} else {
				throw new DAOException("Echec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(result, preparedStatement, connexion);
		}
	}

	@Override
	public Utilisateur trouver(String pseudo) throws Exception {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Utilisateur util = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PSEUDO, false, pseudo);
			result = preparedStatement.executeQuery();
			if (result.next()) {
				util = map(result);
			}
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			fermeturesSilencieuses(result, preparedStatement, connexion);
		}
		return util;
	}

	@Override
	public void supprimer(int id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		int statut = 0;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, false, id);
			statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Suppression impossible");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(result, preparedStatement, connexion);
		}
	}

	@Override
	public void modifier(Utilisateur utilisateur) throws DAOException {
		// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		int statut = 0;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_UTILISATEUR, false,
					utilisateur.getEmail(), utilisateur.getMdp(), utilisateur.getNom(), utilisateur.getPrenom(),
					utilisateur.getPseudo(), utilisateur.getIdentifiant());
			statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Modification Utilisateur impossible");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
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

	private static Utilisateur map(ResultSet resultSet) throws SQLException {
		Utilisateur utilisateur = new Utilisateur(resultSet.getString("nom"), resultSet.getString("prenom"),
				resultSet.getString("pseudo"), resultSet.getString("mot_de_passe"), resultSet.getString("email"),
				resultSet.getInt("id"));
		return utilisateur;
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
