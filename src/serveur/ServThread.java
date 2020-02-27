package serveur;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import servlet.Identifiant_BDD;
import view.ViewAuthenticator;

public class ServThread implements Runnable {

	protected boolean isRunning = true;
	private Thread thread;

	private Socket socket;
	private BufferedReader entree;
	private DataOutputStream sortie;
	private ObjectInputStream entree_obj;
	private String chainerecue = "";
//	private boolean Suspendre;
	
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

//		Suspendre = false;
		try {

			while (isRunning) {

				entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				sortie = new DataOutputStream(socket.getOutputStream());

				chainerecue = entree.readLine();

				if (chainerecue.equals("Plus d'ennemies !") || chainerecue.equals("Plus de bomberman !")
						|| chainerecue.equals("Temps écoulé !")) {
					System.out.println("Fin partie -> " + chainerecue);
//				this.Suspendre = true;
				
				String resultat;
				if(chainerecue.equals("Plus d'ennemies !"))
					resultat="V";
				else
					resultat="D";
				
				String pseudo = entree.readLine();
				String score = entree.readLine();
				int score_int = Integer.parseInt(score);
					
				System.out.println(pseudo+"  "+resultat+"   "+score_int);
				
				try {
					connexion = DriverManager.getConnection( Identifiant_BDD.getUrljdbc(), Identifiant_BDD.getUtilisateurBdd(), Identifiant_BDD.getMotDePasseBdd());
					 Statement statement = connexion.createStatement();
					 
					int statut = statement.executeUpdate( "INSERT INTO Historique (pseudo_util, date_partie , score, resultat)"
							+ "VALUES ('"+pseudo+"', NOW(), "+score_int+", '"+resultat+"');" );
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
				    if ( connexion != null )
				        try {
				            connexion.close();
				        } catch ( SQLException ignore ) {

					String resultat;
					if (chainerecue.equals("Plus d'ennemies !"))
						resultat = "V";
					else
						resultat = "D";

					String pseudo = entree.readLine();
					String score = entree.readLine();
					int score_int = Integer.parseInt(score);

					System.out.println(pseudo + "  " + resultat + "   " + score_int);

					try {
						connexion = DriverManager.getConnection(urlJDBC, utilisateurBdd, motDePasseBdd);
						Statement statement = connexion.createStatement();

						int statut = statement
								.executeUpdate("INSERT INTO Historique (pseudo_util, date_partie , score, resultat)"
										+ "VALUES ('" + pseudo + "', NOW(), " + score_int + ", '" + resultat + "');");

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

				if (chainerecue.equals("exit")) {
					stop();
					socket.close();
					break;

				}

//			while(Suspendre) {
//				Thread.sleep(100);
//			}

			}

		} catch (IOException | NullPointerException e) {
			System.out.println("Déconnexion");
		}

	}

	public void stop() {
		isRunning = false;
	}

	public void init() {
		System.out.println("Connexion");
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public String getChainerecue() {
		return chainerecue;
	}

	public void setCSortie(String ChaineSortie) {
		try {
			sortie.writeChars(ChaineSortie);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public boolean isSuspendre() {
//		return Suspendre;
//	}
//	public void setSuspendre(boolean suspendre) {
//		Suspendre = suspendre;
//	}

}
