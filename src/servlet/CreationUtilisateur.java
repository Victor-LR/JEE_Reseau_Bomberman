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
 * Servlet implementation class CreationUtilisateur
 */
@WebServlet("/CreationUtilisateur")
public class CreationUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String CHAMP_NOM = "nomUtilisateur";
	public static final String CHAMP_PRENOM = "prenomUtilisateur";
	public static final String CHAMP_MAIL = "emailUtilisateur";
	public static final String CHAMP_PSEUDO = "pseudoUtilisateur";
	public static final String CHAMP_MDP = "mdpUtilisateur";
	public static final String CHAMP_MDP2 = "mdp2Utilisateur";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreationUtilisateur() {
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
		this.getServletContext().getRequestDispatcher("/creationUtilisateur.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean erreur = false;
		HttpSession session = request.getSession();

		String nom = request.getParameter(CHAMP_NOM);
		String prenom = request.getParameter(CHAMP_PRENOM);
		String mail = request.getParameter(CHAMP_MAIL);
		String pseudo = request.getParameter(CHAMP_PSEUDO);
		String mdp = request.getParameter(CHAMP_MDP);
		String mdp2 = request.getParameter(CHAMP_MDP2);
		try {
			valideNom(nom);
		} catch (Exception e) {
			erreur = true;
			request.setAttribute("erreurnom", e.getMessage());
		}
		try {
			validePrenom(prenom);
		} catch (Exception e) {
			erreur = true;
			request.setAttribute("erreurprenom", e.getMessage());
		}
		try {
			validePseudo(pseudo);
		} catch (Exception e) {
			erreur = true;
			request.setAttribute("erreurpseudo", e.getMessage());
		}
		try {
			valideMdp(mdp);
		} catch (Exception e) {
			erreur = true;
			request.setAttribute("erreurmdp", e.getMessage());
		}
		try {
			valideMdp2(mdp, mdp2);
		} catch (Exception e) {
			erreur = true;
			request.setAttribute("erreurmdp2", e.getMessage());
		}
		if (erreur) {
			this.getServletContext().getRequestDispatcher("/creationUtilisateur.jsp").forward(request, response);
		} else {
			Utilisateur util = new Utilisateur(nom, prenom, pseudo, mdp, mail);
			executerInsert(request);
			request.setAttribute("succes", "Création réussie");
			session.setAttribute("pseudo", pseudo);
			this.getServletContext().getRequestDispatcher("/WEB-INF/accueilUtilisateur.jsp").forward(request, response);
		}
	}

	public void valideNom(String nom) throws Exception {
		if (nom.length() < 2) {
			throw (new Exception("Le nom doit contenir 2 caractères !"));
		}
	}

	public void validePrenom(String prenom) throws Exception {
		if (prenom.length() < 2)
			throw (new Exception("Le prénom doit contenir 2 caractères !"));
	}

	public void validePseudo(String pseudo) throws Exception {
		if (pseudo.length() < 2)
			throw (new Exception("Le pseudo doit contenir 2 caractères !"));
	}

	public void valideMdp(String mdp) throws Exception {
		if (mdp.length() < 4) {
			throw (new Exception("Le mot de passe doit contenir 8 caractères !"));
		}
	}

	public void valideMdp2(String mdp, String mdp2) throws Exception {
		if (!(mdp2.matches(mdp))) {
			throw (new Exception("Le mot de passe doit être le meme que le premier !"));
		}
	}

	public void executerInsert(HttpServletRequest request) {
		/* Connexion à la base de données */
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		String url = "jdbc:mysql://localhost:3306/bdd_bomberman";
		String utilisateur = "root";
		String motDePasse = "ce1mdpp";
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		int statut = 0;
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Création de l'objet gérant les requêtes */
			statement = connexion.prepareStatement(
					"INSERT INTO Utilisateur (email, mot_de_passe, nom, prenom, pseudo, date_inscription) VALUES(?, MD5(?), ?, ?, ?, NOW());");
			String paramEmail = request.getParameter(CHAMP_MAIL);
			String paramMdp = request.getParameter(CHAMP_MDP);
			String paramNom = request.getParameter(CHAMP_NOM);
			String paramPrenom = request.getParameter(CHAMP_PRENOM);
			String paramPseudo = request.getParameter(CHAMP_PSEUDO);
			statement.setString(1, paramEmail);
			statement.setString(2, paramMdp);
			statement.setString(3, paramNom);
			statement.setString(4, paramPrenom);
			statement.setString(5, paramPseudo);
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
