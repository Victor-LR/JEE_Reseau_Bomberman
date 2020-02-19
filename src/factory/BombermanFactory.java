package factory;

import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;
import agents.ColorAgent;
import strategie.Comportement;
import strategie.Strategie_Aleatoire;
import strategie.Strategie_Bird;
import strategie.Strategie_Joueur1;
import strategie.Strategie_Joueur2;

public class BombermanFactory extends AgentFactory{

	public Agent createAgent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible,boolean isSick,String nom_strategie) {
		// TODO Auto-generated method stub
		switch (nom_strategie) {
		case "Aleatoire.java":
			return new Agent_Bomberman(x, y, agentAction, type, color, isInvincible, isSick,new Strategie_Aleatoire());
		case "Bird.java":
			return new Agent_Bomberman(x, y, agentAction, type, color, isInvincible, isSick,new Strategie_Bird());
		case "Joueur1.java":
			return new Agent_Bomberman(x, y, agentAction, type, color, isInvincible, isSick,new Strategie_Joueur1());
		case "Joueur2.java":
			return new Agent_Bomberman(x, y, agentAction, type, color, isInvincible, isSick,new Strategie_Joueur2());
		default:
			return new Agent_Bomberman(x, y, agentAction, type, color, isInvincible, isSick,new Strategie_Aleatoire());
		}
		
	}

}
