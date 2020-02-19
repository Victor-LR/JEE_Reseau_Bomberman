package pattern_etats;
import java.util.ArrayList;

import agents.Agent;
import agents.Agent_Bomberman;
import game.BombermanGame;

public class BombermanNormal implements BombermanEtat{
	private BombermanAgress etat;
	private Agent_Bomberman agent;
	private BombermanGame game;
	private ArrayList<Agent> list_agent;
	
	public BombermanNormal(Agent_Bomberman agent_b, BombermanGame game, ArrayList<Agent> liste, BombermanAgress state) {
		this.etat = state;
		this.agent = agent_b;
		this.game = game;
		list_agent = liste;
	}

	@Override
	public void isNormal() {

	}

	@Override
	public void isInvincible() {
		etat.setEtat(new BombermanInvincible(agent,game,list_agent,etat));
	}

}
