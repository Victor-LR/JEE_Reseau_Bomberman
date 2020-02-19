package agents;

import java.util.ArrayList;

import game.BombermanGame;
import strategie.Comportement;


public class Agent_Bird extends Agent_Ennemi{

	public Agent_Bird(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible,
			boolean isSick,Comportement strat) {
		super(x, y, agentAction, type, color, isInvincible, isSick,strat);
	}


}
