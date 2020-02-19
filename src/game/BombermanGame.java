package game;

import java.util.ArrayList;

import agents.Agent;
import agents.AgentAction;
import agents.Agent_Bomberman;
import controleur.ControleurBombermanGame;
import factory.AgentFactory;
import factory.BombermanFactory;
import factory.EnnemyFactory;
import key.Keys;
import key.Keys_2;
import objects.InfoBomb;
import objects.InfoItem;
import objects.ItemType;
import objects.StateBomb;
import pattern_etats.BombermanAgress;
import pattern_etats.BombermanEtat;
import pattern_etats.BombermanNormal;

public class BombermanGame extends Game implements Observable {

	int tailleX;
	int tailleY;
	
    private ArrayList<Agent> agentList;
    private ArrayList<Agent> ListAgentsStart;
    private ArrayList<InfoBomb> bombes;
    private static boolean[][] list_wall;
    private ArrayList<InfoItem> list_item;
    private ArrayList<BombermanAgress> list_etat;

    private AgentFactory ennemyFactory;
    private AgentFactory bombermanFactory;
    
    private static Keys key_1;
    private static Keys_2 key_2;
    
    private ArrayList<String> nom_strats;
    
    private int PointsPartie = 0;
    

	public BombermanGame() {
		agentList = new ArrayList<Agent>();
		ListAgentsStart = new ArrayList<Agent>();
		bombes = new ArrayList<InfoBomb>();
		list_item = new ArrayList<InfoItem>();
		key_1 = new Keys();
		key_2 = new Keys_2();
		
    }

	@Override
	public boolean gameContinue() {
		//Si le jeu ne contient plus qu'un seul agent qui est un bomberman -> Game Over
		//Si le jeu ne contient plus de bombermans -> Game Over
		
		if (agentList.size()==1 && agentList.get(0).getType()=='B')
		{
			System.out.println("Plus d'ennemies !");
			return false;
		}
		for(int i = 0; i<agentList.size();i++) {
			if (agentList.get(i).getType()=='B')
				return true;
			if (i==agentList.size()-1)
			{
				System.out.println("Plus de bomberman !");
				return false;
			}
		}
		return false;
	}

	@Override
	public void initializeGame() {
		
		agentList = new ArrayList<Agent>();
		ennemyFactory=new EnnemyFactory();
		bombermanFactory=new BombermanFactory();
		bombes = new ArrayList<InfoBomb>();
		list_item = new ArrayList<InfoItem>();
		list_etat = new ArrayList<BombermanAgress>();
		
		int ind_bbm = 0;
		for(Agent agent : ListAgentsStart)
		{
			if(agent.getType()=='B') {
				System.out.println("Bomb");
				Agent_Bomberman bomberman = (Agent_Bomberman) bombermanFactory.createAgent(agent.getX(), agent.getY(), agent.getAgentAction(), 'B', agent.getColor(), agent.isInvincible(), agent.isSick(),this.getNom_strats().get(ind_bbm));
				agentList.add(bomberman);
				list_etat.add(new BombermanAgress(bomberman, this,agentList));
				System.out.println("Strat " + this.getNom_strats().get(ind_bbm));
				ind_bbm++;
			}
			else {
				//System.out.println("Enn");
				agentList.add(ennemyFactory.createAgent(agent.getX(), agent.getY(), agent.getAgentAction(), agent.getType(), agent.getColor(), agent.isInvincible(), agent.isSick(),null));
			}
		}
		
		this.tailleX = ControleurBombermanGame.getMap().getStart_brokable_walls().length;
		this.tailleY = ControleurBombermanGame.getMap().getStart_brokable_walls()[0].length;
		list_wall = new boolean[tailleX][tailleY];
		
		for(int i = 0 ; i < tailleX ; i++) {
			for(int j = 0 ; j < tailleY ; j++) {
				list_wall[i][j] = ControleurBombermanGame.getMap().getStart_brokable_walls()[i][j];
			}
		}
		list_item = new ArrayList<InfoItem>();

	}

	public void setListAgentsStart(ArrayList<Agent> listAgentsStart) {
		ListAgentsStart = listAgentsStart;
	}
	
