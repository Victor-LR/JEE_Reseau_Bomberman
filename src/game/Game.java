package game;

import java.util.ArrayList;

import view.Observer;

public abstract class Game implements Runnable, Observable {

	protected int turn;
	protected int maxTurn;
	protected ArrayList<Observer> List_observers = new ArrayList<Observer>();
	
	protected boolean isRunning;
	
	private Thread thread;
	private long time = 1000;
	
	public Game() {
		this.maxTurn = 100;
	}

	//Initialisation du Jeu
	public void init(){	
		turn = 0;
		isRunning = true;
		initializeGame();
		notifyObserver();
	}
	
	
	public void step(){
		if(gameContinue() && turn < maxTurn) {
			turn++;
			takeTurn();
		}else {
			isRunning = false;
			gameOver();
		}
		notifyObserver();
	}
	
	public int getTurn() {
		return turn;
	}

	public void run() {
		while(isRunning) {
			step();
			
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		isRunning = false;
	}
	
	public void launch() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public Thread getThread() {
		return thread;
	}

	//Getters et Setters
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	//Méthodes interface Observable
	
	public int getMaxTurn() {
		return maxTurn;
	}

	public void setMaxTurn(int maxTurn) {
		this.maxTurn = maxTurn;
	}

	@Override
	public void registerObserver(Observer observer) {
		this.List_observers.add(observer);
		
	}

	@Override
	public void removeObserver(Observer observer) {
		this.List_observers.remove(observer);	
	}

	@Override
	public void notifyObserver() {
		for(int i = 0; i < this.List_observers.size() ; i++)
			this.List_observers.get(i).update(this);		
	}

	public abstract boolean gameContinue();
	
	public abstract void initializeGame();
		
	public abstract void takeTurn();
	
	public abstract void gameOver();
	
}
