package dao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitialisationDAOFactory implements ServletContextListener {

	private DAOFactory daoFactory;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		ServletContext servlet = event.getServletContext();
		this.daoFactory = DAOFactory.getInstance();
		servlet.setAttribute("daofactory", daoFactory);
	}

}
