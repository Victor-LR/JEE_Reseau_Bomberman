package pattern_etats;
import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;
import game.BombermanGame;

public class BombermanInvincible implements BombermanEtat{

	private BombermanAgress etat;
	private BombermanGame game;
	private Agent_Bomberman agent;
	private ArrayList<Agent> list_agent;
	
	public BombermanInvincible(Agent_Bomberman agent_b, BombermanGame game, ArrayList<Agent> liste, BombermanAgress state) {
		this.etat=state;
		this.agent = agent_b;
		this.game = game;
		this.list_agent=liste;
	}

	
	@Override
	public void isNormal() {
		// TODO Auto-generated method stub
		etat.setEtat(new BombermanNormal(agent,game,list_agent,etat));
	}

	@Override
	public void isInvincible() {
		// TODO Auto-generated method stub
		if(agent.isInvincible())
		{
			for(int i = 0; i<list_agent.size();i++) {
				if(list_agent.get(i).getId()!=agent.getId()) {
					AgentAction action = AgentAction.STOP;
					if(list_agent.get(i).getY()==agent.getY() && agent.getX()<list_agent.get(i).getX()) {
						action=AgentAction.MOVE_RIGHT;
					}
					
					if(list_agent.get(i).getY()==agent.getY() && agent.getX()>list_agent.get(i).getX()) {
						action = AgentAction.MOVE_LEFT;
					}
					
					if(list_agent.get(i).getX()==agent.getX() && agent.getY()<list_agent.get(i).getY()) {
						action = AgentAction.MOVE_DOWN;
					}
					
					if(list_agent.get(i).getX()==agent.getX() && agent.getY()>list_agent.get(i).getY()) {
						action = AgentAction.MOVE_UP;
					}
										
					if(list_agent.get(i).getY()>agent.getY()) {
						action = AgentAction.MOVE_DOWN;
					}
					
					if(list_agent.get(i).getY()<agent.getY()) {
						action = AgentAction.MOVE_UP;
					}
					
					if(game.isLegalMove(agent, action))
						game.moveAgent(agent, action);
					else
						game.placeBomb(agent);
					break;
				}
			}
		}
		else
			isNormal();
	}
}
