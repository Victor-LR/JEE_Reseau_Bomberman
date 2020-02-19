package perceptron;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;
import controleur.ControleurBombermanGame;
import game.BombermanGame;
import perceptron.SparseVector;
import map.Map;
import strategie.Comportement;

public class Reward {
	
	public void getAverageReward(int nb_simulations, int max_turn, Comportement strategie, Map map ) {
		
		int scoreFinal=0 ;
		
		ArrayList<ControleurBombermanGame> List_Control = new ArrayList<ControleurBombermanGame>();

		
		for (int i = 0 ; i < nb_simulations ; i++){
			ControleurBombermanGame Control_Percep = new ControleurBombermanGame(true);
			Control_Percep.setMap(map);
			Control_Percep.getJeu_bomberman().setListAgentsStart(Control_Percep.getMap().getStart_agents());
			
			ArrayList<String> nom_strats = new ArrayList();
			nom_strats.add("Aléatoire");
			Control_Percep.getJeu_bomberman().setNom_strats(nom_strats);
			
			Control_Percep.getJeu_bomberman().init();
			Control_Percep.getJeu_bomberman().setMaxTurn(max_turn);
			for (Agent a : Control_Percep.getJeu_bomberman().getAgentList()) {
				if(a.getType() == 'B') {
					a.setStrategie(strategie);
					((Agent_Bomberman)a).setVies(1);
				}
					
			}
			
			Control_Percep.start();
			
			
			List_Control.add(Control_Percep);
		}
		
		for(int j = 0 ; j < List_Control.size(); j++){
			try {
				List_Control.get(j).getJeu_bomberman().getThread().join();
				System.out.println("	Attente Thread n°"+j);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("erreur !");
			}
				
		}
		System.out.println(" Fin des Thread");
		for(int j = 0 ; j < List_Control.size(); j++){

			scoreFinal += List_Control.get(j).getJeu_bomberman().getPointsPartie();
			
		}
		SparseVector etat = getEtat(List_Control.get(0).getJeu_bomberman(),List_Control.get(0).getJeu_bomberman().getAgentList().get(0));
		//System.out.println(getEtatAction(etat,AgentAction.PUT_BOMB).toString());
		System.out.println("Moyenne des scores :"+scoreFinal/nb_simulations);
	}
	
	
	public void vizualize(int max_turn, Comportement strategie, Map map, int time_wait) {
		
		ControleurBombermanGame Control_Percep = new ControleurBombermanGame(false);
		Control_Percep.setMap(map);
		
		Control_Percep.getJeu_bomberman().setListAgentsStart(Control_Percep.getMap().getStart_agents());
		ArrayList<String> nom_strats = new ArrayList();
		nom_strats.add("Aléatoire");
		Control_Percep.getJeu_bomberman().setNom_strats(nom_strats);
		
		Control_Percep.getJeu_bomberman().init();
		Control_Percep.getJeu_bomberman().setTime(time_wait);
		Control_Percep.getJeu_bomberman().setMaxTurn(max_turn);
		for (Agent a : Control_Percep.getJeu_bomberman().getAgentList()) {
			if(a.getType() == 'B') {
				a.setStrategie(strategie);
				((Agent_Bomberman)a).setVies(1);
			}
		}
		Control_Percep.setPerceptron(true);
		Control_Percep.start();
	}
	
	public SparseVector getEtat(BombermanGame Jeu_bbm , Agent agent)
	{
		SparseVector vec1 = new SparseVector(75);
		

		
		//position agent (bomberman) 
		int x = agent.getX();
		int y = agent.getY();
		
		//int ind_actions = 0;
		int point_depart_x = x-2;
		int point_depart_y = y-2;
		
		
		//Positionne les informations dans le Sparcevector		
		//for(int k = 0 ; k <5 ; k++) {
			
			
			for(int i = 0; i < 5 ; i++) {
				for(int j = 0; j < 5; j++) {
	
					//vec1.setValue(j+1*i+1, 1);
					
					if(Jeu_bbm.Isennemie(point_depart_x + j, point_depart_y + i))
						vec1.setValue((i*5)+j, 1);
					
					if(Jeu_bbm.IsBombe(point_depart_x + j, point_depart_y + i))
						vec1.setValue(25+(i*5)+j, 1);
					
					if(Jeu_bbm.IsMur(point_depart_x + j, point_depart_y + i))
						vec1.setValue(50+(i*5)+j, 1);
					
				}
			}
			
			//ind_actions+=75;
		
		//}
		
		//System.out.println(vec1.toString());
		return(vec1);
	}
	
	public SparseVector getEtatAction(SparseVector Etat , AgentAction action) {
		
		SparseVector vecPhi = new SparseVector(75*5+1);
		
		//valeur de la première position du SparceVector à 1
		vecPhi.setValue(0, 1);
		
		switch(action) {
		case MOVE_DOWN:
			for (int i = 0 ; i < 75 ; i++)
				vecPhi.setValue(i+1, Etat.getValue(i));
			break;
		case MOVE_UP:
			for (int i = 0 ; i < 75 ; i++)
				vecPhi.setValue(i+1+75, Etat.getValue(i));
			break;
		case MOVE_RIGHT:
			for (int i = 0 ; i < 75 ; i++)
				vecPhi.setValue(i+1+150, Etat.getValue(i));
			break;
		case MOVE_LEFT:
			for (int i = 0 ; i < 75 ; i++)
				vecPhi.setValue(i+1+225, Etat.getValue(i));
			break;
		case PUT_BOMB:
			for (int i = 0 ; i < 75 ; i++)
				vecPhi.setValue(i+1+300, Etat.getValue(i));
			break;
		}
		
		return vecPhi;
		
	}
}
