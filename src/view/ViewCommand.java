package view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controleur.InterfaceController;
import game.BombermanGame;
import game.Game;
import game.Observable;
import game.SimpleGame;

public class ViewCommand implements Observer{

	private JPanel vueCommand /*= new JFrame()*/;
	

	private InterfaceController controller;
	
	JButton InitChoice;
	JButton StepChoice;
	JButton RunChoice;
	JButton StopChoice;
	JComboBox MapChoice;
	JSlider slider;
	
	private JLabel turn = new JLabel();
	
	public ViewCommand(InterfaceController control ,BombermanGame jeu, JFrame frame) {

		this.controller=control;
		jeu.registerObserver(this);
		
		//vueCommand = frame;
		
		/*vueCommand = new JFrame();
		vueCommand.setTitle("Commande");
		vueCommand.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vueCommand.setSize(new Dimension(700, 300));*/
		
		/*Dimension windowSize = vueCommand.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 - 350; 
        vueCommand.setLocation(dx, dy);*/
		vueCommand = new JPanel();

		
		
		JPanel panneauState = new JPanel(new GridLayout(2, 1));
		JPanel panneauCommande = new JPanel(new GridLayout(1, 4));
		JPanel panneauTour = new JPanel(new GridLayout(1, 2)); 
		
		
	//Bouton Restart
		Icon icon_restart = new ImageIcon("Icones/icon_restart.png");
		InitChoice = new JButton(icon_restart);
		panneauCommande.add(InitChoice);
		
		InitChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				controller.run();
				StopChoice.setEnabled(true);
				StepChoice.setEnabled(false);
				InitChoice.setEnabled(false);
				RunChoice.setEnabled(false);
			}
			});
		
	//Bouton Step
		Icon icon_step = new ImageIcon("Icones/icon_step.png");
		StepChoice = new JButton(icon_step);
		panneauCommande.add(StepChoice);
		
		StepChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				controller.step();
			}
			});
	//Bouton Start
		Icon icon_run = new ImageIcon("Icones/icon_run.png");
		RunChoice = new JButton(icon_run);
		panneauCommande.add(RunChoice);
		
		RunChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				controller.start();
				StopChoice.setEnabled(true);
				StepChoice.setEnabled(false);
				InitChoice.setEnabled(false);
				RunChoice.setEnabled(false);
				
			}
			});
	//Bouton Pause
		Icon icon_pause = new ImageIcon("Icones/icon_pause.png");
		StopChoice = new JButton(icon_pause);
		panneauCommande.add(StopChoice);
		
		StopChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				controller.stop();
				StopChoice.setEnabled(false);
				StepChoice.setEnabled(true);
				InitChoice.setEnabled(true);
				RunChoice.setEnabled(true);
			}
			});
		

		
	//Barre de vitesse
		slider = new JSlider(1, 10, 1);
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		slider.addChangeListener(new ChangeListener() {
			long temps_start = jeu.getTime();
			
		    public void stateChanged(ChangeEvent event) {
		        int value = slider.getValue();
		        jeu.setTime(temps_start/value);
		      }
		 });
		
		StopChoice.setEnabled(false);
		StepChoice.setEnabled(false);
		InitChoice.setEnabled(false);
		
		panneauTour.add(slider);
		panneauTour.add(turn);
		
		panneauState.add(panneauCommande);
		panneauState.add(panneauTour);
		vueCommand.add(panneauState);
		vueCommand.setVisible(true);
		
		
		
	//Liste Map
		MapChoice=new JComboBox();
		File repertoire = new File("layouts");
		File[] files = repertoire.listFiles();
		for(int i = 0; i< files.length; i++)
			MapChoice.addItem(files[i].getName());
		MapChoice.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evenement) {
				StopChoice.setEnabled(false);
				StepChoice.setEnabled(true);
				InitChoice.setEnabled(true);
				RunChoice.setEnabled(true);
				controller.changeMap(MapChoice.getSelectedItem().toString());
			}
		});
		
		
		panneauCommande.add(MapChoice);
		frame.add("North",vueCommand);
		frame.setSize(new Dimension(frame.getWidth()+1,frame.getHeight()+1));
		
	}

	public JComboBox getMapChoice() {
		return MapChoice;
	}

	@Override
	public void update(Observable obs) {
		// TODO Auto-generated method stub
		BombermanGame simple_jeu = (BombermanGame) obs;
		this.turn.setText("Tour n° :"+ simple_jeu.getTurn());
		
	}

	public JPanel getVueCommand() {
		return vueCommand;
	}
}
