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
 * Servlet implementation class AccesAccorde
 */
@WebServlet("/AccesAccorde")
public class AccesAccorde extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccesAccorde() {
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
		HttpSession session = request.getSession();
		int total;
		float victoire, defaite;
		float ratio = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		total = executerCountTotal(session, Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd());
		victoire = executerCountResultat(session, Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd(), "V");
		defaite = executerCountResultat(session, Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd(), "D");
		boolean b = (victoire + defaite) != 0;
		if ((victoire + defaite) != 0) {
			ratio = victoire / (victoire + defaite) * 100;
		}
		request.setAttribute("totalParties", total);
		request.setAttribute("ratioParties", ratio);
		request.setAttribute("victoireParties", (int) victoire);
		request.setAttribute("defaiteParties", (int) defaite);
		this.getServletContext().getRequestDispatcher("/restreint/accueilUtilisateur.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public int executerCountTotal(HttpSession session, String url, String utilisateur, String motDePasse) {
		/* Connexion à la base de données */

		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		int count = 0;
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Création de l'objet gérant les requêtes */
			statement = connexion.prepareStatement("SELECT COUNT(*) FROM Historique WHERE pseudo_util=?;");
			Utilisateur util = (Utilisateur) session.getAttribute("utilisateur");
			statement.setString(1, util.getPseudo());
			resultat = statement.executeQuery();
			if (resultat.next()) {
				count = resultat.getInt(1);
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
		return count;
	}

	public int executerCountResultat(HttpSession session, String url, String utilisateur, String motDePasse,
			String result) {
		/* Connexion à la base de données */

		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		int count = 0;
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Création de l'objet gérant les requêtes */
			statement = connexion
					.prepareStatement("SELECT COUNT(resultat) FROM Historique WHERE (pseudo_util = ? AND resultat=?);");
			Utilisateur util = (Utilisateur) session.getAttribute("utilisateur");
			statement.setString(1, util.getPseudo());
			statement.setString(2, result);
			resultat = statement.executeQuery();
			if (resultat.next()) {
				count = resultat.getInt(1);
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
		return count;
	}
}
