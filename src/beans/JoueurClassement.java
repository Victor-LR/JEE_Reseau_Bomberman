package beans;

public class JoueurClassement {
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
}
