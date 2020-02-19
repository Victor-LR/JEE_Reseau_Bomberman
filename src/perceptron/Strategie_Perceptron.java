package perceptron;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import game.BombermanGame;
import strategie.Comportement;

public class Strategie_Perceptron implements Comportement {
	
	private Perceptron perceptron;

	@Override
	public AgentAction doActionPerceptron(Agent agent, ArrayList<Agent> agents,BombermanGame BBMG) {
		
		this.perceptron = new Perceptron(0.02,5,5);
		
		Reward reward = new Reward();
		
		SparseVector vecEtat = reward.getEtat(BBMG, agent);
		
		
		ArrayList<AgentAction> ListAction = new ArrayList<AgentAction>();
		double max_val = 0;
		
		// On ajoute dans une liste d'actions la ou les actions qui donnent le score le plus important
		
		for(AgentAction act : AgentAction.values()) {
			if(act != AgentAction.STOP && BBMG.isLegalMove(agent, act)) {

				double val_action = perceptron.getScore(reward.getEtatAction(vecEtat, act));
				if (val_action > max_val) {
					max_val = val_action;
					ListAction.clear();
					ListAction.add(act);
				}else if (val_action == max_val)
					ListAction.add(act);
			}
				
		}
		
		
		int action_random = (int) (Math.random()*ListAction.size());
		//System.out.println("				RANDO "+ action_random);
		if(ListAction.size() !=0 )
			return ListAction.get(action_random);
		else return AgentAction.STOP;
	}

	public Perceptron getPerceptron() {
		return perceptron;
	}

	public void setPerceptron(Perceptron perceptron) {
		this.perceptron = perceptron;
	}

	@Override
	public AgentAction doAction(Agent agent, ArrayList<Agent> agents) {
		// TODO Auto-generated method stub
		return null;
	}

}
