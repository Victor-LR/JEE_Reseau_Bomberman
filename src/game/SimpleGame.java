package game;

public class SimpleGame extends Game implements Observable {

	public SimpleGame() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeTurn() {
		// TODO Auto-generated method stub
		System.out.println("Tour "+this.turn+" en cours");

	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		System.out.println("Fin de Jeu "+this.turn);
	}

	@Override
	public boolean gameContinue() {
		// TODO Auto-generated method stub
		return true;
	}

	public int getTurn() {
		// TODO Auto-generated method stub
		return this.turn;
	}

}