	@Override
	//Actions effectuées par les agents tout les tours
	public void takeTurn() {
		
		for(int i = 0; i < agentList.size(); i++) {
			
			Agent agent = agentList.get(i);
			if(!agent.isInvincible()) {
				AgentAction action = agent.doAction(agentList,this);
				//AgentAction action = key_1.getKaction();
				
				moveAgent(agent,action);
				
				if (agent.getType()=='B') {
					itemBoost(agent.getX(), agent.getY(), (Agent_Bomberman)agent);
					if (AgentAction.PUT_BOMB == action && !agent.isSick())
					{
						placeBomb((Agent_Bomberman)agent);
					}
					invTurn((Agent_Bomberman)agent);
					sicTurn((Agent_Bomberman)agent);
					this.PointsPartie = ((Agent_Bomberman) agent).getPoints();
					//System.out.println("Points bomb" + i+" " +bombPoints((Agent_Bomberman) agent));
				}
				else
				{
					bombermanKill(agent);
				}
			}
			else
			{
				invTurn((Agent_Bomberman)agent);
				sicTurn((Agent_Bomberman)agent);
				if(agent.getStrategie().getClass().getName()!="strategie.Strategie_Joueur1" && agent.getClass().getName()!="strategie.Strategie_Joueur2") {
					for(int j = 0; j < list_etat.size();j++) {
						if(agent.getId()==list_etat.get(j).getAgent().getId())
							list_etat.get(j).isInvincible();
					}
				}
				else
				{
					AgentAction action = agent.doAction(agentList,this);
					moveAgent(agent,action);
					itemBoost(agent.getX(), agent.getY(), (Agent_Bomberman)agent);
					if (AgentAction.PUT_BOMB == action && !agent.isSick())
					{
						placeBomb((Agent_Bomberman)agent);
					}
					invTurn((Agent_Bomberman)agent);
					sicTurn((Agent_Bomberman)agent);
				}
			}			
		}
		bombeTurn();
	}

	private int bombPoints(Agent_Bomberman agent) {
		// TODO Auto-generated method stub
		return agent.getPoints();
	}

	@Override
	public void gameOver() {
		System.out.println("Fin du jeu au tour : " + this.turn);
	}
	
	public ArrayList<Agent> getAgentList() {
		return agentList;
	}
	
	//Réalise le déplacement d'un agent
	public void moveAgent(Agent agent, AgentAction action)
	{
		int x = agent.getX();
		int y = agent.getY();

		switch (action) {
		//MOVE_UP,MOVE_DOWN,MOVE_LEFT,MOVE_RIGHT,STOP,PUT_BOMB
		case MOVE_UP:
			agent.setAgentAction(action);
				agent.setY(y-1);
			break;
			
		case MOVE_DOWN:
			agent.setAgentAction(action);
				agent.setY(y+1);
			break;

		case MOVE_LEFT:
			agent.setAgentAction(action);
				agent.setX(x-1);
			break;
			
		case MOVE_RIGHT:
			agent.setAgentAction(action);
				agent.setX(x+1);
			break;
			
		case STOP:
		case PUT_BOMB:
		default:
			agent.setAgentAction(AgentAction.STOP);
			break;
		}
	}
	
