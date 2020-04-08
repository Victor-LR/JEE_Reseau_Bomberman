package client;
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

import view.ViewAuthenticator;

public class ClientBomberman {

	public static void main(String[] args) {

		//Lance la boîte de dialogue d'authentification 
		ViewAuthenticator VA = new ViewAuthenticator();
		
		Connection connexion = null;
		try {
			//Connexion à la base de données
		    connexion = DriverManager.getConnection( Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd() );
		    
		    Statement statement = connexion.createStatement();
		    String pseudoBdd = null;
		    String mdpBdd = null;
		    HashMap<String,String> Pseudo_mdp = new HashMap();
		    

			ResultSet resultat = statement.executeQuery("SELECT pseudo, mot_de_passe FROM Utilisateur ");

			//On récupère les couples pseudos,mdp de la bdd
			while (resultat.next()) {
				pseudoBdd = resultat.getString("pseudo");
				mdpBdd = resultat.getString("mot_de_passe");

				Pseudo_mdp.put(pseudoBdd, mdpBdd);
			}

			boolean bonMdp;
			//On vérifie que le pseudo et le mdp saisis correspondent bien à un couple pseudo,mdp de la bdd
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
			System.out.println("Port " + port + " adresse " + serveur);

			try {
				//Connexion au ServerSocket
				socket = new Socket(serveur, port);
				sortie = new PrintWriter(socket.getOutputStream(), true);
				entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String chaine;
				
				//On ferme la fenêtre d'authentification
				VA.FermerFenetre();
				boolean isRunning = true;
				
				//On envoi le pseudo du joueur au serveur
				sortie.println(pseudoBdd);
				
				//Création du panneau de commande pour controler le bomberman
				PanelControl panneauClient = new PanelControl();
				
				while (isRunning) {

					panneauClient.getJFrame().requestFocusInWindow();
					System.out.print("");
					
					//Récupération des informations envoyé par le serveur
					chaine = entree.readLine();
					
					//Envoi de l'action saisi au serveur
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
