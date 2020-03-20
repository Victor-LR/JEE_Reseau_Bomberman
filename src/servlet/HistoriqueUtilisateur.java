package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Partie;
import beans.Utilisateur;
import dao.DAOFactory;
import dao.HistoriqueDao;

/**
 * Servlet implementation class HistoriqueUtilisateur
 */
@WebServlet("/HistoriqueUtilisateur")
public class HistoriqueUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HistoriqueDao dao;

	public void init() throws ServletException {
		this.dao = ((DAOFactory) getServletContext().getAttribute("daofactory")).getHistoriqueDao();
	}

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
		Utilisateur util = (Utilisateur) session.getAttribute("utilisateur");
		ArrayList<Partie> listeParties = new ArrayList<>();
		listeParties = dao.listingParties(util.getPseudo());
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

}