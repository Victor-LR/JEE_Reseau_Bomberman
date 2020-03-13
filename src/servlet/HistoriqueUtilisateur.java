package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Identifiant_BDD;
import beans.Partie;
import beans.Utilisateur;

/**
 * Servlet implementation class HistoriqueUtilisateur
 */
@WebServlet("/HistoriqueUtilisateur")
public class HistoriqueUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HistoriqueUtilisateur() {
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
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		ArrayList<Partie> listeParties = new ArrayList<>();
		listeParties = executerSelectHistorique(session, Identifiant_BDD.getUrljdbc(),
				Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd());
		request.setAttribute("listeParties", listeParties);
		this.getServletContext().getRequestDispatcher("/restreint/historiqueUtilisateur.jsp").forward(request,
				response);
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

	public ArrayList<Partie> executerSelectHistorique(HttpSession session, String url, String utilisateur,
			String motDePasse) {
		/* Connexion à la base de données */

		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		int count = 0;
		ArrayList<Partie> listeParties = new ArrayList<Partie>();
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Création de l'objet gérant les requêtes */
			statement = connexion.prepareStatement("SELECT * FROM Historique WHERE pseudo_util = ?;");
			Utilisateur util = (Utilisateur) session.getAttribute("utilisateur");
			statement.setString(1, util.getPseudo());
			resultat = statement.executeQuery();
			while (resultat.next()) {
				Partie partie = new Partie(resultat.getInt(1), resultat.getString(2), resultat.getDate(3),
						resultat.getInt(4), resultat.getString(5));
				listeParties.add(partie);
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
		return listeParties;
	}

}