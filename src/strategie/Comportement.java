package strategie;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import game.BombermanGame;

public interface Comportement {
	public AgentAction doAction(Agent agent, ArrayList<Agent> agents);
	public AgentAction doActionPerceptron(Agent agent, ArrayList<Agent> agents,BombermanGame BBMG);
	

}
