package strategie;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import game.BombermanGame;

public class Strategie_Bird implements Comportement{

	public AgentAction doAction(Agent agent, ArrayList<Agent> agents) {
		
		for(int i =0; i<agents.size(); i++) {
//			 Détecte un bomberman sur un rayon de 3 cases
			System.out.println(agents.get(i).getType() +"   "+agents.get(i).getX() + "    " + agents.get(i).getY());
			AgentAction[] listaction = AgentAction.values();
			int action_random = (int) (Math.random()*listaction.length);
			
			if(agents.get(i).getType()=='B' && (agents.get(i).getX()< agent.getX()+3) && (agents.get(i).getX() > agent.getX()-3) && (agents.get(i).getY() <= agent.getY()+3) && (agents.get(i).getY() >= agent.getY()-3) && BombermanGame.isFlying(agent, listaction[action_random]))
			{
				return listaction[action_random];
			}
		}
		return AgentAction.STOP;
	}

	@Override
	public AgentAction doActionPerceptron(Agent agent, ArrayList<Agent> agents, BombermanGame BBMG) {
		// TODO Auto-generated method stub
		return null;
	}


}
