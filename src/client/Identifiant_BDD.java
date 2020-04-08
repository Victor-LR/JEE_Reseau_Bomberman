package client;

//Indentifiants utilisés par le client pour se connecter à la BDD
public class Identifiant_BDD {

	private final static String urlJDBC = "jdbc:mysql://localhost:3306/bdd_bomberman";
	private final static String utilisateurBdd = "root";
	private final static String motDePasseBdd = "mysql";

	public static String getUrljdbc() {
		return urlJDBC;
	}

	public static String getUtilisateurBdd() {
		return utilisateurBdd;
	}

	public static String getMotDePasseBdd() {
		return motDePasseBdd;
	}

}
