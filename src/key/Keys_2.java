package key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import agents.AgentAction;
import map.Map;

public class Keys_2 implements KeyListener{
	
	private AgentAction Kaction;

	private static int Deplacement; 
	
	public Keys_2() {
		Deplacement = 0;
		bool_to_action();

	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		switch(evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			Deplacement = 1;
			bool_to_action();
			break;
		case KeyEvent.VK_LEFT:
			Deplacement = 2;
			bool_to_action();
			break;
		case KeyEvent.VK_DOWN:
			Deplacement = 3;
			bool_to_action();
			break;
		case KeyEvent.VK_RIGHT:
			Deplacement = 4;
			bool_to_action();
			break;
		case KeyEvent.VK_CONTROL:
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
		switch(evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			Deplacement = 1;
			bool_to_action();
			break;
		case KeyEvent.VK_LEFT:
			Deplacement = 2;
			bool_to_action();
			break;
		case KeyEvent.VK_DOWN:
			Deplacement = 3;
			bool_to_action();
			break;
		case KeyEvent.VK_RIGHT:
			Deplacement = 4;
			bool_to_action();
			break;
		case KeyEvent.VK_CONTROL:
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

	public void bool_to_action() {
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
	}
	
	public void setKaction(AgentAction kaction) {
		Kaction = kaction;
	}
	
}

