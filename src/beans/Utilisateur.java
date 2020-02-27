package beans;

public class Utilisateur {
	private Integer identifiant;
	private String nom;
	private String prenom;
	private String pseudo;
	private String mdp;
	private String email;

	public Utilisateur(String nom, String prenom, String pseudo, String mdp, String email, Integer id) {
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setPseudo(pseudo);
		this.setMdp(mdp);
		this.setEmail(email);
		this.identifiant = id;
	}

	public Integer getIdentifiant() {
		return identifiant;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
