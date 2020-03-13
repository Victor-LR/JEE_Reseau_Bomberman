package view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ViewAuthenticator {

	private JFrame affiche;
	private JButton valider;

	private JLabel label_id;
	private JLabel texte_id;
	private JTextField identification;

	private JLabel texte_mdp;
	private JLabel label_mdp;
	private JPasswordField mdp;

	private String identifiant = "";
	private String mot_de_passe = "";

	public ViewAuthenticator() {

		affiche = new JFrame();
		affiche.setLayout(new GridLayout(3, 1));
		valider = new JButton();
		valider.setText("Valider");

		label_id = new JLabel();
		label_id.setLayout(new GridLayout(1, 2));
		texte_id = new JLabel(" Identifiant : ");
		identification = new JTextField(25);
		label_id.add(texte_id);
		label_id.add(identification);

		label_mdp = new JLabel();
		label_mdp.setLayout(new GridLayout(1, 2));
		texte_mdp = new JLabel(" Mot de passe : ");
		mdp = new JPasswordField(20);
		label_mdp.add(texte_mdp);
		label_mdp.add(mdp);

		affiche.setTitle("Authentification");
		affiche.setSize(new Dimension(400, 500));
		Dimension windowSize = affiche.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		affiche.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		affiche.setLocation(dx, dy);

		affiche.add(label_id);
		affiche.add(label_mdp);
		affiche.add(valider);
		affiche.setVisible(true);

		valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				identifiant = identification.getText().toString();
				mot_de_passe = mdp.getText().toString();
				// mot_de_passe = mdp.getSelectedText().toString();

				// System.out.println("Id :" + identifiant);
				// System.out.println("MDP :" + mot_de_passe);

			}
		});

	}

	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public String getIdentifiant() {
		// TODO Auto-generated method stub
		return identifiant;
	}

	public void FermerFenetre() {
		affiche.dispose();
	}

}
