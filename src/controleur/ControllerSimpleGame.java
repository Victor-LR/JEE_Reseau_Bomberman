package controleur;

import game.Game;
import game.SimpleGame;
import map.Map;
import view.ViewCommand;
import view.ViewSimpleGame;

public class ControllerSimpleGame implements InterfaceController {

	private SimpleGame Simple_Jeu;
	
	public ControllerSimpleGame(SimpleGame Game) {
		//ViewCommand vue_command = new ViewCommand(this,Game);
		ViewSimpleGame vue_jeu = new ViewSimpleGame(this,Game);
		this.Simple_Jeu= Game;
		
		}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		this.Simple_Jeu.step();
	
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		if (this.Simple_Jeu == null) 
			this.Simple_Jeu.init();
		this.Simple_Jeu.launch();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.Simple_Jeu.init();
		this.Simple_Jeu.launch();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		this.Simple_Jeu.stop();
	}

	@Override
	public void setTime(double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeMap(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMap(Map map) {
		// TODO Auto-generated method stub
		
	}

}
