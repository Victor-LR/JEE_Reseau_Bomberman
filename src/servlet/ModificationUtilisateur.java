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
import dao.HistoriqueDao;
import dao.UtilisateurDao;

/**
 * Servlet implementation class ModificationUtilisateur
 */
@WebServlet("/ModificationUtilisateur")
public class ModificationUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CHAMP_NOM = "nomUtilisateur";
	public static final String CHAMP_PRENOM = "prenomUtilisateur";
	public static final String CHAMP_MAIL = "emailUtilisateur";
	public static final String CHAMP_PSEUDO = "pseudoUtilisateur";
	public static final String CHAMP_MDP = "mdpUtilisateur";
	public static final String CHAMP_MDP2 = "mdp2Utilisateur";
	private UtilisateurDao daoUtilisateur;
	private HistoriqueDao daoHistorique;

	public void init() throws ServletException {
		this.daoUtilisateur = ((DAOFactory) getServletContext().getAttribute("daofactory")).getUtilisateurDao();
		this.daoHistorique = ((DAOFactory) getServletContext().getAttribute("daofactory")).getHistoriqueDao();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModificationUtilisateur() {
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
		this.getServletContext().getRequestDispatcher("/restreint/modificationUtilisateur.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		String nom = request.getParameter(CHAMP_NOM);
		String prenom = request.getParameter(CHAMP_PRENOM);
		String mail = request.getParameter(CHAMP_MAIL);
		String pseudo = request.getParameter(CHAMP_PSEUDO);
		String mdp = request.getParameter(CHAMP_MDP);
		String mdp2 = request.getParameter(CHAMP_MDP2);
		boolean erreur = false;
		HttpSession session = request.getSession();
		Utilisateur util = (Utilisateur) session.getAttribute("utilisateur");
		String exPseudo = util.getPseudo();

		// Vérification des champs
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
			if (!pseudo.matches(exPseudo)) {
				Utilisateur u = daoUtilisateur.trouver(pseudo);
				validePseudo(pseudo, u);
			}
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
			this.getServletContext().getRequestDispatcher("/restreint/modificationUtilisateur.jsp").forward(request,
					response);
		} else {
			util.setEmail(mail);
			util.setNom(nom);
			util.setPrenom(prenom);
			util.setPseudo(pseudo);
			util.setMdp(mdp);
			daoUtilisateur.modifier(util);
			daoHistorique.modifier(util, exPseudo);
			session.setAttribute("utilisateur", util);
			this.getServletContext().getRequestDispatcher("/restreint/modificationReussie.jsp").forward(request,
					response);
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
