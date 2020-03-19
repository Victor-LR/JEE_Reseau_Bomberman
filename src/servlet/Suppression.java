package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDao;

/**
 * Servlet implementation class Suppression
 */
@WebServlet("/Suppression")
public class Suppression extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao dao;
	public static final String SESSION_UTILISATEUR = "utilisateur";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public void init() throws ServletException {
		this.dao = ((DAOFactory) getServletContext().getAttribute("daofactory")).getUtilisateurDao();
	}

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
		Utilisateur utilisateurSession = (Utilisateur) request.getSession().getAttribute("utilisateur");
		dao.supprimer(utilisateurSession.getIdentifiant());
		this.getServletContext().getRequestDispatcher("/suppression.jsp").forward(request, response);
	}

}
