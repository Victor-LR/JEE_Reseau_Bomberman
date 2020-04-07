package strategie;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import game.BombermanGame;
import key.Keys;

public class Strategie_Joueur1 implements Comportement {
	
	//public static Keys key_1 ;
	public AgentAction ActionC;
	
	public Strategie_Joueur1 () {
		//key_1 = BombermanGame.getKey_1();
	}
	
	@Override
	public AgentAction doAction(Agent agent, ArrayList<Agent> agents) {

	//	ActionClient = BombermanGame.getActionClient();
		if (BombermanGame.isLegalMove(agent,ActionC)) {
				return ActionC;
				
			}else
				return AgentAction.STOP;
//		if (BombermanGame.isLegalMove(agent,key_1.getKaction())) {
//			return key_1.getKaction();
//			
//		}else
//			return AgentAction.STOP;

	}

	@Override
	public AgentAction doActionPerceptron(Agent agent, ArrayList<Agent> agents, BombermanGame BBMG) {
		// TODO Auto-generated method stub
		return null;
	}

	public AgentAction getActionC() {
		return ActionC;
	}

	public void setActionC(AgentAction actionClient) {
		ActionC = actionClient;
	}

}
