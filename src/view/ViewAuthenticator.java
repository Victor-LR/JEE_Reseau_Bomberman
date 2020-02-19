package view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;

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
	private JLabel label_mdp ;
	private JPasswordField mdp;
	
	public ViewAuthenticator() {
		
		affiche = new JFrame();
		affiche.setLayout(new GridLayout(3,1));
		valider = new JButton();
		valider.setText("Valider");
		
		label_id = new JLabel();
		label_id.setLayout(new GridLayout(1,2));
		texte_id = new JLabel(" Identifiant : ");
		identification = new JTextField(20);
		label_id.add(texte_id);
		label_id.add(identification);
		
		label_mdp = new JLabel();
		label_mdp.setLayout(new GridLayout(1,2));
		texte_mdp = new JLabel(" Mot de passe : ");
		mdp = new JPasswordField(20);
		label_mdp.add(texte_mdp);
		label_mdp.add(mdp);
		
		affiche.setTitle("Authentification");
		affiche.setSize(new Dimension(400, 500));
		Dimension windowSize = affiche.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 ;
		int dy = centerPoint.y - windowSize.height / 2 ;
		affiche.setLocation(dx, dy);
		
		affiche.add(label_id);
		affiche.add(label_mdp);
		affiche.add(valider);
		affiche.setVisible(true);
	}
	
	
}
