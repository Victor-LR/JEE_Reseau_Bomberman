package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import agents.AgentAction;
import beans.Identifiant_BDD;
import controleur.ControleurBombermanGame;

public class ServThread implements Runnable {

	protected boolean isRunning = true;
	private Thread thread;

	private Socket socket;
	private BufferedReader entree;
	private PrintWriter sortie;
	Connection connexion = null;

	public ServThread(ServerSocket ecoute) {
		try {
			socket = ecoute.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void run() {

		try {
			entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sortie = new PrintWriter(socket.getOutputStream(), true);
			
			//Récupération du pseudo du joueur
			String pseudo = entree.readLine();

			//Lance le jeu Bomberman
			ControleurBombermanGame CBG = new ControleurBombermanGame(false, pseudo);
			
			boolean FirstTime = true;
			
			while (isRunning) {

				sortie.println("RIEN");
				//Récupère l'action du envoyé par le client
				String RecupAction_string = entree.readLine();
				AgentAction RecupAction = AgentAction.valueOf(RecupAction_string);
				
				//Transmet l'action récupéré au bomberman du Jeu
				CBG.getJeu_bomberman().setActionClient(RecupAction);

				//Vérifie si la partie est finie
				if (CBG.getJeu_bomberman().isFin_partie()) {

					//Boolean qui empêche d'envoyer le résultat de la partie plus d'une fois dans la bdd
					if (FirstTime) {
						FirstTime = false;
						String resultat;
						if (CBG.getJeu_bomberman().getMessage_fin_partie().matches("Plus d'ennemies !"))
							resultat = "V";
						else
							resultat = "D";
						int score_int = CBG.getJeu_bomberman().getPointsPartie();

						System.out.println(pseudo + "  " + resultat + "   " + score_int);

						try {
							//Connexion à la bdd et envoi des informations
							connexion = DriverManager.getConnection(Identifiant_BDD.getUrljdbc(),
									Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd());
							Statement statement = connexion.createStatement();

							int statut = statement.executeUpdate(
									"INSERT INTO Historique (pseudo_util, date_partie , score, resultat)" + "VALUES ('"
											+ pseudo + "', NOW(), " + score_int + ", '" + resultat + "');");

						} catch (SQLException e) {
							e.printStackTrace();
						} finally {
							if (connexion != null)
								try {
									connexion.close();
								} catch (SQLException ignore) {

								}
						}
					}
				} else {
					FirstTime = true;
				}
				System.out.print("");
				//Quand on ferme le jeu, on ferme aussi le thread
				if (CBG.isExit()) {
					sortie.println("FERMER");
					stop();
					socket.close();
					isRunning = false;
					System.out.println("Déconnexion Client");
				}

			}

		} catch (IOException e) {

		}

	}

	public void stop() {
		isRunning = false;
	}

	public void init() {
		System.out.println("Connexion Client");
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

}
