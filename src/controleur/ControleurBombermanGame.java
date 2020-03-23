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
	String pseudo = "";
	private boolean exit; 
	
	public ControleurBombermanGame(boolean perc, String pseud) {
		exit = false;
		ControleurBombermanGame.perceptron = perc;
		this.Jeu_bomberman = new BombermanGame();
		this.pseudo = pseud;
		if (perceptron) {
			Jeu_bomberman.setTime(1);
		}else {
			vue_jeu = new ViewBombermanGame(this,Jeu_bomberman,"layouts/niveau1.lay",this.pseudo);
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
		vue_jeu = new ViewBombermanGame(this,Jeu_bomberman,"layouts/" + name,this.pseudo);
		vue_commande = new ViewCommand(this,Jeu_bomberman,vue_jeu.getJframe_bbm());
		Jeu_bomberman.setListAgentsStart(map.getStart_agents());
	}
	
	public boolean FrameActive() {
		//this.vue_jeu.getJframe_bbm()
		return this.vue_jeu.getJframe_bbm().isDisplayable();
		
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

	@Override
	public void exit() {
		setExit(true);
		this.vue_jeu.getJframe_bbm().dispose();
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public ViewBombermanGame getVue_jeu() {
		return vue_jeu;
	}
}
