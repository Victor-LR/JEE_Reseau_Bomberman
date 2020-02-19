package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import agents.Agent;
import controleur.InterfaceController;
import game.BombermanGame;
import game.Observable;
import map.Map;
import strategie.Strategie_Joueur1;

public class ViewBombermanGame implements Observer {
	
	
	private JFrame jframe_bbm;
	private PanelBomberman Plateau_jeu;
	private static Map map_jeu;
	private ArrayList<Agent> ListStartAgent;
	private InterfaceController controleur;
	private boolean[][] list_breakable_walls;

	public ViewBombermanGame(InterfaceController control, BombermanGame Jeu, String filename) {
		this.controleur = control;
		Jeu.registerObserver(this);
		jframe_bbm = new JFrame();
		try {
			map_jeu = new Map(filename);
			control.setMap(map_jeu);
			this.Plateau_jeu = new PanelBomberman(map_jeu);
			ListStartAgent = map_jeu.getStart_agents();
			list_breakable_walls = map_jeu.getStart_brokable_walls();
			//Jeu.setListAgentsStart(ListStartAgent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Plateau_jeu.addKeyListener(Jeu.getKey_1());
		Plateau_jeu.addKeyListener(Jeu.getKey_2());
		
		jframe_bbm.setTitle("Game");
		jframe_bbm.setSize(new Dimension(map_jeu.getSizeX()*45, (map_jeu.getSizeY()*45+150)));
		Dimension windowSize = jframe_bbm.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 ;
		int dy = centerPoint.y - windowSize.height / 2 ;
		jframe_bbm.setLocation(dx, dy);
		jframe_bbm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		
		jframe_bbm.setLayout(new BorderLayout());
		
		jframe_bbm.add("Center",Plateau_jeu);
		
		PanelStrategie p_strat = new PanelStrategie(map_jeu);
		Jeu.setNom_strats(p_strat.getStrats());
		
		jframe_bbm.add("South",p_strat);
		
		jframe_bbm.setVisible(true);
		
	}

	public JFrame getJframe_bbm() {
		return jframe_bbm;
	}

	@Override
	public void update(Observable obs) {
		this.Plateau_jeu.repaint();
		this.Plateau_jeu.requestFocusInWindow();
		BombermanGame jeu_bbm = (BombermanGame) obs;
		//System.out.println(jeu_bbm.getAgentList().get(0).getType());
		this.Plateau_jeu.setInfoGame(jeu_bbm.getList_wall(), jeu_bbm.getAgentList(),jeu_bbm.getList_item(), jeu_bbm.getBombes());

		//this.turn.setText("Tour n° :"+ simple_jeu.getTurn());
	}
	
	
	public static Map getMap_jeu() {
		return map_jeu;
	}

	
}