	//Réalise l'explosion d'une bombe et ses conséquences
	public void bombExplode(InfoBomb bomb)
	{
		int x = bomb.getX();
		int y = bomb.getY();

		ArrayList<Agent> agents = this.agentList;
		ArrayList<Integer> nbPoints = new ArrayList<Integer>(); 
		
		// TEST RANGE EAST
		
		for(int i = x; i<= x + bomb.getRange(); i++){
			// Vérifie si il y a un agent et le détruit
			for(int j = 0; j< agents.size(); j++){
				Agent agent = agents.get(j);
				if(agent.getX() == i && agent.getY() == y && bomb.getId()!=agent.getId()){
					nbPoints.add(addPoints(agents.get(j)));
					agents.remove(j);
				}
			}
			
			// Vérifie si il y a une bombe et la détruit
			for(int j = 0; j<bombes.size(); j++){
				InfoBomb bombe = bombes.get(j);
				if(bomb != bombe)
				if(bombe.getX() == i & bombe.getY() == y){
					bombe.setStateBomb(StateBomb.Boom);
					}
				bombe.setRange_wall_at(0, i-x);
			}
			
			//Vérifie si il y a un mur et le détruit
			if(i < list_wall.length)
				if(list_wall[i][y]) {
					list_wall[i][y]=false;
					nbPoints.add(10);
					if (!ControleurBombermanGame.isPerceptron())
						creerItem(i,y);
					break;
				}
		}
		
		// TEST RANGE SOUTH
		
		for(int i = y; i <= y +bomb.getRange(); i++){
			
			// Vérifie si il y a un agent et le détruit
			for(int j = 0; j< agents.size(); j++){
				Agent agent = agents.get(j);
				if(agent.getX() == x && agent.getY() == i && bomb.getId()!=agent.getId()){
					nbPoints.add(addPoints(agents.get(j)));
					agents.remove(j);
				}
			}
			
			// Vérifie si il y a une bombe et la détruit
			for(int j = 0; j<bombes.size(); j++){
				InfoBomb bombe = bombes.get(j);
				if(bomb != bombe)
					if(bombe.getX() == x & bombe.getY() == i ){
						bombe.setStateBomb(StateBomb.Boom);
					}
				bombe.setRange_wall_at(1, i-y);
			}
			//Vérifie si il y a un mur et le détruit
			if(i < list_wall[x].length)
				if(list_wall[x][i]){
					list_wall[x][i]=false;
					nbPoints.add(10);
					if (!ControleurBombermanGame.isPerceptron())
						creerItem(x,i);
					break;
				}
		}
		
		// TEST RANGE WEST
		
		for(int i = x; i >= x-bomb.getRange(); i--){
			
			// Vérifie si il y a un agent et le détruit
			for(int j = 0; j< agents.size(); j++){
				Agent agent = agents.get(j);
				if(agent.getX() == i && agent.getY() == y && bomb.getId()!=agent.getId()){
					nbPoints.add(addPoints(agents.get(j)));
					agents.remove(j);
				}
			}
			
			// Vérifie si il y a une bombe et la détruit
			for(int j = 0; j<bombes.size(); j++){
				InfoBomb bombe = bombes.get(j);
				if(bomb != bombe)
					if(bombe.getX() == i & bombe.getY() == y){
						bombe.setStateBomb(StateBomb.Boom);
					}
				bombe.setRange_wall_at(2, x-i);
			}
			//Vérifie si il y a un mur et le détruit
			if(i > 0)
				if(list_wall[i][y]){
					list_wall[i][y]=false;
					nbPoints.add(10);
					if (!ControleurBombermanGame.isPerceptron())
						creerItem(i,y);
					break;
				}
	
		}
		
		
		// TEST RANGE NORTH
		for(int i = y; i >= y-bomb.getRange(); i--){
			
			// Vérifie si il y a un agent et le détruit
			for(int j = 0; j< agents.size(); j++){
				Agent agent = agents.get(j);
				if(agent.getX() == x && agent.getY() == i && bomb.getId()!=agent.getId()){
					nbPoints.add(addPoints(agents.get(j)));
					agents.remove(j);
				}
			}
			
			// Vérifie si il y a une bombe et la détruit
			for(int j = 0; j<bombes.size(); j++){
				InfoBomb bombe = bombes.get(j);
				if(bomb != bombe)
				if(bombe.getX() == x & bombe.getY() == i){
					bombe.setStateBomb(StateBomb.Boom);
				}
				bombe.setRange_wall_at(3, y-i);
			}
			//Vérifie si il y a un mur et le détruit
			if(i > 0)
				if(list_wall[x][i]){
					list_wall[x][i]=false;
					nbPoints.add(10);
					if (!ControleurBombermanGame.isPerceptron())
						creerItem(x,i);
					break;
				}
		}

		//Ajout des points au bomberman correspondant
		for(int i = 0;i<agents.size(); i++) {
			if(agents.get(i).getType()=='B' && bomb.getId()==agents.get(i).getId()) {
				calculPt((Agent_Bomberman)agents.get(i), nbPoints);
			}
		}
		
	}
	
