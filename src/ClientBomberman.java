import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

import beans.Identifiant_BDD;
import controleur.ControleurBombermanGame;
import view.ViewAuthenticator;

public class ClientBomberman {

	public static void main(String[] args) {

		ViewAuthenticator VA = new ViewAuthenticator();
	//	Identifiant_BDD ID_BDD = new Identifiant_BDD();
		
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd() );
		    
		    Statement statement = connexion.createStatement();
		    String pseudoBdd = null;
		    String mdpBdd = null;
		    HashMap<String,String> Pseudo_mdp = new HashMap();
		    
		    System.out.println(connexion.isClosed());

			ResultSet resultat = statement.executeQuery("SELECT pseudo, mot_de_passe FROM Utilisateur ");
			// + " WHERE (pseudo = \""+VA.getIdentifiant()+"\") & (mot_de_passe =
			// \""+VA.getMot_de_passe()+"\");" );

			while (resultat.next()) {
				pseudoBdd = resultat.getString("pseudo");
				mdpBdd = resultat.getString("mot_de_passe");

				Pseudo_mdp.put(pseudoBdd, mdpBdd);
			}

			boolean bonMdp;
			do {
				String MPD = (String) Pseudo_mdp.get(VA.getIdentifiant());
				System.out.print("");
				bonMdp = false;
				if (MPD != null) {
					if (MPD.matches(VA.getMot_de_passe()))
						bonMdp = true;
				}

			} while (!Pseudo_mdp.containsKey(VA.getIdentifiant()) || !bonMdp);

			pseudoBdd = VA.getIdentifiant();
			Socket socket;
			BufferedReader entree;
			PrintWriter sortie;
			String serveur = "localhost";
			int port = 35000;

			System.out.println("CLIENT");
			// serveur = args[0];
			// port = Integer.parseInt(args[1]);
			System.out.println("Port " + port + " adresse " + serveur);
			Scanner commande = new Scanner(System.in);

			try {
				socket = new Socket(serveur, port);
				sortie = new PrintWriter(socket.getOutputStream(), true);
				entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String chaine;

				VA.FermerFenetre();
				ControleurBombermanGame CBG = new ControleurBombermanGame(false,pseudoBdd);
				boolean firstTime = true;

				while (true) {

					chaine = "";
//					chaine = commande.nextLine();
//					sortie.println("_");
					System.out.print("");
					if (!CBG.getJeu_bomberman().isRunning() && CBG.getJeu_bomberman().getTurn() > 0
							&& !CBG.getJeu_bomberman().gameContinue()) {
						if (firstTime) {
							firstTime = false;
							sortie.println(CBG.getJeu_bomberman().getMessage_fin_partie());
							sortie.println(pseudoBdd);
							sortie.println(CBG.getJeu_bomberman().getPointsPartie());
						}
					} else {
						firstTime = true;
					}

//					if(chaine.equals("exit")) {
//						sortie.println(chaine);
//						socket.close();
//						break;
//					}
//					else
//						sortie.println(chaine);
				}

			} catch (IOException e) {
				e.printStackTrace();
				VA.FermerFenetre();
			}

		} catch (SQLException e) {
			VA.FermerFenetre();
			System.out.println("Impossible de se connecter à la base de donnée");

		} finally {
			if (connexion != null)
				try {
					/* Fermeture de la connexion */
					connexion.close();
				} catch (SQLException e) {

				}
		}

	}

}
