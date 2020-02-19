package strategie;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import game.BombermanGame;

public class Strategie_Aleatoire implements Comportement {

	public AgentAction doAction(Agent agent, ArrayList<Agent> agents) {
		
		AgentAction[] listaction = AgentAction.values();
		int action_random = (int) (Math.random()*listaction.length);
		
		if (BombermanGame.isLegalMove(agent, listaction[action_random]))
			return listaction[action_random];
		else
			return AgentAction.STOP;
		
	}

	@Override
	public AgentAction doActionPerceptron(Agent agent, ArrayList<Agent> agents, BombermanGame BBMG) {
		// TODO Auto-generated method stub
		return null;
	}

}
