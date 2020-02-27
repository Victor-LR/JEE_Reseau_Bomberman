package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Utilisateur;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CHAMP_PSEUDO = "pseudoUtilisateur";
	public static final String CHAMP_MDP = "mdpUtilisateur";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Connexion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* Chargement du driver JDBC pour MySQL */
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		Utilisateur util = null;
		String pseudo = request.getParameter(CHAMP_PSEUDO);
		String mdp = request.getParameter(CHAMP_MDP);
		HttpSession session = request.getSession();
		boolean erreur = false;
		try {
			validePseudo(request, pseudo, Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd());
		} catch (Exception e) {
			erreur = true;
			request.setAttribute("erreurpseudo", e.getMessage());
		}
		try {
			util = valideMdp(request, Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd());
		} catch (Exception e) {
			erreur = true;
			request.setAttribute("erreurmdp", e.getMessage());
		}
		if (erreur)
			this.getServletContext().getRequestDispatcher("/accueil.jsp").forward(request, response);
		else {
			session.setAttribute("utilisateur", util);
			this.getServletContext().getRequestDispatcher("/restreint/connexionReussie.jsp").forward(request, response);
		}
	}

	public void validePseudo(HttpServletRequest request, String pseudo, String url, String utilisateur,
			String motDePasse) throws Exception {
		if (pseudo.isEmpty()) {
			throw new Exception("Veuillez remplir le champ");
		}
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		String mdpBdd = null;
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Création de l'objet gérant les requêtes */
			statement = connexion.prepareStatement("SELECT * FROM Utilisateur WHERE pseudo = ?;");
			statement.setString(1, pseudo);
			/* Exécution d'une requête de lecture */
			resultat = statement.executeQuery();
			/* Récupération des données du résultat de la requête de lecture */
			if (!resultat.next()) {
				throw new Exception("Pseudo non existant");
			}
		} catch (SQLException e) {
			System.out.println("Erreur lors de la connexion : " + e.getMessage());
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
	}

	public Utilisateur valideMdp(HttpServletRequest request, String url, String utilisateur, String motDePasse)
			throws Exception {
		/* Connexion à la base de données */
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		String pseudo = request.getParameter(CHAMP_PSEUDO);
		String mdp = request.getParameter(CHAMP_MDP);

		String nomUtilisateur = null;
		String mdpUtilisateur = null;
		Integer idUtilisateur = null;
		String emailUtilisateur = null;
		String prenomUtilisateur = null;
		String pseudoUtilisateur = null;
		Utilisateur util = null;

		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Création de l'objet gérant les requêtes */
			statement = connexion.prepareStatement("SELECT * FROM Utilisateur WHERE pseudo = ?;");
			statement.setString(1, pseudo);
			/* Exécution d'une requête de lecture */
			resultat = statement.executeQuery();
			/* Récupération des données du résultat de la requête de lecture */
			if (resultat.next()) {

				idUtilisateur = resultat.getInt("id");
				emailUtilisateur = resultat.getString("email");
				mdpUtilisateur = resultat.getString("mot_de_passe");
				nomUtilisateur = resultat.getString("nom");
				pseudoUtilisateur = resultat.getString("pseudo");
				prenomUtilisateur = resultat.getString("prenom");
				util = new Utilisateur(nomUtilisateur, prenomUtilisateur, pseudoUtilisateur, mdpUtilisateur,
						emailUtilisateur, idUtilisateur);
			}
		} catch (SQLException e) {
			System.out.println("Erreur lors de la connexion : " + e.getMessage());
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
		System.out.println(mdp + "  " + mdpUtilisateur);
		if (!mdp.matches(mdpUtilisateur))
			throw new Exception("Mot de passe incorrect");
		return util;
	}
}