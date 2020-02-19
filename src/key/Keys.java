package key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import agents.AgentAction;
import map.Map;

public class Keys implements KeyListener {

private AgentAction Kaction;
	
	private static int Deplacement; 
	
	public Keys () {
		Deplacement = 0;
		bool_to_action();
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {

		switch(evt.getKeyChar()) {
		case 'z':
			Deplacement = 1;
			bool_to_action();
			break;
		case 'q':
			Deplacement = 2;
			bool_to_action();
			break;
		case 's':
			Deplacement = 3;
			bool_to_action();
			break;
		case 'd':
			Deplacement = 4;
			bool_to_action();
			break;
		case 'e':
			Deplacement = 5;
			bool_to_action();
			break;
		default :
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		switch(evt.getKeyChar()) {
		case 'z':
			Deplacement = 1;
			bool_to_action();
			break;
		case 'q':
			Deplacement = 2;
			bool_to_action();
			break;
		case 's':
			Deplacement = 3;
			bool_to_action();
			break;
		case 'd':
			Deplacement = 4;
			bool_to_action();
			break;
		case 'e':
			Deplacement = 5;
			bool_to_action();
			break;
		default :
			break;
		}
	}

	public AgentAction getKaction() {
		return Kaction;
	}

	private void bool_to_action() {
		switch(Deplacement){
		case 0:
			setKaction(AgentAction.STOP);

			break;
		case 1:
			setKaction(AgentAction.MOVE_UP);
			break;
		case 2:
			setKaction(AgentAction.MOVE_LEFT);
			break;
		case 3:
			setKaction(AgentAction.MOVE_DOWN);
			break;
		case 4:
			setKaction(AgentAction.MOVE_RIGHT);
			break;
		case 5:
			setKaction(AgentAction.PUT_BOMB);
			break;
		default:
			break;
		}
//		
	}
	
	private void setKaction(AgentAction action) {
		this.Kaction = action;
	}

}