	//Réalise le tour d'une bombe
	public void bombeTurn(){
		
		for(int i = 0; i < bombes.size(); i++){

			InfoBomb bombe = bombes.get(i);							
				switch(bombe.getStateBomb()) {
				case Step1:
					bombe.setStateBomb(StateBomb.Step2);
					break;
					
				case Step2:
					bombe.setStateBomb(StateBomb.Step3);
					break;
					
				case Step3:
					bombe.setStateBomb(StateBomb.Boom);
					bombExplode(bombe);
					break;
					
				case Boom:
					
					bombes.remove(bombe);
					
					break;
				}
		}
	}
	
	//place une bombe à la position du bomberman
	public void placeBomb(Agent_Bomberman bomberman)
	{
		int x = bomberman.getX();
		int y = bomberman.getY();
		if(this.bombes.size()>=0) {
		InfoBomb bomb = new InfoBomb(x,y,bomberman.getRange(),StateBomb.Step1, bomberman.getId());
		bombes.add(bomb);
		}
	}
	
	
	//Créer un item à l'endroit du mur brisé

	public void creerItem(int x, int y) {
		ItemType[] listitem = ItemType.values();
		int drop_random = (int) (Math.random()*100);
		if(drop_random < 40) {
			int item_random = (int) (Math.random()*listitem.length);
			list_item.add(new InfoItem(x,y,listitem[item_random]));
		}
	}

	
	//Améliore le bomberman en fonction de l'item ramassé
	//Le bomberman pourra toujours avoir une bombe et/ou une range de 1 malgré les malus
	public void itemBoost(int x, int y, Agent_Bomberman agent) {
		for(int i=0; i < list_item.size();i++)
		{
			if(list_item.get(i).getX()==x && list_item.get(i).getY()==y && agent.getType()=='B')
			{
				switch(list_item.get(i).getType()) {
					case BOMB_DOWN:
						if(agent.getNbBombes()>1)
						{
							agent.setNbBombes(agent.getNbBombes()-1);
						}
						break;
					case BOMB_UP:
						agent.setNbBombes(agent.getNbBombes()+1);
						break;
					case FIRE_DOWN:
						if(agent.getRange()>1)
							agent.setRange(agent.getRange()-1);
						break;
					case FIRE_UP:
						agent.setRange(agent.getRange()+1);
						break;
					case FIRE_SUIT:
						agent.setInvincible(true);
						agent.setTourInv(this.turn);
						break;
					case SKULL:
						agent.setSick(true);
						agent.setTourSic(this.turn);
						break;
					default:
						break;
				}
				list_item.remove(i);
			}
		}
	}
	
	public void bombermanKill(Agent agent) {
		for(int i=0; i < agentList.size();i++)
		{
			if(agentList.get(i).getX()==agent.getX() && agentList.get(i).getY()==agent.getY() && !agentList.get(i).isInvincible() && agentList.get(i).getType()=='B')
			{
				if(!lifeRemaining((Agent_Bomberman) agentList.get(i)))
					agentList.remove(i);
			}
		}
	}
	
	//Vérifie si le mouvement respecte les règles du jeu
	public static boolean isLegalMove(Agent agent, AgentAction action) {
		boolean[][] liste_unbreakable_wall = ControleurBombermanGame.getMap().get_walls();
		
		switch (action) {
			case MOVE_DOWN:
				if (!liste_unbreakable_wall[agent.getX()][agent.getY()+1] && !list_wall[agent.getX()][agent.getY()+1])
					return true;
				break;
			case MOVE_UP:
				if (!liste_unbreakable_wall[agent.getX()][agent.getY()-1] && !list_wall[agent.getX()][agent.getY()-1])
					return true;
				break;
			case MOVE_RIGHT:
				if (!liste_unbreakable_wall[agent.getX()+1][agent.getY()] && !list_wall[agent.getX()+1][agent.getY()])
					return true;
				break;
			case MOVE_LEFT:
				if (!liste_unbreakable_wall[agent.getX()-1][agent.getY()] && !list_wall[agent.getX()-1][agent.getY()])
					return true;
				break;
			case PUT_BOMB:
				return true;
			default:
				break;
		}
		return false;
	}
	
