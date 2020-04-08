package client;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import agents.AgentAction;
import key.Keys;

//Créer un Jframe afin de récupérer les déplacements saisis aux claviers, ou via les boutons, par l'utilisateur

public class PanelControl {
	
	private JFrame RecupKeys;
	private Keys key_1;
	
	JButton Gauche;
	JButton Haut;
	JButton Droite;
	JButton Bas;
	JButton Bombe;
	
	public PanelControl() {
		key_1 = new Keys();
		RecupKeys = new JFrame();
		RecupKeys.addKeyListener(key_1);
		RecupKeys.setVisible(true);
		Dimension windowSize = RecupKeys.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		RecupKeys.setSize(new Dimension(600, 200));
		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		RecupKeys.setLocation(dx-200, dy+200);
		
		JPanel Panneau = new JPanel(new GridLayout(3, 1));
		JPanel panneauCommande = new JPanel(new GridLayout(1, 5));
		
		JLabel Explication1 = new JLabel();
		Explication1.setText("Pour déplacer le bomberman vous pouvez utiliser les touches 'ZQSD' et 'E'.");
		Panneau.add(Explication1);
		
		JLabel Explication2 = new JLabel();
		Explication2.setText("Ou bien utiliser les boutons du panneau ci-dessous.");
		Panneau.add(Explication2);
		
		Icon icon_g = new ImageIcon("Icones/icon_gauche.png");
		Gauche = new JButton(icon_g);
		panneauCommande.add(Gauche);
		Gauche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				key_1.setKaction(AgentAction.MOVE_LEFT);
			}
			});
		
		Icon icon_h = new ImageIcon("Icones/icon_haut.png");
		Haut = new JButton(icon_h);
		panneauCommande.add(Haut);
		Haut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				key_1.setKaction(AgentAction.MOVE_UP);
			}
			});
		
		Icon icon_b = new ImageIcon("Icones/icon_bas.png");
		Bas = new JButton(icon_b);
		panneauCommande.add(Bas);
		Bas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				key_1.setKaction(AgentAction.MOVE_DOWN);
			}
			});
		
		Icon icon_d= new ImageIcon("Icones/icon_droite.png");
		Droite = new JButton(icon_d);
		panneauCommande.add(Droite);
		Droite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				key_1.setKaction(AgentAction.MOVE_RIGHT);
			}
			});
		

		
		Icon icon_bombe= new ImageIcon("image/Bomb_0.png");
		Bombe = new JButton(icon_bombe);
		panneauCommande.add(Bombe);
		Bombe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				key_1.setKaction(AgentAction.PUT_BOMB);
			}
			});
		
		Panneau.add(panneauCommande);
		RecupKeys.add(Panneau);
		RecupKeys.setSize(new Dimension(600+1, 200+1));
	}

	public JFrame getJFrame() {
		return RecupKeys;
	}

	public Keys getKey_1() {
		return key_1;
	}

	
}
