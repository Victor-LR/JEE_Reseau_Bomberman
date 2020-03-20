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
 * Servlet implementation class CreationUtilisateur
 */
@WebServlet("/CreationUtilisateur")
public class CreationUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao dao;
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

	public void init() throws ServletException {
		this.dao = ((DAOFactory) getServletContext().getAttribute("daofactory")).getUtilisateurDao();
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

		Integer id = 0;
		String nom = request.getParameter(CHAMP_NOM);
		String prenom = request.getParameter(CHAMP_PRENOM);
		String mail = request.getParameter(CHAMP_MAIL);
		String pseudo = request.getParameter(CHAMP_PSEUDO);
		String mdp = request.getParameter(CHAMP_MDP);
		String mdp2 = request.getParameter(CHAMP_MDP2);
		// Vérification de chaque champ rempli par l'utilisateur
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
			Utilisateur u = dao.trouver(pseudo);
			validePseudo(pseudo, u);
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
		try {
			valideEmail(mail);
		} catch (Exception e) {
			erreur = true;
			request.setAttribute("erreuremail", e.getMessage());
		}
		if (erreur) {
			this.getServletContext().getRequestDispatcher("/creationUtilisateur.jsp").forward(request, response);
		} else {
			Utilisateur util = new Utilisateur(nom, prenom, pseudo, mdp, mail, id);
			dao.creer(util);
			request.setAttribute("succes", "Création réussie");
			session.setAttribute("utilisateur", util);
			this.getServletContext().getRequestDispatcher("/restreint/creationReussie.jsp").forward(request, response);
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

	public void validePseudo(String pseudo, Utilisateur u) throws Exception {
		if (pseudo.length() < 2)
			throw (new Exception("Le pseudo doit contenir 2 caractères !"));
		if (u != null && pseudo.matches(u.getPseudo()))
			throw (new Exception("Le pseudo existe déjà"));
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

	public void valideEmail(String email) throws Exception {
		if (email.isEmpty()) {
			throw (new Exception("Le champ doit être rempli !"));
		}
	}
}
