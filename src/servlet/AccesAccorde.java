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

/**
 * Servlet implementation class AccesAccorde
 */
@WebServlet("/AccesAccorde")
public class AccesAccorde extends HttpServlet {

	private HistoriqueDao dao;
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		this.dao = ((DAOFactory) getServletContext().getAttribute("daofactory")).getHistoriqueDao();
	}

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
		Utilisateur util = (Utilisateur) session.getAttribute("utilisateur");
		int total;
		float victoire, defaite;
		float ratio = 0;
		total = dao.totalPartie(util.getPseudo());
		victoire = dao.countResultat(util.getPseudo(), "V");
		defaite = dao.countResultat(util.getPseudo(), "D");
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
}
