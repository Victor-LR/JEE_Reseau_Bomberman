package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDao;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CHAMP_PSEUDO = "pseudoUtilisateur";
	public static final String CHAMP_MDP = "mdpUtilisateur";
	private UtilisateurDao dao;

	public void init() throws ServletException {
		this.dao = ((DAOFactory) getServletContext().getAttribute("daofactory")).getUtilisateurDao();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Connexion() {
		super();
		// TODO Auto-generated constructor stub
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
			util = dao.trouver(pseudo);
		} catch (Exception e) {
			erreur = true;
			request.setAttribute("erreurpseudo", e.getMessage());
		}
		try {
			valideMdp(util, mdp);
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

	public void valideMdp(Utilisateur util, String motDePasse) throws Exception {
		if (!util.getMdp().matches(motDePasse))
			throw new Exception("Mot de passe incorrect");
	}
}