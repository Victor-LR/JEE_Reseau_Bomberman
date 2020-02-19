package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import agents.Agent;
import map.Map;

public class PanelStrategie extends JPanel {

	private ArrayList<String> Strats = new ArrayList<String>();
	private ArrayList<JComboBox> List_CBox = new ArrayList<JComboBox>();
	private int nb_bombermans = 0;
	
	public PanelStrategie(Map map) {
		
		for(Agent agent : map.getStart_agents()) {
			if(agent.getType() == 'B') nb_bombermans++;
			
				
		}
				
		for(int i = 0 ; i < nb_bombermans ; i++) {
			JComboBox list_strat = new JComboBox();
			List_CBox.add(list_strat);
			
			File repertoire = new File("src/strategie");
			File[] files = repertoire.listFiles();
			
			for(int j = 0; j< files.length; j++) {
				String nom_strat = files[j].getName();

				if (nom_strat != "Comportement.java")
					list_strat.addItem(nom_strat.substring(10));
			}
			list_strat.setSelectedIndex(0);
			//Strats.add(list_strat.getSelectedItem().toString());
			
			Strats.add(list_strat.getSelectedItem().toString());
			
			list_strat.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent evenement) {

					Strats.set(List_CBox.indexOf(list_strat),list_strat.getSelectedItem().toString());
				}
			});
			//Strats.add(list_strat);
			this.add(list_strat);
		
		}
		
	}


	public ArrayList<String> getStrats() {
		return Strats;
	}

}
