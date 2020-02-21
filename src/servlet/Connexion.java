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
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		String urlJDBC = "jdbc:mysql://localhost:3306/bdd_bomberman";
		String utilisateurBdd = "root";
		String motDePasseBdd = "ce1mdpp";

		String pseudo = request.getParameter(CHAMP_PSEUDO);
		String mdp = request.getParameter(CHAMP_MDP);
		HttpSession session = request.getSession();
		boolean erreur = false;
		try {
			validePseudo(pseudo);
		} catch (Exception e) {
			erreur = true;
			request.setAttribute("erreurpseudo", e.getMessage());
		}
		try {
			valideMdp(request, urlJDBC, utilisateurBdd, motDePasseBdd);
		} catch (Exception e) {
			erreur = true;
			request.setAttribute("erreurmdp", e.getMessage());
		}
		if (erreur)
			this.getServletContext().getRequestDispatcher("/accueil.jsp").forward(request, response);
		else {
			session.setAttribute("pseudo", pseudo);
			this.getServletContext().getRequestDispatcher("/WEB-INF/accueilUtilisateur.jsp").forward(request, response);
		}
	}

	public void validePseudo(String pseudo) throws Exception {
		if (pseudo.isEmpty()) {
			throw new Exception("Veuillez remplir le champ");
		}
	}

	public void valideMdp(HttpServletRequest request, String url, String utilisateur, String motDePasse)
			throws Exception {
		/* Connexion à la base de données */
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultat = null;
		String pseudo = request.getParameter(CHAMP_PSEUDO);
		String mdp = request.getParameter(CHAMP_MDP);
		String mdpBdd = "";
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Création de l'objet gérant les requêtes */
			statement = connexion.prepareStatement("SELECT * FROM Utilisateur WHERE pseudo = ?;");
			statement.setString(1, pseudo);
			/* Exécution d'une requête de lecture */
			resultat = statement.executeQuery();

			/* Récupération des données du résultat de la requête de lecture */
			while (resultat.next()) {
				int idUtilisateur = resultat.getInt("id");
				String emailUtilisateur = resultat.getString("email");
				mdpBdd = resultat.getString("mot_de_passe");
				String nomUtilisateur = resultat.getString("nom");
				/* Formatage des données pour affichage dans la JSP finale. */
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
		System.out.println(mdp + "  " + mdpBdd);
		if (!mdp.matches(mdpBdd))
			throw new Exception("Mot de passe incorrect");
	}
}