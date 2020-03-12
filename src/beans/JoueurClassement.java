package beans;

public class JoueurClassement {
	private float ratio;
	private int nbDefaite;
	private int nbVictoire;
	private String pseudo;

	public JoueurClassement(String ps, int nb) {
		nbVictoire = nb;
		pseudo = ps;
	}

	public int getNbVictoire() {
		return nbVictoire;
	}

	public void setNbVictoire(int nbVictoire) {
		this.nbVictoire = nbVictoire;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio() {
		float victoire = nbVictoire;
		float defaite = nbDefaite;
		if ((victoire + defaite) != 0) {
			ratio = victoire / (victoire + defaite) * 100;
		}
	}

	public int getNbDefaite() {
		return nbDefaite;
	}

	public void setNbDefaite(int nbDefaite) {
		this.nbDefaite = nbDefaite;
	}
}
