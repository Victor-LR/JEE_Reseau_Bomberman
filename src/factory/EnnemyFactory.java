package factory;

import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bird;
import agents.Agent_Ennemi;
import agents.Agent_Ennemi_Simple;
import agents.Agent_Rajion;
import agents.ColorAgent;
import strategie.Comportement;
import strategie.Strategie_Aleatoire;
import strategie.Strategie_Bird;

public class EnnemyFactory extends AgentFactory{

	public Agent createAgent(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible, boolean isSick,String nom_strategie) {
		// TODO Auto-generated method stub
		switch(String.valueOf(type)) {
			
			case "E":
				return new Agent_Ennemi_Simple(x, y, agentAction, type, color, isInvincible, isSick, new Strategie_Aleatoire());
				
			case "V":
				return new Agent_Bird(x, y, agentAction, type, color, isInvincible, isSick,new Strategie_Bird());
				
			case "R":
				return new Agent_Rajion(x, y, agentAction, type, color, isInvincible, isSick,new Strategie_Aleatoire());
				
			default:
				break;
		}
		return null;
	}

}
