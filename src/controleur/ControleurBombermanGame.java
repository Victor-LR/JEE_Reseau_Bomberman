package controleur;

import game.BombermanGame;
import map.Map;
import view.ViewBombermanGame;
import view.ViewCommand;

public class ControleurBombermanGame implements InterfaceController {

	private BombermanGame Jeu_bomberman;
	private ViewBombermanGame vue_jeu;
	ViewCommand vue_commande;
	private static boolean perceptron = false;
	private static Map map;
	
	public ControleurBombermanGame(boolean perc) {
		ControleurBombermanGame.perceptron = perc;
		this.Jeu_bomberman = new BombermanGame();

		if (perceptron) {
			Jeu_bomberman.setTime(1);
		}else {
			vue_jeu = new ViewBombermanGame(this,Jeu_bomberman,"layouts/niveau1.lay");
			vue_commande = new ViewCommand(this,Jeu_bomberman,vue_jeu.getJframe_bbm());
			Jeu_bomberman.setListAgentsStart(map.getStart_agents());
		}
		
		
	}

	@Override
	public void step() {
		this.Jeu_bomberman.step();		
	}

	@Override
	public void start() {
		if (this.Jeu_bomberman.getAgentList().size() == 0) 
			this.Jeu_bomberman.init();
		this.Jeu_bomberman.launch();
	}

	@Override
	public void run() {
		this.Jeu_bomberman.init();
		this.Jeu_bomberman.launch();
	}

	@Override
	public void stop() {
		this.Jeu_bomberman.stop();

	}

	@Override
	public void setTime(double time) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void changeMap(String name) {
		stop();
		vue_jeu.getJframe_bbm().dispose();
		Jeu_bomberman = new BombermanGame();
		vue_jeu = new ViewBombermanGame(this,Jeu_bomberman,"layouts/" + name);
		vue_commande = new ViewCommand(this,Jeu_bomberman,vue_jeu.getJframe_bbm());
		Jeu_bomberman.setListAgentsStart(map.getStart_agents());
	}

	public static boolean isPerceptron() {
		return perceptron;
	}

	public void setPerceptron(boolean perceptron) {
		ControleurBombermanGame.perceptron = perceptron;
	}

	public BombermanGame getJeu_bomberman() {
		return Jeu_bomberman;
	}

	public static Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
}
