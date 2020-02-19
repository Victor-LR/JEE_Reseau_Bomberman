package controleur;

import map.Map;

public interface InterfaceController {

	public void step();
	public void start();
	public void run();
	public void stop();
	public void setTime(double time);
	public void changeMap(String name);
	public void setMap(Map map);
}
