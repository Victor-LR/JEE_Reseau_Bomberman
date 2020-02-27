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

import beans.Identifiant_BDD;
import beans.Utilisateur;

/**
 * Servlet implementation class Suppression
 */
@WebServlet("/Suppression")
public class Suppression extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Suppression() {
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
		executerSuppression(request);
		this.getServletContext().getRequestDispatcher("/suppression.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void executerSuppression(HttpServletRequest request) {
		/* Connexion à la base de données */
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		HttpSession session = request.getSession();
		int statut = 0;
		try {
			connexion = DriverManager.getConnection(Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd());

			/* Création de l'objet gérant les requêtes */
			statement = connexion.prepareStatement("DELETE FROM Utilisateur WHERE id = ?;");
			Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("utilisateur");
			System.out.println("Session " + utilisateurSession.getIdentifiant());
			statement.setInt(1, utilisateurSession.getIdentifiant());
			statut = statement.executeUpdate();
			/* Exécution d'une requête de lecture */
			// resultat = statement.executeQuery();

			/* Récupération des données du résultat de la requête de lecture */
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
	}

}
