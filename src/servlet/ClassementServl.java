package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Classement;
import dao.DAOFactory;
import dao.HistoriqueDao;

/**
 * Servlet implementation class ClassementServl
 */
@WebServlet("/ClassementServl")
public class ClassementServl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HistoriqueDao dao;
	public static final String CHAMP_MOIS = "MONTH(date_partie)=MONTH(NOW())";
	public static final String CHAMP_JOUR = "DAY(date_partie)=DAY(NOW())";

	public void init() throws ServletException {
		this.dao = ((DAOFactory) getServletContext().getAttribute("daofactory")).getHistoriqueDao();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClassementServl() {
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
		Classement classement = new Classement();
		try {
			// Liste des joueurs qui sont triés lors de la récupération dans la base en
			// fonction du nombre de victoire/ratio jour/mois
			classement.setClassementJournalier(dao.trouverClassement(CHAMP_JOUR));
			classement.setClassementMensuel(dao.trouverClassement(CHAMP_MOIS));
			classement.setClassementJournalierRatio(dao.trouverRatio(classement.getClassementJournalier(), CHAMP_JOUR));
			classement.setClassementMensuelRatio(dao.trouverRatio(classement.getClassementMensuel(), CHAMP_MOIS));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("classementJournalier", classement.getClassementJournalier());
		request.setAttribute("classementMensuel", classement.getClassementMensuel());
		request.setAttribute("classementJournalierRatio", classement.getClassementJournalierRatio());
		request.setAttribute("classementMensuelRatio", classement.getClassementMensuelRatio());
		this.getServletContext().getRequestDispatcher("/classement.jsp").forward(request, response);
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
