package agents;

import strategie.Comportement;

/**
	Regroupe tous les types d'ennemis
*/
public abstract class Agent_Ennemi extends Agent {

	public Agent_Ennemi(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible,
			boolean isSick,Comportement strat) {
		super(x, y, agentAction, type, color, isInvincible, isSick, strat);
		// TODO Auto-generated constructor stub
	}

}
