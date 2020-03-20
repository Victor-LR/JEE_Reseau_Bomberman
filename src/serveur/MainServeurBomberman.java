package serveur;

import java.io.IOException;
import java.net.ServerSocket;

import game.BombermanGame;

public class MainServeurBomberman {

	public static void main(String[] args) {
		int port = 36000;
		ServerSocket ecoute;
		BombermanGame game;
		
			try {
				
				//port = Integer.parseInt(args[0]);
				ecoute = new ServerSocket(port);
				System.out.println("SERVEUR");
				System.out.println(ecoute.getLocalPort());
				System.out.println(ecoute.getInetAddress().getHostName());
				
				while(true) {
					String chainerecue ;
					ServThread servT = new ServThread(ecoute);
					servT.init();
					chainerecue = servT.getChainerecue();
					
				}
				
				} catch (IOException e) {
					e.printStackTrace();
				}


	}
	
}
