package serveur;

import java.io.IOException;
import java.net.ServerSocket;

public class MainServeurBomberman {

	public static void main(String[] args) {
		int port = 36000;
		ServerSocket ecoute;
		
			try {
				//Création du serveur socket
				ecoute = new ServerSocket(port);
				System.out.println("SERVEUR");
				System.out.println(ecoute.getLocalPort());
				System.out.println(ecoute.getInetAddress().getHostName());
				
				while(true) {
					//Appel à un thread, nécessaire pour gérer plusieurs clients simultanément 
					ServThread servT = new ServThread(ecoute);
					servT.init();
					
				}
				
				} catch (IOException e) {
					e.printStackTrace();
				}


	}
	
}
