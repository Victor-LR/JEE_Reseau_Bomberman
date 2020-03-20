package beans;

import java.util.ArrayList;

public class Classement {

	private ArrayList<JoueurClassement> classementJournalierVictoire;
	private ArrayList<JoueurClassement> classementMensuelVictoire;
	private ArrayList<JoueurClassement> classementJournalierRatio;
	private ArrayList<JoueurClassement> classementMensuelRatio;

	public Classement() {
		classementJournalierVictoire = new ArrayList<JoueurClassement>();
		classementMensuelVictoire = new ArrayList<JoueurClassement>();
		classementJournalierRatio = new ArrayList<JoueurClassement>();
		classementMensuelRatio = new ArrayList<JoueurClassement>();
	}

	public ArrayList<JoueurClassement> getClassementJournalier() {
		return classementJournalierVictoire;
	}

	public void setClassementJournalier(ArrayList<JoueurClassement> classementJournalier) {
		this.classementJournalierVictoire = classementJournalier;
	}

	public ArrayList<JoueurClassement> getClassementMensuel() {
		return classementMensuelVictoire;
	}

	public void setClassementMensuel(ArrayList<JoueurClassement> classementMensuel) {
		this.classementMensuelVictoire = classementMensuel;
	}

	public ArrayList<JoueurClassement> getClassementJournalierRatio() {
		return classementJournalierRatio;
	}

	public void setClassementJournalierRatio(ArrayList<JoueurClassement> classementRatio) {
		this.classementJournalierRatio = classementRatio;
	}

	public ArrayList<JoueurClassement> getClassementMensuelRatio() {
		return classementMensuelRatio;
	}

	public void setClassementMensuelRatio(ArrayList<JoueurClassement> classementMensuelRatio) {
		this.classementMensuelRatio = classementMensuelRatio;
	}
}