	//Comme isLegalMove mais adapté pour l'agent Bird
	public static boolean isFlying(Agent agent, AgentAction action) {
		boolean[][] list_wall = ControleurBombermanGame.getMap().get_walls();

		
		switch (action) {
			case MOVE_DOWN:
				if (!list_wall[agent.getX()][agent.getY()+1])
					return true;
				break;
			case MOVE_UP:
				if (!list_wall[agent.getX()][agent.getY()-1])
					return true;
				break;
			case MOVE_RIGHT:
				if (!list_wall[agent.getX()+1][agent.getY()])
					return true;
				break;
			case MOVE_LEFT:
				if (!list_wall[agent.getX()-1][agent.getY()])
					return true;
				break;
			default:
				break;
		}
		return false;
	}
	
	//Enlève la maladie 10 tours plus tard
	public void sicTurn(Agent_Bomberman agent) {
		if(agent.getTourSic()+10 == this.turn)
			agent.setSick(false);
	}

	//Enlève l'invincibilité 10 tours plus tard
	public void invTurn(Agent_Bomberman agent) {
		if(agent.getTourInv()+10 == this.turn)
			agent.setInvincible(false);
	}
	
	// Test si il y a un ennemi à une position précise
	public boolean Isennemie(int x, int y) {
		
		for (Agent agent : this.agentList) {
			if ((agent.getType() != 'B') && (agent.getX() == x) && (agent.getY() == y))
				return true;
		}
		return false;
	}
	
	// Test si il y a une bombe à une position précise
	public boolean IsBombe(int x, int y) {
		
		for (InfoBomb bomb : this.bombes) {
			if ((bomb.getX() == x) && (bomb.getY() == y))
				return true;
		}
		return false;
	}
	
	// Test si il y a un mur à une position précise
	public boolean IsMur(int x, int y) {
		
		if (x < 0 || y < 0 || x > this.tailleX || y > this.tailleY)
			return false;
		else {
			try {
				return this.getList_wall()[x][y];
			} catch (Exception e) {
				return false;
			}
		}
			
	}
	
	//Liste des getteurs et setteurs
	public ArrayList<InfoBomb> getBombes() {
		return bombes;
	}

	public boolean[][] getList_wall() {
		return list_wall;
	}
	
	public ArrayList<InfoItem> getList_item() {
		return list_item;
	}
	
	public static Keys getKey_1() {
		return key_1;
	}
	
	public static Keys_2 getKey_2() {
		return key_2;
	}

	public ArrayList<String> getNom_strats() {
		return nom_strats;
	}

	public void setNom_strats(ArrayList<String> nom_strats) {
		this.nom_strats = nom_strats;
	}

	//Ajoute les points en fonction de l'ennemi tué
	public int addPoints(Agent ennemy) {
		int points=0;
		switch(ennemy.getType()) {
		case 'B':
			points = 50;
			break;
		
		case 'E':
			points = 50;
			break;
			
		case 'R':
			points = 50;
			break;
			
		case 'V':
			points = 50;
			break;
			
		default:
			break;
		}
		return points;
	}
	 
	public void calculPt(Agent_Bomberman agent, ArrayList<Integer> nbPoint) {
		int sum=agent.getPoints();
		for(int i:nbPoint)
		{
			sum+=i;
		}
		agent.setPoints(sum);
	}

	public int getPointsPartie() {
		return PointsPartie;
	}
	
	//Enlève les vies et le rend invulnérable pendant 10 tours
	//Renvoie un booleen pour tuer ou pas le bomberman
	public boolean lifeRemaining(Agent_Bomberman agent) {
		agent.setVies(agent.getVies() -1 );
		if(agent.getVies()==0)
			return false;
		else {
			agent.setInvincible(true);
			agent.setTourInv(this.turn);
			return true;
		}
	}
}
