package beans;

import java.util.Date;

public class Partie {
	private int id_partie;
	private String pseudo_util;
	private int score_partie;
	private Date date_partie;
	private String resultat_partie;

	public Partie(int id, String pseudo, Date date, int score, String resultat) {
		id_partie = id;
		pseudo_util = pseudo;
		score_partie = score;
		date_partie = date;
		resultat_partie = resultat;
	}

	public int getId_partie() {
		return id_partie;
	}

	public void setId_partie(int id_partie) {
		this.id_partie = id_partie;
	}

	public String getPseudo_util() {
		return pseudo_util;
	}

	public void setPseudo_util(String pseudo_util) {
		this.pseudo_util = pseudo_util;
	}

	public int getScore_partie() {
		return score_partie;
	}

	public void setScore_partie(int score) {
		this.score_partie = score;
	}

	public Date getDate_partie() {
		return date_partie;
	}

	public void setDate_partie(Date date_partie) {
		this.date_partie = date_partie;
	}

	public String getResultat_partie() {
		return resultat_partie;
	}

	public void setResultat_partie(String resultat_partie) {
		this.resultat_partie = resultat_partie;
	}
}
