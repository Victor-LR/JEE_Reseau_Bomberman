import controleur.ControleurBombermanGame;
import map.Map;
import perceptron.Reward;
import perceptron.Strategie_Perceptron;
import strategie.Comportement;
import strategie.Strategie_Aleatoire;

public class Main_Reward {

	public static void main(String[] args) {
		Reward R = new Reward();
		Map map = null;
		try {
			map = new Map("layouts/niveau1.lay");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Comportement perceptron = new Strategie_Perceptron();
		Comportement alea = new Strategie_Aleatoire();
		R.getAverageReward(1000, 100, perceptron,map );
		System.out.println(((Strategie_Perceptron)perceptron).getPerceptron().toString());
		R.vizualize(100, perceptron, map, 50);
	}
	

}
