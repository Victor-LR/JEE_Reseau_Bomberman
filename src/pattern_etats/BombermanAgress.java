package pattern_etats;

import java.util.ArrayList;

import agents.Agent;
import agents.Agent_Bomberman;
import game.BombermanGame;

public class BombermanAgress {
	private BombermanEtat etat;
	private Agent_Bomberman agent;
	private ArrayList<Agent> list_agent;
	
	public BombermanAgress(Agent_Bomberman agent_b, BombermanGame game, ArrayList<Agent> liste) {
		agent=agent_b;
		list_agent=liste;
		etat=new BombermanNormal(agent_b,game,list_agent,this);
	}
	
	public void isNormal() {
		etat.isNormal();
	}
	
	public void isInvincible() {
		etat.isInvincible();
	}

	public Agent_Bomberman getAgent() {
		return agent;
	}

	public BombermanEtat getEtat() {
		return etat;
	}

	public void setEtat(BombermanEtat etat) {
		this.etat = etat;
	}
}
