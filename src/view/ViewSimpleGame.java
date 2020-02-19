package view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;

import controleur.InterfaceController;
import game.Observable;
import game.SimpleGame;

public class ViewSimpleGame implements Observer{

	JFrame jFrame = new JFrame();
	private JLabel turn = new JLabel();
	
	InterfaceController controller;
	
	public ViewSimpleGame(InterfaceController control ,SimpleGame jeu) {
		// TODO Auto-generated constructor stub
		this.controller = control;
		jeu.registerObserver(this);
		
		jFrame.setTitle("Game");
		jFrame.setSize(new Dimension(700, 500));
		Dimension windowSize = jFrame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 ;
		int dy = centerPoint.y - windowSize.height / 2 ;
		jFrame.setLocation(dx, dy);
		jFrame.setVisible(true);
		this.turn.setText("truc");
		jFrame.add(turn);
	}
	
	

	@Override
	public void update(Observable obs) {
		// TODO Auto-generated method stub
		SimpleGame simple_jeu = (SimpleGame) obs;
		this.turn.setText("Tour n° :"+ simple_jeu.getTurn());
		
	}

}
