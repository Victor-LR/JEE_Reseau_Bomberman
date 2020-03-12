package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Classement;

/**
 * Servlet implementation class ClassementServl
 */
@WebServlet("/ClassementServl")
public class ClassementServl extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
