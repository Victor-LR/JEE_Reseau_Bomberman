package serveur;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServThread implements Runnable {

	protected boolean isRunning = true;
	private Thread thread;
	
	Socket socket;
	BufferedReader entree;
	DataOutputStream sortie;
	String chainerecue = "";
	
	
	public ServThread(ServerSocket ecoute) {
		
		try {
			socket = ecoute.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		
		try {
		
		while(isRunning) {

			entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sortie = new DataOutputStream(socket.getOutputStream());
			chainerecue = entree.readLine();
			
			if(!chainerecue.equals(""))
				System.out.println("     "+chainerecue);
			
			if(chainerecue.equals("exit")) {
				stop();
				socket.close();
				break;
				
			}
			
//			try {
//				this.wait();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

//				System.out.println("Le nombre inconnu est compris entre " + borne_min + " et " + borne_max);
//			}
			
		}

		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	
	

}
