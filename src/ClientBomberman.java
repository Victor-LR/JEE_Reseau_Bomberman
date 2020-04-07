import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
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

import javax.swing.JFrame;

import beans.Identifiant_BDD;
import key.Keys;
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
			int port = 36000;

			System.out.println("CLIENT");
			// serveur = args[0];
			// port = Integer.parseInt(args[1]);
			System.out.println("Port " + port + " adresse " + serveur);
			Scanner commande = new Scanner(System.in);

			try {
				socket = new Socket(serveur, port);
				sortie = new PrintWriter(socket.getOutputStream(), true);
				entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
				String chaine;

				VA.FermerFenetre();
				//ControleurBombermanGame CBG = new ControleurBombermanGame(false,pseudoBdd);
				boolean firstTime = true;
				boolean isRunning = true;
				sortie.println(pseudoBdd);
				
				PanelControl panneauClient = new PanelControl();
				
				while (isRunning) {

					panneauClient.getJFrame().requestFocusInWindow();
					
					//chaine = "";
					System.out.print("");
					chaine = entree.readLine();

					sortie.println(panneauClient.getKey_1().getKaction());
					
					if(chaine.matches("FERMER")) {
						panneauClient.getJFrame().dispose();
						socket.close();
						isRunning = false;
					}
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
