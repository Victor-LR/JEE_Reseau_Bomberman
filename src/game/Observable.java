package game;

import view.Observer;

public interface Observable {
	public void registerObserver(Observer observer);
	void removeObserver(Observer observer);
	void notifyObserver();
}
