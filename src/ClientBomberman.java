import game.SimpleGame;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;

import controleur.ControleurBombermanGame;
import controleur.ControllerSimpleGame;
import controleur.InterfaceController;
import game.BombermanGame;
import game.Game;
import view.ViewAuthenticator;
import view.ViewBombermanGame;
import view.ViewCommand;
import view.ViewSimpleGame;

public class ClientBomberman {
	
	public static void main(String[] args) {
		
		ViewAuthenticator VA = new ViewAuthenticator();
		
		while(!VA.getIdentifiant().matches("truc")) {
			
		}

		
		Socket socket;
		BufferedReader entree;
		PrintWriter sortie;
		String serveur = "localhost";
		int port = 3500;
			
		System.out.println("CLIENT");
		//serveur = args[0];
		//port = Integer.parseInt(args[1]);
		System.out.println("Port "+ port+ " adresse "+serveur);
		Scanner commande = new Scanner(System.in);
		
		try {
			socket = new Socket(serveur,port);
			sortie = new PrintWriter(socket.getOutputStream(), true);
			entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String chaine;
			
			VA.FermerFenetre();
			ControleurBombermanGame CBG = new ControleurBombermanGame(false);
			
			while(true) {
			
//				chaine = commande.nextLine();
				sortie.println("");
				if(!CBG.getJeu_bomberman().gameContinue()) {
					sortie.println(CBG.getJeu_bomberman().getMessage_fin_partie());
				}
				
//				if(chaine.equals("exit")) {
//					sortie.println(chaine);
//					socket.close();
//					break;
//				}
//				else
//					sortie.println(chaine);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			VA.FermerFenetre();
		}
		
		/*//ControleurBombermanGame CBG = new ControleurBombermanGame(false);

*/
		
	}

}
