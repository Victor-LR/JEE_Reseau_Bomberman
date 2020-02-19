package strategie;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import game.BombermanGame;
import key.Keys_2;

public class Strategie_Joueur2 implements Comportement {
public static Keys_2 key_2 ;
	
	public Strategie_Joueur2 () {
		key_2 = BombermanGame.getKey_2();
	}
	
	@Override
	public AgentAction doAction(Agent agent, ArrayList<Agent> agents) {

		
		if (BombermanGame.isLegalMove(agent,key_2.getKaction())) {
			return key_2.getKaction();
			
		}else
			return AgentAction.STOP;

	}

	@Override
	public AgentAction doActionPerceptron(Agent agent, ArrayList<Agent> agents, BombermanGame BBMG) {
		// TODO Auto-generated method stub
		return null;
	}
}
