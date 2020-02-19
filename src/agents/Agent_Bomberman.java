package agents;

import strategie.Comportement;

public class Agent_Bomberman extends Agent{

	private int range;
	private int nbBombes;
	private int points;
	private int vies;
	
	//Enregistre le tour où le bomberman est devenu inv/sick pour le remettre en état normal au n-ieme tour
	private int tourInv;
	private int tourSic;

	
	public Agent_Bomberman(int x, int y, AgentAction agentAction, char type, ColorAgent color, boolean isInvincible,
			boolean isSick,Comportement strat) {
		super(x, y, agentAction, type, color, isInvincible, isSick,strat);
		this.range = 2;
		this.nbBombes = 1;
		this.tourInv = 0;
		this.tourSic = 0;
		this.points = 0;
		this.vies = 3;
	}

	public int getTourInv() {
		return tourInv;
	}

	public void setTourInv(int tourInv) {
		this.tourInv = tourInv;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getNbBombes() {
		return nbBombes;
	}

	public void setNbBombes(int nbBombes) {
		this.nbBombes = nbBombes;
	}

	public int getTourSic() {
		return tourSic;
	}

	public void setTourSic(int tourSic) {
		this.tourSic = tourSic;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getVies() {
		return vies;
	}

	public void setVies(int vies) {
		this.vies = vies;
	}


}
